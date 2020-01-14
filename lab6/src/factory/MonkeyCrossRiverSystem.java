package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonkeyCrossRiverSystem {

  private static Integer monkeyNum = 0;
  private static Integer ladderNum = 0;
  private static Integer length = 0;
  private static Integer time = 0;
  private static Integer num = 0;
  private static Integer maxSpeed = 0;

  /**
   * 读取配置文件，确定一系列参数，并建立系统.
   * 
   * @param  filePath    文件路径
   * @throws IOException IO异常
   */
  public static void readFileAndCreateSystem(String filePath) throws IOException {
    assert filePath != null : "传入参数文件路径为空";
    File file = new File(filePath);
    BufferedReader bfReader = new BufferedReader(new FileReader(file));
    String lineString = null;
    if ((lineString = bfReader.readLine()) != null) {
      Pattern pattern =
          Pattern.compile("n=(\\d+),h=(\\d+),t=(\\d+),N=(\\d+),k=(\\d+),MV=(\\d+)");
      Matcher matcher = pattern.matcher(lineString);
      if (matcher.find()) {
        ladderNum = Integer.valueOf(matcher.group(1));
        length = Integer.valueOf(matcher.group(2));
        time = Integer.valueOf(matcher.group(3));
        monkeyNum = Integer.valueOf(matcher.group(4));
        num = Integer.valueOf(matcher.group(5));
        maxSpeed = Integer.valueOf(matcher.group(6));
        LadderFactory.ladderGenerator(ladderNum, length);
        MonkeyFactory.monkeyGenerator(monkeyNum, time, num, maxSpeed);
      }
    }
    bfReader.close();
  }

  /**
   * get the monkey number.
   * 
   * @return the monkeyNum
   */
  public static Integer getMonkeyNum() {
    return monkeyNum;
  }

  /**
   * get the ladder number.
   * 
   * @return the ladderNum
   */
  public static Integer getLadderNum() {
    return ladderNum;
  }

  /**
   * get the ladder length.
   * 
   * @return the length
   */
  public static Integer getLength() {
    return length;
  }

  /**
   * get the time.
   * 
   * @return the time
   */
  public static Integer getTime() {
    return time;
  }

  /**
   * get the number created in the time.
   * 
   * @return the number
   */
  public static Integer getNum() {
    return num;
  }

  /**
   * get the max speed.
   * 
   * @return the maxSpeed
   */
  public static Integer getMaxSpeed() {
    return maxSpeed;
  }
}
