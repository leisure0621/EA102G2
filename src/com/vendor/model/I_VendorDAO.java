package com.vendor.model;

import java.util.*;

import com.b2cpro_main.model.B2cproMainVO;

public interface I_VendorDAO {
	public void insert(VendorVO vendorVO);

	public void update(VendorVO vendorVO);

	public void delete(String vendor_id);

	public VendorVO findByPrimaryKey(String vendor_id);

	public List<VendorVO> getAll();

	public Set<B2cproMainVO> getProByVendor(String vendor_id);
}
