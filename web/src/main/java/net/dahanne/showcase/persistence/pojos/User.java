package net.dahanne.showcase.persistence.pojos;

/**
 * Created by anthony on 2014-10-19.
 */
public class User {
  private long id;
  private String uuid;
  private String openId;
  private String email;
  private String firstName;
  private String lastName;
  private String zipCode;
  private String department;
  private String timezone;
  private boolean admin = false;
  private Account account;

  public User() {
  }

  public User(long id, String uuid, String openId, String email, String firstName, String lastName, String zipCode, String department, String timezone, boolean admin) {
    this.id = id;
    this.uuid = uuid;
    this.openId = openId;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.zipCode = zipCode;
    this.department = department;
    this.timezone = timezone;
    this.admin = admin;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (id != user.id) return false;
    if (!openId.equals(user.openId)) return false;
    if (!uuid.equals(user.uuid)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + uuid.hashCode();
    result = 31 * result + openId.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", uuid='" + uuid + '\'' +
        ", openId='" + openId + '\'' +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", department='" + department + '\'' +
        ", timezone='" + timezone + '\'' +
        ", admin=" + admin +
        '}';
  }
}
