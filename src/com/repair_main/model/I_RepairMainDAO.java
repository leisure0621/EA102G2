package com.repair_main.model;

import java.util.List;

public interface I_RepairMainDAO {
	public RepairMainVO insert(RepairMainVO repairMainVO);
	public void update(RepairMainVO repairMainVO);
	public void delete(String repair_id);
	public RepairMainVO findByPrimaryKey(String repair_id);
	
	public List<RepairMainVO> findByMem_id(String mem_id);
	
	public List<RepairMainVO> getAll();
}
