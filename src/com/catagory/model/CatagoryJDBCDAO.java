package com.catagory.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.b2cpro_main.model.B2cproMainVO;
import com.spec_main.model.SpecMainVO;

public class CatagoryJDBCDAO implements I_CatagoryDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String pswd = "EA102G2";

	private static final String INSERT_STMT = "INSERT INTO CATAGORY (CAT_ID, CAT_DES) VALUES ('CAT' || LPAD(SEQ_CAT_ID.NEXTVAL,4,'0'), ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM CATAGORY";
	private static final String UPDATE = "UPDATE CATAGORY SET CAT_DES = ? WHERE CAT_ID = ?";
	private static final String DELETE_CAT = "DELETE FROM CATAGORY WHERE CAT_ID = ?";
	private static final String GET_Spec_ByCatId_STMT = "SELECT * FROM SPEC_MAIN WHERE CAT_ID = ?";
	private static final String GET_B2cpro_ByCatId_STMT = "SELECT * FROM B2CPRO_MAIN WHERE CAT_ID = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM CATAGORY WHERE CAT_ID = ?";

	public static void main(String[] args) {
//		System.out.println("Start inserting...");
//		Set<SpecMainVO> set = new LinkedHashSet<SpecMainVO>();
		CatagoryJDBCDAO dao = new CatagoryJDBCDAO();

		CatagoryVO catVO = new CatagoryVO();
		catVO = dao.findByPrimaryKey("CAT0001");
		System.out.println(catVO.getCat_id());
		System.out.println(catVO.getCat_des());
//		Set<B2cproMainVO> set = c.getB2cprosByCatId("CAT0002");
//		set = dao.getSpecsByCatId("CAT0002");
//
//		for (SpecMainVO spec : set) {
//			System.out.println("{");
//			System.out.println("  " + spec.getSpec_id() + ": " + spec.getSpec_des());
//			System.out.println("}");
//		}
//		System.out.println("search complete! The result is listed below...");
//		for (B2cproMainVO apro : set) {
//			System.out.println("{");
//			System.out.println("     CAT_ID: " + apro.getCat_id());
//			System.out.println("     PRO ID: " + apro.getPro_id());
//			System.out.println("     PRO NAME: " + apro.getPro_name());
//			System.out.println("     VENDOR : " + apro.getVendor_id());
//			System.out.println("     PRICE: " + apro.getRrp());
//			if (apro.getStatus() == 0)
//				System.out.println("     STATUS: " + "下架");
//			else
//				System.out.println("     STATUS: " + "上架");
//			System.out.println("     ESTABLISHED TIME: " + apro.getEst_time());
//			System.out.println("}\n");
//		}
	}

	@Override
	public void insert(CatagoryVO catVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, catVO.getCat_des());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(CatagoryVO catVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, catVO.getCat_des());
			pstmt.setString(2, catVO.getCat_id());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}

	}

	@Override
	public void delete(String cat_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(DELETE_CAT);

			pstmt.setString(1, cat_id);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<CatagoryVO> getAll() {
		List<CatagoryVO> list = new ArrayList<CatagoryVO>();
		CatagoryVO catVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				catVO = new CatagoryVO();
				catVO.setCat_id(rs.getString("CAT_ID"));
				catVO.setCat_des(rs.getString("CAT_DES"));
				list.add(catVO);
			}
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public Set<SpecMainVO> getSpecsByCatId(String cat_id) {

		Set<SpecMainVO> set = new LinkedHashSet<SpecMainVO>();
		SpecMainVO specVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_Spec_ByCatId_STMT);
			pstmt.setString(1, cat_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				specVO = new SpecMainVO();
				specVO.setCat_id(rs.getString("CAT_ID"));
				specVO.setSpec_id(rs.getString("SPEC_ID"));
				specVO.setSpec_des(rs.getString("SPEC_DES"));
				set.add(specVO);
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

		return set;
	}

	@Override
	public Set<B2cproMainVO> getB2cprosByCatId(String cat_id) {
		Set<B2cproMainVO> set = new LinkedHashSet<B2cproMainVO>();
		B2cproMainVO b2cproVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_B2cpro_ByCatId_STMT);
			pstmt.setString(1, cat_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				b2cproVO = new B2cproMainVO();
				b2cproVO.setCat_id(cat_id);
				b2cproVO.setEst_time(rs.getTimestamp("EST_TIME"));
				b2cproVO.setPro_id(rs.getString("PRO_ID"));
				b2cproVO.setPro_name(rs.getString("PRO_NAME"));
				b2cproVO.setRrp(rs.getInt("RRP"));
				b2cproVO.setStatus(rs.getInt("STATUS"));
				b2cproVO.setStock(rs.getInt("STOCK"));
				b2cproVO.setVendor_id(rs.getString("VENDOR_ID"));
				b2cproVO.setPro_des(rs.getString("PRO_DES"));
				set.add(b2cproVO);
			}
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

		return set;
	}

	@Override
	public CatagoryVO findByPrimaryKey(String cat_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CatagoryVO catVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, cat_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				catVO = new CatagoryVO();
				catVO.setCat_id(rs.getString("cat_id"));
				catVO.setCat_des(rs.getString("cat_des"));
			}
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return catVO;
	}
}
