package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		log("== ReadyServlet doPost ==");
		HttpSession session = request.getSession();
		Player player1 = (Player) session.getAttribute("player1");
		Player player2 = (Player) session.getAttribute("player2");
		
		// セッション格納判定
		if (player1 == null
			|| player2 == null) {
				player1 = new Player();
				player2 = new Player();
		}
		
		// index.jspからデータ受け取り
		request.setCharacterEncoding("UTF8");
		
		// プレイヤー1とプレイヤー2の、プレイヤー名/コマ/ターンをセット
		String player1Name = request.getParameter("player1Name");
		player1.setPlayerName(player1Name);
	    player1.setDiscColor("W");
	    player1.setTurn(false);
	    String player2Name = request.getParameter("player2Name");
	    player2.setPlayerName(player2Name);
	    player2.setDiscColor("B");
	    player2.setTurn(false);
	    
	    // プレイヤー1とプレイヤー2のインスタンスをセッション格納
	 	session.setAttribute("player1", player1);
	 	session.setAttribute("player2", player2);
	    
		// ready.jspへ遷移
        log("forward to ready.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("view/ready.jsp");
		rd.forward(request, response);
	}
}
