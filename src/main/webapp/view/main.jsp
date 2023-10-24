<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Board, model.Player"%>
<%
// セッションからのデータの取得
Board board = (Board) session.getAttribute("board");
String playerName = (String) session.getAttribute("playerName");
String discColor = (String) session.getAttribute("discColor");
String black_disc_image_path = (String) session.getAttribute("black_disc_image_path");
String white_disc_image_path = (String) session.getAttribute("white_disc_image_path");
String board_image_path = (String) session.getAttribute("board_image_path");

String[][] boardList = board.getBoardList();

System.out.println("=====" + playerName + "さん(" + discColor + ")のターン！" + "=====");
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<title>オセロゲーム</title>
	</head>
	<body>
		<p><%= playerName %>さんのターン！</p>
		<% if (discColor == "B") { %>
			<p><img class="black_disc">を置いてください</p>
		<% } else if (discColor == "W") { %>
			<p><img class="white_disc">を置いてください</p>
		<% } %>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8</p>
		<% int cnt = 1; %>
		<form action="<%= request.getContextPath() %>/main" name="discSetForm" method="post">
			<table>
			<% for (int i = 1; i <= 8; i++) { %>
				<tr>
				<% for (int j = 1; j <= 8; j++) { %>
					<% if (boardList[i][j] == "-") { %>
						<td><input type="submit" class="board" name="board" value="<%= i %>,<%= j %>" alt="盤面マス"></td>
					<% } else if (boardList[i][j] == "B") { %>
						<td><input type="submit" class="black_disc" name="black_disc" value="<%= i %>,<%= j %>" alt="黒コマ"></td>
					<% } else if (boardList[i][j] == "W") { %>
						<td><input type="submit" class="white_disc" name="white_disc" value="<%= i %>,<%= j %>" alt="白コマ"></td>
					<% } %>
			 	<% } %>
			 		<td><%= cnt++ %></td>
			 	</tr>
	        <% } %>
			</table>
		</form>
	</body>
</html>