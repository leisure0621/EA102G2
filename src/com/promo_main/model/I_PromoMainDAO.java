package com.promo_main.model;

import java.util.List;

public interface I_PromoMainDAO {
	public void insert(PromoMainVO promoMainVO);
	public void update(PromoMainVO promoMainVO);
	public PromoMainVO findByPrimaryKey(String promo_id);
	public List<PromoMainVO> getAll();
	public List<PromoMainVO> findByQuery(String query);
}
