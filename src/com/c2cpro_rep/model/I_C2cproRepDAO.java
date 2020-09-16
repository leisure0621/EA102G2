package com.c2cpro_rep.model;

import java.util.List;

public interface I_C2cproRepDAO {
	public void insert(C2cproRepVO c2cproRepVO);

	public void update(C2cproRepVO c2cproRepVO);

	public void delete(String rep_id);

	public C2cproRepVO findByPrimaryKey(String rep_id);

	public List<C2cproRepVO> getAll();
}
