package systemfile;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import circularorbit.SocialNetworkCircle;
import iostrategy.BufferedIoStrategy;

import java.io.File;
import java.io.IOException;
import myexception.FileChooseException;
import org.junit.jupiter.api.Test;

class SocialNetworkFileTest {

  /**
   * 测试标签不合法.
   */
  @Test void testLabel() throws IOException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file = new File("src/TestFile/SocialNetworkFile/LabelTest.txt");
    try {
      socialNetworkCircle.setReadFile(file);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试性别不合法.
   */
  @Test void testSex() throws IOException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file = new File("src/TestFile/SocialNetworkFile/SexTest.txt");
    try {
      socialNetworkCircle.setReadFile(file);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试亲密度不合法：为0.
   */
  @Test void testIntimacyZero() throws IOException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file = new File("src/TestFile/SocialNetworkFile/IntimacyTest1.txt");
    try {
      socialNetworkCircle.setReadFile(file);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试亲密度不合法：位数超限制.
   */
  @Test void testIntimacyDigit() throws IOException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file = new File("src/TestFile/SocialNetworkFile/IntimacyTest2.txt");
    try {
      socialNetworkCircle.setReadFile(file);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试年龄不合法：不为整数.
   */
  @Test void testAge() throws IOException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file = new File("src/TestFile/SocialNetworkFile/AgeTest.txt");
    try {
      socialNetworkCircle.setReadFile(file);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试中心用户缺失.
   */
  @Test void testNoCentralUser() throws IOException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file = new File("src/TestFile/SocialNetworkFile/CentralUserTest.txt");
    try {
      socialNetworkCircle.setReadFile(file);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试有用户同名.
   */
  @Test void testLabelSame() throws IOException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file = new File("src/TestFile/SocialNetworkFile/LabelSameTest.txt");
    try {
      socialNetworkCircle.setReadFile(file);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试依赖关系错误：有朋友未被定义就参与了亲密度构建.
   */
  @Test void testDependency() throws IOException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file = new File("src/TestFile/SocialNetworkFile/DependencyTest.txt");
    try {
      socialNetworkCircle.setReadFile(file);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }

  /**
   * 测试多余无用信息行.
   */
  @Test void testUselessLine() throws IOException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file = new File("src/TestFile/SocialNetworkFile/UselessLineTest.txt");
    try {
      socialNetworkCircle.setReadFile(file);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
      assertTrue(false, "此处不可达");
    } catch (Exception e) {
      assertEquals(e.getClass(), FileChooseException.class);
    }
  }
}
