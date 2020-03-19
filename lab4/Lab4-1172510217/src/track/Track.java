package track;

import java.util.ArrayList;
import java.util.List;

public class Track<E> {

    private double radius;// the radius of the track or the order of the tracks
    private List<E> physicalObjects = new ArrayList<>();// save all the physical objects on the same track

    /*
     * initialize the track
     */
    public Track(double radius) {
        this.radius = radius;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * add an object in the track
     * 
     * @param e object to be added in the track
     * @return true if the object e is not in the track before and its radius is the
     *         same as the track; else return false
     */
    public boolean add(E e) {
        if (!physicalObjects.contains(e)) {
            physicalObjects.add(e);
            return true;
        }
        return false;
    }

    /**
     * delete an object in the track
     * 
     * @param e object to be deleted in the track
     * @return true if the object e is in the track before and its radius is the
     *         same as the track; else return false
     */
    public boolean remove(E e) {
        if (physicalObjects.contains(e)) {
            physicalObjects.remove(e);
            return true;
        }
        return false;
    }

    /**
     * check if the object exists in the track
     * 
     * @param e the object to be checked
     * @return true if existed in the track;else false
     */
    public boolean contains(E e) {
        return physicalObjects.contains(e);
    }

    /**
     * get the number of the physical objects in the track
     * 
     * @return the number of the physical objects in the track
     */
    public int getNumberOfObjects() {
        return physicalObjects.size();
    }

    @Override public String toString() {
        String string = "";
        string += "radius=" + radius + " : ";
        for (E e : physicalObjects) {
            string += e + " ";
        }
        return string;
    }

    public List<E> getTrackObjects() {
        return this.physicalObjects;
    }

    public int getPhysicalObjectIndex(E e) {
        for (int i = 0; i < physicalObjects.size(); i++) {
            if (physicalObjects.get(i).equals(e)) {
                return i;
            }
        }
        return -1;
    }
}
