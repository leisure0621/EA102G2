package com.bid_detail.model;

import java.sql.Timestamp;
import java.util.Date;

public class BidDetailVO implements java.io.Serializable{
	
	private String bid_id;
	private String mem_id;
	private Integer bid_price;
	private Timestamp bid_time;
	
	
	public String getBid_id() {
		return bid_id;
	}
	public void setBid_id(String bid_id) {
		this.bid_id = bid_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Integer getBid_price() {
		return bid_price;
	}
	public void setBid_price(Integer bid_price) {
		this.bid_price = bid_price;
	}
	public Timestamp getBid_time() {
		return bid_time;
	}
	public void setBid_time(Timestamp bid_time) {
		this.bid_time = bid_time;
	}
	
	
}
