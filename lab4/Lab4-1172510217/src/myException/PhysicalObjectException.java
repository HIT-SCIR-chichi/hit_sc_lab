package myException;

public class PhysicalObjectException extends Exception {

    public PhysicalObjectException() {
    }

    public PhysicalObjectException(String message) {
        super(message);
    }

    @Override public String toString() {
        return "PhysicalObjectException:" + getMessage();
    }
}
