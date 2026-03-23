<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>编辑评价</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/CSS/Comments.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card edit-card shadow">
				    <div class="card-header">
				        <h3 class="mb-0">修改我的评价</h3>
				    </div>
                    <div class="card-body">
                        <form action="comment" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="commentId" value="${comment.comment_id}">

                            <div class="mb-3">
                                <label class="form-label">教师 ID</label>
                                <input type="text" class="form-control" value="${comment.teacher_id}" disabled>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">评分 (1-5分)</label>
                                <select name="score" class="form-select">
                                    <option value="5" ${comment.score == 5 ? 'selected' : ''}>5分 - 非常满意</option>
                                    <option value="4" ${comment.score == 4 ? 'selected' : ''}>4分 - 满意</option>
                                    <option value="3" ${comment.score == 3 ? 'selected' : ''}>3分 - 一般</option>
                                    <option value="2" ${comment.score == 2 ? 'selected' : ''}>2分 - 不满意</option>
                                    <option value="1" ${comment.score == 1 ? 'selected' : ''}>1分 - 极差</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">评价内容</label>
                                <textarea name="content" class="form-control" rows="5" required>${comment.content}</textarea>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-success">保存修改</button>
                                <a href="comment?action=list" class="btn btn-outline-secondary">取消</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>