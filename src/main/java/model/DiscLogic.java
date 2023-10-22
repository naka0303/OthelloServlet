package model;

public class DiscLogic {
	/**
     * コマ数算出
     */
    public void calcDiscNum(Disc disc, String[][] boardList) {
        int blackCnt = 0;
        int whiteCnt = 0;
        
        for (String[] row : boardList) {
            for (String column : row) {
                if (column == "B") {
                    blackCnt++;
                } else if (column == "W") {
                    whiteCnt++;
                }
            }
        }
        disc.setBlackNum(blackCnt);
        disc.setWhiteNum(whiteCnt);
    }
}
