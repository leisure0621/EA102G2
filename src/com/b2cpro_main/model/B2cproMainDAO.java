package com.b2cpro_main.model;

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

import com.pro_spec.model.ProSpecVO;

public class B2cproMainDAO implements I_B2cproMainDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO B2CPRO_MAIN(PRO_ID,PRO_NAME,CAT_ID,PICTURE,RRP,STOCK,VENDOR_ID,STATUS,PRO_DES) VALUES ('BP' || LPAD(SEQ_BPRO_ID.NEXTVAL,4,'0'), ? , ? , ? , ? , ? , ? , ? , ?)";
	private static final String UPDATE_STMT = "UPDATE B2CPRO_MAIN SET PRO_NAME = ? , CAT_ID = ? , PICTURE = ? , RRP = ? ,STOCK = ?, VENDOR_ID = ? , STATUS = ? , PRO_DES = ? WHERE PRO_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM B2CPRO_MAIN WHERE PRO_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM B2CPRO_MAIN ORDER BY PRO_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM B2CPRO_MAIN WHERE PRO_ID = ?";
	private static final String Get_SpecD_ByPro_STMT = "SELECT * FROM PRO_SPEC WHERE PRO_ID = ?";

	@Override
	public void insert(B2cproMainVO pro) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, pro.getPro_name());
			pstmt.setString(2, pro.getCat_id());
			pstmt.setBytes(3, pro.getPicture());
			pstmt.setInt(4, pro.getRrp());
			pstmt.setInt(5, pro.getStock());
			pstmt.setString(6, pro.getVendor_id());
			pstmt.setInt(7, pro.getStatus());
			pstmt.setString(8, pro.getPro_des());

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
	public void update(B2cproMainVO pro) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, pro.getPro_name());
			pstmt.setString(2, pro.getCat_id());
			pstmt.setBytes(3, pro.getPicture());
			pstmt.setInt(4, pro.getRrp());
			pstmt.setInt(5, pro.getStock());
			pstmt.setString(6, pro.getVendor_id());
			pstmt.setInt(7, pro.getStatus());
			pstmt.setString(8, pro.getPro_des());
			pstmt.setString(9, pro.getPro_id());

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
	public void delete(String pro_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, pro_id);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
	public B2cproMainVO findByPrimaryKey(String pro_id) {

		B2cproMainVO pro = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, pro_id);

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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return pro;
	}

	@Override
	public List<B2cproMainVO> getAll() {
		List<B2cproMainVO> list = new ArrayList<B2cproMainVO>();
		B2cproMainVO pro = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
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
				list.add(pro);
			}

			// Handle any driver errors
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
	public Set<ProSpecVO> getSpecdByPro(String pro_id) {
		Set<ProSpecVO> set = new LinkedHashSet<ProSpecVO>();
		ProSpecVO psVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(Get_SpecD_ByPro_STMT);
			pstmt.setString(1, pro_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				psVO = new ProSpecVO();
				psVO.setPro_id(rs.getString("PRO_ID"));
				psVO.setSpecDet_id(rs.getString("SPECDET_ID"));
				set.add(psVO);
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

	@Override
	public String insertWithProId(B2cproMainVO pro) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String pro_id = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String[] cols = { "PRO_ID" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, pro.getPro_name());
			pstmt.setString(2, pro.getCat_id());
			pstmt.setBytes(3, pro.getPicture());
			pstmt.setInt(4, pro.getRrp());
			pstmt.setInt(5, pro.getStock());
			pstmt.setString(6, pro.getVendor_id());
			pstmt.setInt(7, pro.getStatus());
			pstmt.setString(8, pro.getPro_des());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				pro_id = rs.getString(1);
			}

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
		return pro_id;
	}

}
