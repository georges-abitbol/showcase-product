package net.dahanne.showcase.ad.pojos;

import net.dahanne.showcase.ad.enums.Type;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "event")
public class Event {

  private Type type;
  private Marketplace marketplace;
  private String applicationUuid;
  private UserInfo creator;
  private Payload payload;
  private String returnUrl;

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Marketplace getMarketplace() {
    return marketplace;
  }

  public void setMarketplace(Marketplace marketplace) {
    this.marketplace = marketplace;
  }

  public String getApplicationUuid() {
    return applicationUuid;
  }

  public void setApplicationUuid(String applicationUuid) {
    this.applicationUuid = applicationUuid;
  }


  @XmlElement(name = "creator")
  public UserInfo getCreator() {
    return creator;
  }

  public void setCreator(UserInfo creator) {
    this.creator = creator;
  }

  @XmlElement(name = "payload")
  public Payload getPayload() {
    return payload;
  }

  public void setPayload(Payload payload) {
    this.payload = payload;
  }

  @XmlElement(name = "returnUrl")
  public String getReturnUrl() {
    return returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  @Override
  public String toString() {
    return "Event{" +
        "type=" + type +
        ", marketplace=" + marketplace +
        ", applicationUuid='" + applicationUuid + '\'' +
        ", creator=" + creator +
        ", payload=" + payload +
        ", returnUrl='" + returnUrl + '\'' +
        '}';
  }
}
