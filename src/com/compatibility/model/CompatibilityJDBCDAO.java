package com.compatibility.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vendor.model.VendorVO;

public class CompatibilityJDBCDAO implements I_CompatibilityDAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String pswd = "EA102G2";

	private static final String INSERT_STMT = "INSERT INTO COMPATIBILITY(COMP_ID, SPECDET_ID1, SPECDET_ID2) VALUES ('COM' || LPAD(SEQ_COMP_ID.NEXTVAL,4,'0'),? ,?)";
	private static final String UPDATE_STMT = "UPDATE COMPATIBILITY SET SPECDET_ID1 = ?, SPECDET_ID2 = ? WHERE COMP_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM COMPATIBILITY WHERE COMP_ID = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM COMPATIBILITY WHERE COMP_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM COMPATIBILITY";

	public static void main(String[] args) {
		CompatibilityJDBCDAO dao = new CompatibilityJDBCDAO();
//		CompatibilityVO cVO = new CompatibilityVO();
		List<CompatibilityVO> list = new ArrayList<CompatibilityVO>();
//		cVO.setComp_id("COM0061");
//		cVO.setSpecDet_id1("SPD0003");
//		cVO.setSpecDet_id2("SPD0004");
//		dao.update(cVO);
//		cVO = dao.findByPrimaryKey("COM0001");
//		System.out.println(cVO.getComp_id());
//		System.out.println(cVO.getSpecDet_id1());
//		System.out.println(cVO.getSpecDet_id2());
		list = dao.getAll();
		for (CompatibilityVO cVO : list) {
			System.out.println(cVO.getComp_id());
			System.out.println(cVO.getSpecDet_id1());
			System.out.println(cVO.getSpecDet_id2());
			System.out.println();
		}
		System.out.println("Complete!");

	}

	@Override
	public void insert(CompatibilityVO cVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, cVO.getSpecDet_id1());
			pstmt.setString(2, cVO.getSpecDet_id2());

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
	public void update(CompatibilityVO cVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, cVO.getSpecDet_id1());
			pstmt.setString(2, cVO.getSpecDet_id2());
			pstmt.setString(3, cVO.getComp_id());

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
	public void delete(String comp_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, comp_id);

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
	public CompatibilityVO findByPrimaryKey(String comp_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CompatibilityVO cVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, comp_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cVO = new CompatibilityVO();
				cVO.setComp_id(rs.getString("COMP_ID"));
				cVO.setSpecDet_id1(rs.getString("SPECDET_ID1"));
				cVO.setSpecDet_id2(rs.getString("SPECDET_ID2"));
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

		return cVO;
	}

	@Override
	public List<CompatibilityVO> getAll() {
		List<CompatibilityVO> list = new ArrayList<CompatibilityVO>();
		CompatibilityVO cVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cVO = new CompatibilityVO();
				cVO.setComp_id(rs.getString("COMP_ID"));
				cVO.setSpecDet_id1(rs.getString("SPECDET_ID1"));
				cVO.setSpecDet_id2(rs.getString("SPECDET_ID2"));
				list.add(cVO);
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
	public Set<CompatibilityVO> getCompsByPro(String pro_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
