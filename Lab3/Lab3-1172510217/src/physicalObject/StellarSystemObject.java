package physicalObject;

public class StellarSystemObject extends PhysicalObject {

    private String planetName = new String();
    private String planetState = new String();
    private String planetColor = new String();
    private double planetRadius = -1;
    private double revolutionSpeed = -1;
    private String revolutionDiretion = new String();
    private double angle = -1;

    public StellarSystemObject(String planetName, String planetState, String planetColor, double planetRadius,
            double revolutionSpeed, String revolutionDiretion, double angle) {
        this.planetName = planetName;
        this.planetState = planetState;
        this.planetColor = planetColor;
        this.planetRadius = planetRadius;
        this.revolutionSpeed = revolutionSpeed;
        this.revolutionDiretion = revolutionDiretion;
        this.angle = angle;
    }

    /**
     * @return the planetName
     */
    public String getPlanetName() {
        return planetName;
    }

    /**
     * @param planetName the planetName to set
     */
    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    /**
     * @return the planetState
     */
    public String getPlanetState() {
        return planetState;
    }

    /**
     * @param planetState the planetState to set
     */
    public void setPlanetState(String planetState) {
        this.planetState = planetState;
    }

    /**
     * @return the planetColor
     */
    public String getPlanetColor() {
        return planetColor;
    }

    /**
     * @param planetColor the planetColor to set
     */
    public void setPlanetColor(String planetColor) {
        this.planetColor = planetColor;
    }

    /**
     * @return the planetRadius
     */
    public double getPlanetRadius() {
        return planetRadius;
    }

    /**
     * @param planetRadius the planetRadius to set
     */
    public void setPlanetRadius(double planetRadius) {
        this.planetRadius = planetRadius;
    }

    /**
     * @return the revolutionSpeed
     */
    public double getRevolutionSpeed() {
        return revolutionSpeed;
    }

    /**
     * @param revolutionSpeed the revolutionSpeed to set
     */
    public void setRevolutionSpeed(double revolutionSpeed) {
        this.revolutionSpeed = revolutionSpeed;
    }

    /**
     * @return the revolutionDiretion
     */
    public String getRevolutionDiretion() {
        return revolutionDiretion;
    }

    /**
     * @param revolutionDiretion the revolutionDiretion to set
     */
    public void setRevolutionDiretion(String revolutionDiretion) {
        this.revolutionDiretion = revolutionDiretion;
    }

    /**
     * @return the angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override public String toString() {
        return "[name=" + planetName + "]";
    }
}
