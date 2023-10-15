<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// セッションからのデータの取得
String playerName1 = (String)session.getAttribute("playerName1");
String playerName2 = (String)session.getAttribute("playerName2");
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>オセロゲーム</title>
	</head>
	<body>
		<p><%= playerName1 %>さんのターン！</p>
		<p><%= playerName2 %>さんのターン！</p>
		<form action="<%= request.getContextPath() %>/main" method="post">
			<input type="submit" value="コマを置く">
		</form>
	</body>
</html>