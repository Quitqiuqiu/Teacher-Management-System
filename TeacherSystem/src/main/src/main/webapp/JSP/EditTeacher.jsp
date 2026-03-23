<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javaBean.Teacher" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="zh">
	<head>
	    <meta charset="UTF-8">
	    <title>编辑教师</title>
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
	</head>

	<body>
		<%
		    Teacher teacher = (Teacher) request.getAttribute("teacher");
		%>
		<div class="container mt-5">
		    <h2 class="mb-4">编辑教师信息</h2>
		    <form action="${pageContext.request.contextPath}/admin?action=update" method="post">
		        <input type="hidden" name="workId" value="<%= teacher.getWork_id() %>">
		        <div class="mb-3">
		            <label class="form-label">姓名</label>
		            <input type="text" name="name" class="form-control" value="<%= teacher.getName() %>" required>
		        </div>
		        <div class="mb-3">
		            <label class="form-label">性别</label>
		            <select name="gender" class="form-control">
		                <option value="男" <%= teacher.getGender().equals("男") ? "selected" : "" %>>男</option>
		                <option value="女" <%= teacher.getGender().equals("女") ? "selected" : "" %>>女</option>
		            </select>
		        </div>
		        <div class="mb-3">
		            <label class="form-label">学院</label>
		            <input type="text" name="school" class="form-control" value="<%= teacher.getSchool() %>" required>
		        </div>
		        <div class="mb-3">
		            <label class="form-label">职称</label>
		            <select name="title" class="form-control">
		                <option value="教授" <%= teacher.getTitle().equals("教授") ? "selected" : "" %>>教授</option>
		                <option value="副教授" <%= teacher.getTitle().equals("副教授") ? "selected" : "" %>>副教授</option>
		                <option value="讲师" <%= teacher.getTitle().equals("讲师") ? "selected" : "" %>>讲师</option>
		            </select>
		        </div>
		        <button type="submit" class="btn btn-primary">保存修改</button>
		        <a href="${pageContext.request.contextPath}/admin?action=list" class="btn btn-secondary">取消</a>
		    </form>
		</div>
	</body>
</html>
