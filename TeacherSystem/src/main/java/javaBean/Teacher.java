package javaBean;

import java.io.Serializable;

public class Teacher implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int work_id;    // 教师工号
    private String name;    // 姓名
    private String gender;  // 性别
    private String school;  // 学院
    private String title;   // 职称

    // 无参构造方法
    public Teacher() {}

    // 有参构造方法
    public Teacher(int workId, String name, String gender, String school, String title) {
        this.work_id = workId;
        this.name = name;
        this.gender = gender;
        this.school = school;
        this.title = title;
    }

	public int getWork_id() {
		return work_id;
	}

	public void setWork_id(int work_id) {
		this.work_id = work_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Teacher [work_id=" + work_id + ", name=" + name + ", gender=" + gender + ", school=" + school
				+ ", title=" + title + ", user_id=" + "]";
	}
}