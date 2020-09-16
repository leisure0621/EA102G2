package com.compatibility.model;

public class CompatibilityVO implements java.io.Serializable{
	private String comp_id;
	private String specDet_id1;
	private String specDet_id2;

	public String getComp_id() {
		return comp_id;
	}

	public void setComp_id(String comp_id) {
		this.comp_id = comp_id;
	}

	public String getSpecDet_id1() {
		return specDet_id1;
	}

	public void setSpecDet_id1(String specDet_id1) {
		this.specDet_id1 = specDet_id1;
	}

	public String getSpecDet_id2() {
		return specDet_id2;
	}

	public void setSpecDet_id2(String specDet_id2) {
		this.specDet_id2 = specDet_id2;
	}
}
