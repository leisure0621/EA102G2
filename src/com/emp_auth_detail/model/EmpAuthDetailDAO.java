package com.emp_auth_detail.model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.DataSource;

public class EmpAuthDetailDAO implements I_EmpAuthDetailDAO {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,empAuthDetailVO.getAuth_des());
			
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
	public void update(EmpAuthDetailVO empAuthDetailVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,empAuthDetailVO.getAuth_des());
			pstmt.setString(2,empAuthDetailVO.getAuth_id());
			
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
	public void delete(String auth_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, auth_id);			
			
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
	public EmpAuthDetailVO findByPrimaryKey(String auth_id) {
		// TODO Auto-generated method stub
		EmpAuthDetailVO empAuthDetailVO = null;
    
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, auth_id);		

			rs = pstmt.executeQuery();

			while (rs.next()) {
				empAuthDetailVO = new EmpAuthDetailVO();
				
				empAuthDetailVO.setAuth_id(rs.getString("AUTH_ID"));
				empAuthDetailVO.setAuth_des(rs.getString("AUTH_DES"));
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				empAuthDetailVO = new EmpAuthDetailVO();
				
				empAuthDetailVO.setAuth_id(rs.getString("AUTH_ID"));
				empAuthDetailVO.setAuth_des(rs.getString("AUTH_DES"));
				
				list.add(empAuthDetailVO);
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
