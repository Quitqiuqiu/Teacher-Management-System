package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaBean.Teacher;
import daoPackage.AdminDAO;
import daoPackage.DBUtil;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
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
            	case "list":
            		listTeachers(request, response);
            		break;
	            case "search":
	                searchTeachers(request, response);
	                break;
	            case "filter":
	                filterTeachers(request, response);
	                break;
	            case "add":
	            	showAddForm(request, response);
	            	break;
	            case "edit":
	                showEditForm(request, response);
	                break;
	            default:
	            	listTeachers(request, response);
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
                case "add":
                    addTeacher(request, response);
                    break;
                case "delete":
	            	deleteTeacher(request, response);
	            	break;
                case "update":
                    updateTeacher(request, response);
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
        request.getRequestDispatcher("/JSP/AdminDashboard.jsp").forward(request, response);
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
        request.getRequestDispatcher("/JSP/AdminDashboard.jsp").forward(request, response);
    }
    
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str); 
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //筛选
    private void filterTeachers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String school = request.getParameter("school");
        String gender = request.getParameter("gender");
        String title = request.getParameter("title");
        List<Teacher> teachers = adminDAO.filterTeachers(school, gender, title);
        request.setAttribute("teachers", teachers);
        request.getRequestDispatcher("/JSP/AdminDashboard.jsp").forward(request, response);
    }
    
    // 删除教师
    private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int workId = Integer.parseInt(request.getParameter("workId"));
        boolean success = adminDAO.deleteTeacher(workId);

     // 使用 request 设置删除结果
        if (success) {
            request.setAttribute("message", "删除成功！");
        } else {
            request.setAttribute("message", "删除失败！");
        }

        request.getRequestDispatcher("admin?action=list").forward(request, response);
    }
    
    // 显示教师新增表单
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/JSP/AddTeacher.jsp").forward(request, response);
    }
    
    // 处理添加教师请求
    private void addTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String workId = request.getParameter("workId");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String school = request.getParameter("school");
        String title = request.getParameter("title");

        Teacher teacher = new Teacher(Integer.parseInt(workId), name, gender, school, title);
        adminDAO.addTeacher(teacher);

        response.sendRedirect("admin?action=list");
    }

    // 显示教师编辑表单
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int workId = Integer.parseInt(request.getParameter("workId"));
        Teacher teacher = adminDAO.searchByWorkId(workId);
        request.setAttribute("teacher", teacher);
        request.getRequestDispatcher("/JSP/EditTeacher.jsp").forward(request, response);
    }

    // 处理更新教师请求
    private void updateTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int workId = Integer.parseInt(request.getParameter("workId"));
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String school = request.getParameter("school");
        String title = request.getParameter("title");

        Teacher teacher = new Teacher(workId, name, gender, school, title);
        adminDAO.updateTeacher(teacher);

        response.sendRedirect("admin?action=list");
    }
}