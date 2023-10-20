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

/**
 * Servlet implementation class ReadyServlet
 */
@WebServlet("/ready")
public class ReadyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadyServlet() {
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
		Player player1 = (Player)session.getAttribute("player1");
		Player player2 = (Player)session.getAttribute("player2");
		Board board = (Board)session.getAttribute("board");
		BoardLogic boardLogic = (BoardLogic)session.getAttribute("boardLogic");
		
		// セッション保持判定
		if (player1 == null || player2 == null || board == null || boardLogic == null) {
			player1 = new Player();
			player2 = new Player();
			board = new Board();
			boardLogic = new BoardLogic();
		}
		
		session.setAttribute("player1", player1);
		session.setAttribute("player2", player2);
		session.setAttribute("board", board);
		session.setAttribute("boardLogic", boardLogic);
		
		// index.jspからデータ受け取り
		request.setCharacterEncoding("UTF8");
		
		String player1Name = request.getParameter("player1Name");
		player1.setPlayerName(player1Name);
	    player1.setDiscColor("W");
	    String player2Name = request.getParameter("player2Name");
	    player2.setPlayerName(player2Name);
	    player2.setDiscColor("B");
	    
		session.setAttribute("player1Name", player1.getPlayerName());
		session.setAttribute("player1Disc", player1.getDiscColor());
		session.setAttribute("player2Name", player2.getPlayerName());
		session.setAttribute("player2Disc", player2.getDiscColor());
		
		// 盤面初期状態(1回のみ実行)
	    boardLogic.initialize(board);
	    
		// 他コマ方向格納リスト初期化
		boardLogic.initializeAllOtherDiscPos(board);
		
		// ターン状況をセッション保持
	    player1.setTurn(false);
	    player2.setTurn(false);
	    session.setAttribute("player1Turn", player1.isTurn());
	    session.setAttribute("player2Turn", player2.isTurn());

		// ready.jspへデータ渡し
		RequestDispatcher rd = request.getRequestDispatcher("view/ready.jsp");
		rd.forward(request, response);
	}

}

