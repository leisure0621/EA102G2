package com.emp.model;

import java.sql.*;

public class EmpVO {
	private String emp_id;
	private String emp_firstname;
	private String emp_lastname;
	private String dept_no;
	private Integer salary;
	private Date hiredate;
	private String password;
	private Integer status;
	
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_firstname() {
		return emp_firstname;
	}
	public void setEmp_firstname(String emp_firstname) {
		this.emp_firstname = emp_firstname;
	}
	public String getEmp_lastname() {
		return emp_lastname;
	}
	public void setEmp_lastname(String emp_lastname) {
		this.emp_lastname = emp_lastname;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
