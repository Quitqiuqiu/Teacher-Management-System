<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>发表评价 - 教师管理系统</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/CSS/Comments.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="d-flex align-items-center mb-4 p-3 bg-light rounded shadow-sm">
                    <div class="me-3">
                        <img src="${pageContext.request.contextPath}/images/teacher.png" 
                             alt="Teacher" class="rounded-circle" style="width: 60px; height: 60px; object-fit: cover;">
                    </div>
                    <div>
                        <h3 class="mb-0 text-primary">正在评价：${teacher.name} 老师</h3>
                        <small class="text-muted">${teacher.school} · ${teacher.title}</small>
                    </div>
                </div>

                <div class="card edit-card shadow">
                    <div class="card-header">
                        <h5 class="mb-0">填写评价表单</h5>
                    </div>
                    <div class="card-body p-4">
                        <form action="${pageContext.request.contextPath}/comment" method="post">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="teacherId" value="${teacher.work_id}">

                            <div class="mb-4">
                                <label class="form-label fw-bold">1. 给老师打个分吧</label>
                                <select name="score" class="form-select form-select-lg">
                                    <option value="5">⭐⭐⭐⭐⭐ 5分 - 强烈推荐</option>
                                    <option value="4">⭐⭐⭐⭐ 4分 - 比较满意</option>
                                    <option value="3" selected>⭐⭐⭐ 3分 - 表现平平</option>
                                    <option value="2">⭐⭐ 2分 - 有待改进</option>
                                    <option value="1">⭐ 1分 - 体验较差</option>
                                </select>
                            </div>

                            <div class="mb-4">
                                <label class="form-label fw-bold">2. 详细评价内容</label>
                                <textarea name="content" class="form-control" rows="6" 
                                          placeholder="请客观描述您的上课感受，这对其他同学很有帮助..." required></textarea>
                            </div>

                            <div class="d-flex justify-content-between align-items-center">
                                <a href="userDashboard?action=list" class="text-decoration-none text-secondary">
                                    <i class="bi bi-arrow-left"></i> 返回列表页
                                </a>
                                <div class="action-buttons">
                                    <button type="reset" class="btn btn-outline-secondary me-2">重置</button>
                                    <button type="submit" class="btn btn-primary px-4">提交评价</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>