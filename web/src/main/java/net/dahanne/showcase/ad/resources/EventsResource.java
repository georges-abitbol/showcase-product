package net.dahanne.showcase.ad.resources;

import net.dahanne.showcase.ServiceLocator;
import net.dahanne.showcase.ad.enums.ErrorTypes;
import net.dahanne.showcase.ad.pojos.Event;
import net.dahanne.showcase.ad.pojos.Result;
import net.dahanne.showcase.ad.services.EventsService;
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

/**
 * resource exposed at "/api/ad/events" path
 */
@Path("/ad")
public class EventsResource {

  private static final Logger LOG = LoggerFactory.getLogger(EventsResource.class);
  private final EventsService eventsService;

  private final OAuthConsumer consumer;

  public EventsResource() {
    eventsService = ServiceLocator.locate(EventsService.class);
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

    LOG.debug("Received an event with the following url : " + url);

    String signedUrl = null;
    try {
      signedUrl = consumer.sign(url);
      LOG.debug("Here is the signed url " + signedUrl);
    } catch (OAuthException e) {
      LOG.debug("There was an exception signing the url " + url);
      e.printStackTrace();
    }

    if (signedUrl != null) {
      Client client = ClientBuilder.newClient();
      WebTarget target = client.target(signedUrl);
      Event event = target.request(MediaType.APPLICATION_XML).get(Event.class);
      LOG.warn("The event details are : " + event);

      switch (event.getType()) {
        case SUBSCRIPTION_ORDER:
          LOG.info("Subscription order start");
          result = eventsService.processSubscriptionOrder(event);
          LOG.info("Subscription order end");
          break;
        case SUBSCRIPTION_CANCEL:
          LOG.info("Subscription cancellation start");
          result = eventsService.processSubscriptionCancel(event);
          LOG.info("Subscription cancellation end");
          break;
        case SUBSCRIPTION_CHANGE:
          LOG.info("Subscription update start");
          result = eventsService.processSubscriptionChangeEvent(event);
          LOG.info("Subscription update end");
          break;
        case USER_ASSIGNMENT:
          LOG.info("User assignment start");
          result = eventsService.processUserAssignmentEvent(event);
          LOG.info("User assignment end");
          break;
        case USER_UNASSIGNMENT:
          LOG.info("Subscription update start");
          result = eventsService.processUserUnAssignmentEvent(event);
          LOG.info("Subscription update end");
          break;
        default:
          result.setError(ErrorTypes.UNKNOWN_ERROR);
          result.setMessage("Event not yet supported");
      }
    }
    return result;
  }
}
