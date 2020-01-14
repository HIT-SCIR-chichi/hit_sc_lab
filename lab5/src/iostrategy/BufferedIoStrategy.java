package iostrategy;

import centralobject.CentralObject;
import centralobject.Stellar;
import circularorbit.AtomStructure;
import circularorbit.SocialNetworkCircle;
import circularorbit.StellarSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import myexception.CentralObjectException;
import myexception.DependencyException;
import myexception.FileChooseException;
import myexception.FileGrammerException;
import myexception.LabelSameException;
import physicalobject.Friend;
import physicalobject.PhysicalObject;
import physicalobject.StellarSystemObject;
import track.Track;

public class BufferedIoStrategy implements IoStrategy {

  /**
   * read file and create system.
   * 
   * @throws IOException IO
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
      InputStreamReader reader =
          new InputStreamReader(new FileInputStream(file));
      BufferedReader bfReader = new BufferedReader(reader);
      while ((lineString = bfReader.readLine()) != null) {
        lineCount++;
        if ((matcher = pattern1.matcher(lineString)).find()) {
          count++;
          String group1 = matcher.group(1);
          String group2 = matcher.group(2);
          String group3 = matcher.group(3);
          if (!Pattern.matches("[0-9a-zA-Z]+", group1)) {
            bfReader.close();
            throw new FileGrammerException("恒星名字错误，行号：" + lineCount, 10);
          }
          if (!Pattern.matches(
              "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
              group2)) {
            bfReader.close();
            throw new FileGrammerException("恒星半径错误，行号：" + lineCount, 10);
          }
          if (!Pattern.matches(
              "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
              group3)) {
            bfReader.close();
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
            bfReader.close();
            throw new FileGrammerException("行星名错误，行号：" + lineCount, 10);
          }
          if (!Pattern.matches("[0-9a-zA-Z]+", group2)) {
            bfReader.close();
            throw new FileGrammerException("行星状态错误，行号：" + lineCount, 10);
          }
          if (!Pattern.matches("[0-9a-zA-Z]+", group3)) {
            bfReader.close();
            throw new FileGrammerException("行星颜色错误，行号：" + lineCount, 10);
          }
          String group4 = matcher.group(4);
          String group5 = matcher.group(5);
          String group6 = matcher.group(6);

          if (!Pattern.matches(
              "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
              group4)) {
            bfReader.close();
            throw new FileGrammerException("行星半径错误，行号：" + lineCount + group4,
                10);
          }
          if (!Pattern.matches(
              "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
              group5)) {
            bfReader.close();
            throw new FileGrammerException("轨道半径错误，行号：" + lineCount, 10);
          }
          if (!Pattern.matches(
              "[0-9]{1,4}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?",
              group6)) {
            bfReader.close();
            throw new FileGrammerException("公转速度错误，行号：" + lineCount, 10);
          }
          String group7 = matcher.group(7);
          String group8 = matcher.group(8);
          if (!Pattern.matches("(CW)|(CCW)", group7)) {
            bfReader.close();
            throw new FileGrammerException("公转方向错误，行号：" + lineCount, 10);
          }
          if (!Pattern.matches("[0-9]{1,3}([.][0-9]+)?", group8)) {
            bfReader.close();
            throw new FileGrammerException("初始角度错误，行号：" + lineCount, 10);
          }
          double angle = Double.parseDouble(group8);
          if (angle < 0 || angle >= 360) {
            bfReader.close();
            throw new FileGrammerException("初始角度范围错误，行号：" + lineCount, 10);
          }
          StellarSystemObject planet = new StellarSystemObject(group1, group2,
              group3, Double.parseDouble(group4), Double.parseDouble(group6),
              group7, angle);
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
            bfReader.close();
            throw new FileGrammerException("无用信息行，行数：" + lineCount, 10);
          }
        }
      }
      long endTime = System.currentTimeMillis();
      System.out.println("行星系统BufferedIO读文件:" + (endTime - startTime) + "ms");
      if (count != 1) {
        bfReader.close();
        throw new CentralObjectException("恒星缺失或多余");
      }
      bfReader.close();
      stellarSystem.sortTrack();
      stellarSystem.checkRep();
    } catch (CentralObjectException e) {
      throw new FileChooseException("文件中心恒星错误，需要重新选择文件：" + e.getMessage());
    } catch (FileGrammerException e) {
      throw new FileChooseException("文件语法解析错误，需要重新选择文件：" + e.getMessage());
    }
  }

  /**
   * read file.
   * 
   * @throws IOException         IO
   * @throws FileChooseException 文件选取
   */
  public void readFileAndCreateSystem(AtomStructure atomStructure, File file)
      throws IOException, FileChooseException {
    try {
      InputStreamReader reader =
          new InputStreamReader(new FileInputStream(file));
      BufferedReader bfReader = new BufferedReader(reader);
      Pattern pattern1 = Pattern.compile("ElementName ::= ((?:[a-z]|[A-Z])+)");
      Pattern pattern11 = Pattern.compile("[A-Z][a-z]?");
      Pattern pattern2 = Pattern.compile("NumberOfTracks ::= ([-]?[0-9]?)");
      Pattern pattern3 = Pattern.compile(
          "NumberOfElectron ::= [ ]*([1-9][/]\\d+(?:[ ]*[;][ ]*[1-9][/]\\d+)*)");
      String lineString = "";
      int trackNum = 0;
      int trackAct = 0;
      Matcher matcher;
      int count = 0;// 统计有效行数
      while ((lineString = bfReader.readLine()) != null) {
        if ((matcher = pattern1.matcher(lineString)).find()) {
          count++;
          String eleName = matcher.group(1);
          if (eleName.length() > 2) {
            bfReader.close();
            throw new FileGrammerException(
                "ElementName length should not be" + eleName.length(), 20);
          } else if (eleName.length() == 2) {
            if (!pattern11.matcher(eleName).find()) {
              bfReader.close();
              throw new FileGrammerException("此处的元素名应该是大写字母+小写字母", 21, eleName);
            }
          } else if (eleName.length() == 1) {
            if (!pattern11.matcher(eleName).find()) {
              bfReader.close();
              throw new FileGrammerException("元素名应该是大写字母", 22, eleName);
            }
          }
          CentralObject element = new CentralObject();
          element.setName(eleName);
          atomStructure.addCentralPoint(element);
        } else if ((matcher = pattern2.matcher(lineString)).find()) {
          count++;
          trackNum = Integer.parseInt(matcher.group(1));
          if (trackNum <= 0) {
            bfReader.close();
            throw new FileGrammerException("轨道数错误：" + trackNum, 23);
          }
        } else if ((matcher = pattern3.matcher(lineString)).find()) {
          count++;
          lineString = matcher.group(1);
          String[] strings = lineString.split(";");
          trackAct = strings.length;
          for (int i = 0; i < strings.length; i++) {
            matcher =
                Pattern.compile("[ ]*([1-9])[/](\\d+)[ ]*").matcher(strings[i]);
            if (matcher.find()) {
              if (Integer.parseInt(matcher.group(1)) != i + 1) {
                bfReader.close();
                throw new FileGrammerException("轨道号未按照顺序", 25);
              }
              for (int j = 0; j < Integer.parseInt(matcher.group(2)); j++) {
                PhysicalObject physicalObject = new PhysicalObject();
                physicalObject.setTrackRadius(i + 1);
                atomStructure.addPhysicalObject(physicalObject);
              }
            }
          }
        } else {
          if (!lineString.equals("")) {
            bfReader.close();
            throw new FileGrammerException("非法行：无相应处理机制", 20);
          }
        }
      }
      if (trackAct != trackNum) {
        bfReader.close();
        throw new DependencyException("文件输入轨道数和参数轨道数不一致", 24, "" + trackAct);
      }
      if (count != 3) {
        bfReader.close();
        throw new FileGrammerException("缺少信息行", 20);
      }
      bfReader.close();
      atomStructure.sortTrack();
    } catch (FileGrammerException e) {
      throw new FileChooseException("文件语法解析错误，需要重新选择文件：" + e.getMessage());
    } catch (DependencyException e) {
      throw new FileChooseException("依赖关系错误，需要重新选择文件：" + e.getMessage());
    }
  }

