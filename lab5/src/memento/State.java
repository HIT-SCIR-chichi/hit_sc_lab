package memento;

public class State {

  private double trackRadius = -1;

  /**
   * set the state field.
   * 
   * @param trackRadius ¹ìµÀ°ë¾¶
   */
  public State(double trackRadius) {
    super();
    this.trackRadius = trackRadius;
  }

  /**
   * get the state's field.
   * 
   * @return the trackRadius
   */
  public double getTrackRadius() {
    return trackRadius;
  }

  /**
   * override the toString method.
   */
  @Override public String toString() {
    return "State [trackRadius=" + trackRadius + "]";
  }

}
