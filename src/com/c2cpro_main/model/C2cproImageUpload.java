package com.c2cpro_main.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class C2cproImageUpload {
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String user = "EA102G2";
	private static final String password = "EA102G2";
	private static final String UPLOAD = "UPDATE C2CPRO_MAIN SET PHOTO = ? WHERE PRO_ID = ?";

//	public static void main(String[] args) throws IOException{
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		C2cproMainJDBCDAO dao = new C2cproMainJDBCDAO();
//		List<C2cproMainVO>list = new ArrayList<C2cproMainVO>();
//		list = dao.getAll();
//		int count = 1;
//		System.out.println("資料庫共"+list.size() +"筆");
//		while(count<= list.size()) {
//			byte[] img;
//			if(count<10) {
//				img = getPictureByteArray("WebContent/front_end/c2cproMain/proImg/CP000" + count + ".jpg");
//				try {
//					Class.forName(driver);
//					con = DriverManager.getConnection(url, user, password);
//					pstmt = con.prepareStatement(UPLOAD);
//					pstmt.setBytes(1,img);
//					pstmt.setString(2, "CP000"+count);
//					pstmt.executeUpdate();
//				}catch(ClassNotFoundException e) {
//					e.printStackTrace();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} finally {
//					if (pstmt != null) {
//						try {
//							pstmt.close();
//						} catch (SQLException se) {
//							System.out.println(se);
//						}
//					}
//					if (con != null) {
//						try {
//							con.close();
//						} catch (SQLException se) {
//							System.out.println(se);
//						}
//					}
//				}
//					
//				
//			}else {
//				img = getPictureByteArray("WebContent/front_end/c2cproMain/proImg/CP00" + count + ".jpg");
//				try {
//					Class.forName(driver);
//					con = DriverManager.getConnection(url, user, password);
//					pstmt = con.prepareStatement(UPLOAD);
//					pstmt.setBytes(1,img);
//					pstmt.setString(2, "CP00"+count);
//					pstmt.executeUpdate();
//				}catch(ClassNotFoundException e) {
//					e.printStackTrace();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} finally {
//					if (pstmt != null) {
//						try {
//							pstmt.close();
//						} catch (SQLException se) {
//							System.out.println(se);
//						}
//					}
//					if (con != null) {
//						try {
//							con.close();
//						} catch (SQLException se) {
//							System.out.println(se);
//						}
//					}
//				}
//				
//			}
//			System.out.println("第"+count + "張 上傳成功 ");
//			count++;
//		}
//		System.out.println("圖片新增共"+(count-1) +"筆");
//	}
	 public static byte[] getPictureByteArray(String path) throws IOException{
		 File file = new File(path);
		 FileInputStream fis = new FileInputStream(file);
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 byte[] buffer = new byte[1024];
		 int i;
		 while((i= fis.read(buffer))!= -1) {
			 baos.write(buffer,0,i);
				 
			 
		 }
		 baos.close();
		 fis.close();
		 return baos.toByteArray();
	 }
}
