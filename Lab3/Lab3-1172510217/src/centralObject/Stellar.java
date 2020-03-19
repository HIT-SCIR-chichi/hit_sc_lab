package centralObject;

public class Stellar extends CentralObject {

    private double radius = -1;
    private double mess = -1;

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * @return the mess
     */
    public double getMess() {
        return mess;
    }

    /**
     * @param mess the mess to set
     */
    public void setMess(double mess) {
        this.mess = mess;
    }

    @Override public String toString() {
        return getName() + " [radius=" + radius + ", mess=" + mess + "]";
    }
}
