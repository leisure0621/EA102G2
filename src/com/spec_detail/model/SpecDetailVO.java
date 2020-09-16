package com.spec_detail.model;

public class SpecDetailVO implements java.io.Serializable{
	private String specdet_id;
	private String spec_id;
	private String detail_des;

	public String getSpecdet_id() {
		return specdet_id;
	}

	public void setSpecdet_id(String specdet_id) {
		this.specdet_id = specdet_id;
	}

	public String getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}

	public String getDetail_des() {
		return detail_des;
	}

	public void setDetail_des(String detail_des) {
		this.detail_des = detail_des;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detail_des == null) ? 0 : detail_des.hashCode());
		result = prime * result + ((spec_id == null) ? 0 : spec_id.hashCode());
		result = prime * result + ((specdet_id == null) ? 0 : specdet_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SpecDetailVO))
			return false;
		SpecDetailVO other = (SpecDetailVO) obj;
		if (detail_des == null) {
			if (other.detail_des != null)
				return false;
		} else if (!detail_des.equals(other.detail_des))
			return false;
		if (spec_id == null) {
			if (other.spec_id != null)
				return false;
		} else if (!spec_id.equals(other.spec_id))
			return false;
		if (specdet_id == null) {
			if (other.specdet_id != null)
				return false;
		} else if (!specdet_id.equals(other.specdet_id))
			return false;
		return true;
	}

}
