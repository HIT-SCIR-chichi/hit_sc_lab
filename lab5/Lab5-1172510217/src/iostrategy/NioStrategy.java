package iostrategy;

import centralobject.Stellar;
import circularorbit.AtomStructure;
import circularorbit.SocialNetworkCircle;
import circularorbit.StellarSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import myexception.CentralObjectException;
import myexception.FileChooseException;
import myexception.FileGrammerException;
import myexception.LabelSameException;
import physicalobject.Friend;
import physicalobject.StellarSystemObject;
import track.Track;

public class NioStrategy implements IoStrategy {

  /**
   * stellar system.
   */
  public void readFileAndCreateSystem(StellarSystem stellarSystem, File file)
      throws IOException, FileChooseException {
    try {
      String lineString = "";
      Pattern pattern1 = Pattern.compile(
          "Stellar ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
      Pattern pattern2 =
          Pattern.compile("Planet ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*"
              + ",[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*"
              + ",[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
      Matcher matcher;
      int lineCount = 0;
      int count = 0;
      long startTime = System.currentTimeMillis();
      FileInputStream fileInputStream = new FileInputStream(file);
      FileChannel fileChannel = fileInputStream.getChannel();
      ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
      int bytesRead = fileChannel.read(buffer);
      ByteBuffer stringBuffer = ByteBuffer.allocate(80);
      while (bytesRead != -1) {
        buffer.flip();
        while (buffer.hasRemaining()) {
          byte b = buffer.get();
          if (b == 13 || b == 10) {
            stringBuffer.flip();
            lineString =
                Charset.forName("utf-8").decode(stringBuffer).toString();
            lineCount++;
            if ((matcher = pattern1.matcher(lineString)).find()) {
              count++;
              String group1 = matcher.group(1);
              String group2 = matcher.group(2);
              String group3 = matcher.group(3);
              if (!Pattern.matches("[0-9a-zA-Z]+", group1)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("恒星名字错误，行号：" + lineCount, 10);
              }
              if (!Pattern.matches(
                  "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
                  group2)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("恒星半径错误，行号：" + lineCount, 10);
              }
              if (!Pattern.matches(
                  "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
                  group3)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("恒星质量错误，行号：" + lineCount, 10);
              }
              Stellar stellar = new Stellar();
              stellar.setName(group1);
              stellar.setRadius(Double.parseDouble(group2));
              stellar.setMess(Double.parseDouble(group3));
              stellarSystem.addCentralPoint(stellar);
            } else if ((matcher = pattern2.matcher(lineString)).find()) {
              String group1 = matcher.group(1);
              String group2 = matcher.group(2);
              String group3 = matcher.group(3);
              if (!Pattern.matches("[0-9a-zA-Z]+", group1)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("行星名错误，行号：" + lineCount, 10);
              }
              if (!Pattern.matches("[0-9a-zA-Z]+", group2)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("行星状态错误，行号：" + lineCount, 10);
              }
              if (!Pattern.matches("[0-9a-zA-Z]+", group3)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("行星颜色错误，行号：" + lineCount, 10);
              }
              String group4 = matcher.group(4);
              String group5 = matcher.group(5);
              String group6 = matcher.group(6);
              if (!Pattern.matches(
                  "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
                  group4)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException(
                    "行星半径错误，行号：" + lineCount + group4, 10);
              }
              if (!Pattern.matches(
                  "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
                  group5)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("轨道半径错误，行号：" + lineCount, 10);
              }
              if (!Pattern.matches(
                  "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
                  group6)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("公转速度错误，行号：" + lineCount, 10);
              }
              String group7 = matcher.group(7);
              String group8 = matcher.group(8);
              if (!Pattern.matches("(CW)|(CCW)", group7)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("公转方向错误，行号：" + lineCount, 10);
              }
              if (!Pattern.matches("[0-9]{1,3}([.][0-9]+)?", group8)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("初始角度错误，行号：" + lineCount, 10);
              }
              double angle = Double.parseDouble(group8);
              if (angle < 0 || angle >= 360) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("初始角度范围错误，行号：" + lineCount, 10);
              }
              StellarSystemObject planet = new StellarSystemObject(group1,
                  group2, group3, Double.parseDouble(group4),
                  Double.parseDouble(group6), group7, angle);
              planet.setTrackRadius(Double.parseDouble(group5));
              Track<StellarSystemObject> track =
                  stellarSystem.getTrackByRadius(Double.parseDouble(group5));
              if (track == null) {
                track = new Track<>(Double.parseDouble(matcher.group(5)));
              }
              track.add(planet);
              stellarSystem.addTrack(track);
            } else {
              if (!lineString.equals("")) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("无用信息行，行数：" + lineCount, 10);
              }
            }
            stringBuffer.clear();
          } else {
            if (stringBuffer.hasRemaining()) {
              stringBuffer.put(b);
            } else {
              stringBuffer = reAllocate(stringBuffer);
              stringBuffer.put(b);
            }
          }
        }
        buffer.clear();
        bytesRead = fileChannel.read(buffer);
      }
      long endTime = System.currentTimeMillis();
      System.out.println("行星系统Nio读文件:" + (endTime - startTime) + "ms");
      if (count != 1) {
        fileChannel.close();
        fileInputStream.close();
        throw new CentralObjectException("恒星缺失或多余");
      }
      fileChannel.close();
      fileInputStream.close();
      stellarSystem.sortTrack();
      stellarSystem.checkRep();
    } catch (CentralObjectException e) {
      throw new FileChooseException("文件中心恒星错误，需要重新选择文件：" + e.getMessage());
    } catch (FileGrammerException e) {
      throw new FileChooseException("文件语法解析错误，需要重新选择文件：" + e.getMessage());
    }
  }

