package P3;

public class Board {

    private Boolean[][] goBoard1 = new Boolean[19][19];// 用于储存棋盘棋子存在情况：true表示已被占用
    private Boolean[][] goBoard2 = new Boolean[19][19];// 用于储存棋手占用情况：true表示player1

    private Boolean[][] chessBoard1 = new Boolean[8][8];// 用于储存棋盘棋子存在情况：true表示已被占用
    private Boolean[][] chessBoard2 = new Boolean[8][8];// 用于储存棋手占用情况：true表示player1

    /**
     * function：初始化Boolean数组元素为false，表示棋盘上无棋子
     */
    public Board(int gameType) {
        if (gameType == 2) {
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {
                    goBoard1[i][j] = false;// false表示此处无棋子
                    goBoard2[i][j] = false;// false表示此处被棋手1占用
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (i >= 0 && i < 2) {
                        chessBoard1[i][j] = true;// false表示此处无棋子
                        chessBoard2[i][j] = true;// false表示此处被棋手1占用
                    } else if (i >= 6 && i < 8) {
                        chessBoard1[i][j] = true;// false表示此处无棋子
                        chessBoard2[i][j] = false;// false表示此处被棋手1占用
                    } else {
                        chessBoard1[i][j] = false;// false表示此处无棋子
                        chessBoard2[i][j] = false;// false表示此处被棋手1占用
                    }
                }
            }
        }
    }

    /**
     * function：将某个棋子下在棋盘上
     * 
     * @param piece 待落子
     */
    public void setGoBoard(Piece piece) {
        int px = piece.getPiecePosition().getPx();// 得到位置横坐标
        int py = piece.getPiecePosition().getPy();// 得到位置纵坐标
        goBoard1[px][py] = true;
        goBoard2[px][py] = piece.getPlayer();
    }

    /**
     * 将某个棋子从棋盘的二维数组表示上删除
     * 
     * @param piece 待操作棋子
     */
    public void deleteChessBoardPiece(Piece piece) {
        int px = piece.getPiecePosition().getPx();
        int py = piece.getPiecePosition().getPy();
        chessBoard1[px][py] = false;
        chessBoard2[px][py] = false;
    }

    /**
     * 将某个棋子的坐标位置表示在二维棋盘数组上
     * 
     * @param piece 待操作棋子
     */
    public void addChessBoradPiece(Piece piece) {
        int px = piece.getPiecePosition().getPx();
        int py = piece.getPiecePosition().getPy();
        chessBoard1[px][py] = true;
        chessBoard2[px][py] = piece.getPlayer();
    }

    /**
     * 根据坐标判断该位置是否被占用
     * 
     * @param position 要判断的位置
     * @param gameType 要判断的游戏类型
     * @return 返回值为true，表示已被占用；false表示未被占用
     */
    public boolean getOccupation(Position position, int gameType) {
        if (gameType == 1) {// 若为象棋，则访问chessBoard
            return chessBoard1[position.getPx()][position.getPy()];
        } else {
            return goBoard1[position.getPx()][position.getPy()];
        }
    }

    /**
     *根据坐标判断该位置被谁占用
     * @param position 要判断的坐标位置
     * @param gameType 要判断的游戏类型
     * @return 返回值为true，表示被选手一占用，否则被选手二占用
     */
    public boolean getOccupationPlayer(Position position, int gameType) {
        if (gameType == 1) {//表示为象棋
            return chessBoard2[position.getPx()][position.getPy()];
        } else {
            return goBoard2[position.getPx()][position.getPy()];
        }
    }

    /**
     * 检查某位置的占用情况：包括是否被占用以及被谁占用
     * @param position 要判断的坐标位置
     * @param gameType 要判断的游戏：1代表象棋，2代表围棋
     */
    public void checkOccupation(Position position, int gameType) {
        int px = position.getPx();// 得到位置横坐标
        int py = position.getPy();// 得到位置纵坐标
        if (gameType == 2) {
            if (goBoard1[px][py]) {
                System.out.print("该位置" + position.toString() + "已被棋手");
                if (goBoard2[px][py]) {
                    System.out.println("1占用");
                } else {
                    System.out.println("2占用");
                }
            } else {
                System.out.println("该位置" + position.toString() + "未被占用");
            }
        } else {
            if (chessBoard1[px][py]) {
                System.out.print("该位置" + position.toString() + "已被棋手");
                if (chessBoard2[px][py]) {
                    System.out.println("1占用");
                } else {
                    System.out.println("2占用");
                }
            } else {
                System.out.println("该位置" + position.toString() + "未被占用");
            }
        }
    }
}
