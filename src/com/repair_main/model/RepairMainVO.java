package com.repair_main.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class RepairMainVO implements Serializable{
	private String repair_id;
	private String mem_id;
	private String cat_id;
	private String pro_name;
	private String description;
	private String status_id;
	private Timestamp est_time;
	private Double amount;
	private String dev_address;
	private String recipient;
	private Double pay_via;
	private Double delivery;
	
	public String getRepair_id() {
		return repair_id;
	}
	public void setRepair_id(String repair_id) {
		this.repair_id = repair_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus_id() {
		return status_id;
	}
	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}
	public Timestamp getEst_time() {
		return est_time;
	}
	public void setEst_time(Timestamp est_time) {
		this.est_time = est_time;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDev_address() {
		return dev_address;
	}
	public void setDev_address(String dev_address) {
		this.dev_address = dev_address;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public Double getPay_via() {
		return pay_via;
	}
	public void setPay_via(Double pay_via) {
		this.pay_via = pay_via;
	}
	public Double getDelivery() {
		return delivery;
	}
	public void setDelivery(Double delivery) {
		this.delivery = delivery;
	}
	
	

}
