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
     * 配置コマ行列番号
     */
    private String rowNoSet;
    private String columnNoSet;
    
    /**
     * 盤外判定フラグ
     * 
     */
    private boolean boardOutsideFlg = false;
    
    /**
     * 勝敗判定フラグ
     */
    private boolean judgeGameFinishFlg = false;

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
	
	public String getRowNoSet() {
		return rowNoSet;
	}
	
	public void setRowNoSet(String rowNoSet) {
		this.rowNoSet = rowNoSet;
	}
	
	public String getColumnNoSet() {
		return columnNoSet;
	}
	
	public void setColumnNoSet(String columnNoSet) {
		this.columnNoSet = columnNoSet;
	}

	public boolean isBoardOutsideFlg() {
		return boardOutsideFlg;
	}

	public void setBoardOutsideFlg(boolean boardOutsideFlg) {
		this.boardOutsideFlg = boardOutsideFlg;
	}
	
	public boolean isJudgeGameFinishFlg() {
		return judgeGameFinishFlg;
	}

	public void setJudgeGameFinishFlg(boolean judgeGameFinishFlg) {
		this.judgeGameFinishFlg = judgeGameFinishFlg;
	}
}
