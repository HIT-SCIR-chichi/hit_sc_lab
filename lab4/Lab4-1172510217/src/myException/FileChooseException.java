package myException;

public class FileChooseException extends Exception {

    public FileChooseException() {
    }

    public FileChooseException(String message) {
        super(message);
    }

    @Override public String toString() {
        return "FileChooseException:" + getMessage();
    }
}
