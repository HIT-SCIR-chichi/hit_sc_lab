package P3;

public class Piece {

    private Position piecePosition = new Position();// 棋子的坐标
    private String pieceName = new String();// 棋子的类别
    private Boolean player;// 棋子的拥有者：true表示为棋手1

    /**
     * 初始化一颗棋子
     * 
     * @param px        棋子的横坐标
     * @param py        棋子的纵坐标
     * @param pieceName 棋子的名字
     * @param player    棋子的拥有者
     */
    public Piece(int px, int py, String pieceName, Boolean player) {
        this.pieceName = pieceName;
        this.piecePosition.setPosition(px, py);
        this.player = player;
    }

    /**
     * 设置棋子的坐标
     * 
     * @param px 棋子的横坐标
     * @param py 棋子的纵坐标
     */
    public void setPiecePosition(int px, int py) {
        this.piecePosition.setPosition(px, py);
    }

    /**
     * 设置棋子的拥有者
     * 
     * @param player 棋子拥有者为Player
     */
    public void setPlayer(Boolean player) {
        this.player = player;
    }

    /**
     * 得到坐标
     * 
     * @return 返回坐标
     */
    public Position getPiecePosition() {
        return this.piecePosition;
    }

    /**
     * 得到棋子的拥有者
     * 
     * @return 返回棋子的拥有者
     */
    public Boolean getPlayer() {
        return player;
    }

    /**
     * 得到棋子的名字
     * 
     * @return 返回棋子的名字
     */
    public String getPieceName() {
        return pieceName;
    }

    /**
     * 设置棋子的名字
     * 
     * @param pieceName 棋子的名字
     */
    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }
}
