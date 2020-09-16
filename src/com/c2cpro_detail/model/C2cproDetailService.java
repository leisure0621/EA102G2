package com.c2cpro_detail.model;

import java.util.List;

public class C2cproDetailService {
	private I_C2cproDetailDAO dao;

	public C2cproDetailService() {
		dao = new C2cproDetailJNDIDAO();
	}

	public C2cproDetailVO addC2cproDetail(String pro_id, String spec_id, String spec_detail) {
		C2cproDetailVO c2cproDetailVO = new C2cproDetailVO();
		c2cproDetailVO.setPro_id(pro_id);
		c2cproDetailVO.setSpec_id(spec_id);
		c2cproDetailVO.setSpec_detail(spec_detail);
		dao.insert(c2cproDetailVO);
		return c2cproDetailVO;
	}

	public C2cproDetailVO updateC2cproDetail(String pro_id, String spec_id, String spec_detail) {
		C2cproDetailVO c2cproDetailVO = new C2cproDetailVO();
		c2cproDetailVO.setPro_id(pro_id);
		c2cproDetailVO.setSpec_id(spec_id);
		c2cproDetailVO.setSpec_detail(spec_detail);
		dao.update(c2cproDetailVO);
		return c2cproDetailVO;
	}

	public void deleteC2cproDetail(String pro_id) {
		dao.delete(pro_id);
	}

	public List<C2cproDetailVO> getOneC2cproDetail(String pro_id) {
		return dao.findByPrimaryKey(pro_id);
	}

	public List<C2cproDetailVO> getAll() {
		return dao.getAll();

	}
}
