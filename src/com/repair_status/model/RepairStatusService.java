package com.repair_status.model;

import java.util.List;

public class RepairStatusService {
	
	private I_RepairStatusDAO dao;
	
	public RepairStatusService() {
		dao = new RepairStatusJDBCDAO();
	}
	
	public RepairStatusVO addRepairStatus(String status_des) {
		
		RepairStatusVO repairStatusVO = new RepairStatusVO();
		
		repairStatusVO.setStatus_des(status_des);
		dao.insert(repairStatusVO);
		
		return repairStatusVO;
	}
	
	public RepairStatusVO updateRepairStatus(String status_id, String status_des) {
		
		RepairStatusVO repairStatusVO = new RepairStatusVO();
		
		repairStatusVO.setStatus_id(status_id);
		repairStatusVO.setStatus_des(status_des);
		dao.update(repairStatusVO);
		
		return repairStatusVO;
	}
	
	public void deleteRepairStatus(String status_id) {
		dao.delete(status_id);
	}
	
	public RepairStatusVO getOneRepairStatus(String status_id) {
		return dao.findByPrimaryKey(status_id);
	}
	
	public List<RepairStatusVO> getAll(){
		return dao.getAll();
	}

}
