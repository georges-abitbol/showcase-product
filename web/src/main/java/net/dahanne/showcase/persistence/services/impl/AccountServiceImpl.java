package net.dahanne.showcase.persistence.services.impl;

import net.dahanne.showcase.NotImplementedException;
import net.dahanne.showcase.persistence.pojos.Account;
import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.persistence.pojos.User;
import net.dahanne.showcase.persistence.services.AccountService;

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
 * AccountService implementation using SQL
 */
public class AccountServiceImpl implements AccountService {

  final DataSource dataSource;

  public AccountServiceImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void createAccount(Account account) throws DataAccessException {
    try (Connection connection = dataSource.getConnection()) {

      connection.setAutoCommit(false);

      // we first insert the account
      String accountInsertQuery = "insert into accounts( uuid,  name,  editionCode,  maxUsers,  appDirectManaged, appDirectBaseUrl ) VALUES(?,?,?,?,?,?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(accountInsertQuery)) {
        preparedStatement.setString(1, account.getUuid());
        preparedStatement.setString(2, account.getName());
        preparedStatement.setString(3, account.getEditionCode());
        preparedStatement.setInt(4, account.getMaxUsers());
        preparedStatement.setBoolean(5, account.isAppDirectManaged());
        preparedStatement.setString(6, account.getAppDirectBaseUrl());
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        throw new DataAccessException(e);
      }

      // and then the associated users
      for (User user : account.getUsers()) {
        String userInsertQuery = "insert into users(id, uuid,  openId,  email,  firstName,  lastName,  zipCode,  department,  timezone, admin) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(userInsertQuery)) {
          preparedStatement.setLong(1, user.getId());
          preparedStatement.setString(2, user.getUuid());
          preparedStatement.setString(3, user.getOpenId());
          preparedStatement.setString(4, user.getEmail());
          preparedStatement.setString(5, user.getFirstName());
          preparedStatement.setString(6, user.getLastName());
          preparedStatement.setString(7, user.getZipCode());
          preparedStatement.setString(8, user.getDepartment());
          preparedStatement.setString(9, user.getTimezone());
          preparedStatement.setBoolean(10, user.isAdmin());
          preparedStatement.executeUpdate();
        } catch (SQLException e) {
          throw new DataAccessException(e);
        }
      }
      connection.commit();

    } catch (SQLException e1) {
      throw new ProcessingException(e1);
    }

  }

  @Override
  public Account createOrUpdateAccount(long id, Account account) {
    throw new NotImplementedException();
  }

  @Override
  public Collection<Account> getAllAccounts() throws DataAccessException {
    List<Account> accounts = new ArrayList();
    try (Connection connection = dataSource.getConnection()) {

      String query = "SELECT * " +
          "from accounts, users " +
          "where accounts.id = users.accountId";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        ResultSet rs = preparedStatement.executeQuery();
        long id = 0;
        List<User> users = null;
        while (rs.next()) {
          long accountId = rs.getLong("accounts.id");
          if (accountId != id) {
            // create the account
            users = new ArrayList<>();
            String accountsUuid = rs.getString("accounts.uuid");
            String name = rs.getString("name");
            String editionCode = rs.getString("editionCode");
            String appDirectBaseUrl = rs.getString("appDirectBaseUrl");
            int maxUsers = rs.getInt("maxUsers");
            boolean appDirectManaged = rs.getBoolean("appDirectManaged");
            Account account = new Account(id, accountsUuid, name, users, editionCode, maxUsers, appDirectManaged, appDirectBaseUrl);
            accounts.add(account);
            id = accountId;
          }
          long userId = rs.getLong("users.id");
          String userUuid = rs.getString("users.uuid");
          String openId = rs.getString("openId");
          String email = rs.getString("email");
          String firstName = rs.getString("firstName");
          String lastName = rs.getString("lastName");
          String zipCode = rs.getString("zipCode");
          String department = rs.getString("department");
          String timezone = rs.getString("timezone");
          boolean admin = rs.getBoolean("admin");
          User user = new User(userId, userUuid, openId, email, firstName, lastName, zipCode, department, timezone, admin);
          users.add(user);

        }
      } catch (SQLException e) {
        throw new DataAccessException(e);
      }

    } catch (SQLException e1) {
      throw new DataAccessException(e1);
    }
    return accounts;
  }

  @Override
  public void deleteAccount(long id) {

  }

  @Override
  public Account getAccount(long id) throws DataAccessException {
    Account account = null;
//    try (Connection connection = dataSource.getConnection()) {
//
//      String query = "SELECT id, uuid,  openId,  email,  firstName,  lastName,  zipCode,  department,  timezone, admin from accounts u where id = ?";
//
//      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//        preparedStatement.setLong(1, id);
//        ResultSet rs = preparedStatement.executeQuery();
//
//        while (rs.next()) {
//          String uuid = rs.getString("uuid");
//          String openId = rs.getString("openId");
//          String email = rs.getString("email");
//          String firstName = rs.getString("firstName");
//          String lastName = rs.getString("lastName");
//          String zipCode = rs.getString("zipCode");
//          String department = rs.getString("department");
//          String timezone = rs.getString("timezone");
//          boolean admin = rs.getBoolean("admin");
//
//          account = new Account(id, uuid, openId, email, firstName, lastName, zipCode, department, timezone, admin);
//        }
//      } catch (SQLException e) {
//        throw new DataAccessException(e);
//      }
//
//    } catch (SQLException e1) {
//      throw new DataAccessException(e1);
//    }
    return account;
  }
}
