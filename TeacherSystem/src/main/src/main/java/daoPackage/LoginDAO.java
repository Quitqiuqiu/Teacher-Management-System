package daoPackage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javaBean.User;

public class LoginDAO {
	// 验证用户的用户名和密码
    public User validateUser(String email, String password) throws UserNotFoundException, InvalidPasswordException {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password"); 
                // 验证密码
                if (password.equals(storedPassword)) {
                    user = new User(rs.getInt("user_id"),rs.getString("username"),rs.getString("password"),rs.getString("role"),email);
                    return user;
                } else {
                    throw new InvalidPasswordException("密码错误");
                }
            } else {
                throw new UserNotFoundException("用户不存在");
            }
        }catch (SQLException e) {
        	throw new DataAccessException("数据库访问异常", e);
        }
    }
    
    // 自定义异常类
    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidPasswordException extends Exception {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }
    
    public static class DataAccessException extends RuntimeException {
        public DataAccessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}