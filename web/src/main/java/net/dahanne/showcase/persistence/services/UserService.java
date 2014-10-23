package net.dahanne.showcase.persistence.services;

import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.persistence.pojos.User;

import java.util.Collection;

/**
 * Created by anthony on 2014-10-19.
 * <p/>
 * Basic CRUD for Users
 */
public interface UserService {
  void createUser(User user) throws DataAccessException;

  User createOrUpdateUser(long id, User user);

  Collection<User> getAllUsers() throws DataAccessException;

  void deleteUser(String uuid) throws DataAccessException;

  User getUserByUuid(String uuid) throws DataAccessException;
}
