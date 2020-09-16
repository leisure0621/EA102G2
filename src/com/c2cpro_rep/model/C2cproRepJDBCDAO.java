package com.c2cpro_rep.model;

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

public class C2cproRepJDBCDAO implements I_C2cproRepDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	private static final String INSERT_STMT = "INSERT INTO C2CPRO_REP(REP_ID,INFORMANT,REPORTED_CONTENT,PICTURE,CASE_DESCRIPTION,PROCESS)"
			+ "VALUES('CPR'|| LPAD(SEQ_CPROREP_ID.NEXTVAL,4,'0'),?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT REP_ID,INFORMANT,REPORTED_CONTENT,PICTURE,CASE_DESCRIPTION,PROCESS,EST_TIME,FINISH_TIME FROM C2CPRO_REP ORDER BY REP_ID DESC";
	private static final String GET_ONE_STMT = "SELECT REP_ID,INFORMANT,REPORTED_CONTENT,PICTURE,CASE_DESCRIPTION,PROCESS,EST_TIME,FINISH_TIME FROM C2CPRO_REP WHERE REP_ID =?";
	private static final String DELETE = "DELETE FROM C2CPRO_REP WHERE REP_ID=?";
	private static final String UPDATE = "UPDATE C2CPRO_REP SET PROCESS=?,FINISH_TIME= CURRENT_TIMESTAMP WHERE REP_ID = ?";

	@Override
	public void insert(C2cproRepVO c2cproRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, c2cproRepVO.getInformant());
			pstmt.setString(2, c2cproRepVO.getReported_content());
			pstmt.setBytes(3, c2cproRepVO.getPicture());
			pstmt.setString(4, c2cproRepVO.getCase_description());
			pstmt.setInt(5, c2cproRepVO.getProcess());
		//	pstmt.setTimestamp(6, c2cproRepVO.getFinish_time());
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
	public void update(C2cproRepVO c2cproRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(2, c2cproRepVO.getRep_id());
//			pstmt.setString(1, c2cproRepVO.getInformant());
//			pstmt.setString(2, c2cproRepVO.getReported_content());
//			pstmt.setBytes(3, c2cproRepVO.getPicture());
//			pstmt.setString(4, c2cproRepVO.getCase_description());
			pstmt.setInt(1, c2cproRepVO.getProcess());

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
	public void delete(String rep_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rep_id);

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
	public C2cproRepVO findByPrimaryKey(String rep_id) {
		C2cproRepVO c2cproRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rep_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				c2cproRepVO = new C2cproRepVO();
				c2cproRepVO.setRep_id(rs.getString("REP_ID"));
				c2cproRepVO.setInformant(rs.getString("INFORMANT"));
				c2cproRepVO.setReported_content(rs.getString("REPORTED_CONTENT"));
				c2cproRepVO.setPicture(rs.getBytes("PICTURE"));
				c2cproRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				c2cproRepVO.setProcess(rs.getInt("PROCESS"));
				c2cproRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2cproRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));

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

		return c2cproRepVO;

	}

	@Override
	public List<C2cproRepVO> getAll() {

		List<C2cproRepVO> list = new ArrayList<C2cproRepVO>();
		C2cproRepVO c2cproRepVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				c2cproRepVO = new C2cproRepVO();
				c2cproRepVO.setRep_id(rs.getString("REP_ID"));
				c2cproRepVO.setInformant(rs.getString("INFORMANT"));
				c2cproRepVO.setReported_content(rs.getString("REPORTED_CONTENT"));
				c2cproRepVO.setPicture(rs.getBytes("PICTURE"));
				c2cproRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				c2cproRepVO.setProcess(rs.getInt("PROCESS"));
				c2cproRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2cproRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));
				list.add(c2cproRepVO);
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
		C2cproRepJDBCDAO dao = new C2cproRepJDBCDAO();
		// insert
		C2cproRepVO main1 = new C2cproRepVO();
//		main1.setRep_id("CPR0007");
		main1.setInformant("MEM0001");
		main1.setReported_content("CP0002");
//		main1.setPicture();//BLOB
		main1.setCase_description("手滑");
		main1.setProcess(0);
//		main1.setEst_time();//不需要
//		main1.setFinish_time();//不需要
		dao.insert(main1);
		System.out.println("insert OK!");

		// update
		C2cproRepVO main2 = new C2cproRepVO();
		main2.setRep_id("CPR0008");
		main2.setInformant("MEM0001");
		main2.setReported_content("CP0002");
//		main2.setPicture();//BLOB
		main2.setCase_description("手超級滑");
		main2.setProcess(0);

		dao.update(main2);
		System.out.println("update OK!");

		// delete
		dao.delete("CPR0007");
		System.out.println("DELETE OK!");

		C2cproRepVO main3 = dao.findByPrimaryKey("CPR0008");
		System.out.print(main3.getRep_id() + ",");
		System.out.print(main3.getInformant() + ",");
		System.out.print(main3.getReported_content() + ",");
		System.out.print(main3.getPicture() + ",");
		System.out.print(main3.getCase_description() + ",");
		System.out.print(main3.getProcess() + ",");

		java.util.Date dat1 = main3.getEst_time();
		Format sfm3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		System.out.print(sfm3.format(dat1) + ",");
		Date dat2 = main3.getFinish_time();
		if (dat2 == null) {
			System.out.println(dat2);
		} else {
			System.out.print(sfm3.format(dat2));
		}

		System.out.println();
		System.out.println("-----------------------------");

		List<C2cproRepVO> list = dao.getAll();
		for (C2cproRepVO main4 : list) {
			System.out.print(main4.getRep_id() + ",");
			System.out.print(main4.getInformant() + ",");
			System.out.print(main4.getReported_content() + ",");
			System.out.print(main4.getPicture() + ",");
			System.out.print(main4.getCase_description() + ",");
			System.out.print(main4.getProcess() + ",");
			Date dat5 = main4.getEst_time();
			Format sfm2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			System.out.print(sfm2.format(dat5) + ",");

			Date dat4 = main4.getFinish_time();
			if (dat4 == null) {

				System.out.println(dat4);

			} else {
				Format sfm4 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				System.out.print(sfm4.format(dat4));
				System.out.println();

			}

		}
		System.out.print("SELECT OK!");
	}

}
