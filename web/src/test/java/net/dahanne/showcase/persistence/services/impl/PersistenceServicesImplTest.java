package net.dahanne.showcase.persistence.services.impl;

import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.persistence.JdbcUtilities;
import net.dahanne.showcase.persistence.pojos.Account;
import net.dahanne.showcase.persistence.pojos.User;
import net.dahanne.showcase.persistence.services.AccountService;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * These are absolutely NOT unit tests : they are integration tests, but fast enough (in memory db)
 * to keep them in src/test/java
 */
public class PersistenceServicesImplTest {

  private JdbcDataSource source;
  private AccountService accountService;
  private UserServiceImpl userService;

  @Before
  public void setUp() throws Exception {
    source = new JdbcDataSource();
    source.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    source.setUser("sa");
    source.setPassword("sa");
    JdbcUtilities.createTables(source);
//    JdbcUtilities.addSampleData(source);
    accountService = new AccountServiceImpl(source);
    userService = new UserServiceImpl(source);
  }

  @Test
  public void test_createAccountAndUser_and_updateAccount_deleteAccountAndUsers() throws Exception {
    String userUuid = "1111-1111";
    User user1 = new User(userUuid, "anthonyOpenIdUrl", "anthony@dahanne.net", "anthony", "dahanne", "h2l", "IT", "EST", false);
    List<User> users = new ArrayList<>();
    users.add(user1);
    String accountUuid = "0000-0000";
    Account account = new Account(accountUuid, "Anthony", users, "FREE", 10, true, "http://app.com");
    accountService.createAccountAndAssociatedUsers(account);
    assertThat(userService.getAllUsers(), hasSize(1));


    // before making assertions, we add another user directly using UserService
    String userUuid2 = "4444-6666";
    User user2 = new User(userUuid2, "anthonyOpenIdUrl", "anthony@dahanne.net", "anthony", "dahanne", "h2l", "IT", "EST", false);
    user2.setAccount(account);
    userService.createUser(user2);

    assertThat(userService.getAllUsers(), hasSize(2));
    assertThat(accountService.getAllAccounts(), hasSize(1));

    User user1InDb = userService.getUserByUuid(userUuid);
    assertThat(user1InDb, is(user1));
    User user2InDb = userService.getUserByUuid(userUuid2);
    assertThat(user2InDb, is(user2));
    Account accountInDb = accountService.getAccount(accountUuid);
    assertThat(accountInDb, is(account));


    // let's update the account
    account.setEditionCode("paying edition");
    account.setMaxUsers(10);
    accountService.update(account);
    Account accountUpdated = accountService.getAccount(accountUuid);
    assertThat(accountUpdated, is(account));

    // let's delete one of the 2 users
    userService.deleteUser(userUuid);
    assertThat(userService.getAllUsers(), hasSize(1));

    // let's delete the account
    accountService.deleteAccountAndAssociatedUsers(accountUuid);

    // and check it's all gone
    assertThat(userService.getAllUsers(), empty());
    assertThat(accountService.getAllAccounts(), empty());


  }


  @Test(expected = DataAccessException.class)
  public void test_createUserWithUnknownAccount() throws Exception {
    String userUuid = "1111-1111";
    User user1 = new User(userUuid, "anthonyOpenIdUrl", "anthony@dahanne.net", "anthony", "dahanne", "h2l", "IT", "EST", false);
    List<User> users = new ArrayList<>();
    users.add(user1);
    String accountUuid = "does-not-exist";
    Account account = new Account();
    account.setUuid(accountUuid);
    userService.createUser(user1);
  }


  @After
  public void tearDown() throws Exception {
    JdbcUtilities.dropTables(source);
  }
}