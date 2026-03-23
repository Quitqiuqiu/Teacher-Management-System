<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="javaBean.User" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
	    <meta charset="UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>个人信息</title>
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${pageContext.request.contextPath}/CSS/Profile.css" rel="stylesheet" type="text/css">
	</head>

	<body>
	    <div class="container mt-4">
	        <h1 class="mb-4">个人信息</h1>

	        <table class="table table-bordered">
	            <tr>
	                <th>用户ID</th>
	                <td>${user.getUser_id()}</td>
	            </tr>
	            <tr>
	                <th>用户名</th>
	                <td>${user.getUsername()}</td>
	            </tr>
	            <tr>
	                <th>身份</th>
	                <td>${user.getRole()}</td>
	            </tr>
	            <tr>
	                <th>电子邮件</th>
	                <td>${user.getEmail()}</td>
	            </tr>
	        </table>

		<!-- 根据身份返回不同页面 -->
		<% 
			User user = (User) session.getAttribute("user");
			String role = user.getRole(); // 获取当前用户角色
		    String returnUrl = (role != null && role.equals("管理员")) ? "admin?action=list" : "userDashboard?action=list";
		%>
	        <div class="button-container">
			    <a href="userProfile?action=editUsername" class="btn btn-warning">修改用户名</a>
			    <a href="userProfile?action=editPassword" class="btn btn-warning">修改密码</a>
			    <a href="userProfile?action=editEmail" class="btn btn-warning">修改邮箱</a>
			    <a href="userProfile?action=delete" class="btn btn-danger">删除账户</a>
			    <a href="<%= returnUrl %>" class="btn btn-primary">返回</a>
			</div>

			<script src="${pageContext.request.contextPath}/JavaScript/Alert.js"></script>
	        <c:if test="${not empty param.message}">
	            <div class="alert alert-info">${param.message}</div>
	        </c:if>
	    </div>
	</body>
</html>