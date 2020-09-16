package com.promo_detail.model;

public class PromoDetailVO implements java.io.Serializable {
	private String promo_id;
	private String pro_id;
	private Integer promo_price;
	
	public String getPromo_id() {
		return promo_id;
	}
	public void setPromo_id(String promo_id) {
		this.promo_id = promo_id;
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public Integer getPromo_price() {
		return promo_price;
	}
	public void setPromo_price(Integer promo_price) {
		this.promo_price = promo_price;
	}
	
	
}
