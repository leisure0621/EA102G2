package com.bid_detail.model;

import java.sql.*;
import java.util.*;

public class BidDetailJDBCDAO implements I_BidDetailDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";

	private static final String INSERT_STMT = "INSERT INTO BID_DETAIL (BID_ID, MEM_ID, BID_PRICE) VALUES(?,?,?)";
	private static final String GET_ONE_BYBIDID_STMT = "SELECT * FROM BID_DETAIL WHERE BID_ID = ? ORDER BY BID_TIME";
	private static final String GET_ONE_BYMEMID_STMT = "SELECT * FROM BID_DETAIL WHERE MEM_ID = ? ORDER BY BID_TIME";
	private static final String GET_ALL_STMT = "SELECT * FROM BID_DETAIL ORDER BY BID_ID";

	@Override
	public void insert(BidDetailVO BidDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, BidDetailVO.getBid_id());
			pstmt.setString(2, BidDetailVO.getMem_id());
			pstmt.setInt(3, BidDetailVO.getBid_price());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public List<BidDetailVO> findByBidId(String bid_id) {
		List<BidDetailVO> list = new ArrayList<BidDetailVO>();
		BidDetailVO bidDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BYBIDID_STMT);

			pstmt.setString(1, bid_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bidDetailVO = new BidDetailVO();
				bidDetailVO.setBid_id(rs.getString("bid_id"));
				bidDetailVO.setMem_id(rs.getString("mem_id"));
				bidDetailVO.setBid_price(rs.getInt("bid_price"));
				bidDetailVO.setBid_time(rs.getTimestamp("bid_time"));
				list.add(bidDetailVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public List<BidDetailVO> findByMemId(String mem_id) {
		List<BidDetailVO> list = new ArrayList<BidDetailVO>();
		BidDetailVO bidDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BYMEMID_STMT);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bidDetailVO = new BidDetailVO();
				bidDetailVO.setBid_id(rs.getString("bid_id"));
				bidDetailVO.setMem_id(rs.getString("mem_id"));
				bidDetailVO.setBid_price(rs.getInt("bid_price"));
				bidDetailVO.setBid_time(rs.getTimestamp("bid_time"));
				list.add(bidDetailVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public List<BidDetailVO> getAll() {
		List<BidDetailVO> list = new ArrayList<BidDetailVO>();
		BidDetailVO bidDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bidDetailVO = new BidDetailVO();
				bidDetailVO.setBid_id(rs.getString("bid_id"));
				bidDetailVO.setMem_id(rs.getString("mem_id"));
				bidDetailVO.setBid_price(rs.getInt("bid_price"));
				bidDetailVO.setBid_time(rs.getTimestamp("bid_time"));
				list.add(bidDetailVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public static void main(String[] args) {
//		BidDetailJDBCDAO dao = new BidDetailJDBCDAO();

		// insert
//		BidDetailVO bidDetailVO1 = new BidDetailVO();
//		bidDetailVO1.setBid_id("BD0004");
//		bidDetailVO1.setMem_id("MEM0002");
//		bidDetailVO1.setBid_price(4500);
//		dao.insert(bidDetailVO1);
//
//		System.out.println("insert complete");

		// seach for all by Bid_id

//		List<BidDetailVO> list1 = dao.findByBidId("BD0001");
//		for (BidDetailVO bid : list1) {
//			System.out.print(bid.getBid_id() + ",");
//			System.out.print(bid.getMem_id() + ",");
//			System.out.print(bid.getBid_price() + ",");
//			System.out.print(bid.getBid_time());
//			System.out.println();
//		}

		// seach for all by Mem_id
//		List<BidDetailVO> list2 = dao.findByMemId("MEM0001");
//		for (BidDetailVO bid : list2) {
//			System.out.print(bid.getBid_id() + ",");
//			System.out.print(bid.getMem_id() + ",");
//			System.out.print(bid.getBid_price() + ",");
//			System.out.print(bid.getBid_time());
//			System.out.println();
//		}

		// seach for all
//		List<BidDetailVO> list3 = dao.getAll();
//		for (BidDetailVO bid : list3) {
//			System.out.print(bid.getBid_id() + ",");
//			System.out.print(bid.getMem_id() + ",");
//			System.out.print(bid.getBid_price() + ",");
//			System.out.print(bid.getBid_time());
//			System.out.println();
//		}

	}

	@Override
	public List<BidDetailVO> findByQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
