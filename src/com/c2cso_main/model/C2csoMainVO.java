package com.c2cso_main.model;

import java.sql.Timestamp;

public class C2csoMainVO implements java.io.Serializable {
private String so_id;
private String pro_id;
private String buyer_id;
private Integer  quantity;
private Timestamp est_time;
private String status;
public String getSo_id() {
	return so_id;
}
public void setSo_id(String so_id) {
	this.so_id = so_id;
}
public String getPro_id() {
	return pro_id;
}
public void setPro_id(String pro_id) {
	this.pro_id = pro_id;
}
public String getBuyer_id() {
	return buyer_id;
}
public void setBuyer_id(String buyer_id) {
	this.buyer_id = buyer_id;
}
public Integer getQuantity() {
	return quantity;
}
public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}
public Timestamp getEst_time() {
	return est_time;
}
public void setEst_time(Timestamp est_time) {
	this.est_time = est_time;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

}
