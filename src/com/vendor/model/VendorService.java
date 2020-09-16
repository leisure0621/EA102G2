package com.vendor.model;

import java.util.*;

import com.b2cpro_main.model.B2cproMainVO;

public class VendorService {

	private I_VendorDAO dao;

	public VendorService() {
		dao = new VendorDAO();
	}

	public void insert(VendorVO vendorVO) {
		dao.insert(vendorVO);
	}

	public void update(VendorVO vendorVO) {
		dao.update(vendorVO);
	}

	public void delete(String vendor_id) {
		dao.delete(vendor_id);
	}

	public VendorVO findByPrimaryKey(String vendor_id) {
		return dao.findByPrimaryKey(vendor_id);
	}

	public List<VendorVO> getAll() {
		return dao.getAll();
	}

	public Set<B2cproMainVO> getProByVendor(String vendor_id) {
		return dao.getProByVendor(vendor_id);
	}

}
