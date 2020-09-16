package com.text_main.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.closeHandle.CloseHandle;
import com.msg.model.MsgVO;

public class I_TextMainDAO implements TextMainDAO_interface {

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
			"INSERT INTO TEXT_MAIN (TEXT_ID,AUTHOR_ID,TITLE,CONTENT,STATUS) VALUES ('T' || LPAD(SEQ_TEXT_ID.NEXTVAL, 4, '0'), ?, ?, ?, 1)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM TEXT_MAIN order by TEXT_ID DESC";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM TEXT_MAIN WHERE TEXT_ID = ?";
		private static final String GET_Msgs_ByText_id_STMT =
			"SELECT * FROM MSG WHERE TEXT_ID = ?";
		private static final String DELETE = 
			"DELETE FROM TEXT_MAIN where TEXT_ID = ?";
		private static final String UPDATE = 
			"UPDATE TEXT_MAIN SET AUTHOR_ID=?, TITLE=?, CONTENT=?,EST_TIME=? ,STATUS=? WHERE TEXT_ID=?";
		private static final String UPDATE_STATUS = 
			"UPDATE TEXT_MAIN SET STATUS=? WHERE TEXT_ID=?";
		private static final String GET_ONE_BY_TEXTID = 
			"SELECT * FROM TEXT_MAIN WHERE TEXT_ID=?";
		CloseHandle closeHandle = new CloseHandle();

	@Override
	public void insert(TextMainVO TextMainVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, TextMainVO.getAuthor_id());
			pstmt.setString(2, TextMainVO.getTitle());
			pstmt.setString(3, TextMainVO.getContent());
//			pstmt.setInt(4, TextMainVO.getStatus());

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
	public void update(TextMainVO TextMainVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, TextMainVO.getAuthor_id());
			pstmt.setString(2, TextMainVO.getTitle());
			pstmt.setString(3, TextMainVO.getContent());
			pstmt.setTimestamp(4, TextMainVO.getEst_time());
			pstmt.setInt(5, TextMainVO.getStatus());
			pstmt.setString(6, TextMainVO.getText_id());

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
	public void update_status(TextMainVO TextMainVO) {
		System.out.println("進入狀態修改");
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);

			pstmt.setInt(1, TextMainVO.getStatus());
			pstmt.setString(2, TextMainVO.getText_id());

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
	public void delete(String Text_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, Text_id);

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
	public TextMainVO findByPrimaryKey(String Text_id) {

		TextMainVO TextMainVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, Text_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				TextMainVO = new TextMainVO();
				TextMainVO.setText_id(rs.getString("TEXT_ID"));
				TextMainVO.setAuthor_id(rs.getString("AUTHOR_ID"));
				TextMainVO.setTitle(rs.getString("TITLE"));
				TextMainVO.setContent(rs.getString("CONTENT"));
				TextMainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				TextMainVO.setStatus(rs.getInt("STATUS"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured1111. "
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
		return TextMainVO;
	}
//***************************************************************************************
	@Override
	public List<TextMainVO> findByTextId(String text_id) {
		List<TextMainVO> list = new ArrayList<TextMainVO>();
		TextMainVO textmainVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

//			Class.forName(DRIVER);
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_TEXTID);

			pstmt.setString(1, text_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				textmainVO  = new TextMainVO();
				textmainVO.setText_id(rs.getString("TEXT_ID"));
				textmainVO.setAuthor_id(rs.getString("AUTHOR_ID"));
				textmainVO.setTitle(rs.getString("TITLE"));
				textmainVO.setContent(rs.getString("CONTENT"));
				textmainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				textmainVO.setStatus(rs.getInt("STATUS"));
				list.add(textmainVO);
			}
			System.out.println("DAO-findByTextId");
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			closeHandle.close(rs,pstmt,con);
		}
		return list;
	}
	
	@Override
	public List<TextMainVO> getAll() {
		List<TextMainVO> list = new ArrayList<TextMainVO>();
		TextMainVO TextMainVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				TextMainVO  = new TextMainVO();
				TextMainVO.setText_id(rs.getString("TEXT_ID"));
				TextMainVO.setAuthor_id(rs.getString("AUTHOR_ID"));
				TextMainVO.setTitle(rs.getString("TITLE"));
				TextMainVO.setContent(rs.getString("CONTENT"));
				TextMainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				TextMainVO.setStatus(rs.getInt("STATUS"));
				list.add(TextMainVO); // Store the row in the list
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
	@Override
	public Set<MsgVO> getMsgsByText_id(String text_id) {
		Set<MsgVO> set = new LinkedHashSet<MsgVO>();
		MsgVO msgVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Msgs_ByText_id_STMT);
			pstmt.setString(1, text_id);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				msgVO = new MsgVO();
				msgVO.setMsg_id(rs.getString("msg_id"));
				msgVO.setText_id(rs.getString("text_id"));
				msgVO.setAuthor_id(rs.getString("author_id"));
				msgVO.setContent(rs.getString("content"));
				msgVO.setEst_time(rs.getTimestamp("est_time"));
				msgVO.setStatus(rs.getInt("est_time"));
				set.add(msgVO); // Store the row in the vector
			}
	
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return set;
	}
}