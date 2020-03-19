package circularorbit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import circularorbit.SocialNetworkCircle;
import iostrategy.BufferedIoStrategy;

import java.io.File;
import java.io.IOException;
import myexception.FileChooseException;
import org.junit.jupiter.api.Test;
import physicalobject.Friend;

class SocialNetworkCircleTest {

  /**
   * testing strategy : 标签不合法.
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

  @Test void testGetDistance() throws Exception {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file =
        new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt");
    socialNetworkCircle.setReadFile(file);
    socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
    int distance =
        socialNetworkCircle.getDistance(socialNetworkCircle.getCentralPoint(),
            socialNetworkCircle.getFriendByName("LisaWong"));
    assertEquals(1, distance);
  }

  @Test void testAddRelationAndRefactor() throws Exception {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file =
        new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt");
    socialNetworkCircle.setReadFile(file);
    socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
    socialNetworkCircle.addRelationAndRefactor(
        socialNetworkCircle.getCentralPoint(),
        socialNetworkCircle.getFriendByName("JackMa"), 0.9);
    assertTrue(socialNetworkCircle.getTrack(1)
        .contains(socialNetworkCircle.getFriendByName("JackMa")));
    assertFalse(socialNetworkCircle.addRelationAndRefactor(null,
        socialNetworkCircle.getCentralPoint(), 0.8));
    assertFalse(socialNetworkCircle.addRelationAndRefactor(
        socialNetworkCircle.getCentralPoint(), null, 0.8));
    assertFalse(socialNetworkCircle.addRelationAndRefactor(
        socialNetworkCircle.getCentralPoint(),
        socialNetworkCircle.getFriendByName("TomWong"), 0.5));
    assertFalse(socialNetworkCircle.addRelationAndRefactor(
        socialNetworkCircle.getCentralPoint(),
        socialNetworkCircle.getFriendByName("PonyMa"), 0.52121));
    assertFalse(socialNetworkCircle.addRelationAndRefactor(
        socialNetworkCircle.getCentralPoint(),
        socialNetworkCircle.getFriendByName("PonyMa"), 0));
  }

  /**
   * testing strategy : 参数为null+删除后某用户不存在与中心用户的关系.
   */
  @Test void testDeleteAndRefactor() throws Exception {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file =
        new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt");
    socialNetworkCircle.setReadFile(file);
    socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
    socialNetworkCircle.deleteRelationAndRefactor(
        socialNetworkCircle.getCentralPoint(),
        socialNetworkCircle.getFriendByName("TomWong"));
    assertEquals(2, socialNetworkCircle.getTrackObjectsNumber(1));
    assertFalse(socialNetworkCircle.deleteRelationAndRefactor(null,
        socialNetworkCircle.getCentralPoint()));
    assertFalse(socialNetworkCircle.deleteRelationAndRefactor(
        socialNetworkCircle.getCentralPoint(), null));
    assertTrue(socialNetworkCircle.deleteRelationAndRefactor(
        socialNetworkCircle.getCentralPoint(),
        socialNetworkCircle.getFriendByName("LisaWong")));
    assertTrue(socialNetworkCircle.deleteRelationAndRefactor(
        socialNetworkCircle.getCentralPoint(),
        socialNetworkCircle.getFriendByName("DavidChen")));
  }

  /**
   * testing strategy : 不含某朋友+含有某朋友.
   */
  @Test void testRemoveFriend() {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    Friend centralUser = new Friend();
    centralUser.setFriendName("TOMMYWONG");
    assertFalse(socialNetworkCircle.removeFriend(centralUser));
    socialNetworkCircle.addCentralUser(centralUser);
    assertTrue(socialNetworkCircle.removeFriend(centralUser));
  }

  /**
   * testing strategy : num范围错误+num正常.
   */
  @Test void testGetFriend() {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    Friend friend = new Friend();
    Friend friend2 = new Friend();
    friend.setFriendName("xiaoming");
    friend2.setFriendName("xiaozhang");
    socialNetworkCircle.addFriend(friend);
    socialNetworkCircle.addFriend(friend2);
    assertEquals(friend, socialNetworkCircle.getFriend(1));
    assertEquals(null, socialNetworkCircle.getFriend(0));
    assertEquals(null, socialNetworkCircle.getFriend(3));
  }

  /**
   * testing strategy : friend不是第一层轨道物体+是第一层轨道物体.
   */
  @Test void testInfomationDiffusivity()
      throws IOException, FileChooseException {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file =
        new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt");
    socialNetworkCircle.setReadFile(file);
    socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
    assertEquals(-1, socialNetworkCircle
        .informationdiffusivity(socialNetworkCircle.getFriendByName("JackMa")));
    assertEquals(1, socialNetworkCircle.informationdiffusivity(
        socialNetworkCircle.getFriendByName("LisaWong")));
    assertEquals(0, socialNetworkCircle.informationdiffusivity(
        socialNetworkCircle.getFriendByName("TomWong")));
  }

  /**
   * testing strategy : 两者为同一个人+两者无关系+有关系.
   */
  @Test void testGetLogicalDistance() throws Exception {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file =
        new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt");
    socialNetworkCircle.setReadFile(file);
    socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
    int distance = socialNetworkCircle.getLogicalDistance(
        socialNetworkCircle.getCentralPoint(),
        socialNetworkCircle.getFriendByName("LisaWong"));
    assertEquals(1, distance);
    assertEquals(0,
        socialNetworkCircle.getLogicalDistance(
            socialNetworkCircle.getCentralPoint(),
            socialNetworkCircle.getCentralPoint()));
    assertEquals(-1,
        socialNetworkCircle.getLogicalDistance(
            socialNetworkCircle.getCentralPoint(),
            socialNetworkCircle.getFriendByName("PonyMa")));
  }

  /**
   * testing strategy : 为中心用户+无关系+有关系.
   */
  @Test void testGetFriendTrackNum() throws Exception {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File file =
        new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt");
    socialNetworkCircle.setReadFile(file);
    socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
    assertEquals(0, socialNetworkCircle
        .getFriendTrackNum(socialNetworkCircle.getCentralPoint()));
    assertEquals(-1, socialNetworkCircle
        .getFriendTrackNum(socialNetworkCircle.getFriendByName("PonyMa")));
    assertEquals(1, socialNetworkCircle
        .getFriendTrackNum(socialNetworkCircle.getFriendByName("LisaWong")));
  }

  /**
   * testing strategy : 添加空的中心用户.
   */
  @Test void testAddCentralUser() {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    assertEquals(false, socialNetworkCircle.addCentralUser(null));
  }

  /**
   * testing strategy :无中心用户.
   */
  @Test void testCheckRep() {
    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    try {
      socialNetworkCircle.checkRep();
    } catch (FileChooseException e) {
      assertTrue(true);
    }
  }
}
