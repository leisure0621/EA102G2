package com.c2cpro_detail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class C2cproDetailJNDIDAO  implements I_C2cproDetailDAO{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO C2CPRO_DETAIL(PRO_ID,SPEC_ID,SPEC_DETAIL)" + "VALUES(?,?,?)";
	private static final String GET_ALL_STMT = "SELECT PRO_ID,SPEC_ID,SPEC_DETAIL  FROM C2CPRO_DETAIL ORDER BY  PRO_ID";
	private static final String GET_ONE_STMT = "SELECT PRO_ID,SPEC_ID,SPEC_DETAIL  FROM C2CPRO_DETAIL WHERE PRO_ID=?  ";
	private static final String DELETE = "DELETE FROM C2CPRO_DETAIL WHERE PRO_ID=?";
	private static final String UPDATE = "UPDATE C2CPRO_DETAIL SET SPEC_DETAIL=? WHERE PRO_ID=? and SPEC_ID = ?";

	@Override
	public void insert(C2cproDetailVO c2cproDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, c2cproDetailVO.getPro_id());
			pstmt.setString(2, c2cproDetailVO.getSpec_id());
			pstmt.setString(3, c2cproDetailVO.getSpec_detail());

			pstmt.executeUpdate();

		}  catch (SQLException se) {
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
	public void update(C2cproDetailVO c2cproDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(2, c2cproDetailVO.getPro_id());
			pstmt.setString(3, c2cproDetailVO.getSpec_id());
			pstmt.setString(1, c2cproDetailVO.getSpec_detail());

			pstmt.executeUpdate();

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public void delete(String pro_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, pro_id);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public List<C2cproDetailVO> findByPrimaryKey(String pro_id) {
		List<C2cproDetailVO> list = new ArrayList<C2cproDetailVO>();
		C2cproDetailVO c2cproDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, pro_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				c2cproDetailVO = new C2cproDetailVO();
				c2cproDetailVO.setPro_id(rs.getString("PRO_ID"));
				c2cproDetailVO.setSpec_id(rs.getString("SPEC_ID"));
				c2cproDetailVO.setSpec_detail(rs.getString("SPEC_DETAIL"));
				list.add(c2cproDetailVO);
			}

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public List<C2cproDetailVO> getAll() {
		List<C2cproDetailVO> list = new ArrayList<C2cproDetailVO>();
		C2cproDetailVO c2cproDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				c2cproDetailVO = new C2cproDetailVO();
				c2cproDetailVO.setPro_id(rs.getString("PRO_ID"));
				c2cproDetailVO.setSpec_id(rs.getString("SPEC_ID"));
				c2cproDetailVO.setSpec_detail(rs.getString("SPEC_DETAIL"));

				list.add(c2cproDetailVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
}
