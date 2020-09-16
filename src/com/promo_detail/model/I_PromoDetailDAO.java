package com.promo_detail.model;

import java.util.List;

public interface I_PromoDetailDAO {
	 public void insert(PromoDetailVO promoDetailVO);
	 public PromoDetailVO getOneForUpdate(String promo_id, String pro_id);
     public void update(PromoDetailVO promoDetailVO);
     public void delete(String promo_id, String pro_id);
     public List<PromoDetailVO> findByPromoId(String promo_id);
     public List<PromoDetailVO> findByProId(String pro_id);
     public List<PromoDetailVO> getAll();
     public List<PromoDetailVO> findByQuery(String query);
}
