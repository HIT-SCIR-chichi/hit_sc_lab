package systemfile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import circularorbit.AtomStructure;
import iostrategy.BufferedIoStrategy;

import java.io.File;
import java.io.IOException;
import myexception.FileChooseException;
import org.junit.jupiter.api.Test;

class AtomStructureFileTest {

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
