package com.promo_main.model;

import java.sql.Date;
import java.sql.Timestamp;

public class PromoMainVO implements java.io.Serializable{
	private String promo_id;
	private String promo_name;
	private Date start_date;
	private Date end_date;
	private Timestamp est_time;
	private Integer status;
	
	public String getPromo_id() {
		return promo_id;
	}
	public void setPromo_id(String promo_id) {
		this.promo_id = promo_id;
	}
	public String getPromo_name() {
		return promo_name;
	}
	public void setPromo_name(String promo_name) {
		this.promo_name = promo_name;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Timestamp getEst_time() {
		return est_time;
	}
	public void setEst_time(Timestamp est_time) {
		this.est_time = est_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
