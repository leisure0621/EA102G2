package com.b2cpro_main.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class B2cproImageUpload {
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String userid = "EA102G2";
	private static final String pswd = "EA102G2";
	private static final String UPLOAD = "UPDATE B2CPRO_MAIN SET PICTURE = ?, PRO_DES = ? WHERE PRO_ID = ?";

//	public static void main(String[] args) throws IOException {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		B2cproMainJDBCDAO dao = new B2cproMainJDBCDAO();
//		int size = dao.getAll().size();
//
//		int count = 1;
//		while (count <= size) {
//			byte[] img;
//			String str;
//			if (count < 10) {
//				img = getPictureByteArray("WebContent/back_end/b2cproduct/images/BP000" + count + ".png");
//				str = getLongString("WebContent/back_end/b2cproduct/text/BP000" + count + ".txt");
//				try {
//					Class.forName(driver);
//					con = DriverManager.getConnection(url, userid, pswd);
//					Clob clob = con.createClob();
//					pstmt = con.prepareStatement(UPLOAD);
//					clob.setString(1, str);
//
//					pstmt.setBytes(1, img);
//					pstmt.setClob(2, clob);
//					pstmt.setString(3, "BP000" + count);
//					pstmt.executeUpdate();
//
//				} catch (ClassNotFoundException e) {
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
//			} else {
//				img = getPictureByteArray("WebContent/back_end/b2cproduct/images/BP00" + count + ".png");
//				str = getLongString("WebContent/back_end/b2cproduct/text/BP00" + count + ".txt");
//				try {
//					Class.forName(driver);
//					con = DriverManager.getConnection(url, userid, pswd);
//					Clob clob = con.createClob();
//					pstmt = con.prepareStatement(UPLOAD);
//					clob.setString(1, str);
//
//					pstmt.setBytes(1, img);
//					pstmt.setClob(2, clob);
//					pstmt.setString(3, "BP00" + count);
//					pstmt.executeUpdate();
//
//				} catch (ClassNotFoundException e) {
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
//
//					if (con != null) {
//						try {
//							con.close();
//						} catch (SQLException se) {
//							System.out.println(se);
//						}
//					}
//				}
//			}
//			System.out.println(" BP00" + count + " pic upload complete!");
//			count++;
//		}
//
//	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();
		return baos.toByteArray();
	}

	public static String getLongString(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder(); // StringBuffer is thread-safe!
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();
		return sb.toString();
	}
}