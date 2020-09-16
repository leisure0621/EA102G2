package com.emp_auth.model;

public class EmpAuthVO implements java.io.Serializable{
	private String emp_id;
	private String auth_id;
	
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
}
