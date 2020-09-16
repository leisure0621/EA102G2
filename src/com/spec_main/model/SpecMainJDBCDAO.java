package com.spec_main.model;

import java.util.*;
import java.sql.*;

import com.spec_detail.model.SpecDetailVO;

public class SpecMainJDBCDAO implements I_SpecMainDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String pswd = "EA102G2";

	private static final String INSERT_STMT = "INSERT INTO SPEC_MAIN(SPEC_ID, CAT_ID, SPEC_DES) VALUES ('SP' || LPAD(SEQ_SPEC_ID.NEXTVAL,4,'0'),? ,?)";
	private static final String UPDATE_STMT = "UPDATE SPEC_MAIN SET CAT_ID = ? , SPEC_DES = ? WHERE SPEC_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM SPEC_MAIN WHERE SPEC_ID = ?";
	private static final String GET_ONE_STMT = "SELECT CAT_ID, SPEC_ID, SPEC_DES FROM SPEC_MAIN WHERE SPEC_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM SPEC_MAIN";
	private static final String GET_SpecD_BySpecId_STMT = "SELECT * FROM SPEC_DETAIL WHERE SPEC_ID = ? ORDER BY SPECDET_ID";

	public static void main(String[] args) {

		// 新增
//		SpecVO specVO = new SpecVO();
//		specVO.setCat_id("CAT0001");
//		specVO.setSpec_des("有無光碟機");
//		SpecJDBCDAO s = new SpecJDBCDAO();
//		s.insert(specVO);
//		System.out.println("insert complete!");

		// 修改
//		SpecVO specVO = new SpecVO();
//		specVO.setCat_id("CAT0002");
//		specVO.setSpec_des("測試測試");
//		specVO.setSpec_id("SP0001");
//		SpecJDBCDAO s = new SpecJDBCDAO();
//		s.update(specVO);
//		System.out.println("update complete!");

		// 刪除
		SpecMainJDBCDAO s = new SpecMainJDBCDAO();
//		s.delete("SP0090");
//		System.out.println("delete complete!");

		// 找一個
//		SpecMainVO specVO = s.findByPrimaryKey("SP0002");
//
//		System.out.print(specVO.getSpec_id() + ",");
//		System.out.print(specVO.getCat_id() + ",");
//		System.out.print(specVO.getSpec_des() + ",");
//		System.out.println();

		// 列全部
//		List<SpecMainVO> list = new ArrayList<SpecMainVO>();
//		list = s.getAll();
//		for (SpecMainVO specVO : list) {
//			System.out.println("{");
//			System.out.println("  Spec id: " + specVO.getSpec_id());
//			System.out.println("  Category id: " + specVO.getCat_id());
//			System.out.println("  Spec description: " + specVO.getSpec_des());
//			System.out.println("}");
//		}

		// 列子項目

		Set<SpecDetailVO> set = new LinkedHashSet<SpecDetailVO>();
		set = s.getSpecDBySpecId("SP0001");
		for (SpecDetailVO sdVO : set) {
			System.out.println("{");
			System.out.println("  Spec ID: " + sdVO.getSpec_id());
			System.out.println("  Spec detail id: " + sdVO.getSpecdet_id());
			System.out.println("  Spec detail description: " + sdVO.getDetail_des());
		}
	}

	@Override
	public void insert(SpecMainVO specVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, specVO.getCat_id());
			pstmt.setString(2, specVO.getSpec_des());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load driver. " + ce.getMessage());
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
	public void update(SpecMainVO specVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, specVO.getCat_id());
			pstmt.setString(2, specVO.getSpec_des());
			pstmt.setString(3, specVO.getSpec_id());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A databse error occured." + se.getMessage());
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
	public void delete(String spec_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, spec_id);
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
	public SpecMainVO findByPrimaryKey(String spec_id) {
		SpecMainVO specVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, spec_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				specVO = new SpecMainVO();
				specVO.setSpec_id(rs.getString("SPEC_ID"));
				specVO.setCat_id(rs.getString("CAT_ID"));
				specVO.setSpec_des(rs.getString("SPEC_DES"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

		return specVO;
	}

	@Override
	public List<SpecMainVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<SpecMainVO> list = new ArrayList<SpecMainVO>();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SpecMainVO specVO = new SpecMainVO();
				specVO.setSpec_id(rs.getString("SPEC_ID"));
				specVO.setCat_id(rs.getString("CAT_ID"));
				specVO.setSpec_des(rs.getString("SPEC_DES"));
				list.add(specVO);
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
	public Set<SpecDetailVO> getSpecDBySpecId(String spec_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Set<SpecDetailVO> set = new LinkedHashSet<SpecDetailVO>();
		SpecDetailVO specdVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pswd);
			pstmt = con.prepareStatement(GET_SpecD_BySpecId_STMT);
			
			pstmt.setString(1, spec_id);
			rs = pstmt.executeQuery();
			


			while (rs.next()) {
				specdVO = new SpecDetailVO();
				specdVO.setDetail_des(rs.getString("DETAIL_DES"));
				specdVO.setSpec_id(rs.getString("SPEC_ID"));
				specdVO.setSpecdet_id(rs.getString("SPECDET_ID"));
				set.add(specdVO);
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

		return set;
	}

}
