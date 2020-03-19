package P3;

public class Player {

    private String playName;// 玩家的姓名

    /**
     * 设置玩家姓名
     * 
     * @param playerName
     */
    public Player(String playerName) {
        this.playName = playerName; // 设置玩家姓名
    }

    /**
     * 返回玩家姓名
     * 
     * @return 玩家姓名
     */
    public String getPlayerName() {
        return this.playName;// 返回玩家的姓名
    }
}
