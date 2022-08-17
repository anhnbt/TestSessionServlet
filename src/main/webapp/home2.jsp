<%@page import="com.anhnbt.SessionCounter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String sessionId = session.getId();
%>
<%=sessionId %>
<a href="/TestSessionServlet/index.jsp">Home</a>
<hr>
<%
//SessionCounter counter = (SessionCounter) session.getAttribute("counter");
//out.println("getRemoteAddr: " + request.getRemoteAddr());
%>
Number of online user(s): <%--counter.getActiveSessionNumber() --%>
</body>
</html>