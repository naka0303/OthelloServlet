package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Board;
import model.BoardLogic;
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
		HttpSession session = request.getSession();
		Board board = (Board)session.getAttribute("board");
		BoardLogic boardLogic = (BoardLogic)session.getAttribute("boardLogic");
		Player player1 = (Player)session.getAttribute("player1");
		Player player2 = (Player)session.getAttribute("player2");
		PlayerLogic playerLogic = (PlayerLogic)session.getAttribute("playerLogic");
			
		// セッション保持判定
		if (board == null) {
			board = new Board();
			boardLogic = new BoardLogic();
			player1 = new Player();
			player2 = new Player();
			playerLogic = new PlayerLogic();
		}
		
		session.setAttribute("board", board);
		session.setAttribute("boardLogic", boardLogic);
		session.setAttribute("player1", player1);
		session.setAttribute("player2", player2);
		session.setAttribute("playerLogic", playerLogic);
		
		boolean retryFlg = false;
		
		// index.jspからデータ受け取り
		request.setCharacterEncoding("UTF8");
			
		// プレイヤーのターン状況取得
		boolean player1Turn = (Boolean) session.getAttribute("player1Turn");
        boolean player2Turn = (Boolean) session.getAttribute("player2Turn");
        
        // プレイヤーのターン切り替え
		if (!retryFlg) {
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
		session.setAttribute("player1Turn", player1.isTurn());
	    session.setAttribute("player2Turn", player2.isTurn());
		
		// プレイヤーのターン切り替え
		player1Turn = (Boolean) session.getAttribute("player1Turn");
        player2Turn = (Boolean) session.getAttribute("player2Turn");
            
        System.out.print(player1Turn);
        System.out.print(player2Turn);
            
        String playerName = "";
        String discColor = "";
        if (player1Turn) {
            playerName = (String) session.getAttribute("player1Name");
            discColor = (String) session.getAttribute("player1Disc");
        } else {
        	playerName = (String) session.getAttribute("player2Name");
            discColor = (String) session.getAttribute("player2Disc");
        }
        
        System.out.println("=====" + playerName + "さん(" + discColor + ")のターン！" + "=====");
		    
		String[][] boardList = board.getBoardList();
		session.setAttribute("playerName", playerName);
		session.setAttribute("discColor", discColor);
		session.setAttribute("boardList", boardList);
		
		retryFlg = false;
		
		// main.jspへデータ渡し
		RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
		rd.forward(request, response);
	}
}
