package com.promo_detail.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;

public class PromoDetailJNDIDAO implements I_PromoDetailDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO PROMO_DETAIL(PROMO_ID, PRO_ID, PROMO_PRICE) VALUES(?, ?, ?)";
	private static final String GET_ONE_FOR_UPDATE = "SELECT * FROM PROMO_DETAIL WHERE PROMO_ID =? AND PRO_ID = ?";
	private static final String UPDATE = "UPDATE PROMO_DETAIL SET PROMO_PRICE =? WHERE PROMO_ID =? AND PRO_ID = ?";
	private static final String DELETE = "DELETE FROM PROMO_DETAIL WHERE PROMO_ID = ? AND PRO_ID = ? ";
	private static final String GET_ONE_BY_PROMO_ID_STMT = "SELECT * FROM PROMO_DETAIL WHERE PROMO_ID =?";
	private static final String GET_ONE_BY_PRO_ID_STMT = "SELECT * FROM PROMO_DETAIL WHERE PRO_ID =? ORDER BY PROMO_ID";
	private static final String GET_ALL_STMT = "SELECT * FROM PROMO_DETAIL ORDER BY PROMO_ID";
	private static final String GET_QUERY_STMT = 
			"SELECT * FROM PROMO_DETAIL WHERE UPPER(PROMO_ID) LIKE UPPER(?) OR UPPER(PRO_ID) LIKE UPPER(?)";
	
	@Override
	public void insert(PromoDetailVO promoDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, promoDetailVO.getPromo_id());
			pstmt.setString(2, promoDetailVO.getPro_id());
			pstmt.setInt(3, promoDetailVO.getPromo_price());
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
	public PromoDetailVO getOneForUpdate(String promo_id, String pro_id) {
		PromoDetailVO promoDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_FOR_UPDATE);

			pstmt.setString(1, promo_id);
			pstmt.setString(2, pro_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				promoDetailVO = new PromoDetailVO();
				promoDetailVO.setPromo_id(rs.getString("promo_id"));
				promoDetailVO.setPro_id(rs.getString("pro_id"));
				promoDetailVO.setPromo_price(rs.getInt("promo_price"));
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
		return promoDetailVO;
	}

	@Override
	public void update(PromoDetailVO promoDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, promoDetailVO.getPromo_price());
			pstmt.setString(2, promoDetailVO.getPromo_id());
			pstmt.setString(3, promoDetailVO.getPro_id());
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
	public void delete(String promo_id, String pro_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, promo_id);
			pstmt.setString(2, pro_id);

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
	public List<PromoDetailVO> findByPromoId(String promo_id) {
		List<PromoDetailVO> list = new ArrayList<PromoDetailVO>();
		PromoDetailVO promoDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_PROMO_ID_STMT);

			pstmt.setString(1, promo_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				promoDetailVO = new PromoDetailVO();
				promoDetailVO.setPromo_id(rs.getString("promo_id"));
				promoDetailVO.setPro_id(rs.getString("pro_id"));
				promoDetailVO.setPromo_price(rs.getInt("promo_price"));
				list.add(promoDetailVO);
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
	public List<PromoDetailVO> findByProId(String pro_id) {
		List<PromoDetailVO> list = new ArrayList<PromoDetailVO>();
		PromoDetailVO promoDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_PRO_ID_STMT);
			pstmt.setString(1, pro_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				promoDetailVO = new PromoDetailVO();
				promoDetailVO.setPromo_id(rs.getString("PROMO_ID"));
				promoDetailVO.setPro_id(rs.getString("PRO_ID"));
				promoDetailVO.setPromo_price(rs.getInt("PROMO_PRICE"));
				list.add(promoDetailVO);
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
	public List<PromoDetailVO> getAll() {
		List<PromoDetailVO> list = new ArrayList<PromoDetailVO>();
		PromoDetailVO promoDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				promoDetailVO = new PromoDetailVO();
				promoDetailVO.setPromo_id(rs.getString("PROMO_ID"));
				promoDetailVO.setPro_id(rs.getString("PRO_ID"));
				promoDetailVO.setPromo_price(rs.getInt("PROMO_PRICE"));
				list.add(promoDetailVO);
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
	public List<PromoDetailVO> findByQuery(String query) {
		List<PromoDetailVO> list = new ArrayList<PromoDetailVO>();
		PromoDetailVO promoDetailVO= null;
		
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
				promoDetailVO = new PromoDetailVO();
				
				promoDetailVO.setPromo_id(rs.getString("PROMO_ID"));
				promoDetailVO.setPro_id(rs.getString("PRO_ID"));
				promoDetailVO.setPromo_price(rs.getInt("PRMO_PRICE"));
				
				list.add(promoDetailVO);
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
