<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的评价管理</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/CSS/Comments.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>我的评价</h1>
            <div class="d-flex">
	            <a href="userDashboard?action=list" class="btn btn-secondary me-2">返回首页</a>
	            <a href="userProfile?action=view" class="btn btn-secondary">返回个人中心</a>
            </div>
        </div>

        <div class="card mb-4">
            <div class="card-body">
                <form action="comment" method="get" class="row g-3">
                    <input type="hidden" name="action" value="search">
                    <div class="col-md-10">
                        <input type="text" name="keyword" class="form-control" placeholder="输入评价内容关键词..." value="${keyword}">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">搜索</button>
                    </div>
                </form>
            </div>
        </div>

        <table class="table table-hover table-striped">
            <thead class="table-dark">
                <tr>
                    <th>教师ID</th>
                    <th>评价内容</th>
                    <th>评分</th>
                    <th>评价时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="comment" items="${commentList}">
                    <tr>
                        <td>${comment.teacher_id}</td>
						<td class="comment-content">${comment.content}</td>
						<td>
						    <span class="badge badge-score-${comment.score}">${comment.score} 分</span>
						</td>
                        <td>${comment.create_time}</td>
                        <td>
                            <a href="comment?action=toEdit&commentId=${comment.comment_id}" class="btn btn-sm btn-warning">编辑</a>
                            
                            <form action="comment?action=delete" method="post" style="display:inline;" onsubmit="return confirm('确定要删除这条评价吗？');">
                                <input type="hidden" name="commentId" value="${comment.comment_id}">
                                <button type="submit" class="btn btn-sm btn-danger">删除</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty commentList}">
                    <tr>
                        <td colspan="5" class="text-center">暂无评价记录</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>