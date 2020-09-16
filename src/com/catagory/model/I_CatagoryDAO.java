package com.catagory.model;

import java.util.*;

import com.b2cpro_main.model.B2cproMainVO;
import com.spec_main.model.SpecMainVO;

public interface I_CatagoryDAO {
	public void insert(CatagoryVO catVO);

	public void update(CatagoryVO catVO);

	public void delete(String cat_id);

	public List<CatagoryVO> getAll();

	public Set<B2cproMainVO> getB2cprosByCatId(String cat_id);

	public Set<SpecMainVO> getSpecsByCatId(String cat_id);

	public CatagoryVO findByPrimaryKey(String cat_id);
	
}
