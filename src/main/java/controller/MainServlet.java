package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		
		// main.jspからデータ受け取り
		request.setCharacterEncoding("UTF8");
		
		// TODO: 外部ファイルに移動させる
		String url = "jdbc:postgresql://localhost:5432/othelloservlet";
		String user = "postgres";
		String password = "admin";
				
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
					
			String sql = "SELECT * FROM boardList_elements";
			PreparedStatement ps = conn.prepareStatement(sql);
					
			ResultSet rs = ps.executeQuery();
					
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String image_name = rs.getString("image_name");
				String image_path = rs.getString("image_path");
				
				if (id == 1) {
					session.setAttribute("black_disc_image_path", image_path);
				} else if (id == 2) {
					session.setAttribute("white_disc_image_path", image_path);
				} else if (id == 3) {
					session.setAttribute("board_image_path", image_path);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		// main.jspで選択された配置コマ行番号/列番号を取得
		String setRowColumnNo = request.getParameter("board");
		String setRowNo = "";
		String setColumnNo = "";
		if (setRowColumnNo != null) {
			String[] setRowColumnNoSplitted = setRowColumnNo.split(",");
			setRowNo = setRowColumnNoSplitted[0];
			setColumnNo = setRowColumnNoSplitted[1];
			board.setRowNoSet(setRowNo);
			board.setColumnNoSet(setColumnNo);
		}
		
		// 他コマ方向格納リスト初期化
		boardLogic.initializeAllOtherDiscPos(board);
		
		// ready.jspから遷移してきた場合とそれ以外で処理を変える
		// TODO: (4,7)にコマを配置するとnullエラー発生する
		if (setRowNo == "" && setColumnNo == "") {
			log("from ready.jsp");
			
			// プレイヤー1のターンにする
			player1.setTurn(true);
			
			// 盤面初期状態(1回のみ実行)
		    boardLogic.initialize(board);
		    
		    // 盤面インスタンスとリトライフラグをセッション格納
		    session.setAttribute("playerName", player1.getPlayerName());
		    session.setAttribute("discColor", player1.getDiscColor());
	        session.setAttribute("board", board);
	        session.setAttribute("retryFlg", true);
	        
	        // main.jspへ遷移
	        log("forward to main.jsp");
	     	RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
	     	rd.forward(request, response);
		} else {
			log("from main.jsp");
			
			// プレイヤーのターン状況取得
			boolean player1Turn = player1.isTurn();
	        boolean player2Turn = player2.isTurn();
	        
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
			
			// プレイヤーのターン状況を再取得
			player1Turn = player1.isTurn();
			player2Turn = player2.isTurn();
			
			String playerName = "";
			String discColor = "";
			if (player1Turn) {
				playerName = player1.getPlayerName();
				discColor = player1.getDiscColor();
			} else {
				playerName = player2.getPlayerName();
				discColor = player2.getDiscColor();
			}
			
			log("player1Turn:" + player1Turn + " " + "player2Turn:" + player2Turn);
			log("playerName:" + playerName);
			log("discColor:" + discColor);
	        
	        // main.jspで入力された行列番号をint型にキャスト
			Integer rowNoSet2Int = Integer.parseInt(board.getRowNoSet());
			Integer columnNoSet2Int = Integer.parseInt(board.getColumnNoSet());
	        log("rowNoSet2Int:" + rowNoSet2Int);
	        log("columnNoSet2Int:" + columnNoSet2Int);
	        
	        // 反転対象行列番号リスト初期化
	        ArrayList<ArrayList<ArrayList<Integer>>> allOtherDiscRowNoColumnNo = new ArrayList<>();
	        
	        // 他色取得
	        String otherDisc = boardLogic.getOtherDiscColor(discColor);
	        
	        // コマ配置可否チェック
	        boolean isSetDiscFlg = boardLogic.isSetDisc(board.getBoardList(), rowNoSet2Int, columnNoSet2Int);
	        
	        // 指定したマスにコマが置いてある場合は探索終了
	        if (!isSetDiscFlg) {
	            System.out.println("指定したマスには既にコマが配置されています。");
	
	            session.setAttribute("retryFlg", true);
	        }
	        
	        // 他コマ隣接チェック
	        boolean isNextToOtherDiscFlg = boardLogic.isNextToOtherDisc(board, rowNoSet2Int, columnNoSet2Int, otherDisc);
	        
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
	                // 他コマ行列番号リスト取得
	                otherDiscRowNoColumnNo = boardLogic.addOtherDiscRowNoColumnNo(board, rowNoSet2Int, columnNoSet2Int, targetDiscPos, otherDisc, discColor, noCounter);
	                
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
	        
	        // コマ配置(他コマをひっくり返す)
	        for (ArrayList<ArrayList<Integer>> otherDiscRowNoColumnNo : allOtherDiscRowNoColumnNo) {
	            for (ArrayList<Integer> rowNoColumnNo : otherDiscRowNoColumnNo) {
	                if (rowNoColumnNo.size() != 0) {
	                    int otherDiscRowNo = rowNoColumnNo.get(0);
	                    int otherDiscColumnNo = rowNoColumnNo.get(1);
	                    
	                    boardLogic.setDisc(board, rowNoSet2Int, columnNoSet2Int, discColor);
	                    boardLogic.setDisc(board, otherDiscRowNo, otherDiscColumnNo, discColor);
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
	        
	        // 次ターンのプレイヤー名/コマをセッション格納
	        if (player1Turn) {
	        	session.setAttribute("playerName", player2.getPlayerName());
	        	session.setAttribute("discColor", player2.getDiscColor());
	        } else {
	        	session.setAttribute("playerName", player1.getPlayerName());
	        	session.setAttribute("discColor", player1.getDiscColor());
	        }
	        
	        // 次ターンのプレイヤーの名前とコマを取得
			session.setAttribute("board", board);
			session.setAttribute("retryFlg", false);
			
			// main.jspへ遷移
			log("forward to main.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
			rd.forward(request, response);
		}
	}
}
