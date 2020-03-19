package circularorbit;

import applications.App;
import centralobject.CentralObject;
import iostrategy.IoStrategy;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import myexception.FileChooseException;
import physicalobject.PhysicalObject;
import track.Track;

public class AtomStructure
    extends ConcreteCircularOrbit<CentralObject, PhysicalObject> {

  private static final Logger logger =
      Logger.getLogger(App.class.getSimpleName());
  private File readFile = null;
  private String writeFilePath = null;

  /**
   * clone 方法实现.
   */
  @Override public Object clone() throws CloneNotSupportedException {
    AtomStructure atomStructure = (AtomStructure) super.clone();
    atomStructure.readFile = null;
    atomStructure.writeFilePath = null;
    return atomStructure;
  }

  /**
   * set the file to read.
   * 
   * @param file read file
   */
  public void setReadFile(File file) {
    this.readFile = file;
  }

  /**
   * set the write file path.
   * 
   * @param writeFilePath write file path.
   */
  public void setWriteFilePath(String writeFilePath) {
    this.writeFilePath = writeFilePath;
  }

  /**
   * write file strategy.
   * 
   * @param  ioStrategy  strategy.
   * @throws IOException IO异常
   */
  public void saveSystemInfoInFile(IoStrategy ioStrategy) throws IOException {
    ioStrategy.saveSystemInfoInFile(this, writeFilePath);
  }

  /**
   * read file.
   * 
   * @param  ioStrategy          读文件的策略
   * @throws IOException         IO异常
   * @throws FileChooseException 文件选取异常
   */
  public void readFileAndCreateSystem(IoStrategy ioStrategy)
      throws IOException, FileChooseException {
    ioStrategy.readFileAndCreateSystem(this, readFile);
  }

  /**
   * transit an object to a track.
   * 
   * @param  physicalObject an object
   * @param  track          the new track
   * @return                true if the new track is not the same as the older
   *                        one; else false
   */
  public boolean transit(PhysicalObject physicalObject,
      Track<PhysicalObject> track) {
    assert physicalObject != null && track != null : logIn("参数错误：null");
    assert getAlltracks().contains(track) : logIn("参数错误：系统中无track");
    if (physicalObject.getTrackRadius() == track.getRadius()) {
      assert track.contains(physicalObject)
          && physicalObject.getTrackRadius() == track.getRadius() : logIn(
              "跃迁失败");
      return false;
    }
    if (deletePhysicalObject(physicalObject)) {
      physicalObject.setTrackRadius(track.getRadius());
      track.add(physicalObject);
      assert track.contains(physicalObject)
          && physicalObject.getTrackRadius() == track.getRadius() : logIn(
              "跃迁失败");
      return true;
    } else {
      assert track.contains(physicalObject)
          && physicalObject.getTrackRadius() == track.getRadius() : logIn(
              "跃迁失败");
      return false;
    }
  }

  /**
   * add an object to the system.
   * 
   * @param  physicalObject an object
   * @return                true if the object is not in the system; else false
   */
  public boolean addPhysicalObject(PhysicalObject physicalObject) {
    assert physicalObject != null : logIn("参数错误：null");
    Track<PhysicalObject> track =
        getTrackByRadius(physicalObject.getTrackRadius());
    if (track == null) {
      track = new Track<>(physicalObject.getTrackRadius());
      track.add(physicalObject);
      this.addTrack(track);
      return true;
    } else {
      return track.add(physicalObject);
    }
  }

  /**
   * delete an object.
   * 
   * @param  physicalObject an object
   * @return                true if the object is in the system; else false
   */
  public boolean deletePhysicalObject(PhysicalObject physicalObject) {
    assert physicalObject != null : logIn("参数错误：null");
    Track<PhysicalObject> track =
        getTrackByRadius(physicalObject.getTrackRadius());
    if (track != null) {
      return track.remove(physicalObject);
    } else {
      return false;
    }
  }

  private static String logIn(String message) {
    logger.severe(message);
    return "已将assert错误信息加载在日志文件里";
  }

}
