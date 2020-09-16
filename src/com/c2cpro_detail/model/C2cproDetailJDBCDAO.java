package com.c2cpro_detail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class C2cproDetailJDBCDAO implements I_C2cproDetailDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	private static final String INSERT_STMT = "INSERT INTO C2CPRO_DETAIL(PRO_ID,SPEC_ID,SPEC_DETAIL)" + "VALUES(?,?,?)";
	private static final String GET_ALL_STMT = "SELECT PRO_ID,SPEC_ID,SPEC_DETAIL  FROM C2CPRO_DETAIL ORDER BY  PRO_ID";
	private static final String GET_ONE_STMT = "SELECT PRO_ID,SPEC_ID,SPEC_DETAIL  FROM C2CPRO_DETAIL WHERE PRO_ID=?  ";
	private static final String DELETE = "DELETE FROM C2CPRO_DETAIL WHERE PRO_ID=?";
	private static final String UPDATE = "UPDATE C2CPRO_DETAIL SET SPEC_DETAIL=? WHERE PRO_ID=? and SPEC_ID = ?";

	@Override
	public void insert(C2cproDetailVO c2cproDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, c2cproDetailVO.getPro_id());
			pstmt.setString(2, c2cproDetailVO.getSpec_id());
			pstmt.setString(3, c2cproDetailVO.getSpec_detail());

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
	public void update(C2cproDetailVO c2cproDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(2, c2cproDetailVO.getPro_id());
			pstmt.setString(3, c2cproDetailVO.getSpec_id());
			pstmt.setString(1, c2cproDetailVO.getSpec_detail());

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
	public void delete(String pro_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, pro_id);

			pstmt.executeUpdate();

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
	public List<C2cproDetailVO> findByPrimaryKey(String pro_id) {
		List<C2cproDetailVO> list = new ArrayList<C2cproDetailVO>();
		C2cproDetailVO c2cproDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, pro_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				c2cproDetailVO = new C2cproDetailVO();
				c2cproDetailVO.setPro_id(rs.getString("PRO_ID"));
				c2cproDetailVO.setSpec_id(rs.getString("SPEC_ID"));
				c2cproDetailVO.setSpec_detail(rs.getString("SPEC_DETAIL"));
				list.add(c2cproDetailVO);
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

	@Override
	public List<C2cproDetailVO> getAll() {
		List<C2cproDetailVO> list = new ArrayList<C2cproDetailVO>();
		C2cproDetailVO c2cproDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				c2cproDetailVO = new C2cproDetailVO();
				c2cproDetailVO.setPro_id(rs.getString("PRO_ID"));
				c2cproDetailVO.setSpec_id(rs.getString("SPEC_ID"));
				c2cproDetailVO.setSpec_detail(rs.getString("SPEC_DETAIL"));

				list.add(c2cproDetailVO);
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
		C2cproDetailJDBCDAO dao = new C2cproDetailJDBCDAO();

//		C2cproDetailVO main1 = new C2cproDetailVO();
//		main1.setPro_id("CP0009");
//		main1.setSpec_id("SP0001");
//		main1.setSpec_detail("手滑拉");
//		dao.insert(main1);
//		System.out.println("insert OK!");
//
//		C2cproDetailVO main2 = new C2cproDetailVO();
//		main2.setPro_id("CP0006");
//		main2.setSpec_id("SP0002");
//		main2.setSpec_detail("手超級滑");
//		dao.update(main2);
//		System.out.println("update OK!");
//		
//		dao.delete("CP0008");
//		System.out.println("DELETE OK!");

		List<C2cproDetailVO> list1 = dao.findByPrimaryKey("CP0001");
		for(C2cproDetailVO main3 : list1) {
			System.out.print(main3.getPro_id() + " , ");
			System.out.print(main3.getSpec_id() + " , ");
			System.out.print(main3.getSpec_detail() + " , ");
			System.out.println();
			
		}
		System.out.println("==============================");

		List<C2cproDetailVO> list2 = dao.getAll();
		for (C2cproDetailVO main4 : list2) {
			System.out.print(main4.getPro_id() + " , ");
			System.out.print(main4.getSpec_id() + " , ");
			System.out.print(main4.getSpec_detail() + " , ");
			System.out.println();

		}

	}

}
