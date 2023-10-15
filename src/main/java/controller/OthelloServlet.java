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
import model.Disc;
import model.Player;
import model.PlayerLogic;

/**
 * Servlet implementation class OthelloServlet
 */
@WebServlet("/main")
public class OthelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OthelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// セッション取得
		Disc disc = (Disc)session.getAttribute("disc");
		Player player = (Player)session.getAttribute("player");
		PlayerLogic playerLogic = (PlayerLogic)session.getAttribute("playerLogic");
		
		// セッション保持判定
		if (disc == null && player == null && playerLogic == null) {
			disc = new Disc();
			player = new Player();
			playerLogic = new PlayerLogic();
		} else {
			for (int i = 0; i <= 1; i++) {
				playerLogic.initialize(player);
			}
		}
		session.setAttribute("disc", disc);
		session.setAttribute("player", player);
		session.setAttribute("playerLogic", playerLogic);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("view/index.jsp");
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Board board = (Board)session.getAttribute("board");
		BoardLogic boardLogic = (BoardLogic)session.getAttribute("boardLogic");
		
		// index.jspからデータ受け取り
	    request.setCharacterEncoding("UTF8");
		
		// セッション保持判定
		if (board == null) {
			board = new Board();
			boardLogic = new BoardLogic();
		}
		
		session.setAttribute("board", board);
		session.setAttribute("boardLogic", boardLogic);
		boardLogic.initialize(board);
	    
		String playerName1 = request.getParameter("playerName1");
		String playerName2 = request.getParameter("playerName2");
		
		session.setAttribute("playerName1", playerName1);
		session.setAttribute("playerName2", playerName2);
		
		// main.jspへデータ渡し
		RequestDispatcher rd = request.getRequestDispatcher("view/main.jsp");
		rd.forward(request, response);
	}

}
