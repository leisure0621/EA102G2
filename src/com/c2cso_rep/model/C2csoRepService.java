package com.c2cso_rep.model;

import java.util.List;

public class C2csoRepService {
	private I_C2csoRepDAO dao;

	public C2csoRepService() {
		dao = new C2csoRepJNDIDAO();
	}

	public C2csoRepVO addC2csoRep(String informant, String so_id, String case_description) {
		C2csoRepVO c2csoRepVO = new C2csoRepVO();
		c2csoRepVO.setInformant(informant);
		c2csoRepVO.setSo_id(so_id);
		c2csoRepVO.setCase_description(case_description);
		dao.insert(c2csoRepVO);
		return c2csoRepVO;
	}

	public C2csoRepVO updateC2csoRep(String rep_id, String informant, String so_id, String case_description,
			Integer process) {
		C2csoRepVO c2csoRepVO = new C2csoRepVO();
		c2csoRepVO.setRep_id(rep_id);
		c2csoRepVO.setInformant(informant);
		c2csoRepVO.setSo_id(so_id);
		c2csoRepVO.setCase_description(case_description);
		c2csoRepVO.setProcess(process);
		dao.update(c2csoRepVO);
		return c2csoRepVO;
	}

	public void deleteC2csoRep(String rep_id) {
		dao.delete(rep_id);

	}

	public C2csoRepVO getOneC2csoRep(String rep_id) {
		return dao.findByPrimaryKey(rep_id);
	}

	public List<C2csoRepVO> getAll() {
		return dao.getAll();
	}
}
