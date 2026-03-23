package daoPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javaBean.Comment;

public class CommentDAO {

    private Connection conn;

    // 构造方法，注入数据库连接
    public CommentDAO(Connection conn) {
        this.conn = conn;
    }
    

     //提交新的评价
    public boolean addComment(Comment comment) throws SQLException {
        String sql = "INSERT INTO comments (user_id, teacher_id, content, score, create_time) VALUES (?, ?, ?, ?, NOW())";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, comment.getUser_id());
            pstmt.setInt(2, comment.getTeacher_id());
            pstmt.setString(3, comment.getContent());
            pstmt.setInt(4, comment.getScore());
            
            return pstmt.executeUpdate() > 0;
        }
    }
    
    //查看我的所有评价
    public List<Comment> getMyComments(int userId) throws SQLException {
        String sql = "SELECT * FROM comments WHERE user_id = ? ORDER BY create_time DESC";
        List<Comment> comments = new ArrayList<>();
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    comments.add(mapResultSetToComment(rs));
                }
            }
        }
        return comments;
    }
    
    //根据comment_id获取评价
    public Comment getCommentById(int commentId) throws SQLException {
        String sql = "SELECT * FROM comments WHERE comment_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, commentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Comment comment = new Comment();
                    comment.setComment_id(rs.getInt("comment_id"));
                    comment.setUser_id(rs.getInt("user_id"));
                    comment.setTeacher_id(rs.getInt("teacher_id"));
                    comment.setContent(rs.getString("content"));
                    comment.setScore(rs.getInt("score"));
                    comment.setCreate_time(rs.getString("create_time"));
                    return comment;
                }
            }
        }
        // 如果没有找到对应的 ID，返回 null，方便 Servlet 层进行判断
        return null;
    }

    //搜索我的评价 (根据关键字模糊查询内容)
    public List<Comment> searchMyComments(int userId, String keyword) throws SQLException {
        String sql = "SELECT * FROM comments WHERE user_id = ? AND content LIKE ? ORDER BY create_time DESC";
        List<Comment> comments = new ArrayList<>();
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, "%" + keyword + "%"); // 模糊匹配
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    comments.add(mapResultSetToComment(rs));
                }
            }
        }
        return comments;
    }

    //修改我的评价 (必须校验 user_id 确保安全)
    public boolean updateComment(int commentId, int userId, String content, int score) throws SQLException {
        String sql = "UPDATE comments SET content = ?, score = ? WHERE comment_id = ? AND user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content);
            pstmt.setInt(2, score);
            pstmt.setInt(3, commentId);
            pstmt.setInt(4, userId);
            return pstmt.executeUpdate() > 0;
        }
    }

    //删除我的评价 (必须校验 user_id 确保安全)
    public boolean deleteComment(int commentId, int userId) throws SQLException {
        String sql = "DELETE FROM comments WHERE comment_id = ? AND user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, commentId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        }
    }

     //根据教师工号计算平均评分
    public double getAverageScore(int teacherId) throws SQLException {
        String sql = "SELECT AVG(score) as avg_score FROM comments WHERE teacher_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teacherId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 如果没有评分，rs.getDouble 会返回 0.0
                    return rs.getDouble("avg_score");
                }
            }
        }
        return 0.0;
    }

    //内部辅助方法：将结果集映射为 Bean 对象
    private Comment mapResultSetToComment(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setComment_id(rs.getInt("comment_id"));
        comment.setUser_id(rs.getInt("user_id"));
        comment.setTeacher_id(rs.getInt("teacher_id"));
        comment.setContent(rs.getString("content"));
        comment.setScore(rs.getInt("score"));
        comment.setCreate_time(rs.getString("create_time"));
        return comment;
    }
}