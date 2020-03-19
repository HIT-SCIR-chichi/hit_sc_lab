package circularorbit;

import applications.App;
import iostrategy.IoStrategy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import myexception.CentralObjectException;
import myexception.DependencyException;
import myexception.FileChooseException;
import myexception.SystemLegalException;
import physicalobject.Friend;
import track.Track;

public class SocialNetworkCircle extends ConcreteCircularOrbit<Friend, Friend> {

  private List<Friend> allFriends = new ArrayList<>();// 储存所有的朋友
  private Map<String, Friend> nameToFriend = new HashMap<>();// 储存所有的姓名对应的朋友
  private static final Logger logger =
      Logger.getLogger(App.class.getSimpleName());
  private File readFile = null;
  private String writeFilePath = null;

  @Override public void initSystem() {
    super.initSystem();
    allFriends.clear();
    nameToFriend.clear();
  }

  /**
   * clone 方法实现.
   */
  @Override public Object clone() throws CloneNotSupportedException {
    SocialNetworkCircle socialNetworkCircle =
        (SocialNetworkCircle) super.clone();
    socialNetworkCircle.allFriends = new ArrayList<>(this.allFriends);
    socialNetworkCircle.nameToFriend = new HashMap<>(this.nameToFriend);
    socialNetworkCircle.readFile = null;
    socialNetworkCircle.writeFilePath = null;
    return allFriends;

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
    this.initSystem();
    ioStrategy.readFileAndCreateSystem(this, readFile);
  }

  /**
   * 检查表示不变量.
   * 
   * @throws FileChooseException 文件选取异常
   */
  public void checkRep() throws FileChooseException {
    try {
      // 判断中心用户是否存在
      Friend centralUser = getCentralPoint();
      if (centralUser == null) {
        throw new CentralObjectException("缺少中心用户");
      }
      // 判断轨道上的朋友是否和中心用户的距离是最短距离
      Map<Friend, Boolean> visited = new HashMap<>();
      for (Friend friend : allFriends) {
        visited.put(friend, false);
      }
      visited.put(centralUser, true);
      Queue<Friend> queue = new LinkedBlockingQueue<>();
      queue.add(centralUser);
      Map<Friend, Integer> distance = new HashMap<>();
      distance.put(centralUser, 0);
      while (!queue.isEmpty()) {
        Friend head = queue.poll();
        Friend friend = head.getFriend(1);
        int i = 0;
        int size = head.getAllFriends().size();
        while (friend != null) {
          if (!visited.get(friend)) {
            int dis = distance.get(head) + 1;
            visited.put(friend, true);
            distance.put(friend, dis);
            if (friend.getTrackRadius() != dis) {
              throw new SystemLegalException("构建轨道错误，朋友所处轨道与其最短距离不符：" + friend
                  + "应该为：" + dis + "实际为：" + friend.getTrackRadius());
            }
            queue.add(friend);
          } else if (++i < size) {
            friend = head.getAllFriends().get(i);
          } else {
            break;
          }
        }
      }
      // 判断是否存在朋友未定义就被用于构建亲密度关系，同时判断其余的朋友是否是轨道半径为-1
      for (Friend friend : allFriends) {
        if (!visited.get(friend) && friend.getTrackRadius() != -1) {
          throw new SystemLegalException("构建轨道错误，朋友所处轨道与其最短距离不符：" + friend
              + "应该为：" + (-1) + "实际为：" + friend.getTrackRadius());
        }
        if (friend.getSex().equals("")) {
          throw new DependencyException("未被定义的朋友即被添加了关系");
        }
      }
    } catch (CentralObjectException e) {
      throw new FileChooseException("中心用户错误，需要重新选择文件：" + e.getMessage());
    } catch (SystemLegalException e) {
      System.out.println("系统退出，系统出现问题" + e.getMessage());
    } catch (DependencyException e) {
      throw new FileChooseException("依赖关系错误，需要重新选择文件：" + e.getMessage());
    }
  }

