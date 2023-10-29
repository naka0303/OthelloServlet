package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Board;
import model.BoardLogic;
import model.Disc;
import model.DiscLogic;
import model.Player;
import model.PlayerLogic;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log("== MainServlet ==");
		HttpSession session = request.getSession();
		Disc disc = (Disc) session.getAttribute("disc");
		DiscLogic discLogic = (DiscLogic) session.getAttribute("discLogic");
		Board board = (Board) session.getAttribute("board");
		BoardLogic boardLogic = (BoardLogic) session.getAttribute("boardLogic");
		Player player1 = (Player) session.getAttribute("player1");
		Player player2 = (Player) session.getAttribute("player2");
		PlayerLogic playerLogic = (PlayerLogic) session.getAttribute("playerLogic");
		
		// セッション保持判定
		String refererUrl = request.getHeader("REFERER");
		if (refererUrl.contains("ready")) {
			if (disc == null
				|| discLogic == null
				|| board == null
				|| boardLogic == null
				|| playerLogic == null) {
					disc = new Disc();
					discLogic = new DiscLogic();
					board = new Board();
					boardLogic = new BoardLogic();
					playerLogic = new PlayerLogic();
			}
		} else if (refererUrl.contains("main")) {
			if (disc == null
				|| discLogic == null
				|| boardLogic == null
				|| playerLogic == null) {
					disc = new Disc();
					discLogic = new DiscLogic();
					boardLogic = new BoardLogic();
					playerLogic = new PlayerLogic();
			}
		}
		
		// main.jspから受け取るデータを"UTF8"に変更
		request.setCharacterEncoding("UTF8");
		
		// 画像ファイルパスセッション格納
		String blackDiscImgPath = "/img/black_disc.jpeg";
		String whiteDiscImgPath = "/img/white_disc.jpeg";
		session.setAttribute("blackDiscImgPath", blackDiscImgPath);
		session.setAttribute("whiteDiscImgPath", whiteDiscImgPath);
		
		// パス判定
		if (request.getParameter("pass") != null) {
			log("pass-btn selected");
			
			// プレイヤーのターン切り替え
			playerLogic.switchTurn(player1, player2);
			log("player1Turn:" + player1.isTurn() + " " + "player2Turn:" + player2.isTurn());
			
			// プレイヤーインスタンスセッション格納
			session.setAttribute("player1", player1);
			session.setAttribute("player2", player2);
			
			// main.jspへ遷移
			log("forward to main.jsp");
         	RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
         	rd.forward(request, response);
			
			return;
		}
		
		// 他コマ方向格納リスト初期化
		boardLogic.initializeAllOtherDiscPos(board);
		
		// ready.jspから遷移してきた場合
		if (refererUrl.contains("ready")) {
			log("from ready.jsp");
			
			// プレイヤー1のターンに変更
			player1.setTurn(true);
			
			// 盤面初期状態(1回のみ実行)
		    boardLogic.initialize(board);
		    
		    // コマ数算出
	        String[][] boardList = board.getBoardList();
	        discLogic.calcDiscNum(disc, boardList);
		    
		    // 盤面インスタンス/プレイヤーインスタンス/リトライフラグをセッション格納
		    session.setAttribute("disc", disc);
	        session.setAttribute("board", board);
	        session.setAttribute("player1", player1);
	        session.setAttribute("player2", player2);
	        
	        // main.jspへ遷移
	        log("forward to main.jsp");
	     	RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
	     	rd.forward(request, response);
	     	
	     	return;
		}
	     	
	    // main.jspから遷移してきた場合
		log("from main.jsp");
		
		// プレイヤーのターン状況取得
		boolean player1Turn = player1.isTurn();
		boolean player2Turn = player2.isTurn();
	    
	    Player playerTurned;
	    if (player1Turn) {
	    	playerTurned = player1;
	    } else {
	        playerTurned = player2;
	    }
	    log("player1Turn:" + player1Turn + " " + "player2Turn:" + player2Turn);
	    
	    // main.jspでクリックされたマスの列番号/行番号を取得
	 	String setRowColumnNo = request.getParameter("board");
	 	if (setRowColumnNo != null) {
	 		String[] setRowColumnNoSplitted = setRowColumnNo.split(",");
	 		
	 		board.setRowNoSet(setRowColumnNoSplitted[0]);
	 		board.setColumnNoSet(setRowColumnNoSplitted[1]);
	 	}
	    
	    // main.jspで入力された行列番号をint型にキャスト
		Integer rowNoSet2Int = Integer.parseInt(board.getRowNoSet());
		Integer columnNoSet2Int = Integer.parseInt(board.getColumnNoSet());
	    log("rowNoSet2Int:" + rowNoSet2Int);
	    log("columnNoSet2Int:" + columnNoSet2Int);
	    
	    // 反転対象行列番号リスト初期化
	    ArrayList<ArrayList<ArrayList<Integer>>> allOtherDiscColorRowNoColumnNo = new ArrayList<>();
	    
	    // 自コマ色/他コマ色取得
	    String discColor = playerTurned.getDiscColor();
	    String otherDiscColor = boardLogic.getOtherDiscColor(discColor);
	        
	    // 他コマ隣接チェック
	    boolean isNextToOtherDiscFlg = boardLogic.isNextToOtherDisc(board, rowNoSet2Int, columnNoSet2Int, otherDiscColor);
	        
	    // 他コマが隣接していない場合はmain.jspへ遷移
	    if (!isNextToOtherDiscFlg) {
	    	log("cannot set disc.");
	        
	        // main.jspへ遷移
	    	log("forward to main.jsp");
	        RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
	        rd.forward(request, response);
	            
	        return;
	    }
	     
	    // 他コマ方向格納リスト取得
	    ArrayList<Integer> allTargetDiscPos = new ArrayList<>();
	    allTargetDiscPos = boardLogic.getAllOtherDiscPos(board);
	    log("allTargetDiscPos:" + allTargetDiscPos);
	        
	    // 他コマを検出した数分ループ        
	    for (int targetDiscPos : allTargetDiscPos) {
	    	// フラグ初期化
	        boardLogic.initializeFlg(board);
	
	        // 他コマ行列番号リスト
	        ArrayList<Integer> otherDiscColorRowNoColumnNo = new ArrayList<>();
	        // 全他コマ行列番号リスト
	        ArrayList<ArrayList<Integer>> otherDiscColorRowNoColumnNoAsPos = new ArrayList<>();
	            
	        int noCounter = 1;
	        boolean selfDiscFlg = false;
	        boolean emptyFlg = false;
	        boolean boardOutsideFlg = false;
	        while (!boardLogic.getSelfDiscFlg(board)) {
	            // 他コマ行列番号リスト取得
	            otherDiscColorRowNoColumnNo = boardLogic.addOtherDiscRowNoColumnNo(board, rowNoSet2Int, columnNoSet2Int, targetDiscPos, otherDiscColor, discColor, noCounter);
	                
	            // 全他コマ行列番号リストに、他コマ行列番号リストを格納
	            otherDiscColorRowNoColumnNoAsPos.add(otherDiscColorRowNoColumnNo);
	                
	            selfDiscFlg = boardLogic.getSelfDiscFlg(board);
	            emptyFlg = boardLogic.getEmptyFlg(board);
	            boardOutsideFlg = boardLogic.getBoardOutsideFlg(board);
	
	            // 自コマを検出したら探索終了
	            if (selfDiscFlg) {
	                break;
	            }
	
	            // 探索先にコマが配置されていないか、探索先が盤外だったら、全他コマ行列番号リストを初期化し探索終了
	            if (emptyFlg || boardOutsideFlg) {
	            	ArrayList<ArrayList<Integer>> clearVal = new ArrayList<>();
	                otherDiscColorRowNoColumnNoAsPos = clearVal;
	                break;
	            }
	            noCounter++;
	        }
	            
	        if(!selfDiscFlg) {
	            continue;
	        }
	        
	        // 反転対象行列番号リストに、全他コマ行列番号リストを追加
	        allOtherDiscColorRowNoColumnNo.add(otherDiscColorRowNoColumnNoAsPos);
	    }
	        
	    log("allOtherDiscColorRowNoColumnNo:" + allOtherDiscColorRowNoColumnNo);
	    
	    // 探索の結果、引っくり返せるコマが無かったらmain.jspへ遷移
	    if (allOtherDiscColorRowNoColumnNo.size() == 0) {
	        log("cannot set disc.");
	            
	        // main.jspへ遷移
	        log("forward to main.jsp");
	        RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
	        rd.forward(request, response);
	        
	        return;
	    }
	        
	    // コマ配置(他コマをひっくり返す)
	    for (ArrayList<ArrayList<Integer>> otherDiscColorRowNoColumnNo : allOtherDiscColorRowNoColumnNo) {
	        for (ArrayList<Integer> rowNoColumnNo : otherDiscColorRowNoColumnNo) {
	            if (rowNoColumnNo.size() != 0) {
	                int otherDiscColorRowNo = rowNoColumnNo.get(0);
	                int otherDiscColorColumnNo = rowNoColumnNo.get(1);
	                    
	                boardLogic.setDisc(board, rowNoSet2Int, columnNoSet2Int, discColor);
	                boardLogic.setDisc(board, otherDiscColorRowNo, otherDiscColorColumnNo, discColor);
	            }
	        }
	    }
	        
	    // コマ数算出
	    String[][] boardList = board.getBoardList();
	    discLogic.calcDiscNum(disc, boardList);
	        
	    // オセロ盤面表示
	    Integer blackNum = disc.getBlackNum();
	    Integer whiteNum = disc.getWhiteNum();
	    log("blackNum:" + blackNum + " " + "whiteNum:" + whiteNum);
	    boardLogic.display(board, blackNum, whiteNum);
	     	        
	    // プレイヤーのターン切り替え
	    playerLogic.switchTurn(player1, player2);
	    log("player1Turn:" + player1.isTurn() + " " + "player2Turn:" + player2.isTurn());
			
		// 勝敗判定
		boolean judgeGameFinishFlg = boardLogic.judgeGameFinish(blackNum, whiteNum);
		if (judgeGameFinishFlg) {
			if (whiteNum > blackNum) {
				log("Winner is" + " " + player1.getPlayerName() + "!!");
				player1.setResultFlg(true);
			} else {
				log("Winner is" + " " + player2.getPlayerName() + "!!");
				player2.setResultFlg(true);
			}
		}
		
		// 盤面インスタンス/コマインスタンス/プレイヤーインスタンス/リトライフラグをセッション格納
		session.setAttribute("board", board);
		session.setAttribute("disc", disc);
		session.setAttribute("player1", player1);
		session.setAttribute("player2", player2);
		
		// main.jspへ遷移
		log("forward to main.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
		rd.forward(request, response);
	}
}
