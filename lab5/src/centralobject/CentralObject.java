package centralobject;

public class CentralObject {

  private String name = null;

  /**
   * get the name of the centralObject.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * set the name of the centralObject.
   * 
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  @Override public String toString() {
    return name;

  }

}
