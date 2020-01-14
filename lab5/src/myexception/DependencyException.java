package myexception;

public class DependencyException extends Exception {

  private int exceptionNum = -1;// 异常号：1x,2x,3x
  private String info = "";

  public DependencyException() {
  }

  /**
   * get the exceptionNum.
   * 
   * @return the exceptionNum
   */
  public int getExceptionNum() {
    return exceptionNum;
  }

  /**
   * set the exceptionNum.
   * 
   * @param exceptionNum the exceptionNum to set
   */
  public void setExceptionNum(int exceptionNum) {
    this.exceptionNum = exceptionNum;
  }

  /**
   * get the info.
   * 
   * @return the info
   */
  public String getInfo() {
    return info;
  }

  /**
   * set the info.
   * 
   * @param info the info to set
   */
  public void setInfo(String info) {
    this.info = info;
  }

  /**
   * construct.
   * 
   * @param exceptionNum 异常号
   */
  public DependencyException(String message, int exceptionNum) {
    super(message);
    this.exceptionNum = exceptionNum;
  }

  /**
   * construct.
   * 
   * @param exceptionNum 异常号
   * @param info         要添加的信息
   */
  public DependencyException(String message, int exceptionNum, String info) {
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
