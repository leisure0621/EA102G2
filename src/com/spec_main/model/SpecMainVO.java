package com.spec_main.model;

public class SpecMainVO implements java.io.Serializable{
	private String spec_id;
	private String cat_id;
	private String spec_des;

	public String getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getSpec_des() {
		return spec_des;
	}

	public void setSpec_des(String spec_des) {
		this.spec_des = spec_des;
	}
}
