package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import daoPackage.AdminDAO;
import daoPackage.CommentDAO;
import daoPackage.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaBean.Comment;
import javaBean.Teacher;
import javaBean.User;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list"; // 默认显示列表

        try (Connection conn = DBUtil.getConnection()) {
            CommentDAO commentDAO = new CommentDAO(conn);
            
            switch (action) {
                case "list":
                    listMyComments(request, response, commentDAO);
                    break;
                case "search":
                    searchMyComments(request, response, commentDAO);
                    break;
                case "toEdit": // 跳转到编辑页面
                    showEditPage(request, response, commentDAO);
                    break;
                case "toAdd": // 跳转到评价页面
                    showAddPage(request, response, conn);
                    break;
                case "getAverage":
                    getTeacherAverage(request, response, commentDAO);
                    break;
                default:
                    response.sendRedirect("comment?action=list");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 处理中文
        String action = request.getParameter("action");

        try (Connection conn = DBUtil.getConnection()) {
            CommentDAO commentDAO = new CommentDAO(conn);

            switch (action) {
            case "add": // 新增：执行评价提交
                processAddComment(request, response, commentDAO);
                break;
            case "update":
                updateComment(request, response, commentDAO);
                break;
            case "delete":
                deleteComment(request, response, commentDAO);
                break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // --- 具体业务实现逻辑 ---

    private void listMyComments(HttpServletRequest request, HttpServletResponse response, CommentDAO dao) 
            throws SQLException, ServletException, IOException {
        int userId = getSessionUserId(request);
        List<Comment> list = dao.getMyComments(userId);
        request.setAttribute("commentList", list);
        request.getRequestDispatcher("/JSP/MyComments.jsp").forward(request, response);
    }

    private void searchMyComments(HttpServletRequest request, HttpServletResponse response, CommentDAO dao) 
            throws SQLException, ServletException, IOException {
        int userId = getSessionUserId(request);
        String keyword = request.getParameter("keyword");
        List<Comment> list = dao.searchMyComments(userId, keyword);
        request.setAttribute("commentList", list);
        request.setAttribute("keyword", keyword); // 用于搜索框回显
        request.getRequestDispatcher("/JSP/MyComments.jsp").forward(request, response);
    }

    private void showEditPage(HttpServletRequest request, HttpServletResponse response, CommentDAO dao) 
            throws SQLException, ServletException, IOException {
        
        String idStr = request.getParameter("commentId");
        if (idStr != null) {
            int commentId = Integer.parseInt(idStr);
            Comment comment = dao.getCommentById(commentId);
            
            if (comment != null) {
                request.setAttribute("comment", comment);
                request.getRequestDispatcher("/JSP/EditComment.jsp").forward(request, response);
            } else {
                response.sendRedirect("comment?action=list&error=notfound");
            }
        }
    }
    
    private void showAddPage(HttpServletRequest request, HttpServletResponse response, Connection conn) 
            throws SQLException, ServletException, IOException {
        int teacherWorkId = Integer.parseInt(request.getParameter("teacherId"));
        
        // 需要查出教师信息供 AddComment.jsp 回显姓名
        AdminDAO adminDAO = new AdminDAO(conn);
        Teacher teacher = adminDAO.searchByWorkId(teacherWorkId);
        
        request.setAttribute("teacher", teacher);
        request.getRequestDispatcher("/JSP/AddComment.jsp").forward(request, response);
    }
    
    private void processAddComment(HttpServletRequest request, HttpServletResponse response, CommentDAO dao) 
            throws SQLException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        int score = Integer.parseInt(request.getParameter("score"));
        String content = request.getParameter("content");

        Comment comment = new Comment();
        comment.setUser_id(user.getUser_id());
        comment.setTeacher_id(teacherId);
        comment.setScore(score);
        comment.setContent(content);

        boolean success = dao.addComment(comment);

        if (success) {
            response.sendRedirect("comment?action=list&msg=success");
        } else {
            response.sendRedirect("userDashboard?action=list&msg=fail");
        }
    }

    private void updateComment(HttpServletRequest request, HttpServletResponse response, CommentDAO dao) 
            throws SQLException, IOException {
        int userId = getSessionUserId(request);
        int commentId = Integer.parseInt(request.getParameter("commentId"));
        String content = request.getParameter("content");
        int score = Integer.parseInt(request.getParameter("score"));

        boolean success = dao.updateComment(commentId, userId, content, score);
        response.sendRedirect("comment?action=list&status=" + (success ? "updated" : "error"));
    }

    private void deleteComment(HttpServletRequest request, HttpServletResponse response, CommentDAO dao) 
            throws SQLException, IOException {
        int userId = getSessionUserId(request);
        int commentId = Integer.parseInt(request.getParameter("commentId"));

        dao.deleteComment(commentId, userId);
        response.sendRedirect("comment?action=list");
    }
    
    private void getTeacherAverage(HttpServletRequest request, HttpServletResponse response, CommentDAO dao) 
            throws SQLException, IOException {
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        double avg = dao.getAverageScore(teacherId);
        
        String result = String.format("%.1f", avg);
        
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(result);
    }

    // 辅助方法：从 Session 获取当前用户ID
    private int getSessionUserId(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) return -1; // 实际开发应拦截此情况
        return user.getUser_id();
    }
}