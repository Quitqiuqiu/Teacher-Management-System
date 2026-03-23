<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>删除账户</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/CSS/Profile.css" rel="stylesheet" type="text/css">
	</head>
	
	<body>
		<div class="container">
			<h1>确认删除账户</h1>
			<div class="delete-warning">
	            <p>删除账户后，所有相关数据将被永久删除，无法恢复。请确认您的密码以继续操作。</p>
	        </div>
			<form action="${pageContext.request.contextPath}/userProfile?action=delete" method="post">
			    <div class="form-group">
			        <label for="password">请输入密码以确认删除</label>
			        <input type="password" class="form-control" id="password" name="password" required>
			    </div>
			    <div class="button-group">
				    <button type="submit" class="btn btn-primary">删除账户</button>
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