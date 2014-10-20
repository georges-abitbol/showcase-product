package net.dahanne.showcase;

/**
 * Created by anthony on 2014-10-19.
 *
 * This exception should be thrown when the application could not access (or write)
 * the data from (to) the persistence layer
 *
 */
public class DataAccessException extends Exception {
  public DataAccessException(Exception e) {
    super(e);
  }
}
