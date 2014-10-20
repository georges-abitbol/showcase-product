package net.dahanne.showcase.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "marketplace")
public class Marketplace {

  private String partner;
  private String baseUrl;

  public String getPartner() {
    return partner;
  }

  public void setPartner(String partner) {
    this.partner = partner;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Override
  public String toString() {
    return "MarketplaceInfo{" +
        "partner='" + partner + '\'' +
        ", baseUrl='" + baseUrl + '\'' +
        '}';
  }
}
