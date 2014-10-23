package net.dahanne.showcase.ad.pojos;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

@XmlRootElement(name = "payload")
public class Payload {

  private final HashMap<String, String> configuration = new HashMap<String, String>();
  private UserInfo user;
  private AccountInfo account;
  private Company company;
  private Order order;

  public UserInfo getUser() {
    return user;
  }

  public void setUser(UserInfo user) {
    this.user = user;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public HashMap<String, String> getConfiguration() {
    return configuration;
  }

  public AccountInfo getAccount() {
    return account;
  }

  public void setAccount(AccountInfo account) {
    this.account = account;
  }

  @Override
  public String toString() {
    return "Payload{" +
        "configuration=" + configuration +
        ", user=" + user +
        ", account=" + account +
        ", company=" + company +
        ", order=" + order +
        '}';
  }
}
