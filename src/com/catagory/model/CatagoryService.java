package com.catagory.model;

import java.util.List;
import java.util.Set;

import com.b2cpro_main.model.B2cproMainVO;
import com.spec_main.model.SpecMainVO;

public class CatagoryService {
	private I_CatagoryDAO dao;

	public CatagoryService() {
		dao = new CatagoryDAO();
	}

	public void insert(CatagoryVO catVO) {
		dao.insert(catVO);
	}

	public void update(CatagoryVO catVO) {
		dao.update(catVO);
	}

	public void delete(String cat_id) {
		dao.delete(cat_id);
	}

	public List<CatagoryVO> getAll() {
		return dao.getAll();
	}

	public Set<B2cproMainVO> getB2cprosByCatId(String cat_id) {
		return dao.getB2cprosByCatId(cat_id);
	}

	public Set<SpecMainVO> getSpecsByCatId(String cat_id) {
		return dao.getSpecsByCatId(cat_id);
	}

	public CatagoryVO findByPrimaryKey(String cat_id) {
		return dao.findByPrimaryKey(cat_id);
	}
}
