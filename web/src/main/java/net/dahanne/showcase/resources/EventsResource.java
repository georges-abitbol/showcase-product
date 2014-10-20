package net.dahanne.showcase.resources;

import com.squareup.okhttp.OkHttpClient;
import net.dahanne.showcase.ServiceLocator;
import net.dahanne.showcase.UserService;
import net.dahanne.showcase.pojos.Event;
import net.dahanne.showcase.pojos.Result;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.signature.QueryStringSigningStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * resource exposed at "/api/ad/events" path
 */
@Path("/ad")
public class EventsResource {

  private static final Logger LOG = LoggerFactory.getLogger(EventsResource.class);
  private final UserService userService;
  private final OAuthConsumer consumer;

  public EventsResource() {
    userService = ServiceLocator.locate(UserService.class);
    consumer =  ServiceLocator.locate(OAuthConsumer.class);
  }

  @Path("/events")
  @GET
  @Produces(MediaType.APPLICATION_XML + ";charset=" + "UTF-8")
  public Result getEvent(@QueryParam("url") String url, @Context HttpHeaders httpHeaders) throws IOException {

    Result result =  new Result();
    if(url == null || url.trim().equals("")) {
       return new Result(false,"No url was provided");
    }

    LOG.warn("Received an event with the following url : " + url);
//    LOG.warn("Here are the headers sent along the request : ");
//    StringBuilder sb = new StringBuilder();
//
//    for (Map.Entry<String, List<String>> h : httpHeaders.getRequestHeaders().entrySet()) {
//      sb.append("key : " + h.getKey());
//      sb.append("value : " + h.getValue());
//      sb.append(System.lineSeparator());
//    }

    String signedUrl = null;
    try {
      signedUrl = consumer.sign(url);
      LOG.warn("Here is the signed url " + signedUrl);
    } catch (OAuthException e) {
      LOG.warn("There was an exception signing the url " + url);
      e.printStackTrace();
    }

    if (signedUrl != null) {
      Client client = ClientBuilder.newClient();
      WebTarget target = client.target(signedUrl);
      Event event = target.request(MediaType.APPLICATION_XML).get(Event.class);
      LOG.warn("The event details are : " + event);

      switch (event.getType()) {
        case SUBSCRIPTION_ORDER:
          LOG.warn("Subscription order");

          break;
        case SUBSCRIPTION_CANCEL:
          LOG.warn("Subscription cancellation");
          break;
      }


//      Response response = client.newCall(request).execute();
//      if (!response.isSuccessful()) {
//        throw new IOException("Unexpected code " + response);
//      }

//      Headers responseHeaders = response.headers();
//      for (int i = 0; i < responseHeaders.size(); i++) {
//        LOG.warn(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//      }
//
//      LOG.warn(response.body().string());
      result.setMessage("Account creation successful");
      result.setSuccess(true);
      result.setAccountIdentifier("new-account-identifier");

    }
    return result;

  }
}
