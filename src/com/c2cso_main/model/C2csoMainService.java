package com.c2cso_main.model;

import java.util.List;

public class C2csoMainService {
	private I_C2csoMainDAO dao;

	public C2csoMainService() {
		dao = new C2csoMainJNDIDAO();
	}

	public C2csoMainVO addC2csoMain(String pro_id, String buyer_id, Integer quantity, String status) {
		C2csoMainVO c2csoMainVO = new C2csoMainVO();
		c2csoMainVO.setPro_id(pro_id);
		c2csoMainVO.setBuyer_id(buyer_id);
		c2csoMainVO.setQuantity(quantity);
		c2csoMainVO.setStatus(status);
		dao.insert(c2csoMainVO);
		return c2csoMainVO;

	}

	public C2csoMainVO updateC2csoMain(String so_id, String pro_id, String buyer_id, Integer quantity, String status) {
		C2csoMainVO c2csoMainVO = new C2csoMainVO();
		c2csoMainVO.setSo_id(so_id);
		c2csoMainVO.setPro_id(pro_id);
		c2csoMainVO.setBuyer_id(buyer_id);
		c2csoMainVO.setQuantity(quantity);
		c2csoMainVO.setStatus(status);
		dao.update(c2csoMainVO);
		return c2csoMainVO;
	}

	public void deleteC2csoMain(String so_id) {
		dao.delete(so_id);
	}

	public C2csoMainVO getOneC2csoMain(String so_id) {
		return dao.findByPrimaryKey(so_id);
	}

	public List<C2csoMainVO> getAll() {
		return dao.getAll();

	}
}
