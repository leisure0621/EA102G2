package com.promo_main.model;

import java.util.*;
import java.sql.*;
import java.sql.Date;

public class PromoMainJDBCDAO implements I_PromoMainDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	
	private static final String INSERT_STMT = "INSERT INTO PROMO_MAIN(PROMO_ID, PROMO_NAME, START_DATE, END_DATE, STATUS) VALUES ('PRM' || LPAD(SEQ_PROMO_ID.NEXTVAL, 4, '0'), ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE PROMO_MAIN SET PROMO_NAME =?, START_DATE =?, END_DATE =?, EST_TIME=?, STATUS =? WHERE PROMO_ID =?";
	private static final String GET_ONE_STMT = "SELECT * FROM PROMO_MAIN WHERE PROMO_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM PROMO_MAIN ORDER BY PROMO_ID";
	
	@Override
	public void insert(PromoMainVO promoMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, promoMainVO.getPromo_name());
			pstmt.setDate(2, promoMainVO.getStart_date());
			pstmt.setDate(3, promoMainVO.getEnd_date());
			pstmt.setInt(4, promoMainVO.getStatus());
			pstmt.executeUpdate();
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace(System.err);
				}
			}
			
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, promoMainVO.getPromo_name());
			pstmt.setDate(2, promoMainVO.getStart_date());
			pstmt.setDate(3, promoMainVO.getEnd_date());
			pstmt.setTimestamp(4, promoMainVO.getEst_time());
			pstmt.setInt(5, promoMainVO.getStatus());
			pstmt.setString(6, promoMainVO.getPromo_id());
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch(SQLException se){
			throw new RuntimeException("A database error occured." + se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace(System.err);
				}
			}
			
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, promo_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				promoMainVO = new PromoMainVO();
				promoMainVO.setPromo_id(rs.getString("PROMO_ID"));
				promoMainVO.setPromo_name(rs.getString("PROMO_NAME"));
				promoMainVO.setStart_date(rs.getDate("START_DATE"));
				promoMainVO.setEnd_date(rs.getDate("END_DATE"));
				promoMainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				promoMainVO.setStatus(rs.getInt("STATUS"));
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
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
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	
	public static void main(String[] args) {
		PromoMainJDBCDAO dao = new PromoMainJDBCDAO();
		
		// insert 
//		PromoMainVO promoMainVO1 = new PromoMainVO();
//		promoMainVO1.setPromo_name("test");
//		promoMainVO1.setStart_date(java.sql.Date.valueOf("2020-07-01"));
//		promoMainVO1.setEnd_date(java.sql.Date.valueOf("2020-07-02"));
//		promoMainVO1.setStatus(1);
//		dao.insert(promoMainVO1);
//		System.out.println("insert complete");
		
		// update
//		PromoMainVO promoMainVO2 = new PromoMainVO();
//		promoMainVO2.setPromo_name("test");
//		promoMainVO2.setStart_date(java.sql.Date.valueOf("2020-08-01"));
//		promoMainVO2.setEnd_date(java.sql.Date.valueOf("2020-08-15"));
//		promoMainVO2.setStatus(new Integer(1));
//		promoMainVO2.setPromo_id("PRM0005");
//		dao.update(promoMainVO2);
//		
//		System.out.println("update complete");
		
		// search for one
//		PromoMainVO promoMainVO3 = dao.findByPrimaryKey("PRM0003");
//		System.out.println(promoMainVO3.getPromo_id() + ",");
//		System.out.println(promoMainVO3.getPromo_name() + ",");
//		System.out.println(promoMainVO3.getStart_date() + ",");
//		System.out.println(promoMainVO3.getEnd_date() + ",");
//		System.out.println(promoMainVO3.getEst_time() + ",");
//		System.out.println(promoMainVO3.getStatus());
		
		// search for all
		List<PromoMainVO> list = dao.getAll();
		for(PromoMainVO promoMainVO : list) {
			System.out.print(promoMainVO.getPromo_id() + ",");
			System.out.print(promoMainVO.getPromo_name() + ",");
			System.out.print(promoMainVO.getStart_date() + ",");
			System.out.print(promoMainVO.getEnd_date() + ",");
			System.out.print(promoMainVO.getEst_time() + ",");
			System.out.print(promoMainVO.getStatus());
			System.out.println();
		}
		
	}

	@Override
	public List<PromoMainVO> findByQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}
}