  /**
   * 添加朋友.
   * 
   * @param  friend 待添加的朋友
   * @return        返回添加是否成功的真值
   */
  public boolean addFriend(Friend friend) {
    assert friend != null : logIn("参数错误：null");
    Boolean result = allFriends.contains(friend);
    allFriends.add(friend);
    nameToFriend.put(friend.getFriendName(), friend);
    return result;
  }

  /**
   * 添加中心用户.
   * 
   * @param  centralUser 中心用户
   * @return             返回添加是否成功的真值
   */
  public boolean addCentralUser(Friend centralUser) {
    if (centralUser == null) {
      return false;
    }
    allFriends.add(centralUser);
    nameToFriend.put(centralUser.getFriendName(), centralUser);
    this.addCentralPoint(centralUser);
    assert getCentralPoint() != null : logIn("中心用户为null");
    return true;

  }

  /**
   * get the distance of the centralUser and the friend.
   * 
   * @param  friend1 one friend
   * @param  friend2 another friend
   * @return         the distance
   */
  public int getDistance(Friend friend1, Friend friend2) {
    assert friend1 != null && friend2 != null : logIn("参数错误：null");
    assert (allFriends.contains(friend1))
        && (allFriends.contains(friend2)) : logIn("参数错误：参数不在系统中");
    Map<Friend, Boolean> visited = new HashMap<>();// 用于判断是否被访问
    for (Friend temp : allFriends) { // 将所有person标记为未被访问
      visited.put(temp, false);
    }
    visited.put(friend1, true);
    Queue<Friend> queue = new LinkedBlockingQueue<>();// 先广要用队列来做
    queue.add(friend1);// person1入队
    Map<Friend, Integer> distance = new HashMap<>();// 用于记录距离
    distance.put(friend1, 0);
    if (friend1.equals(friend2)) {
      return 0;
    }
    while (!queue.isEmpty()) { // 循环直到队列为空
      Friend head = queue.poll();// 得到队首元素，并将其出队
      Friend tempt = head.getFriend(1);// 得到与其有关的第一个人
      int i = 0;
      int size = head.getAllFriends().size();
      while (tempt != null) { // 循环直到无人与head有直接关系
        if (!visited.get(tempt)) { // 若tempt未被访问
          if (tempt.equals(friend2)) { // 若找到friend
            return distance.get(head) + 1;
          } else { // 若未找到
            visited.put(tempt, true);
            distance.put(tempt, distance.get(head) + 1);
            queue.add(tempt);// 将当前friend入队
          }
        }
        if (++i < size) { // 继续寻找与其有关系的人
          tempt = head.getAllFriends().get(i);
        } else {
          break;
        }
      }
    }
    return -1;
  }

  /**
   * get all the distance in the socialNetwork circle.
   */
  public void getAllDistance() {
    Friend centralUser = getCentralPoint();
    Map<Friend, Boolean> visited = new HashMap<>();// 用于判断是否被访问
    for (Friend friend : allFriends) { // 将所有person标记为未被访问
      visited.put(friend, false);
    }
    visited.put(centralUser, true);
    Queue<Friend> queue = new LinkedBlockingQueue<>();// 先广要用队列来做
    queue.add(centralUser);
    Map<Friend, Integer> distance = new HashMap<>();// 用于记录最短距离
    distance.put(centralUser, 0);
    while (!queue.isEmpty()) { // 循环直到队列为空
      Friend head = queue.poll();// 得到队首元素，并将其出队
      Friend friend = head.getFriend(1);// 得到与其有关的第一个人
      int i = 0;
      int size = head.getAllFriends().size();
      while (friend != null) { // 循环直到无人与head有直接关系
        if (!visited.get(friend)) { // 若tempt未被访问
          int dis = distance.get(head) + 1;
          visited.put(friend, true);
          distance.put(friend, dis);
          friend.setTrackRadius(dis);
          this.addFriendOnTrack(friend);
          queue.add(friend);// 将当前friend入队
        } else if (++i < size) { // 继续寻找与其有关系的人
          friend = head.getAllFriends().get(i);
        } else {
          break;
        }
      }
    }
  }

