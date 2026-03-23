<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Logout</title>
</head>
<body>
    <%
        session.invalidate();  // 使当前会话无效
    %>
    <h2>您已安全退出！</h2>
    <a href="login.jsp">回到登录页</a>
</body>
</html>