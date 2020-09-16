package com.spec_detail.model;

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

public class SpecDetailDAO implements I_SpecDetailDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO SPEC_DETAIL(SPECDET_ID, SPEC_ID,DETAIL_DES) VALUES ('SPD' || LPAD(SEQ_SPECD_ID.NEXTVAL,4,'0'), ?,?)";
	private static final String UPDATE_STMT = "UPDATE SPEC_DETAIL SET SPEC_ID = ?, DETAIL_DES = ? WHERE SPECDET_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM SPEC_DETAIL WHERE SPECDET_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM SPEC_DETAIL ORDER BY SPECDET_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM SPEC_DETAIL WHERE SPECDET_ID = ?";

	@Override
	public void insert(SpecDetailVO specdVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, specdVO.getSpec_id());
			pstmt.setString(2, specdVO.getDetail_des());

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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(SpecDetailVO specdVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, specdVO.getSpec_id());
			pstmt.setString(2, specdVO.getDetail_des());
			pstmt.setString(3, specdVO.getSpecdet_id());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String specd_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, specd_id);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public SpecDetailVO findByPrimaryKey(String specd_id) {
		SpecDetailVO sdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, specd_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				sdVO = new SpecDetailVO();
				sdVO.setDetail_des(rs.getString("DETAIL_DES"));
				sdVO.setSpec_id(rs.getString("SPEC_ID"));
				sdVO.setSpecdet_id(rs.getString("SPECDET_ID"));
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
		return sdVO;
	}

	@Override
	public List<SpecDetailVO> getAll() {
		List<SpecDetailVO> list = new ArrayList<SpecDetailVO>();
		SpecDetailVO sdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sdVO = new SpecDetailVO();
				sdVO.setDetail_des(rs.getString("DETAIL_DES"));
				sdVO.setSpec_id(rs.getString("SPEC_ID"));
				sdVO.setSpecdet_id(rs.getString("SPECDET_ID"));
				list.add(sdVO);
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
		return list;
	}

	@Override
	public String insertWithPK(SpecDetailVO specdVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String specdet_id = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String[] cols = { "SPECDET_ID" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, specdVO.getSpec_id());
			pstmt.setString(2, specdVO.getDetail_des());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				specdet_id = rs.getString(1);
			}
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
		return specdet_id;
	}

}
