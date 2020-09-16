package com.c2cso_status.model;

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

import com.c2cpro_rep.model.C2cproRepVO;

public class C2csoStatusJDBCDAO implements I_C2csoStatusDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	private static final String INSERT_STMT = "INSERT INTO C2CSO_STATUS(STATUS_ID,STATUS_DES)"
			+ "VALUES('CST'|| LPAD(SEQ_CSOST_ID.NEXTVAL,4,'0'),?)";
	private static final String GET_ALL_STMT = "SELECT STATUS_ID,STATUS_DES FROM C2CSO_STATUS ORDER BY STATUS_ID";
	private static final String GET_ONE_STMT = "SELECT STATUS_ID,STATUS_DES FROM C2CSO_STATUS WHERE STATUS_ID=? ";
	private static final String DELETE = "DELETE FROM C2CSO_STATUS WHERE STATUS_ID=?";
	private static final String UPDATE = "UPDATE C2CSO_STATUS SET STATUS_DES=? WHERE STATUS_ID=? ";

	@Override
	public void insert(C2csoStatusVO c2csoStatusVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, c2csoStatusVO.getStatus_des());

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
	public void update(C2csoStatusVO c2csoStatusVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(2, c2csoStatusVO.getStatus_id());
			pstmt.setString(1, c2csoStatusVO.getStatus_des());

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
	public void delete(String status_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, status_id);

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
	public C2csoStatusVO findByPrimaryKey(String status_id) {
		C2csoStatusVO c2csoStatusVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, status_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				c2csoStatusVO = new C2csoStatusVO();
				c2csoStatusVO.setStatus_id(rs.getString("STATUS_ID"));
				c2csoStatusVO.setStatus_des(rs.getString("STATUS_DES"));

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

		return c2csoStatusVO;
	}

	@Override
	public List<C2csoStatusVO> getAll() {
		List<C2csoStatusVO> list = new ArrayList<C2csoStatusVO>();
		C2csoStatusVO c2csoStatusVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				c2csoStatusVO = new C2csoStatusVO();
				c2csoStatusVO.setStatus_id(rs.getString("STATUS_ID"));
				c2csoStatusVO.setStatus_des(rs.getString("STATUS_DES"));

				list.add(c2csoStatusVO);
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
		C2csoStatusJDBCDAO dao = new C2csoStatusJDBCDAO();
		// insert
		C2csoStatusVO main1 = new C2csoStatusVO();
		main1.setStatus_des("不爽出貨");
		dao.insert(main1);
		System.out.println("insert OK!");

		// update
		C2csoStatusVO main2 = new C2csoStatusVO();
		main2.setStatus_id("CST0018");
		main2.setStatus_des("更改");
		dao.update(main2);
		System.out.println("update OK!");

		// delete
		dao.delete("CST0013");
		System.out.println("DELETE OK!");
		// select
		C2csoStatusVO main3 = dao.findByPrimaryKey("CST0001");
		System.out.print(main3.getStatus_id() + ",");
		System.out.print(main3.getStatus_des());

		System.out.println();
		System.out.println("-----------------------------");

		List<C2csoStatusVO> list = dao.getAll();
		for (C2csoStatusVO main4 : list) {
			System.out.print(main4.getStatus_id() + ",");
			System.out.print(main4.getStatus_des());
			System.out.println( );
		}
		System.out.print("SELECT OK!");
	}

}
