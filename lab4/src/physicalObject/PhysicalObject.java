package physicalObject;

public class PhysicalObject {

    private double trackRadius = -1;

    /**
     * @return the trackRadius
     */
    public double getTrackRadius() {
        return trackRadius;
    }

    /**
     * @param trackRadius the trackRadius to set
     */
    public void setTrackRadius(double trackRadius) {
        this.trackRadius = trackRadius;
    }

    @Override public String toString() {
        return "[e]";
    }
}