  /**
   * atom structure.
   */
  public void readFileAndCreateSystem(AtomStructure atomStructure, File file)
      throws IOException, FileChooseException {
    try {
      throw new NoSuchMethodException("原子系统：不支持的操作");
    } catch (NoSuchMethodException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * read socialNetwork file in the nio strategy.
   */
  public void readFileAndCreateSystem(SocialNetworkCircle socialNetworkCircle,
      File file) throws IOException, FileChooseException {
    try {
      Pattern pattern1 = Pattern.compile(
          "CentralUser ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
      Pattern pattern2 = Pattern
          .compile("Friend ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
      Pattern pattern3 = Pattern.compile(
          "SocialTie ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
      String lineString = "";
      int count = 0;
      Matcher matcher;
      int lineCount = 0;
      long startTime = System.currentTimeMillis();
      FileInputStream fileInputStream = new FileInputStream(file);
      FileChannel fileChannel = fileInputStream.getChannel();
      ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
      int bytesRead = fileChannel.read(buffer);
      ByteBuffer stringBuffer = ByteBuffer.allocate(80);
      while (bytesRead != -1) {
        buffer.flip();
        while (buffer.hasRemaining()) {
          byte b = buffer.get();
          if (b == 13 || b == 10) {
            stringBuffer.flip();
            lineString =
                Charset.forName("utf-8").decode(stringBuffer).toString();
            lineCount++;
            if ((matcher = pattern1.matcher(lineString)).find()) {
              count++;
              if (!Pattern.matches("[A-Za-z0-9]+", matcher.group(1))) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("中心用户名非法，行数：" + lineCount, 30);
              }
              if (!Pattern.matches("\\d+", matcher.group(2))) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("年龄非法，行数：" + lineCount, 30);
              }
              if (!Pattern.matches("M|F", matcher.group(3))) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("性别非特定的两字符，行数：" + lineCount, 30);
              }
              Friend centralUser = new Friend();
              centralUser.setAge(Integer.parseInt(matcher.group(2)));
              centralUser.setFriendName(matcher.group(1));
              centralUser.setSex(matcher.group(3));
              centralUser.setTrackRadius(0);
              socialNetworkCircle.addCentralPoint(centralUser);
              socialNetworkCircle.addFriend(centralUser);
            } else if ((matcher = pattern2.matcher(lineString)).find()) {
              if (!Pattern.matches("[A-Za-z0-9]+", matcher.group(1))) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("朋友姓名非法，行数：" + lineCount, 30);
              }
              if (!Pattern.matches("\\d+", matcher.group(2))) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("年龄非法，行数：" + lineCount, 30);
              }
              if (!Pattern.matches("M|F", matcher.group(3))) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("性别非特定的两字符，行数：" + lineCount, 30);
              }
              Friend friend =
                  socialNetworkCircle.getFriendByName(matcher.group(1));
              if (friend == null) {
                friend = new Friend();
                friend.setFriendName(matcher.group(1));
                friend.setAge(Integer.parseInt(matcher.group(2)));
                friend.setSex(matcher.group(3));
                socialNetworkCircle.addFriend(friend);
              } else {
                if (!friend.getSex().equals("")) {
                  fileChannel.close();
                  fileInputStream.close();
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
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("朋友姓名非法，行数：" + lineCount, 30);
              }
              if (!Pattern.matches("[A-Za-z0-9]+", f2String)) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("朋友姓名非法，行数：" + lineCount, 30);
              }
              if (!Pattern.matches("([0][.][0-9]{0,3})|([1]([.][0]{0,3})?)",
                  matcher.group(3))) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("亲密度参数错误，行数：" + lineCount, 30);
              }
              if (f1String.equals(f2String)) {
                fileChannel.close();
                fileInputStream.close();
                throw new LabelSameException("待添加关系的两人为同一个人，行数：" + lineCount);
              }
              double intimacy = Double.parseDouble(matcher.group(3));
              if (intimacy == 0) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("亲密度参数错误，行数：" + lineCount, 30);
              }
              Friend friend1 = socialNetworkCircle.getFriendByName(f1String);
              Friend friend2 = socialNetworkCircle.getFriendByName(f2String);
              if (friend1 == null) {
                friend1 = new Friend();
                friend1.setFriendName(f1String);
                socialNetworkCircle.addFriend(friend1);
              }
              if (friend2 == null) {
                friend2 = new Friend();
                friend2.setFriendName(f2String);
                socialNetworkCircle.addFriend(friend2);
              }
              if (friend1.getSocialTie(friend2) != 0
                  && friend1.getSocialTie(friend2) != intimacy) {
                fileChannel.close();
                fileInputStream.close();
                throw new LabelSameException(
                    "待添加的两人已存在亲密度且与此处亲密度不同，行数：" + lineCount);
              }
              friend1.addSocialTie(friend2, intimacy);
              friend2.addSocialTie(friend1, intimacy);
            } else {
              if (!lineString.equals("")) {
                fileChannel.close();
                fileInputStream.close();
                throw new FileGrammerException("无用信息行，行数：" + lineCount, 31);
              }
            }
            stringBuffer.clear();
          } else {
            if (stringBuffer.hasRemaining()) {
              stringBuffer.put(b);
            } else {
              stringBuffer = reAllocate(stringBuffer);
              stringBuffer.put(b);
            }
          }
        }
        buffer.clear();
        bytesRead = fileChannel.read(buffer);
      }
      long endTime = System.currentTimeMillis();
      System.out.println("社交网络Nio读文件:" + (endTime - startTime) + "ms");
      if (count != 1) {
        fileChannel.close();
        fileInputStream.close();
        throw new CentralObjectException("中心用户缺失或多余，行数：" + lineCount);
      }
      socialNetworkCircle.getAllDistance();
      fileChannel.close();
      fileInputStream.close();
      socialNetworkCircle.sortTrack();
      socialNetworkCircle.checkRep();
    } catch (FileGrammerException e) {
      throw new FileChooseException("文件语法解析错误，需要重新选择文件：" + e.getMessage());
    } catch (LabelSameException e) {
      throw new FileChooseException("存在标签相同元素，需要重新选择文件：" + e.getMessage());
    } catch (CentralObjectException e) {
      throw new FileChooseException("中心用户错误，需要重新选择文件：" + e.getMessage());
    }
  }

  /**
   * save system in the strategy of nio.
   */
  public void saveSystemInfoInFile(StellarSystem stellarSystem, String filePath)
      throws IOException {
    StringBuilder stringBuilder = new StringBuilder("Stellar ::= <");
    stringBuilder.append(stellarSystem.getCentralPoint().getName()).append(",")
        .append(stellarSystem
            .enotationTransform(stellarSystem.getCentralPoint().getRadius()))
        .append(",")
        .append(stellarSystem
            .enotationTransform(stellarSystem.getCentralPoint().getMess()))
        .append(">\n");// 恒星行
    // 下面书写行星行
    long startTime = 0;
    long endTime = 0;
    long sumTime = 0;
    FileOutputStream fileOutputStream =
        new FileOutputStream(new File(filePath));
    FileChannel fileChannel = fileOutputStream.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
    int size = stellarSystem.getTracksNumber();
    StellarSystemObject planet = null;
    for (int i = 0; i < size; i++) {
      planet = stellarSystem.getPlanetByNum(i + 1);
      stringBuilder.append("Planet ::= <").append(planet.getPlanetName())
          .append(",").append(planet.getPlanetState()).append(",")
          .append(planet.getPlanetColor()).append(",")
          .append(stellarSystem.enotationTransform(planet.getPlanetRadius()))
          .append(",")
          .append(stellarSystem.enotationTransform(planet.getTrackRadius()))
          .append(",")
          .append(stellarSystem.enotationTransform(planet.getRevolutionSpeed()))
          .append(",").append(planet.getRevolutionDiretion()).append(",")
          .append(planet.getAngle()).append(">\n");
      if (stringBuilder.length() > 1024 * 1024 - 100) {
        buffer.put(stringBuilder.toString().getBytes());
        buffer.flip();
        startTime = System.currentTimeMillis();
        fileChannel.write(buffer);
        endTime = System.currentTimeMillis();
        sumTime += endTime - startTime;
        buffer.clear();
        stringBuilder = new StringBuilder();
      }
    }
    buffer.put(stringBuilder.toString().getBytes());
    buffer.flip();
    startTime = System.currentTimeMillis();
    fileChannel.write(buffer);
    endTime = System.currentTimeMillis();
    sumTime += endTime - startTime;
    System.out.println("行星系统Nio写文件:" + sumTime + "ms");
    fileChannel.close();
    fileOutputStream.close();
  }

  /**
   * atom structure.
   */
  public void saveSystemInfoInFile(AtomStructure atomStructure, String filePath)
      throws IOException {
    try {
      throw new NoSuchMethodException("原子系统：不支持的操作");
    } catch (NoSuchMethodException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * save system in the strategy of nioStrategy.
   */
  public void saveSystemInfoInFile(SocialNetworkCircle socialNetworkCircle,
      String filePath) throws IOException {
    long startTime = 0;
    long endTime = 0;
    long sumTime = 0;
    FileOutputStream fileOutputStream =
        new FileOutputStream(new File(filePath));
    FileChannel fileChannel = fileOutputStream.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
    StringBuilder stringBuilder = new StringBuilder("CentralUser ::= <");
    // 中心用户行
    stringBuilder.append(socialNetworkCircle.getCentralPoint().getFriendName())
        .append(",").append(socialNetworkCircle.getCentralPoint().getAge())
        .append(",").append(socialNetworkCircle.getCentralPoint().getSex())
        .append(">");
    // 所有的朋友用户行
    for (Friend friend : socialNetworkCircle.getAllFriends()) {
      if (!friend.equals(socialNetworkCircle.getCentralPoint())) {
        stringBuilder.append("\nFriend ::= <").append(friend.getFriendName())
            .append(",").append(friend.getAge()).append(",")
            .append(friend.getSex()).append(">");
        if (stringBuilder.length() > 1024 * 1024 - 100) {
          buffer.put(stringBuilder.toString().getBytes());
          buffer.flip();
          startTime = System.currentTimeMillis();
          fileChannel.write(buffer);
          endTime = System.currentTimeMillis();
          sumTime += endTime - startTime;
          buffer.clear();
          stringBuilder = new StringBuilder();
        }
      }
    }
    // 所有亲密度关系行
    int size = socialNetworkCircle.getAllFriends().size();
    for (int i = 0; i < size; i++) {
      Friend friend = socialNetworkCircle.getAllFriends().get(i);
      for (int j = i; j < size; j++) {
        double intimacy = socialNetworkCircle.getAllFriends().get(i)
            .getSocialTie(socialNetworkCircle.getAllFriends().get(j));
        if (intimacy != 0) {
          stringBuilder.append("\nSocialTie ::= <")
              .append(friend.getFriendName()).append(",")
              .append(
                  socialNetworkCircle.getAllFriends().get(j).getFriendName())
              .append(",").append(intimacy).append(">");
          if (stringBuilder.length() > 1024 * 1024 - 100) {
            buffer.put(stringBuilder.toString().getBytes());
            buffer.flip();
            startTime = System.currentTimeMillis();
            fileChannel.write(buffer);
            endTime = System.currentTimeMillis();
            sumTime += endTime - startTime;
            buffer.clear();
            stringBuilder = new StringBuilder();
          }
        }
      }
    }
    buffer.put(stringBuilder.toString().getBytes());
    buffer.flip();
    startTime = System.currentTimeMillis();
    fileChannel.write(buffer);
    endTime = System.currentTimeMillis();
    sumTime += endTime - startTime;
    buffer.clear();
    stringBuilder = new StringBuilder();
    System.out.println("社交网络Nio写文件:" + sumTime + "ms");
    fileChannel.close();
    fileOutputStream.close();
  }

  private static ByteBuffer reAllocate(ByteBuffer stringBuffer) {
    final int capacity = stringBuffer.capacity();
    byte[] newBuffer = new byte[capacity * 2];
    System.arraycopy(stringBuffer.array(), 0, newBuffer, 0, capacity);
    return (ByteBuffer) ByteBuffer.wrap(newBuffer).position(capacity);
  }
}
