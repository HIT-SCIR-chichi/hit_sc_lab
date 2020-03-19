package P3;

public class Position {

    private int px = -1;
    private int py = -1;

    /**
     * 设置坐标属性
     * 
     * @param px 横坐标
     * @param py 纵坐标
     */
    public void setPosition(int px, int py) {
        this.px = px;
        this.py = py;
    }

    /**
     * 得到横坐标
     * 
     * @return 返回值为横坐标
     */
    public int getPx() {
        return px;
    }

    /**
     * 得到纵坐标
     * 
     * @return 纵坐标
     */
    public int getPy() {
        return py;
    }

    /**
     * 判断position和当前位置是否重合
     * 
     * @param position 待判断的坐标位置
     * @return true表示重合；否则不重合
     */
    public boolean positionEqual(Position position) {
        if (px == position.getPx()) {
            if (py == position.getPy()) {
                return true;
            }
        }
        return false;
    }

    @Override public String toString() {
        return "(" + px + "," + py + ")";
    }

    /**
     * 检查坐标是否越界，不在棋盘范围内
     * 
     * @param gametype gameType == 1表示为象棋，否则为围棋
     * @return 返回值为true表示在棋盘范围内
     */
    public Boolean checkPosition(int gametype) {
        if (gametype == 2) {// 围棋
            return px >= 0 && px <= 18 && py >= 0 && py <= 18;
        } // 象棋
        return px >= 0 && px <= 7 && py >= 0 && py <= 7;
    }

}
