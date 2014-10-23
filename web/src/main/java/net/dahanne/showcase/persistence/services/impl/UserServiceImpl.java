package net.dahanne.showcase.persistence.services.impl;

import net.dahanne.showcase.NotImplementedException;
import net.dahanne.showcase.persistence.pojos.Account;
import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.persistence.pojos.User;
import net.dahanne.showcase.persistence.services.UserService;

import javax.sql.DataSource;
import javax.ws.rs.ProcessingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by anthony on 2014-10-19.
 * <p/>
 * UserService implementation using SQL
 */
public class UserServiceImpl implements UserService {

  final DataSource dataSource;

  public UserServiceImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void createUser(User user) throws DataAccessException {
    try (Connection connection = dataSource.getConnection()) {

      String updateQuery = "insert into users(uuid,  openId,  email,  firstName,  lastName,  zipCode,  department,  timezone, admin) VALUES(?,?,?,?,?,?,?,?,?,?)";

      try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
        connection.setAutoCommit(false);
        preparedStatement.setString(1, user.getUuid());
        preparedStatement.setString(2, user.getOpenId());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setString(6, user.getZipCode());
        preparedStatement.setString(7, user.getDepartment());
        preparedStatement.setString(8, user.getTimezone());
        preparedStatement.setBoolean(9, user.isAdmin());
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

      String query = "SELECT id, uuid,  openId,  email,  firstName,  lastName,  zipCode,  department,  timezone, admin from users u";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
          long id = rs.getLong("id");
          String uuid = rs.getString("uuid");
          String openId = rs.getString("openId");
          String email = rs.getString("email");
          String firstName = rs.getString("firstName");
          String lastName = rs.getString("lastName");
          String zipCode = rs.getString("zipCode");
          String department = rs.getString("department");
          String timezone = rs.getString("timezone");
          boolean admin = rs.getBoolean("admin");

          User user = new User(id, uuid, openId, email, firstName, lastName, zipCode, department, timezone, admin);
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
  public User getUserByUuid(String uuid) throws DataAccessException {
    User user = null;
    try (Connection connection = dataSource.getConnection()) {

      String query = "SELECT * from users u, accounts a where uuid = ? and accounts.id = users.accountId ";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setString(1, uuid);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
          long userId = rs.getLong("users.id");
          String openId = rs.getString("openId");
          String email = rs.getString("email");
          String firstName = rs.getString("firstName");
          String lastName = rs.getString("lastName");
          String zipCode = rs.getString("zipCode");
          String department = rs.getString("department");
          String timezone = rs.getString("timezone");
          boolean admin = rs.getBoolean("admin");

          user = new User(userId, uuid, openId, email, firstName, lastName, zipCode, department, timezone, admin);
          String accountsUuid = rs.getString("accounts.uuid");
          long accountId = rs.getLong("accounts.id");
          String name = rs.getString("name");
          String editionCode = rs.getString("editionCode");
          String appDirectBaseUrl = rs.getString("appDirectBaseUrl");
          int maxUsers = rs.getInt("maxUsers");
          boolean appDirectManaged = rs.getBoolean("appDirectManaged");
          Account account = new Account(accountId, accountsUuid, name, null, editionCode, maxUsers, appDirectManaged, appDirectBaseUrl);
          user.setAccount(account);
        }
      } catch (SQLException e) {
        throw new DataAccessException(e);
      }

    } catch (SQLException e1) {
      throw new DataAccessException(e1);
    }
    return user;
  }
}
