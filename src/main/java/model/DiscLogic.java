package model;

public class DiscLogic {
	/**
     * コマ数算出
     */
    public void calcDiscNum(Disc disc, String[][] boardList) {
        int blackCnt = 0;
        int whiteCnt = 0;
        for (String[] rowNoColumnNo : boardList) {
            for (String var : rowNoColumnNo) {
                if (var == "B") {
                    blackCnt++;
                } else if (var == "W") {
                    whiteCnt++;
                }
            }
        }
        disc.setBlackNum(blackCnt);
        disc.setWhiteNum(whiteCnt);
    }

}
