package net.dahanne.showcase.ad.pojos;

import net.dahanne.showcase.ad.enums.ErrorTypes;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
public class Result {
  private boolean success;
  private ErrorTypes error;
  private String message;
  private String accountIdentifier;

  public Result(boolean success, String message) {
    this(success, null, message, null);
  }

  public Result(boolean success, ErrorTypes error, String message, String accountIdentifier) {
    super();
    this.success = success;
    this.error = error;
    this.message = message;
    this.accountIdentifier = accountIdentifier;
  }

  public Result() {
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public ErrorTypes getError() {
    return error;
  }

  public void setError(ErrorTypes error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getAccountIdentifier() {
    return accountIdentifier;
  }

  public void setAccountIdentifier(String accountIdentifier) {
    this.accountIdentifier = accountIdentifier;
  }

  @Override
  public String toString() {
    return "Result{" +
        "success=" + success +
        ", error=" + error +
        ", message='" + message + '\'' +
        ", accountIdentifier='" + accountIdentifier + '\'' +
        '}';
  }
}
