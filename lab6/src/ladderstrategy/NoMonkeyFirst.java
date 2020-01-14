package ladderstrategy;

import adt.Monkey;
import factory.LadderFactory;
import factory.MonkeyFactory;

public class NoMonkeyFirst implements LadderStrategy {

  /**
   * no monkey first strategy.
   * 
   * @return 0 if there is no ladder left; else return the ladder id
   */
  public Integer ladderStrategy(Monkey monkey) {
    Integer size = LadderFactory.getLadderNum();
    Integer monkeyId = monkey.getId();
    if (MonkeyFactory.getMonkeyState(monkey.getId()).equals(0)) {
      for (int i = 1; i <= size; i++) {
        if (LadderFactory.getOccupySize(i) == 0) {
          MonkeyFactory.changeState(monkeyId, 1);
          if (monkey.getDirection().equals(0)) {
            LadderFactory.changeOccupy(i, monkeyId, 1);
          } else {
            LadderFactory.changeOccupy(i, monkeyId, LadderFactory.getLadderLength());
          }
          return i;
        }
      }
      return null;
    } else {
      Integer ladderId = LadderFactory.getMonkeyLadder(monkeyId);
      Integer nowRung = LadderFactory.getMonkeyRung(monkeyId);
      Integer speed = monkey.getSpeed();
      Integer ladderLength = LadderFactory.getLadderLength();
      if (monkey.getDirection().equals(0)) {
        if (ladderLength >= nowRung + speed) {
          LadderFactory.changeOccupy(ladderId, monkeyId, nowRung + speed);
          return speed;
        } else {
          MonkeyFactory.changeState(monkeyId, 2);
          LadderFactory.changeOccupy(ladderId, monkeyId, 0);
          return null;
        }
      } else {
        if (nowRung - speed >= 1) {
          LadderFactory.changeOccupy(ladderId, monkeyId, nowRung - speed);
          return speed;
        } else {
          MonkeyFactory.changeState(monkeyId, 2);
          LadderFactory.changeOccupy(ladderId, monkeyId, 0);
          return null;
        }
      }
    }
  }

  @Override public String toString() {
    return "NoMonkeyFirst";
  }

}
