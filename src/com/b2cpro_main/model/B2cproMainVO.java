package com.b2cpro_main.model;

import java.sql.*;

public class B2cproMainVO implements java.io.Serializable {
	private String pro_id;
	private String pro_name;
	private String cat_id;
	private byte[] picture;
	private Integer rrp;
	private Integer stock;
	private String vendor_id;
	private Integer status;
	private String pro_des;
	private Timestamp est_time;

	public String getPro_id() {
		return pro_id;
	}

	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Integer getRrp() {
		return rrp;
	}

	public void setRrp(Integer rrp) {
		this.rrp = rrp;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPro_des() {
		return pro_des;
	}

	public void setPro_des(String pro_des) {
		this.pro_des = pro_des;
	}

	public Timestamp getEst_time() {
		return est_time;
	}

	public void setEst_time(Timestamp est_time) {
		this.est_time = est_time;
	}
}