  /**
   * read file.
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
      InputStreamReader reader =
          new InputStreamReader(new FileInputStream(file));
      BufferedReader bfReader = new BufferedReader(reader);
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
          socialNetworkCircle.addCentralPoint(centralUser);
          socialNetworkCircle.addFriend(centralUser);
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
          Friend friend = socialNetworkCircle.getFriendByName(matcher.group(1));
          if (friend == null) {
            friend = new Friend();
            friend.setFriendName(matcher.group(1));
            friend.setAge(Integer.parseInt(matcher.group(2)));
            friend.setSex(matcher.group(3));
            socialNetworkCircle.addFriend(friend);
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
          if (!Pattern.matches("([0][.][0-9]{0,3})|([1]([.][0]{0,3})?)",
              matcher.group(3))) {
            bfReader.close();
            throw new FileGrammerException("亲密度参数错误，行数：" + lineCount, 30);
          }
          if (f1String.equals(f2String)) {
            bfReader.close();
            throw new LabelSameException("待添加关系的两人为同一个人，行数：" + lineCount);
          }
          double intimacy = Double.parseDouble(matcher.group(3));
          if (intimacy == 0) {
            bfReader.close();
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
            bfReader.close();
            throw new LabelSameException(
                "待添加的两人已存在亲密度且与此处亲密度不同，行数：" + lineCount);
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
      long endTime = System.currentTimeMillis();
      System.out.println("社交网络BufferedIO读文件:" + (endTime - startTime) + "ms");
      if (count != 1) {
        bfReader.close();
        throw new CentralObjectException("中心用户缺失或多余，行数：" + lineCount);
      }
      socialNetworkCircle.getAllDistance();
      bfReader.close();
      socialNetworkCircle.sortTrack();
      socialNetworkCircle.checkRep();
    } catch (FileGrammerException e) {
      throw new FileChooseException("文件语法解析错误，需要重新选择文件：" + e.getMessage());
    } catch (LabelSameException e) {
      throw new FileChooseException("存在表情相同元素，需要重新选择文件：" + e.getMessage());
    } catch (CentralObjectException e) {
      throw new FileChooseException("中心用户错误，需要重新选择文件：" + e.getMessage());
    }
  }

  /**
   * read file.
   * 
   * @throws IOException IO
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
    }
    long startTime = System.currentTimeMillis();
    FileWriter fileWriter = new FileWriter(filePath);
    BufferedWriter bfWriter = new BufferedWriter(fileWriter);
    bfWriter.write(stringBuilder.toString());
    long endTime = System.currentTimeMillis();
    System.out.println("行星系统BufferedIO写文件:" + (endTime - startTime) + "ms");
    bfWriter.close();
  }

  /**
   * save system.
   */
  public void saveSystemInfoInFile(AtomStructure atomStructure, String filePath)
      throws IOException {
    StringBuilder stringBuilder = new StringBuilder("ElementName ::= ");
    stringBuilder.append(atomStructure.getCentralPoint().getName());// 元素第一行
    stringBuilder.append("\nNumberOfTracks ::= ")
        .append(atomStructure.getTracksNumber());// 元素第二行
    stringBuilder.append("\nNumberOfElectron ::= ");// 元素第三行
    for (int i = 1; i <= atomStructure.getTracksNumber(); i++) {
      stringBuilder.append(i).append("/")
          .append(atomStructure.getTrackObjectsNumber(i));
      if (i < atomStructure.getTracksNumber()) {
        stringBuilder.append(";");
      }
    }
    FileWriter fileWriter = new FileWriter(filePath);
    BufferedWriter bfWriter = new BufferedWriter(fileWriter);
    bfWriter.write(stringBuilder.toString());
    bfWriter.close();
  }

  /**
   * save system.
   */
  public void saveSystemInfoInFile(SocialNetworkCircle socialNetworkCircle,
      String filePath) throws IOException {
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
      }
    }
    // 所有亲密度关系行
    int size = socialNetworkCircle.getAllFriends().size();
    for (int i = 0; i < size; i++) {
      Friend friend = socialNetworkCircle.getAllFriends().get(i);
      for (int j = i; j < size; j++) {
        double intimacy =
            friend.getSocialTie(socialNetworkCircle.getAllFriends().get(j));
        if (intimacy != 0) {
          stringBuilder.append("\nSocialTie ::= <")
              .append(friend.getFriendName()).append(",")
              .append(
                  socialNetworkCircle.getAllFriends().get(j).getFriendName())
              .append(",").append(intimacy).append(">");
        }
      }
    }
    long startTime = System.currentTimeMillis();
    FileWriter fileWriter = new FileWriter(filePath);
    BufferedWriter bfWriter = new BufferedWriter(fileWriter);
    bfWriter.write(stringBuilder.toString());
    long endTime = System.currentTimeMillis();
    System.out.println("社交网络BufferedIO写文件:" + (endTime - startTime) + "ms");
    bfWriter.close();
  }

}
