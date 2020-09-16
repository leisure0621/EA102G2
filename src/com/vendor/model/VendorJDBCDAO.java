package com.vendor.model;

import java.sql.*;
import java.util.List;

import com.b2cpro_main.model.B2cproMainVO;


import java.util.*;

public class VendorJDBCDAO implements I_VendorDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String pswd = "EA102G2";
	
	private static final String INSERT_STMT = "INSERT INTO VENDOR(VENDOR_ID, VENDOR_NAME) VALUES ('V' || LPAD(SEQ_VENDOR_ID.NEXTVAL,4,'0'), ?)";
	private static final String UPDATE_STMT = "UPDATE VENDOR SET VENDOR_NAME = ? WHERE VENDOR_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM VENDOR WHERE VENDOR_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM VENDOR ORDER BY VENDOR_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM VENDOR WHERE VENDOR_ID = ?";
	private static final String GET_Pro_ByVendor_STMT = "SELECT * FROM B2CPRO_MAIN WHERE VENDOR_ID = ? ORDER BY PRO_ID";

	public static void main(String[] args) {
//		VendorVO vendorVO = new VendorVO();
//		vendorVO.setVendor_name("吳永志2");
//		vendorVO.setVendor_id("V0022");
		VendorJDBCDAO d = new VendorJDBCDAO();
//		List<VendorVO> list = new ArrayList<VendorVO>();
//		d.insert(vendorVO);
//		d.update(vendorVO);
//		System.out.println("insert successful!");
//		System.out.println("update complete!");
//		d.delete("V0020");
//		System.out.println("delete complete!");
//		vendorVO = d.findByPrimaryKey("V0002");
//		System.out.println("Vendor id : " + vendorVO.getVendor_id());
//		System.out.println("Vendor name : " + vendorVO.getVendor_name());
//		System.out.println("Vendor establish date : " + vendorVO.getEst_time());
		Set<B2cproMainVO> set = new LinkedHashSet<B2cproMainVO>();
		set = d.getProByVendor("V0002");
		for (B2cproMainVO pro : set) {
			System.out.println("{");
			System.out.println("  PRODUCT ID : " + pro.getPro_id());
			System.out.println("  PRODUCT NAME : " + pro.getPro_name());
			System.out.println("  DESCRIPTION : " + pro.getPro_des());
			System.out.println("  CATEGORY : " + pro.getCat_id());
			System.out.println("  VENDOR : " + pro.getVendor_id());
			System.out.println("  PRICE : " + pro.getRrp());
			System.out.println("  STOCK : " + pro.getStock());
			System.out.println("  STATUS : " + pro.getStatus());
			System.out.println("  ESTABLISH　TIME : " + pro.getEst_time());
			System.out.println("}");
		}

//		list = d.getAll();
//		for(VendorVO aVendor:list) {
//			System.out.println("{");
//			System.out.println("  Vendor id : " + aVendor.getVendor_id());
//			System.out.println("  Vendor name : " + aVendor.getVendor_name());
//			System.out.println("  Establish time : " + aVendor.getEst_time());
//			System.out.println("}\n");
//		}

	}

	@Override
	public void insert(VendorVO vendorVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, vendorVO.getVendor_name());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void update(VendorVO vendorVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, vendorVO.getVendor_name());
			pstmt.setString(2, vendorVO.getVendor_id());

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
	public void delete(String vendor_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, vendor_id);

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
	public VendorVO findByPrimaryKey(String vendor_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		VendorVO vendorVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, vendor_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vendorVO = new VendorVO();
				vendorVO.setVendor_id(rs.getString("VENDOR_ID"));
				vendorVO.setVendor_name(rs.getString("VENDOR_NAME"));
				vendorVO.setEst_time(rs.getTimestamp("EST_TIME"));
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

		return vendorVO;
	}

	@Override
	public List<VendorVO> getAll() {
		List<VendorVO> list = new ArrayList<VendorVO>();
		VendorVO vendorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vendorVO = new VendorVO();
				vendorVO.setVendor_id(rs.getString("VENDOR_ID"));
				vendorVO.setVendor_name(rs.getString("VENDOR_NAME"));
				vendorVO.setEst_time(rs.getTimestamp("EST_TIME"));
				list.add(vendorVO);
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
	public Set<B2cproMainVO> getProByVendor(String vendor_id) {

		Set<B2cproMainVO> set = new LinkedHashSet<B2cproMainVO>();
		B2cproMainVO pro = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_Pro_ByVendor_STMT);
			pstmt.setString(1, vendor_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pro = new B2cproMainVO();
				pro.setCat_id(rs.getString("CAT_ID"));
				pro.setEst_time(rs.getTimestamp("EST_TIME"));
				pro.setPicture(rs.getBytes("PICTURE"));
				pro.setPro_des(rs.getString("PRO_DES"));
				pro.setPro_id(rs.getString("PRO_ID"));
				pro.setPro_name(rs.getString("PRO_NAME"));
				pro.setRrp(rs.getInt("RRP"));
				pro.setStatus(rs.getInt("STATUS"));
				pro.setStock(rs.getInt("STOCK"));
				pro.setVendor_id(rs.getString("VENDOR_ID"));
				set.add(pro);
			}
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database drive. " + ce.getMessage());
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
}
