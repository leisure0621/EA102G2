package com.c2cso_main.model;

import java.util.List;

public interface I_C2csoMainDAO {

	public void insert(C2csoMainVO c2csoMainVO);
	public void update(C2csoMainVO c2csoMainVO);
	public void delete(String so_id);
	public C2csoMainVO findByPrimaryKey(String so_id);
	public List<C2csoMainVO>getAll();
}
