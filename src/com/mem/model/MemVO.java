package com.mem.model;
import java.sql.Date;
import java.sql.Timestamp;

public class MemVO implements java.io.Serializable{
	private String  mem_id;
	private String  first_name;
	private String  last_name;
	private String  nickname;
	private String  tel;
	private String  mob;
	private String  email;
	private String  password;
	private String  shop_name;
	private String  credit_card;
	private Date  credit_card_expires;
	private Integer  credit_card_cvc;
	private String  bank_account;
	private Timestamp  est_time;
	private String  address;
	private Integer  authority;
	
	public String getMem_id() {
		return mem_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getNickname() {
		return nickname;
	}
	public String getTel() {
		return tel;
	}
	public String getMob() {
		return mob;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getShop_name() {
		return shop_name;
	}
	public String getCredit_card() {
		return credit_card;
	}
	public Date getCredit_card_expires() {
		return credit_card_expires;
	}
	public Integer getCredit_card_cvc() {
		return credit_card_cvc;
	}
	public String getBank_account() {
		return bank_account;
	}
	public Timestamp getEst_time() {
		return est_time;
	}
	public String getAddress() {
		return address;
	}
	public Integer getAuthority() {
		return authority;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setMob(String mob) {
		this.mob = mob;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}
	public void setCredit_card_expires(Date credit_card_expires) {
		this.credit_card_expires = credit_card_expires;
	}
	public void setCredit_card_cvc(Integer credit_card_cvc) {
		this.credit_card_cvc = credit_card_cvc;
	}
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	public void setEst_time(Timestamp est_time) {
		this.est_time = est_time;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAuthority(Integer authority) {
		this.authority = authority;
	}
	
	
	
}
