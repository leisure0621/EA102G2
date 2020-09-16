package com.bid_main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BidMainJNDIDAO implements I_BidMainDAO{
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
		"INSERT INTO BID_MAIN (BID_ID, BID_TITLE, BID_DES, PRO_ID, START_PRICE, INCR, STATUS, WINNER, START_TIME, END_TIME) VALUES ('BD' || LPAD(SEQ_BID_ID.NEXTVAL, 4, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT BID_ID, BID_TITLE, BID_DES, PRO_ID, START_PRICE, INCR, STATUS, WINNER, START_TIME, END_TIME, EST_TIME FROM BID_MAIN order by BID_ID";
	private static final String GET_ONE_STMT = 
		"SELECT BID_ID, BID_TITLE, BID_DES, PRO_ID, START_PRICE, INCR, STATUS, WINNER, START_TIME, END_TIME, EST_TIME FROM BID_MAIN where BID_ID = ?";
	private static final String DELETE = 
		"DELETE FROM BID_MAIN where BID_ID = ?";
	private static final String UPDATE = 
		"UPDATE BID_MAIN set BID_TITLE = ? , BID_DES = ? , PRO_ID = ?, START_PRICE = ?, INCR = ?, STATUS = ?, WINNER = ?, START_TIME = ?, END_TIME = ?, EST_TIME= ? WHERE BID_ID = ?";
	private static final String UPDATE_WINNER = 
		"UPDATE BID_MAIN set WINNER = ?, STATUS = ? WHERE BID_ID = ?";
	private static final String GET_QUERY_STMT = 
			"SELECT * FROM BID_MAIN WHERE UPPER(BID_ID) LIKE UPPER(?) OR UPPER(BID_TITLE) LIKE UPPER(?) OR UPPER(BID_DES) LIKE UPPER(?) OR UPPER(PRO_ID) LIKE UPPER(?)";
	
	@Override
	public void insert(BidMainVO bidmainVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bidmainVO.getBid_title());
			pstmt.setString(2, bidmainVO.getBid_des());
			pstmt.setString(3, bidmainVO.getPro_id());
			pstmt.setInt(4, bidmainVO.getStart_price());
			pstmt.setInt(5, bidmainVO.getIncr());
			pstmt.setInt(6, bidmainVO.getStatus());
			pstmt.setString(7, bidmainVO.getWinner());
			pstmt.setTimestamp(8, bidmainVO.getStart_time());
			pstmt.setTimestamp(9, bidmainVO.getEnd_time());
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void update(BidMainVO bidmainVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, bidmainVO.getBid_title());
			pstmt.setString(2, bidmainVO.getBid_des());
			pstmt.setString(3, bidmainVO.getPro_id());
			pstmt.setInt(4, bidmainVO.getStart_price());
			pstmt.setInt(5, bidmainVO.getIncr());
			pstmt.setInt(6, bidmainVO.getStatus());
			pstmt.setString(7, bidmainVO.getWinner());
			pstmt.setTimestamp(8, bidmainVO.getStart_time());
			pstmt.setTimestamp(9, bidmainVO.getEnd_time());
			pstmt.setTimestamp(10, bidmainVO.getEst_time());
			pstmt.setString(11, bidmainVO.getBid_id());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void updateWinner(BidMainVO bidmainVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_WINNER);

			pstmt.setString(1, bidmainVO.getWinner());
			pstmt.setInt(2, bidmainVO.getStatus());
			pstmt.setString(3, bidmainVO.getBid_id());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void delete(String bidId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, bidId);

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public BidMainVO findByPrimaryKey(String bidId) {

		BidMainVO bidmainVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, bidId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				bidmainVO = new BidMainVO();
				bidmainVO.setBid_id(rs.getString("BID_ID"));
				bidmainVO.setBid_title(rs.getString("BID_TITLE"));
				bidmainVO.setBid_des(rs.getString("BID_DES"));
				bidmainVO.setPro_id(rs.getString("PRO_ID"));
				bidmainVO.setStart_price(rs.getInt("START_PRICE"));
				bidmainVO.setIncr(rs.getInt("INCR"));
				bidmainVO.setStatus(rs.getInt("STATUS"));
				bidmainVO.setWinner(rs.getString("WINNER"));
				bidmainVO.setStart_time(rs.getTimestamp("START_TIME"));
				bidmainVO.setEnd_time(rs.getTimestamp("END_TIME"));
				bidmainVO.setEst_time(rs.getTimestamp("EST_TIME"));

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return bidmainVO;
	}

	@Override
	public List<BidMainVO> getAll() {
		List<BidMainVO> list = new ArrayList<BidMainVO>();
		BidMainVO bidmainVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bidmainVO = new BidMainVO();
				bidmainVO.setBid_id(rs.getString("BID_ID"));
				bidmainVO.setBid_title(rs.getString("BID_TITLE"));
				bidmainVO.setBid_des(rs.getString("BID_DES"));
				bidmainVO.setPro_id(rs.getString("PRO_ID"));
				bidmainVO.setStart_price(rs.getInt("START_PRICE"));
				bidmainVO.setIncr(rs.getInt("INCR"));
				bidmainVO.setStatus(rs.getInt("STATUS"));
				bidmainVO.setWinner(rs.getString("WINNER"));
				bidmainVO.setStart_time(rs.getTimestamp("START_TIME"));
				bidmainVO.setEnd_time(rs.getTimestamp("END_TIME"));
				bidmainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				list.add(bidmainVO); 
				
				// Store the row in the list
			}
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public List<BidMainVO> findByQuery(String query) {
		List<BidMainVO> list = new ArrayList<BidMainVO>();
		BidMainVO bidMainVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_QUERY_STMT);

			pstmt.setString(1, "%"+query+"%");	
			pstmt.setString(2, "%"+query+"%");
			pstmt.setString(3, "%"+query+"%");
			pstmt.setString(4, "%"+query+"%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				bidMainVO = new BidMainVO();
				
				bidMainVO.setBid_id(rs.getString("BID_ID"));
				bidMainVO.setBid_title(rs.getString("BID_TITLE"));
				bidMainVO.setBid_des(rs.getString("BID_DES"));
				bidMainVO.setPro_id(rs.getString("PRO_ID"));
				bidMainVO.setStart_price(rs.getInt("START_PRICE"));
				bidMainVO.setIncr(rs.getInt("INCR"));
				bidMainVO.setStatus(rs.getInt("STATUS"));
				bidMainVO.setWinner(rs.getString("WINNER"));
				bidMainVO.setStart_time(rs.getTimestamp("START_TIME"));
				bidMainVO.setEnd_time(rs.getTimestamp("END_TIME"));
				bidMainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				
				list.add(bidMainVO);
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
