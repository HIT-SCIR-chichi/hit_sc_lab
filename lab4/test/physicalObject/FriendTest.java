package physicalObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class FriendTest {

    /**
     * testing strategy : 测试friend性别为F+为M
     */
    @Test void testToString() {
        Friend friend = new Friend();
        friend.setFriendName("@Author ZJR");
        friend.setAge(10);
        friend.setSex("M");
        assertEquals("[name=" + friend.getFriendName() + ", age=" + friend.getAge() + " sex=男" + "]",
                friend.toString());
        friend.setSex("F");
        assertEquals("[name=" + friend.getFriendName() + ", age=" + friend.getAge() + " sex=女" + "]",
                friend.toString());
    }

    /**
     * testing strategy : 正常测试，选取一个测试用例
     */
    @Test void testGetFriendName() {
        Friend friend = new Friend();
        friend.setFriendName("@Author ZJR");
        assertEquals("@Author ZJR", friend.getFriendName());
    }

    /**
     * testing strategy : 正常测试，选取一个测试用例
     */
    @Test void testSetFriendName() {
        Friend friend = new Friend();
        friend.setFriendName("@Author ZJR");
        assertEquals("@Author ZJR", friend.getFriendName());
    }

    /**
     * testing strategy : 正常测试，选取一个测试用例
     */
    @Test void testGetAge() {
        Friend friend = new Friend();
        friend.setFriendName("@Author ZJR");
        friend.setAge(10);
        friend.setSex("M");
        assertEquals(10, friend.getAge());
    }

    /**
     * testing strategy : 正常测试，选取一个测试用例
     */
    @Test void testSetAge() {
        Friend friend = new Friend();
        friend.setFriendName("@Author ZJR");
        friend.setAge(10);
        friend.setSex("M");
        assertEquals(10, friend.getAge());
    }

    /**
     * testing strategy : 正常测试，选取一个测试用例
     */
    @Test void testGetSex() {
        Friend friend = new Friend();
        friend.setFriendName("@Author ZJR");
        friend.setAge(10);
        friend.setSex("M");
        assertEquals("M", friend.getSex());
    }

    /**
     * testing strategy : 正常测试，选取一个测试用例
     */
    @Test void testSetSex() {
        Friend friend = new Friend();
        friend.setFriendName("@Author ZJR");
        friend.setAge(10);
        friend.setSex("M");
        assertEquals("M", friend.getSex());

    }

    /**
     * testing strategy :friends中已含有该朋友+不含有该朋友
     */
    @Test void testAddSocialTie() {
        Friend centralUser = new Friend();
        Friend friend1 = new Friend();
        boolean res = centralUser.addSocialTie(friend1, 0.9);
        // friends中已含有该朋友
        assertEquals(res, true);
        assertEquals(0.9, centralUser.getSocialTie(friend1));
        // friends中不含该朋友
        res = centralUser.addSocialTie(friend1, 0.99);
        assertEquals(res, false);
        assertEquals(0.99, centralUser.getSocialTie(friend1));
    }

    /**
     * testing strategy : friends不含某朋友+含有某朋友
     */
    @Test void testDeleteSocialTie() {
        Friend centralUser = new Friend();
        Friend friend1 = new Friend();
        Friend friend2 = new Friend();
        centralUser.addSocialTie(friend1, 0.9);
        centralUser.addSocialTie(friend2, 0.99);
        // friends含有测试的朋友
        boolean res = centralUser.deleteSocialTie(friend1);
        assertEquals(res, true);
        assertEquals(0, centralUser.getSocialTie(friend1));
        // friends不含测试的朋友
        res = centralUser.deleteSocialTie(friend1);
        assertEquals(res, false);
        assertEquals(0, centralUser.getSocialTie(friend1));
    }

    /**
     * testing strategy : friends中不含该朋友+含有该朋友
     */
    @Test void testGetSocialTie() {
        Friend centralUser = new Friend();
        Friend friend1 = new Friend();
        Friend friend2 = new Friend();
        centralUser.addSocialTie(friend1, 0.9);
        // friends中含该朋友
        assertEquals(0.9, centralUser.getSocialTie(friend1));
        // 不含有该朋友
        assertEquals(0, centralUser.getSocialTie(friend2));
    }

    /**
     * testing strategy : 正常测试，friends中有多个，且按顺序
     */
    @Test void testGetAllFriends() {
        Friend centralUser = new Friend();
        Friend friend1 = new Friend();
        Friend friend2 = new Friend();
        centralUser.addSocialTie(friend1, 0.9);
        centralUser.addSocialTie(friend2, 0.99);
        List<Friend> list = new ArrayList<>();
        list.add(friend1);
        list.add(friend2);
        assertEquals(list, centralUser.getAllFriends());
    }

}
