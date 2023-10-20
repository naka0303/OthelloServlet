<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// セッションからのデータの取得
String[][] boardList = (String[][]) session.getAttribute("boardList");
String playerName = (String) session.getAttribute("playerName");
String discColor = (String) session.getAttribute("discColor");
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
		<p>1&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;3&nbsp;&nbsp;&nbsp;4&nbsp;&nbsp;&nbsp;5&nbsp;&nbsp;&nbsp;6&nbsp;&nbsp;&nbsp;7&nbsp;&nbsp;&nbsp;8</p>
		<table>
			<% int cnt = 1; %>
			<% for (String[] disc : boardList) { %>
				<tr>
				<% for (String d : disc) { %>
					<% if (d != null) { %>
		 				<td><%= d %></td>
		 				<td></td>
		 				<td></td>
		 				<td></td>
		 			<% } %>
		 		<% } %>
		 		<% if (cnt - 1 >= 1) { %>
		 			<td><%= cnt - 1 %></td>
		 			<td></td>
		 			<td></td>
		 			<td></td>
		 		<% } %>
		 		</tr>
                <%= cnt++ %>
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