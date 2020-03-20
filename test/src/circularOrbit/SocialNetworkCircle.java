package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import physicalObject.Friend;
import track.Track;

public class SocialNetworkCircle extends ConcreteCircularOrbit<Friend, Friend> {

    private List<Friend> allFriends = new ArrayList<>();// 储存所有的朋友

    public void readFileAndCreateSystem(File file) throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bfReader = new BufferedReader(reader);
        String string = "";
        List<String> lineList = new ArrayList<>();
        int lineNum = 0;
        while ((string = bfReader.readLine()) != null) {
            lineNum++;
            String[] strings = string.split("[:][:][=]");
            Pattern pattern1 = Pattern.compile("([A-Z]|[a-z]|[0-9])+");
            Pattern pattern3 = Pattern.compile("[,][ ]*(([0][.][0-9]{0,2}[0-9])|([1]([.][0]{0,3})?))");// 先取出亲密度数据所在的区间
            Pattern pattern2 = Pattern.compile("([0][.][0-9]{0,2}[1-9])|([1]([.][0]{0,3})?)");// 为了取出亲密度数据同时保证不会取错
            Matcher matcher;
            if (strings[0].contains("CentralUser")) {
                matcher = pattern1.matcher(strings[1]);
                while (matcher.find()) {
                    lineList.add(matcher.group());
                }
                Friend centralUser = new Friend();
                centralUser.setAge(Integer.parseInt(lineList.get(1)));
                centralUser.setFriendName(lineList.get(0));
                centralUser.setSex(lineList.get(2));
                centralUser.setTrackRadius(0.0);
                addCentralUser(centralUser);// 将中心用户加入系统
                lineList.removeAll(lineList);

            } else if (strings[0].contains("SocialTie")) {
                matcher = pattern1.matcher(strings[1]);
                while (matcher.find() && lineList.size() < 2) {
                    lineList.add(matcher.group());
                }
                matcher = pattern3.matcher(strings[1]);
                if (matcher.find()) {
                    matcher = pattern2.matcher(matcher.group());
                    if (matcher.find()) {
                        lineList.add(matcher.group());
                    }
                }
                if (getFriendByName(lineList.get(0)) == null) {
                    Friend friend1 = new Friend();
                    friend1.setFriendName(lineList.get(0));
                    this.addFriend(friend1);
                }
                if (getFriendByName(lineList.get(1)) == null) {
                    Friend friend2 = new Friend();
                    friend2.setFriendName(lineList.get(1));
                    this.addFriend(friend2);
                }
                if (lineList.size() < 3 || Double.parseDouble(lineList.get(2)) == 0) {
                    System.out.println("此行格式非法：" + lineNum);
                    continue;
                }
                getFriendByName(lineList.get(0)).addSocialTie(getFriendByName(lineList.get(1)),
                        Double.parseDouble(lineList.get(2)));
                getFriendByName(lineList.get(1)).addSocialTie(getFriendByName(lineList.get(0)),
                        Double.parseDouble(lineList.get(2)));
                lineList.removeAll(lineList);
            } else if (strings[0].contains("Friend")) {
                matcher = pattern1.matcher(strings[1]);
                while (matcher.find()) {
                    lineList.add(matcher.group());
                }
                Friend friend = getFriendByName(lineList.get(0));
                if (friend == null) {
                    friend = new Friend();
                    friend.setFriendName(lineList.get(0));
                    this.addFriend(friend);
                }
                friend.setAge(Integer.parseInt(lineList.get(1)));
                friend.setSex(lineList.get(2));
                lineList.removeAll(lineList);
            } else {
                System.out.println("此行格式非法：" + lineNum);
            }
        }
        bfReader.close();
        Friend centralUser = getCentralPoint();
        for (Friend friend : allFriends) {
            friend.setTrackRadius(getDistance(centralUser, friend));
            this.addFriendOnTrack(friend);// 添加的同时新建轨道
        }
        sortTrack();
    }

    public boolean addFriend(Friend friend) {
        Boolean result = allFriends.contains(friend);
        allFriends.add(friend);
        return result;
    }

    public boolean addCentralUser(Friend centralUser) {
        if (centralUser == null) {
            return false;
        }
        allFriends.add(centralUser);
        this.addCentralPoint(centralUser);
        return true;

    }

    /**
     * get the distance of the centralUser and the friend
     * 
     * @param friend1 one friend
     * @param friend2 another friend
     * @return the distance
     */
    public int getDistance(Friend friend1, Friend friend2) {
        Map<Friend, Boolean> visited = new HashMap<>();// 用于判断是否被访问
        Map<Friend, Integer> distance = new HashMap<>();// 用于记录距离
        for (Friend temp : allFriends) {// 将所有person标记为未被访问
            visited.put(temp, false);
        }
        visited.put(friend1, true);
        Queue<Friend> queue = new LinkedBlockingQueue<>();// 先广要用队列来做
        queue.add(friend1);// person1入队
        distance.put(friend1, 0);
        if (friend1.equals(friend2)) {
            return 0;
        }
        while (!queue.isEmpty()) {// 循环直到队列为空
            Friend head = queue.poll();// 得到队首元素，并将其出队
            Friend tempt = head.getAllFriends().peek();// 得到与其有关的第一个人
            int i = 0;
            while (tempt != null) {// 循环直到无人与head有直接关系
                if (!visited.get(tempt)) {// 若tempt未被访问
                    if (tempt.equals(friend2)) {// 若找到friend
                        return distance.get(head) + 1;
                    } else {// 若未找到
                        visited.put(tempt, true);
                        distance.put(tempt, distance.get(head) + 1);
                        queue.add(tempt);// 将当前friend入队
                    }
                }
                if (++i < head.getAllFriends().size()) {// 继续寻找与其有关系的人
                    tempt = head.getAllFriends().get(i);
                } else {
                    break;
                }
            }
        }
        return -1;
    }

    public Friend getFriendByName(String name) {
        for (Friend friend : allFriends) {
            if (friend.getFriendName().equals(name)) {
                return friend;
            }
        }
        return null;
    }

    public boolean addFriendOnTrack(Friend friend) {
        if (friend.getTrackRadius() == -1 || friend.equals(getCentralPoint())) {
            return false;
        }
        Track<Friend> track = getTrackByRadius(friend.getTrackRadius());
        if (track == null) {
            track = new Track<Friend>(friend.getTrackRadius());
            track.add(friend);
            addTrack(track);
            return true;
        }
        return track.add(friend);
    }

    /**
     * get the friend's track number
     * 
     * @param friend the centralUser's friend
     * @return the number on which track the friend is
     */
    public int getFriendTrackNum(Friend friend) {
        int result = (int) friend.getTrackRadius();
        if (result == -1) {
            System.out.println("He/She is not the friend of the centralUser!");
        } else if (result == 0) {
            System.out.println("He/She is the centralUser!");
        }
        return result;
    }

    /**
     * get logical distance between friend1 and friend2
     * 
     * @param friend1 one friend
     * @param friend2 another friend
     * @return the logical distance
     */
    public int getLogicalDistance(Friend friend1, Friend friend2) {
        int result = getDistance(friend1, friend2);
        if (result == 0) {
            System.out.println("两者为同一人！");
        } else if (result == -1) {
            System.out.println("两者之间无任何关系！");
        }
        return result;
    }

    /**
     * calculate the "information diffusion" of a friend in the first orbit
     * 
     * @return the number of new friends who can you meet indirectly through this
     *         friend
     */
    public int Informationdiffusivity(Friend friend) {
        if (friend.getTrackRadius() != 1) {
            System.out.println("该朋友不是中心用户的朋友！");
            return -1;
        }
        int result = 0;
        for (Friend friend2 : friend.getAllFriends()) {
            if (!getCentralPoint().getAllFriends().contains(friend2)// 若中心用户朋友的朋友不是中心用户的朋友而且关系亲密度乘积大于0.25，则说明有可能认识
                    && getCentralPoint().getSocialTie(friend) * friend.getSocialTie(friend2) > 0.25) {
                result++;
            }
        }
        return result;
    }

    /**
     * add a relation between friend1 and friend2, and recreate the system
     * 
     * @param friend1  one friend
     * @param friend2  another friend
     * @param intimacy the intimacy between friend1 and friend2
     */
    public void addRelationAndRefactor(Friend friend1, Friend friend2, double intimacy) {
        if (friend1 == null || friend2 == null) {
            System.out.println("friend输入非法：为空引用");
            return;
        }
        if (friend1.getSocialTie(friend2) != 0) {
            System.out.println("friend1和friend2本就是朋友关系！");
            return;
        }
        Pattern pattern = Pattern.compile("([0][.][0-9]{0,2}[0-9])|([1]([.][0]{0,3})?)");
        Matcher matcher = pattern.matcher(String.valueOf(intimacy));
        if (!matcher.matches() || intimacy == 0) {
            System.out.println("亲密度参数非法！");
            return;
        }
        friend1.addSocialTie(friend2, intimacy);
        friend2.addSocialTie(friend1, intimacy);
        Friend centralUser = getCentralPoint();
        for (Friend friend : allFriends) {
            int distance = getDistance(centralUser, friend);
            if (distance != friend.getTrackRadius()) {// 删去原轨道上的物体
                Track<Friend> track = getTrackByRadius(friend.getTrackRadius());
                if (track != null) {
                    track.remove(friend);
                    if (track.getNumberOfObjects() == 0) {
                        this.deleteTrack(track);
                    }
                }
            }
            friend.setTrackRadius(distance);
            this.addFriendOnTrack(friend);
        }
    }

    /**
     * delete a relation between friend1 and friend2, and recreate the system
     * 
     * @param friend1 one friend
     * @param friend2 another friend
     */
    public boolean deleteRelationAndRefactor(Friend friend1, Friend friend2) {
        if (friend1 == null || friend2 == null) {
            return false;
        }
        if (friend1.getSocialTie(friend2) == 0) {
            return false;
        }
        friend1.deleteSocialTie(friend2);
        friend2.deleteSocialTie(friend1);
        Friend centralUser = getCentralPoint();
        for (Friend friend : allFriends) {
            int distance = getDistance(centralUser, friend);
            if (distance != friend.getTrackRadius()) {// 删去原轨道上的物体
                Track<Friend> track = getTrackByRadius(friend.getTrackRadius());
                track.remove(friend);
                if (track.getNumberOfObjects() == 0) {
                    this.deleteTrack(track);
                }
            }
            friend.setTrackRadius(distance);
            this.addFriendOnTrack(friend);
        }
        return true;
    }

    /**
     * get the number of the list of allFriends
     * 
     * @return the number of the list of allFriends
     */
    public int getFriendNum() {
        return allFriends.size();
    }

    public Friend getFriend(int num) {
        if (num <= 0 || num > getFriendNum()) {
            return null;
        }
        return allFriends.get(num - 1);
    }

    public boolean removeFriend(Friend friend) {
        if (!allFriends.contains(friend)) {
            return false;
        }
        for (Friend friend2 : allFriends) {
            deleteRelationAndRefactor(friend2, friend);
        }
        allFriends.remove(friend);
        return true;
    }

}
