package com.b2cso_main.model;

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

public class B2cso_mainDAO implements I_B2cso_mainDAO{
	private static DataSource ds = null;
    static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
    
    private static final String INSERT_STMT = 
		"INSERT INTO B2CSO_MAIN(SO_ID, TYPE, BUYER_ID, STATUS, DELIVERY, EST_TIME, AMOUNT, DEL_ADDRESS, RECIPIENT, PAY_VIA) " + 
		"VALUES ('BSO' || LPAD(SEQ_MEM_ID.NEXTVAL, 4, '0'), ?, ?, ?, ?, TO_DATE(TO_CHAR(SYSDATE,'YYYYMM'),'YYYY-MM'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT SO_ID, TYPE, BUYER_ID, STATUS, DELIVERY, EST_TIME, AMOUNT, DEL_ADDRESS, RECIPIENT, PAY_VIA FROM B2CSO_MAIN";
	private static final String GET_ONE_STMT = 
		"SELECT SO_ID, TYPE, BUYER_ID, STATUS, DELIVERY, EST_TIME, AMOUNT, DEL_ADDRESS, RECIPIENT, PAY_VIA FROM B2CSO_MAIN WHERE SO_ID = ?";
	private static final String UPDATE =
		"UPDATE B2CSO_MAIN SET STATUS = ?, AMOUNT = ?, DEL_ADDRESS = ?, PAY_VIA = ? WHERE SO_ID = ?";
	private static final String GET_LASTEST_STMT = 
		"SELECT SO_ID FROM (SELECT SO_ID FROM B2CSO_MAIN ORDER BY B2CSO_MAIN.SO_ID DESC) WHERE ROWNUM <= 1";
	
	@Override
	public void insert(B2cso_mainVO b2cso_mainVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, b2cso_mainVO.getType());
			pstmt.setString(2, b2cso_mainVO.getBuyer_id());
			pstmt.setString(3, b2cso_mainVO.getStatus());
			pstmt.setInt(4, b2cso_mainVO.getDelivery());
			pstmt.setInt(5, b2cso_mainVO.getAmount());
			pstmt.setString(6, b2cso_mainVO.getDel_address());
			pstmt.setString(7, b2cso_mainVO.getRecipient());
			pstmt.setInt(8, b2cso_mainVO.getPay_via());

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
	public void update(B2cso_mainVO b2cso_mainVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1,b2cso_mainVO.getStatus());
			pstmt.setInt(2,b2cso_mainVO.getAmount());
			pstmt.setString(3,b2cso_mainVO.getDel_address());
			pstmt.setInt(4,b2cso_mainVO.getPay_via());
			pstmt.setString(5,b2cso_mainVO.getSo_id());
			
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
	public B2cso_mainVO findByPrimaryKey(String so_id) {
		// TODO Auto-generated method stub
		B2cso_mainVO b2cso_mainVO = null;
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {

	    	con = ds.getConnection();
	      pstmt = con.prepareStatement(GET_ONE_STMT);

	      pstmt.setString(1, so_id);

	      rs = pstmt.executeQuery();

	      while (rs.next()) {
	    	b2cso_mainVO = new B2cso_mainVO();
	    	b2cso_mainVO.setSo_id(rs.getString("so_id"));
	    	b2cso_mainVO.setType(rs.getInt("type"));
	    	b2cso_mainVO.setBuyer_id(rs.getString("buyer_id"));
	    	b2cso_mainVO.setStatus(rs.getString("status"));
	    	b2cso_mainVO.setDelivery(rs.getInt("delivery"));
	    	b2cso_mainVO.setEst_time(rs.getTimestamp("est_time"));
	    	b2cso_mainVO.setAmount(rs.getInt("amount"));
	    	b2cso_mainVO.setDel_address(rs.getString("del_address"));
	    	b2cso_mainVO.setRecipient(rs.getString("recipient"));
	    	b2cso_mainVO.setPay_via(rs.getInt("pay_via"));
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
	    return b2cso_mainVO;
	}
	@Override
	public String getLatestB2cso_main() {
		String so_id = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LASTEST_STMT);
			rs = pstmt.executeQuery();

			rs.next();
			so_id = rs.getString("SO_ID");

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return so_id;
	}
	@Override
	public List<B2cso_mainVO> getAll() {
		// TODO Auto-generated method stub
		List<B2cso_mainVO> list = new ArrayList<B2cso_mainVO>();
  	  	B2cso_mainVO b2cso_mainVO = new B2cso_mainVO(); 
		Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {

    	  con = ds.getConnection();
	      pstmt = con.prepareStatement(GET_ALL_STMT);
	      rs = pstmt.executeQuery();

	      while (rs.next()) {
	    	b2cso_mainVO = new B2cso_mainVO();
	    	b2cso_mainVO.setSo_id(rs.getString("so_id"));
	    	b2cso_mainVO.setType(rs.getInt("type"));
	    	b2cso_mainVO.setBuyer_id(rs.getString("buyer_id"));
	    	b2cso_mainVO.setStatus(rs.getString("status"));
	    	b2cso_mainVO.setDelivery(rs.getInt("delivery"));
	    	b2cso_mainVO.setEst_time(rs.getTimestamp("est_time"));
	    	b2cso_mainVO.setAmount(rs.getInt("amount"));
	    	b2cso_mainVO.setDel_address(rs.getString("del_address"));
	    	b2cso_mainVO.setRecipient(rs.getString("recipient"));
	    	b2cso_mainVO.setPay_via(rs.getInt("pay_via"));
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
