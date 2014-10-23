package net.dahanne.showcase.resources;

import net.dahanne.showcase.ServiceLocator;
import net.dahanne.showcase.persistence.services.UserService;
import net.dahanne.showcase.ad.pojos.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * resource exposed at "/api/users" path
 */
@Path("/dummy")
public class TestResource {

  private static final Logger LOG = LoggerFactory.getLogger(TestResource.class);
  private final UserService userService;

  public TestResource() {
    userService = ServiceLocator.locate(UserService.class);
  }


  @Path("/events")
  @GET
  @Produces(MediaType.APPLICATION_XML + ";charset=" + "UTF-8")
  public Result getEvent(@QueryParam("url") String url, @Context HttpHeaders httpHeaders) throws IOException {

    Result result = new Result();
    result.setMessage("Super !");
    result.setSuccess(true);
    result.setAccountIdentifier("Ouam !");
    return result;

  }
}
