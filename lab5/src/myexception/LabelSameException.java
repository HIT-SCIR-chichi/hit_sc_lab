package myexception;

public class LabelSameException extends Exception {

  public LabelSameException() {
  }

  public LabelSameException(String message) {
    super(message);
  }

  @Override public String toString() {
    return "LabelSameException:" + getMessage();
  }
}
