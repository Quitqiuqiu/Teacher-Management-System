package Servlet;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import javaBean.User;
import daoPackage.RegisterDAO;
import daoPackage.RegisterDAO.DataAccessException;
import daoPackage.RegisterDAO.UserAlreadyExistsException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private RegisterDAO registerDAO = new RegisterDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        // 检查密码和确认密码是否一致
        if (!password.equals(confirmPassword)) {
        	request.setAttribute("error", "密码不一致！请重新注册。");
            request.getRequestDispatcher("JSP/Logister.jsp").forward(request, response);
            return;
        }

        User user = new User(username,password,email);
        try {
            registerDAO.registerUser(user);
            request.setAttribute("success", "注册成功！"); 
            request.getRequestDispatcher("JSP/Logister.jsp").forward(request, response);
        } catch (UserAlreadyExistsException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("JSP/Logister.jsp").forward(request, response);
        } catch (DataAccessException e) {
            e.printStackTrace(); 
            request.setAttribute("error", "注册失败，请重试！"); 
            request.getRequestDispatcher("JSP/Logister.jsp").forward(request, response);
        }
    }
}