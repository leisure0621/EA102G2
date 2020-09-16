package com.emp_auth.model;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

public class EmpAuthDAO implements I_EmpAuthDAO {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,empAuthVO.getEmp_id());
			pstmt.setString(2,empAuthVO.getAuth_id());
			
			pstmt.executeUpdate();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, emp_id);
			pstmt.setString(2, auth_id);
			
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
	};
	
	public List<EmpAuthVO> findByPrimaryKey(String emp_id) {
		List<EmpAuthVO> list = new ArrayList<EmpAuthVO>();
		EmpAuthVO empAuthVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empAuthVO = new EmpAuthVO();
				
				empAuthVO.setEmp_id(rs.getString("EMP_ID"));
				empAuthVO.setAuth_id(rs.getString("AUTH_ID"));
				
				list.add(empAuthVO);
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
	};
}
