package com.repair_status.model;

import java.util.List;

public interface I_RepairStatusDAO {
	public void insert(RepairStatusVO repairStatusVO);
	public void update(RepairStatusVO repairStatusVO);
	public void delete(String status_id);
	public RepairStatusVO findByPrimaryKey(String status_id);
	public List<RepairStatusVO> getAll();
}
