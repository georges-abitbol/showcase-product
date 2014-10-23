package net.dahanne.showcase.persistence.services;

import net.dahanne.showcase.persistence.pojos.Account;
import net.dahanne.showcase.persistence.DataAccessException;

import java.util.Collection;

/**
 * Created by anthony on 2014-10-21.
 */
public interface AccountService {
  Collection<Account> getAllAccounts() throws DataAccessException;

  Account getAccount(String uuid) throws DataAccessException;

  void createAccountAndAssociatedUsers(Account account) throws DataAccessException;

  void update(Account account) throws DataAccessException;

  void deleteAccountAndAssociatedUsers(String uuid) throws DataAccessException;
}
