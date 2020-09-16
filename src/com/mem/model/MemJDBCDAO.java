package com.mem.model;

import java.util.*;
import java.sql.*;

public class MemJDBCDAO implements I_MemDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";

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

	public void insert(MemVO memVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	
	public void update(MemVO memVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

	public void delete(String mem_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_id);			
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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


	public MemVO findByPrimaryKey(String mem_id) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_id);		

			rs = pstmt.executeQuery();

			
			System.out.println();
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_EMAIL_STMT);
			
			pstmt.setString(1, "%"+email+"%");
			
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		return list;
	}
	

	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		return list;
	}
	
	public static void main(String[] args) {
		MemJDBCDAO dao = new MemJDBCDAO();
		
//		for(int i = 0;i<5;i++) {
//			// 新增
			MemVO memVO1  = new MemVO();
			memVO1.setFirst_name("陳");
			memVO1.setLast_name("郁成");
			memVO1.setNickname("成成成");
			memVO1.setTel("035566201");
			memVO1.setMob("0916619904");
			memVO1.setEmail("i18995@bcaoo.com");
			memVO1.setShop_name("清道夫");
			memVO1.setCredit_card("55206175738301");
			memVO1.setCredit_card_expires(java.sql.Date.valueOf("1990-09-01"));
			memVO1.setCredit_card_cvc(450);
			memVO1.setBank_account("01212840059101");
			memVO1.setAddress("屏東縣來義鄉德泉街95巷146弄634號");
			memVO1.setAuthority(2);
			dao.insert(memVO1);
//		}		
//		System.out.println("-------------------------------------------");
//			
//		// 修改
//		MemVO memVO2  = new MemVO();
//		memVO2.setFirst_name("陳");
//		memVO2.setLast_name("春婉");
//		memVO2.setNickname("草尼瑪");
//		memVO2.setTel("035566201");
//		memVO2.setMob("0916619904");
//		memVO2.setEmail("i18999@bcaoo.com");
//		memVO2.setPassword("NL4foG7MX");
//		memVO2.setShop_name("清道夫");
//		memVO2.setCredit_card("55206175738301");
//		memVO2.setCredit_card_expires(java.sql.Date.valueOf("1990-11-01"));
//		memVO2.setCredit_card_cvc(450);
//		memVO2.setBank_account("01212840059101");
//		memVO2.setAddress("屏東縣來義鄉德泉街95巷146弄634號");
//		memVO2.setAuthority(2);
//		memVO2.setMem_id("MEM0003");
//		dao.update(memVO2);
//		
//		// 查詢
//		MemVO list1 = dao.findByPrimaryKey("MEM0004");
//		System.out.print(list1.getMem_id()+"\t");
//		System.out.print(list1.getFirst_name()+"\t");
//		System.out.print(list1.getLast_name()+"\t");
//		System.out.print(list1.getNickname()+"\t");
//		System.out.print(list1.getTel()+"\t");
//		System.out.print(list1.getMob()+"\t");
//		System.out.print(list1.getEmail()+"\t");
//		System.out.print(list1.getPassword()+"\t");
//		System.out.print(list1.getShop_name()+"\t");
//		System.out.print(list1.getCredit_card()+"\t");
//		System.out.print(list1.getCredit_card_expires()+"\t");
//		System.out.print(list1.getCredit_card_cvc()+"\t");
//		System.out.print(list1.getBank_account()+"\t");
//		System.out.print(list1.getEst_time()+"\t");
//		System.out.print(list1.getAddress()+"\t");
//		System.out.print(list1.getAuthority()+"\t");
//		System.out.println();
//		System.out.println("-------------------------------------------");
//		
//		// 查詢Email
//		List<MemVO> emailList = dao.findByEmail("eoopy.com");
//		for (MemVO memVO11 : emailList) {
//			System.out.print(memVO11.getMem_id()+"\t");
//			System.out.print(memVO11.getFirst_name()+"\t");
//			System.out.print(memVO11.getLast_name()+"\t");
//			System.out.print(memVO11.getNickname()+"\t");
//			System.out.print(memVO11.getTel()+"\t");
//			System.out.print(memVO11.getMob()+"\t");
//			System.out.print(memVO11.getEmail()+"\t");
//			System.out.print(memVO11.getPassword()+"\t");
//			System.out.print(memVO11.getShop_name()+"\t");
//			System.out.print(memVO11.getCredit_card()+"\t");
//			System.out.print(memVO11.getCredit_card_expires()+"\t");
//			System.out.print(memVO11.getCredit_card_cvc()+"\t");
//			System.out.print(memVO11.getBank_account()+"\t");
//			System.out.print(memVO11.getEst_time()+"\t");
//			System.out.print(memVO11.getAddress()+"\t");
//			System.out.print(memVO11.getAuthority()+"\t");
//			System.out.println();
//		}
//		System.out.println("-------------------------------------------");
//		
		// 查詢全部
		List<MemVO> list = dao.getAll();
		for (MemVO memVO : list) {
			System.out.print(memVO.getMem_id()+"\t");
			System.out.print(memVO.getFirst_name()+"\t");
			System.out.print(memVO.getLast_name()+"\t");
			System.out.print(memVO.getNickname()+"\t");
			System.out.print(memVO.getTel()+"\t");
			System.out.print(memVO.getMob()+"\t");
			System.out.print(memVO.getEmail()+"\t");
			System.out.print(memVO.getPassword()+"\t");
			System.out.print(memVO.getShop_name()+"\t");
			System.out.print(memVO.getCredit_card()+"\t");
			System.out.print(memVO.getCredit_card_expires()+"\t");
			System.out.print(memVO.getCredit_card_cvc()+"\t");
			System.out.print(memVO.getBank_account()+"\t");
			System.out.print(memVO.getEst_time()+"\t");
			System.out.print(memVO.getAddress()+"\t");
			System.out.print(memVO.getAuthority()+"\t");
			System.out.println();
		}
		System.out.println("-------------------------------------------");
//
//		// 依照输入值模糊查询
//		List<MemVO> list = dao.findByQuery("");
//		for (MemVO memVO : list) {
//			System.out.print(memVO.getMem_id()+"\t");
//			System.out.print(memVO.getFirst_name()+"\t");
//			System.out.print(memVO.getLast_name()+"\t");
//			System.out.print(memVO.getNickname()+"\t");
//			System.out.print(memVO.getTel()+"\t");
//			System.out.print(memVO.getMob()+"\t");
//			System.out.print(memVO.getEmail()+"\t");
//			System.out.print(memVO.getPassword()+"\t");
//			System.out.print(memVO.getShop_name()+"\t");
//			System.out.print(memVO.getCredit_card()+"\t");
//			System.out.print(memVO.getCredit_card_expires()+"\t");
//			System.out.print(memVO.getCredit_card_cvc()+"\t");
//			System.out.print(memVO.getBank_account()+"\t");
//			System.out.print(memVO.getEst_time()+"\t");
//			System.out.print(memVO.getAddress()+"\t");
//			System.out.print(memVO.getAuthority()+"\t");
//			System.out.println();
//		}
//		System.out.println("-------------------------------------------");
//	
//		// 刪除
//		dao.delete("MEM0006");
//		System.out.println("刪除 MEM0006 成功");
//		System.out.println("-------------------------------------------");
	}


}