  /**
   * 通过姓名得到friend.
   * 
   * @param  name 姓名
   * @return      返回friend
   */
  public Friend getFriendByName(String name) {
    return nameToFriend.get(name);
  }

  /**
   * 将朋友添加到轨道上.
   * 
   * @param  friend 朋友
   * @return        返回是否添加成功的真值
   */
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
   * get the friend's track number.
   * 
   * @param  friend the centralUser's friend
   * @return        the number on which track the friend is
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
   * get logical distance between friend1 and friend2.
   * 
   * @param  friend1 one friend
   * @param  friend2 another friend
   * @return         the logical distance
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
   * calculate the "information diffusion" of a friend in the first orbit.
   * 
   * @return the number of new friends who can you meet indirectly through this
   *         friend
   */
  public int informationdiffusivity(Friend friend) {
    assert friend != null : logIn("参数错误：null");
    if (friend.getTrackRadius() != 1) {
      System.out.println("该朋友不是中心用户的朋友！");
      return -1;
    }
    int result = 0;
    for (Friend friend2 : friend.getAllFriends()) {
      // 若中心用户朋友的朋友不是中心用户的朋友而且关系亲密度乘积大于0.25，则说明有可能认识
      if (!getCentralPoint().getAllFriends().contains(friend2)
          && getCentralPoint().getSocialTie(friend)
              * friend.getSocialTie(friend2) > 0.25) {
        result++;
      }
    }
    return result;
  }

  /**
   * add a relation between friend1 and friend2, and recreate the system.
   * 
   * @param friend1  one friend
   * @param friend2  another friend
   * @param intimacy the intimacy between friend1 and friend2
   */
  public boolean addRelationAndRefactor(Friend friend1, Friend friend2,
      double intimacy) {
    if (friend1 == null || friend2 == null) {
      return false;
    }
    if (friend1.getSocialTie(friend2) != 0) {
      return false;
    }
    Pattern pattern =
        Pattern.compile("([0][.][0-9]{0,2}[0-9])|([1]([.][0]{0,3})?)");
    Matcher matcher = pattern.matcher(String.valueOf(intimacy));
    if (!matcher.matches() || intimacy == 0) {
      return false;
    }
    friend1.addSocialTie(friend2, intimacy);
    friend2.addSocialTie(friend1, intimacy);
    Friend centralUser = getCentralPoint();
    for (Friend friend : allFriends) {
      int distance = getDistance(centralUser, friend);
      if (distance != friend.getTrackRadius()) { // 删去原轨道上的物体
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
    assert friend1.getSocialTie(friend2) == intimacy
        && friend2.getSocialTie(friend1) == intimacy : logIn("增加关系失败");
    return true;
  }

  /**
   * delete a relation between friend1 and friend2, and recreate the system.
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
      if (distance != friend.getTrackRadius()) { // 删去原轨道上的物体
        Track<Friend> track = getTrackByRadius(friend.getTrackRadius());
        track.remove(friend);
        if (track.getNumberOfObjects() == 0) {
          this.deleteTrack(track);
        }
      }
      friend.setTrackRadius(distance);
      this.addFriendOnTrack(friend);
    }
    assert (friend1.getSocialTie(friend2) == 0)
        && (friend2.getSocialTie(friend1) == 0) : logIn("删除失败");
    return true;
  }

  /**
   * get the number of the list of allFriends.
   * 
   * @return the number of the list of allFriends
   */
  public int getFriendNum() {
    return allFriends.size();
  }

  /**
   * 根据序号找到朋友.
   * 
   * @param  num 序号
   * @return     返回某个朋友
   */
  public Friend getFriend(int num) {
    if (num <= 0 || num > getFriendNum()) {
      return null;
    }
    return allFriends.get(num - 1);
  }

  /**
   * remove friend from the list of allFriends.
   * 
   * @param  friend friend != null
   * @return        true if allFriends contain friend , else false
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
    nameToFriend.remove(friend.getFriendName());
    return true;
  }

  private static String logIn(String message) {
    logger.severe(message);
    return "已将assert错误信息加载在日志文件里";
  }

  public List<Friend> getAllFriends() {
    return allFriends;
  }

}
