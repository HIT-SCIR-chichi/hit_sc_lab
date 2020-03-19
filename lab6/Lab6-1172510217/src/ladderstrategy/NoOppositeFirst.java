package ladderstrategy;

import adt.Monkey;
import factory.LadderFactory;
import factory.MonkeyFactory;

public class NoOppositeFirst implements LadderStrategy {

  /**
   * no opposite first strategy
   * if there is no monkey in a ladder, then choose the ladder. If all the ladders is in
   * use, then choose the ladder which is not in use with a opposite coming monkey.
   */
  public Integer ladderStrategy(Monkey monkey) {
    Integer ladderNum = LadderFactory.getLadderNum();
    Integer monkeyId = monkey.getId();
    Integer direction = monkey.getDirection();
    if (MonkeyFactory.getMonkeyState(monkey.getId()).equals(0)) {
      // 优先选择没有猴子的梯子
      for (int i = 1; i <= ladderNum; i++) {
        if (LadderFactory.getOccupySize(i) == 0) {
          MonkeyFactory.changeState(monkeyId, 1);
          if (direction.equals(0)) {
            LadderFactory.changeOccupy(i, monkeyId, 1);
          } else {
            LadderFactory.changeOccupy(i, monkeyId, LadderFactory.getLadderLength());
          }
          return i;
        }
      }
      // 若均被占用，再选择无对向而行猴子的梯子
      for (int i = 1; i <= ladderNum; i++) {
        if (LadderFactory.getOccupySize(i)
            - LadderFactory.monkeySameDirection(i, monkey) == 0) {
          if (direction.equals(0)) {
            if (!LadderFactory.checkOccupy(i, 1)) {
              MonkeyFactory.changeState(monkeyId, 1);
              LadderFactory.changeOccupy(i, monkeyId, 1);
              return i;
            }
          } else {
            if (!LadderFactory.checkOccupy(i, LadderFactory.getLadderLength())) {
              MonkeyFactory.changeState(monkeyId, 1);
              LadderFactory.changeOccupy(i, monkeyId, LadderFactory.getLadderLength());
              return i;
            }
          }
        }
      }
      return null;
    } else {
      Integer ladderId = LadderFactory.getMonkeyLadder(monkeyId);
      Integer nowRung = LadderFactory.getMonkeyRung(monkeyId);
      Integer speed = monkey.getSpeed();
      Integer ladderLength = LadderFactory.getLadderLength();
      // 处理猴子：L->R
      if (monkey.getDirection().equals(0)) {
        for (int i = nowRung + 1; i <= nowRung + speed; i++) {
          if (i > ladderLength) {
            MonkeyFactory.changeState(monkeyId, 2);
            LadderFactory.changeOccupy(ladderId, monkeyId, 0);
            return null;
          }
          if (LadderFactory.checkOccupy(ladderId, i)) {
            LadderFactory.changeOccupy(ladderId, monkeyId, i - 1);
            return i - nowRung - 1;
          }
        }
        LadderFactory.changeOccupy(ladderId, monkeyId, nowRung + speed);
        return speed; // 处理猴子：R->L
      } else {
        for (int i = nowRung - 1; i >= nowRung - speed; i--) {
          if (i < 1) {
            MonkeyFactory.changeState(monkeyId, 2);
            LadderFactory.changeOccupy(ladderId, monkeyId, 0);
            return null;
          }
          if (LadderFactory.checkOccupy(ladderId, i)) {
            LadderFactory.changeOccupy(ladderId, monkeyId, i + 1);
            return nowRung - i - 1;
          }
        }
        LadderFactory.changeOccupy(ladderId, monkeyId, nowRung - speed);
        return speed;
      }
    }
  }

  @Override public String toString() {
    return "NoOppositeFirst";
  }

}
