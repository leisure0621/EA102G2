package com.dept.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeptJDBCDAO implements I_DeptDAO{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	
	private static final String INSERT_STMT = 
			"INSERT INTO DEPT( DEPT_ID, DEPT_DES ) VALUES ( 'D' || LPAD(SEQ_DEPT_ID.NEXTVAL, 4, '0'), ? ) ";
	private static final String GET_ALL_STMT = 
			"SELECT DEPT_ID, DEPT_DES  FROM DEPT";
	private static final String GET_ONE_STMT = 
			"SELECT DEPT_ID, DEPT_DES  FROM DEPT WHERE DEPT_ID = ?";
	private static final String UPDATE =
			"UPDATE DEPT SET DEPT_DES = ? WHERE DEPT_ID = ?";
	private static final String DELETE = 
			"DELETE FROM DEPT WHERE DEPT_ID = ?";
	
	@Override
	public void insert(DeptVO deptVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,deptVO.getDept_des());
			
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
	public void update(DeptVO deptVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,deptVO.getDept_des());
			pstmt.setString(2,deptVO.getDept_id());
			
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
	public void delete(String dept_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, dept_id);			
			
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
	public DeptVO findByPrimaryKey(String dept_id) {
		// TODO Auto-generated method stub
		DeptVO deptVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, dept_id);		

			rs = pstmt.executeQuery();

			while (rs.next()) {
				deptVO = new DeptVO();
				
				deptVO.setDept_id(rs.getString("DEPT_ID"));
				deptVO.setDept_des(rs.getString("DEPT_DES"));
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
		return deptVO;
	}
	@Override
	public List<DeptVO> getAll() {
		// TODO Auto-generated method stub
		List<DeptVO> list = new ArrayList<DeptVO>();
		DeptVO deptVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				deptVO = new DeptVO();
				
				deptVO.setDept_id(rs.getString("DEPT_ID"));
				deptVO.setDept_des(rs.getString("DEPT_DES"));
				
				list.add(deptVO);
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
		DeptJDBCDAO dao = new DeptJDBCDAO();
		
		DeptVO deptVO  = new DeptVO();
		deptVO.setDept_des("財務部");
		dao.insert(deptVO);
		System.out.println("-------------------------------------------");
		
		DeptVO deptVO1  = new DeptVO();
		deptVO1.setDept_id("D0004");
		deptVO1.setDept_des("總經理");
		dao.update(deptVO1);
		
		DeptVO list1 = dao.findByPrimaryKey("D0004");
		System.out.print(list1.getDept_id()+"\t");
		System.out.print(list1.getDept_des()+"\t");
		System.out.println();
		System.out.println("-------------------------------------------");
		
		List<DeptVO> list = dao.getAll();
		for (DeptVO deptVO11 : list) {
			System.out.print(deptVO11.getDept_id()+"\t");
			System.out.print(deptVO11.getDept_des()+"\t");
			System.out.println();
		}
		System.out.println("-------------------------------------------");
		
		// 刪除
		dao.delete("D0022");
		System.out.println("刪除 D0022 成功");
		System.out.println("-------------------------------------------");
	}
}
