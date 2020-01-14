package P3;

import java.util.Scanner;

public class MyChessAndGoGame {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int player = 0;// 用于判断玩家是谁
        System.out.println("请先选择游戏：chess或go");
        String gametype = in.nextLine();
        int input = 0;
        if (gametype.equals("chess")) {
            Game game = new Game(1);
            System.out.print("请输入棋手一的名字：");
            String player1 = in.nextLine();
            game.setPlayer1(player1);
            System.out.print("请输入棋手二的名字：");
            String player2 = in.nextLine();
            game.setPlayer2(player2);
            game.showChessMenu();
            input = in.nextInt();
            while (true) {// 不断读取玩家输入的功能选项，直到退出游戏
                switch (input) {
                case 1:
                    System.out.println("请输入移子坐标");
                    if (game.movePiece(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), player % 2 == 0)) {
                        player++;
                    }
                    game.printBoard(1);
                    break;
                case 2:
                    System.out.println("请输入吃子坐标");
                    if (game.eatPiece(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), player % 2 == 0)) {
                        player++;
                    }
                    game.printBoard(1);
                    break;
                case 3:
                    System.out.println("跳过该选手");
                    game.changeHistory("跳过 ", player % 2 == 0);
                    player++;
                    game.printBoard(1);
                    break;
                case 4:
                    in.close();
                    System.exit(0);
                case 5:
                    game.printBoard(1);
                    break;
                case 6:
                    System.out.println("请输入要选择的选手1或2");
                    game.showHistory(in.nextInt() == 1);
                    break;
                case 7:
                    System.out.println("请输入查询坐标");
                    Position position = new Position();
                    position.setPosition(in.nextInt(), in.nextInt());
                    game.getBoard(1).checkOccupation(position, 1);
                    break;
                case 8:
                    System.out.println("请输入要选择的选手1或2");
                    System.out.println(game.getPiecesSize(in.nextInt() == 1));
                    break;
                default:
                    System.out.println("请输入正确！");
                }
                game.showChessMenu();
                input = in.nextInt();
            }
        } else if (gametype.equals("go")) {
            Game game = new Game(2);
            System.out.print("请输入棋手一的名字：");
            String player1 = in.nextLine();
            game.setPlayer1(player1);
            System.out.print("请输入棋手二的名字：");
            String player2 = in.nextLine();
            game.setPlayer2(player2);
            game.showGoMenu();
            input = in.nextInt();
            while (true) {// 不断读取玩家输入的功能选项，直到退出游戏
                switch (input) {
                case 1:
                    System.out.println("请输入落子坐标");
                    if (game.placePiece(in.nextInt(), in.nextInt(), player % 2 == 0)) {
                        player++;
                    }
                    game.printBoard(2);
                    break;
                case 2:
                    System.out.println("请输入提子坐标");
                    if (game.extractPiece(in.nextInt(), in.nextInt(), player % 2 == 0)) {
                        player++;
                    }
                    game.printBoard(2);
                    break;
                case 3:
                    System.out.println("跳过该选手");
                    game.changeHistory("跳过 ", player % 2 == 0);
                    player++;
                    break;
                case 4:
                    in.close();
                    System.exit(0);
                case 5:
                    game.printBoard(2);
                    break;
                case 6:
                    System.out.println("请输入要选择的选手1或2");
                    game.showHistory(in.nextInt() == 1);
                    break;
                case 7:
                    System.out.println("请输入查询坐标");
                    Position position = new Position();
                    position.setPosition(in.nextInt(), in.nextInt());
                    game.getBoard(2).checkOccupation(position, 2);
                    break;
                case 8:
                    System.out.println("请输入要选择的选手1或2");
                    System.out.println(game.getPiecesSize(in.nextInt() == 1));
                    break;
                default:
                    System.out.println("请输入正确！");
                }
                game.showGoMenu();
                input = in.nextInt();
            }
        } else {
            System.out.println("Error input game!");
            in.close();
            System.exit(0);
        }
        in.close();
    }
}
