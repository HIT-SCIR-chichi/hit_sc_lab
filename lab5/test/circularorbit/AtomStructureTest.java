package circularorbit;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import circularorbit.AtomStructure;
import iostrategy.BufferedIoStrategy;

import java.io.File;
import java.io.IOException;
import myexception.FileChooseException;
import org.junit.jupiter.api.Test;
import physicalobject.PhysicalObject;

class AtomStructureTest {

  /**
   * testing strategy : 测试跃迁前和跃迁后轨道不变+正常情况.
   */
  @Test void testTransit() throws Exception {
    AtomStructure atomStructure = new AtomStructure();
    atomStructure.setReadFile(
        new File("src/Spring2019_HITCS_SC_Lab3-master/AtomicStructure.txt"));
    atomStructure.readFileAndCreateSystem(new BufferedIoStrategy());
    int num1 = atomStructure.getTrackObjectsNumber(1);
    int num2 = atomStructure.getTrackObjectsNumber(2);
    atomStructure.transit(atomStructure.getTrack(1).getIndexPhysicalObject(1),
        atomStructure.getTrack(2));
    assertTrue(atomStructure.getTrackObjectsNumber(1) == (num1 - 1)
        && atomStructure.getTrackObjectsNumber(2) == num2 + 1);
    assertFalse(atomStructure.transit(
        atomStructure.getTrack(1).getIndexPhysicalObject(1),
        atomStructure.getTrack(1)));
  }

  /**
   * testing strategy : 测试不含某物体.
   */
  @Test void testDeletePhysicalObject()
      throws IOException, FileChooseException {
    AtomStructure atomStructure = new AtomStructure();
    atomStructure.setReadFile(
        new File("src/Spring2019_HITCS_SC_Lab3-master/AtomicStructure.txt"));
    atomStructure.readFileAndCreateSystem(new BufferedIoStrategy());
    PhysicalObject physicalObject = new PhysicalObject();
    assertFalse(atomStructure.deletePhysicalObject(physicalObject));
  }

  /**
   * 测试元素名只有一个小写字母+多余无用信息行.
   */
  @Test void testAtomLabelCase() throws IOException {
    try {
      AtomStructure atomStructure = new AtomStructure();
      File file = new File("src/TestFile/AtomFile/LabelTest1.txt");
      atomStructure.setReadFile(file);
      atomStructure.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达!");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试元素名有三个字母+多余无用信息行.
   */
  @Test void testAtomLabelLength() throws IOException {
    try {
      AtomStructure atomStructure = new AtomStructure();
      File file = new File("src/TestFile/AtomFile/LabelTest2.txt");
      atomStructure.setReadFile(file);
      atomStructure.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达!");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试轨道数目和实际给出的轨道数不一致+多余无用信息行.
   */
  @Test void testAtomTrackNum() throws IOException {
    try {
      AtomStructure atomStructure = new AtomStructure();
      File file = new File("src/TestFile/AtomFile/TrackTest.txt");
      atomStructure.setReadFile(file);
      atomStructure.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达!");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试读取文件缺少信息行.
   */
  @Test void testLackOfLine() throws IOException {
    try {
      AtomStructure atomStructure = new AtomStructure();
      File file = new File("src/TestFile/AtomFile/LackOfLineTest.txt");
      atomStructure.setReadFile(file);
      atomStructure.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达!");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试文件轨道序号顺序排列.
   */
  @Test void testTrackOrder() throws IOException {
    try {
      AtomStructure atomStructure = new AtomStructure();
      File file = new File("src/TestFile/AtomFile/TrackNumOrderTest.txt");
      atomStructure.setReadFile(file);
      atomStructure.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达!");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }
}
