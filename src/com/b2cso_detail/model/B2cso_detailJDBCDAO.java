package com.b2cso_detail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.b2cso_main.model.B2cso_mainVO;

public class B2cso_detailJDBCDAO implements I_B2cso_detailDAO{
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String userid = "EA102G2";
    String passwd = "EA102G2";
    
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1,b2cso_detailVO.getPro_id());
			pstmt.setString(2,b2cso_detailVO.getSo_id());
			pstmt.setInt(3,b2cso_detailVO.getPrice());
			pstmt.setInt(4,b2cso_detailVO.getQuantity());
			

			pstmt.executeUpdate();

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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1,b2cso_detailVO.getPrice());
			pstmt.setInt(2,b2cso_detailVO.getQuantity());
			pstmt.setString(3,b2cso_detailVO.getSo_id());
			pstmt.setString(4,b2cso_detailVO.getPro_id());
			
			pstmt.executeUpdate();

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

	      Class.forName(driver);
	      con = DriverManager.getConnection(url, userid, passwd);
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

	      Class.forName(driver);
	      con = DriverManager.getConnection(url, userid, passwd);
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
	    return list;
	}
	
	public static void main (String[] args) {
		B2cso_detailJDBCDAO dao = new B2cso_detailJDBCDAO();
		
		B2cso_detailVO b2cso_detailVO1  = new B2cso_detailVO();
		b2cso_detailVO1.setPrice(2500);
		b2cso_detailVO1.setQuantity(1);
		b2cso_detailVO1.setSo_id("BSO0001");
		b2cso_detailVO1.setPro_id("BP0024");
		dao.update(b2cso_detailVO1);
	    System.out.println("修改成功");
	    System.out.println("---------------------------");
	    B2cso_detailVO b2cso_detailVO2  = new B2cso_detailVO();
		b2cso_detailVO2.setPrice(8000);
		b2cso_detailVO2.setQuantity(2);
		b2cso_detailVO2.setSo_id("BSO0001");
		b2cso_detailVO2.setPro_id("BP0027");
		dao.insert(b2cso_detailVO2);
	    System.out.println("新增成功");
	    System.out.println("---------------------------");
	    System.out.println("findByPrimaryKey: ");
	    List<B2cso_detailVO> list1 = dao.findByPrimaryKey("BSO0001");
	    for (B2cso_detailVO b2cso_detailVO : list1) {
			System.out.print(b2cso_detailVO.getSo_id()+"\t");
			System.out.print(b2cso_detailVO.getPro_id()+"\t");
			System.out.print(b2cso_detailVO.getPrice()+"\t");
			System.out.print(b2cso_detailVO.getQuantity()+"\t");
			System.out.println();
		}
	    System.out.println("---------------------------");
	    System.out.println("getAll: ");
		List<B2cso_detailVO> list = dao.getAll();
		for (B2cso_detailVO b2cso_detailVO : list) {
			System.out.print(b2cso_detailVO.getSo_id()+"\t");
			System.out.print(b2cso_detailVO.getPro_id()+"\t");
			System.out.print(b2cso_detailVO.getPrice()+"\t");
			System.out.print(b2cso_detailVO.getQuantity()+"\t");
			System.out.println();
		}
	}
}
