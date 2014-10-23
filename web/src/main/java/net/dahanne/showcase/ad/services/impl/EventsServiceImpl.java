package net.dahanne.showcase.ad.services.impl;

import net.dahanne.showcase.ad.enums.ErrorTypes;
import net.dahanne.showcase.ad.pojos.Event;
import net.dahanne.showcase.ad.pojos.Result;
import net.dahanne.showcase.ad.services.EventsService;
import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.persistence.pojos.Account;
import net.dahanne.showcase.persistence.pojos.User;
import net.dahanne.showcase.persistence.services.AccountService;
import net.dahanne.showcase.persistence.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by anthony on 2014-10-23.
 */
public class EventsServiceImpl implements EventsService {
  private final AccountService accountService;
  private final UserService userService;

  private static final Logger LOG = LoggerFactory.getLogger(EventsServiceImpl.class);


  public EventsServiceImpl(AccountService accountService, UserService userService) {
    this.accountService = accountService;
    this.userService = userService;
  }

  @Override
  public Result processSubscriptionOrder(Event event) {
    Result result = new Result();
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
        account.setAppDirectManaged(true);
        account.setAppDirectBaseUrl(event.getMarketplace().getBaseUrl());
        account.setUsers(new ArrayList<User>() {{
          add(user);
        }});
        accountService.createAccountAndAssociatedUsers(account);
        result.setSuccess(true);
        result.setAccountIdentifier(account.getUuid());
        result.setMessage("The account with identifier " + uuid + " was successfully created");
      }
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  @Override
  public Result processSubscriptionCancel(Event event) {
    Result result = new Result();
    String uuid = event.getPayload().getAccount().getAccountIdentifier();
    try {
      accountService.deleteAccountAndAssociatedUsers(uuid);
      result.setSuccess(true);
      result.setMessage("The account with identifier " + uuid + " was successfully deleted");
    } catch (DataAccessException e) {
      result.setSuccess(false);
      result.setError(ErrorTypes.ACCOUNT_NOT_FOUND);
      result.setMessage("The account with identifier " + uuid + " could not be deleted");
    }
    return result;
  }

  @Override
  public Result processSubscriptionChangeEvent(Event event) {
    Result result = new Result();
    String uuid = event.getPayload().getAccount().getAccountIdentifier();
    try {
      Account accountToUpdate = new Account();
      accountToUpdate.setUuid(event.getPayload().getAccount().getAccountIdentifier());
      accountToUpdate.setEditionCode(event.getPayload().getOrder().getEditionCode());
      accountToUpdate.setAppDirectManaged(true);
      accountToUpdate.setAppDirectBaseUrl(event.getMarketplace().getBaseUrl());
      accountService.update(accountToUpdate);
      result.setSuccess(true);
      result.setMessage("The account with identifier " + uuid + " was successfully updated");
    } catch (DataAccessException e) {
      result.setSuccess(false);
      result.setError(ErrorTypes.ACCOUNT_NOT_FOUND);
      result.setMessage("The account with identifier " + uuid + " could not be updated");
    }
    return result;
  }


  @Override
  public Result processUserAssignmentEvent(Event event) {
    Result result = new Result();
    // info there : http://info.appdirect.com/developers/docs/event_references/user_attributes/
    User user = new User();
    String uuid = event.getPayload().getUser().getUuid();

    try {
      if (userService.getUserByUuid(uuid) != null) {
        result.setSuccess(false);
        result.setError(ErrorTypes.USER_ALREADY_EXISTS);
        result.setMessage("The user with the uuid " + uuid + " already exists.");
        return result;
      }
    } catch (DataAccessException e) {
      // good the user does does not exist
    }
    user.setUuid(uuid);
    user.setOpenId(event.getPayload().getUser().getOpenId());
    user.setEmail(event.getPayload().getUser().getEmail());
    user.setFirstName(event.getPayload().getUser().getFirstName());
    user.setLastName(event.getPayload().getUser().getLastName());
    Map<String, String> userAttributes = event.getPayload().getUser().getAttributes();
    Account account = new Account();
    account.setUuid(event.getPayload().getAccount().getAccountIdentifier());
    user.setAccount(account);
    if (userAttributes != null) {
      user.setAdmin(Boolean.parseBoolean(userAttributes.get("appAdmin")));
      user.setDepartment(userAttributes.get("companyDepartment"));
      user.setTimezone(userAttributes.get("timeZone"));
      user.setZipCode(userAttributes.get("zipCode"));
    }
    try {
      userService.createUser(user);
      result.setMessage("Successfully assigned and created user with uuid: " + user.getUuid());
      result.setSuccess(true);
    } catch (DataAccessException e) {
      result.setSuccess(false);
      result.setError(ErrorTypes.ACCOUNT_NOT_FOUND);
      result.setMessage("The account with identifier " + uuid + " could not be found");
    }
    return result;
  }

  @Override
  public Result processUserUnAssignmentEvent(Event event) {
    Result result = new Result();
    String uuid = event.getPayload().getUser().getUuid();
    try {
      userService.deleteUser(uuid);
      result.setSuccess(true);
      result.setMessage("Successfully deleted user: " + event.getPayload().getUser().getOpenId());
    } catch (DataAccessException e) {
      result.setSuccess(false);
      result.setError(ErrorTypes.USER_NOT_FOUND);
      result.setMessage("The user with identifier " + uuid + " could not be found");
    }
    return result;
  }


}
