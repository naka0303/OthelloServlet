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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		if (disc == null
			|| discLogic == null
			|| board == null
			|| boardLogic == null
			|| player1 == null
			|| player2 == null
			|| playerLogic == null) {
				disc = new Disc();
				discLogic = new DiscLogic();
				board = new Board();
				boardLogic = new BoardLogic();
				player1 = new Player();
				player2 = new Player();
				playerLogic = new PlayerLogic();
		}
		
		session.setAttribute("disc", disc);
		session.setAttribute("discLogic", discLogic);
		session.setAttribute("board", board);
		session.setAttribute("boardLogic", boardLogic);
		session.setAttribute("player1", player1);
		session.setAttribute("player2", player2);
		session.setAttribute("playerLogic", playerLogic);
				
		// main.jspからデータ受け取り
		request.setCharacterEncoding("UTF8");
		
		// main.jspで入力された行番号/列番号を取得し、セッション格納
		String setRowNo = request.getParameter("setRowNo");
		String setColumnNo = request.getParameter("setColumnNo");
		
		// 他コマ方向格納リスト初期化
		boardLogic.initializeAllOtherDiscPos(board);
		
		// ready.jspから遷移してきた場合とそれ以外で処理を変える
		if (setRowNo == null && setColumnNo == null) {
			log("from ready.jsp");
			
			// プレイヤー1のターンにし、プレイヤー名とコマ色をセッション格納
			player1.setTurn(true);
			session.setAttribute("player1Turn", player1.isTurn());
            session.setAttribute("playerName", (String) session.getAttribute("player1Name"));
            session.setAttribute("discColor", (String) session.getAttribute("player1Disc"));
            
            System.out.println("=====" + (String) session.getAttribute("playerName") + "さん(" + (String) session.getAttribute("discColor") + ")のターン！" + "=====");
			
			// 盤面初期状態(1回のみ実行)
		    boardLogic.initialize(board);
			
			// 初期盤面をセッション格納
	        session.setAttribute("boardList", board.getBoardList());
	        
	        // リトライ
	        session.setAttribute("retryFlg", true);
	        
	        // main.jspへ遷移
	        log("forward to main.jsp");
	     	RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
	     	rd.forward(request, response);
		} else {
			log("from main.jsp");
			
			// プレイヤーのターン状況取得
			boolean player1Turn = (Boolean) session.getAttribute("player1Turn");
	        boolean player2Turn = (Boolean) session.getAttribute("player2Turn");
	        
	        // プレイヤーのターン切り替え
	        log("retryFlg:" + (Boolean) session.getAttribute("retryFlg"));
			if (!(Boolean) session.getAttribute("retryFlg")) {
	            if (!player1Turn && !player2Turn) {
	                player1.setTurn(true);
	            } else if (player1Turn && !player2Turn) {
	                player1.setTurn(false);
	                player2.setTurn(true);
	            } else if (!player1Turn && player2Turn) {
	                player1.setTurn(true);
	                player2.setTurn(false);
	            }
	        }
			
			// 切り替え後のプレイヤーのターン状況をセッション格納
			log("player1Turn:" + player1.isTurn());
			log("player2Turn:" + player2.isTurn());
			session.setAttribute("player1Turn", player1.isTurn());
			session.setAttribute("player2Turn", player2.isTurn());
			
			// ターン中のプレイヤーの名前とコマを取得
	        if ((Boolean) session.getAttribute("player1Turn")) {
	        	session.setAttribute("playerName", (String) session.getAttribute("player1Name"));
	            session.setAttribute("discColor", (String) session.getAttribute("player1Disc"));
	        } else {
	        	session.setAttribute("playerName", (String) session.getAttribute("player2Name"));
	            session.setAttribute("discColor", (String) session.getAttribute("player2Disc"));
	        }
	        
	        System.out.println("=====" + (String) session.getAttribute("playerName") + "さん(" + (String) session.getAttribute("discColor") + ")のターン！" + "=====");
	        
	        // main.jspで入力された行列番号をint型にキャスト
	        log("setRowNo:" + setRowNo);
	        log("setColumnNo:" + setColumnNo);
	        session.setAttribute("setRowNo2int", Integer.parseInt(setRowNo));
			session.setAttribute("setColumnNo2int", Integer.parseInt(setColumnNo));
		
	        // 反転対象行列番号リスト初期化
	        ArrayList<ArrayList<ArrayList<Integer>>> allOtherDiscRowNoColumnNo = new ArrayList<>();
	        
	        // 他色取得
	        String otherDisc = boardLogic.getOtherDiscColor((String) session.getAttribute("discColor"));
	        
	        // コマ配置可否チェック
	        boolean isSetDiscFlg = boardLogic.isSetDisc(board, (int) session.getAttribute("setRowNo2int"), (int) session.getAttribute("setColumnNo2int"));
	        
	        // 指定したマスにコマが置いてある場合は探索終了
	        if (!isSetDiscFlg) {
	            System.out.println("指定したマスには既にコマが配置されています。");
	
	            session.setAttribute("retryFlg", true);
	        }
	        
	        // 他コマ隣接チェック
	        boolean isNextToOtherDiscFlg = boardLogic.isNextToOtherDisc(board, (int) session.getAttribute("setRowNo2int"), (int) session.getAttribute("setColumnNo2int"), otherDisc);
	        
	        // 他コマが隣接していない場合は探索終了
	        if (!isNextToOtherDiscFlg) {
	            System.out.println("引っくり返せるコマがありません。");
	
	            session.setAttribute("retryFlg", true);
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
	            ArrayList<Integer> otherDiscRowNoColumnNo = new ArrayList<>();
	            // 全他コマ行列番号リスト
	            ArrayList<ArrayList<Integer>> otherDiscRowNoColumnNoAsPos = new ArrayList<>();
	            
	            int noCounter = 1;
	            boolean selfDiscFlg = false;
	            boolean emptyFlg = false;
	            boolean boardOutsideFlg = false;
	            while (!boardLogic.getSelfDiscFlg(board)) {
	            	int rowNo = (int) session.getAttribute("setRowNo2int");
	            	int columnNo = (int) session.getAttribute("setColumnNo2int");
	            	
	                // 他コマ行列番号リスト取得
	                otherDiscRowNoColumnNo = boardLogic.addOtherDiscRowNoColumnNo(board, rowNo, columnNo, targetDiscPos, otherDisc, (String) session.getAttribute("discColor"), noCounter);
	                
	                // 全他コマ行列番号リストに、他コマ行列番号リストを格納
	                otherDiscRowNoColumnNoAsPos.add(otherDiscRowNoColumnNo);
	                
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
	                    otherDiscRowNoColumnNoAsPos = clearVal;
	                    break;
	                }
	                noCounter++;
	            }
	            
	            if(!selfDiscFlg) {
	                continue;
	            }
	
	            // 反転対象行列番号リストに、全他コマ行列番号リストを追加
	            allOtherDiscRowNoColumnNo.add(otherDiscRowNoColumnNoAsPos);
	        }
	        
	        log("allOtherDiscRowNoColumnNo:" + allOtherDiscRowNoColumnNo);
	        
	        if (allOtherDiscRowNoColumnNo.size() == 0) {
	            System.out.println("探索の結果、ひっくり返せるコマがありませんでした。");
	            
	            session.setAttribute("retryFlg", true);
	            
	            // main.jspへ遷移
	         	RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
	         	rd.forward(request, response);
	        }
	        
	        for (ArrayList<ArrayList<Integer>> otherDiscRowNoColumnNo : allOtherDiscRowNoColumnNo) {
	            for (ArrayList<Integer> rowNoColumnNo : otherDiscRowNoColumnNo) {
	                if (rowNoColumnNo.size() != 0) {
	                    int otherDiscRowNo = rowNoColumnNo.get(0);
	                    int otherDiscColumnNo = rowNoColumnNo.get(1);
	                    
	                    // コマ配置(他コマをひっくり返す)
	                    boardLogic.setDisc(board, (int) session.getAttribute("setRowNo2int"), (int) session.getAttribute("setColumnNo2int"), (String) session.getAttribute("discColor"));
	                    boardLogic.setDisc(board, otherDiscRowNo, otherDiscColumnNo, (String) session.getAttribute("discColor"));
	                }
	            }
	        }
	        
	        // コマ数算出
	        String[][] boardList = board.getBoardList();
	        discLogic.calcDiscNum(disc, boardList);
	        
	        // オセロ盤面表示
	        Integer blackNum = disc.getBlackNum();
	        Integer whiteNum = disc.getWhiteNum();
	        boardLogic.display(board, blackNum, whiteNum);
	        
	        // 次ターンのプレイヤーの名前とコマを取得
	        // FIXME: l:143と共通化したい
	        if ((Boolean) session.getAttribute("player1Turn")) {
	        	session.setAttribute("playerName", (String) session.getAttribute("player2Name"));
	            session.setAttribute("discColor", (String) session.getAttribute("player2Disc"));
	        } else {
	        	session.setAttribute("playerName", (String) session.getAttribute("player1Name"));
	            session.setAttribute("discColor", (String) session.getAttribute("player1Disc"));
	        }
			session.setAttribute("boardList", boardList);
			
			session.setAttribute("retryFlg", false);
			
			// main.jspへ遷移
			log("forward to main.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
			rd.forward(request, response);
		}
	}
}
