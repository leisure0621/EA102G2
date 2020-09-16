package com.c2cpro_main.model;

import java.util.List;

public class C2cproMainService {
	private I_C2cproMainDAO dao;

	public C2cproMainService() {
		dao = new C2cproMainJNDIDAO();
	}

	public C2cproMainVO addC2cproMain(String mem_id, String pro_name, String cat_id, Integer quantity, Double price,
			byte[] photo, String description, Integer delivery) {
		C2cproMainVO c2cproMainVO = new C2cproMainVO();
		c2cproMainVO.setMem_id(mem_id);
		c2cproMainVO.setPro_name(pro_name);
		c2cproMainVO.setCat_id(cat_id);
		c2cproMainVO.setQuantity(quantity);
		c2cproMainVO.setPrice(price);
		c2cproMainVO.setPhoto(photo);
		c2cproMainVO.setDescription(description);

		c2cproMainVO.setDelivery(delivery);
		dao.insert(c2cproMainVO);

		return c2cproMainVO;
	}

	public C2cproMainVO updateC2cproMain(String pro_id, String mem_id, String pro_name, String cat_id, Integer quantity,
			Double price, byte[] photo, String description, Integer status, Integer delivery) {
		C2cproMainVO c2cproMainVO = new C2cproMainVO();
		c2cproMainVO.setPro_id(pro_id);
		c2cproMainVO.setMem_id(mem_id);
		c2cproMainVO.setPro_name(pro_name);
		c2cproMainVO.setCat_id(cat_id);
		c2cproMainVO.setQuantity(quantity);
		c2cproMainVO.setPrice(price);
		c2cproMainVO.setPhoto(photo);
		c2cproMainVO.setDescription(description);
		c2cproMainVO.setStatus(status);
		c2cproMainVO.setDelivery(delivery);
		dao.update(c2cproMainVO);
		return c2cproMainVO;
	}
	public C2cproMainVO update_proStatus(String pro_id,Integer status) {
		C2cproMainVO c2cproMainVO = new C2cproMainVO();
		c2cproMainVO.setPro_id(pro_id);
		c2cproMainVO.setStatus(status);
		dao.update_status(c2cproMainVO);
		return c2cproMainVO;
	}
	public void deleteC2cproMain(String pro_id) {
		dao.delete(pro_id);
	}

	public C2cproMainVO getOneC2cproMain(String pro_id) {
		return dao.findByPrimaryKey(pro_id);

	}

	public List<C2cproMainVO> getAll() {
		return dao.getAll();
	}

	public List<C2cproMainVO> getLike(String pro_name) {
		return dao.getLike(pro_name);
	}

	public List<B2cjoinDetailVO> getDetail(String pro_name) {
		return dao.getDetail(pro_name);
	}

	public String insertWithSpecs(C2cproMainVO c2cproMainVO) {
		return dao.insertWithSpecs(c2cproMainVO);
	}
	
}
