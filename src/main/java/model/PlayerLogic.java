package model;
import java.util.Scanner;

public class PlayerLogic {
	/** 初期設定(1回のみ実行)
     * 
     */
    public void initialize(Player player) {
    	Scanner scan = new Scanner(System.in);
    	// プレイヤー名/コマ色設定
        System.out.println("プレイヤーの名前を入力してください");
        String playerName = scan.nextLine();
        
        player.setPlayerName(playerName);
        player.setTurn(false);
    }
}
