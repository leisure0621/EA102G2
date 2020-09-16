package com.spec_main.model;

import java.util.*;

import com.spec_detail.model.SpecDetailVO;

public class SpecMainService {
	private I_SpecMainDAO dao;

	public SpecMainService() {
		dao = new SpecMainDAO();
	}

	public void insert(SpecMainVO specVO) {
		dao.insert(specVO);
	}

	public void update(SpecMainVO specVO) {
		dao.update(specVO);
	}

	public void delete(String spec_id) {
		dao.delete(spec_id);
	}

	public SpecMainVO findByPrimaryKey(String spec_id) {
		return dao.findByPrimaryKey(spec_id);
	}

	public List<SpecMainVO> getAll() {
		return dao.getAll();
	}

	public Set<SpecDetailVO> getSpecDBySpecId(String spec_id) {
		return dao.getSpecDBySpecId(spec_id);
	}
}
