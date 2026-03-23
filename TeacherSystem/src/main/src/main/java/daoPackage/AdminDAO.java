package daoPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaBean.Teacher;

public class AdminDAO {
    private Connection conn;

    // 通过构造方法注入 Connection
    public AdminDAO(Connection conn) {
        this.conn = conn;
    }

    // 查询所有教师信息
    public List<Teacher> getAllTeachers() {
        String sql = "SELECT * FROM teachers";
        List<Teacher> teachers = new ArrayList<>();
        try (ResultSet rs = DBUtil.query(sql)) {
            while (rs.next()) {
                Teacher teacher = new Teacher(
                    rs.getInt("work_id"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getString("school"),
                    rs.getString("title")
                );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("Teachers list fetched, size: " + teachers.size());
        return teachers;
    }

    // 根据 work_id 精确查询教师信息
    public Teacher searchByWorkId(int keyword) {
        String sql = "SELECT * FROM teachers WHERE work_id = ?";
        Teacher teacher = null;

        try (ResultSet rs = DBUtil.query(sql, keyword)) {
            if (rs.next()) {
                teacher = new Teacher(
                    rs.getInt("work_id"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getString("school"),
                    rs.getString("title")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    // 根据 name 模糊查询教师信息
    public List<Teacher> searchByName(String name) {
        String sql = "SELECT * FROM teachers WHERE name LIKE ?";
        List<Teacher> teachers = new ArrayList<>();

        try (ResultSet rs = DBUtil.query(sql, "%" + name + "%")) {
            while (rs.next()) {
                Teacher teacher = new Teacher(
                    rs.getInt("work_id"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getString("school"),
                    rs.getString("title")
                );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    // 筛选教师信息
    public List<Teacher> filterTeachers(String school, String gender, String title) throws SQLException {
    	StringBuilder sql = new StringBuilder("SELECT * FROM teachers WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (school != null && !school.isEmpty()) {
            sql.append(" AND school LIKE ?"); 
            params.add("%" + school + "%"); 
        }
        if (gender != null && !gender.isEmpty()) {
            sql.append(" AND gender = ?");
            params.add(gender);
        }
        if (title != null && !title.isEmpty()) {
            sql.append(" AND title = ?");
            params.add(title);
        }
        
        List<Teacher> teachers = new ArrayList<>();
        try (ResultSet rs = DBUtil.query(sql.toString(), params.toArray())) {
            while (rs.next()) {
                teachers.add(new Teacher(
                    rs.getInt("work_id"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getString("school"),
                    rs.getString("title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return teachers;
    }

    // 增加教师信息
    public boolean addTeacher(Teacher teacher) {
        String sql = "INSERT INTO teachers (work_id, name, gender, school, title) VALUES (?, ?, ?, ?, ?)";
        try {
            int rows = DBUtil.executeUpdate(sql, teacher.getWork_id(), teacher.getName(), teacher.getGender(), teacher.getSchool(), teacher.getTitle());
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除教师信息
    public boolean deleteTeacher(int workId) {
        String sql = "DELETE FROM teachers WHERE work_id = ?";
        try {
            int rows = DBUtil.executeUpdate(sql, workId);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新教师信息
    public boolean updateTeacher(Teacher teacher) {
        String sql = "UPDATE teachers SET name = ?, gender = ?, school = ?, title = ? WHERE work_id = ?";
        try {
            int rows = DBUtil.executeUpdate(sql, teacher.getName(), teacher.getGender(), teacher.getSchool(), teacher.getTitle(), teacher.getWork_id());
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}