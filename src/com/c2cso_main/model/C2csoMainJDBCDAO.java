package com.c2cso_main.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class C2csoMainJDBCDAO implements I_C2csoMainDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	private static final String INSERT_STMT = "INSERT INTO C2CSO_MAIN (SO_ID,PRO_ID,BUYER_ID,QUANTITY,STATUS)"
			+ "VALUES('CSO'|| LPAD(SEQ_CSO_ID.NEXTVAL,4,'0'),?,?,?,?)";
	//問題*****新訂單是否預設狀態
	private static final String GET_ALL_STMT = "SELECT SO_ID,PRO_ID,BUYER_ID,QUANTITY,EST_TIME,STATUS FROM C2CSO_MAIN ORDER BY SO_ID DESC";
	private static final String GET_ONE_STMT = "SELECT SO_ID,PRO_ID,BUYER_ID,QUANTITY,EST_TIME,STATUS FROM C2CSO_MAIN WHERE SO_ID=?";
	private static final String DELETE = "DELETE FROM C2CSO_MAIN WHERE SO_ID=?";
	private static final String UPDATE = "UPDATE C2CSO_MAIN SET PRO_ID=?,BUYER_ID=?,QUANTITY=?,STATUS=? WHERE SO_ID=?";

	// 問題****訂單修改是否只能修改狀態??
	@Override
	public void insert(C2csoMainVO c2csoMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, c2csoMainVO.getPro_id());
			pstmt.setString(2, c2csoMainVO.getBuyer_id());
			pstmt.setInt(3, c2csoMainVO.getQuantity());
			pstmt.setString(4, c2csoMainVO.getStatus());

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
	public void update(C2csoMainVO c2csoMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(5, c2csoMainVO.getSo_id());
			pstmt.setString(1, c2csoMainVO.getPro_id());
			pstmt.setString(2, c2csoMainVO.getBuyer_id());
			pstmt.setInt(3, c2csoMainVO.getQuantity());
			pstmt.setString(4, c2csoMainVO.getStatus());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public void delete(String so_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, so_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public C2csoMainVO findByPrimaryKey(String so_id) {
		C2csoMainVO c2csoMainVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, so_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				c2csoMainVO = new C2csoMainVO();
				c2csoMainVO.setSo_id(rs.getString("SO_ID"));
				c2csoMainVO.setPro_id(rs.getString("PRO_ID"));
				c2csoMainVO.setBuyer_id(rs.getString("BUYER_ID"));
				c2csoMainVO.setQuantity(rs.getInt("QUANTITY"));
				c2csoMainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2csoMainVO.setStatus(rs.getString("STATUS"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

		return c2csoMainVO;

	}

	@Override
	public List<C2csoMainVO> getAll() {
		List<C2csoMainVO> list = new ArrayList<C2csoMainVO>();
		C2csoMainVO c2csoMainVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				c2csoMainVO = new C2csoMainVO();
				c2csoMainVO.setSo_id(rs.getString("SO_ID"));
				c2csoMainVO.setPro_id(rs.getString("PRO_ID"));
				c2csoMainVO.setBuyer_id(rs.getString("BUYER_ID"));
				c2csoMainVO.setQuantity(rs.getInt("QUANTITY"));
				c2csoMainVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2csoMainVO.setStatus(rs.getString("STATUS"));

				list.add(c2csoMainVO);
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	public static void main(String[] args) {
		C2csoMainJDBCDAO dao = new C2csoMainJDBCDAO();
		// insert
//		C2csoMainVO main1 = new C2csoMainVO();
//
//		main1.setPro_id("CP0004");
//		main1.setBuyer_id("MEM0001");
//		main1.setQuantity(5);
//
//		main1.setStatus("CST0011");// 不需要
//
//		dao.insert(main1);
//		System.out.println("insert OK!");

		// update
		C2csoMainVO main2 = new C2csoMainVO();
		main2.setSo_id("CSO0024");
		main2.setPro_id("CP0001");
		main2.setBuyer_id("MEM0001");
		main2.setQuantity(5);
		main2.setStatus("CST0001");

		dao.update(main2);
		System.out.println("update OK!");
//
//		// delete
//		dao.delete("");
//		System.out.println("DELETE OK!");
//
//		C2csoMainVO main3 = dao.findByPrimaryKey("");
//		System.out.print(main3.getSo_id() + ",");
//		System.out.print(main3.getPro_id() + ",");
//		System.out.print(main3.getBuyer_id() + ",");
//		System.out.print(main3.getQuantity() + ",");
//
//		java.util.Date dat1 = main3.getEst_time();
//		Format sfm3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//		System.out.print(sfm3.format(dat1) + ",");
//
//		System.out.print(main3.getStatus() + ",");
//
//		System.out.println();
//		System.out.println("-----------------------------");
//
//		List<C2csoMainVO> list = dao.getAll();
//		for (C2csoMainVO main4 : list) {
//			System.out.print(main4.getSo_id() + ",");
//			System.out.print(main4.getPro_id() + ",");
//			System.out.print(main4.getBuyer_id() + ",");
//			System.out.print(main4.getQuantity() + ",");
//
//			java.util.Date dat2 = main4.getEst_time();
//			Format sfm2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//			System.out.print(sfm2.format(dat2) + ",");
//
//			System.out.print(main4.getStatus() + ",");
//			}
//
//		
//		System.out.print("SELECT OK!");
	}

}
