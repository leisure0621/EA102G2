package com.compatibility.model;

import java.util.*;

public interface I_CompatibilityDAO {
	public void insert(CompatibilityVO cVO);

	public void update(CompatibilityVO cVO);

	public void delete(String comp_id);

	public CompatibilityVO findByPrimaryKey(String comp_id);

	public List<CompatibilityVO> getAll();

	public Set<CompatibilityVO> getCompsByPro(String pro_id);

}
