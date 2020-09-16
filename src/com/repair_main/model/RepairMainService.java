package com.repair_main.model;

import java.util.ArrayList;
import java.util.List;

public class RepairMainService {
	
	private I_RepairMainDAO dao;  
	
	public RepairMainService() {  
		dao = new RepairMainJNDIDAO();
	}
	
	public RepairMainVO addRepairMain(String mem_id, String cat_id,     
			String pro_name, String description, String status_id,
			Double amount, String dev_address, String recipient, 
			Double pay_via, Double delivery) {
		
		RepairMainVO repairMainVO = new RepairMainVO();
		
		repairMainVO.setMem_id(mem_id);
		repairMainVO.setCat_id(cat_id);
		repairMainVO.setPro_name(pro_name);
		repairMainVO.setDescription(description);
		repairMainVO.setStatus_id(status_id);
		repairMainVO.setAmount(amount);
		repairMainVO.setDev_address(dev_address);
		repairMainVO.setRecipient(recipient);
		repairMainVO.setPay_via(pay_via);
		repairMainVO.setDelivery(delivery);
		repairMainVO = dao.insert(repairMainVO);
		
		
		
		return repairMainVO;
		
	}
	
	public RepairMainVO updateRepairMain(String repair_id, String mem_id,   
			String cat_id, String pro_name, String description, String status_id,
			Double amount, String dev_address, String recipient, 
			Double pay_via, Double delivery) {
		
RepairMainVO repairMainVO = new RepairMainVO();
		
		repairMainVO.setRepair_id(repair_id);  
		repairMainVO.setMem_id(mem_id);		   
		repairMainVO.setCat_id(cat_id);
		repairMainVO.setPro_name(pro_name);
		repairMainVO.setDescription(description);
		repairMainVO.setStatus_id(status_id);
		repairMainVO.setAmount(amount);
		repairMainVO.setDev_address(dev_address);
		repairMainVO.setRecipient(recipient);
		repairMainVO.setPay_via(pay_via);
		repairMainVO.setDelivery(delivery);
		dao.update(repairMainVO);
		
		return repairMainVO;
		
	}
	
	public void deleteRepairMain(String repair_id) {
		dao.delete(repair_id);
	}
	
	public RepairMainVO getOneRepairMain(String repair_id) {
		return dao.findByPrimaryKey(repair_id);
	}
	
	public List<RepairMainVO> findByMem_id(String mem_id){
		return dao.findByMem_id(mem_id);
	}
	
	public List<RepairMainVO> getAll(){
		return dao.getAll();
	}

}
