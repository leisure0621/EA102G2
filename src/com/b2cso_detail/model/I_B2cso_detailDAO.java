package com.b2cso_detail.model;

import java.util.List;

public interface I_B2cso_detailDAO {
	public void insert(B2cso_detailVO b2cso_detail);
	public void update(B2cso_detailVO b2cso_detail);	
	public List<B2cso_detailVO> findByPrimaryKey(String so_id);
	public List<B2cso_detailVO> getAll();
}
