package com.c2cso_rep.model;

import java.util.List;

public interface I_C2csoRepDAO {
	public void insert(C2csoRepVO c2csoRepVO);

	public void update(C2csoRepVO c2csoRepVO);

	public void delete(String rep_id);

	public C2csoRepVO findByPrimaryKey(String rep_id);

	public List<C2csoRepVO> getAll();
}
