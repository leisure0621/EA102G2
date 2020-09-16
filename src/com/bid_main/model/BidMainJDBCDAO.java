package com.bid_main.model;

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


public class BidMainJDBCDAO implements I_BidMainDAO{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	
	private static final String INSERT_STMT = 
		"INSERT INTO BID_MAIN (BID_ID, BID_TITLE, BID_DES, PRO_ID, START_PRICE, INCR, STATUS, WINNER, START_TIME, END_TIME) VALUES ('BD' || LPAD(SEQ_BID_ID.NEXTVAL, 4, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT BID_ID, BID_TITLE, BID_DES, PRO_ID, START_PRICE, INCR, STATUS, WINNER, START_TIME, END_TIME, EST_TIME FROM BID_MAIN order by BID_ID";
	private static final String GET_ONE_STMT = 
		"SELECT BID_ID, BID_TITLE, BID_DES, PRO_ID, START_PRICE, INCR, STATUS, WINNER, START_TIME, END_TIME, EST_TIME FROM BID_MAIN where BID_ID = ?";
	private static final String DELETE = 
		"DELETE FROM BID_MAIN where BID_ID = ?";
	private static final String UPDATE = 
		"UPDATE BID_MAIN set BID_TITLE = ? , BID_DES = ? , PRO_ID = ?, START_PRICE = ?, INCR = ?, STATUS = ?, WINNER = ?, START_TIME = ?, END_TIME = ?, EST_TIME= ? WHERE BID_ID = ?";

//	java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    String formatDate = df.format(new java.util.Date());
//    out.println(formatDate);
		
	@Override
	public void insert(BidMainVO bidmainVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bidmainVO.getBid_title());
			pstmt.setString(2, bidmainVO.getBid_des());
			pstmt.setString(3, bidmainVO.getPro_id());
			pstmt.setInt(4, bidmainVO.getStart_price());
			pstmt.setInt(5, bidmainVO.getIncr());
			pstmt.setInt(6, bidmainVO.getStatus());
			pstmt.setString(7, bidmainVO.getWinner());
			pstmt.setTimestamp(8, bidmainVO.getStart_time());
			pstmt.setTimestamp(9, bidmainVO.getEnd_time());
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

	@Override
	public void update(BidMainVO bidmainVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, bidmainVO.getBid_title());
			pstmt.setString(2, bidmainVO.getBid_des());
			pstmt.setString(3, bidmainVO.getPro_id());
			pstmt.setInt(4, bidmainVO.getStart_price());
			pstmt.setInt(5, bidmainVO.getIncr());
			pstmt.setInt(6, bidmainVO.getStatus());
			pstmt.setString(7, bidmainVO.getWinner());
			pstmt.setTimestamp(8, bidmainVO.getStart_time());
			pstmt.setTimestamp(9, bidmainVO.getEnd_time());
			pstmt.setTimestamp(10, bidmainVO.getEst_time());
			pstmt.setString(11, bidmainVO.getBid_id());

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

	@Override
	public void delete(String bidId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, bidId);

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

	@Override
	public BidMainVO findByPrimaryKey(String bidId) {

		BidMainVO bidmainVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, bidId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bidmainVO = new BidMainVO();
				bidmainVO.setBid_id(rs.getString("BID_ID"));
				bidmainVO.setBid_title(rs.getString("BID_TITLE"));
				bidmainVO.setBid_des(rs.getString("BID_DES"));
				bidmainVO.setPro_id(rs.getString("PRO_ID"));
				bidmainVO.setStart_price(rs.getInt("START_PRICE"));
				bidmainVO.setIncr(rs.getInt("INCR"));
				bidmainVO.setStatus(rs.getInt("STATUS"));
				bidmainVO.setWinner(rs.getString("WINNER"));
				bidmainVO.setStart_time(rs.getTimestamp("START_TIME"));
				bidmainVO.setEnd_time(rs.getTimestamp("END_TIME"));
				bidmainVO.setEst_time(rs.getTimestamp("EST_TIME"));

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
		return bidmainVO;
	}

