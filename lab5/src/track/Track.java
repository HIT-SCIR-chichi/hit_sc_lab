package track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Track<E> {

  private double radius;// the radius of the track or the order of the tracks
  private Map<Integer, E> physicalObjects = new HashMap<>();// 储存所有的序号对应的轨道物体
  private Map<E, Integer> allOrders = new HashMap<>();// 储存所有的轨道物体及对应的序号

  /*
   * initialize the track
   */
  public Track(double radius) {
    this.radius = radius;
  }

  /**
   * get the radius.
   * 
   * @return the radius
   */
  public double getRadius() {
    return radius;
  }

  /**
   * add an object in the track.
   * 
   * @param  e object to be added in the track
   * @return   true if the object e is not in the track before and its radius is
   *           the
   *           same as the track; else return false
   */
  public boolean add(E e) {
    if (!physicalObjects.containsValue(e)) {
      physicalObjects.put(physicalObjects.size() + 1, e);
      allOrders.put(e, physicalObjects.size());
      return true;
    }
    return false;
  }

  /**
   * delete an object in the track.
   * 
   * @param  e object to be deleted in the track
   * @return   true if the object e is in the track before and its radius is the
   *           same as the track; else return false
   */
  public boolean remove(E e) {
    if (physicalObjects.containsValue(e)) {
      physicalObjects.remove(allOrders.remove(e));
      sortTrackObjects();
      return true;
    }
    return false;
  }

  /**
   * check if the object exists in the track.
   * 
   * @param  e the object to be checked
   * @return   true if existed in the track;else false
   */
  public boolean contains(E e) {
    return physicalObjects.containsValue(e);
  }

  /**
   * get the number of the physical objects in the track.
   * 
   * @return the number of the physical objects in the track
   */
  public int getNumberOfObjects() {
    return physicalObjects.size();
  }

  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("radius=" + radius + " : ");
    for (E e : physicalObjects.values()) {
      stringBuilder.append(e + " ");
    }
    return stringBuilder.toString();
  }

  /**
   * 根据物体得到他保存的序号.
   * 
   * @param  e 物体
   * @return   返回序号
   */
  public int getPhysicalObjectIndex(E e) {
    if (allOrders.containsKey(e)) {
      return allOrders.get(e);
    }
    return -1;
  }

  public E getIndexPhysicalObject(int num) {
    return physicalObjects.get(num);
  }

  /**
   * sort the track objects.
   */
  private void sortTrackObjects() {
    List<E> physicalObjectsList = new ArrayList<>();
    physicalObjectsList.addAll(allOrders.keySet());
    Collections.sort(physicalObjectsList, new Comparator<E>() {

      public int compare(E o1, E o2) {
        return Integer.compare(allOrders.get(o1), allOrders.get(o2));
      }
    });
    physicalObjects.clear();
    allOrders.clear();
    for (int i = 0; i < physicalObjectsList.size(); i++) {
      physicalObjects.put(i + 1, physicalObjectsList.get(i));
      allOrders.put(physicalObjectsList.get(i), i + 1);
    }
  }

}
