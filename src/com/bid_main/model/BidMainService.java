package com.bid_main.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class BidMainService {

	private static I_BidMainDAO dao;

	public BidMainService() {
		dao = new BidMainJNDIDAO();
	}

	public BidMainVO addBid(String bid_title, String bid_des, String pro_id, Integer start_price, Integer incr,
			Integer status, Timestamp start_time, Timestamp end_time) {

		BidMainVO bidMainVO = new BidMainVO();

		bidMainVO.setBid_title(bid_title);
		bidMainVO.setBid_des(bid_des);
		bidMainVO.setPro_id(pro_id);
		bidMainVO.setStart_price(start_price);
		bidMainVO.setIncr(incr);
		bidMainVO.setStatus(status);
		bidMainVO.setStart_time(start_time);
		bidMainVO.setEnd_time(end_time);
		dao.insert(bidMainVO);

		return bidMainVO;

	}

	public BidMainVO update(String bid_id, String bid_title, String bid_des, String pro_id, Integer start_price,
			Integer incr, Integer status, String winner, Timestamp start_time, Timestamp end_time, Timestamp est_time) {

		BidMainVO bidMainVO = new BidMainVO();

		bidMainVO.setBid_id(bid_id);
		bidMainVO.setBid_title(bid_title);
		bidMainVO.setBid_des(bid_des);
		bidMainVO.setPro_id(pro_id);
		bidMainVO.setStart_price(start_price);
		bidMainVO.setIncr(incr);
		bidMainVO.setStatus(status);
		bidMainVO.setWinner(winner);
		bidMainVO.setStart_time(start_time);
		bidMainVO.setEnd_time(end_time);
		bidMainVO.setEst_time(est_time);
		dao.update(bidMainVO);

		return bidMainVO;

	}
	
	public static void updateWinner(String bid_id, Integer status, String winner) {

		BidMainVO bidMainVO = new BidMainVO();

		bidMainVO.setBid_id(bid_id);
		bidMainVO.setStatus(status);
		bidMainVO.setWinner(winner);
		dao.updateWinner(bidMainVO);
//		return bidMainVO;
	}


	public BidMainVO getOneBid(String bid_id) {
		return dao.findByPrimaryKey(bid_id);
	}

	public List<BidMainVO> getAll() {
		return dao.getAll();
	}
	
	public void deleteBidId(String bid_id) {
		dao.delete(bid_id);
	}

	public List<BidMainVO> getQuery(String query){
		return dao.getAll().stream()
				.filter(e -> e.getBid_id().contains(query) ||
						e.getBid_title().contains(query) ||
						e.getBid_des().contains(query) ||
						e.getPro_id().contains(query)
				).collect(Collectors.toList());
	};
}
