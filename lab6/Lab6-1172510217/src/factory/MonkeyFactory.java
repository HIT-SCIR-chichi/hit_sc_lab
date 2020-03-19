package factory;

import adt.Monkey;
import adt.SystemConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import ladderstrategy.LadderStrategy;
import ladderstrategy.MyStrategy;
import ladderstrategy.NoMonkeyFirst;
import ladderstrategy.NoOppositeFirst;

public class MonkeyFactory {

  private static List<Monkey> allMonkeys =
      Collections.synchronizedList(new ArrayList<>());
  private static Map<Integer, Integer> monkeyToState =
      Collections.synchronizedMap(new HashMap<>());// 猴子及其状态
  private static double pops = 0;// 猴子过河的吞吐率
  private static double fairness = 0;// 猴子过河的公平性参数

  public static void addMonkey(Monkey monkey) {
    allMonkeys.add(monkey);
    monkeyToState.put(monkey.getId(), 0);
  }

  public static void changeState(Integer monkeyId, Integer state) {
    monkeyToState.put(monkeyId, state);
  }

  public static Integer getMonkeyState(Integer monkeyId) {
    return monkeyToState.get(monkeyId);
  }

  public static Integer getMonkeyNum() {
    return allMonkeys.size();
  }

  /**
   * get monkey by the monkey id.
   * 
   * @param  monkeyId the unique monkey id
   * @return          the monkey
   */
  public static Monkey getMonkeyById(Integer monkeyId) {
    if (monkeyId <= allMonkeys.size()) {
      return allMonkeys.get(monkeyId - 1);
    }
    return null;
  }

  /**
   * to get:得到当前时刻已经过河的猴子数目.
   * 
   * @return the number of the monkeys which is over the river
   */
  public static Integer getOverMonkey() {
    synchronized (monkeyToState) {
      Integer overMonkey = 0;
      for (Integer monkeyId : monkeyToState.keySet()) {
        if (monkeyToState.get(monkeyId).equals(2)) {
          overMonkey++;
        }
      }
      return overMonkey;
    }
  }

  public static synchronized Boolean isAllOver() {
    return !(monkeyToState.containsValue(0) || monkeyToState.containsValue(1));
  }

  /**
   * 猴子产生器.
   * 
   * @param time     产生猴子的时间间隔
   * @param num      一次产生的数目
   * @param maxSpeed 猴子的最大速度
   */
  public static void monkeyGenerator(Integer monkeyNum, Integer time, Integer num,
      Integer maxSpeed) {
    assert time != null && time > 0 : "错误：传入参数time错误";
    assert num != null && num > 0 : "错误：传入参数num错误";
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

      @Override public void run() {
        for (int i = 0; i < num; i++) {
          int size = allMonkeys.size();
          if (size < monkeyNum) {
            Monkey monkey = new Monkey(size + 1, (int) (Math.random() * 2),
                (int) (Math.random() * maxSpeed + 1), size / num * time,
                createLadderStrategy());
            MonkeyFactory.addMonkey(monkey);
            Thread monkeyThread = new Thread(monkey);
            monkeyThread.start();// 启动猴子过河进程
          } else {
            timer.cancel();
          }
        }
      }
    };
    timer.scheduleAtFixedRate(task, 0, time * 1000 / SystemConstant.getSpeedUp());
  }

  /**
   * 随机产生一种策略.
   * 
   * @return 返回一种选择梯子的策略
   */
  public static LadderStrategy createLadderStrategy() {
    Integer num = SystemConstant.getStrategyNum();
    LadderStrategy ladderStrategy = new NoMonkeyFirst();
    int randomNum = (int) (Math.random() * num) + 1;
    if (randomNum == 2) {
      ladderStrategy = new NoOppositeFirst();
    }
    if (randomNum == 3) {
      ladderStrategy = new MyStrategy();
    }
    return ladderStrategy;
  }

  /**
   * get the pops and fairness of the system when it is over.
   */
  public static void setPopsAndFairness() {
    synchronized (MonkeyFactory.class) {
      pops = 0;
      fairness = 0;
      Integer monkeyNum = allMonkeys.size();
      for (int i = 0; i < monkeyNum; i++) {
        Integer monkeyEndTime1 =
            allMonkeys.get(i).getBornTime() + allMonkeys.get(i).getLiveTime();
        if (monkeyEndTime1 > pops) {
          pops = monkeyEndTime1;
        }
        for (int j = i + 1; j < monkeyNum; j++) {
          Integer monkeyEndTime2 =
              allMonkeys.get(j).getBornTime() + allMonkeys.get(j).getLiveTime();
          if ((monkeyEndTime1 - monkeyEndTime2) * (allMonkeys.get(i).getBornTime()
              - allMonkeys.get(j).getBornTime()) >= 0) {
            fairness++;
          } else {
            fairness--;
          }
        }
      }
      pops = (double) monkeyNum / pops;
      fairness = fairness / (double) (monkeyNum * (monkeyNum - 1) / 2);
    }
  }

  /**
   * get the pops of the system.
   * 
   * @return the pops
   */
  public static double getPops() {
    return pops;
  }

  /**
   * get the fairness of the system.
   * 
   * @return the fairness
   */
  public static double getFairness() {
    return fairness;
  }

}
