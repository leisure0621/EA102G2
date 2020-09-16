package com.promo_main.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;
import java.sql.Date;

public class PromoMainJNDIDAO implements I_PromoMainDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO PROMO_MAIN(PROMO_ID, PROMO_NAME, START_DATE, END_DATE, STATUS) VALUES ('PRM' || LPAD(SEQ_PROMO_ID.NEXTVAL, 4, '0'), ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE PROMO_MAIN SET PROMO_NAME =?, START_DATE =?, END_DATE =?, EST_TIME=?, STATUS =? WHERE PROMO_ID =?";
	private static final String GET_ONE_STMT = "SELECT * FROM PROMO_MAIN WHERE PROMO_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM PROMO_MAIN ORDER BY PROMO_ID";
	private static final String GET_QUERY_STMT = 
			"SELECT * FROM PROMO_MAIN WHERE UPPER(PROMO_ID) LIKE UPPER(?) OR UPPER(PROMO_NAME) LIKE UPPER(?)";
	

	@Override
	public void insert(PromoMainVO promoMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, promoMainVO.getPromo_name());
			pstmt.setDate(2, promoMainVO.getStart_date());
			pstmt.setDate(3, promoMainVO.getEnd_date());
			pstmt.setInt(4, promoMainVO.getStatus());
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
	public void update(PromoMainVO promoMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, promoMainVO.getPromo_name());
			pstmt.setDate(2, promoMainVO.getStart_date());
			pstmt.setDate(3, promoMainVO.getEnd_date());
			pstmt.setTimestamp(4, promoMainVO.getEst_time());
			pstmt.setInt(5, promoMainVO.getStatus());
			pstmt.setString(6, promoMainVO.getPromo_id());
			pstmt.executeUpdate();

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
	}

	@Override
	public PromoMainVO findByPrimaryKey(String promo_id) {
		PromoMainVO promoMainVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, promo_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				promoMainVO = new PromoMainVO();
				promoMainVO.setPromo_id(rs.getString("PROMO_ID"));
				promoMainVO.setPromo_name(rs.getString("PROMO_NAME"));
				promoMainVO.setStart_date(rs.getDate("START_DATE"));
				promoMainVO.setEnd_date(rs.getDate("END_DATE"));
				promoMainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				promoMainVO.setStatus(rs.getInt("STATUS"));
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

		return promoMainVO;
	}

	@Override
	public List<PromoMainVO> getAll() {
		List<PromoMainVO> list = new ArrayList<PromoMainVO>();
		PromoMainVO promoMainVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				promoMainVO = new PromoMainVO();
				promoMainVO.setPromo_id(rs.getString("PROMO_ID"));
				promoMainVO.setPromo_name(rs.getString("PROMO_NAME"));
				promoMainVO.setStart_date(rs.getDate("START_DATE"));
				promoMainVO.setEnd_date(rs.getDate("END_DATE"));
				promoMainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				promoMainVO.setStatus(rs.getInt("STATUS"));
				list.add(promoMainVO);
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
	public List<PromoMainVO> findByQuery(String query) {
		List<PromoMainVO> list = new ArrayList<PromoMainVO>();
		PromoMainVO promoMainVO= null;
		
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
				promoMainVO = new PromoMainVO();
				
				promoMainVO.setPromo_id(rs.getString("PROMO_ID"));
				promoMainVO.setPromo_name(rs.getString("PROMO_NAME"));
				promoMainVO.setStart_date(rs.getDate("START_DATE"));
				promoMainVO.setEnd_date(rs.getDate("END_DATE"));
				promoMainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				promoMainVO.setStatus(rs.getInt("STATUS"));
				
				list.add(promoMainVO);
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
