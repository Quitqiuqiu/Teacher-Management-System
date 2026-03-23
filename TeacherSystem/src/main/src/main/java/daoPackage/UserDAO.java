package daoPackage;

import java.sql.*;

import javaBean.User;

public class UserDAO {
    private Connection conn;

    // 构造方法，注入数据库连接
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // 根据用户 ID 获取用户信息
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("email")
                    );
                } else {
                    return null;
                }
            }
        }
    }
    
    // 更新用户名
    public boolean updateUsername(int userId, String username) throws SQLException {
        String sql = "UPDATE users SET username = ? WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, userId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    // 更新密码
    public boolean updatePassword(int userId, String password) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, password); 
            pstmt.setInt(2, userId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }
    
    //更新邮箱
    public boolean updateEmail(int userId, String email) throws SQLException {
        String sql = "UPDATE users SET email = ? WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setInt(2, userId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    // 删除账户
    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }
}