package adt;

import factory.MonkeyCrossRiverSystem;
import factory.MonkeyFactory;
import java.util.logging.Logger;
import ladderstrategy.LadderStrategy;

/**
 * AF:id,猴子的唯一标识
 * direction,0=L->R;1=R->L
 * speed:猴子在梯子上的爬行速度,正整数
 * RI: speed等均为整数
 * safe from exposure:
 * 这个类中所有的成员变量除了liveTime都是private且一经创建不会改变，不会有表示泄漏的问题
 * thread safe:
 * for这个类没有多线程公共的数据，不会有线程安全问题.
 */
public class Monkey implements Runnable {

  private static StringBuilder logBuilder = new StringBuilder();
  private static Logger logger =
      Logger.getLogger(MonkeyCrossRiverSystem.class.getSimpleName());//猴子日志
  private Integer id = null;// 猴子的id，是独一无二的；从1开始
  private Integer direction = null;// 猴子的移动方向，0代表L->R，1代表R->L
  private Integer speed = null;// 猴子的速度，为正整数,范围是[1,MV]
  private Integer bornTime = null;// 猴子的产生时间，是确定的非负整数
  private Integer liveTime = null;// 猴子的过河终止时间，是确定的正整数
  private LadderStrategy ladderStrategy = null;// 猴子选择梯子的策略

  /**
   * the monkey is unique when born.
   * 
   * @param id             unique id
   * @param direction      the direction when crossing the river
   * @param speed          the speed when crossing the river
   * @param bornTime       the born time of the monkey
   * @param ladderStrategy the strategy the monkey takes to cross the river
   */
  public Monkey(Integer id, Integer direction, Integer speed, Integer bornTime,
      LadderStrategy ladderStrategy) {
    this.id = id;
    this.direction = direction;
    this.speed = speed;
    this.bornTime = bornTime;
    this.ladderStrategy = ladderStrategy;
  }

  /**
   * override monkey thread.
   */
  @Override public void run() {
    liveTime = 0;
    Integer monkeyState = 0;
    do {
      synchronized (Monkey.class) {
        ladderStrategy.ladderStrategy(this);
        monkeyState = MonkeyFactory.getMonkeyState(id);
        if (monkeyState.equals(0)) {
          logger.info("猴子：" + id + " 方向：" + directionString() + " 状态：等待 耗时：" + liveTime
              + "  策略：" + ladderStrategy);
          logBuilder.append("猴子：").append(id).append(" 方向：").append(directionString())
              .append(" 状态：等待 耗时：").append(liveTime++).append(" 策略：")
              .append(ladderStrategy).append("\n");
        } else if (monkeyState.equals(1)) {
          logger.info("猴子：" + id + " 方向：" + directionString() + " 状态：进行 耗时：" + liveTime
              + "  策略：" + ladderStrategy);
          logBuilder.append("猴子：").append(id).append(" 方向：").append(directionString())
              .append(" 状态：进行 耗时：").append(liveTime++).append(" 策略：")
              .append(ladderStrategy).append("\n");
        } else {
          logger.info("猴子：" + id + " 方向：" + directionString() + " 状态：成功 耗时：" + liveTime
              + "  策略：" + ladderStrategy);
          logBuilder.append("猴子：").append(id).append(" 方向：").append(directionString())
              .append(" 状态：成功 耗时：").append(liveTime).append(" 策略：").append(ladderStrategy)
              .append("\n");
          break;
        }
      }
      try {
        Thread.sleep(1000 / SystemConstant.getSpeedUp());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } while (true);
  }

  /**
   * get the live time of the monkey.
   * 
   * @return the liveTime
   */
  public Integer getLiveTime() {
    return liveTime;
  }

  /**
   * get the id of the monkey.
   * 
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * get the direction of the monkey.
   * 
   * @return the direction
   */
  public Integer getDirection() {
    return direction;
  }

  /**
   * get the speed of the monkey.
   * 
   * @return the speed
   */
  public Integer getSpeed() {
    return speed;
  }

  /**
   * get the born time of the monkey.
   * 
   * @return the bornTime
   */
  public Integer getBornTime() {
    return bornTime;
  }

  /**
   * get the strategy the monkey takes.
   * 
   * @return the ladderStrategy
   */
  public LadderStrategy getLadderStrategy() {
    return ladderStrategy;
  }

  /**
   * direction to string.
   * 
   * @return the string of the direction
   */
  public String directionString() {
    if (direction.equals(0)) {
      return "L->R";
    } else {
      return "R->L";
    }
  }

  /**
   * override the toString method in the class to express the monkey better.
   */
  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder("monkey[id=");
    stringBuilder.append(id).append(",diretion=").append(this.directionString())
        .append(",speed=").append(speed).append(",bornTime=").append(bornTime)
        .append(",ladderStrategy=").append(ladderStrategy).append("]");
    return stringBuilder.toString();
  }

  public static String getLogString() {
    return logBuilder.toString();
  }

}
