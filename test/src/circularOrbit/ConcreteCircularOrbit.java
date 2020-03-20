package circularOrbit;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import track.Track;

public class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {

    private List<Track<E>> allTracks = new ArrayList<>();// save all the tracks
    private L centralPoint = null;// central point of the system

    public Iterator<E> iterator() {
        return new PhysicalObjectItertor();
    }

    public void initSystem() {
        allTracks.removeAll(allTracks);
        this.centralPoint = null;
    }

    /**
     * add a track in the circularOrbit system, return true if the track has not
     * been in the list of allTracks; else return false
     */
    public boolean addTrack(Track<E> track) {
        if (allTracks.contains(track)) {
            return false;
        }
        allTracks.add(track);
        return true;
    }

    /**
     * delete a track from the list of the allTracks. Return true if the track is in
     * the list; else return false
     */
    public boolean deleteTrack(Track<E> track) {
        if (allTracks.remove(track)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * add a central point in the system. Return true if a central point is in the
     * system; else return false
     */
    public boolean addCentralPoint(L l) {
        if (centralPoint != null) {
            return false;
        }
        centralPoint = l;
        return true;
    }

    /**
     * add an object on a track
     * return true if the object e has been in the track; else return false
     */
    public boolean addTrackObject(Track<E> track, E e) {
        return track.add(e);
    }

    public Track<E> getTrackByRadius(double radius) {
        for (Track<E> track : allTracks) {
            if (track.getRadius() == radius) {
                return track;
            }
        }
        return null;
    }

    public L getCentralPoint() {
        return centralPoint;
    }

    @Override public String toString() {
        String string = "";
        string += "The centralPoint and all the tracks are as follows\n";
        string += "centralpoint : " + centralPoint + "\n";
        for (Track<E> track : allTracks) {
            string += track + "\n";
        }
        return string;
    }

    /**
     * 对轨道进行升序排列
     */
    public void sortTrack() {
        Collections.sort(allTracks, new Comparator<Track<E>>() {

            @Override public int compare(Track<E> track1, Track<E> track2) {
                return ((Double) track1.getRadius()).compareTo((Double) track2.getRadius());
            }
        });
    }

    public double getSystemEntropy() {
        if (allTracks.size() <= 0) {
            return 0;
        }
        double minRadius = allTracks.get(0).getRadius();
        double maxRadius = allTracks.get(allTracks.size() - 1).getRadius();
        if (minRadius == maxRadius) {
            return 0;
        }
        List<Double> list = new ArrayList<>();
        for (Track<E> track : allTracks) {
            list.add((track.getRadius() - minRadius) / (maxRadius - minRadius) + 1);
        }
        double sum = 0;// 计算样本和
        int sumOfObjects = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i) * allTracks.get(i).getNumberOfObjects();
            sumOfObjects += allTracks.get(i).getNumberOfObjects();
        } // 下面的for循环计算各轨道的比重
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) / sum);
        }
        double k = 1 / Math.log(sumOfObjects);// 常数k
        double result = 0;// 以下计算熵值
        for (int i = 0; i < list.size(); i++) {
            result += -k * list.get(i) * Math.log(list.get(i));
        }
        return result;
    }

    public int getTracksNumber() {
        return allTracks.size();
    }

    public E getEByNum(int num) {
        for (int i = 0; i < getTracksNumber(); i++) {
            num -= getTrackObjectsNumber(i + 1);
            if (num <= 0) {
                return getTrack(i + 1).getTrackObjects().get(num + getTrackObjectsNumber(i + 1) - 1);
            }
        }
        return null;

    }

    public int getTrackObjectsNumber(int trackNumber) {
        if (trackNumber > allTracks.size()) {
            return -1;
        }
        return allTracks.get(trackNumber - 1).getNumberOfObjects();
    }

    public Track<E> getTrack(int trackNumber) {
        if (trackNumber > allTracks.size()) {
            return null;
        }
        return allTracks.get(trackNumber - 1);
    }

    private class PhysicalObjectItertor implements Iterator<E> {

        private int countObject = 0;// 当前物体计数
        private int numObject = 0;// 物体总数目

        public PhysicalObjectItertor() {// 得到系统物体总数目
            for (int i = 0; i < getTracksNumber(); i++) {
                numObject += getTrackObjectsNumber(i + 1);
            }
        }

        public boolean hasNext() {// 判断是否还有下一个物体
            return countObject < numObject;
        }

        public E next() {
            if (hasNext()) {
                return getEByNum(++countObject);// 通过当前物体的序号得到该物体
            }
            throw new NoSuchElementException();
        }
    }

}
