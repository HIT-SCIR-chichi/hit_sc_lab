package circularorbit;

import applications.App;
import centralobject.Stellar;
import iostrategy.IoStrategy;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import myexception.CentralObjectException;
import myexception.FileChooseException;
import myexception.LabelSameException;
import myexception.PhysicalObjectException;
import myexception.StellarRadiusException;
import physicalobject.StellarSystemObject;
import track.Track;

public class StellarSystem
    extends ConcreteCircularOrbit<Stellar, StellarSystemObject> {

  /* 该系统必须在使用时更新readTime */
  private Calendar readTime = null;// 读取文件建立此系统的时刻
  private static final Logger logger =
      Logger.getLogger(App.class.getSimpleName());
  private File readFile = null;
  private String writeFilePath = null;

  /**
   * clone 方法实现.
   */
  @Override public Object clone() throws CloneNotSupportedException {
    StellarSystem stellarSystem = (StellarSystem) super.clone();
    stellarSystem.readTime = Calendar.getInstance();
    stellarSystem.readFile = null;
    stellarSystem.writeFilePath = null;
    return stellarSystem;
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

  public StellarSystemObject getPlanetByNum(int num) {
    return this.getTrack(num).getIndexPhysicalObject(1);
  }

  /**
   * note:将数值表示成科学记数法的形式.
   * 
   * @param  number 要转换形式的数值
   * @return        返回科学记数法正确格式的字符串形式
   */
  public String enotationTransform(double number) {
    if (number < 10000) {
      return String.valueOf(number);
    } else {
      int count = 0;
      StringBuilder stringBuilder = new StringBuilder();
      while (number >= 10) {
        number /= 10;
        count++;
      }
      stringBuilder.append(number);
      stringBuilder.append("e");
      stringBuilder.append(count);
      return stringBuilder.toString();
    }
  }

  /**
   * 检查表示不变量.
   * 
   * @throws FileChooseException 文件选取异常
   */
  public void checkRep() throws FileChooseException {
    try {
      // 判断中心天体存在且唯一
      if (getCentralPoint() == null) {
        throw new CentralObjectException("中心恒星缺失");
      }
      Set<String> labelSet = new HashSet<>();
      labelSet.add(getCentralPoint().getName());
      for (int i = 0; i < getTracksNumber(); i++) {
        Track<StellarSystemObject> track = getTrack(i + 1);
        // 判断是否一个轨道上有且只有一个行星
        if (track.getNumberOfObjects() != 1) {
          throw new PhysicalObjectException(
              "轨道上行星数目非法" + track + track.getNumberOfObjects());
        }
        // 判断该系统中是否存在标签相同的情况
        StellarSystemObject planet = track.getIndexPhysicalObject(1);
        if (!labelSet.add(planet.getPlanetName())) {
          throw new LabelSameException("系统中天体名存在相同情况");
        }
        // 判断是否存在两个轨道半径之差小于等于两个天体半径之和的情况
        double radiusSum = planet.getPlanetRadius();
        double trackRadiusDif = planet.getTrackRadius();
        radiusSum =
            (i == 0) ? radiusSum + getCentralPoint().getRadius() : radiusSum
                + getTrack(i).getIndexPhysicalObject(1).getPlanetRadius();
        trackRadiusDif = (i == 0) ? trackRadiusDif : trackRadiusDif
            - getTrack(i).getIndexPhysicalObject(1).getTrackRadius();
        if (trackRadiusDif < radiusSum) {
          throw new StellarRadiusException("行星轨道半径之差小于两相邻星体轨道半径之和:" + (i + 1));
        }
      }
    } catch (CentralObjectException e) {
      throw new FileChooseException("checkRep接收到异常，需要重新选择文件：" + e.getMessage());
    } catch (PhysicalObjectException e) {
      throw new FileChooseException("checkRep接收到异常，需要重新选择文件：" + e.getMessage());
    } catch (LabelSameException e) {
      throw new FileChooseException("checkRep接收到异常，需要重新选择文件：" + e.getMessage());
    } catch (StellarRadiusException e) {
      throw new FileChooseException("checkRep接收到异常，需要重新选择文件：" + e.getMessage());
    }
  }

  /**
   * 设置readTime.
   * 
   * @param readTime 读取时间，默认的初始系统时间
   */
  public void setReadTime(Calendar readTime) {
    this.readTime = readTime;
  }

  /**
   * 得到readTime.
   * 
   * @return 返回readTime
   */
  public Calendar getReadTime() {
    return this.readTime;
  }

  /**
   * 计算相对于读取文件时刻的某时刻的行星角度位置.
   * 
   * @param  planet  待计算的行星
   * @param  newTime 新的时刻
   * @return         返回新时刻的角度
   */
  public double calculatePosition(StellarSystemObject planet,
      Calendar newTime) {
    assert planet != null && newTime != null : logIn("参数错误：null");
    long timeDif = newTime.getTimeInMillis() - readTime.getTimeInMillis();
    timeDif /= 1000;// 差的秒数
    double angleDif = planet.getRevolutionSpeed() * timeDif * 360
        / (2 * Math.PI * planet.getTrackRadius());
    if (planet.getRevolutionDiretion().equals("CCW")) {
      angleDif += planet.getAngle();
      return angleDif % 360;
    } else {
      angleDif = planet.getAngle() - angleDif;
      angleDif %= 360;
      return angleDif < 0 ? angleDif + 360 : angleDif;
    }
  }

  /**
   * 得到读取文件时刻两个行星之间的物理距离.
   * 
   * @param  planet1 行星1
   * @param  planet2 行星2
   * @return         返回两个行星之间的物理距离
   */
  public double getPhysicalDistance(StellarSystemObject planet1,
      StellarSystemObject planet2) {
    assert planet1 != null && planet2 != null : logIn("参数错误：null");
    double radius1 = planet1.getTrackRadius();
    double radius2 = planet2.getTrackRadius();
    double angleDif = planet1.getAngle() - planet2.getAngle();
    double distance = Math.pow(radius1, 2) + Math.pow(radius2, 2)
        - 2 * radius1 * radius2 * Math.cos(angleDif);
    return Math.pow(distance, 0.5);
  }

  /**
   * 得到恒星与某行星之间的物理距离.
   * 
   * @param  planet 待计算距离的行星;planet != null
   * @return        返回距离
   */
  public double getPhysicalDistanceStar(StellarSystemObject planet) {
    assert planet != null : logIn("参数错误：null");
    return planet.getTrackRadius();
  }

  private static String logIn(String message) {
    logger.severe(message);
    return "已将assert错误信息加载在日志文件里";
  }

}
