package com.c2cpro_main.model;

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

public class C2cproMainJNDIDAO implements I_C2cproMainDAO{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO C2CPRO_MAIN(PRO_ID,MEM_ID,PRO_NAME,CAT_ID,QUANTITY,PRICE,PHOTO,DESCRIPTION,STATUS,DELIVERY)"
			+ "VALUES('CP'|| LPAD(SEQ_CPRO_ID.NEXTVAL,4,'0'),?,?,?,?,?,?,?,1,?)";
	private static final String GET_ALL_STMT = "SELECT PRO_ID,MEM_ID,PRO_NAME,CAT_ID,QUANTITY,PRICE,PHOTO,DESCRIPTION,STATUS,DELIVERY,EST_TIME FROM C2CPRO_MAIN ORDER BY PRO_ID DESC";
	private static final String GET_ONE_STMT = "SELECT PRO_ID,MEM_ID,PRO_NAME,CAT_ID,QUANTITY,PRICE,PHOTO,DESCRIPTION,STATUS,DELIVERY,EST_TIME FROM C2CPRO_MAIN WHERE PRO_ID = ? ORDER BY PRO_ID DESC";
	private static final String DELETE = "DELETE FROM C2CPRO_MAIN WHERE PRO_ID = ?";
	private static final String UPDATE = "UPDATE C2CPRO_MAIN SET  MEM_ID=?,PRO_NAME=?,CAT_ID=?,QUANTITY=?,PRICE=?,PHOTO=?,DESCRIPTION=?,STATUS=?,DELIVERY=?,EST_TIME= CURRENT_TIMESTAMP WHERE PRO_ID = ?";
	private static final String GET_LIKE_STMT ="SELECT PRO_ID,MEM_ID,PRO_NAME,CAT_ID,QUANTITY,PRICE,PHOTO,DESCRIPTION,STATUS,DELIVERY,EST_TIME FROM C2CPRO_MAIN WHERE PRO_NAME LIKE ? ORDER BY PRO_ID DESC"; 
	private static final String GET_PRO_DETAIL = "SELECT A.PRO_ID, PRO_NAME ,B.SPECDET_ID, SPEC_DES,DETAIL_DES,C.SPEC_ID FROM B2CPRO_MAIN A JOIN PRO_SPEC B ON A.PRO_ID = B.PRO_ID JOIN SPEC_DETAIL C ON B.SPECDET_ID = C.SPECDET_ID JOIN SPEC_MAIN D ON D.SPEC_ID= C.SPEC_ID WHERE A.PRO_NAME = ?";
	private static final String UPDATE_STATUS = "UPDATE C2CPRO_MAIN SET  STATUS=? WHERE PRO_ID = ?";
	
	
	@Override
	public List<B2cjoinDetailVO> getDetail(String pro_name) {
		List<B2cjoinDetailVO> list = new ArrayList<B2cjoinDetailVO>();
		B2cjoinDetailVO b2cjoinDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PRO_DETAIL);

