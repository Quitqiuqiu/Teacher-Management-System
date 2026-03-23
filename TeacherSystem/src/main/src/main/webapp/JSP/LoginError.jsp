<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Login Error</title>
		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
			rel="stylesheet">
		<link rel="stylesheet" href="../CSS/Login.css">
	</head>
	<body class="container mt-5">
		<h2>教师管理系统 - 登录失败</h2>
		<div class="alert alert-danger" role="alert">
			<%= request.getParameter("error") %>
		</div>
		<form action="${pageContext.request.contextPath}/login" method="post">
			<div class="mb-3">
				<label for="username" class="form-label">邮箱</label> 
				<input type="text" id="email" name="email" class="form-control" required>
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">密码</label> 
				<input type="password" id="password" name="password" class="form-control" required>
			</div>
			<button type="submit" class="btn btn-primary">登录</button>
		</form>
		<p class="mt-3">
			尚无账号? <a href="register.jsp">注册</a>
		</p>
	</body>
</html>