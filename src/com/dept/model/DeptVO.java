package com.dept.model;

public class DeptVO implements java.io.Serializable {
	private String dept_id;
	private String dept_des;
	
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_des() {
		return dept_des;
	}
	public void setDept_des(String dept_des) {
		this.dept_des = dept_des;
	}
}
