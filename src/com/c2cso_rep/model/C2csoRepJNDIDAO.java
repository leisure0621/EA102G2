package com.c2cso_rep.model;

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

public class C2csoRepJNDIDAO implements I_C2csoRepDAO{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO C2CSO_REP(REP_ID,INFORMANT,SO_ID,CASE_DESCRIPTION,PROCESS)"
			+ "VALUES('COR'|| LPAD(SEQ_CSOREP_ID.NEXTVAL,4,'0'),?,?,?,0)";
	private static final String GET_ALL_STMT = "SELECT REP_ID,INFORMANT,SO_ID,CASE_DESCRIPTION,PROCESS,EST_TIME,FINISH_TIME FROM C2CSO_REP ORDER BY REP_ID";
	private static final String GET_ONE_STMT = "SELECT REP_ID,INFORMANT,SO_ID,CASE_DESCRIPTION,PROCESS,EST_TIME,FINISH_TIME FROM C2CSO_REP WHERE REP_ID=? ";
	private static final String DELETE = "DELETE FROM C2CSO_REP WHERE REP_ID=?";
	private static final String UPDATE = "UPDATE C2CSO_REP SET INFORMANT=?,SO_ID=?,CASE_DESCRIPTION=?,PROCESS=?,FINISH_TIME = CURRENT_TIMESTAMP WHERE REP_ID=?";

	@Override
	public void insert(C2csoRepVO c2csoRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, c2csoRepVO.getInformant());
			pstmt.setString(2, c2csoRepVO.getSo_id());
			pstmt.setString(3, c2csoRepVO.getCase_description());
			
			

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
	public void update(C2csoRepVO c2csoRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(5, c2csoRepVO.getRep_id());
			pstmt.setString(1, c2csoRepVO.getInformant());
			pstmt.setString(2, c2csoRepVO.getSo_id());
			pstmt.setString(3, c2csoRepVO.getCase_description());
			pstmt.setInt(4, c2csoRepVO.getProcess());

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
	public C2csoRepVO findByPrimaryKey(String rep_id) {
		C2csoRepVO c2csoRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rep_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				c2csoRepVO = new C2csoRepVO();
				c2csoRepVO.setRep_id(rs.getString("REP_ID"));
				c2csoRepVO.setInformant(rs.getString("INFORMANT"));
				c2csoRepVO.setSo_id(rs.getString("SO_ID"));
				c2csoRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				c2csoRepVO.setProcess(rs.getInt("PROCESS"));
				c2csoRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2csoRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));

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

		return c2csoRepVO;

	}

	@Override
	public List<C2csoRepVO> getAll() {
		List<C2csoRepVO> list = new ArrayList<C2csoRepVO>();
		C2csoRepVO c2csoRepVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				c2csoRepVO = new C2csoRepVO();
				c2csoRepVO.setRep_id(rs.getString("REP_ID"));
				c2csoRepVO.setInformant(rs.getString("INFORMANT"));
				c2csoRepVO.setSo_id(rs.getString("SO_ID"));
				c2csoRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				c2csoRepVO.setProcess(rs.getInt("PROCESS"));
				c2csoRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2csoRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));
				list.add(c2csoRepVO);
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

}
