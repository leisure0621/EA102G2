package com.b2cso_main.model;

import java.util.List;

public class B2cso_mainService {
	private I_B2cso_mainDAO dao;
	
	public B2cso_mainService() {
		dao = new B2cso_mainDAO();
	}
	
	public void insert(Integer type, String buyer_id, String status, Integer delivery,
			Integer amount, String del_address, String recipient, Integer pay_via) {
		B2cso_mainVO b2cso_mainVO = new B2cso_mainVO();
		b2cso_mainVO.setType(type);
		b2cso_mainVO.setBuyer_id(buyer_id);
		b2cso_mainVO.setStatus(status);
		b2cso_mainVO.setDelivery(delivery);
		b2cso_mainVO.setAmount(amount);
		b2cso_mainVO.setDel_address(del_address);
		b2cso_mainVO.setRecipient(recipient);
		b2cso_mainVO.setPay_via(pay_via);
		dao.insert(b2cso_mainVO);
	}
	
	public void update(String status, Integer amount, String del_address,
			Integer pay_via, String so_id) {
		B2cso_mainVO b2cso_mainVO = new B2cso_mainVO();
		b2cso_mainVO.setStatus(status);
		b2cso_mainVO.setAmount(amount);
		b2cso_mainVO.setDel_address(del_address);
		b2cso_mainVO.setPay_via(pay_via);
		b2cso_mainVO.setSo_id(so_id);
		dao.update(b2cso_mainVO);
	}
	
	public B2cso_mainVO getOneB2csoMain(String so_id) {
		return dao.findByPrimaryKey(so_id);
	}
	
	public String getLatestSoId() {
		return dao.getLatestB2cso_main();
	}
	
	public List<B2cso_mainVO> getAll() {
		return dao.getAll();
	}
}
