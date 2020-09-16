package com.emp_auth_detail.model;

import java.sql.*;
import java.util.*;

public class EmpAuthDetailJDBCDAO implements I_EmpAuthDetailDAO{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	
	private static final String INSERT_STMT = 
			"INSERT INTO EMP_AUTH_DETAIL( AUTH_ID, AUTH_DES ) VALUES ( 'AUT' || LPAD(SEQ_AUTH_ID.NEXTVAL, 4, '0'), ? ) ";
	private static final String GET_ALL_STMT = 
			"SELECT AUTH_ID, AUTH_DES  FROM EMP_AUTH_DETAIL";
	private static final String GET_ONE_STMT = 
			"SELECT AUTH_ID, AUTH_DES  FROM EMP_AUTH_DETAIL WHERE AUTH_ID = ?";
	private static final String UPDATE =
			"UPDATE EMP_AUTH_DETAIL SET AUTH_DES = ? WHERE AUTH_ID = ?";
	private static final String DELETE = 
			"DELETE FROM EMP_AUTH_DETAIL WHERE AUTH_ID = ?";
	
	@Override
	public void insert(EmpAuthDetailVO empAuthDetailVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,empAuthDetailVO.getAuth_des());
			
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
	public void update(EmpAuthDetailVO empAuthDetailVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,empAuthDetailVO.getAuth_des());
			pstmt.setString(2,empAuthDetailVO.getAuth_id());
			
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
	public void delete(String auth_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, auth_id);			
			
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
	public EmpAuthDetailVO findByPrimaryKey(String auth_id) {
		// TODO Auto-generated method stub
		EmpAuthDetailVO empAuthDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, auth_id);		

			rs = pstmt.executeQuery();

			while (rs.next()) {
				empAuthDetailVO = new EmpAuthDetailVO();
				
				empAuthDetailVO.setAuth_id(rs.getString("AUTH_ID"));
				empAuthDetailVO.setAuth_des(rs.getString("AUTH_DES"));
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
		return empAuthDetailVO;
	}

	@Override
	public List<EmpAuthDetailVO> getAll() {
		// TODO Auto-generated method stub
		List<EmpAuthDetailVO> list = new ArrayList<EmpAuthDetailVO>();
		EmpAuthDetailVO empAuthDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				empAuthDetailVO = new EmpAuthDetailVO();
				
				empAuthDetailVO.setAuth_id(rs.getString("AUTH_ID"));
				empAuthDetailVO.setAuth_des(rs.getString("AUTH_DES"));
				
				list.add(empAuthDetailVO);
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
	
	public static void main (String[] args) {
		EmpAuthDetailJDBCDAO dao = new EmpAuthDetailJDBCDAO();
		
		EmpAuthDetailVO empAuthDetailVO  = new EmpAuthDetailVO();
		empAuthDetailVO.setAuth_des("00狗");
		dao.insert(empAuthDetailVO);
		System.out.println("-------------------------------------------");
		
		EmpAuthDetailVO empAuthDetailVO1  = new EmpAuthDetailVO();
		empAuthDetailVO1.setAuth_id("AUT0026");
		empAuthDetailVO1.setAuth_des("狗與我");
		dao.update(empAuthDetailVO1);
		
		EmpAuthDetailVO list1 = dao.findByPrimaryKey("AUT0007");
		System.out.print(list1.getAuth_id()+"\t");
		System.out.print(list1.getAuth_des()+"\t");
		System.out.println();
		System.out.println("-------------------------------------------");
		
		List<EmpAuthDetailVO> list = dao.getAll();
		for (EmpAuthDetailVO empAuthDetailVO11 : list) {
			System.out.print(empAuthDetailVO11.getAuth_id()+"\t");
			System.out.print(empAuthDetailVO11.getAuth_des()+"\t");
			System.out.println();
		}
		System.out.println("-------------------------------------------");
		
		// 刪除
		dao.delete("AUT0026");
		System.out.println("刪除 AUT0026 成功");
		System.out.println("-------------------------------------------");
	}

}
