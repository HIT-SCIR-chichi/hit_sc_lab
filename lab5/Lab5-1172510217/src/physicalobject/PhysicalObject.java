package physicalobject;

public class PhysicalObject {

  private double trackRadius = -1;

  /**
   * get track radius.
   * 
   * @return the trackRadius
   */
  public double getTrackRadius() {
    return trackRadius;
  }

  /**
   * set track radius.
   * 
   * @param trackRadius the trackRadius to set
   */
  public void setTrackRadius(double trackRadius) {
    this.trackRadius = trackRadius;
  }

  @Override public String toString() {
    return "[e]";
  }
}
