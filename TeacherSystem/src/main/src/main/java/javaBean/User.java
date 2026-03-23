package javaBean;

import java.io.Serializable;

public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int user_id;
	private String username;
	private String password;
	private String role; 
    private String email;
	
    public User() {}
    
    public User(int userId, String username, String password, String role, String email) {
        this.user_id = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }
    
    public User(String username, String password, String email) {
    	this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public int getUser_id() {
		return user_id;
	}
	
    public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
    public String getUsername() {
		return username;
	}
	
    public void setUsername(String username) {
		this.username = username;
	}
	
    public String getPassword() {
		return password;
	}
	
    public void setPassword(String password) {
		this.password = password;
	}
	
    public String getRole() {
		return role;
	}
	
    public void setRole(String role) {
		this.role = role;
	}
	
    public String getEmail() {
		return email;
	}
	
    public void setEmail(String email) {
		this.email = email;
	}
}