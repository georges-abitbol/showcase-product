package net.dahanne.showcase.ad.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "account")
public class AccountInfo {
  private String accountIdentifier;
  private String status;

  public AccountInfo() {
  }

  public String getAccountIdentifier() {
    return accountIdentifier;
  }

  public void setAccountIdentifier(String accountIdentifier) {
    this.accountIdentifier = accountIdentifier;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "AccountInfo{" +
        "accountIdentifier='" + accountIdentifier + '\'' +
        ", status='" + status + '\'' +
        '}';
  }
}
