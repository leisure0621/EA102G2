package com.emp.model;

import java.util.*;

public interface I_EmpDAO {
	public String insert(EmpVO empVo);
	public void update(EmpVO empVo);
	public void delete(String emp_id);
	public EmpVO findByPrimaryKey(String emp_id);
	public List<EmpVO> getAll();
	public String getLatestEmp();
	public List<EmpVO> findByQuery(String query);
}
