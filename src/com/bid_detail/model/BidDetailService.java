package com.bid_detail.model;

import java.util.List;
import java.util.stream.Collectors;

public class BidDetailService {
	
	private I_BidDetailDAO dao;
	
	public BidDetailService() {
		dao = new BidDetailJNDIDAO();
	}
	
	public List<BidDetailVO> getByBidId(String bid_id){
		return dao.findByBidId(bid_id);
	}
	
	
	public List<BidDetailVO> getByMemId(String mem_id){
		return dao.findByMemId(mem_id);
	}
	
	public List<BidDetailVO> getAll(){
		return dao.getAll();
	}
	

	public BidDetailVO addBidDetail(String bid_id, String mem_id, Integer bid_price) {
		
		BidDetailVO bidDetailVO = new BidDetailVO();
		bidDetailVO.setBid_id(bid_id);
		bidDetailVO.setMem_id(mem_id);
		bidDetailVO.setBid_price(bid_price);
		dao.insert(bidDetailVO);

		return bidDetailVO;
	}
	
	public List<BidDetailVO> getQuery(String query){
		return dao.getAll().stream()
				.filter(e -> e.getBid_id().contains(query) ||
						e.getMem_id().contains(query)
				).collect(Collectors.toList());
	};
}

