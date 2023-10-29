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
    private boolean resultFlg;

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

	public boolean isResultFlg() {
		return resultFlg;
	}

	public void setResultFlg(boolean resultFlg) {
		this.resultFlg = resultFlg;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
}
