package com.vendor.model;

import java.sql.*;

public class VendorVO implements java.io.Serializable {
	private String vendor_id;
	private String vendor_name;
	private Timestamp est_time;

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public Timestamp getEst_time() {
		return est_time;
	}

	public void setEst_time(Timestamp est_time) {
		this.est_time = est_time;
	}
}
