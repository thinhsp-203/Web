<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("account");
%>
<html>
<head>
    <title>Trang chủ</title>
</head>
<body>
<% if (user != null) { %>
    <h2>Xin chào, <%= user.getFullName() %>!</h2>
    <p>Username: <%= user.getUserName() %></p>
    <p><a href="<%=request.getContextPath()%>/logout">Đăng xuất</a></p>
<% } else { %>
    <p>Bạn chưa đăng nhập. <a href="<%=request.getContextPath()%>/login">Đăng nhập</a></p>
<% } %>
</body>
</html>
