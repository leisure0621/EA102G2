package com.text_main.model;
import java.sql.Date;
import java.sql.Timestamp;

public class TextMainVO implements java.io.Serializable{
	private String text_id;
	private String author_id;
	private String title;
	private String content;
	private Timestamp est_time;
	private Integer status;
	public String getText_id() {
		return text_id;
	}
	public void setText_id(String text_id) {
		this.text_id = text_id;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp  getEst_time() {
		return est_time;
	}
	public void setEst_time(Timestamp  est_time) {
		this.est_time = est_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}
