package com.b2cso_detail.model;

import java.util.*;
import java.sql.Date;

public class B2cso_detailService {
	private I_B2cso_detailDAO dao;
	
	public B2cso_detailService() {
		dao = new B2cso_detailDAO();
	}
	
	public void addDetail(Integer price, String pro_id, Integer quantity, String so_id) {
		B2cso_detailVO b2cso_detailVO = new B2cso_detailVO();
		b2cso_detailVO.setPrice(price);
		b2cso_detailVO.setPro_id(pro_id);
		b2cso_detailVO.setQuantity(quantity);
		b2cso_detailVO.setSo_id(so_id);
		
		dao.insert(b2cso_detailVO);
	}
	
	public void updateDetail(Integer price, String pro_id, Integer quantity, String so_id) {
		B2cso_detailVO b2cso_detailVO = new B2cso_detailVO();
		b2cso_detailVO.setPrice(price);
		b2cso_detailVO.setPro_id(pro_id);
		b2cso_detailVO.setQuantity(quantity);
		b2cso_detailVO.setSo_id(so_id);
		
		dao.update(b2cso_detailVO);
	}
	
	public List<B2cso_detailVO> getOneDetails(String so_id){
		return dao.findByPrimaryKey(so_id);
	}
	
	public List<B2cso_detailVO> getAll() {
		return dao.getAll();
	}
}
