package com.spec_detail.model;

import java.util.*;

public class SpecDetailService {
	private I_SpecDetailDAO dao;

	public SpecDetailService() {
		dao = new SpecDetailDAO();
	}

	public void insert(SpecDetailVO specdVO) {
		dao.insert(specdVO);
	}

	public void update(SpecDetailVO specdVO) {
		dao.update(specdVO);
	}

	public void delete(String specd_id) {
		dao.delete(specd_id);
	}

	public SpecDetailVO findByPrimaryKey(String specd_id) {
		return dao.findByPrimaryKey(specd_id);
	}

	public List<SpecDetailVO> getAll() {
		return dao.getAll();
	}

	public String insertWithPK(SpecDetailVO specdVO) {
		return dao.insertWithPK(specdVO);
	}

}