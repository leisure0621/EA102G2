package com.mem.model;

import java.util.*;
import java.sql.*;

import javax.naming.*;
import javax.sql.DataSource;

public class MemDAO implements I_MemDAO {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 會員相關
	private static final String INSERT_STMT = 
			"INSERT INTO MEM_MAIN( MEM_ID, FIRST_NAME, LAST_NAME,NICKNAME,TEL,MOB,EMAIL,PASSWORD,SHOP_NAME,CREDIT_CARD,CREDIT_CARD_EXPIRES,CREDIT_CARD_CVC,BANK_ACCOUNT,ADDRESS,AUTHORITY ) VALUES ( 'MEM' || LPAD(SEQ_MEM_ID.NEXTVAL, 4, '0'),?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
	private static final String GET_ALL_STMT = 
		"SELECT MEM_ID,FIRST_NAME,LAST_NAME,NICKNAME,TEL,MOB,EMAIL,PASSWORD,SHOP_NAME,CREDIT_CARD,CREDIT_CARD_EXPIRES,CREDIT_CARD_CVC,BANK_ACCOUNT,EST_TIME,ADDRESS,AUTHORITY FROM MEM_MAIN";
	private static final String GET_ONE_STMT = 
		"SELECT MEM_ID,FIRST_NAME,LAST_NAME,NICKNAME,TEL,MOB,EMAIL,PASSWORD,SHOP_NAME,CREDIT_CARD,CREDIT_CARD_EXPIRES,CREDIT_CARD_CVC,BANK_ACCOUNT,EST_TIME,ADDRESS,AUTHORITY FROM MEM_MAIN WHERE MEM_ID = ?";
	private static final String GET_EMAIL_STMT = 
		"SELECT MEM_ID,FIRST_NAME,LAST_NAME,NICKNAME,TEL,MOB,EMAIL,PASSWORD,SHOP_NAME,CREDIT_CARD,CREDIT_CARD_EXPIRES,CREDIT_CARD_CVC,BANK_ACCOUNT,EST_TIME,ADDRESS,AUTHORITY FROM MEM_MAIN WHERE UPPER(EMAIL) LIKE UPPER(?)";
	private static final String DELETE = 
		"DELETE FROM MEM_MAIN where MEM_ID = ?";
	private static final String UPDATE = 
		"UPDATE MEM_MAIN SET FIRST_NAME = ?, LAST_NAME = ?, NICKNAME = ?, TEL = ?, MOB = ?, EMAIL = ?, PASSWORD = ?, SHOP_NAME = ?, CREDIT_CARD = ?,CREDIT_CARD_EXPIRES = TO_DATE(TO_CHAR(?,'yyyymm'),'yyyy-mm'), CREDIT_CARD_CVC = ?, BANK_ACCOUNT = ?, ADDRESS = ?, AUTHORITY = ? WHERE MEM_ID = ?";
	private static final String GET_QUERY_STMT = 
		"SELECT MEM_ID,FIRST_NAME,LAST_NAME,NICKNAME,TEL,MOB,EMAIL,PASSWORD,SHOP_NAME,CREDIT_CARD,CREDIT_CARD_EXPIRES,CREDIT_CARD_CVC,BANK_ACCOUNT,EST_TIME,ADDRESS,AUTHORITY FROM MEM_MAIN " + 
		"WHERE TO_CHAR(EST_TIME, 'YYYY-MM-DD HH24:MI:SS') LIKE UPPER(?) OR UPPER(MEM_ID) LIKE UPPER(?) OR UPPER(FIRST_NAME) LIKE UPPER(?) OR UPPER(LAST_NAME) LIKE UPPER(?) OR UPPER(EMAIL) LIKE UPPER(?)";
	
	@Override
	public void insert(MemVO memVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memVO.getFirst_name());
			pstmt.setString(2, memVO.getLast_name());
			pstmt.setString(3, memVO.getNickname());
			pstmt.setString(4, memVO.getTel());
			pstmt.setString(5, memVO.getMob());
			pstmt.setString(6, memVO.getEmail());
			pstmt.setString(7, memVO.getShop_name());
			pstmt.setString(8, memVO.getCredit_card());
			pstmt.setString(9, memVO.getEmail());
			pstmt.setDate(10, memVO.getCredit_card_expires());
			pstmt.setInt(11, memVO.getCredit_card_cvc());
			pstmt.setString(12, memVO.getBank_account());
			pstmt.setString(13, memVO.getAddress());
			pstmt.setInt(14, memVO.getAuthority());

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
	public void update(MemVO memVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, memVO.getFirst_name());
			pstmt.setString(2, memVO.getLast_name());
			pstmt.setString(3, memVO.getNickname());
			pstmt.setString(4, memVO.getTel());
			pstmt.setString(5, memVO.getMob());
			pstmt.setString(6, memVO.getEmail());
			pstmt.setString(7, memVO.getPassword());
			pstmt.setString(8, memVO.getShop_name());
			pstmt.setString(9, memVO.getCredit_card());
			pstmt.setDate(10, memVO.getCredit_card_expires());
			pstmt.setInt(11, memVO.getCredit_card_cvc());
			pstmt.setString(12, memVO.getBank_account());
			pstmt.setString(13, memVO.getAddress());
			pstmt.setInt(14, memVO.getAuthority());
			pstmt.setString(15, memVO.getMem_id());			

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
	public void delete(String mem_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_id);			
			
			pstmt.executeUpdate();
			
