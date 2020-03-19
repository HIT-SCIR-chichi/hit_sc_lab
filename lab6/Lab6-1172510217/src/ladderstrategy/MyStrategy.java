package ladderstrategy;

import adt.Monkey;
import factory.LadderFactory;
import factory.MonkeyFactory;

/**
 * my personal strategy:优先选取未被占用的梯子；若均被占用，优先选取无对向行驶的猴子；
 * then:若有许多选择，则优先选取梯子上同向猴子数目最少且距离自己最远的梯子.
 * 
 * @author 86185
 */
public class MyStrategy implements LadderStrategy {

  @Override public String toString() {
    return "MyStrategy";
  }

  /**
   * my strategy.
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
      Integer betterLadder = ladderNum + 1;
      Integer monkeySameDirection = MonkeyFactory.getMonkeyNum() + 1;
      // 若方向为L->R
      if (direction.equals(0)) {
        for (int i = 1; i <= ladderNum; i++) {
          if (LadderFactory.getOccupySize(i)
              - LadderFactory.monkeySameDirection(i, monkey) == 0) {
            if (!LadderFactory.checkOccupy(i, 1)) {
              // 寻找同向猴子最少的梯子
              if (LadderFactory.monkeySameDirection(i, monkey) < monkeySameDirection) {
                betterLadder = i;
                monkeySameDirection = LadderFactory.monkeySameDirection(i, monkey);
              }
              // 在上一步的前提下，寻找梯子上第一个猴子距当前猴子最远的梯子.
              if (LadderFactory.monkeySameDirection(i, monkey) == monkeySameDirection) {
                if (LadderFactory.getFirstRungIdSameDirection(i, monkey) < LadderFactory
                    .monkeySameDirection(betterLadder, monkey)) {
                  betterLadder = i;
                }
              }
            }
          }
        }
        if (!betterLadder.equals(ladderNum + 1)) {
          MonkeyFactory.changeState(monkeyId, 1);
          LadderFactory.changeOccupy(betterLadder, monkeyId, 1);
          return betterLadder;
        }
      } else {
        for (int i = 1; i <= ladderNum; i++) {
          if (LadderFactory.getOccupySize(i)
              - LadderFactory.monkeySameDirection(i, monkey) == 0) {
            if (!LadderFactory.checkOccupy(i, LadderFactory.getLadderLength())) {
              // 寻找最少同向猴子的梯子
              if (LadderFactory.monkeySameDirection(i, monkey) < monkeySameDirection) {
                betterLadder = i;
                monkeySameDirection = LadderFactory.monkeySameDirection(i, monkey);
              }
              // 在上一步的前提下，寻找梯子上第一个猴子距当前猴子最远的梯子.
              if (LadderFactory.monkeySameDirection(i, monkey) == monkeySameDirection) {
                if (LadderFactory.getFirstRungIdSameDirection(i, monkey) > LadderFactory
                    .monkeySameDirection(betterLadder, monkey)) {
                  betterLadder = i;
                }
              }
            }
          }
        }
        if (!betterLadder.equals(ladderNum + 1)) {
          MonkeyFactory.changeState(monkeyId, 1);
          LadderFactory.changeOccupy(betterLadder, monkeyId,
              LadderFactory.getLadderLength());
          return betterLadder;
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
}
