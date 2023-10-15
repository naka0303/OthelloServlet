package model;

public class Player {
    /** 
     * プレイヤー名
     */
    private String playerName;

    /**
     * コマ色
     */
    private String discColor;

    /**
     * パス判定
     */
    private boolean pass;

    /**
     * ターン判定
     */
    private boolean turn;

    // getter/setter
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getDiscColor() {
		return discColor;
	}

	public void setDiscColor(String discColor) {
		this.discColor = discColor;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
}
