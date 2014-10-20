package net.dahanne.showcase;

import java.util.Collection;

/**
 * Created by anthony on 2014-10-19.
 *
 * Basic CRUD for Users
 *
 */
public interface UserService {
  void createUser(User user) throws DataAccessException;
  User createOrUpdateUser(long id, User user);
  Collection<User> getAllUsers() throws DataAccessException;
  void deleteUser(long id);
  User getUser(long id);
}
