package com.b2cso_main.model;

import java.sql.Timestamp;

public class B2cso_mainVO implements java.io.Serializable {
	private String so_id;
	private Integer type;
	private String buyer_id;
	private String status;
	private Integer delivery;
	private Timestamp est_time;
	private Integer amount;
	private String del_address;
	private String recipient;
	private Integer pay_via;
	
	public String getSo_id() {
		return so_id;
	}
	public Integer getType() {
		return type;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public String getStatus() {
		return status;
	}
	public Integer getDelivery() {
		return delivery;
	}
	public Timestamp getEst_time() {
		return est_time;
	}
	public Integer getAmount() {
		return amount;
	}
	public String getDel_address() {
		return del_address;
	}
	public String getRecipient() {
		return recipient;
	}
	public Integer getPay_via() {
		return pay_via;
	}
	public void setSo_id(String so_id) {
		this.so_id = so_id;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}
	public void setEst_time(Timestamp est_time) {
		this.est_time = est_time;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public void setDel_address(String del_address) {
		this.del_address = del_address;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public void setPay_via(Integer pay_via) {
		this.pay_via = pay_via;
	}
	
}
