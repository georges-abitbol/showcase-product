package net.dahanne.showcase.pojos;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

@XmlRootElement(name = "payload")
public class Payload {

  private final HashMap<String, String> configuration = new HashMap<String, String>();
  private UserInfo user;
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

  @Override
  public String toString() {
    return "EventPayload{" +
        "user=" + user +
        ", company=" + company +
        ", order=" + order +
        ", configuration=" + configuration +
        '}';
  }
}
