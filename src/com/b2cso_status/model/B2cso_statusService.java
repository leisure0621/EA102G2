package com.b2cso_status.model;

import java.util.List;

public class B2cso_statusService {
	private I_B2cso_statusDAO dao;
	
	public B2cso_statusService() {
		dao = new B2cso_statusDAO();
	}
	
	public B2cso_statusVO getOneB2Cso_status(String status_id) {
		return dao.findByPrimaryKey(status_id);
	}
	
	public List<B2cso_statusVO> getAll() {
		return dao.getAll();
	}
}
