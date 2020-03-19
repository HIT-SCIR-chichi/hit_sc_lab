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
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applications.App;
import myException.CentralObjectException;
import myException.DependencyException;
import myException.FileChooseException;
import myException.FileGrammerException;
import myException.LabelSameException;
import myException.SystemLegalException;
import physicalObject.Friend;
import track.Track;

public class SocialNetworkCircle extends ConcreteCircularOrbit<Friend, Friend> {

    private List<Friend> allFriends = new ArrayList<>();// 储存所有的朋友
    private static final Logger logger = Logger.getLogger(App.class.getSimpleName());

    public void readFileAndCreateSystem(File file) throws IOException, FileChooseException {
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bfReader = new BufferedReader(reader);
            Pattern pattern1 = Pattern.compile("CentralUser ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
            Pattern pattern2 = Pattern.compile("Friend ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
            Pattern pattern3 = Pattern.compile("SocialTie ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
            String lineString = new String();
            int count = 0;
            Matcher matcher;
            int lineCount = 0;
            while ((lineString = bfReader.readLine()) != null) {
                lineCount++;// 行计数自加一
                if ((matcher = pattern1.matcher(lineString)).find()) {
                    count++;
                    if (!Pattern.matches("[A-Za-z0-9]+", matcher.group(1))) {
                        bfReader.close();
                        throw new FileGrammerException("中心用户名非法，行数：" + lineCount, 30);
                    }
                    if (!Pattern.matches("\\d+", matcher.group(2))) {
                        bfReader.close();
                        throw new FileGrammerException("年龄非法，行数：" + lineCount, 30);
                    }
                    if (!Pattern.matches("M|F", matcher.group(3))) {
                        bfReader.close();
                        throw new FileGrammerException("性别非特定的两字符，行数：" + lineCount, 30);
                    }
                    Friend centralUser = new Friend();
                    centralUser.setAge(Integer.parseInt(matcher.group(2)));
                    centralUser.setFriendName(matcher.group(1));
                    centralUser.setSex(matcher.group(3));
                    centralUser.setTrackRadius(0);
                    addCentralPoint(centralUser);
                    addFriend(centralUser);
                } else if ((matcher = pattern2.matcher(lineString)).find()) {
                    if (!Pattern.matches("[A-Za-z0-9]+", matcher.group(1))) {
                        bfReader.close();
                        throw new FileGrammerException("朋友姓名非法，行数：" + lineCount, 30);
                    }
                    if (!Pattern.matches("\\d+", matcher.group(2))) {
                        bfReader.close();
                        throw new FileGrammerException("年龄非法，行数：" + lineCount, 30);
                    }
                    if (!Pattern.matches("M|F", matcher.group(3))) {
                        bfReader.close();
                        throw new FileGrammerException("性别非特定的两字符，行数：" + lineCount, 30);
                    }
                    Friend friend = getFriendByName(matcher.group(1));
                    if (friend == null) {
                        friend = new Friend();
                        friend.setFriendName(matcher.group(1));
                        friend.setAge(Integer.parseInt(matcher.group(2)));
                        friend.setSex(matcher.group(3));
                        addFriend(friend);
                    } else {
                        if (!friend.getSex().equals("")) {
                            bfReader.close();
                            throw new LabelSameException("该朋友姓名已存在，行数：" + lineCount);
                        } else {
                            friend.setAge(Integer.parseInt(matcher.group(2)));
                            friend.setSex(matcher.group(3));
                        }
                    }
                } else if ((matcher = pattern3.matcher(lineString)).find()) {
                    String f1String = matcher.group(1);
                    String f2String = matcher.group(2);
                    if (!Pattern.matches("[A-Za-z0-9]+", f1String)) {
                        bfReader.close();
                        throw new FileGrammerException("朋友姓名非法，行数：" + lineCount, 30);
                    }
                    if (!Pattern.matches("[A-Za-z0-9]+", f2String)) {
                        bfReader.close();
                        throw new FileGrammerException("朋友姓名非法，行数：" + lineCount, 30);
                    }
                    if (!Pattern.matches("([0][.][0-9]{0,2}[1-9])|([1]([.][0]{0,3})?)", matcher.group(3))) {
                        bfReader.close();
                        throw new FileGrammerException("亲密度参数错误，行数：" + lineCount, 30);
                    }
                    if (f1String.equals(f2String)) {
                        bfReader.close();
                        throw new LabelSameException("待添加关系的两人为同一个人，行数：" + lineCount);
                    }
                    double intimacy = Double.parseDouble(matcher.group(3));
                    Friend friend1 = getFriendByName(f1String);
                    Friend friend2 = getFriendByName(f2String);
                    if (friend1 == null) {
                        friend1 = new Friend();
                        friend1.setFriendName(f1String);
                        addFriend(friend1);
                    }
                    if (friend2 == null) {
                        friend2 = new Friend();
                        friend2.setFriendName(f2String);
                        addFriend(friend2);
                    }
                    if (friend1.getSocialTie(friend2) != 0 && friend1.getSocialTie(friend2) != intimacy) {
                        bfReader.close();
                        throw new LabelSameException("待添加的两人已存在亲密度且与此处亲密度不同，行数：" + lineCount);
                    }
                    friend1.addSocialTie(friend2, intimacy);
                    friend2.addSocialTie(friend1, intimacy);
                } else {
                    if (!lineString.equals("")) {
                        bfReader.close();
                        throw new FileGrammerException("无用信息行，行数：" + lineCount, 31);
                    }
                }
            }
            if (count != 1) {
                bfReader.close();
                throw new CentralObjectException("中心用户缺失或多余，行数：" + lineCount);
            }
            Friend centralUser = getCentralPoint();
            for (Friend friend : allFriends) {
                friend.setTrackRadius(getDistance(centralUser, friend));
                this.addFriendOnTrack(friend);// 添加的同时新建轨道
            }
            bfReader.close();
            sortTrack();
            checkRep();
        } catch (FileGrammerException e) {
            throw new FileChooseException("文件语法解析错误，需要重新选择文件：" + e.getMessage());
        } catch (LabelSameException e) {
            throw new FileChooseException("存在表情相同元素，需要重新选择文件：" + e.getMessage());
        } catch (CentralObjectException e) {
            throw new FileChooseException("中心用户错误，需要重新选择文件：" + e.getMessage());
        }

    }

    public void checkRep() throws FileChooseException {
        try {
            // 判断中心用户是否存在
            if (getCentralPoint() == null) {
                throw new CentralObjectException("缺少中心用户");
            }
            Friend centralUser = getCentralPoint();
            // 判断彭友所处轨道是否正确，判断是否存在朋友未定义就被用于构建亲密度关系
            for (Friend friend : allFriends) {
                if (getDistance(centralUser, friend) != friend.getTrackRadius()) {
                    throw new SystemLegalException("轨道构建违法：存在朋友所在轨道和实际不符");
                }
                if (friend.getSex().equals("")) {
                    throw new DependencyException("未被定义的朋友即被添加了关系");
                }
            }
        } catch (CentralObjectException e) {
            throw new FileChooseException("中心用户错误，需要重新选择文件：" + e.getMessage());
        } catch (SystemLegalException e) {
            System.out.println("系统退出，系统出现问题");
            System.exit(0);
        } catch (DependencyException e) {
            throw new FileChooseException("依赖关系错误，需要重新选择文件：" + e.getMessage());
        }
    }

    public boolean addFriend(Friend friend) {
        assert friend != null : logIn("参数错误：null");
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
        assert getCentralPoint() != null : logIn("中心用户为null");
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
        assert friend1 != null && friend2 != null : logIn("参数错误：null");
        assert allFriends.contains(friend1) && allFriends.contains(friend2) : logIn("参数错误：参数不在系统中");
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
        assert friend != null : logIn("参数错误：null");
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
        assert friend != null : logIn("参数错误：null");
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
        assert friend1 != null && friend2 != null : logIn("参数错误：null");
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
        assert friend != null : logIn("参数错误：null");
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
    public boolean addRelationAndRefactor(Friend friend1, Friend friend2, double intimacy) {
        if (friend1 == null || friend2 == null) {
            return false;
        }
        if (friend1.getSocialTie(friend2) != 0) {
            return false;
        }
        Pattern pattern = Pattern.compile("([0][.][0-9]{0,2}[0-9])|([1]([.][0]{0,3})?)");
        Matcher matcher = pattern.matcher(String.valueOf(intimacy));
        if (!matcher.matches() || intimacy == 0) {
            return false;
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
        assert friend1.getSocialTie(friend2) == intimacy && friend2.getSocialTie(friend1) == intimacy : logIn("增加关系失败");
        return true;
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
        assert friend1.getSocialTie(friend2) == 0 && friend2.getSocialTie(friend1) == 0 : logIn("删除失败");
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

    /**
     * 
     * @param friend friend != null
     * @return true if allFriends contain friend , else false
     */
    public boolean removeFriend(Friend friend) {
        assert friend != null : logIn("参数错误：null");
        if (!allFriends.contains(friend)) {
            return false;
        }
        for (Friend friend2 : allFriends) {
            deleteRelationAndRefactor(friend2, friend);
        }
        allFriends.remove(friend);
        return true;
    }

    private static String logIn(String message) {
        logger.severe(message);
        return "已将assert错误信息加载在日志文件里";
    }

}
