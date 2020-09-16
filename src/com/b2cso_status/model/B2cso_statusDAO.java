package com.b2cso_status.model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.DataSource;

public class B2cso_statusDAO implements I_B2cso_statusDAO {
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
	    "SELECT STATUS_ID, STATUS_DES FROM B2CSO_STATUS";
	  private static final String GET_ONE_STMT =
	    "SELECT STATUS_ID, STATUS_DES FROM B2CSO_STATUS WHERE STATUS_ID = ?";

	  @Override
	  public B2cso_statusVO findByPrimaryKey(String status_id) {
	    // TODO Auto-generated method stub
	    B2cso_statusVO b2cso_statusVO = null;
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {

    	  con = ds.getConnection();
	      pstmt = con.prepareStatement(GET_ONE_STMT);

	      pstmt.setString(1, status_id);

	      rs = pstmt.executeQuery();

	      while (rs.next()) {
	        b2cso_statusVO = new B2cso_statusVO();

	        b2cso_statusVO.setStatus_id(rs.getString("status_id"));
	        b2cso_statusVO.setStatus_des(rs.getString("status_des"));
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
	    return b2cso_statusVO;
	  }
	  
	  @Override
	  public List<B2cso_statusVO> getAll() {
	    // TODO Auto-generated method stub
	    List < B2cso_statusVO > list = new ArrayList<B2cso_statusVO>();
	    B2cso_statusVO b2cso_statusVO = null;

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {

	      con = ds.getConnection();
	      pstmt = con.prepareStatement(GET_ALL_STMT);
	      rs = pstmt.executeQuery();

	      while (rs.next()) {
	        b2cso_statusVO = new B2cso_statusVO();

	        b2cso_statusVO.setStatus_id(rs.getString("status_id"));
	        b2cso_statusVO.setStatus_des(rs.getString("status_des"));

	        list.add(b2cso_statusVO);
	      }
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
	    return list;
	  }
}
