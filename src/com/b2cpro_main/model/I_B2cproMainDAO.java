package com.b2cpro_main.model;

import java.util.*;

import com.pro_spec.model.ProSpecVO;

public interface I_B2cproMainDAO {
	public void insert(B2cproMainVO pro);

	public void update(B2cproMainVO pro);

	public void delete(String pro_id);

	public B2cproMainVO findByPrimaryKey(String pro_id);

	public List<B2cproMainVO> getAll();

	public Set<ProSpecVO> getSpecdByPro(String pro_id);

	public String insertWithProId(B2cproMainVO pro);
}
