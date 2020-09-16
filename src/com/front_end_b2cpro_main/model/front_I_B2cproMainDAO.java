package com.front_end_b2cpro_main.model;

import java.util.*;

import com.front_end_b2cpro_main.model.front_B2cproMainVO;
import com.pro_spec.model.ProSpecVO;

public interface front_I_B2cproMainDAO {
	public void insert(front_B2cproMainVO pro);

	public void update(front_B2cproMainVO pro);

	public void delete(String pro_id);

	public front_B2cproMainVO findByPrimaryKey(String pro_id);
	
	public List<front_B2cproMainVO> getLike(String pro_name);

	public List<front_B2cproMainVO> getAll();

	public Set<ProSpecVO> getSpecdByPro(String pro_id);

	public String insertWithProId(front_B2cproMainVO pro);
}
