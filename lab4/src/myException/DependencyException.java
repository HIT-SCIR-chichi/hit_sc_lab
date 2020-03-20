package myException;

public class DependencyException extends Exception {

    private int exceptionNum = -1;// “Ï≥£∫≈£∫1x,2x,3x
    private String info = new String();

    public DependencyException() {
    }

    
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

    /**
     * @param exceptionNum
     */
    public DependencyException(String message,int exceptionNum) {
        super(message);
        this.exceptionNum = exceptionNum;
    }

    /**
     * @param exceptionNum
     * @param info
     */
    public DependencyException(String message,int exceptionNum, String info) {
        super(message);
        this.exceptionNum = exceptionNum;
        this.info = info;
    }

    public DependencyException(String message) {
        super(message);
    }

    @Override public String toString() {
        return "DependencyException" + getMessage();
    }

}
