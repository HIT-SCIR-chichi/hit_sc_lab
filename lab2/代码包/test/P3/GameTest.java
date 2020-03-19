package P3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class GameTest {

    // Test strategy for Game.checkPlacePiece
    // 坐标越界+不越界
    // 坐标位置已被占用+未被占用

    @Test void testCheckPlacePiece() {
        Game goGame = new Game(2);
        assertFalse(goGame.checkPlacePiece(-1, 19));// 测试坐标越界

        assertTrue(goGame.checkPlacePiece(1, 2));// 测试坐标不越界 +未被占用

        goGame.placePiece(1, 2, true);
        assertFalse(goGame.checkPlacePiece(1, 2));// 测试坐标已被占用
    }

    // Test strategy for Game.checkExtractPiece
    // 坐标越界+不越界
    // 坐标位置已被对手占用+未被对手占用
    @Test void testCheckExtractPiece() {
        Game goGame = new Game(2);
        assertFalse(goGame.checkExtractPiece(-1, 19, true));// 测试坐标越界
        assertFalse(goGame.checkExtractPiece(2, 3, true)); // 测试坐标不越界+未被对方占用
    }

    // Test strategy for Game.movePiece
    // 坐标越界+不越界
    // 坐标位置已被对手占用+未被对手占用
    // 目标位置已被占用+未被占用
    @Test void testMovePiece() {
        Game chessGame = new Game(1);
        assertTrue(chessGame.movePiece(1, 1, 2, 2, true));// 检查坐标不越界+坐标位置被自己占用+目标位置未被占用
        assertFalse(chessGame.movePiece(10, 0, 1, 1, true));// 检查坐标越界
        assertFalse(chessGame.movePiece(1, 0, 1, 1, false));// 检查要移动的棋子是对手棋子
        assertFalse(chessGame.movePiece(1, 0, 7, 1, true));// 检查要移动的坐标位置已被占用
    }

    // Test strategy for Game.eatPiece
    // 坐标越界+不越界
    // 起始棋子非己方+是己方
    // 目标棋子非敌方+是敌方
    @Test void testEatPiece() {
        Game chessGame = new Game(1);
        assertTrue(chessGame.eatPiece(0, 0, 7, 0, true));// 检查正确输入情况
        assertFalse(chessGame.eatPiece(0, 0, 6, 1, false));// 检查起始棋子非己方
        assertFalse(chessGame.eatPiece(0, 0, 10, 0, false));// 检查棋子坐标位置越界
        assertFalse(chessGame.eatPiece(0, 0, 1, 1, true));// 检查目标棋子非己方
    }

}
