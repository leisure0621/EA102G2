package com.spec_main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.spec_detail.model.SpecDetailVO;

public class SpecMainDAO implements I_SpecMainDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO SPEC_MAIN(SPEC_ID, CAT_ID, SPEC_DES) VALUES ('SP' || LPAD(SEQ_SPEC_ID.NEXTVAL,4,'0'),? ,?)";
	private static final String UPDATE_STMT = "UPDATE SPEC_MAIN SET CAT_ID = ? , SPEC_DES = ? WHERE SPEC_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM SPEC_MAIN WHERE SPEC_ID = ?";
	private static final String GET_ONE_STMT = "SELECT CAT_ID, SPEC_ID, SPEC_DES FROM SPEC_MAIN WHERE SPEC_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM SPEC_MAIN";
	private static final String GET_SpecD_BySpecId_STMT = "SELECT * FROM SPEC_DETAIL WHERE SPEC_ID = ? ORDER BY SPECDET_ID";

	@Override
	public void insert(SpecMainVO specVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, specVO.getCat_id());
			pstmt.setString(2, specVO.getSpec_des());
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
	public void update(SpecMainVO specVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, specVO.getCat_id());
			pstmt.setString(2, specVO.getSpec_des());
			pstmt.setString(3, specVO.getSpec_id());

			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, spec_id);
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
	public SpecMainVO findByPrimaryKey(String spec_id) {
		SpecMainVO specVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, spec_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				specVO = new SpecMainVO();
				specVO.setSpec_id(rs.getString("SPEC_ID"));
				specVO.setCat_id(rs.getString("CAT_ID"));
				specVO.setSpec_des(rs.getString("SPEC_DES"));
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SpecMainVO specVO = new SpecMainVO();
				specVO.setSpec_id(rs.getString("SPEC_ID"));
				specVO.setCat_id(rs.getString("CAT_ID"));
				specVO.setSpec_des(rs.getString("SPEC_DES"));
				list.add(specVO);
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
	public Set<SpecDetailVO> getSpecDBySpecId(String spec_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Set<SpecDetailVO> set = new LinkedHashSet<SpecDetailVO>();
		SpecDetailVO specdVO = null;

		try {
			con = ds.getConnection();
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
