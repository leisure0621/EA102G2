package com.pro_spec.model;

import java.util.*;

public class ProSpecService {
	private I_ProSpecDAO dao;

	public ProSpecService() {
		dao = new ProSpecDAO();
	}

	public void insert(ProSpecVO psVO) {
		dao.insert(psVO);
	}

	public void update(ProSpecVO psVO, String specd_id) {
		dao.update(psVO, specd_id);
	}

	public void delete(String pro_id, String specd_id) {
		dao.delete(pro_id, specd_id);
	}

	public List<ProSpecVO> getAll() {
		return dao.getAll();
	}

	public List<ProSpecVO> findByPrimaryKey(String pro_id) {
		return dao.findByPrimaryKey(pro_id);
	}

	public void deleteSpecsFromPro(String pro_id) {
		dao.deleteSpecsFromPro(pro_id);
	}
}
