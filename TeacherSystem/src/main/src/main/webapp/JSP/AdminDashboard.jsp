<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>教师信息管理——管理员</title>
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Dashboard.css">
	</head>
	
	<body>
	    <div class="container mt-4">
		    <h1 class="mb-4">教师管理系统</h1>

			<div class="button-container">
				<a href="${pageContext.request.contextPath}/admin?action=add" class="btn btn-primary mb-3">添加教师</a>
				<a href="userProfile?action=view" class="btn btn-primary mb-3">个人中心</a>
			</div>
		    
		    <!-- 搜索栏 -->
			<form action="${pageContext.request.contextPath}/admin" method="get" class="mb-3">
				<input type="hidden" name="action" value="search">
				<div class="input-group">
					<input type="text" name="keyword" class="form-control" placeholder="通过工号或姓名搜索">
					<button class="btn btn-primary" type="submit">搜索</button>
				</div>
			</form>
			<!-- 筛选栏 -->
			<form action="${pageContext.request.contextPath}/admin" method="get" class="mb-4">
				<input type="hidden" name="action" value="filter">
				<div class="row">
					<div class="col-md-4">
						<input type="text" name="school" class="form-control" placeholder="按学院筛选">
					</div>
					<div class="col-md-4">
						<select name="gender" class="form-select">
						<option value="">所有性别</option>
						<option value="男">男</option>
						<option value="女">女</option>
						</select>
					</div>
					<div class="col-md-4">
						<select name="title" class="form-select">
						<option value="">所有职称</option>
						<option value="教授">教授</option>
						<option value="副教授">副教授</option>
						<option value="讲师">讲师</option>
						</select>
					</div>
				</div>
				<button class="btn btn-success col-mt-3" type="submit">筛选</button>
			</form>
		    <!-- 教师表 -->
		    <table class="table table-bordered">
		        <thead>
		        <tr>
		            <th>工号</th>
		            <th>姓名</th>
		            <th>性别</th>
		            <th>学院</th>
		            <th>职称</th>
		            <th>操作</th>
		        </tr>
		        </thead>
		        <tbody>
			        <c:forEach var="teacher" items="${teachers}">
			            <tr>
			                <td>${teacher.getWork_id()}</td>
			                <td>${teacher.getName()}</td>
			                <td>${teacher.getGender()}</td>
			                <td>${teacher.getSchool()}</td>
			                <td>${teacher.getTitle()}</td>
			                <td>
			                    <button type="button" 
								        class="btn btn-sm btn-info text-white" 
								        data-bs-toggle="modal" 
								        data-bs-target="#teacherDetailModal"
								        data-workid="${teacher.getWork_id()}"
								        data-name="${teacher.getName()}"
								        data-gender="${teacher.getGender()}"
								        data-school="${teacher.getSchool()}"
								        data-title="${teacher.getTitle()}">
								    详情
								</button>
			                    <a href="admin?action=edit&workId=${teacher.getWork_id()}" class="btn btn-warning btn-sm">修改</a>
			                    <button class="btn btn-danger btn-sm" onclick="confirmDelete('${teacher.getWork_id()}')">删除</button>
			                </td>
			            </tr>
			        </c:forEach>
		        </tbody>
		    </table>
		</div>
		
		<!-- 教师详情信息 -->
		<div class="modal fade" id="teacherDetailModal" tabindex="-1" aria-hidden="true">
		    <div class="modal-dialog modal-dialog-centered">
		        <div class="modal-content shadow-lg">
		            <div class="modal-header bg-info text-white">
		                <h5 class="modal-title"><i class="bi bi-person-badge"></i> 教师详细资料</h5>
		                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
		            </div>
		            <div class="modal-body p-4">
		                <div class="row align-items-center">
		                    <div class="col-md-5 d-flex justify-content-center mb-3 mb-md-0">
		                        <img src="${pageContext.request.contextPath}/images/teacher.png" 
		                             id="modalImage"
		                             class="rounded-circle border border-4 border-light shadow" 
		                             style="width: 140px; height: 140px; object-fit: cover;" 
		                             alt="教师照片">
		                    </div>
		                    <div class="col-md-7">
		                        <table class="table table-sm table-borderless mb-0">
		                            <tr>
		                                <th class="text-muted" width="70">工号:</th>
		                                <td id="modalWorkId" class="text-dark"></td>
		                            </tr>
		                            <tr>
		                                <th class="text-muted">姓名:</th>
		                                <td id="modalName"></td>
		                            </tr>
		                            <tr>
		                                <th class="text-muted">性别:</th>
		                                <td id="modalGender"></td>
		                            </tr>
		                            <tr>
		                                <th class="text-muted">学院:</th>
		                                <td id="modalSchool"></td>
		                            </tr>
		                            <tr>
		                                <th class="text-muted">职称:</th>
		                                <td id="modalTitle"></td>
		                            </tr>
		                            <tr>
		                                <th class="text-muted">平均分:</th>
		                                <td id="modalAverageScore"></td>
		                            </tr>
		                        </table>
		                        
					            <div class="modal-footer">
								    <a id="modalRateBtn" class="btn btn-success px-4">去评价</a>
								    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
								</div>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		
		<script src="${pageContext.request.contextPath}/JavaScript/Alert.js"></script>
		<c:if test="${empty teachers}">
			<div id="alertBox" class="alert alert-danger">
				<p>查无此人！</p>
			</div>
		</c:if>
		<c:if test="${not empty requestScope.message}">
			   <div id="alertBox" class="alert alert-info">${requestScope.message}</div>
		</c:if>
		
		<!-- 确认删除教师 -->
		<form id="deleteForm" action="admin" method="post">
			<input type="hidden" name="action" value="delete">
			<input type="hidden" name="workId" id="deleteWorkId">
		</form>
		<script>
		    function confirmDelete(workId) {
		        if (confirm("确定要删除该教师吗？")) {
		        	document.getElementById("deleteWorkId").value = workId;
		            document.getElementById("deleteForm").submit(); 
		        }
		    }
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
		<script>const contextPath = '${pageContext.request.contextPath}';</script>
		<script src="${pageContext.request.contextPath}/JavaScript/ShowDetail.js"></script>
	</body>
</html>