package com.bid_main.model;

import java.sql.*;

public class BidMainVO implements java.io.Serializable {
	private String bid_id;
	private String bid_title;
	private String bid_des;
	private String pro_id;
	private Integer start_price;
	private Integer incr;
	private Integer status;
	private String winner;
	private Timestamp start_time;
	private Timestamp end_time;
	private Timestamp est_time;
	
	
	public String getBid_id() {
		return bid_id;
	}
	public void setBid_id(String bid_id) {
		this.bid_id = bid_id;
	}
	public String getBid_title() {
		return bid_title;
	}
	public void setBid_title(String bid_title) {
		this.bid_title = bid_title;
	}
	public String getBid_des() {
		return bid_des;
	}
	public void setBid_des(String bid_des) {
		this.bid_des = bid_des;
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public Integer getStart_price() {
		return start_price;
	}
	public void setStart_price(Integer start_price) {
		this.start_price = start_price;
	}
	public Integer getIncr() {
		return incr;
	}
	public void setIncr(Integer incr) {
		this.incr = incr;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;	
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public Timestamp getEst_time() {
		return est_time;
	}
	public void setEst_time(Timestamp est_time) {
		this.est_time = est_time;
	}
	
	
	@Override
	public String toString() {
		return "BidMainVO [bid_id=" + bid_id + ", bid_title=" + bid_title + ", bid_des=" + bid_des + ", pro_id="
				+ pro_id + ", start_price=" + start_price + ", incr=" + incr + ", status=" + status + ", winner="
				+ winner + ", start_time=" + start_time + ", end_time=" + end_time + ", est_time=" + est_time + "]";
	}
	
	
	
}
