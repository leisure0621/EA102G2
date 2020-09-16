package com.c2cpro_detail.model;

import java.util.List;

public interface I_C2cproDetailDAO {
	public void insert(C2cproDetailVO c2cproDetailVO);
	public void update(C2cproDetailVO c2cproDetailVO);
	public void delete(String pro_id);
	public List<C2cproDetailVO> findByPrimaryKey(String pro_id);
	public List<C2cproDetailVO> getAll();

}
