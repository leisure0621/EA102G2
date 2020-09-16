package com.b2cso_status.model;

import java.util.List;

import com.b2cso_status.model.B2cso_statusVO;

public interface I_B2cso_statusDAO {
	public B2cso_statusVO findByPrimaryKey(String status_id);
	public List<B2cso_statusVO> getAll();
}
