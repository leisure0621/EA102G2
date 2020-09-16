package com.c2cso_rep.model;

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

public class C2csoRepJDBCDAO implements I_C2csoRepDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	private static final String INSERT_STMT = "INSERT INTO C2CSO_REP(REP_ID,INFORMANT,SO_ID,CASE_DESCRIPTION,PROCESS)"
			+ "VALUES('COR'|| LPAD(SEQ_CSOREP_ID.NEXTVAL,4,'0'),?,?,?,0)";
	private static final String GET_ALL_STMT = "SELECT REP_ID,INFORMANT,SO_ID,CASE_DESCRIPTION,PROCESS,EST_TIME,FINISH_TIME FROM C2CSO_REP ORDER BY REP_ID";
	private static final String GET_ONE_STMT = "SELECT REP_ID,INFORMANT,SO_ID,CASE_DESCRIPTION,PROCESS,EST_TIME,FINISH_TIME FROM C2CSO_REP WHERE REP_ID=? ";
	private static final String DELETE = "DELETE FROM C2CSO_REP WHERE REP_ID=?";
	private static final String UPDATE = "UPDATE C2CSO_REP SET INFORMANT=?,SO_ID=?,CASE_DESCRIPTION=?,PROCESS=?,FINISH_TIME = CURRENT_TIMESTAMP WHERE REP_ID=?";

	@Override
	public void insert(C2csoRepVO c2csoRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, c2csoRepVO.getInformant());
			pstmt.setString(2, c2csoRepVO.getSo_id());
			pstmt.setString(3, c2csoRepVO.getCase_description());
			
			

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
	public void update(C2csoRepVO c2csoRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(5, c2csoRepVO.getRep_id());
			pstmt.setString(1, c2csoRepVO.getInformant());
			pstmt.setString(2, c2csoRepVO.getSo_id());
			pstmt.setString(3, c2csoRepVO.getCase_description());
			pstmt.setInt(4, c2csoRepVO.getProcess());

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
	public C2csoRepVO findByPrimaryKey(String rep_id) {
		C2csoRepVO c2csoRepVO = null;
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

				c2csoRepVO = new C2csoRepVO();
				c2csoRepVO.setRep_id(rs.getString("REP_ID"));
				c2csoRepVO.setInformant(rs.getString("INFORMANT"));
				c2csoRepVO.setSo_id(rs.getString("SO_ID"));
				c2csoRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				c2csoRepVO.setProcess(rs.getInt("PROCESS"));
				c2csoRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2csoRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));

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

		return c2csoRepVO;

	}

	@Override
	public List<C2csoRepVO> getAll() {
		List<C2csoRepVO> list = new ArrayList<C2csoRepVO>();
		C2csoRepVO c2csoRepVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				c2csoRepVO = new C2csoRepVO();
				c2csoRepVO.setRep_id(rs.getString("REP_ID"));
				c2csoRepVO.setInformant(rs.getString("INFORMANT"));
				c2csoRepVO.setSo_id(rs.getString("SO_ID"));
				c2csoRepVO.setCase_description(rs.getString("CASE_DESCRIPTION"));
				c2csoRepVO.setProcess(rs.getInt("PROCESS"));
				c2csoRepVO.setEst_time(rs.getTimestamp("EST_TIME"));
				c2csoRepVO.setFinish_time(rs.getTimestamp("FINISH_TIME"));
				list.add(c2csoRepVO);
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
		C2csoRepJDBCDAO dao = new C2csoRepJDBCDAO();

		C2csoRepVO main1 = new C2csoRepVO();
		main1.setInformant("MEM0002");
		main1.setSo_id("CSO0001");
		main1.setCase_description("GGG");
		dao.insert(main1);
		System.out.println("insert OK!");

		// update
		C2csoRepVO main2 = new C2csoRepVO();
		main2.setRep_id("COR0006");
		main2.setInformant("MEM0002");
		main2.setSo_id("CSO0001");
		main2.setCase_description("G");
		main2.setProcess(1);
		dao.update(main2);
		System.out.println("update OK!");

		// delete
		dao.delete("COR0006");
		System.out.println("DELETE OK!");

		C2csoRepVO main3 = dao.findByPrimaryKey("COR0001");
		System.out.print(main3.getRep_id() + ",");
		System.out.print(main3.getInformant() + ",");
		System.out.print(main3.getSo_id() + ",");
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

		List<C2csoRepVO> list = dao.getAll();
		for (C2csoRepVO main4 : list) {
			System.out.print(main4.getRep_id() + ",");
			System.out.print(main4.getInformant() + ",");
			System.out.print(main4.getSo_id() + ",");
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
