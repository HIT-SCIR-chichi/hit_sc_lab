package memento;

public class State {

    private double trackRadius = -1;

    /**
     * @param trackRadius
     */
    public State(double trackRadius) {
        super();
        this.trackRadius = trackRadius;
    }

    /**
     * @return the trackRadius
     */
    public double getTrackRadius() {
        return trackRadius;
    }

    /**
     * override the toString method
     */
    @Override public String toString() {
        return "State [trackRadius=" + trackRadius + "]";
    }

}
