package com.pro_spec.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProSpecJDBCDAO implements I_ProSpecDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String pswd = "EA102G2";

	private static final String INSERT_STMT = "INSERT INTO PRO_SPEC(PRO_ID, SPECDET_ID) VALUES (? ,?)";
	private static final String UPDATE_STMT = "UPDATE PRO_SPEC SET PRO_ID = ? , SPECDET_ID = ? WHERE PRO_ID = ? and SPECDET_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM PRO_SPEC WHERE PRO_ID = ? and SPECDET_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM PRO_SPEC";
	private static final String GET_ONE_STMT = "SELECT * FROM PRO_SPEC WHERE PRO_ID = ?";
	private static final String DELETE_PROSPECS_STMT = "DELETE FROM PRO_SPEC WHERE PRO_ID = ?";

	public static void main(String[] args) {
		ProSpecJDBCDAO dao = new ProSpecJDBCDAO();
//		ProSpecVO psVO = new ProSpecVO();
//		psVO.setPro_id("BP0002");
//		psVO.setSpecDet_id("SPD0010");
//		dao.insert(psVO);
//		System.out.println("ok!");
//		String pro_id = "BP0002";
//		String specd_id = "SPD0011";
//		dao.update(psVO, specd_id);
//		dao.delete(pro_id, specd_id);
//		System.out.println("delete complete!");
//		List<ProSpecVO> list = new ArrayList<ProSpecVO>();
//		list = dao.getAll();
//		for (ProSpecVO psVO : list) {
//			System.out.println("{");
//			System.out.println("  product: " + psVO.getPro_id());
//			System.out.println("  spec: " + psVO.getSpecDet_id());
//			System.out.println("}");
//		}
//		List<ProSpecVO> list = new ArrayList<ProSpecVO>();
//		list = dao.findByPrimaryKey("BP0001");
//		for (ProSpecVO psVO : list) {
//			System.out.println(psVO.getPro_id());
//			System.out.println(psVO.getSpecDet_id());
//		}

		dao.deleteSpecsFromPro("BP0002");
	}

	@Override
	public void insert(ProSpecVO psVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, psVO.getPro_id());
			pstmt.setString(2, psVO.getSpecDet_id());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(ProSpecVO psVO, String specd_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, psVO.getPro_id());
			pstmt.setString(2, specd_id);
			pstmt.setString(3, psVO.getPro_id());
			pstmt.setString(4, psVO.getSpecDet_id());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String pro_id, String specd_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, pro_id);
			pstmt.setString(2, specd_id);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database erro occured. " + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public List<ProSpecVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<ProSpecVO> list = new ArrayList<ProSpecVO>();
		ProSpecVO psVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				psVO = new ProSpecVO();
				psVO.setPro_id(rs.getString("PRO_ID"));
				psVO.setSpecDet_id(rs.getString("SPECDET_ID"));
				list.add(psVO);
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

		return list;
	}

	@Override
	public List<ProSpecVO> findByPrimaryKey(String pro_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<ProSpecVO> list = new ArrayList<ProSpecVO>();
		ProSpecVO psVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, pro_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				psVO = new ProSpecVO();
				psVO.setPro_id(rs.getString("PRO_ID"));
				psVO.setSpecDet_id(rs.getString("SPECDET_ID"));
				list.add(psVO);
			}
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

		return list;
	}

	@Override
	public void deleteSpecsFromPro(String pro_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(DELETE_PROSPECS_STMT);

			pstmt.setString(1, pro_id);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
}
