package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javaBean.User;
import daoPackage.DBUtil;
import daoPackage.UserDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/userProfile")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Connection conn = DBUtil.getConnection();
            userDAO = new UserDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("数据库连接失败", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null) {
                action = "view";
            }

            switch (action) {
                case "view":
                    viewUserProfile(request, response);
                    break;
                case "delete":
                	showDeleteAccountPage(request, response);
                    break;
                case "editUsername":
                	request.getRequestDispatcher("/JSP/EditUsername.jsp").forward(request, response);
                	break;
                case "editPassword":
                	request.getRequestDispatcher("/JSP/EditPassword.jsp").forward(request, response);
                	break;
                case "editEmail":
                	request.getRequestDispatcher("/JSP/EditEmail.jsp").forward(request, response);
                	break;
                default:
                    viewUserProfile(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");
    	
    	try {
    		switch (action) {
    		case "delete":
                deleteUserAccount(request, response);
                break;
    		case "updateUsername":
    			updateUsername(request, response);
    			break;
    		case "updatePassword":
    			updatePassword(request, response);
    			break;
    		case "updateEmail":
    			updateEmail(request, response);
    			break;
    		default:
    			response.sendRedirect("userProfile");
    		}
    	} catch (SQLException e) {
    		throw new ServletException(e);
    	}
    }
    
    //展示个人信息
    private void viewUserProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("JSP/Logister.jsp");
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("JSP/UserProfile.jsp").forward(request, response);
    }

    // 显示删除账户页面
    private void showDeleteAccountPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("JSP/Logister.jsp");
            return;
        }
        request.getRequestDispatcher("JSP/DeleteAccount.jsp").forward(request, response);
    }
    
    //删除账户
    private void deleteUserAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("JSP/Logister.jsp");
            return;
        }
        //验证密码
        String password = request.getParameter("password");
        if (!user.getPassword().equals(password)) {
            request.setAttribute("message", "密码不正确，无法删除账户！");
            request.getRequestDispatcher("JSP/DeleteAccount.jsp").forward(request, response);
            return;
        }

        // 执行删除操作
        boolean success = userDAO.deleteUser(user.getUser_id());

        if (success) {
            session.invalidate();
            response.sendRedirect("JSP/Logister.jsp");
        } else {
            request.setAttribute("message", "删除账户失败！");
            request.getRequestDispatcher("JSP/DeleteAccount.jsp").forward(request, response);
        }
    }
    
    // 更新用户名
    private void updateUsername(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("JSP/Logister.jsp");
            return;
        }

        String newUsername = request.getParameter("username");
        boolean success = userDAO.updateUsername(user.getUser_id(), newUsername);
        if (success) {
            user.setUsername(newUsername);
            session.setAttribute("user", user);
            response.sendRedirect("userProfile?action=view");
        } else {
            request.setAttribute("message", "用户名更新失败！");
            request.getRequestDispatcher("JSP/EditUsername.jsp").forward(request, response);
        }
    }

    // 更新密码
    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("JSP/Logister.jsp");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (!user.getPassword().equals(currentPassword)) {
            request.setAttribute("message", "当前密码不正确！");
            request.getRequestDispatcher("JSP/EditPassword.jsp").forward(request, response);
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "新密码与确认密码不一致！");
            request.getRequestDispatcher("JSP/EditPassword.jsp").forward(request, response);
            return;
        }

        boolean success = userDAO.updatePassword(user.getUser_id(), newPassword);
        if (success) {
            user.setPassword(newPassword);
            session.setAttribute("user", user);
            response.sendRedirect("userProfile?action=view");
        } else {
            request.setAttribute("message", "密码更新失败！");
            request.getRequestDispatcher("JSP/EditPassword.jsp").forward(request, response);
        }
    }
    
    // 更新邮箱
    private void updateEmail(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("JSP/Logister.jsp");
            return;
        }
        
        String newEmail = request.getParameter("email");
        String currentPassword = request.getParameter("currentPassword");
        if (!user.getPassword().equals(currentPassword)) {
            request.setAttribute("message", "当前密码不正确！");
            request.getRequestDispatcher("JSP/EditEmail.jsp").forward(request, response);
            return;
        }
        
        boolean success = userDAO.updateEmail(user.getUser_id(), newEmail);
        if (success) {
            user.setEmail(newEmail);
            session.setAttribute("user", user);
            response.sendRedirect("userProfile?action=view");
        } else {
            request.setAttribute("message", "邮箱更新失败！");
            request.getRequestDispatcher("JSP/EditEmail.jsp").forward(request, response);
        }
    }
}