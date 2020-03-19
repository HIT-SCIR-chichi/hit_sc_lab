package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private Map<String, String> map = new HashMap<String, String>();
    private static Board goBoard = new Board(2);// 围棋棋盘
    private static Board chessBoard = new Board(1);// 象棋棋盘
    private Player player1;// 选手1
    private Player player2;// 选手2
    private static List<Piece> allPieces1 = new ArrayList<>();// 玩家一的所有棋子
    private static List<Piece> allPieces2 = new ArrayList<>();// 玩家二的所有棋子
    private String player1History = "";// 玩家一的游戏历史
    private String player2History = "";// 玩家二的游戏历史

    /**
     * 如果是象棋，则添加初始化的32个棋子
     * 
     * @param gameTye gameType == 1代表象棋
     */
    public Game(int gameTye) {
        if (gameTye == 1) {// 若是象棋，则初始化游戏，加入32个初始的棋子
            allPieces1.add(new Piece(0, 0, "Rook", true));
            allPieces1.add(new Piece(0, 1, "Knight", true));
            allPieces1.add(new Piece(0, 2, "Bishop", true));
            allPieces1.add(new Piece(0, 3, "Queen", true));
            allPieces1.add(new Piece(0, 4, "King", true));
            allPieces1.add(new Piece(0, 5, "Bishop", true));
            allPieces1.add(new Piece(0, 6, "Knight", true));
            allPieces1.add(new Piece(0, 7, "Rook", true));
            map.put("Rook", "车");
            map.put("Knight", "马");
            map.put("Bishop", "象");
            map.put("King", "王");
            map.put("Queen", "后");
            map.put("Pawn", "卒");

            for (int i = 0; i < 8; i++) {
                allPieces1.add(new Piece(1, i, "Pawn", true));
                allPieces2.add(new Piece(6, i, "Pawn", false));
            }

            allPieces2.add(new Piece(7, 0, "Rook", false));
            allPieces2.add(new Piece(7, 1, "Knight", false));
            allPieces2.add(new Piece(7, 2, "Bishop", false));
            allPieces2.add(new Piece(7, 3, "Queen", false));
            allPieces2.add(new Piece(7, 4, "King", false));
            allPieces2.add(new Piece(7, 5, "Bishop", false));
            allPieces2.add(new Piece(7, 6, "Knight", false));
            allPieces2.add(new Piece(7, 7, "Rook", false));
        }
    }

    /**
     * 输出围棋菜单
     */
    public void showGoMenu() {
        System.out.println("输入数字选择要实现的功能");
        System.out.println(
                "********************************************************************************************");
        System.out.println("1:落子\t2:提子\t3:跳过\t4:结束\t5:棋盘\t6:历史\t7:查询占用  8:查询棋子总数");
        System.out.println(
                "********************************************************************************************");
    }

    /**
     * 输出象棋菜单
     */
    public void showChessMenu() {
        System.out.println("输入数字选择要实现的功能");
        System.out.println(
                "********************************************************************************************");
        System.out.println("1:移子\t2:吃子\t3:跳过\t4:结束\t5:棋盘\t6:历史\t7:查询占用  8:查询棋子总数");
        System.out.println(
                "********************************************************************************************");
    }

    /**
     * 在围棋棋盘上落子
     * 
     * @param px     落子的横坐标
     * @param py     落子的纵坐标
     * @param player 执行落子的选手，true表示棋手一
     * @return 若返回true，则表示坐标位置适合落子；否则表示非法坐标
     */
    public boolean placePiece(int px, int py, Boolean player) {
        if (!checkPlacePiece(px, py)) {// 若为非法坐标，则直接返回false
            return false;
        }
        Piece piece = new Piece(px, py, "goPiece", player);
        if (player) {// true代表玩家一
            player1History += "落子:" + "(" + px + "," + py + ") ";
            allPieces1.add(piece);
        } else {// false代表玩家二
            player2History += "落子:" + "(" + px + "," + py + ") ";
            allPieces2.add(piece);
        }
        goBoard.setGoBoard(piece);// 将此棋子表示在棋盘上，相应的坐标标记为已被占用
        return true;
    }

    /**
     * 检查待落子坐标是否合法
     * 
     * @param px 待落子的横坐标
     * @param py 待落子的纵坐标
     * @return 返回值为true，表示坐标合法；否则坐标非法
     */
    public Boolean checkPlacePiece(int px, int py) {
        Position position = new Position();
        position.setPosition(px, py);
        if (position.checkPosition(2)) {// 检查坐标是否满足围棋的不超出棋盘范围的要求
            if (!goBoard.getOccupation(position, 2)) {// 检查是否有占用情况
                return true;
            }
            System.out.println("坐标非法：当前坐标位置已有棋子！");
            return false;
        }
        System.out.println("坐标非法：超出棋盘范围！");
        return false;
    }

    /**
     * 在围棋棋盘上提子
     * 
     * @param px     待提子的横坐标
     * @param py     待提子的纵坐标
     * @param player true表示为棋手一，否则为棋手二
     * @return 返回true表示为提子坐标合法，否则不合法
     */
    public boolean extractPiece(int px, int py, Boolean player) {
        if (!checkExtractPiece(px, py, player)) {// 若坐标非法，则返回false
            return false;
        }
        Piece piece = new Piece(px, py, "goPiece", player);
        if (player) {
            player1History += "提子:" + "(" + px + "," + py + ") ";
            for (int i = 0; i < allPieces2.size(); i++) {// 提子要删掉原来的棋子
                if (allPieces2.get(i).getPiecePosition().getPx() == px
                        && allPieces2.get(i).getPiecePosition().getPy() == py) {
                    allPieces2.remove(i);
                    break;
                }
            }
        } else {
            player2History += "提子:" + "(" + px + "," + py + ") ";
            for (int i = 0; i < allPieces1.size(); i++) {// 提子要删掉原来的棋子
                if (allPieces1.get(i).getPiecePosition().getPx() == px
                        && allPieces1.get(i).getPiecePosition().getPy() == py) {
                    allPieces1.remove(i);
                    break;
                }
            }
        }
        goBoard.setGoBoard(piece);// 将待提子坐标位置标记为palyer占用
        return true;
    }

    /**
     * 检查待提子位置坐标是否合法
     * 
     * @param px     待提子横坐标
     * @param py     待提子纵坐标
     * @param player 操作的玩家
     * @return 返回为true，表示坐标合法，否则非法
     */
    public Boolean checkExtractPiece(int px, int py, Boolean player) {
        Position position = new Position();
        position.setPosition(px, py);
        if (position.checkPosition(2)) {// 检查坐标位置是否未超过象棋棋盘范围
            if (goBoard.getOccupation(position, 2)) {// 检查目标位置是否被占用
                if (goBoard.getOccupationPlayer(position, 2) != player) {// 检查目标位置是否被对手玩家占用
                    return true;
                }
                System.out.println("坐标非法：当前坐标位置棋子被本人占用！");
                return false;
            }
            System.out.println("坐标非法：当前坐标位置未被占用！");
            return false;
        }
        System.out.println("坐标非法：坐标超出棋盘范围！");
        return false;
    }

    /**
     * 象棋移子
     * 
     * @param pxOld  棋子的原坐标的横坐标
     * @param pyOld  棋子原坐标的纵坐标
     * @param pxNew  棋子新坐标的横坐标
     * @param pyNew  棋子新坐标的纵坐标
     * @param player 操作玩家
     * @return 返回为true，表示坐标合法；否则非法
     */
    public Boolean movePiece(int pxOld, int pyOld, int pxNew, int pyNew, boolean player) {
        Action checkAction = new Action();
        if (!checkAction.checkMovePiece(pxOld, pyOld, pxNew, pyNew, player, chessBoard)) {
            return false;// 检查坐标是否合法
        }
        if (player) {
            player1History += "移子:(" + pxOld + "," + pyOld + ")" + "->(" + pxNew + "," + pxOld + ") ";

        } else {
            player2History += "移子:(" + pxOld + "," + pyOld + ")" + "->(" + pxNew + "," + pxOld + ") ";
        }
        Position position = new Position();
        position.setPosition(pxOld, pyOld);
        Piece piece = getPieceByPosition(position);// 得到待操作的piece
        chessBoard.deleteChessBoardPiece(piece);// 将棋盘上的印记标记为自然状态
        piece.setPiecePosition(pxNew, pyNew);// 将该棋子更新状态
        chessBoard.addChessBoradPiece(piece);// 将棋盘上的印记标记为更新状态
        return true;
    }

    /**
     * 象棋吃子
     * 
     * @param pxOld  棋子的原坐标的横坐标
     * @param pyOld  棋子原坐标的纵坐标
     * @param pxNew  棋子新坐标的横坐标
     * @param pyNew  棋子新坐标的纵坐标
     * @param player 操作玩家
     * @return 返回为true，表示坐标合法；否则非法
     */
    public Boolean eatPiece(int pxOld, int pyOld, int pxNew, int pyNew, boolean player) {
        Action checkAction = new Action();
        if (!checkAction.checkEatPiece(pxOld, pyOld, pxNew, pyNew, player, chessBoard)) {
            return false;// 检查吃子坐标是否合理
        }
        Position position = new Position();
        position.setPosition(pxNew, pyNew);
        Piece piece = getPieceByPosition(position);// 得到待操作的piece
        if (player) {
            player1History += "吃子:(" + pxOld + "," + pyOld + ")" + "->(" + pxNew + "," + pxOld + ") ";
            allPieces2.remove(piece);
        } else {
            player2History += "吃子:(" + pxOld + "," + pyOld + ")" + "->(" + pxNew + "," + pxOld + ") ";
            allPieces1.remove(piece);
        }
        chessBoard.deleteChessBoardPiece(piece);// 将棋盘上的印记标记为自然状态
        piece.setPlayer(player);
        chessBoard.addChessBoradPiece(piece);// 将棋盘上的印记标记为更新状态
        position.setPosition(pxOld, pyOld);
        piece = getPieceByPosition(position);// 得到待操作的piece
        chessBoard.deleteChessBoardPiece(piece);// 将棋盘上起始位置的状态标记为自然状态
        piece.setPiecePosition(pxNew, pyNew);
        return true;
    }

    /**
     * function:输出棋手下棋历史纪录
     * 
     * @param player 待输出历史纪录的棋手
     */
    public void showHistory(Boolean player) {
        if (player) {
            System.out.println(player1History);
        } else {
            System.out.println(player2History);
        }
    }

    /**
     * 设置玩家一姓名
     * 
     * @param string
     */
    public void setPlayer1(String string) {
        player1 = new Player(string);
    }

    /**
     * 得到玩家一姓名
     * 
     * @return 返回玩家一姓名
     */
    public String getPlayer1() {
        return player1.getPlayerName();
    }

    /**
     * 设置玩家二姓名
     * 
     * @param string 玩家的姓名
     */
    public void setPlayer2(String string) {
        player2 = new Player(string);
    }

    /**
     * 得到玩家二的姓名
     * 
     * @return 返回玩家二的姓名
     */
    public String getPlayer2() {
        return player2.getPlayerName();
    }

    /**
     * 得到棋盘
     * 
     * @param gameType == 1代表输出象棋棋盘，否则为围棋棋盘
     * @return 返回值为棋盘Board
     */
    public Board getBoard(int gameType) {
        if (gameType == 1) {
            return chessBoard;
        } else {
            return goBoard;
        }
    }

    /**
     * 得到棋子数目
     * 
     * @param player 玩家1或2
     * @return 返回玩家1或2的棋子数目
     */
    public int getPiecesSize(Boolean player) {
        if (player) {
            return allPieces1.size();
        }
        return allPieces2.size();

    }

    /**
     * 根据Position得到相应位置的棋子
     * 
     * @param position 位置类
     * @return 返回Position对应的棋子
     */
    public Piece getPieceByPosition(Position position) {
        int size1 = getPiecesSize(true);
        int size2 = getPiecesSize(false);
        for (int i = 0; i < size1; i++) {// 遍历所有的棋子，找到符合位置坐标的
            if (allPieces1.get(i).getPiecePosition().positionEqual(position)) {
                return allPieces1.get(i);
            }
        }
        for (int i = 0; i < size2; i++) {
            if (allPieces2.get(i).getPiecePosition().positionEqual(position)) {
                return allPieces2.get(i);
            }
        }
        return null;
    }

    /**
     * 改变玩家历史
     * 
     * @param string 在玩家历史上加上string
     * @param player 待改变的玩家历史，true代表改变玩家一的历史
     */
    public void changeHistory(String string, Boolean player) {
        if (player) {
            player1History += string;
        } else {
            player2History += string;
        }
    }

    /**
     * 输出棋盘
     * 
     * @param gameType gameType == 1代表输出象棋棋盘，否则输出围棋棋盘
     */
    public void printBoard(int gameType) {
        if (gameType == 1) {// 输出象棋棋盘
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Position position = new Position();
                    position.setPosition(i, j);
                    if (chessBoard.getOccupation(position, gameType)) {
                        if (chessBoard.getOccupationPlayer(position, gameType)) {
                            System.out.print("1:" + map.get(this.getPieceByPosition(position).getPieceName()) + "\t");
                        } else {
                            System.out.print("2:" + map.get(this.getPieceByPosition(position).getPieceName()) + "\t");
                        }
                    } else {
                        System.out.print(" 空 \t");
                    }
                }
                System.out.println();
            }
            System.out.println("Player1:" + this.getPlayer1() + "\tPlayer2:" + this.getPlayer2());
        } else {// 输出围棋棋盘
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {
                    Position position = new Position();
                    position.setPosition(i, j);
                    if (goBoard.getOccupation(position, gameType)) {
                        if (goBoard.getOccupationPlayer(position, gameType)) {
                            System.out.print("黑 ");
                        } else {
                            System.out.print("白 ");
                        }
                    } else {
                        System.out.print("空 ");
                    }
                }
                System.out.println();
            }
            System.out.println("Player1:" + this.getPlayer1() + "\tPlayer2:" + this.getPlayer2());
        }
    }
}
