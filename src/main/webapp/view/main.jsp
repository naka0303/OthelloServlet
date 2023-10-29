<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Board, model.Player, model.Disc"%>
<%
// セッションからのデータの取得
Board board = (Board) session.getAttribute("board");
Disc disc = (Disc) session.getAttribute("disc");
Player player1 = (Player) session.getAttribute("player1");
Player player2 = (Player) session.getAttribute("player2");
String blackDiscImgPath = (String) session.getAttribute("blackDiscImgPath");
String whiteDiscImgPath = (String) session.getAttribute("whiteDiscImgPath");

Player playerTurned;
if (player1.isTurn()) {
	playerTurned = player1;
} else {
	playerTurned = player2;
}

String[][] boardList = board.getBoardList();

System.out.println("=====" + playerTurned.getPlayerName() + "さん(" + playerTurned.getDiscColor() + ")のターン！" + "=====");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<title>オセロゲーム</title>
	</head>
	<body>
		<p><%= playerTurned.getPlayerName() %>さんのターン！</p>
		<% if (playerTurned.getDiscColor() == "B") { %>
			<p><img src="<%= request.getContextPath() %><%= blackDiscImgPath %>" class="black_disc" alt="黒コマ">を置いてください</p>
		<% } else if (playerTurned.getDiscColor() == "W") { %>
			<p><img src="<%= request.getContextPath() %><%= whiteDiscImgPath %>" class="white_disc" alt="白コマ">を置いてください</p>
		<% } %>
		<p>戦況</p>
		<p>黒コマ：<%= disc.getBlackNum() %>&nbsp;&nbsp;白コマ：<%= disc.getWhiteNum() %></p>
		<% if (player1.isResultFlg()) { %>
			<p>Winner is <%= player1.getPlayerName() %>!!</p>
		<% } else if (player2.isResultFlg()) { %>
			<p>Winner is <%= player2.getPlayerName() %>!!</p>
		<% } %>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8</p>
		<% int cnt = 1; %>
		<table>
		<% for (int i = 1; i <= 8; i++) { %>
			<tr>
			<% for (int j = 1; j <= 8; j++) { %>
				<% if (boardList[i][j] == "-") { %>
					<form action="<%= request.getContextPath() %>/main" name="discSetForm" method="post">
						<td><input type="submit" class="board" name="board" value="<%= i %>,<%= j %>" alt="盤面マス"></td>
					</form>
				<% } else if (boardList[i][j] == "B") { %>
					<td><img src="<%= request.getContextPath() %><%= blackDiscImgPath %>" class="black_disc" alt="黒コマ"></td>
				<% } else if (boardList[i][j] == "W") { %>
					<td><img src="<%= request.getContextPath() %><%= whiteDiscImgPath %>" class="white_disc" alt="白コマ"></td>
				<% } %>
			<% } %>
				<td><%= cnt++ %></td>
			</tr>
	    <% } %>
		</table>
		<form action="<%= request.getContextPath() %>/main" name="discSetForm" method="post">
			<input type="submit" name="pass" value="パス"></td>
		</form>
	</body>
</html>