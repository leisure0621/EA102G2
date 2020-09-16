package com.bid_detail.model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.DataSource;

public class BidDetailJNDIDAO implements I_BidDetailDAO {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		}catch(NamingException e){
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO BID_DETAIL (BID_ID, MEM_ID, BID_PRICE) VALUES(?,?,?)";
	private static final String GET_ONE_BYBIDID_STMT = "SELECT * FROM BID_DETAIL WHERE BID_ID = ? ORDER BY BID_TIME";
	private static final String GET_ONE_BYMEMID_STMT = "SELECT * FROM BID_DETAIL WHERE MEM_ID = ? ORDER BY BID_TIME DESC";
	private static final String GET_ALL_STMT = "SELECT * FROM BID_DETAIL ORDER BY BID_ID";
	private static final String GET_QUERY_STMT = "SELECT * FROM BID_DETAIL WHERE UPPER(BID_ID) LIKE UPPER(?) OR UPPER(MEM_ID) LIKE UPPER(?)";
	
	@Override
	public void insert(BidDetailVO BidDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, BidDetailVO.getBid_id());
			pstmt.setString(2, BidDetailVO.getMem_id());
			pstmt.setInt(3, BidDetailVO.getBid_price());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public List<BidDetailVO> findByBidId(String bid_id) {
		List<BidDetailVO> list = new ArrayList<BidDetailVO>();
		BidDetailVO bidDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BYBIDID_STMT);

			pstmt.setString(1, bid_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bidDetailVO = new BidDetailVO();
				bidDetailVO.setBid_id(rs.getString("bid_id"));
				bidDetailVO.setMem_id(rs.getString("mem_id"));
				bidDetailVO.setBid_price(rs.getInt("bid_price"));
				bidDetailVO.setBid_time(rs.getTimestamp("bid_time"));
				list.add(bidDetailVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return list;
	}

	@Override
	public List<BidDetailVO> findByMemId(String mem_id) {
		List<BidDetailVO> list = new ArrayList<BidDetailVO>();
		BidDetailVO bidDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BYMEMID_STMT);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bidDetailVO = new BidDetailVO();
				bidDetailVO.setBid_id(rs.getString("bid_id"));
				bidDetailVO.setMem_id(rs.getString("mem_id"));
				bidDetailVO.setBid_price(rs.getInt("bid_price"));
				bidDetailVO.setBid_time(rs.getTimestamp("bid_time"));
				list.add(bidDetailVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<BidDetailVO> getAll() {
		List<BidDetailVO> list = new ArrayList<BidDetailVO>();
		BidDetailVO bidDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bidDetailVO = new BidDetailVO();
				bidDetailVO.setBid_id(rs.getString("bid_id"));
				bidDetailVO.setMem_id(rs.getString("mem_id"));
				bidDetailVO.setBid_price(rs.getInt("bid_price"));
				bidDetailVO.setBid_time(rs.getTimestamp("bid_time"));
				list.add(bidDetailVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<BidDetailVO> findByQuery(String query) {
		List<BidDetailVO> list = new ArrayList<BidDetailVO>();
		BidDetailVO bidDetailVO= null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_QUERY_STMT);

			pstmt.setString(1, "%"+query+"%");	
			pstmt.setString(2, "%"+query+"%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				bidDetailVO = new BidDetailVO();
				
				bidDetailVO.setBid_id(rs.getString("bid_id"));
				bidDetailVO.setMem_id(rs.getString("mem_id"));
				bidDetailVO.setBid_price(rs.getInt("bid_price"));
				bidDetailVO.setBid_time(rs.getTimestamp("bid_time"));
				
				list.add(bidDetailVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}
