package com.text_rep.model;
import java.sql.Date;
import java.sql.Timestamp;

public class TextRepVO implements java.io.Serializable{
	private String rep_id;
	private String informant;
	private String text_id;
	private byte[] picture;
	private String case_description;
	private Integer process;
	private Timestamp est_time;
	private Timestamp finish_time;
	public String getRep_id() {
		return rep_id;
	}
	public void setRep_id(String rep_id) {
		this.rep_id = rep_id;
	}
	public String getInformant() {
		return informant;
	}
	public void setInformant(String informant) {
		this.informant = informant;
	}
	public String getText_id() {
		return text_id;
	}
	public void setText_id(String text_id) {
		this.text_id = text_id;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
	public String getCase_description() {
		return case_description;
	}
	public void setCase_description(String case_description) {
		this.case_description = case_description;
	}
	public Integer getProcess() {
		return process;
	}
	public void setProcess(Integer process) {
		this.process = process;
	}
	public Timestamp getEst_time() {
		return est_time;
	}
	public void setEst_time(Timestamp est_time) {
		this.est_time = est_time;
	}
	public Timestamp getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(Timestamp finish_time) {
		this.finish_time = finish_time;
	}
	
	
	

}
