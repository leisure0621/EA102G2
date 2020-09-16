package com.b2cso_detail.model;

public class B2cso_detailVO {
	private String so_id;
	private String pro_id;
	private Integer price;
	private Integer quantity;
	
	public String getSo_id() {
		return so_id;
	}
	public String getPro_id() {
		return pro_id;
	}
	public Integer getPrice() {
		return price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setSo_id(String so_id) {
		this.so_id = so_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
