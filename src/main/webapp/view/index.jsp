<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>オセロゲーム</title>
	</head>
	<body>
		<form action="<%= request.getContextPath() %>/ready" method="post">
			<p>1人目のプレイヤー名を入力してください。</p>
			<input type="text" name="player1Name">
			<p>2人目のプレイヤー名を入力してください。</p>
			<input type="text" name="player2Name">
			<input type="submit" value="送信">
		</form>
	</body>
</html>