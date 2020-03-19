package physicalObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Friend extends PhysicalObject {

    private String friendName = new String();
    private int age = -1;
    private String sex = new String();
    private Map<Friend, Double> socialTieMap = new HashMap<>();
    private LinkedList<Friend> allFriends = new LinkedList<>();

    /**
     * @return the friendName
     */
    public String getFriendName() {
        return friendName;
    }

    /**
     * @param friendName the friendName to set
     */
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * add a socialTie between him and his friend
     * 
     * @param friend   his friend
     * @param intimacy socialTie
     * @return true if the friend is not in the map; else false
     */
    public boolean addSocialTie(Friend friend, double intimacy) {
        boolean result = socialTieMap.containsKey(friend);
        socialTieMap.put(friend, intimacy);
        allFriends.add(friend);
        return !result;
    }

    /**
     * delete a socialTie between him and his friend
     * 
     * @param friend his friend
     * @return true if the socialTie has been added; else false
     */
    public boolean deleteSocialTie(Friend friend) {
        Boolean result = socialTieMap.containsKey(friend);
        socialTieMap.remove(friend);
        allFriends.remove(friend);
        return result;
    }

    /**
     * get the socialTie between him and the friend
     * 
     * @param friend his friend
     * @return the socialTie
     */
    public double getSocialTie(Friend friend) {
        if (socialTieMap.containsKey(friend)) {
            return socialTieMap.get(friend);
        }
        return 0;
    }

    public LinkedList<Friend> getAllFriends() {
        return this.allFriends;
    }

    @Override public String toString() {
        if(getSex().equals("M")) {
            return "[name=" + friendName + ", age=" + getAge() + " sex=男" + "]";
        }else if(getSex().equals("F")) {
            return "[name=" + friendName + ", age=" + getAge() + " sex=女"+ "]";
        }else {
            return "性别参数输入错误！";
        }
    }
}
