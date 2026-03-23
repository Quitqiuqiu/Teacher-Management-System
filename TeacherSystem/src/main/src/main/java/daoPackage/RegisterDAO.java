package daoPackage; // 替换为你的包名

import javaBean.User; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDAO {

    public void registerUser(User user) throws UserAlreadyExistsException, DataAccessException {
        String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
        String insertQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // 检查邮箱是否已存在
            checkStmt.setString(1, user.getEmail());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                throw new UserAlreadyExistsException("该邮箱已被注册");
            }

            // 执行注册
            insertStmt.setString(1, user.getUsername());
            insertStmt.setString(2, user.getEmail());
            insertStmt.setString(3, user.getPassword());

            insertStmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("数据库访问异常", e);
        }
    }
    
    // 自定义异常类
    public class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
        public UserAlreadyExistsException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public class DataAccessException extends Exception {
        public DataAccessException(String message) {
            super(message);
        }
        public DataAccessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}