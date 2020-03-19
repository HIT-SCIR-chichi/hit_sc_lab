package myException;

public class SystemLegalException extends Exception {

    public SystemLegalException() {
    }

    public SystemLegalException(String message) {
        super(message);
    }

    @Override public String toString() {
        return "SyetemlegalException" + getMessage();
    }

}
