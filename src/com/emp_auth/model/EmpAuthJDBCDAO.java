package com.emp_auth.model;

import java.sql.*;
import java.util.*;

public class EmpAuthJDBCDAO implements I_EmpAuthDAO{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	
	// 新增
	private static final String INSERT_STMT = 
			"INSERT INTO EMP_AUTH( EMP_ID, AUTH_ID )VALUES ( ?, ? )";
	// 搜尋多項
	private static final String GET_ALL_STMT = 
			"SELECT EMP_ID, AUTH_ID FROM EMP_AUTH";
	// 搜尋單項
	private static final String GET_ONE_STMT = 
			"SELECT EMP_ID, AUTH_ID FROM EMP_AUTH WHERE EMP_ID = ?";
	// 摻除項目
	private static final String DELETE = 
			"DELETE FROM EMP_AUTH WHERE EMP_ID = ? AND AUTH_ID = ?";
	
	public void insert(EmpAuthVO empAuthVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,empAuthVO.getEmp_id());
			pstmt.setString(2,empAuthVO.getAuth_id());
			
			pstmt.executeUpdate();
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
	};
	public void delete(String emp_id, String auth_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, emp_id);
			pstmt.setString(2, auth_id);
			
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
	};
	public List<EmpAuthVO> findByPrimaryKey(String emp_id) {
		List<EmpAuthVO> list = new ArrayList<EmpAuthVO>();
		EmpAuthVO empAuthVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, emp_id);
			
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				empAuthVO = new EmpAuthVO();
				
				empAuthVO.setEmp_id(rs.getString("EMP_ID"));
				empAuthVO.setAuth_id(rs.getString("AUTH_ID"));
				
				list.add(empAuthVO);
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
	};
	public List<EmpAuthVO> getAll() {
		List<EmpAuthVO> list = new ArrayList<EmpAuthVO>();
		EmpAuthVO empAuthVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empAuthVO = new EmpAuthVO();
				
				empAuthVO.setEmp_id(rs.getString("EMP_ID"));
				empAuthVO.setAuth_id(rs.getString("AUTH_ID"));
				
				list.add(empAuthVO);
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
	};
	
	public static void main (String[] args) {
		EmpAuthJDBCDAO dao = new EmpAuthJDBCDAO();
		
//		EmpAuthVO empAuthVO = new EmpAuthVO();
//		empAuthVO.setEmp_id("E0004");
//		empAuthVO.setAuth_id("AUT0003");
//		dao.insert(empAuthVO);
		
		List<EmpAuthVO> list1 = dao.findByPrimaryKey("E0002");
		for (EmpAuthVO empAuthVO1 : list1) {
			System.out.print(empAuthVO1.getEmp_id()+"\t");
			System.out.print(empAuthVO1.getAuth_id()+"\t");
			System.out.println();
		}
		System.out.println("-------------------------------------------");
		
//		List<EmpAuthVO> list = dao.getAll();
//		for (EmpAuthVO empAuthVO1 : list) {
//			System.out.print(empAuthVO1.getEmp_id()+"\t");
//			System.out.print(empAuthVO1.getAuth_id()+"\t");
//			System.out.println();
//		}
//		System.out.println("-------------------------------------------");
//		
//		dao.delete("E0004", "AUT0003");
//		System.out.println("刪除  E0004 的 AUT0003 成功");
//		System.out.println("-------------------------------------------");
	}
}
