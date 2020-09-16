package com.text_main.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.c2cpro_main.model.C2cproMainService;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.msg.model.MsgVO;
import com.text_main.model.*;

public class TextMainServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display1".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("進入");
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("text_id");
				System.out.println("進入接收");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入文章編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/textmain/textmain_index.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String text_id = null;
				try {
					text_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("文章編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/textmain/textmain_index.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				TextMainService TextMainSvc = new TextMainService();
				TextMainVO TextMainVO = TextMainSvc.getOneText_main(text_id);
				System.out.println("TextMainVO.getText_id(): " + TextMainVO.getText_id());
				if (TextMainVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/textmain/textmain_index.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("textmainVO", TextMainVO); // 資料庫取出的empVO物件,存入req
				String url = "/front_end/msg/msg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要查詢的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/textmain/textmain_index.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String text_id = req.getParameter("text_id");
				System.out.println(text_id);
				/***************************2.開始查詢資料****************************************/
				TextMainService TextMainService = new TextMainService();
				TextMainVO TextMainVO = TextMainService.getOneText_main(text_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("textmainVO", TextMainVO);         // 資料庫取出的empVO物件,存入req
				String url = "/textmain/update_textmain_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/textmain/textmain_index.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				System.out.println("到SERVLET");
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String text_id = req.getParameter("text_id");
				System.out.println(text_id);
				String author_id = req.getParameter("author_id").trim();
				System.out.println(author_id);
				if (author_id == null || author_id.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}	
//				String author_id_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (author_id == null || author_id.trim().length() == 0) {
//					errorMsgs.add("�|���s��: �ФŪť�");
//			    } else if(!author_id.trim().matches(author_id_Reg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
//					errorMsgs.add("�|���s��: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
//	            }
				
				String title = req.getParameter("title").trim();
				System.out.println(title);
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("標題請勿空白");
				}	
				
				String content = req.getParameter("content").trim();
				System.out.println(content);
				if (content == null || content.trim().length() == 0) {
						errorMsgs.add("文章內容請勿空白");
				}
				
				java.sql.Timestamp est_time = null;
				try {
					est_time = new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println(est_time);
				} catch (IllegalArgumentException e) {
					est_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer status = null;
				try {
					status = new Integer(req.getParameter("status").trim());
					System.out.println(status);
				} catch (NumberFormatException e) {
					status = 0;
					errorMsgs.add("狀態碼請勿空白");
				}

				

				
//				String text_id = req.getParameter("text_id");
				TextMainVO textmainVO = new TextMainVO();
				textmainVO.setText_id(text_id);
				textmainVO.setAuthor_id(author_id);
				textmainVO.setTitle(title);
				textmainVO.setContent(content);
				textmainVO.setEst_time(est_time);
				textmainVO.setStatus(status);
				System.out.println("修改完成2");

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("textmainVO", textmainVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/textmain/update_textmain_input.jsp");
					failureView.forward(req, res);
					return; 
				}
				
				/***************************2.開始修改資料*****************************************/
				TextMainService TextMainSvc = new TextMainService();
				textmainVO = TextMainSvc.updateText_main(text_id,author_id, title, content, est_time,status);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("textmainVO", textmainVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/textmain/listOnetext.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				System.out.println("修改完1");
				successView.forward(req, res);
				System.out.println("修改完成");
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/textmain/update_textmain_input.jsp");
				failureView.forward(req, res);
			}
		}
//if ("update_status".equals(action)) { // 來自update_emp_input.jsp的請求
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//		
//			
//				System.out.println("到SERVLET");
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String text_id = req.getParameter("text_id");
//				System.out.println(text_id);
//				String author_id = req.getParameter("author_id");
////				
//				String title = req.getParameter("title");
//				System.out.println(title);
//				
//				
//				String content = req.getParameter("content");
//				System.out.println(content);
//			
//				
//				java.sql.Timestamp est_time = null;
//				
//				est_time = new java.sql.Timestamp(System.currentTimeMillis());
//				System.out.println(est_time);
//				
//
//				Integer status = null;
//				
//				status = new Integer(req.getParameter("status"));
//				System.out.println(status);
//				
//
//				
//
//				
////				String text_id = req.getParameter("text_id");
//				TextMainVO textmainVO = new TextMainVO();
//				textmainVO.setText_id(text_id);
//				textmainVO.setAuthor_id(author_id);
//				textmainVO.setTitle(title);
//				textmainVO.setContent(content);
//				textmainVO.setEst_time(est_time);
//				textmainVO.setStatus(status);
//				System.out.println("修改完成2");
//
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("textmainVO", textmainVO); // 含有輸入格式錯誤的empVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/textmain/update_textmain_input.jsp");
//					failureView.forward(req, res);
//					return; 
//				}
//				
//				/***************************2.開始修改資料*****************************************/
//				TextMainService TextMainSvc = new TextMainService();
//				textmainVO = TextMainSvc.updateText_main(text_id,author_id, title, content, est_time,status);
//				
//				/***************************3.修改完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("textmainVO", textmainVO); // 資料庫update成功後,正確的的empVO物件,存入req
//				String url = "/back_end/text_main/text_mainManagement.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
//				System.out.println("修改完1");
//				successView.forward(req, res);
//				System.out.println("修改完成");
//				/***************************其他可能的錯誤處理*************************************/
//			} 
		
			
		// 更新權限
if("update_textStatus".equals(action)) {
	res.setContentType("text/html; charset=UTF-8");
	System.out.println("進入權限修改");
	String text_id = req.getParameter("text_id");
	Integer status = new Integer(req.getParameter("status"));
	System.out.println("text_id "+text_id);
	System.out.println("status "+status);
	
	TextMainService textMainSvc = new TextMainService();

	textMainSvc.update_textStatus(text_id, status);
	
	PrintWriter out = res.getWriter();
	JSONObject jsonObject = new JSONObject();
	Map map = new HashMap();
	
	if(status==1) {
		map.put("succe", "上架");
		map.put("process", 2);
	}else if(status==0) {
		map.put("succe", "下架");
		map.put("process", 1);
	}
	
	jsonObject.put("data", map);
	jsonObject.put("status", 200);
	
	out.print(jsonObject);
}
//////////////////////////////////////////////////////////////////////////////////////
        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("進入新增");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				String text_id = req.getParameter("text_id");
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (text_id == null || text_id.trim().length() == 0) {
//					errorMsgs.add("�峹�s��: �ФŪť�");
//				} else if(!text_id.trim().matches(enameReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
//					errorMsgs.add("�峹�s��: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
//	            }
				
				String author_id = req.getParameter("author_id").trim();
				if (author_id == null || author_id.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
				
				
				String title = req.getParameter("title").trim();
				if (title == null || title.trim().length() == 0) {
						errorMsgs.add("標題請勿空白");
					}
				
				String content = req.getParameter("content").trim();
				if (content == null || content.trim().length() == 0) {
						errorMsgs.add("文章內容請勿空白");
				}
					
				Integer status = 1;
//				try {
//					status = new Integer(req.getParameter("status").trim());
//				} catch (NumberFormatException e) {
//					status = 0;
//					errorMsgs.add("狀態碼請勿空白");
//				}
				
 
				TextMainVO TextMainVO = new TextMainVO();
//				TextMainVO.setText_id(text_id);
				TextMainVO.setAuthor_id(author_id);
				TextMainVO.setTitle(title);
				TextMainVO.setContent(content);
				TextMainVO.setStatus(status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("textmainVO", TextMainVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/textmain/addNewText.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				TextMainService empSvc = new TextMainService();
				TextMainVO = empSvc.addText_main(author_id,title, content,status);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front_end/textmain/textmain_index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/textmain/addNewText.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String text_id = req.getParameter("text_id");
				
				/***************************2.開始刪除資料***************************************/
				TextMainService empSvc = new TextMainService();
				empSvc.deleteText_main(text_id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/textmain/listAlltext.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/textmain/listAlltext.jsp");
				failureView.forward(req, res);
			}
		}
	}
	
}
