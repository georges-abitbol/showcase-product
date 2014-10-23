package net.dahanne.showcase.resources;

import net.dahanne.showcase.persistence.pojos.Account;
import net.dahanne.showcase.persistence.services.AccountService;
import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.ServiceLocator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * resource exposed at "/api/accounts" path
 */
@Path("/accounts")
public class AccountResource {

  private final AccountService accountService;

  public AccountResource() {
    accountService = ServiceLocator.locate(AccountService.class);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public Collection<Account> getAllAccounts() throws DataAccessException {
    return accountService.getAllAccounts();
  }

  @Path("{uuid}")
  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public Account getAccount(@PathParam("uuid") String uuid) throws DataAccessException {
    return accountService.getAccount(uuid);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public Account createAccount(Account account) throws DataAccessException {
    accountService.createAccountAndAssociatedUsers(account);
    return account;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public Account addAccount(Account account) throws DataAccessException {
    accountService.update(account);
    return account;
  }

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
  public void deleteAccount(@PathParam("id") String uuid) throws DataAccessException {
    accountService.deleteAccountAndAssociatedUsers(uuid);
  }
}
