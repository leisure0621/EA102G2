package com.emp_auth_detail.model;

import java.util.*;

public interface I_EmpAuthDetailDAO {
	public void insert(EmpAuthDetailVO empAuthDetailVO);
	public void update(EmpAuthDetailVO empAuthDetailVO);
	public void delete(String auth_id);
	public EmpAuthDetailVO findByPrimaryKey(String auth_id);
	public List<EmpAuthDetailVO> getAll();

}
