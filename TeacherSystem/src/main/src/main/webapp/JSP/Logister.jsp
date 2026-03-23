<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>注册登录</title>
	<link href="${pageContext.request.contextPath}/CSS/Logister.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<div class="content">
	        <div class="form sign-in">
	            <h2>欢迎回来</h2>
	            <form action="${pageContext.request.contextPath}/login" method="post">
		            <label>
		                <span>邮箱</span>
		                <input type="text" id="email" name="email" required>
		            </label>
		            <label>
		                <span>密码</span>
		                <input type="password" id="password" name="password" required>
		            </label>
		            <p class="forgot-pass"><a href="#">忘记密码？</a></p>
		            <button type="submit" class="submit">登 录</button>
	            </form>
	        </div>
	        <div class="sub-cont">
	            <div class="img">
	                <div class="img__text m--up">
	                    <h2>还未注册？</h2>
	                    <p>立即注册，进行教师管理！</p>
	                </div>
	                <div class="img__text m--in">
	                    <h2>已有帐号？</h2>
	                    <p>有帐号就登录吧，好久不见了！</p>
	                </div>
	                <div class="img__btn">
	                    <span class="m--up">注 册</span>
	                    <span class="m--in">登 录</span>
	                </div>
	            </div>
	            <div class="form sign-up">
	                <h2>立即注册</h2>
	                <form action="${pageContext.request.contextPath}/register" method="post">
		                <label>
		                    <span>用户名</span>
		                    <input type="text" id="username" name="username" required>
		                </label>
		                <label>
		                    <span>邮箱</span>
		                    <input type="email" id="email" name="email" required>
		                </label>
		                <label>
		                    <span>密码</span>
		                    <input type="password" id="password" name="password" required>
		                </label>
		                <label>
		                    <span>确认密码</span>
		                    <input type="password" id="confirm_password" name="confirm_password" required>
		                </label>
		                <button type="submit" class="submit">注 册</button>
	                </form>
	            </div>
	        </div>
	    </div>
	
	    <script>
		    document.querySelector('.img__btn').addEventListener('click', function() {
		        document.querySelector('.content').classList.toggle('s--signup')
		    })
	    </script>
		
		<script src="${pageContext.request.contextPath}/JavaScript/Alert.js"></script>
		<c:if test="${not empty param.error}">
		    <div id="alertBox" class="alert alert-danger">${param.error}</div>
		</c:if>
		<c:if test="${not empty error}">
			<div id="alertBox" class="alert alert-danger">${error}</div>
		</c:if>
		<c:if test="${not empty success}">
		    <div id="alertBox" class="alert alert-success">${success}</div>
		</c:if>
	</body>
</html>