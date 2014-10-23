package net.dahanne.showcase.ad.resources;

import net.dahanne.showcase.*;
import net.dahanne.showcase.ad.enums.ErrorTypes;
import net.dahanne.showcase.persistence.*;
import net.dahanne.showcase.ad.pojos.Event;
import net.dahanne.showcase.ad.pojos.Result;
import net.dahanne.showcase.persistence.pojos.Account;
import net.dahanne.showcase.persistence.pojos.User;
import net.dahanne.showcase.persistence.services.AccountService;
import net.dahanne.showcase.persistence.services.UserService;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthException;
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
import java.util.ArrayList;

/**
 * resource exposed at "/api/ad/events" path
 */
@Path("/ad")
public class EventsResource {

  private static final Logger LOG = LoggerFactory.getLogger(EventsResource.class);
  private final AccountService accountService;
  private final UserService userService;

  private final OAuthConsumer consumer;

  public EventsResource() {
    accountService = ServiceLocator.locate(AccountService.class);
    userService = ServiceLocator.locate(UserService.class);
    consumer = ServiceLocator.locate(OAuthConsumer.class);
  }

  @Path("/events")
  @GET
  @Produces(MediaType.APPLICATION_XML + ";charset=" + "UTF-8")
  public Result getEvent(@QueryParam("url") String url, @Context HttpHeaders httpHeaders) throws IOException {

    Result result = new Result();
    if (url == null || url.trim().equals("")) {
      return new Result(false, "No url was provided");
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

          User user = new User();
          String uuid = event.getCreator().getUuid();
          user.setUuid(uuid);
          user.setOpenId(event.getCreator().getOpenId());
          user.setEmail(event.getCreator().getEmail());
          try {
            User userFromDb = userService.getUserByUuid(uuid);
            if (userFromDb != null) {
              // well nothing to do, we already have this user in the db
              LOG.info("AppDirect tried to add an already existing user to our db -  weird");
              result.setSuccess(false);
              result.setError(ErrorTypes.USER_ALREADY_EXISTS);
              result.setMessage("The user already exists in our db");
            } else {
              user.setFirstName(event.getCreator().getFirstName());
              user.setLastName(event.getCreator().getLastName());
              user.setAdmin(true);
              Account account = new Account();
              account.setUuid(event.getPayload().getCompany().getUuid());
              account.setName(event.getPayload().getCompany().getName());
              account.setEditionCode(event.getPayload().getOrder().getEditionCode());
//              account.setMaxUsers(event.getPayload().getOrder().getMaxUsers());
              account.setAppDirectManaged(true);
              account.setAppDirectBaseUrl(event.getMarketplace().getBaseUrl());
              account.setUsers(new ArrayList<User>() {{
                add(user);
              }});
              accountService.createAccount(account);
              result.setSuccess(true);
              result.setAccountIdentifier(account.getUuid());
            }
          } catch (DataAccessException e) {
            throw new RuntimeException(e);
          }


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
//      result.setMessage("Account creation successful");
//      result.setSuccess(true);
//      result.setAccountIdentifier("new-account-identifier");

    }
    return result;

  }
}
