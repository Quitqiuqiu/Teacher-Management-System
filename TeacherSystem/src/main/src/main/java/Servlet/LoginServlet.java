package Servlet;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import javaBean.User;
import daoPackage.LoginDAO;
import daoPackage.LoginDAO.InvalidPasswordException;
import daoPackage.LoginDAO.UserNotFoundException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LoginDAO loginDAO = new LoginDAO();
        User user = null;
        String errorMessage = null;
        try {
            user = loginDAO.validateUser(email, password);
        } catch (UserNotFoundException e) {
            errorMessage = "用户不存在，请检查您的邮箱。";
        } catch (InvalidPasswordException e) {
            errorMessage = "密码错误，请重试。";
        } catch (Exception e) {
            errorMessage = "登录时发生错误，请稍后再试。";
        }

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // 根据角色跳转到不同的页面
            if ("管理员".equals(user.getRole())) {
                response.sendRedirect("admin?action=list");
            } else {
                response.sendRedirect("userDashboard?action=list");
            }
        } else {
            if (errorMessage != null) {
                response.sendRedirect("JSP/Logister.jsp?error=" + URLEncoder.encode(errorMessage, "UTF-8"));
            } else {
                response.sendRedirect("JSP/Logister.jsp");
            }
        }
    }
}