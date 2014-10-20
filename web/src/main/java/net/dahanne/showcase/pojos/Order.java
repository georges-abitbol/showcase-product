package net.dahanne.showcase.pojos;

import net.dahanne.showcase.enums.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "order")
public class Order implements Serializable {

  private String editionCode;
  private PricingDuration pricingDuration;

  @XmlElement(name = "editionCode")
  public String getEditionCode() {
    return editionCode;
  }

  public void setEditionCode(String editionCode) {
    this.editionCode = editionCode;
  }

  @XmlElement(name = "pricingDuration")
  public PricingDuration getPricingDuration() {
    return pricingDuration;
  }

  public void setPricingDuration(PricingDuration pricingDuration) {
    this.pricingDuration = pricingDuration;
  }

  @Override
  public String toString() {
    return "Order{" +
        "editionCode='" + editionCode + '\'' +
        ", pricingDuration=" + pricingDuration +
        '}';
  }
}
