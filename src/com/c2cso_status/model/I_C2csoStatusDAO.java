package com.c2cso_status.model;

import java.util.List;

public interface I_C2csoStatusDAO {
	public void insert(C2csoStatusVO c2csoStatusVO);

	public void update(C2csoStatusVO c2csoStatusVO);

	public void delete(String status_id);

	public C2csoStatusVO findByPrimaryKey(String status_id);

	public List<C2csoStatusVO> getAll();
}
