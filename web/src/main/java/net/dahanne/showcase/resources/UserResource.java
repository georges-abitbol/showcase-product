package net.dahanne.showcase.resources;

import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.ServiceLocator;
import net.dahanne.showcase.persistence.pojos.User;
import net.dahanne.showcase.persistence.services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * resource exposed at "/api/users" path
 */
@Path("/users")
public class UserResource {

  private final UserService userService;

  public UserResource() {
    userService = ServiceLocator.locate(UserService.class);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public Collection<User> getAllUsers() throws DataAccessException {
    return userService.getAllUsers();
  }

  @Path("{uuid}")
  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public User getUser(@PathParam("uuid") String uuid) throws DataAccessException {
    return userService.getUserByUuid(uuid);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public User createUser(User user) throws DataAccessException {
    userService.createUser(user);
    return user;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public User addSubscription(@PathParam("id") long id, User user) {
    return userService.createOrUpdateUser(id, user);
  }

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public void deleteUser(@PathParam("id") long id) {
    userService.deleteUser(id);
  }
}
