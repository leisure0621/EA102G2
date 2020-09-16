package com.repair_status.model;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class RepairStatusJDBCDAO implements I_RepairStatusDAO {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	
	private static final String INSERT_STMT = 
			"INSERT INTO repair_status (status_id, status_des)"
			+ "VALUES ('RST' || LPAD(SEQ_REPAIRST_ID.NEXTVAL, 4, '0'), ?)";
	private static final String GET_ALL_STMT = 
			"SELECT status_id, status_des FROM repair_status order by status_id";
	private static final String GET_ONE_STMT =	
			"SELECT status_id, status_des FROM repair_status WHERE status_id = ?";
	private static final String DELETE = 
			"DELETE FROM repair_status where status_id = ?";
	private static final String UPDATE = 
			"UPDATE repair_status set status_des=?"
			+ "where status_id = ?";
	@Override
	public void insert(RepairStatusVO repairStatusVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, repairStatusVO.getStatus_des());
			
						
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. "
					+ e.getMessage());			
		} 
		catch (SQLException se) {
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}	
		}
		
	}
	@Override
	public void update(RepairStatusVO repairStatusVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
						
			pstmt.setString(1, repairStatusVO.getStatus_des());
			pstmt.setString(2, repairStatusVO.getStatus_id());
			
						
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. "
					+ e.getMessage());			
		} 
		catch (SQLException se) {
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
	public void delete(String status_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, status_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. "
					+ e.getMessage());			
		} 
		catch (SQLException se) {
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
	public RepairStatusVO findByPrimaryKey(String status_id) {
		
		RepairStatusVO repairStatusVO = null;
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
				// empVo 也稱為 Domain objects
				repairStatusVO = new RepairStatusVO();
				repairStatusVO.setStatus_id(rs.getString("status_id")); 
				repairStatusVO.setStatus_des(rs.getString("status_des"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		}
		catch (SQLException se) {
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
		return repairStatusVO;
	}
	@Override
	public List<RepairStatusVO> getAll() {
		List<RepairStatusVO> list = new ArrayList<RepairStatusVO>();
		RepairStatusVO repairStatusVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects 
				repairStatusVO = new RepairStatusVO();
				repairStatusVO.setStatus_id(rs.getString("status_id")); 
				repairStatusVO.setStatus_des(rs.getString("status_des"));
				list.add(repairStatusVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} 
		catch (SQLException se) {
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
	
	public static void main(String[] args) {
		RepairStatusJDBCDAO dao = new RepairStatusJDBCDAO();
		
		// 新增
		RepairStatusVO rsVO1 = new RepairStatusVO();
		rsVO1.setStatus_des("維修中MotherFucker");
		dao.insert(rsVO1);
		System.out.println("Insert Success");
				
		//修改
//		RepairStatusVO rsVO2 = new RepairStatusVO();
//		rsVO2.setStatus_des("快修好了啦幹");
//		rsVO2.setStatus_id("RST0021");
//		dao.update(rsVO2);
//		System.out.println("Update Success");
		
		// 刪除
//		dao.delete("RST0021");
//		System.out.println("Delete Success");
		
		// 單個查詢
//		RepairStatusVO rsVO3 = dao.findByPrimaryKey("RST0001");
//		System.out.print(rsVO3.getStatus_id() + ", ");
//		System.out.println(rsVO3.getStatus_des());
//		System.out.println("----------");
		
		// 全部查詢
//		List<RepairStatusVO> list = dao.getAll();
//		for(RepairStatusVO rs : list) {
//			System.out.print(rs.getStatus_id() + ", ");
//			System.out.println(rs.getStatus_des());
//			System.out.println("----------");
//		}
		
	}
	
}
