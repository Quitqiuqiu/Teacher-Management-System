<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>修改个人信息</title>
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${pageContext.request.contextPath}/CSS/Profile.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<div class="container">
		    <h1>修改个人信息</h1>
		    <form action="userProfile?action=update" method="post">
			    <div class="form-group">
			        <label for="username">用户名</label>
			        <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
			    </div>
			    <div class="form-group">
			        <label for="currentPassword">当前密码</label>
			        <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
			    </div>
			    <div class="form-group">
			        <label for="password">新密码</label>
			        <input type="password" class="form-control" id="password" name="password" required>
			    </div>
			    <div class="button-group">
				    <button type="submit" class="btn btn-primary">保存</button>
				    <a href="userProfile?action=view" class="btn btn-secondary">取消</a>
				</div>
			</form>
			
			<script src="${pageContext.request.contextPath}/JavaScript/Alert.js"></script>
			<c:if test="${not empty message}">
			    <div id="alertBox" class="alert alert-danger">
			        ${message}
	    		</div>
			</c:if>
		</div>
	</body>
</html>