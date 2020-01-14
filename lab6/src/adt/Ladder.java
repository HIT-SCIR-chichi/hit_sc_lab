package adt;

/**
 * AF:id,梯子的唯一标识
 * length,梯子的长度,为正整数
 * RI: 属性均为正数
 * safe from exposure:
 * for这个类中所有的成员变量一经创建不会改变，不会有表示泄漏的问题
 * thread safe:
 * for这个类没有多线程公共的数据，不会有线程安全问题.
 */
public class Ladder {

  private Integer id = null;// 梯子的id，独一无二的；从1开始
  private Integer length = null;// 梯子踏板数

  /**
   * the ladder always has a length and an unique id.
   * 
   * @param id the unique id of the ladder
   */
  public Ladder(Integer id, Integer length) {
    this.id = id;
    this.length = length;
  }

  /**
   * get the ladder id.
   * 
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * get the length of the ladder.
   * 
   * @return the length
   */
  public Integer getLength() {
    return length;
  }

  @Override public String toString() {
    return "ladder[id=" + id + ",length=" + length + "]";
  }

}
