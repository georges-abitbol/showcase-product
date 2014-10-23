package net.dahanne.showcase.persistence.pojos;

import java.util.ArrayList;
import java.util.List;

public class Account {

  private String uuid;
  private String name;
  private List<User> users = new ArrayList<User>();
  private String editionCode;
  private int maxUsers = 0;
  private boolean appDirectManaged = false;
  private String appDirectBaseUrl;

  public Account() {
  }

  public Account(String uuid, String name, List<User> users, String editionCode, int maxUsers, boolean appDirectManaged, String appDirectBaseUrl) {
    this.uuid = uuid;
    this.name = name;
    this.users = users;
    this.editionCode = editionCode;
    this.maxUsers = maxUsers;
    this.appDirectManaged = appDirectManaged;
    this.appDirectBaseUrl = appDirectBaseUrl;
  }


  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public String getEditionCode() {
    return editionCode;
  }

  public void setEditionCode(String editionCode) {
    this.editionCode = editionCode;
  }

  public int getMaxUsers() {
    return maxUsers;
  }

  public void setMaxUsers(int maxUsers) {
    this.maxUsers = maxUsers;
  }

  public boolean isAppDirectManaged() {
    return appDirectManaged;
  }

  public void setAppDirectManaged(boolean appDirectManaged) {
    this.appDirectManaged = appDirectManaged;
  }

  public String getAppDirectBaseUrl() {
    return appDirectBaseUrl;
  }

  public void setAppDirectBaseUrl(String appDirectBaseUrl) {
    this.appDirectBaseUrl = appDirectBaseUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Account account = (Account) o;

    if (appDirectManaged != account.appDirectManaged) return false;
    if (maxUsers != account.maxUsers) return false;
    if (appDirectBaseUrl != null ? !appDirectBaseUrl.equals(account.appDirectBaseUrl) : account.appDirectBaseUrl != null)
      return false;
    if (editionCode != null ? !editionCode.equals(account.editionCode) : account.editionCode != null) return false;
    if (!name.equals(account.name)) return false;
    if (!uuid.equals(account.uuid)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = uuid.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + (editionCode != null ? editionCode.hashCode() : 0);
    result = 31 * result + maxUsers;
    result = 31 * result + (appDirectManaged ? 1 : 0);
    result = 31 * result + (appDirectBaseUrl != null ? appDirectBaseUrl.hashCode() : 0);
    return result;
  }


  @Override
  public String toString() {
    return "Account{" +
        "uuid='" + uuid + '\'' +
        ", name='" + name + '\'' +
        ", users=" + users +
        ", editionCode='" + editionCode + '\'' +
        ", maxUsers=" + maxUsers +
        ", appDirectManaged=" + appDirectManaged +
        ", appDirectBaseUrl='" + appDirectBaseUrl + '\'' +
        '}';
  }
}
