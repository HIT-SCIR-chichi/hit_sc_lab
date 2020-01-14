package physicalobject;

import applications.App;
import java.util.logging.Logger;

public class StellarSystemObject extends PhysicalObject {

  private static final Logger logger =
      Logger.getLogger(App.class.getSimpleName());

  private String planetName = "";
  private String planetState = "";
  private String planetColor = "";
  private double planetRadius = -1;
  private double revolutionSpeed = -1;
  private String revolutionDiretion = "";
  private double angle = -1;

  /**
   * constructor.
   * 
   * @param planetName         planetName
   * @param planetState        planetState
   * @param planetColor        planetColor
   * @param planetRadius       planetRadius
   * @param revolutionSpeed    revolutionSpeed
   * @param revolutionDiretion revolutionDiretion
   * @param angle              angle
   */
  public StellarSystemObject(String planetName, String planetState,
      String planetColor, double planetRadius, double revolutionSpeed,
      String revolutionDiretion, double angle) {
    this.planetName = planetName;
    this.planetState = planetState;
    this.planetColor = planetColor;
    this.planetRadius = planetRadius;
    this.revolutionSpeed = revolutionSpeed;
    this.revolutionDiretion = revolutionDiretion;
    this.angle = angle;
  }

  /**
   * precondition: planetName != null && planetName != "".
   * 
   * @return the planetName
   */
  public String getPlanetName() {
    assert planetName != null
        && !planetName.equals("") : logIn("行星名不可为null或空串");
    return planetName;
  }

  /**
   * get the planetState.
   * 
   * @return the planetState
   */
  public String getPlanetState() {
    return planetState;
  }

  /**
   * get the planetColor.
   * 
   * @return the planetColor
   */
  public String getPlanetColor() {
    return planetColor;
  }

  /**
   * get the planet radius.
   * 
   * @return the planetRadius
   */
  public double getPlanetRadius() {
    return planetRadius;
  }

  /**
   * get the revolution speed.
   * 
   * @return the revolutionSpeed
   */
  public double getRevolutionSpeed() {
    assert revolutionSpeed >= 0 : logIn("公转速度错误");
    return revolutionSpeed;
  }

  /**
   * get the revolution direction.
   * 
   * @return the revolutionDiretion
   */
  public String getRevolutionDiretion() {
    return revolutionDiretion;
  }

  /**
   * get the angel.
   * 
   * @return the angle
   */
  public double getAngle() {
    return angle;
  }

  @Override public String toString() {
    return "[name=" + planetName + "]";
  }

  private static String logIn(String message) {
    logger.severe(message);
    return "已将assert错误信息加载在日志文件里";
  }
}
