package com.spec_detail.model;

import java.util.*;

public interface I_SpecDetailDAO {
	public void insert(SpecDetailVO specdVO);

	public String insertWithPK(SpecDetailVO specdVO);

	public void update(SpecDetailVO specdVO);

	public void delete(String specd_id);

	public SpecDetailVO findByPrimaryKey(String specd_id);

	public List<SpecDetailVO> getAll();
}
