package com.c2cpro_rep.model;


import java.util.List;

public class C2cproRepService {
	private I_C2cproRepDAO dao;

	public C2cproRepService() {
		dao = new C2cproRepJNDIDAO();

	}

	public C2cproRepVO addC2cproRep(String informant, String reported_content, byte[] picture, String case_description,
			Integer process) {
		C2cproRepVO c2cproRepVO = new C2cproRepVO();
		c2cproRepVO.setInformant(informant);
		c2cproRepVO.setReported_content(reported_content);
		c2cproRepVO.setPicture(picture);
		c2cproRepVO.setCase_description(case_description);
		c2cproRepVO.setProcess(process);
		//c2cproRepVO.setFinish_time(finish_time);
		dao.insert(c2cproRepVO);
		return c2cproRepVO;
	}

	public C2cproRepVO updateC2cproRep(String rep_id, 
			 Integer process) {
		C2cproRepVO c2cproRepVO = new C2cproRepVO();
		c2cproRepVO.setRep_id(rep_id);
//		c2cproRepVO.setInformant(informant);
//		c2cproRepVO.setReported_content(reported_content);
//		c2cproRepVO.setPicture(picture);
//		c2cproRepVO.setCase_description(case_description);
		c2cproRepVO.setProcess(process);
		dao.update(c2cproRepVO);
		return c2cproRepVO;
	}

	public void deleteC2cproRep(String rep_id) {
		dao.delete(rep_id);
	}

	public C2cproRepVO getOneC2cproRep(String rep_id) {
		return dao.findByPrimaryKey(rep_id);
	}

	public List<C2cproRepVO> getAll() {
		return dao.getAll();
	}
}
