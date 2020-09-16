package com.emp.model;

import java.util.*;

import com.mem.model.MemDAO;

import java.sql.Date;

public class EmpService {
	private I_EmpDAO dao;
	
	public EmpService() {
		dao = new EmpDAO();
	}
	
	public void addEmp(
			String emp_firstname, String emp_lastname, String dept_no, 
			Integer salary, Date hiredate, Integer status) {
		EmpVO empVo =  new EmpVO();

		empVo.setEmp_firstname(emp_firstname);
		empVo.setEmp_lastname(emp_lastname);
		empVo.setDept_no(dept_no);
		empVo.setSalary(salary);
		empVo.setHiredate(hiredate);
		empVo.setStatus(status);
		
		dao.insert(empVo);
	};
	public void updateEmp(
			String emp_id, String emp_firstname, String emp_lastname, String dept_no, 
			Integer salary, Date hiredate, String password, Integer status) {
		EmpVO empVo =  new EmpVO();
		
		empVo.setEmp_id(emp_id);
		empVo.setEmp_firstname(emp_firstname);
		empVo.setEmp_lastname(emp_lastname);
		empVo.setDept_no(dept_no);
		empVo.setSalary(salary);
		empVo.setHiredate(hiredate);
		empVo.setPassword(password);
		empVo.setStatus(status);
		
		dao.update(empVo);
	};
	public void delete(String emp_id) {
		dao.delete(emp_id);
	};
	public EmpVO getOneEmp(String emp_id) {
		return dao.findByPrimaryKey(emp_id);
	};
	public List<EmpVO> getAll(){
		return dao.getAll();
	};
	public String getLatestEmp(){
		return dao.getLatestEmp();
	};
	public List<EmpVO> getQuery(String query){
		return dao.findByQuery(query);
	};
}
