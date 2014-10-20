package net.dahanne.showcase;

import javax.sql.DataSource;
import javax.ws.rs.ProcessingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by anthony on 2014-10-19.
 *
 * UserService implementation using SQL
 *
 */
public class UserServiceImpl implements UserService {

  final DataSource dataSource;

  public UserServiceImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void createUser(User user) throws DataAccessException {
    try (Connection connection = dataSource.getConnection()) {

      String updateQuery = "insert into users(id, name) VALUES(?,?)";

      try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
        connection.setAutoCommit(false);
        preparedStatement.setLong(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        throw new DataAccessException(e);
      }
      connection.commit();

    } catch (SQLException e1) {
      throw new ProcessingException(e1);
    }

  }

  @Override
  public User createOrUpdateUser(long id, User user) {
    throw new NotImplementedException();
  }

  @Override
  public Collection<User> getAllUsers() throws DataAccessException {
    List<User> users = new ArrayList();
    try (Connection connection = dataSource.getConnection()) {

      String query = "SELECT id, name from users u";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
          String name = rs.getString("name");
          long id = rs.getLong("id");

          User user = new User(id,name);
          users.add(user);
        }
      } catch (SQLException e) {
        throw new DataAccessException(e);
      }

    } catch (SQLException e1) {
      throw new DataAccessException(e1);
    }
    return users;
  }

  @Override
  public void deleteUser(long id) {

  }

  @Override
  public User getUser(long id) {
    return null;
  }
}
