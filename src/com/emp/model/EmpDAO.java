package com.emp.model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

public class EmpDAO implements I_EmpDAO {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO EMPLOYEE(EMP_ID,EMP_FIRSTNAME,EMP_LASTNAME,DEPT_NO,SALARY,HIREDATE,PASSWORD,STATUS)VALUES ('E' || LPAD(SEQ_MEM_ID.NEXTVAL, 4, '0'),?,?,?,?,TO_DATE(TO_CHAR(?,'YYYYMMDD'),'YYYY-MM-DD'), DBMS_RANDOM.STRING('x', 3) || DBMS_RANDOM.STRING('a', 3) || DBMS_RANDOM.STRING('x', 3),?)";
	private static final String GET_ALL_STMT = "SELECT EMP_ID,EMP_FIRSTNAME,EMP_LASTNAME,DEPT_NO,SALARY,HIREDATE,PASSWORD,STATUS FROM EMPLOYEE";
	private static final String GET_ONE_STMT = "SELECT EMP_ID,EMP_FIRSTNAME,EMP_LASTNAME,DEPT_NO,SALARY,HIREDATE,PASSWORD,STATUS FROM EMPLOYEE WHERE EMP_ID = ?";
	private static final String UPDATE = "UPDATE EMPLOYEE SET EMP_FIRSTNAME = ?,EMP_LASTNAME = ?,DEPT_NO = ?,SALARY = ?,HIREDATE = TO_DATE(TO_CHAR(?,'YYYYMMDD'),'YYYY-MM-DD'), PASSWORD = ?,STATUS = ? WHERE EMP_ID = ?";
	private static final String DELETE = "DELETE FROM EMPLOYEE WHERE EMP_ID = ?";
	private static final String GET_LASTEST_STMT = "SELECT EMP_ID FROM (SELECT EMP_ID FROM EMPLOYEE ORDER BY EMPLOYEE.EMP_ID DESC) WHERE ROWNUM <= 1";
	private static final String GET_QUERY_STNT = "SELECT EMP_ID, EMP_FIRSTNAME, EMP_LASTNAME, DEPT_NO, SALARY, HIREDATE, PASSWORD, STATUS "
			+ "FROM EMPLOYEE WHERE UPPER(EMP_ID) LIKE UPPER(?) OR UPPER(EMP_FIRSTNAME) LIKE UPPER(?) OR UPPER(EMP_LASTNAME) LIKE UPPER(?) OR UPPER(SALARY) LIKE UPPER(?) OR TO_CHAR(HIREDATE, 'YYYY-MM-DD') LIKE UPPER(?)";

	public String insert(EmpVO empVo) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String emp_id = null;

		try {

			con = ds.getConnection();
			String[] cols = { "EMP_ID" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, empVo.getEmp_firstname());
			pstmt.setString(2, empVo.getEmp_lastname());
			pstmt.setString(3, empVo.getDept_no());
			pstmt.setInt(4, empVo.getSalary());
			pstmt.setDate(5, empVo.getHiredate());
			pstmt.setInt(6, empVo.getStatus());

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				emp_id = rs.getString(1);
			}

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
		return emp_id;
	}

	public void update(EmpVO empVo) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, empVo.getEmp_firstname());
			pstmt.setString(2, empVo.getEmp_lastname());
			pstmt.setString(3, empVo.getDept_no());
			pstmt.setInt(4, empVo.getSalary());
			pstmt.setDate(5, empVo.getHiredate());
			pstmt.setString(6, empVo.getPassword());
			pstmt.setInt(7, empVo.getStatus());
			pstmt.setString(8, empVo.getEmp_id());

			pstmt.executeUpdate();

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

	public void delete(String emp_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emp_id);

			pstmt.executeUpdate();

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

	public EmpVO findByPrimaryKey(String emp_id) {
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, emp_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memVO 也稱為 Domain objects
				empVO = new EmpVO();

				empVO.setEmp_id(rs.getString("EMP_ID"));
				empVO.setEmp_firstname(rs.getString("EMP_FIRSTNAME"));
				empVO.setEmp_lastname(rs.getString("EMP_LASTNAME"));
				empVO.setDept_no(rs.getString("DEPT_NO"));
				empVO.setSalary(rs.getInt("SALARY"));
				empVO.setHiredate(rs.getDate("HIREDATE"));
				empVO.setPassword(rs.getString("PASSWORD"));
				empVO.setStatus(rs.getInt("STATUS"));
			}

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
		return empVO;
	}

	public List<EmpVO> getAll() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empVO = new EmpVO();

				empVO.setEmp_id(rs.getString("EMP_ID"));
				empVO.setEmp_firstname(rs.getString("EMP_FIRSTNAME"));
				empVO.setEmp_lastname(rs.getString("EMP_LASTNAME"));
				empVO.setDept_no(rs.getString("DEPT_NO"));
				empVO.setSalary(rs.getInt("SALARY"));
				empVO.setHiredate(rs.getDate("HIREDATE"));
				empVO.setPassword(rs.getString("PASSWORD"));
				empVO.setStatus(rs.getInt("STATUS"));

				list.add(empVO); // Store the row in the list
			}

			// Handle any driver errors
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

	public List<EmpVO> findByQuery(String query) {
		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_QUERY_STNT);

			pstmt.setString(1, "%" + query + "%");
			pstmt.setString(2, "%" + query + "%");
			pstmt.setString(3, "%" + query + "%");
			pstmt.setString(4, "%" + query + "%");
			pstmt.setString(5, "%" + query + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				empVO = new EmpVO();

				empVO.setEmp_id(rs.getString("EMP_ID"));
				empVO.setEmp_firstname(rs.getString("EMP_FIRSTNAME"));
				empVO.setEmp_lastname(rs.getString("EMP_LASTNAME"));
				empVO.setDept_no(rs.getString("DEPT_NO"));
				empVO.setSalary(rs.getInt("SALARY"));
				empVO.setHiredate(rs.getDate("HIREDATE"));
				empVO.setPassword(rs.getString("PASSWORD"));
				empVO.setStatus(rs.getInt("STATUS"));

				list.add(empVO); // Store the row in the list
			}

			// Handle any driver errors
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

	public String getLatestEmp() {
		String emp_id = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LASTEST_STMT);
			rs = pstmt.executeQuery();

			rs.next();
			emp_id = rs.getString("EMP_ID");

			// Handle any driver errors
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
		return emp_id;
	}
}
