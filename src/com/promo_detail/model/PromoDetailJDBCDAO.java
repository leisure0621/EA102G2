package com.promo_detail.model;

import java.util.*;
import java.sql.*;

public class PromoDetailJDBCDAO implements I_PromoDetailDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";

	private static final String INSERT_STMT = "INSERT INTO PROMO_DETAIL(PROMO_ID, PRO_ID, PROMO_PRICE) VALUES(?, ?, ?)";
	private static final String GET_ONE_FOR_UPDATE = "SELECT * FROM PROMO_DETAIL WHERE PROMO_ID =? AND PRO_ID = ?";
	private static final String UPDATE = "UPDATE PROMO_DETAIL SET PROMO_PRICE =? WHERE PROMO_ID =? AND PRO_ID = ?";
	private static final String DELETE = "DELETE FROM PROMO_DETAIL WHERE PROMO_ID = ? AND PRO_ID = ? ";
	private static final String GET_ONE_BY_PROMO_ID_STMT = "SELECT * FROM PROMO_DETAIL WHERE PROMO_ID =?";
	private static final String GET_ONE_BY_PRO_ID_STMT = "SELECT * FROM PROMO_DETAIL WHERE PRO_ID =? ORDER BY PROMO_ID";
	private static final String GET_ALL_STMT = "SELECT * FROM PROMO_DETAIL ORDER BY PROMO_ID";

	@Override
	public void insert(PromoDetailVO promoDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, promoDetailVO.getPromo_id());
			pstmt.setString(2, promoDetailVO.getPro_id());
			pstmt.setInt(3, promoDetailVO.getPromo_price());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, promoDetailVO.getPromo_price());
			pstmt.setString(2, promoDetailVO.getPromo_id());
			pstmt.setString(3, promoDetailVO.getPro_id());

			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, promo_id);
			pstmt.setString(2, pro_id);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				promoDetailVO = new PromoDetailVO();
				promoDetailVO.setPromo_id(rs.getString("PROMO_ID"));
				promoDetailVO.setPro_id(rs.getString("PRO_ID"));
				promoDetailVO.setPromo_price(rs.getInt("PROMO_PRICE"));
				list.add(promoDetailVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

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

	public static void main(String[] args) {
//		PromoDetailJDBCDAO dao = new PromoDetailJDBCDAO();

		// insert
//		PromoDetailVO promoDetailVO1 = new PromoDetailVO();
//		promoDetailVO1.setPromo_id("PRM0005");
//		promoDetailVO1.setPro_id("BP0005");
//		promoDetailVO1.setPromo_price(new Integer(5000));
//		dao.insert(promoDetailVO1);
//		
//		System.out.println("Insert complete");

		// update
//		PromoDetailVO promoDetailVO2 = new PromoDetailVO();
//		promoDetailVO2.setPromo_id("PRM0005");
//		promoDetailVO2.setPro_id("BP0052");
//		promoDetailVO2.setPromo_price(3300);
//		dao.update(promoDetailVO2);
//		
//		System.out.println("Update complete");

		// get one by Promo_id
//		List<PromoDetailVO> list = dao.findByPromoId("PRM0004");
//		for(PromoDetailVO promoDetailVO : list) {
//			System.out.print(promoDetailVO.getPromo_id() + ",");
//			System.out.print(promoDetailVO.getPro_id() + ",");
//			System.out.print(promoDetailVO.getPromo_price());
//			System.out.println();
//		}

		// get one by Pro_id
//		List<PromoDetailVO> list = dao.findByProId("BP0020");
//		for(PromoDetailVO promoDetailVO : list) {
//			System.out.print(promoDetailVO.getPromo_id() + ",");
//			System.out.print(promoDetailVO.getPro_id() + ",");
//			System.out.print(promoDetailVO.getPromo_price());
//			System.out.println();
//		}

		// get all
//		List<PromoDetailVO> list = dao.getAll();
//		for (PromoDetailVO promoDetailVO : list) {
//			System.out.print(promoDetailVO.getPromo_id() + ",");
//			System.out.print(promoDetailVO.getPro_id() + ",");
//			System.out.print(promoDetailVO.getPromo_price());
//			System.out.println();
//		}

		// get one for update display
//		List<PromoDetailVO> list = dao.getOneForUpdate("PRM0001", "BP0001");
//		for (PromoDetailVO promoDetailVO : list) {
//			System.out.print(promoDetailVO.getPromo_id() + ",");
//			System.out.print(promoDetailVO.getPro_id() + ",");
//			System.out.print(promoDetailVO.getPromo_price());
//			System.out.println();
//		}
	}

	@Override
	public List<PromoDetailVO> findByQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
