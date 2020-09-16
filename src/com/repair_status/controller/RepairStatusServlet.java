package com.repair_status.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.repair_status.model.*;

public class RepairStatusServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("status_id");
				if(str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入維修狀態編號");
				}
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/rsSelect_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String status_id = null;
				try {
					status_id = new String(str);
				} catch(Exception e) {
					errorMsgs.add("維修狀態編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/rsSelect_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				RepairStatusService rsSvc = new RepairStatusService();
				RepairStatusVO rsVO = rsSvc.getOneRepairStatus(status_id);
				if(rsVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/rsSelect_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("rsVO", rsVO); // 資料庫取出的empVO物件,存入req
				String url = "/front_end/listOneRepairStatus.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);  // 成功轉交 listOneRepairStatus.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/rsSelect_page.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		
		if("getOne_For_Update".equals(action)) { // 來自listAllRepairStatus.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String status_id = new String(req.getParameter("status_id"));
				
				/***************************2.開始查詢資料****************************************/
				RepairStatusService rsSvc = new RepairStatusService();
				RepairStatusVO rsVO = rsSvc.getOneRepairStatus(status_id);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("rsVO", rsVO);           // 資料庫取出的rsVO物件，存入req
				String url = "/back_end/update_repairStatus_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_repairStatus_input.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/listAllRepairStatus.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		
		if("update".contentEquals(action)) { // 來自update_reapirStatus_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String status_id = new String(req.getParameter("status_id").trim());
				
				String status_des = req.getParameter("status_des");
				String status_desReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)(，,? )]{2,100}$";
				if(status_id == null || status_id.trim().length() == 0) {
					errorMsgs.add("狀態說明: 請勿空白");
				} else if(!status_des.trim().matches(status_desReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("狀態描述: 只能是中、英文字母、數字和_ , 且長度必需在2到100之間");
				}
				
				RepairStatusVO rsVO = new RepairStatusVO();
				rsVO.setStatus_id(status_id);
				rsVO.setStatus_des(status_des);
				
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("rsVO", rsVO); // 含有輸入格式錯誤的rsVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/update_repairStatus_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				RepairStatusService rsSvc = new RepairStatusService();
				rsVO = rsSvc.updateRepairStatus(status_id, status_des);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("rsVO", rsVO); // 資料庫update成功後，正確的rsVO物件,存入req
				String url = "/front_end/listOneRepairStatus.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneRepairStatus.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch(Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/update_repairStatus_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("insert".equals(action)) { // 來自addRepairStatus.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String status_des = req.getParameter("status_des");
				String status_desReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,100}$";
				if(status_des == null || status_des.trim().length() == 0) {
					errorMsgs.add("狀態說明: 請勿空白");
				} else if(!status_des.trim().matches(status_desReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("狀態描述: 只能是中、英文字母、數字和_ , 且長度必需在2到100之間");					
				}
				
				RepairStatusVO rsVO = new RepairStatusVO();
				rsVO.setStatus_des(status_des);
				
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("rsVO", rsVO); // 含有輸入格式錯誤的rsVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/addRepairStatus.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				RepairStatusService rsSvc = new RepairStatusService();
				rsVO = rsSvc.addRepairStatus(status_des);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back_end/listAllRepairStatus.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllRepairStatus.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/listAllRepairStatus.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if("delete".contentEquals(action)) { // 來自listAllRepairStatus.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數***************************************/
				String status_id = new String(req.getParameter("status_id"));
				
				/***************************2.開始刪除資料***************************************/
				RepairStatusService rsSvc = new RepairStatusService();
				rsSvc.deleteRepairStatus(status_id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				String url = "/back_end/listAllRepairStatus.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/				
			} catch(Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/listAllRepairStatus.jsp");
				failureView.forward(req, res);
			}
			
		}
		
	}

}
