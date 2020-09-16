package com.msg.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class I_MsgDAO implements MsgDAO_interface {

	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
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
			"INSERT INTO MSG (MSG_ID,TEXT_ID,AUTHOR_ID,CONTENT,STATUS) VALUES ('M' || LPAD(SEQ_MSG_ID.NEXTVAL, 4, '0'),? ,?, ?, 1)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM MSG order by MSG_ID";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM MSG WHERE MSG_ID = ?";
		private static final String GET_ONE_MSG = 
				"SELECT * FROM MSG WHERE TEXT_ID = ?";
		private static final String DELETE = 
			"DELETE FROM MSG where MSG_ID = ?";
		private static final String UPDATE = 
			"UPDATE MSG SET TEXT_ID=?,AUTHOR_ID=?, CONTENT=?, EST_TIME=?,STATUS=? WHERE MSG_ID=?";

	@Override
	public void insert(MsgVO MsgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, MsgVO.getText_id());
			pstmt.setString(2, MsgVO.getAuthor_id());
			pstmt.setString(3, MsgVO.getContent());
//			pstmt.setInt(4, MsgVO.getStatus());
			

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
	public void update(MsgVO MsgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, MsgVO.getText_id());
			pstmt.setString(2, MsgVO.getAuthor_id());
			pstmt.setString(3, MsgVO.getContent());
			pstmt.setTimestamp(4, MsgVO.getEst_time());
			pstmt.setInt(5, MsgVO.getStatus());
			pstmt.setString(6, MsgVO.getMsg_id());

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
	public void delete(String Msg_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, Msg_id);

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
	public MsgVO findByPrimaryKey(String Msg_id) {

		MsgVO MsgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, Msg_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				MsgVO = new MsgVO();
				MsgVO.setMsg_id(rs.getString("Msg_ID"));
				MsgVO.setText_id(rs.getString("TEXT_ID"));
				MsgVO.setAuthor_id(rs.getString("AUTHOR_ID"));
				MsgVO.setContent(rs.getString("CONTENT"));
				MsgVO.setEst_time(rs.getTimestamp("EST_TIME"));
				MsgVO.setStatus(rs.getInt("STATUS"));
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
		return MsgVO;
	}
	//////////////////////////////////////////////////////////////////////
	@Override
	public MsgVO findByPrimaryKey1(String text_id) {

		MsgVO MsgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MSG);

			pstmt.setString(1, text_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				MsgVO = new MsgVO();
				MsgVO.setText_id(rs.getString("TEXT_ID"));
				MsgVO.setMsg_id(rs.getString("Msg_ID"));
				MsgVO.setAuthor_id(rs.getString("AUTHOR_ID"));
				MsgVO.setContent(rs.getString("CONTENT"));
				MsgVO.setEst_time(rs.getTimestamp("EST_TIME"));
				MsgVO.setStatus(rs.getInt("STATUS"));
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
		return MsgVO;
	}

	@Override
	public List<MsgVO> getAll() {
		List<MsgVO> list = new ArrayList<MsgVO>();
		MsgVO MsgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				MsgVO = new MsgVO();
				MsgVO.setMsg_id(rs.getString("Msg_ID"));
				MsgVO.setText_id(rs.getString("TEXT_ID"));
				MsgVO.setAuthor_id(rs.getString("AUTHOR_ID"));
				MsgVO.setContent(rs.getString("CONTENT"));
				MsgVO.setEst_time(rs.getTimestamp("EST_TIME"));
				MsgVO.setStatus(rs.getInt("STATUS"));
				list.add(MsgVO); // Store the row in the list
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