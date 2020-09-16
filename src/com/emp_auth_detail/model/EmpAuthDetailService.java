package com.emp_auth_detail.model;

import java.util.List;

public class EmpAuthDetailService {
	private I_EmpAuthDetailDAO dao;
	
	public EmpAuthDetailService() {
		dao = new EmpAuthDetailDAO();
	}
	
	public void addEmpAuthDetail(String auth_des, String auth_id) {
		EmpAuthDetailVO empAuthDetailVO =  new EmpAuthDetailVO();

		empAuthDetailVO.setAuth_id(auth_id);
		empAuthDetailVO.setAuth_des(auth_des);
		
		dao.insert(empAuthDetailVO);
	};
	public void updateEmpAuthDetail(String auth_des, String auth_id) {
		EmpAuthDetailVO empAuthDetailVO =  new EmpAuthDetailVO();
		
		empAuthDetailVO.setAuth_id(auth_id);
		empAuthDetailVO.setAuth_des(auth_des);
		
		dao.update(empAuthDetailVO);
	};
	public void delete(String auth_id) {
		dao.delete(auth_id);
	};
	public EmpAuthDetailVO getOneEmpAuthDetail(String auth_id) {
		return dao.findByPrimaryKey(auth_id);
	};
	public List<EmpAuthDetailVO> getAll(){
		return dao.getAll();
	};
}
