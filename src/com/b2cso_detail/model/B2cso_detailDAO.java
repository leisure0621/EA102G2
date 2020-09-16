package com.b2cso_detail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class B2cso_detailDAO implements I_B2cso_detailDAO{
	private static DataSource ds = null;
    static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
    
    private static final String GET_ALL_STMT = 
		"SELECT SO_ID, PRO_ID, PRICE, QUANTITY FROM B2CSO_DETAIL";
    private static final String GET_ONE_STMT = 
		"SELECT SO_ID, PRO_ID, PRICE, QUANTITY FROM B2CSO_DETAIL WHERE SO_ID = ?";
    private static final String UPDATE =
		"UPDATE B2CSO_DETAIL SET PRICE = ?, QUANTITY = ? WHERE SO_ID = ? AND PRO_ID = ?";
    private static final String INSERT_STMT = 
		"INSERT INTO B2CSO_DETAIL(PRO_ID, SO_ID, PRICE, QUANTITY) " + 
		"VALUES ( ?, ?, ?, ?)";
    
	@Override
	public void insert(B2cso_detailVO b2cso_detailVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1,b2cso_detailVO.getPro_id());
			pstmt.setString(2,b2cso_detailVO.getSo_id());
			pstmt.setInt(3,b2cso_detailVO.getPrice());
			pstmt.setInt(4,b2cso_detailVO.getQuantity());
			

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(B2cso_detailVO b2cso_detailVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1,b2cso_detailVO.getPrice());
			pstmt.setInt(2,b2cso_detailVO.getQuantity());
			pstmt.setString(3,b2cso_detailVO.getSo_id());
			pstmt.setString(4,b2cso_detailVO.getPro_id());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<B2cso_detailVO> findByPrimaryKey(String so_id) {
		// TODO Auto-generated method stub
		List<B2cso_detailVO> list = new ArrayList<B2cso_detailVO>();
		B2cso_detailVO b2cso_detailVO = null;
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {

	      con = ds.getConnection();
	      pstmt = con.prepareStatement(GET_ONE_STMT);

	      pstmt.setString(1, so_id);

	      rs = pstmt.executeQuery();

	      while (rs.next()) {
	    	b2cso_detailVO = new B2cso_detailVO();
	    	b2cso_detailVO.setSo_id(rs.getString("so_id"));
	    	b2cso_detailVO.setPro_id(rs.getString("pro_id"));
	    	b2cso_detailVO.setPrice(rs.getInt("price"));
	    	b2cso_detailVO.setQuantity(rs.getInt("quantity"));
	    	list.add(b2cso_detailVO);
	      }

	      // Handle any driver errors
	    } catch (SQLException se) {
	      throw new RuntimeException("A database error occured. "
	        + se.getMessage());
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
	public List<B2cso_detailVO> getAll() {
		// TODO Auto-generated method stub
		List<B2cso_detailVO> list = new ArrayList<B2cso_detailVO>();
		B2cso_detailVO b2cso_mainVO = new B2cso_detailVO(); 
		Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {

	      con = ds.getConnection();
	      pstmt = con.prepareStatement(GET_ALL_STMT);
	      rs = pstmt.executeQuery();

	      while (rs.next()) {
	    	b2cso_mainVO = new B2cso_detailVO();
	    	b2cso_mainVO.setSo_id(rs.getString("so_id"));
	    	b2cso_mainVO.setPro_id(rs.getString("pro_id"));
	    	b2cso_mainVO.setPrice(rs.getInt("price"));
	    	b2cso_mainVO.setQuantity(rs.getInt("quantity"));
	    	list.add(b2cso_mainVO);
	      }

	      // Handle any driver errors
	    } catch (SQLException se) {
	      throw new RuntimeException("A database error occured. "
	        + se.getMessage());
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

}
