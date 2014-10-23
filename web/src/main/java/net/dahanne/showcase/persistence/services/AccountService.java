package net.dahanne.showcase.persistence.services;

import net.dahanne.showcase.persistence.pojos.Account;
import net.dahanne.showcase.persistence.DataAccessException;

import java.util.Collection;

/**
 * Created by anthony on 2014-10-21.
 */
public interface AccountService {
  Collection<Account> getAllAccounts() throws DataAccessException;

  Account getAccount(long id) throws DataAccessException;

  void createAccount(Account account) throws DataAccessException;

  Account createOrUpdateAccount(long id, Account account);

  void deleteAccount(long id);
}
