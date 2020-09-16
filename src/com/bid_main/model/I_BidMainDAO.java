package com.bid_main.model;

import java.util.List;

public interface I_BidMainDAO {
	public void insert(BidMainVO bidVO);
	public void update(BidMainVO bidVO);
	public void updateWinner(BidMainVO bidVO);
	public void delete(String bidId);
	public BidMainVO findByPrimaryKey(String bidId);
	public List<BidMainVO> getAll();
	public List<BidMainVO> findByQuery(String query);
}
