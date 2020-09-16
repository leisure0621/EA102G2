package com.promo_detail.model;

import java.util.List;
import java.util.stream.Collectors;

public class PromoDetailService {
	
	private I_PromoDetailDAO dao;
	
	public PromoDetailService() {
		dao = new PromoDetailJNDIDAO();
	}
	
	public List<PromoDetailVO> getByPromoId(String promo_id){
		return dao.findByPromoId(promo_id);
	}
	
	public List<PromoDetailVO> getByProId(String pro_id){
		return dao.findByProId(pro_id);
	}
	
	public List<PromoDetailVO> getAll(){
		return dao.getAll();
	}
	
	public PromoDetailVO getOneForUpdate(String promo_id, String pro_id) {
		return dao.getOneForUpdate(promo_id, pro_id);
	}
	
	public PromoDetailVO addPromoDetail(String promo_id, String pro_id, Integer promo_price) {
		PromoDetailVO promoDetailVO = new PromoDetailVO();
		
		promoDetailVO.setPromo_id(promo_id);
		promoDetailVO.setPro_id(pro_id);
		promoDetailVO.setPromo_price(promo_price);
		dao.insert(promoDetailVO);
		
		return promoDetailVO;
	}
	
	public PromoDetailVO update(String promo_id, String pro_id, Integer promo_price) {
		PromoDetailVO promoDetailVO = new PromoDetailVO();
		
		promoDetailVO.setPromo_id(promo_id);
		promoDetailVO.setPro_id(pro_id);
		promoDetailVO.setPromo_price(promo_price);
		dao.update(promoDetailVO);
		
		return promoDetailVO;
	}
	
	public void deletePromoDetail(String promo_id, String pro_id) {
		dao.delete(promo_id, pro_id);
	}
	
	public List<PromoDetailVO> getQuery(String query){
		return dao.getAll().stream()
				.filter(e -> e.getPromo_id().contains(query) ||
						e.getPro_id().contains(query)
				).collect(Collectors.toList());
	};
}
