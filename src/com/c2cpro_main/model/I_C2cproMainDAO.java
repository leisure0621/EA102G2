package com.c2cpro_main.model;

import java.util.*;

import com.b2cpro_main.model.B2cproMainVO;

public interface I_C2cproMainDAO {
  public void insert(C2cproMainVO c2cproMainVO);
  public void update(C2cproMainVO c2cproMainVO);
  public void delete(String pro_id);
  public C2cproMainVO findByPrimaryKey(String pro_id);
  public List<C2cproMainVO> getAll();
  public List<C2cproMainVO> getLike(String pro_name);
  public List<B2cjoinDetailVO> getDetail(String pro_name);
  public String insertWithSpecs(C2cproMainVO c2cproMainVO);
 public void update_status(C2cproMainVO c2cproMainVO);	
	
}
