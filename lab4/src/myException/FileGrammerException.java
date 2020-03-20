package myException;

public class FileGrammerException extends Exception {

    private int exceptionNum = -1;// “Ï≥£∫≈£∫1x,2x,3x
    private String info = new String();

    /**
     * @return the exceptionNum
     */
    public int getExceptionNum() {
        return exceptionNum;
    }

    /**
     * @param exceptionNum the exceptionNum to set
     */
    public void setExceptionNum(int exceptionNum) {
        this.exceptionNum = exceptionNum;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    public FileGrammerException() {
    }

    public FileGrammerException(String message) {
        super(message);
    }
    
    public FileGrammerException(String message, int exceptionNum) {
        super(message);
        this.exceptionNum = exceptionNum;
    }
    
    public FileGrammerException(String message, int exceptionNum, String info) {
        super(message);
        this.info = info;
        this.exceptionNum = exceptionNum;
    }


    @Override public String toString() {
        return "FileGrammerException:" + getMessage();
    }
}
