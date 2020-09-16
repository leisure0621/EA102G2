package com.c2cpro_main.model;


import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import com.b2cpro_main.model.B2cproMainVO;



public class C2cproMainJDBCDAO implements I_C2cproMainDAO {
	
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G2";
	String passwd = "EA102G2";
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STATUS);
			pstmt.setString(2, c2cproMainVO.getPro_id());
			
			pstmt.setInt(1, c2cproMainVO.getStatus());
			

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, pro_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	

	public static void main(String[] args) {

		C2cproMainJDBCDAO dao = new C2cproMainJDBCDAO();
		//
//		C2cproMainVO main1 = new C2cproMainVO();
//		main1.setMem_id("MEM0001");
//		main1.setPro_name("K280");
//		main1.setCat_id("CAT0012");
//		main1.setQuantity(1);
//		main1.setPrice(new Double(200));
//		main1.setPhoto();
//		main1.setDescription();
		
//		main1.setDelivery(1);
//		dao.insert(main1);
//		
//
//		
//		//
//		C2cproMainVO main2 = new C2cproMainVO();
//		main2.setPro_id("CP0013");
//		main2.setMem_id("MEM0001");
//		main2.setPro_name("K288");
//		main2.setCat_id("CAT0012");
//		main2.setQuantity(1);
//		main2.setPrice(new Double(123));
////		main2.setPhoto();
////		main2.setDescription();
//		main2.setStatus(0);
//		main2.setDelivery(2);
//		
//		dao.update(main2);
//		System.out.println("UPDATE OK");
//
//		//��
//		dao.delete("CP0017");
//		System.out.println("OK");
//		
//
//		//�閰�
//
//		C2cproMainVO main3 = dao.findByPrimaryKey("CP0001");
//		System.out.print(main3.getPro_id() + ",");
//		System.out.print(main3.getMem_id() + ",");
//		System.out.print(main3.getPro_name() + ",");
//		System.out.print(main3.getCat_id() + ",");
//		System.out.print(main3.getQuantity() + ",");
//		System.out.print(main3.getPrice() + ",");
//		System.out.println(main3.getPhoto());
//		byte[] photo = main3.getPhoto();
//		try {
//			readPicture(photo);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		System.out.print(main3.getDescription()+ ",");
//		System.out.print(main3.getStatus() + ",");
//		System.out.print(main3.getDelivery() + ",");
//		java.util.Date dat3 = main3.getEst_time();
//		Format sfm3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//		System.out.println(sfm3.format(dat3));
//		System.out.println();

//		List<C2cproMainVO> list = dao.getAll();
//		for (C2cproMainVO main4 : list) {
//			System.out.print(main4.getPro_id() + ",");
//			System.out.print(main4.getMem_id() + ",");
//			System.out.print(main4.getPro_name() + ",");
//			System.out.print(main4.getCat_id() + ",");
//			System.out.print(main4.getQuantity() + ",");
//			System.out.print(main4.getPrice() + ",");
//			System.out.print(main4.getPhoto() + ",");
//			System.out.print(main4.getDescription() + ",");
//			System.out.print(main4.getStatus() + ",");
//			System.out.print(main4.getDelivery() + ",");
//
//			java.util.Date dat4 = main4.getEst_time();
//
//			Format sfm4 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//			System.out.println(sfm4.format(dat4));
//
//			System.out.println();
//		}
		
		
//		List<C2cproMainVO> list = dao.getLike("U");
//		for (C2cproMainVO main5 : list) {
//			System.out.print(main5.getPro_id() + ",");
//			System.out.print(main5.getMem_id() + ",");
//			System.out.print(main5.getPro_name() + ",");
//			System.out.print(main5.getCat_id() + ",");
//			System.out.print(main5.getQuantity() + ",");
//			System.out.print(main5.getPrice() + ",");
//			System.out.print(main5.getPhoto() + ",");
//			System.out.print(main5.getDescription() + ",");
//			System.out.print(main5.getStatus() + ",");
//			System.out.print(main5.getDelivery() + ",");
//
//			java.util.Date dat5 = main5.getEst_time();
//
//			Format sfm5 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//			System.out.println(sfm5.format(dat5));
//
//			System.out.println();
//			}
		
		
		List<B2cjoinDetailVO> list6 = dao.getDetail("Intel i5-9600K");
		for(B2cjoinDetailVO main6: list6) {
			System.out.print(main6.getPro_id() + ",");
			System.out.print(main6.getPro_name()+ ",");
			System.out.print(main6.getSpecdet_id()+ ",");
			System.out.print(main6.getSpec_des()+ ",");
			System.out.print(main6.getDetail_des()+ ",");
			System.out.print(main6.getSpec_id());
			System.out.println( );
			
		}
		

	}
	public static void readPicture(byte[] bytes) throws IOException {
		FileOutputStream fos = new FileOutputStream("build/Output/1.png");
		fos.write(bytes);
		fos.flush();
		fos.close();
	}
	
}
