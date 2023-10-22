package model;

import java.util.ArrayList;

public class Board {
	/**
     * オセロ盤面 8列 * 8行
     */
    private String[][] boardList;

    /**
     * 探索先コマ行番号/列番号/方向
     */
    private int seekDiscRowNo = -1;
    private int seekDiscColumnNo = -1;
    private int seekDiscPos = -1;

    /**
     * 他コマ方向格納リスト
     */
    private ArrayList<Integer> allOtherDiscPos = new ArrayList<>();

    /**
     * 自コマ判定フラグ
     */
    private boolean selfDiscFlg = false;

    /**
     * コマ配置判定フラグ
     */
    private boolean emptyFlg = false;

    /**
     * 盤外判定フラグ
     * 
     */
    private boolean boardOutsideFlg = false;

    // getter/setter
	public String[][] getBoardList() {
		return boardList;
	}

	public void setBoardList(String[][] boardList) {
		this.boardList = boardList;
	}

	public int getSeekDiscRowNo() {
		return seekDiscRowNo;
	}

	public void setSeekDiscRowNo(int seekDiscRowNo) {
		this.seekDiscRowNo = seekDiscRowNo;
	}

	public int getSeekDiscColumnNo() {
		return seekDiscColumnNo;
	}

	public void setSeekDiscColumnNo(int seekDiscColumnNo) {
		this.seekDiscColumnNo = seekDiscColumnNo;
	}

	public int getSeekDiscPos() {
		return seekDiscPos;
	}

	public void setSeekDiscPos(int seekDiscPos) {
		this.seekDiscPos = seekDiscPos;
	}

	public ArrayList<Integer> getAllOtherDiscPos() {
		return allOtherDiscPos;
	}

	public void setAllOtherDiscPos(ArrayList<Integer> allOtherDiscPos) {
		this.allOtherDiscPos = allOtherDiscPos;
	}

	public boolean isSelfDiscFlg() {
		return selfDiscFlg;
	}

	public void setSelfDiscFlg(boolean selfDiscFlg) {
		this.selfDiscFlg = selfDiscFlg;
	}

	public boolean isEmptyFlg() {
		return emptyFlg;
	}

	public void setEmptyFlg(boolean emptyFlg) {
		this.emptyFlg = emptyFlg;
	}

	public boolean isBoardOutsideFlg() {
		return boardOutsideFlg;
	}

	public void setBoardOutsideFlg(boolean boardOutsideFlg) {
		this.boardOutsideFlg = boardOutsideFlg;
	}
}