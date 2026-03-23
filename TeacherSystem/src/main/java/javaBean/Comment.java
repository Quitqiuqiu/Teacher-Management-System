package javaBean;

import java.io.Serializable;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int comment_id;		//评论编号
	private int user_id;		//学生ID
	private int teacher_id;		//教师ID
	private String content;		//评价内容
	private int score;			//评价分数
	private String create_time;	//评价时间
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Comment(int user_id, int teacher_id, String content, int score) {
		super();
		this.user_id = user_id;
		this.teacher_id = teacher_id;
		this.content = content;
		this.score = score;
	}

	public Comment(int comment_id, int user_id, int teacher_id, String content, int score, String create_time) {
		super();
		this.comment_id = comment_id;
		this.user_id = user_id;
		this.teacher_id = teacher_id;
		this.content = content;
		this.score = score;
		this.create_time = create_time;
	}

	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}