			System.out.println("MemDAO delete");
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
	public MemVO findByPrimaryKey(String mem_id) {
		MemVO memVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_id);		

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memVO 也稱為 Domain objects
				memVO = new MemVO();
				
				memVO.setMem_id(rs.getString("MEM_ID"));
				memVO.setFirst_name(rs.getString("FIRST_NAME"));
				memVO.setLast_name(rs.getString("LAST_NAME"));
				memVO.setNickname(rs.getString("NICKNAME"));
				memVO.setTel(rs.getString("TEL"));
				memVO.setMob(rs.getString("MOB"));
				memVO.setEmail(rs.getString("EMAIL"));
				memVO.setPassword(rs.getString("PASSWORD"));
				memVO.setShop_name(rs.getString("SHOP_NAME"));
				memVO.setCredit_card(rs.getString("CREDIT_CARD"));
				memVO.setCredit_card_expires(rs.getDate("CREDIT_CARD_EXPIRES"));
				memVO.setCredit_card_cvc(rs.getInt("CREDIT_CARD_CVC"));
				memVO.setBank_account(rs.getString("BANK_ACCOUNT"));
				memVO.setEst_time(rs.getTimestamp("EST_TIME"));
				memVO.setAddress(rs.getString("ADDRESS"));
				memVO.setAuthority(rs.getInt("AUTHORITY"));
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
		return memVO;
	}
	
	public List<MemVO> findByEmail(String email) {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EMAIL_STMT);
			
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				
				memVO.setMem_id(rs.getString("MEM_ID"));
				memVO.setFirst_name(rs.getString("FIRST_NAME"));
				memVO.setLast_name(rs.getString("LAST_NAME"));
				memVO.setNickname(rs.getString("NICKNAME"));
				memVO.setTel(rs.getString("TEL"));
				memVO.setMob(rs.getString("MOB"));
				memVO.setEmail(rs.getString("EMAIL"));
				memVO.setPassword(rs.getString("PASSWORD"));
				memVO.setShop_name(rs.getString("SHOP_NAME"));
				memVO.setCredit_card(rs.getString("CREDIT_CARD"));
				memVO.setCredit_card_expires(rs.getDate("CREDIT_CARD_EXPIRES"));
				memVO.setCredit_card_cvc(rs.getInt("CREDIT_CARD_CVC"));
				memVO.setBank_account(rs.getString("BANK_ACCOUNT"));
				memVO.setEst_time(rs.getTimestamp("EST_TIME"));
				memVO.setAddress(rs.getString("ADDRESS"));
				memVO.setAuthority(rs.getInt("AUTHORITY"));
				
				list.add(memVO); // Store the row in the list
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
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memVO 也稱為 Domain objects
				memVO = new MemVO();
				
				memVO.setMem_id(rs.getString("MEM_ID"));
				memVO.setFirst_name(rs.getString("FIRST_NAME"));
				memVO.setLast_name(rs.getString("LAST_NAME"));
				memVO.setNickname(rs.getString("NICKNAME"));
				memVO.setTel(rs.getString("TEL"));
				memVO.setMob(rs.getString("MOB"));
				memVO.setEmail(rs.getString("EMAIL"));
				memVO.setPassword(rs.getString("PASSWORD"));
				memVO.setShop_name(rs.getString("SHOP_NAME"));
				memVO.setCredit_card(rs.getString("CREDIT_CARD"));
				memVO.setCredit_card_expires(rs.getDate("CREDIT_CARD_EXPIRES"));
				memVO.setCredit_card_cvc(rs.getInt("CREDIT_CARD_CVC"));
				memVO.setBank_account(rs.getString("BANK_ACCOUNT"));
				memVO.setEst_time(rs.getTimestamp("EST_TIME"));
				memVO.setAddress(rs.getString("ADDRESS"));
				memVO.setAuthority(rs.getInt("AUTHORITY"));
				
				list.add(memVO); // Store the row in the list
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
	public List<MemVO> findByQuery(String query) {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_QUERY_STMT);

			pstmt.setString(1, "%"+query+"%");	
			pstmt.setString(2, "%"+query+"%");	
			pstmt.setString(3, "%"+query+"%");
			pstmt.setString(4, "%"+query+"%");	
			pstmt.setString(5, "%"+query+"%");	
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				
				memVO.setMem_id(rs.getString("MEM_ID"));
				memVO.setFirst_name(rs.getString("FIRST_NAME"));
				memVO.setLast_name(rs.getString("LAST_NAME"));
				memVO.setNickname(rs.getString("NICKNAME"));
				memVO.setTel(rs.getString("TEL"));
				memVO.setMob(rs.getString("MOB"));
				memVO.setEmail(rs.getString("EMAIL"));
				memVO.setPassword(rs.getString("PASSWORD"));
				memVO.setShop_name(rs.getString("SHOP_NAME"));
				memVO.setCredit_card(rs.getString("CREDIT_CARD"));
				memVO.setCredit_card_expires(rs.getDate("CREDIT_CARD_EXPIRES"));
				memVO.setCredit_card_cvc(rs.getInt("CREDIT_CARD_CVC"));
				memVO.setBank_account(rs.getString("BANK_ACCOUNT"));
				memVO.setEst_time(rs.getTimestamp("EST_TIME"));
				memVO.setAddress(rs.getString("ADDRESS"));
				memVO.setAuthority(rs.getInt("AUTHORITY"));
				
				list.add(memVO); // Store the row in the list
			}

			// Handle any driver errors
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
		return list;
	}

}
