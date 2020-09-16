package com.repair_main.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RepairMainJDBCDAO implements I_RepairMainDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
	
	
	private static final String INSERT_STMT = 
			"INSERT INTO repair_main (repair_id, mem_id, cat_id, pro_name, description, status_id, amount, dev_address, recipient, pay_via, delivery)"
			+ "VALUES ('RO'||LPAD(SEQ_REPAIR_ID.NEXTVAL, 4, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT repair_id, mem_id, cat_id, pro_name, description, status_id, est_time, amount, dev_address, recipient, pay_via, delivery FROM repair_main order by repair_id";
	private static final String GET_ONE_STMT =	
			"SELECT repair_id, mem_id, cat_id, pro_name, description, status_id, est_time, amount, dev_address, recipient, pay_via, delivery FROM repair_main WHERE repair_id = ?";
	private static final String DELETE = 
			"DELETE FROM repair_main where repair_id = ?";
	private static final String UPDATE = 
			"UPDATE repair_main set mem_id=?, cat_id=?, pro_name=?, description=?, status_id=?, amount=?, dev_address=?, recipient=?, pay_via=?, delivery=?"
			+ "where repair_id = ?";
	
	private static final String GET_BY_MEM = "SELECT repair_id, mem_id, cat_id, pro_name, description, status_id, est_time, amount, dev_address, recipient, pay_via, delivery FROM repair_main WHERE mem_id = ?";
	
	@Override
	public RepairMainVO insert(RepairMainVO repairMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, repairMainVO.getMem_id());
			pstmt.setString(2, repairMainVO.getCat_id());
			pstmt.setString(3, repairMainVO.getPro_name());
			pstmt.setString(4, repairMainVO.getDescription());
			pstmt.setString(5, repairMainVO.getStatus_id());
			pstmt.setDouble(6, repairMainVO.getAmount());
			pstmt.setString(7, repairMainVO.getDev_address());
			pstmt.setString(8, repairMainVO.getRecipient());
			pstmt.setDouble(9, repairMainVO.getPay_via());
			pstmt.setDouble(10, repairMainVO.getDelivery());
			
			pstmt.executeUpdate();
			
			RepairMainService rmSvc = new RepairMainService();
			List<RepairMainVO> list = new ArrayList<RepairMainVO>();
			list = rmSvc.findByMem_id(repairMainVO.getMem_id());
			for(RepairMainVO rmVO:list) {
				repairMainVO.setRepair_id(rmVO.getRepair_id());
				repairMainVO.setEst_time(rmVO.getEst_time());
			}
			return repairMainVO;
			
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
	public void update(RepairMainVO repairMainVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, repairMainVO.getMem_id());
			pstmt.setString(2, repairMainVO.getCat_id());
			pstmt.setString(3, repairMainVO.getPro_name());
			pstmt.setString(4, repairMainVO.getDescription());
			pstmt.setString(5, repairMainVO.getStatus_id());
			pstmt.setDouble(6, repairMainVO.getAmount());
			pstmt.setString(7, repairMainVO.getDev_address());
			pstmt.setString(8, repairMainVO.getRecipient());
			pstmt.setDouble(9, repairMainVO.getPay_via());
			pstmt.setDouble(10, repairMainVO.getDelivery());
			pstmt.setString(11, repairMainVO.getRepair_id());
			
			pstmt.executeUpdate();
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
	public void delete(String repair_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, repair_id);

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
	public RepairMainVO findByPrimaryKey(String repair_id) {
		
		RepairMainVO repairMainVO = null; 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, repair_id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				repairMainVO = new RepairMainVO();
				repairMainVO.setRepair_id(rs.getString("repair_id")); 
				repairMainVO.setMem_id(rs.getString("mem_id"));
				repairMainVO.setCat_id(rs.getString("cat_id"));
				repairMainVO.setPro_name(rs.getString("pro_name"));
				repairMainVO.setDescription(rs.getString("description"));
				repairMainVO.setStatus_id(rs.getString("status_id"));
				repairMainVO.setEst_time(rs.getTimestamp("est_time"));
				repairMainVO.setAmount(rs.getDouble("amount"));
				repairMainVO.setDev_address(rs.getString("dev_address"));
				repairMainVO.setRecipient(rs.getString("recipient"));
				repairMainVO.setPay_via(rs.getDouble("pay_via"));
				repairMainVO.setDelivery(rs.getDouble("delivery"));
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
		return repairMainVO;
	}
	@Override
	public List<RepairMainVO> getAll() {
		List<RepairMainVO> list = new ArrayList<RepairMainVO>();
		RepairMainVO repairMainVO = null;
		
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
				repairMainVO = new RepairMainVO();
				repairMainVO.setRepair_id(rs.getString("repair_id")); 
				repairMainVO.setMem_id(rs.getString("mem_id"));
				repairMainVO.setCat_id(rs.getString("cat_id"));
				repairMainVO.setPro_name(rs.getString("pro_name"));
				repairMainVO.setDescription(rs.getString("description"));
				repairMainVO.setStatus_id(rs.getString("status_id"));
				repairMainVO.setEst_time(rs.getTimestamp("est_time"));
				repairMainVO.setAmount(rs.getDouble("amount"));
				repairMainVO.setDev_address(rs.getString("dev_address"));
				repairMainVO.setRecipient(rs.getString("recipient"));
				repairMainVO.setPay_via(rs.getDouble("pay_via"));
				repairMainVO.setDelivery(rs.getDouble("delivery"));
				list.add(repairMainVO); // Store the row in the list
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
	
	@Override
	public List<RepairMainVO> findByMem_id(String mem_id) {
		
		List<RepairMainVO> list = new ArrayList<RepairMainVO>();
		RepairMainVO rmVO = null;
		
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
				rmVO = new RepairMainVO();
				rmVO.setRepair_id(rs.getString("repair_id"));
				rmVO.setMem_id(rs.getString("mem_id"));
				rmVO.setCat_id(rs.getString("cat_id"));
				rmVO.setPro_name(rs.getString("pro_name"));
				rmVO.setDescription(rs.getString("description"));
				rmVO.setStatus_id(rs.getString("status_id"));
				rmVO.setEst_time(rs.getTimestamp("est_time"));
				rmVO.setAmount(rs.getDouble("amount"));
				rmVO.setDev_address(rs.getString("dev_address"));
				rmVO.setRecipient(rs.getString("recipient"));
				rmVO.setPay_via(rs.getDouble("pay_via"));
				rmVO.setDelivery(rs.getDouble("delivery"));
				list.add(rmVO);
			}
			
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch(SQLException se) {
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
	
	public static void main(String[] args) {
		
		RepairMainJDBCDAO dao = new RepairMainJDBCDAO();
		
		// 新增
		RepairMainVO rmVO1 = new RepairMainVO();
		rmVO1.setMem_id("MEM0001");
		rmVO1.setCat_id("CAT0003");
		rmVO1.setPro_name("媽的板");
		rmVO1.setDescription("電容怎麼破掉了RRRRR");
		rmVO1.setStatus_id("RST0010");
		rmVO1.setAmount(2600.0);
		rmVO1.setDev_address("桃園市桃園區八張二路二段433巷250弄780號28樓");
		rmVO1.setRecipient("公孫婷馨");
		rmVO1.setPay_via(1.0);
		rmVO1.setDelivery(2.0);
		dao.insert(rmVO1);
		System.out.println("Isert Success");
		
		// 修改
//		RepairMainVO rmVO2 = new RepairMainVO();
//		rmVO2.setMem_id("MEM0002");
//		rmVO2.setCat_id("CAT0004");
//		rmVO2.setPro_name("MD");
//		rmVO2.setDescription("CPU與Socket接觸面生鏽");
//		rmVO2.setStatus_id("RST0011");
//		rmVO2.setAmount(3500);
//		rmVO2.setDev_address("新竹市香山區富農一街800巷26弄858號");
//		rmVO2.setRecipient("霍建");
//		rmVO2.setPay_via(2);
//		rmVO2.setDelivery(3);
//		rmVO2.setRepair_id("RO0023");
//		dao.update(rmVO2);
//		System.out.println("Update Success");
		
		// 刪除
//		dao.delete("RO0023");
//		System.out.println("Delete Success");
		
		// 單個查詢
//		RepairMainVO rmVO3 = dao.findByPrimaryKey("RO0001");
//		System.out.print(rmVO3.getRepair_id() + " ,");
//		System.out.print(rmVO3.getMem_id() + " ,");
//		System.out.print(rmVO3.getCat_id() + " ,");
//		System.out.print(rmVO3.getPro_name() + " ,");
//		System.out.print(rmVO3.getDescription() + " ,");
//		System.out.print(rmVO3.getStatus_id() + " ,");
//		System.out.print(rmVO3.getEst_time() + " ,");
//		System.out.print(rmVO3.getAmount() + " ,");
//		System.out.print(rmVO3.getDev_address() + " ,");
//		System.out.print(rmVO3.getRecipient() + " ,");
//		System.out.print(rmVO3.getPay_via() + " ,");
//		System.out.print(rmVO3.getDelivery() + " ,");
//		System.out.println();
//		
//		// 全部查詢
//		List<RepairMainVO> list = dao.getAll(); 
//		for(RepairMainVO rm : list) {
//			System.out.print(rm.getRepair_id() + " ,");
//			System.out.print(rm.getMem_id() + " ,");
//			System.out.print(rm.getCat_id() + " ,");
//			System.out.print(rm.getPro_name() + " ,");
//			System.out.print(rm.getDescription() + " ,");
//			System.out.print(rm.getStatus_id() + " ,");
//			System.out.print(rm.getEst_time() + " ,");
//			System.out.print(rm.getAmount() + " ,");
//			System.out.print(rm.getDev_address() + " ,");
//			System.out.print(rm.getRecipient() + " ,");
//			System.out.print(rm.getPay_via() + " ,");
//			System.out.print(rm.getDelivery() + " ,");
//			System.out.println();
//		}
		
	}
	
}
