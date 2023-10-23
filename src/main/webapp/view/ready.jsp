<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Player"%>
<%
// セッションからのデータの取得
Player player1 = (Player) session.getAttribute("player1");
Player player2 = (Player) session.getAttribute("player2");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>オセロゲーム</title>
	</head>
	<body>
		<p><%= player1.getPlayerName() %>さんのコマは"<%= player1.getDiscColor() %>"です。</p>
		<p><%= player2.getPlayerName() %>さんのコマは"<%= player2.getDiscColor() %>"です。</p>
		<p>準備ができたら「ゲーム開始」を押下してください。</p>
		<form action="<%= request.getContextPath() %>/main" method="post">
			<input type="submit" value="ゲーム開始">
		</form>
	</body>
</html>