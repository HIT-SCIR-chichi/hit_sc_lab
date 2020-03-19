package centralobject;

public class Stellar extends CentralObject {

  private double radius = -1;
  private double mess = -1;

  /**
   * get the radius of the Stellar.
   * 
   * @return the radius
   */
  public double getRadius() {
    return radius;
  }

  /**
   * set the radius of the Stellar.
   * 
   * @param radius the radius to set
   */
  public void setRadius(double radius) {
    this.radius = radius;
  }

  /**
   * get the mess of the Stellar.
   * 
   * @return the mess
   */
  public double getMess() {
    return mess;
  }

  /**
   * set the mess of the Stellar.
   * 
   * @param mess the mess to set
   */
  public void setMess(double mess) {
    this.mess = mess;
  }

  @Override public String toString() {
    return getName() + " [radius=" + radius + ", mess=" + mess + "]";
  }
}
