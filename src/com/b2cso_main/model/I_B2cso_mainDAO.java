package com.b2cso_main.model;

import java.util.List;

public interface I_B2cso_mainDAO {
	public void insert(B2cso_mainVO b2cso_mainVO);
	public void update(B2cso_mainVO b2cso_mainVO);	
	public B2cso_mainVO findByPrimaryKey(String so_id);
	public String getLatestB2cso_main();
	public List<B2cso_mainVO> getAll();
}
