package com.c2cso_status.model;

import java.util.List;

public class C2csoStatusService {
	private I_C2csoStatusDAO dao;

	public C2csoStatusService() {
		dao = new C2csoStatusJNDIDAO();
	}

	public C2csoStatusVO addC2csoStatus(String status_des) {
		C2csoStatusVO c2csoStatusVO = new C2csoStatusVO();
		c2csoStatusVO.setStatus_des(status_des);
		dao.insert(c2csoStatusVO);
		return c2csoStatusVO;

	}

	public C2csoStatusVO updateC2csoStatus(String status_id, String status_des) {
		C2csoStatusVO c2csoStatusVO = new C2csoStatusVO();
		c2csoStatusVO.setStatus_id(status_id);
		c2csoStatusVO.setStatus_des(status_des);
		dao.update(c2csoStatusVO);
		return c2csoStatusVO;
	}

	public void deleteC2csoStatus(String status_id) {
		dao.delete(status_id);
	}

	public C2csoStatusVO getOneC2csoStatus(String status_id) {
		return dao.findByPrimaryKey(status_id);
	}

	public List<C2csoStatusVO> getAll() {
		return dao.getAll();
	}
}
