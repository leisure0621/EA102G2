package com.c2cpro_rep.model;

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

public class C2cproRepJNDIDAO  implements I_C2cproRepDAO{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO C2CPRO_REP(REP_ID,INFORMANT,REPORTED_CONTENT,PICTURE,CASE_DESCRIPTION,PROCESS)"
			+ "VALUES('CPR'|| LPAD(SEQ_CPROREP_ID.NEXTVAL,4,'0'),?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT REP_ID,INFORMANT,REPORTED_CONTENT,PICTURE,CASE_DESCRIPTION,PROCESS,EST_TIME,FINISH_TIME FROM C2CPRO_REP ORDER BY REP_ID DESC";
	private static final String GET_ONE_STMT = "SELECT REP_ID,INFORMANT,REPORTED_CONTENT,PICTURE,CASE_DESCRIPTION,PROCESS,EST_TIME,FINISH_TIME FROM C2CPRO_REP WHERE REP_ID =?";
	private static final String DELETE = "DELETE FROM C2CPRO_REP WHERE REP_ID=?";
	private static final String UPDATE = "UPDATE C2CPRO_REP SET PROCESS=?,FINISH_TIME= CURRENT_TIMESTAMP WHERE REP_ID = ?";

	@Override
	public void insert(C2cproRepVO c2cproRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, c2cproRepVO.getInformant());
			pstmt.setString(2, c2cproRepVO.getReported_content());
			pstmt.setBytes(3, c2cproRepVO.getPicture());
			pstmt.setString(4, c2cproRepVO.getCase_description());
			pstmt.setInt(5, c2cproRepVO.getProcess());
		//	pstmt.setTimestamp(6, c2cproRepVO.getFinish_time());
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
	public void update(C2cproRepVO c2cproRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(2, c2cproRepVO.getRep_id());
//			pstmt.setString(1, c2cproRepVO.getInformant());
//			pstmt.setString(2, c2cproRepVO.getReported_content());
//			pstmt.setBytes(3, c2cproRepVO.getPicture());
//			pstmt.setString(4, c2cproRepVO.getCase_description());
			pstmt.setInt(1, c2cproRepVO.getProcess());

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
	public void delete(String rep_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rep_id);

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
	public C2cproRepVO findByPrimaryKey(String rep_id) {
		C2cproRepVO c2cproRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rep_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				c2cproRepVO = new C2cproRepVO();
				c2cproRepVO.setRep_id(rs.getString("REP_ID"));
				c2cproRepVO.setInformant(rs.getString("INFORMANT"));
				c2cproRepVO.setReported_content(rs.getString("REPORTED_CONTENT"));
				c2cproRepVO.setPicture(rs.getBytes("PICTURE"));
				c2cproRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				c2cproRepVO.setProcess(rs.getInt("PROCESS"));
				c2cproRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2cproRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));

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

		return c2cproRepVO;

	}

	@Override
	public List<C2cproRepVO> getAll() {

		List<C2cproRepVO> list = new ArrayList<C2cproRepVO>();
		C2cproRepVO c2cproRepVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				c2cproRepVO = new C2cproRepVO();
				c2cproRepVO.setRep_id(rs.getString("REP_ID"));
				c2cproRepVO.setInformant(rs.getString("INFORMANT"));
				c2cproRepVO.setReported_content(rs.getString("REPORTED_CONTENT"));
				c2cproRepVO.setPicture(rs.getBytes("PICTURE"));
				c2cproRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				c2cproRepVO.setProcess(rs.getInt("PROCESS"));
				c2cproRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2cproRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));
				list.add(c2cproRepVO);
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
