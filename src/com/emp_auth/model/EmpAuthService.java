package com.emp_auth.model;
import java.util.*;

public class EmpAuthService {
	private I_EmpAuthDAO dao;
	
	public EmpAuthService() {
		dao = new EmpAuthDAO();
	}
	
	public void addEmpAuth(String emp_id, String auth_id) {
		EmpAuthVO empAuthVO =  new EmpAuthVO();

		empAuthVO.setEmp_id(emp_id);
		empAuthVO.setAuth_id(auth_id);
		
		dao.insert(empAuthVO);
	};
	public void delete(String emp_id, String auth_id) {
		dao.delete(emp_id, auth_id);
	};
	public List<EmpAuthVO> getOneEmpAuth(String emp_id) {
		return dao.findByPrimaryKey(emp_id);
	};
	public List<EmpAuthVO> getAll(){
		return dao.getAll();
	};
}
