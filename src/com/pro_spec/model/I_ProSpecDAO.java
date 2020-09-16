package com.pro_spec.model;

import java.util.*;

public interface I_ProSpecDAO {
	public void insert(ProSpecVO psVO);

	public void update(ProSpecVO psVO, String specd_id);

	public void delete(String pro_id, String specd_id);

	public void deleteSpecsFromPro(String pro_id);

	public List<ProSpecVO> getAll();

	public List<ProSpecVO> findByPrimaryKey(String pro_id);
}