	@Override
	public List<BidMainVO> getAll() {
		List<BidMainVO> list = new ArrayList<BidMainVO>();
		BidMainVO bidmainVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bidmainVO = new BidMainVO();
				bidmainVO.setBid_id(rs.getString("BID_ID"));
				bidmainVO.setBid_title(rs.getString("BID_TITLE"));
				bidmainVO.setBid_des(rs.getString("BID_DES"));
				bidmainVO.setPro_id(rs.getString("PRO_ID"));
				bidmainVO.setStart_price(rs.getInt("START_PRICE"));
				bidmainVO.setIncr(rs.getInt("INCR"));
				bidmainVO.setStatus(rs.getInt("STATUS"));
				bidmainVO.setWinner(rs.getString("WINNER"));
				bidmainVO.setStart_time(rs.getTimestamp("START_TIME"));
				bidmainVO.setEnd_time(rs.getTimestamp("END_TIME"));
				bidmainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				list.add(bidmainVO); 
				
				// Store the row in the list
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
		return list;
	}

	public static void main(String[] args) {

//		BidMainJDBCDAO dao = new BidMainJDBCDAO();

		// insert
//		BidMainVO bidmainVO1 = new BidMainVO();
//		bidmainVO1.setBid_title("微星MAG271C 27吋螢幕1000元起");
//		bidmainVO1.setBid_des("微星MAG271C 27吋螢幕1000元起!!! 限時兩天競標");
//		bidmainVO1.setPro_id("BP0051");
//		bidmainVO1.setStart_price(new Integer(1000));
//		bidmainVO1.setIncr(new Integer(500));
//		bidmainVO1.setStatus(new Integer(1));
//		bidmainVO1.setWinner("MEM0001");
//		bidmainVO1.setStart_time(java.sql.Timestamp.valueOf("2020-07-05 13:22:00"));
//		bidmainVO1.setEnd_time(java.sql.Timestamp.valueOf("2020-07-07 13:24:00"));
//		dao.insert(bidmainVO1);
//		
//		System.out.println("insert complete");

//		// update
//		BidMainVO bidmainVO2 = new BidMainVO();
//		bidmainVO2.setBid_title("微星MAG271C 27吋螢幕1500元起");
//		bidmainVO2.setBid_des("微星MAG271C 27吋螢幕1500元起!!! 限時兩天競標");
//		bidmainVO2.setPro_id("BP0051");
//		bidmainVO2.setStart_price(new Integer(1500));
//		bidmainVO2.setIncr(new Integer(300));
//		bidmainVO2.setStatus(new Integer(1));
//		bidmainVO2.setWinner("MEM0002");
//		bidmainVO2.setStart_time(java.sql.Timestamp.valueOf("2020-08-05 12:11:00"));
//		bidmainVO2.setEnd_time(java.sql.Timestamp.valueOf("2020-08-08" 12:11:00));
//		bidmainVO2.setBid_id("BD0006");
//		dao.update(bidmainVO2);
//		
//		System.out.println("update complete");
//
//		// delete
//		dao.delete("BD0006");
//		System.out.println("delete complete");
//		
//		// search for one
//		BidMainVO bidmainVO3 = dao.findByPrimaryKey("BD0005");
//		System.out.println(bidmainVO3.getBid_id() + ",");
//		System.out.println(bidmainVO3.getBid_title() + ",");
//		System.out.println(bidmainVO3.getBid_des() + ",");
//		System.out.println(bidmainVO3.getPro_id() + ",");
//		System.out.println(bidmainVO3.getStart_price() + ",");
//		System.out.println(bidmainVO3.getIncr() + ",");
//		System.out.println(bidmainVO3.getStatus() + ",");
//		System.out.println(bidmainVO3.getWinner() + ",");
//		System.out.println(bidmainVO3.getStart_time() + ",");
//		System.out.println(bidmainVO3.getEnd_time() + ",");
//		System.out.println(bidmainVO3.getEst_time());
		
		System.out.println("---------------------");

		// search for all
//		List<BidMainVO> list = dao.getAll();
//		for (BidMainVO bid : list) {
//			System.out.print(bid.getBid_id() + ",");
//			System.out.print(bid.getBid_title() + ",");
//			System.out.print(bid.getBid_des() + ",");
//			System.out.print(bid.getPro_id() + ",");
//			System.out.print(bid.getStart_price() + ",");
//			System.out.print(bid.getIncr() + ",");
//			System.out.print(bid.getStatus() + ",");
//			System.out.print(bid.getWinner() + ",");
//			System.out.print(bid.getStart_time() + ",");
//			System.out.print(bid.getEnd_time() + ",");
//			System.out.print(bid.getEst_time());
//			System.out.println();
//		}
	}

	@Override
	public void updateWinner(BidMainVO bidVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BidMainVO> findByQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
