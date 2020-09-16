package com.promo_main.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class PromoMainService {
	
	private I_PromoMainDAO dao;
	
	public PromoMainService() {
		dao = new PromoMainJNDIDAO();
	}
	
	public PromoMainVO getByPrimaryKey(String promo_id) {
		return dao.findByPrimaryKey(promo_id);
	}
	
	public List<PromoMainVO> getAll() {
		return dao.getAll();
	}
	
	public PromoMainVO addPromo(String promo_name, Date start_date, Date end_date, Integer status) {
		
		PromoMainVO promoMainVO = new PromoMainVO();
		
		promoMainVO.setPromo_name(promo_name);
		promoMainVO.setStart_date(start_date);
		promoMainVO.setEnd_date(end_date);
		promoMainVO.setStatus(status);
		dao.insert(promoMainVO);
		
		return promoMainVO;
	}
	
	public PromoMainVO update(String promo_id, String promo_name, Date start_date, Date end_date, Timestamp est_time, Integer status) {
		
		PromoMainVO promoMainVO = new PromoMainVO();
		
		promoMainVO.setPromo_id(promo_id);
		promoMainVO.setPromo_name(promo_name);
		promoMainVO.setStart_date(start_date);
		promoMainVO.setEnd_date(end_date);
		promoMainVO.setEst_time(est_time);
		promoMainVO.setStatus(status);
		dao.update(promoMainVO);
		
		return promoMainVO;
	}
	
	public List<PromoMainVO> getQuery(String query){
		return dao.getAll().stream()
				.filter(e -> e.getPromo_id().contains(query) ||
						e.getPromo_name().contains(query)
				).collect(Collectors.toList());
	};
	
	
}
