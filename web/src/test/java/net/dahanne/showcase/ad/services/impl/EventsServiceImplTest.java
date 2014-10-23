package net.dahanne.showcase.ad.services.impl;

import net.dahanne.showcase.ad.enums.ErrorTypes;
import net.dahanne.showcase.ad.pojos.AccountInfo;
import net.dahanne.showcase.ad.pojos.Event;
import net.dahanne.showcase.ad.pojos.Payload;
import net.dahanne.showcase.ad.pojos.Result;
import net.dahanne.showcase.ad.services.EventsService;
import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.persistence.services.AccountService;
import net.dahanne.showcase.persistence.services.UserService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * TODO : add tests for all methods
 */
public class EventsServiceImplTest {

  @Test
  public void processSubscriptionCancel__ok() throws Exception {
    String accountUuid = "account-uuid";

    AccountService accountService =  mock(AccountService.class);
    doNothing().when(accountService).deleteAccountAndAssociatedUsers(accountUuid);

    UserService userService =  null;
    EventsService eventsService =  new EventsServiceImpl(accountService, userService);

    Event event=  mock(Event.class);
    Payload payload = mock(Payload.class);
    AccountInfo account = mock(AccountInfo.class);
    when(account.getAccountIdentifier()).thenReturn(accountUuid);
    when(payload.getAccount()).thenReturn(account);
    when(event.getPayload()).thenReturn(payload);
    event.getPayload().getAccount().getAccountIdentifier();

    Result result = eventsService.processSubscriptionCancel(event);

    Result expectedResult = new Result();
    expectedResult.setSuccess(true);
    expectedResult.setMessage("The account with identifier " + accountUuid + " was successfully deleted");
    assertThat(result, is(expectedResult));

  }

  @Test
  public void processSubscriptionCancel__accountNotFound() throws Exception {
    String accountUuid = "account-uuid";

    AccountService accountService =  mock(AccountService.class);
    doThrow(new DataAccessException("problem")).when(accountService).deleteAccountAndAssociatedUsers(accountUuid);

    UserService userService =  null;
    EventsService eventsService =  new EventsServiceImpl(accountService, userService);

    Event event=  mock(Event.class);
    Payload payload = mock(Payload.class);
    AccountInfo account = mock(AccountInfo.class);
    when(account.getAccountIdentifier()).thenReturn(accountUuid);
    when(payload.getAccount()).thenReturn(account);
    when(event.getPayload()).thenReturn(payload);
    event.getPayload().getAccount().getAccountIdentifier();

    Result result = eventsService.processSubscriptionCancel(event);

    Result expectedResult = new Result();
    expectedResult.setSuccess(false);
    expectedResult.setError(ErrorTypes.ACCOUNT_NOT_FOUND);
    expectedResult.setMessage("The account with identifier " + accountUuid + " could not be deleted");
    assertThat(result, is(expectedResult));

  }

}