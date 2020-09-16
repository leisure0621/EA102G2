package com.bid_detail.model;

import java.util.List;

public interface I_BidDetailDAO {
	 public void insert(BidDetailVO BidDetailVO);
     public List<BidDetailVO> findByBidId(String bid_id);
     public List<BidDetailVO> findByMemId(String mem_id);
     public List<BidDetailVO> getAll();
     public List<BidDetailVO> findByQuery(String query);
}
