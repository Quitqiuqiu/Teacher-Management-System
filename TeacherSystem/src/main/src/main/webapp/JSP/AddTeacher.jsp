<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html lang="zh">
	<head>
	    <meta charset="UTF-8">
	    <title>添加教师</title>
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
	</head>
	
	<body>
		<div class="container mt-5">
		    <h2 class="mb-4">添加教师</h2>
		    <form action="admin?action=add" method="post">
		        <div class="mb-3">
		            <label class="form-label">工号</label>
		            <input type="text" name="workId" class="form-control" required>
		        </div>
		        <div class="mb-3">
		            <label class="form-label">姓名</label>
		            <input type="text" name="name" class="form-control" required>
		        </div>
		        <div class="mb-3">
		            <label class="form-label">性别</label>
		            <select name="gender" class="form-control">
		                <option value="男">男</option>
		                <option value="女">女</option>
		            </select>
		        </div>
		        <div class="mb-3">
		            <label class="form-label">学院</label>
		            <input type="text" name="school" class="form-control" required>
		        </div>
		        <div class="mb-3">
		            <label class="form-label">职称</label>
		            <select name="title" class="form-control">
		                <option value="教授">教授</option>
		                <option value="副教授">副教授</option>
		                <option value="讲师">讲师</option>
		            </select>
		        </div>
		        <button type="submit" class="btn btn-success">添加</button>
		        <a href="${pageContext.request.contextPath}/admin?action=list" class="btn btn-secondary">取消</a>
		    </form>
		</div>
	</body>
</html>