			pstmt.setString(1, pro_name);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				b2cjoinDetailVO = new B2cjoinDetailVO();
				b2cjoinDetailVO.setPro_id(rs.getString("PRO_ID"));				
				b2cjoinDetailVO.setPro_name(rs.getString("PRO_NAME"));
				b2cjoinDetailVO.setSpecdet_id(rs.getString("SPECDET_ID"));
				b2cjoinDetailVO.setSpec_des(rs.getString("SPEC_DES"));
				b2cjoinDetailVO.setDetail_des(rs.getString("DETAIL_DES"));
				b2cjoinDetailVO.setSpec_id(rs.getString("SPEC_ID"));
				list.add(b2cjoinDetailVO);
				
				
				

			}

			// Handle any driver errors
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
	
	@Override
	public void insert(C2cproMainVO c2cproMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, c2cproMainVO.getMem_id());
			pstmt.setString(2, c2cproMainVO.getPro_name());
			pstmt.setString(3, c2cproMainVO.getCat_id());
			pstmt.setInt(4, c2cproMainVO.getQuantity());
			pstmt.setDouble(5, c2cproMainVO.getPrice());
			pstmt.setBytes(6, c2cproMainVO.getPhoto());
			pstmt.setString(7, c2cproMainVO.getDescription());
			
			pstmt.setInt(8, c2cproMainVO.getDelivery());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void update(C2cproMainVO c2cproMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(10, c2cproMainVO.getPro_id());
			pstmt.setString(1, c2cproMainVO.getMem_id());
			pstmt.setString(2, c2cproMainVO.getPro_name());
			pstmt.setString(3, c2cproMainVO.getCat_id());
			pstmt.setInt(4, c2cproMainVO.getQuantity());
			pstmt.setDouble(5, c2cproMainVO.getPrice());
			pstmt.setBytes(6, c2cproMainVO.getPhoto());
			pstmt.setString(7, c2cproMainVO.getDescription());
			pstmt.setInt(8, c2cproMainVO.getStatus());
			pstmt.setInt(9, c2cproMainVO.getDelivery());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void update_status(C2cproMainVO c2cproMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);
			pstmt.setString(2, c2cproMainVO.getPro_id());
			
			pstmt.setInt(1, c2cproMainVO.getStatus());
			

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String pro_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, pro_id);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public C2cproMainVO findByPrimaryKey(String pro_id) {

		C2cproMainVO c2cproMainVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, pro_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				c2cproMainVO = new C2cproMainVO();
				c2cproMainVO.setPro_id(rs.getString("pro_id"));
				c2cproMainVO.setMem_id(rs.getString("mem_id"));
				c2cproMainVO.setPro_name(rs.getString("pro_name"));
				c2cproMainVO.setCat_id(rs.getString("cat_id"));
				c2cproMainVO.setQuantity(rs.getInt("quantity"));
				c2cproMainVO.setPrice(rs.getDouble("price"));
				c2cproMainVO.setPhoto(rs.getBytes("photo"));
				c2cproMainVO.setDescription(rs.getString("description"));
				c2cproMainVO.setStatus(rs.getInt("status"));
				c2cproMainVO.setDelivery(rs.getInt("delivery"));
				c2cproMainVO.setEst_time(rs.getTimestamp("est_time"));

			}

			// Handle any driver errors
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

		return c2cproMainVO;
	}

	@Override
	public List<C2cproMainVO> getAll() {
		List<C2cproMainVO> list = new ArrayList<C2cproMainVO>();
		C2cproMainVO c2cproMainVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();


			while (rs.next()) {
				c2cproMainVO = new C2cproMainVO();
				c2cproMainVO.setPro_id(rs.getString("pro_id"));
				c2cproMainVO.setMem_id(rs.getString("mem_id"));
				c2cproMainVO.setPro_name(rs.getString("pro_name"));
				c2cproMainVO.setCat_id(rs.getString("cat_id"));
				c2cproMainVO.setQuantity(rs.getInt("quantity"));
				c2cproMainVO.setPrice(rs.getDouble("price"));
				c2cproMainVO.setPhoto(rs.getBytes("photo"));
				c2cproMainVO.setDescription(rs.getString("description"));
				c2cproMainVO.setStatus(rs.getInt("status"));
				c2cproMainVO.setDelivery(rs.getInt("delivery"));
				c2cproMainVO.setEst_time(rs.getTimestamp("est_time"));
				list.add(c2cproMainVO);
			}

			// Handle any driver errors
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
	
	@Override
	public List<C2cproMainVO> getLike(String pro_name) {
		List<C2cproMainVO> list = new ArrayList<C2cproMainVO>();
		C2cproMainVO c2cproMainVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIKE_STMT);
			pstmt.setString(1,"%"+pro_name+"%");
			rs = pstmt.executeQuery();


			while (rs.next()) {
				c2cproMainVO = new C2cproMainVO();
				c2cproMainVO.setPro_id(rs.getString("pro_id"));
				c2cproMainVO.setMem_id(rs.getString("mem_id"));
				c2cproMainVO.setPro_name(rs.getString("pro_name"));
				c2cproMainVO.setCat_id(rs.getString("cat_id"));
				c2cproMainVO.setQuantity(rs.getInt("quantity"));
				c2cproMainVO.setPrice(rs.getDouble("price"));
				c2cproMainVO.setPhoto(rs.getBytes("photo"));
				c2cproMainVO.setDescription(rs.getString("description"));
				c2cproMainVO.setStatus(rs.getInt("status"));
				c2cproMainVO.setDelivery(rs.getInt("delivery"));
				c2cproMainVO.setEst_time(rs.getTimestamp("est_time"));
				list.add(c2cproMainVO);
			}

			// Handle any driver errors
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
	public String insertWithSpecs(C2cproMainVO c2cproMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String pro_id = null;
		try {

			con = ds.getConnection();
			String[] cols = {"PRO_ID"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);

			pstmt.setString(1, c2cproMainVO.getMem_id());
			pstmt.setString(2, c2cproMainVO.getPro_name());
			pstmt.setString(3, c2cproMainVO.getCat_id());
			pstmt.setInt(4, c2cproMainVO.getQuantity());
			pstmt.setDouble(5, c2cproMainVO.getPrice());
			pstmt.setBytes(6, c2cproMainVO.getPhoto());
			pstmt.setString(7, c2cproMainVO.getDescription());
			
			pstmt.setInt(8, c2cproMainVO.getDelivery());

			pstmt.executeUpdate();
			

			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				pro_id = rs.getString(1);
			};

			// Handle any driver errors
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
		return pro_id;

	}

}
