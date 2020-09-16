package com.front_end_b2cpro_main.model;

import java.util.*;

import com.front_end_b2cpro_main.model.front_B2cproMainVO;
import com.pro_spec.model.ProSpecVO;

public class front_B2cproMainService {
	private front_I_B2cproMainDAO dao;

	public front_B2cproMainService() {
		dao = new front_B2cproMainDAO();
	}

	public void insert(front_B2cproMainVO pro) {
		dao.insert(pro);
	}

	public void update(front_B2cproMainVO pro) {
		dao.update(pro);
	}

	public void delete(String pro_id) {
		dao.delete(pro_id);
	}

	public front_B2cproMainVO findByPrimaryKey(String pro_id) {
		return dao.findByPrimaryKey(pro_id);
	}

	public List<front_B2cproMainVO> getAll() {
		return dao.getAll();
	}

	public List<front_B2cproMainVO> getLike(String pro_name) {
		return dao.getLike(pro_name);
	}
	
	public Set<ProSpecVO> getSpecByPro(String pro_id) {
		return dao.getSpecdByPro(pro_id);
	}

	public String insertWith(front_B2cproMainVO pro) {
		return dao.insertWithProId(pro);
	}

}
