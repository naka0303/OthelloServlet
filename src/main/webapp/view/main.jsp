<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Board, model.Player"%>
<%
// セッションからのデータの取得
Board board = (Board) session.getAttribute("board");
String playerName = (String) session.getAttribute("playerName");
String discColor = (String) session.getAttribute("discColor");

String[][] boardList = board.getBoardList();

System.out.println("=====" + playerName + "さん(" + discColor + ")のターン！" + "=====");
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="css/ready.css">
		<title>オセロゲーム</title>
	</head>
	<body>
		<p><%= playerName %>さんのターン！</p>
		<p><%= discColor %>を置いてください</p>
		<p>1&nbsp;&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8</p>
		<% int cnt = 1; %>
		<table>
		<% for (int i = 1; i <= 8; i++) { %>
			<tr>
			<% for (int j = 1; j <= 8; j++) { %>
				<td><%= boardList[i][j] %>&nbsp;&nbsp;</td>
		 		<td></td>
		 		<td></td>
		 		<td></td>
		 	<% } %>
		 		<td><%= cnt++ %></td>
		 		<td></td>
		 		<td></td>
		 		<td></td>
		 	</tr>
        <% } %>
		</table>
		<form action="<%= request.getContextPath() %>/main" name="discSetForm" method="post">
			<p>コマを配置したい行番号を入力してください。</p>
			<input type="text" name="setRowNo">
			<p>コマを配置したい列番号を入力してください。</p>
			<input type="text" name="setColumnNo">
			<input type="submit" value="コマを置く">
		</form>
	</body>
</html>