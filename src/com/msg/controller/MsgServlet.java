package com.msg.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.msg.model.MsgService;
import com.msg.model.MsgVO;
import com.text_main.model.*;

public class MsgServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("text_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入留言編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/msg/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String text_id = null;
				try {
					text_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("留言編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/msg/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MsgService MsgSvc = new MsgService();
				MsgVO MsgVO = MsgSvc.getOneMsg1(text_id);
				if (MsgVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/msg/select_page.jsp");
					failureView.forward(req, res);
					return;//
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("msgVO", MsgVO); // 資料庫取出的empVO物件,存入req
				String url = "/front_end/msg/msg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ����漱 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要查詢的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/msg/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { //來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String msg_id = req.getParameter("msg_id");
				System.out.println(msg_id);
				/***************************2.開始查詢資料****************************************/
				MsgService MsgService = new MsgService();
				MsgVO MsgVO = MsgService.getOneMsg(msg_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("msgVO", MsgVO);         // 資料庫取出的empVO物件,存入req
				String url = "/msg/update_msg_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/msg/listAllmsg.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				System.out.println("�SERVLET");
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String msg_id = req.getParameter("msg_id");
				System.out.println(msg_id);
				String text_id = req.getParameter("text_id").trim();
				System.out.println(text_id);
				if (text_id == null || text_id.trim().length() == 0) {
					errorMsgs.add("文章編號請勿空白");
				}	
//				String author_id_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (author_id == null || author_id.trim().length() == 0) {
//					errorMsgs.add("嚙罵嚙踝蕭嚙編嚙踝蕭: 嚙請勿空伐蕭");
//			    } else if(!author_id.trim().matches(author_id_Reg)) { //嚙瘡嚙磊嚙練嚙賠伐蕭嚙篁(嚙磕)嚙踝蕭雃嚙�(regular-expression)
//					errorMsgs.add("嚙罵嚙踝蕭嚙編嚙踝蕭: 嚙線嚙踝蕭O嚙踝蕭嚙畿嚙稷嚙踝蕭r嚙踝蕭嚙畿嚙複字嚙瞎_ , 嚙畿嚙踝蕭嚙論伐蕭嚙豎在2嚙踝蕭10嚙踝蕭嚙踝蕭");
//	            }
				
				String author_id = req.getParameter("author_id").trim();
				System.out.println(author_id);
				if (author_id == null || author_id.trim().length() == 0) {
					errorMsgs.add("會員請勿空白");
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
				MsgVO MsgVO = new MsgVO();
				MsgVO.setText_id(text_id);
				MsgVO.setAuthor_id(author_id);
				MsgVO.setContent(content);
				MsgVO.setEst_time(est_time);
				MsgVO.setStatus(status);
				System.out.println("修改完成2");

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("msgVO", MsgVO); //含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/msg/update_msg_input.jsp");
					failureView.forward(req, res);
					return; 
				}
				
				/***************************2.開始修改資料*****************************************/
				MsgService MsgSvc = new MsgService();
				MsgVO = MsgSvc.updateMsg(msg_id,text_id,author_id, content, est_time,status);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("msgVO", MsgVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/msg/listOnemsg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				System.out.println("修改完1");
				successView.forward(req, res);
				System.out.println("修改完成");
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/msg/update_msg_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				String text_id = req.getParameter("text_id");
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (text_id == null || text_id.trim().length() == 0) {
//					errorMsgs.add("嚙賢章嚙編嚙踝蕭: 嚙請勿空伐蕭");
//				} else if(!text_id.trim().matches(enameReg)) { //嚙瘡嚙磊嚙練嚙賠伐蕭嚙篁(嚙磕)嚙踝蕭雃嚙�(regular-expression)
//					errorMsgs.add("嚙賢章嚙編嚙踝蕭: 嚙線嚙踝蕭O嚙踝蕭嚙畿嚙稷嚙踝蕭r嚙踝蕭嚙畿嚙複字嚙瞎_ , 嚙畿嚙踝蕭嚙論伐蕭嚙豎在2嚙踝蕭10嚙踝蕭嚙踝蕭");
//	            }
				String text_id = req.getParameter("text_id").trim();
				System.out.println("text_id: " + text_id);
//				if (text_id == null || text_id.trim().length() == 0) {
//						errorMsgs.add("文章編號請勿空白");
//				}
//				
				String author_id = req.getParameter("author_id").trim();
				if (author_id == null || author_id.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
				
				
				String content = req.getParameter("content").trim();
				if (content == null) {
						errorMsgs.add("留言內容請勿空白");
				}
					
				Integer status = 1;
//				try {
//					status = new Integer(req.getParameter("status").trim());
//				} catch (NumberFormatException e) {
//					status = 0;
//					errorMsgs.add("狀態碼請勿空白");
//				}
				
 
				MsgVO MsgVO = new MsgVO();
//				MsgVO.setMsg_id(msg_id);
				MsgVO.setAuthor_id(author_id);
				MsgVO.setText_id(text_id);
				MsgVO.setContent(content);
				MsgVO.setStatus(status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("MsgVO", MsgVO); // 含有輸入格式錯誤的empVO物件,也存入req
					System.out.println("錯誤1");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/msg/msg.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				MsgService msgSvc = new MsgService();
				MsgVO = msgSvc.addMsg(text_id,author_id,content);
				req.setAttribute("MsgVO", MsgVO);
				TextMainService TextMainSvc = new TextMainService();
				TextMainVO TextMainVO = TextMainSvc.getOneText_main(text_id);
				req.setAttribute("textmainVO", TextMainVO);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front_end/msg/msg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 嚙編嚙磕嚙踝蕭嚙穀嚙踝蕭嚙踝蕭嚙締istAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				System.out.println("錯誤2");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/msg/msg.jsp");
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
				String msg_id = req.getParameter("msg_id");
				
				/***************************2.開始刪除資料***************************************/
				MsgService msgSvc = new MsgService();
				msgSvc.deleteMsg(msg_id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/msg/listAllmsg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 嚙磋嚙踝蕭嚙踝蕭嚙穀嚙踝蕭,嚙踝蕭嚙稷嚙箴嚙碼嚙磋嚙踝蕭嚙踝蕭嚙諉瘀蕭嚙踝蕭嚙踝蕭
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/msg/listAllmsg.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
