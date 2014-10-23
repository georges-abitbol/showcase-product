package net.dahanne.showcase.persistence.services.impl;

import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.persistence.pojos.Account;
import net.dahanne.showcase.persistence.pojos.User;
import net.dahanne.showcase.persistence.services.AccountService;

import javax.sql.DataSource;
import javax.ws.rs.ProcessingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by anthony on 2014-10-19.
 * AccountService implementation using SQL
 */
public class AccountServiceImpl implements AccountService {

  final DataSource dataSource;

  public AccountServiceImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void createAccountAndAssociatedUsers(Account account) throws DataAccessException {
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
        String userInsertQuery = "insert into users(uuid,  openId,  email,  firstName,  lastName,  zipCode,  department,  timezone, admin, accountUuid) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(userInsertQuery)) {
          preparedStatement.setString(1, user.getUuid());
          preparedStatement.setString(2, user.getOpenId());
          preparedStatement.setString(3, user.getEmail());
          preparedStatement.setString(4, user.getFirstName());
          preparedStatement.setString(5, user.getLastName());
          preparedStatement.setString(6, user.getZipCode());
          preparedStatement.setString(7, user.getDepartment());
          preparedStatement.setString(8, user.getTimezone());
          preparedStatement.setBoolean(9, user.isAdmin());
          preparedStatement.setString(10, account.getUuid());
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
  public void update(Account account) throws DataAccessException {
    try (Connection connection = dataSource.getConnection()) {

      connection.setAutoCommit(false);

      // we first insert the account
      String accountInsertQuery = "update accounts  set name = ?,  editionCode = ?, maxUsers = ?,  appDirectManaged = ?, appDirectBaseUrl = ? where uuid = ? ";
      try (PreparedStatement preparedStatement = connection.prepareStatement(accountInsertQuery)) {
        preparedStatement.setString(1, account.getName());
        preparedStatement.setString(2, account.getEditionCode());
        preparedStatement.setInt(3, account.getMaxUsers());
        preparedStatement.setBoolean(4, account.isAppDirectManaged());
        preparedStatement.setString(5, account.getAppDirectBaseUrl());
        preparedStatement.setString(6, account.getUuid());
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
  public Collection<Account> getAllAccounts() throws DataAccessException {
    List<Account> accounts = new ArrayList();
    try (Connection connection = dataSource.getConnection()) {

      String query = "SELECT accountUuid,name, editionCode, appDirectBaseUrl, maxUsers,appDirectManaged, u.uuid as userUuid, openId, email, firstName, lastName, zipCode, department,timezone, admin   " +
          "from accounts a, users u " +
          "where a.uuid = u.accountUuid";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        ResultSet rs = preparedStatement.executeQuery();
        String uuid = null;
        List<User> users = null;
        while (rs.next()) {
          String accountUuid = rs.getString("accountUuid");
          if (!accountUuid.equals(uuid)) {
            // create the account
            users = new ArrayList<>();
            String name = rs.getString("name");
            String editionCode = rs.getString("editionCode");
            String appDirectBaseUrl = rs.getString("appDirectBaseUrl");
            int maxUsers = rs.getInt("maxUsers");
            boolean appDirectManaged = rs.getBoolean("appDirectManaged");
            Account account = new Account(accountUuid, name, users, editionCode, maxUsers, appDirectManaged, appDirectBaseUrl);
            accounts.add(account);
            uuid = accountUuid;
          }
          String userUuid = rs.getString("userUuid");
          String openId = rs.getString("openId");
          String email = rs.getString("email");
          String firstName = rs.getString("firstName");
          String lastName = rs.getString("lastName");
          String zipCode = rs.getString("zipCode");
          String department = rs.getString("department");
          String timezone = rs.getString("timezone");
          boolean admin = rs.getBoolean("admin");
          User user = new User(userUuid, openId, email, firstName, lastName, zipCode, department, timezone, admin);
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
  public void deleteAccountAndAssociatedUsers(String uuid) throws DataAccessException {
    try (Connection connection = dataSource.getConnection()) {
      String query = "delete from accounts a where a.uuid = ? ";
      connection.setAutoCommit(false);
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setString(1, uuid);
        preparedStatement.executeUpdate();

      } catch (SQLException e) {
        throw new DataAccessException(e);
      }

      connection.commit();


    } catch (SQLException e1) {
      throw new DataAccessException(e1);
    }
  }

  @Override
  public Account getAccount(String uuid) throws DataAccessException {
    Account account = null;
    try (Connection connection = dataSource.getConnection()) {

      String query = "SELECT * from accounts u where uuid = ?";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setString(1, uuid);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
          String accountsUuid = rs.getString("uuid");
          String name = rs.getString("name");
          String editionCode = rs.getString("editionCode");
          String appDirectBaseUrl = rs.getString("appDirectBaseUrl");
          int maxUsers = rs.getInt("maxUsers");
          boolean appDirectManaged = rs.getBoolean("appDirectManaged");
          account = new Account(accountsUuid, name, null, editionCode, maxUsers, appDirectManaged, appDirectBaseUrl);
        }
      } catch (SQLException e) {
        throw new DataAccessException(e);
      }

    } catch (SQLException e1) {
      throw new DataAccessException(e1);
    }
    return account;
  }
}
