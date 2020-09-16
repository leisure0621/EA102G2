package com.dept.model;

import java.util.*;

public interface I_DeptDAO {
	public void insert(DeptVO deptVO);
	public void update(DeptVO deptVO);	
	public void delete(String dept_id);
	public DeptVO findByPrimaryKey(String dept_id);
	public List<DeptVO> getAll();
}
