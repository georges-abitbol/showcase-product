package net.dahanne.showcase.rest;

import net.dahanne.showcase.DataAccessException;
import net.dahanne.showcase.ServiceLocator;
import net.dahanne.showcase.User;
import net.dahanne.showcase.UserService;

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

  @Path("{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public User getUser(@PathParam("id") long id ) {
    return userService.getUser(id);
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
  public User addSubscription(@PathParam("id") long id , User user) {
    return userService.createOrUpdateUser(id, user);
  }

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public void deleteUser(@PathParam("id") long id) {
    userService.deleteUser(id);
  }
}
