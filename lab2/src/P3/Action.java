package P3;

public class Action {

    /**
     * 检查移子的坐标是否合理
     * 
     * @param px         旧的横坐标
     * @param py         旧的纵坐标
     * @param pxNew      新的横坐标
     * @param pyNew      新的纵坐标1
     * @param player     判断玩家是谁
     * @param chessBoard 象棋棋盘
     * @return 若满足条件，则输出true；否则false
     */
    public boolean checkMovePiece(int px, int py, int pxNew, int pyNew, Boolean player, Board chessBoard) {
        Position oldPosition = new Position();
        oldPosition.setPosition(px, py);
        Position newPosition = new Position();
        newPosition.setPosition(pxNew, pyNew);
        if (oldPosition.checkPosition(1)) {// 判断坐标是否超出棋盘范围
            if (newPosition.checkPosition(1)) {// 判断坐标是否超出棋盘范围
                if (chessBoard.getOccupation(oldPosition, 1)) {// 判断原来坐标是否被占用
                    if (chessBoard.getOccupationPlayer(oldPosition, 1) == player) {// 判断原来做表是否被该选手占用
                        if (!chessBoard.getOccupation(newPosition, 1)) {// 判断目标坐标是否被占用
                            return true;
                        }
                        System.out.println("坐标非法：目标坐标位置已有棋子！");
                        return false;
                    }
                    System.out.println("坐标非法：当前坐标位置棋子非己方棋子！");
                    return false;
                }
                System.out.println("坐标非法：当前坐标位置无棋子！");
                return false;
            }
            System.out.println("坐标非法：当前坐标超出棋盘范围！");
            return false;
        }
        System.out.println("坐标非法：目标坐标超出棋盘范围！");
        return false;
    }

    /**
     * 检查吃子的坐标是否合理
     * 
     * @param px         旧的横坐标
     * @param py         旧的纵坐标
     * @param pxNew      新的横坐标
     * @param pyNew      新的纵坐标
     * @param player     判断玩家是谁
     * @param chessBoard 象棋棋盘
     * @return 若满足条件，则输出true；否则false
     */
    public Boolean checkEatPiece(int px, int py, int pxNew, int pyNew, Boolean player, Board chessBoard) {
        Position oldPosition = new Position();
        oldPosition.setPosition(px, py);
        Position newPosition = new Position();
        newPosition.setPosition(pxNew, pyNew);
        if (oldPosition.checkPosition(1)) {// 判断坐标是否超出棋盘范围
            if (newPosition.checkPosition(1)) {// 判断坐标是否超出棋盘范围
                if (chessBoard.getOccupation(oldPosition, 1)) {// 判断原来坐标是否被占用
                    if (chessBoard.getOccupationPlayer(oldPosition, 1) == player) {// 判断原来做表是否被该选手占用
                        if (chessBoard.getOccupation(newPosition, 1)) {// 判断目标坐标是否被占用
                            if (chessBoard.getOccupationPlayer(newPosition, 1) != player) {// 判断目标坐标被哪位选手占用
                                return true;
                            }
                            System.out.println("坐标非法：目标坐标位置非对方棋子！");
                            return false;
                        }
                        System.out.println("坐标非法：目标坐标位置无棋子！");
                        return false;
                    }
                    System.out.println("坐标非法：当前坐标位置棋子非己方棋子！");
                    return false;
                }
                System.out.println("坐标非法：当前坐标位置无棋子！");
                return false;
            }
            System.out.println("坐标非法：目标坐标超出棋盘范围！");
            return false;
        }
        System.out.println("坐标非法：当前坐标超出棋盘范围！");
        return false;
    }
}
