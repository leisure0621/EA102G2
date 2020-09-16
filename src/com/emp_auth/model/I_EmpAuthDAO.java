package com.emp_auth.model;

import java.util.*;

public interface I_EmpAuthDAO {
	public void insert(EmpAuthVO empAuthVO);
	public void delete(String emp_id, String auth_id);
	public List<EmpAuthVO> findByPrimaryKey(String emp_id);
	public List<EmpAuthVO> getAll();
}
