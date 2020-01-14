package circularorbit;

import java.util.Iterator;
import track.Track;

public interface CircularOrbit<L, E> extends Iterable<E>, Cloneable {

  public boolean addTrack(Track<E> track);// 增加轨道

  public double getSystemEntropy();// 得到熵值

  public int getTracksNumber();// 得到轨道数目

  public int getTrackObjectsNumber(int i);// 得到特定轨道序号i上的物体数目

  public Track<E> getTrack(int i);// 得到特定轨道且其序号为i

  public boolean deleteTrack(Track<E> track);// 删除轨道

  public boolean addCentralPoint(L l);// 增加中心点

  public boolean addTrackObject(Track<E> track, E e);// 在已有的一条轨道上增加物体

  public L getCentralPoint();// 得到中心物体

  public Iterator<E> iterator();// 迭代器

}
