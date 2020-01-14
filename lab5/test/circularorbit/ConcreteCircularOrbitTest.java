package circularorbit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import centralobject.CentralObject;
import circularorbit.ConcreteCircularOrbit;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import physicalobject.PhysicalObject;
import track.Track;

class ConcreteCircularOrbitTest {

  /**
   * testing strategy : 选取一个正常的测试用例.
   */
  @Test void testIterator() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    CentralObject centralObject = new CentralObject();
    circularOrbit.addCentralPoint(centralObject);
    Track<PhysicalObject> track1 = new Track<>(1);
    Track<PhysicalObject> track2 = new Track<>(2);
    PhysicalObject physicalObject1 = new PhysicalObject();
    circularOrbit.addTrack(track1);
    circularOrbit.addTrack(track2);
    track1.add(physicalObject1);
    PhysicalObject physicalObject2 = new PhysicalObject();
    track1.add(physicalObject2);
    PhysicalObject physicalObject3 = new PhysicalObject();
    track2.add(physicalObject3);
    circularOrbit.sortTrack();
    Iterator<PhysicalObject> itertor = circularOrbit.iterator();
    int i = 0;
    while (itertor.hasNext()) {
      assertEquals(circularOrbit.getEByNum(++i), itertor.next());
    }
  }

  /**
   * testing strategy : 轨道集合中不含该轨道+含有该轨道.
   */
  @Test void testAddTrack() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    Track<PhysicalObject> track = new Track<>(1);
    // 轨道集合中不含该集合
    assertTrue(circularOrbit.addTrack(track));
    // 轨道集合中含有该集合
    assertFalse(circularOrbit.addTrack(track));
  }

  /**
   * testing strategy : 轨道集合中不含该轨道+含有该轨道.
   */
  @Test void testDeleteTrack() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    Track<PhysicalObject> track = new Track<>(1);
    circularOrbit.addTrack(track);
    // 轨道集合中含有该集合
    assertTrue(circularOrbit.deleteTrack(track));
    // 轨道集合中不含该集合
    assertFalse(circularOrbit.deleteTrack(track));
  }

  /**
   * testing strategy : 系统中心物体为null + 不为null.
   */
  @Test void testAddCentralPoint() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    CentralObject centralObject = new CentralObject();
    // 系统中心物体为null
    assertTrue(circularOrbit.addCentralPoint(centralObject));
    // 系统中心物体不为null
    assertFalse(circularOrbit.addCentralPoint(centralObject));
  }

  /**
   * testing strategy : 不含有该轨道+含有该半径轨道.
   */
  @Test void testGetTrackByRadius() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    Track<PhysicalObject> track1 = new Track<>(1);
    circularOrbit.addTrack(track1);
    // 含有特定半径轨道
    assertEquals(track1, circularOrbit.getTrackByRadius(1));
    // 不含特定半径轨道
    assertEquals(null, circularOrbit.getTrackByRadius(2));
  }

  /**
   * testing strategy : 测试一个正常用例.
   */
  @Test void testToString() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    CentralObject centralObject = new CentralObject();
    circularOrbit.addCentralPoint(centralObject);
    Track<PhysicalObject> track1 = new Track<>(1);
    circularOrbit.addTrack(track1);
    String string = "";
    string += "The centralPoint and all the tracks are as follows\n";
    string += "centralpoint : " + circularOrbit.getCentralPoint() + "\n";
    string += track1 + "\n";
    assertEquals(string, circularOrbit.toString());
  }

  /**
   * testing strategy : 无轨道+只有一个轨道+多轨道，多物体.
   */
  @Test void testGetSystemEntropy() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    CentralObject centralObject = new CentralObject();
    circularOrbit.addCentralPoint(centralObject);
    // 测试无轨道时，熵值为0
    assertEquals(0, circularOrbit.getSystemEntropy());
    Track<PhysicalObject> track1 = new Track<>(1);
    circularOrbit.addTrack(track1);
    track1.add(new PhysicalObject());
    circularOrbit.sortTrack();
    // 测试只有一条轨道时，熵值为0
    assertEquals(0, circularOrbit.getSystemEntropy());
    Track<PhysicalObject> track2 = new Track<>(2);
    circularOrbit.addTrack(track2);
    track2.add(new PhysicalObject());
    // 测试一般情况
    circularOrbit.sortTrack();
    assertTrue(Math.abs(0.9183 - circularOrbit.getSystemEntropy()) < 0.01);
  }

  /**
   * testing strategy : num <= 0 ; num > 物体总数 ;正常.
   */
  @Test void testGetEByNum() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    CentralObject centralObject = new CentralObject();
    circularOrbit.addCentralPoint(centralObject);
    Track<PhysicalObject> track1 = new Track<>(1);
    Track<PhysicalObject> track2 = new Track<>(2);
    PhysicalObject physicalObject1 = new PhysicalObject();
    circularOrbit.addTrack(track1);
    circularOrbit.addTrack(track2);
    track1.add(physicalObject1);
    PhysicalObject physicalObject2 = new PhysicalObject();
    track1.add(physicalObject2);
    PhysicalObject physicalObject3 = new PhysicalObject();
    track2.add(physicalObject3);
    circularOrbit.sortTrack();
    // num <= 0
    assertEquals(null, circularOrbit.getEByNum(0));
    // 正常情况
    assertEquals(physicalObject1, circularOrbit.getEByNum(1));
    // 正常情况
    assertEquals(physicalObject3, circularOrbit.getEByNum(3));
    // num大于物体总数
    assertEquals(null, circularOrbit.getEByNum(4));
  }

  /**
   * testing strategy:参数轨道序号小于等于0+大于轨道数+正常.
   */
  @Test void testGetTrackObjectsNumber() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    Track<PhysicalObject> track = new Track<>(1);
    circularOrbit.addTrack(track);
    track.add(new PhysicalObject());
    track.add(new PhysicalObject());
    circularOrbit.sortTrack();
    // 参数轨道序号小于等于0
    assertEquals(-1, circularOrbit.getTrackObjectsNumber(0));
    assertEquals(-1, circularOrbit.getTrackObjectsNumber(2));
    // 参数轨道数大于轨道数
    assertEquals(2, circularOrbit.getTrackObjectsNumber(1));
  }

  /**
   * testing strategy:参数轨道序号小于等于0+大于轨道数+正常.
   */
  @Test void testGetTrack() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> circularOrbit =
        new ConcreteCircularOrbit<>();
    Track<PhysicalObject> track1 = new Track<>(1);
    Track<PhysicalObject> track2 = new Track<>(2);
    circularOrbit.addTrack(track1);
    circularOrbit.addTrack(track2);
    circularOrbit.sortTrack();
    assertEquals(track1, circularOrbit.getTrack(1));
    // 大于轨道数
    assertEquals(null, circularOrbit.getTrack(3));
    // 参数轨道序号小于等于0
    assertEquals(null, circularOrbit.getTrack(0));
  }

}
