package com.vendor.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.b2cpro_main.model.B2cproMainVO;

public class VendorDAO implements I_VendorDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO VENDOR(VENDOR_ID, VENDOR_NAME) VALUES ('V' || LPAD(SEQ_VENDOR_ID.NEXTVAL,4,'0'), ?)";
	private static final String UPDATE_STMT = "UPDATE VENDOR SET VENDOR_NAME = ? WHERE VENDOR_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM VENDOR WHERE VENDOR_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM VENDOR ORDER BY VENDOR_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM VENDOR WHERE VENDOR_ID = ?";
	private static final String GET_Pro_ByVendor_STMT = "SELECT * FROM B2CPRO_MAIN WHERE VENDOR_ID = ? ORDER BY PRO_ID";

	@Override
	public void insert(VendorVO vendorVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, vendorVO.getVendor_name());
			pstmt.executeUpdate();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, vendorVO.getVendor_name());
			pstmt.setString(2, vendorVO.getVendor_id());

			pstmt.executeUpdate();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, vendor_id);

			pstmt.executeUpdate();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, vendor_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vendorVO = new VendorVO();
				vendorVO.setVendor_id(rs.getString("VENDOR_ID"));
				vendorVO.setVendor_name(rs.getString("VENDOR_NAME"));
				vendorVO.setEst_time(rs.getTimestamp("EST_TIME"));
			}
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vendorVO = new VendorVO();
				vendorVO.setVendor_id(rs.getString("VENDOR_ID"));
				vendorVO.setVendor_name(rs.getString("VENDOR_NAME"));
				vendorVO.setEst_time(rs.getTimestamp("EST_TIME"));
				list.add(vendorVO);
			}

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
			con = ds.getConnection();
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
