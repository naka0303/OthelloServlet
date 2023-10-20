<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// セッションからのデータの取得
String player1Name = (String) session.getAttribute("player1Name");
String player2Name = (String) session.getAttribute("player2Name");
String player1Disc = (String) session.getAttribute("player1Disc");
String player2Disc = (String) session.getAttribute("player2Disc");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>オセロゲーム</title>
	</head>
	<body>
		<p><%= player1Name %>さんのコマは"<%= player1Disc %>"です。</p>
		<p><%= player2Name %>さんのコマは"<%= player2Disc %>"です。</p>
		<p>準備ができたら「ゲーム開始」を押下してください。</p>
		<form action="<%= request.getContextPath() %>/main" method="post">
			<input type="submit" value="ゲーム開始">
		</form>
	</body>
</html>