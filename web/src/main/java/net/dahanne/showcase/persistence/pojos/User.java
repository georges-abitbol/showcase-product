package net.dahanne.showcase.persistence.pojos;

/**
 * Created by anthony on 2014-10-19.
 */
public class User {
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

  public User(String uuid, String openId, String email, String firstName, String lastName, String zipCode, String department, String timezone, boolean admin) {
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

    if (admin != user.admin) return false;
    if (department != null ? !department.equals(user.department) : user.department != null) return false;
    if (email != null ? !email.equals(user.email) : user.email != null) return false;
    if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
    if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
    if (openId != null ? !openId.equals(user.openId) : user.openId != null) return false;
    if (timezone != null ? !timezone.equals(user.timezone) : user.timezone != null) return false;
    if (!uuid.equals(user.uuid)) return false;
    if (zipCode != null ? !zipCode.equals(user.zipCode) : user.zipCode != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = uuid.hashCode();
    result = 31 * result + (openId != null ? openId.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
    result = 31 * result + (department != null ? department.hashCode() : 0);
    result = 31 * result + (timezone != null ? timezone.hashCode() : 0);
    result = 31 * result + (admin ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
        "uuid='" + uuid + '\'' +
        ", openId='" + openId + '\'' +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", department='" + department + '\'' +
        ", timezone='" + timezone + '\'' +
        ", admin=" + admin +
        ", account=" + account +
        '}';
  }
}
