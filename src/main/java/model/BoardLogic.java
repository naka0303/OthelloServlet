package model;

import java.util.ArrayList;

public class BoardLogic {
	/**
     * 他コマ位置特定用enum
     */
    private enum discPosEnum {
        TOP,
        TOPRIGHT,
        TOPLEFT,
        BOTTOM,
        BOTTOMRIGHT,
        BOTTOMLEFT,
        RIGHT,
        LEFT
    };
    
	/** 初期状態(1回のみ実行)
     * 
     */
    public void initialize(Board board) {
        // 盤面行列リスト
    	board.setBoardList(new String[9][9]);
    	
        // 盤面の全ての行列に"-"を値として格納
    	String[][] boardList = board.getBoardList();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                boardList[i][j] = "-";
            }
        }
        
        board.setBoardList(boardList);

        // コマ配置
        setDisc(board, 4, 4, "B");
        setDisc(board, 4, 5, "W");
        setDisc(board, 5, 4, "W");
        setDisc(board, 5, 5, "B");

        // コンソールに盤面表示
        display(board, 2, 2);
    }

    /**
     * フラグ初期化
     */
    public void initializeFlg(Board board) {
    	board.setSelfDiscFlg(false);
        board.setEmptyFlg(false);
        board.setBoardOutsideFlg(false);
    }

    /** コンソールに盤面表示
     * 
     */
    public void display(Board board, Integer blackNum, Integer whiteNum) {
    	String[][] boardList = board.getBoardList();
    	
        System.out.println("黒コマ数：" + blackNum + " " + "白コマ数：" + whiteNum);
        System.out.println("1 2 3 4 5 6 7 8");
        int cnt = 1;
        for (String[] disc : boardList) {
            for (String d : disc) {
                if (d != null) {
                    System.out.print(d + " ");
                }
            }
            if (cnt - 1 >= 1) {
                System.out.print(cnt - 1);
            }
            System.out.println();
            cnt++;
        }
    }

    /** コマ配置
     * 
     * @param rowNo
     * @param columnNo
     * @param discColor
     */
    public void setDisc(Board board, int rowNo, int columnNo, String discColor) {
    	String[][] boardList = board.getBoardList();
    	
        boardList[rowNo][columnNo] = discColor;
        
        board.setBoardList(boardList);
    }

    /**
     * コマ配置可否チェック
     *
     * @param rowNo
     * @param columnNo
     */
    public boolean isSetDisc(String[][] boardList, int rowNo, int columnNo) {
        if (!boardList[rowNo][columnNo].equals("-")) {
            return false;
        }

        return true;
    }

    /**
     * 他隣接チェック
     * 
     * @param rowNo
     * @param columnNo
     * @param otherDisc
     */
    public boolean isNextToOtherDisc(Board board, int rowNo, int columnNo, String otherDisc) {
    	String[][] boardList = board.getBoardList();
    	ArrayList<Integer> allOtherDiscPos = board.getAllOtherDiscPos();
    	
        // 上に他コマがあるかチェック
        if ((1 <= rowNo-1 && rowNo-1 <= 8) && (1 <= columnNo && columnNo <= 8)
             && boardList[rowNo-1][columnNo].equals(otherDisc)) {
        	    allOtherDiscPos.add(discPosEnum.TOP.ordinal());
        }

        // 右上に他コマがあるかチェック
        if ((1 <= rowNo-1 && rowNo-1 <= 8) && (1 <= columnNo+1 && columnNo+1 <= 8)
             && boardList[rowNo-1][columnNo+1].equals(otherDisc)) {
                allOtherDiscPos.add(discPosEnum.TOPRIGHT.ordinal());
        }

        // 左上に他コマがあるかチェック
        if ((1 <= rowNo-1 && rowNo-1 <= 8) && (1 <= columnNo-1 && columnNo-1 <= 8)
             && boardList[rowNo-1][columnNo-1].equals(otherDisc)) {
                allOtherDiscPos.add(discPosEnum.TOPLEFT.ordinal());
        }

        // 下に他コマがあるかチェック
        if ((1 <= rowNo+1 && rowNo+1 <= 8) && (1 <= columnNo && columnNo <= 8)
             && boardList[rowNo+1][columnNo].equals(otherDisc)) {
                allOtherDiscPos.add(discPosEnum.BOTTOM.ordinal());
        }

        // 右下に他コマがあるかチェック
        if ((1 <= rowNo+1 && rowNo+1 <= 8) && (1 <= columnNo+1 && columnNo+1 <= 8)
             && boardList[rowNo+1][columnNo+1].equals(otherDisc)) {
                allOtherDiscPos.add(discPosEnum.BOTTOMRIGHT.ordinal());
        }

        // 左下に他コマがあるかチェック
        if ((1 <= rowNo+1 && rowNo+1 <= 8) && (1 <= columnNo-1 && columnNo-1 <= 8)
             && boardList[rowNo+1][columnNo-1].equals(otherDisc)) {
                allOtherDiscPos.add(discPosEnum.BOTTOMLEFT.ordinal());
        }

        // 右に他コマがあるかチェック
        if ((1 <= rowNo && rowNo <= 8) && (1 <= columnNo+1 && columnNo+1 <= 8)
             && boardList[rowNo][columnNo+1].equals(otherDisc)) {
                allOtherDiscPos.add(discPosEnum.RIGHT.ordinal());
        }

        // 左に他コマがあるかチェック
        if ((1 <= rowNo && rowNo <= 8) && (1 <= columnNo-1 && columnNo-1 <= 8)
             && boardList[rowNo][columnNo-1].equals(otherDisc)) {
                allOtherDiscPos.add(discPosEnum.LEFT.ordinal());
        }

        // 他コマが隣接していなければそのままリターン
        if (allOtherDiscPos.size() == 0) {
            return false;
        }

        return true;
    }

    /**
     * 他コマ探索
     * 
     * @param seekDiscRowNo
     * @param seekDiscColumnNo
     * @param otherDisc
     * @param selfDisc
     */
    private void seekOtherDisc(Board board, int seekDiscRowNo, int seekDiscColumnNo, String otherDisc, String selfDisc) {
    	String[][] boardList = board.getBoardList();
    	
    	if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
    		board.setSelfDiscFlg(false);
            board.setEmptyFlg(false);
            board.setBoardOutsideFlg(false);
        } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
        	board.setSelfDiscFlg(true);
            board.setEmptyFlg(false);
            board.setBoardOutsideFlg(false);
        } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
        	board.setSelfDiscFlg(false);
            board.setEmptyFlg(true);
            board.setBoardOutsideFlg(false);
        } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                   || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
        	board.setSelfDiscFlg(false);
            board.setEmptyFlg(false);
            board.setBoardOutsideFlg(true);
        }
    }

    /**
     * 他コマ行列番号リスト追加
     * 
     * @param rowNo
     * @param columnNo
     * @param otherDiscPos
     * @param otherDisc
     * @param selfDisc
     * @param noCounter
     */
    public ArrayList<Integer> addOtherDiscRowNoColumnNo(Board board, int rowNo, int columnNo, int otherDiscPos, String otherDisc, String selfDisc, int noCounter) {
        ArrayList<Integer> otherDiscRowNoColumnNo = new ArrayList<>();

        // 上に他コマがある場合
        if (otherDiscPos == discPosEnum.TOP.ordinal()) {
        	board.setSeekDiscRowNo(rowNo-noCounter);
        	board.setSeekDiscColumnNo(columnNo);
        }

        // 右上に他コマがある
        if (otherDiscPos == discPosEnum.TOPRIGHT.ordinal()) {
            board.setSeekDiscRowNo(rowNo-noCounter);
            board.setSeekDiscColumnNo(columnNo+noCounter);
        }

        // 左上に他コマがある場合
        if (otherDiscPos == discPosEnum.TOPLEFT.ordinal()) {
            board.setSeekDiscRowNo(rowNo-noCounter);
            board.setSeekDiscColumnNo(columnNo-noCounter);
        }

        // 下に他コマがある場合
        if (otherDiscPos == discPosEnum.BOTTOM.ordinal()) {
            board.setSeekDiscRowNo(rowNo+noCounter);
            board.setSeekDiscColumnNo(columnNo);
        }

        // 右下に他コマがある場合
        if (otherDiscPos == discPosEnum.BOTTOMRIGHT.ordinal()) {
            board.setSeekDiscRowNo(rowNo+noCounter);
            board.setSeekDiscColumnNo(columnNo+noCounter);
        }

        // 左下に他コマがある場合
        if (otherDiscPos == discPosEnum.BOTTOMLEFT.ordinal()) {
            board.setSeekDiscRowNo(rowNo+noCounter);
            board.setSeekDiscColumnNo(columnNo-noCounter);
        }

        // 右に他コマがある場合
        if (otherDiscPos == discPosEnum.RIGHT.ordinal()) {
            board.setSeekDiscRowNo(rowNo);
            board.setSeekDiscColumnNo(columnNo+noCounter);
        }

        // 左に他コマがある場合
        if (otherDiscPos == discPosEnum.LEFT.ordinal()) {
            board.setSeekDiscRowNo(rowNo);
            board.setSeekDiscColumnNo(columnNo-noCounter);
        }
        
        int seekDiscRowNo = board.getSeekDiscRowNo();
        int seekDiscColumnNo = board.getSeekDiscColumnNo();

        // 他コマ探索
        seekOtherDisc(board, seekDiscRowNo, seekDiscColumnNo, otherDisc, selfDisc);

        otherDiscRowNoColumnNo.add(seekDiscRowNo);
        otherDiscRowNoColumnNo.add(seekDiscColumnNo);
        
        return otherDiscRowNoColumnNo;
    }

    /*
     * 他コマ方向取得
     */
    public int getTargetDiscPos(Board board) {
    	return board.getSeekDiscPos();
    }

    /**
     * 他コマ行番号/列番号/方向のリスト返却
     */
    public ArrayList<Integer> getTargetDiscPosRowNoColumnNo(Board board) {
        ArrayList<Integer> targetDiscPosRowNoColumnNo = new ArrayList<>();
        int seekDiscPos = board.getSeekDiscPos();
        int seekDiscRowNo = board.getSeekDiscRowNo();
        int seekDiscColumnNo = board.getSeekDiscColumnNo();
        
        targetDiscPosRowNoColumnNo.add(seekDiscPos);
        targetDiscPosRowNoColumnNo.add(seekDiscRowNo);
        targetDiscPosRowNoColumnNo.add(seekDiscColumnNo);

        return targetDiscPosRowNoColumnNo;
    }

    /** 指定取得
     * 
     * @param discColor
     */
    public String getOtherDiscColor(String discColor) {
        // 指定を特定
        if (discColor.equals("B")) {
            return "W";
        } else {
            return "B";
        }
    }

    /**
     * 他コマ方向格納リスト取得
     * @return
     */
    public ArrayList<Integer> getAllOtherDiscPos(Board board) {
    	return board.getAllOtherDiscPos();
    }

    /**
     * 自コマ判定フラグ取得
     */
    public boolean getSelfDiscFlg(Board board) {
    	return board.isSelfDiscFlg();
    }

    /**
     * コマ配置判定フラグ
     */
    public boolean getEmptyFlg(Board board) {
        return board.isEmptyFlg();
    }

    /** 
     * 盤外判定フラグ
     */
    public boolean getBoardOutsideFlg(Board board) {
        return board.isBoardOutsideFlg();
    }

    public String[][] getBoardList(Board board) {
        return board.getBoardList();
    }

    /**
     * 他コマ方向格納リスト初期化
     */
    public void initializeAllOtherDiscPos(Board board) {
    	board.setAllOtherDiscPos(new ArrayList<>());
    }

    public boolean judgeGameFinish(Integer blackNum, Integer whiteNum) {
        // ゲーム終了判定
        if (blackNum + whiteNum != 64) {
            return false;
        }

        return true;
    }
}
