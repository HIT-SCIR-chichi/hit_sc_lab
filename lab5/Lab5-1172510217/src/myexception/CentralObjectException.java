package myexception;

public class CentralObjectException extends Exception {

  public CentralObjectException() {
  }

  public CentralObjectException(String message) {
    super(message);
  }

  @Override public String toString() {
    return "CentralObjectException:" + getMessage();
  }
}
