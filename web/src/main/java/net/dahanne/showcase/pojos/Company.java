package net.dahanne.showcase.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "company")
public class Company {

  private String uuid;
  private String name;
  private String email;
  private String phoneNumber;
  private String website;
  private String country;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String emailAddress) {
    this.email = emailAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "Company{" +
        "uuid='" + uuid + '\'' +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", website='" + website + '\'' +
        ", country='" + country + '\'' +
        '}';
  }
}
