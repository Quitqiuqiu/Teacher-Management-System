package Servlet;

import daoPackage.AdminDAO;
import daoPackage.DBUtil;
import javaBean.Teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/userDashboard")
public class UserDashboardServlet extends HttpServlet {
	private AdminDAO adminDAO;
	
	@Override
    public void init() throws ServletException {
        super.init();
        try {
            Connection conn = DBUtil.getConnection();
            adminDAO = new AdminDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("数据库连接失败", e);
        }
    }
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }

            switch (action) {
            	case "detail":
            		showDetail(request, response);
                    break;
                case "search":
                    searchTeachers(request, response);
                    break;
                case "filter":
                    filterTeachers(request, response);
                    break;
                default:
                    listTeachers(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
	
    private void listTeachers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Teacher> teachers = adminDAO.getAllTeachers();
        request.setAttribute("teachers", teachers);
        request.getRequestDispatcher("/JSP/UserDashboard.jsp").forward(request, response);
    }
    
    //详情
    private void showDetail(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String workIdStr = request.getParameter("workId");
        
        if (workIdStr != null && !workIdStr.isEmpty()) {
            int workId = Integer.parseInt(workIdStr);
            
            Teacher teacher = adminDAO.searchByWorkId(workId);
            
            if (teacher != null) {
                request.setAttribute("teacher", teacher);
                request.getRequestDispatcher("/JSP/TeacherDetail.jsp").forward(request, response);
            } else {
                response.sendRedirect("userDashboard?action=list&msg=notfound");
            }
        } else {
            response.sendRedirect("userDashboard?action=list");
        }
    }
    
    //搜索
    private void searchTeachers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Teacher> teachers = new ArrayList<>();
        // 判断关键字是否为数字
        if (keyword != null && !keyword.isEmpty()) {
            if (isNumeric(keyword)) {
                // 如果是数字，则按 work_id 精确查询
                Teacher teacher = adminDAO.searchByWorkId(Integer.parseInt(keyword));
                if (teacher != null) {
                    teachers.add(teacher); 
                }
            } else {
                // 如果是字符串，则按 name 模糊查询
                teachers = adminDAO.searchByName(keyword);
            }
        }
        request.setAttribute("teachers", teachers);
        request.getRequestDispatcher("/JSP/UserDashboard.jsp").forward(request, response);
    }
    
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str); // 尝试将字符串解析为整数
            return true;
        } catch (NumberFormatException e) {
            return false; // 如果无法解析，则返回 false
        }
    }

    //筛选
    private void filterTeachers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String school = request.getParameter("school");
        String gender = request.getParameter("gender");
        String title = request.getParameter("title");
        List<Teacher> teachers = adminDAO.filterTeachers(school, gender, title);
        request.setAttribute("teachers", teachers);
        request.getRequestDispatcher("/JSP/UserDashboard.jsp").forward(request, response);
    }
}