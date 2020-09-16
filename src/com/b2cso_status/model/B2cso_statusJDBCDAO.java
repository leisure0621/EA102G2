package com.b2cso_status.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class B2cso_statusJDBCDAO implements I_B2cso_statusDAO {
  String driver = "oracle.jdbc.driver.OracleDriver";
  String url = "jdbc:oracle:thin:@localhost:1521:XE";
  String userid = "EA102G2";
  String passwd = "EA102G2";

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

      Class.forName(driver);
      con = DriverManager.getConnection(url, userid, passwd);
      pstmt = con.prepareStatement(GET_ONE_STMT);

      pstmt.setString(1, status_id);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        b2cso_statusVO = new B2cso_statusVO();

        b2cso_statusVO.setStatus_id(rs.getString("status_id"));
        b2cso_statusVO.setStatus_des(rs.getString("status_des"));
      }

      // Handle any driver errors
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Couldn't load database driver. "
        + e.getMessage());
      // Handle any SQL errors
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

      Class.forName(driver);
      con = DriverManager.getConnection(url, userid, passwd);
      pstmt = con.prepareStatement(GET_ALL_STMT);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        b2cso_statusVO = new B2cso_statusVO();

        b2cso_statusVO.setStatus_id(rs.getString("status_id"));
        b2cso_statusVO.setStatus_des(rs.getString("status_des"));

        list.add(b2cso_statusVO);
      }
      // Handle any driver errors
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Couldn't load database driver. "
        + e.getMessage());
      // Handle any SQL errors
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
  
  public static void main (String[] args) {
	  B2cso_statusJDBCDAO dao = new B2cso_statusJDBCDAO();
	  
	  List<B2cso_statusVO> list = dao.getAll();
	  for (B2cso_statusVO b2cso_statusVO : list) {
		System.out.print(b2cso_statusVO.getStatus_des()+"\t\t");
		System.out.print(b2cso_statusVO.getStatus_id()+"\t");
		System.out.println();
	  }
	  System.out.println("---------------------------");
	  B2cso_statusVO list1 = dao.findByPrimaryKey("BST0001");
	  System.out.print(list1.getStatus_des()+"\t\t");
	  System.out.print(list1.getStatus_id()+"\t");
  }
}
