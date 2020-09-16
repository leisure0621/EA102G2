package com.spec_main.model;

import java.util.*;

import com.spec_detail.model.SpecDetailVO;

public interface I_SpecMainDAO {
	public void insert(SpecMainVO specVO);

	public void update(SpecMainVO specVO);

	public void delete(String spec_id);

	public SpecMainVO findByPrimaryKey(String spec_id);

	public List<SpecMainVO> getAll();

	public Set<SpecDetailVO> getSpecDBySpecId(String spec_id);
}
