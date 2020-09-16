package com.b2cpro_main.model;

import java.util.*;

import com.pro_spec.model.ProSpecVO;

public class B2cproMainService {
	private I_B2cproMainDAO dao;

	public B2cproMainService() {
		dao = new B2cproMainDAO();
	}

	public void insert(B2cproMainVO pro) {
		dao.insert(pro);
	}

	public void update(B2cproMainVO pro) {
		dao.update(pro);
	}

	public void delete(String pro_id) {
		dao.delete(pro_id);
	}

	public B2cproMainVO findByPrimaryKey(String pro_id) {
		return dao.findByPrimaryKey(pro_id);
	}

	public List<B2cproMainVO> getAll() {
		return dao.getAll();
	}

	public Set<ProSpecVO> getSpecByPro(String pro_id) {
		return dao.getSpecdByPro(pro_id);
	}

	public String insertWithProId(B2cproMainVO pro) {
		return dao.insertWithProId(pro);
	}

}
