package com.mem.model;

import java.util.*;

public interface I_MemDAO {
	public void insert(MemVO memVO);
	public void update(MemVO memVO);
	public void delete(String mem_id);
	public MemVO findByPrimaryKey(String mem_id);
	public List<MemVO> findByEmail(String email);
	public List<MemVO> findByQuery(String query);
	public List<MemVO> getAll();
	
}
