package com.compatibility.model;

import java.util.*;

public class CompatibilityService {
	private I_CompatibilityDAO dao;

	public CompatibilityService() {
		dao = new CompatibilityDAO();
	}

	public void insert(CompatibilityVO cVO) {
		dao.insert(cVO);
	}

	public void update(CompatibilityVO cVO) {
		dao.update(cVO);
	}

	public void delete(String comp_id) {
		dao.delete(comp_id);
	}

	public CompatibilityVO findByPrimaryKey(String comp_id) {
		return dao.findByPrimaryKey(comp_id);
	}

	public List<CompatibilityVO> getAll() {
		return dao.getAll();
	}
}