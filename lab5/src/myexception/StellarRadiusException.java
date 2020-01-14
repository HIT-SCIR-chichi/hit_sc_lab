package myexception;

public class StellarRadiusException extends Exception {

  public StellarRadiusException() {
  }

  public StellarRadiusException(String message) {
    super(message);
  }

  @Override public String toString() {
    return "StellarRadiusException" + getMessage();
  }
}
