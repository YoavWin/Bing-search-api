<%@page import="database.UrlEntity"%>
<%@page import="database.MongoInteractor"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>Search Instagram Page on Bing</title>
</head>
<body>
	<h1>Hello, welcome to instagram URL search Bing engine</h1>
	<form action="/ProcessQueryServlet">
		Name:<br> <input type="text" name="name" value="" required>
		<br> <br> <input type="submit" value="Submit">
	</form>
	<div id="table">
		<table border="1">
			<tr>
				<th>Name</th>
				<th>URL</th>
			</tr>

			<%
				List<UrlEntity> entitiesList = MongoInteractor.getInstance().read();
				if (!entitiesList.isEmpty()) {
					for (int i = entitiesList.size() - 1; i >= 0; i--) {
			%>
			<tr>
				<td><%=entitiesList.get(i).getName()%></td>
				<%
					String instagramURL = entitiesList.get(i).getUrl();
							if (instagramURL.contains("http")) {
				%>
				<td><a href=<%=instagramURL%>><%=instagramURL.replaceAll("\"", "")%></a></td>
				<%
					} else {
				%>
				<td><%=instagramURL%></td>
			</tr>
			<%
				}
					}
				}
			%>
		</table>
	</div>
</body>
</html>

