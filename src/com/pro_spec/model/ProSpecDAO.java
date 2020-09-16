package com.pro_spec.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProSpecDAO implements I_ProSpecDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO PRO_SPEC(PRO_ID, SPECDET_ID) VALUES (? ,?)";
	private static final String UPDATE_STMT = "UPDATE PRO_SPEC SET PRO_ID = ? , SPECDET_ID = ? WHERE PRO_ID = ? and SPECDET_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM PRO_SPEC WHERE PRO_ID = ? and SPECDET_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM PRO_SPEC";
	private static final String GET_ONE_STMT = "SELECT * FROM PRO_SPEC WHERE PRO_ID = ?";
	private static final String DELETE_PROSPECS_STMT = "DELETE FROM PRO_SPEC WHERE PRO_ID = ?";

	@Override
	public void insert(ProSpecVO psVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, psVO.getPro_id());
			pstmt.setString(2, psVO.getSpecDet_id());

			pstmt.executeUpdate();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, psVO.getPro_id());
			pstmt.setString(2, specd_id);
			pstmt.setString(3, psVO.getPro_id());
			pstmt.setString(4, psVO.getSpecDet_id());

			pstmt.executeUpdate();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, pro_id);
			pstmt.setString(2, specd_id);

			pstmt.executeUpdate();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				psVO = new ProSpecVO();
				psVO.setPro_id(rs.getString("PRO_ID"));
				psVO.setSpecDet_id(rs.getString("SPECDET_ID"));
				list.add(psVO);
			}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, pro_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				psVO = new ProSpecVO();
				psVO.setPro_id(rs.getString("PRO_ID"));
				psVO.setSpecDet_id(rs.getString("SPECDET_ID"));
				list.add(psVO);
			}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_PROSPECS_STMT);

			pstmt.setString(1, pro_id);

			pstmt.executeUpdate();
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

}
