package com.text_rep.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TextRepDAO implements TextRepDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

		private static final String INSERT_STMT = 
			"INSERT INTO TEXT_REP (REP_ID, INFORMANT, TEXT_ID, CASE_DESCRIPTION, PROCESS) VALUES ( 'TR' || LPAD(SEQ_TEXTREP_ID.NEXTVAL, 4, '0'), ?, ?, ?, 0)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM TEXT_REP order by REP_ID";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM TEXT_REP WHERE REP_ID = ?";
		private static final String DELETE = 
			"DELETE FROM TEXT_REP where REP_ID = ?";
		private static final String UPDATE = 
			"UPDATE TEXT_REP SET PROCESS=?,FINISH_TIME= CURRENT_TIMESTAMP WHERE REP_ID = ?";

	@Override
	public void insert(TextRepVO TextRepVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, TextRepVO.getInformant());
			pstmt.setString(2, TextRepVO.getText_id());
			pstmt.setString(3, TextRepVO.getCase_description());
//			pstmt.setInt(4, TextRepVO.getProcess());
			

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(TextRepVO TextRepVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, TextRepVO.getProcess());
			pstmt.setString(2, TextRepVO.getRep_id());
//			pstmt.setString(1, TextRepVO.getInformant());
//			pstmt.setString(2, TextRepVO.getText_id());
//			pstmt.setString(3, TextRepVO.getCase_description());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String Rep_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, Rep_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public TextRepVO findByPrimaryKey(String Rep_id) {

		TextRepVO TextRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, Rep_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				TextRepVO = new TextRepVO();
				TextRepVO.setRep_id(rs.getString("REP_ID"));
				TextRepVO.setInformant(rs.getString("INFORMANT"));
				TextRepVO.setPicture(rs.getBytes("PICTURE"));
				TextRepVO.setText_id(rs.getString("TEXT_ID"));
				TextRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				TextRepVO.setProcess(rs.getInt("PROCESS"));
				TextRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				TextRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return TextRepVO;
	}

	@Override
	public List<TextRepVO> getAll() {
		List<TextRepVO> list = new ArrayList<TextRepVO>();
		TextRepVO TextRepVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				TextRepVO = new TextRepVO();
				TextRepVO.setRep_id(rs.getString("REP_ID"));
				TextRepVO.setInformant(rs.getString("INFORMANT"));
				TextRepVO.setPicture(rs.getBytes("PICTURE"));
				TextRepVO.setText_id(rs.getString("TEXT_ID"));
				TextRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				TextRepVO.setProcess(rs.getInt("PROCESS"));
				TextRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				TextRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));
				list.add(TextRepVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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