package com.repair_main.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.repair_main.model.*;

public class RepairMainServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if("getOne_For_Display".equals(action)) { // 來自rmSelect_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String repair_id = req.getParameter("repair_id");
				if (repair_id == null || (repair_id.trim()).length() == 0) {
					errorMsgs.add("請輸入維修單號");
				}
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/repairApplication/repairList.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				
				
				/***************************2.開始查詢資料*****************************************/
				RepairMainService rmSvc = new RepairMainService();
				RepairMainVO rmVO = rmSvc.getOneRepairMain(repair_id);
				if(rmVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/repairApplication/repairList.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("rmVO", rmVO); // 資料庫取出的rmVO物件,存入req
				String url = "/front_end/repairApplication/repairList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/		
			} catch (Exception e){
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/repairApplication/repairList.jsp");
				failureView.forward(req, res);
			}			
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllRepairMain.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String repair_id = new String(req.getParameter("repair_id"));
				
				/***************************2.開始查詢資料****************************************/
				RepairMainService rmSvc = new RepairMainService();
				RepairMainVO rmVO = rmSvc.getOneRepairMain(repair_id);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("rmVO", rmVO);         // 資料庫取出的rmVO物件,存入req
				String url = "/back_end/repairMain/update_repairMain_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_repairMain_input.jsp
				successView.forward(req, res);
			} catch(Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/repairMain/listAllRepairMain.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_repairMain_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String repair_id = new String(req.getParameter("repair_id").trim());
				
				String mem_id = new String(req.getParameter("mem_id").trim());

				
//				String mem_id = req.getParameter("mem_id");
//				String mem_idReg = "^[(a-zA-Z0-9_)]{0,7}$";
//				if(mem_id == null || mem_id.trim().length() == 0) {
//					errorMsgs.add("申請會員編號: 請勿空白");
//				} else if(!mem_id.trim().matches(mem_idReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("申請會員編號: 只能是英文字母、數字和_ , 且長度必需在0到7之間");
//				}
				
				String cat_id = new String(req.getParameter("cat_id").trim());
				
				String pro_name = req.getParameter("pro_name").trim();
				if (pro_name == null || pro_name.trim().length() == 0) {
					errorMsgs.add("維修品名稱請勿空白");
				}
				
				String description = req.getParameter("description").trim();
				if (description == null || description.trim().length() == 0) {
					errorMsgs.add("問題描述請勿空白");
				}
				
				String status_id = new String(req.getParameter("status_id").trim());
				
				Double amount = null;
				try {
					amount = new Double(req.getParameter("amount").trim());
				} catch(NumberFormatException e) {
					amount = 0.0;
					errorMsgs.add("總金額請填數字");
				}
				
				String dev_address = req.getParameter("dev_address").trim();
				if (dev_address == null || dev_address.trim().length() == 0) {
					errorMsgs.add("收貨地址請勿空白");
				}
				
				String recipient = req.getParameter("recipient").trim();
				if (recipient == null || recipient.trim().length() == 0) {
					errorMsgs.add("收貨人請勿空白");
				}
				
				Double pay_via = null;
				try {
					pay_via = new Double(req.getParameter("pay_via").trim());
				} catch(NumberFormatException e) {
					pay_via = 0.0;
					errorMsgs.add("總金額請填數字");
				}
				
				Double delivery = null;
				try {
					delivery = new Double(req.getParameter("delivery").trim());
				} catch(NumberFormatException e) {
					delivery = 0.0;
					errorMsgs.add("總金額請填數字");
				}
				
				RepairMainVO rmVO = new RepairMainVO();
				rmVO.setRepair_id(repair_id);
				rmVO.setMem_id(mem_id);
				rmVO.setCat_id(cat_id);
				rmVO.setPro_name(pro_name);
				rmVO.setDescription(description);
				rmVO.setStatus_id(status_id);
				rmVO.setAmount(amount);
				rmVO.setDev_address(dev_address);
				rmVO.setRecipient(recipient);
				rmVO.setPay_via(pay_via);
				rmVO.setDelivery(delivery);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rmVO", rmVO); // 含有輸入格式錯誤的rmVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/repairMain/update_repairMain_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				RepairMainService rmSvc = new RepairMainService();
				rmVO = rmSvc.updateRepairMain(repair_id, mem_id, cat_id, pro_name, 
						description, status_id, amount, dev_address, recipient, pay_via, delivery);			
				/***************************3.修改完成,準備轉交(Send the Success view)*************/		
				req.setAttribute("rmVO", rmVO); // 資料庫update成功後,正確的的rmVO物件,存入req
				String url = "/back_end/repairMain/listAllRepairMain.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllRepairMain.jsp
				successView.forward(req, res);
				
			} catch(Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/repairMain/update_repairMain_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("insert".equals(action)) { // 來自addRepairMain.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> mErrorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("mErrorMsgs", mErrorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_id = req.getParameter("mem_id");
				
				
				String mem_idReg = "^[(a-zA-Z0-9_)]{0,10}$";
				if(mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("申請會員編號: 請勿空白");
				}
				else if(!mem_id.trim().matches(mem_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("申請會員編號: 只能是英文字母、數字和_ , 且長度必需在0到7之間");
				}
				
				String cat_id = req.getParameter("cat_id");
				
				
				String cat_idReg = "^[(a-zA-Z0-9_)]{0,10}$";
				if(cat_id == null || cat_id.trim().length() == 0) {
					errorMsgs.add("維修商品分類代號: 請勿空白");
				} else if(!cat_id.trim().matches(cat_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("維修商品分類代號: 只能是英文字母、數字和_ , 且長度必需在0到7之間");
				}
				
				String pro_name = req.getParameter("pro_name").trim();
				if (pro_name == null || pro_name.trim().length() == 0) {
					errorMsgs.add("維修品名稱請勿空白");
					mErrorMsgs.put("pro_name", "維修品名稱:請勿空白");
				}
				
				
				String description = req.getParameter("description").trim();
				if (description == null || description.trim().length() == 0) {
					errorMsgs.add("問題描述請勿空白");
					mErrorMsgs.put("description","問題描述:請勿空白");
				}
				
				
				String status_id = req.getParameter("status_id").trim();
				if (status_id == null || status_id.trim().length() == 0) {
					errorMsgs.add("維修單狀態編號請勿空白");
				}
				

				
				Double amount = null;
				try {
					amount = new Double(req.getParameter("amount").trim());
				} catch(NumberFormatException e) {
					amount = 0.0;
					errorMsgs.add("總金額請填數字");
				}
				

				
				String dev_address = req.getParameter("dev_address").trim();
				if (dev_address == null || dev_address.trim().length() == 0) {
					errorMsgs.add("收貨地址請勿空白");
					mErrorMsgs.put("dev_address","收貨地址:請勿空白");
				}
				

				
				String recipient = req.getParameter("recipient").trim();
				if (recipient == null || recipient.trim().length() == 0) {
					errorMsgs.add("收貨人請勿空白");
					mErrorMsgs.put("recipient","收貨人:請勿空白");
				}
				

				
				Double pay_via = null;
				try {
					pay_via = new Double(req.getParameter("pay_via").trim());
				} catch(NumberFormatException e) {
					pay_via = 0.0;
					errorMsgs.add("付款方式請填數字");
				}
				

				
				Double delivery = null;
				try {
					delivery = new Double(req.getParameter("delivery").trim());
				} catch(NumberFormatException e) {
					delivery = 0.0;
					errorMsgs.add("運送方式請填數字");
				}
				

				
				RepairMainVO rmVO = new RepairMainVO();
				rmVO.setMem_id(mem_id);
				rmVO.setCat_id(cat_id);
				rmVO.setPro_name(pro_name);
				rmVO.setDescription(description);
				rmVO.setStatus_id(status_id);
				rmVO.setAmount(amount);
				rmVO.setDev_address(dev_address);
				rmVO.setRecipient(recipient);
				rmVO.setPay_via(pay_via);
				rmVO.setDelivery(delivery);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rmVO", rmVO); // 含有輸入格式錯誤的rmVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/repairApplication/addRepairMain.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始新增資料***************************************/
				RepairMainService rmSvc = new RepairMainService();
				rmVO = rmSvc.addRepairMain(mem_id, cat_id, pro_name, description,
						status_id, amount, dev_address, recipient, pay_via, delivery);
				
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("rmVO", rmVO);
				String url = "/front_end/repairApplication/repairList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllRepairMain.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/repairApplication/addRepairMain.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllRepairMain.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數***************************************/
				String repair_id = new String(req.getParameter("repair_id"));
				
				/***************************2.開始刪除資料***************************************/
				RepairMainService rmSvc = new RepairMainService();
				rmSvc.deleteRepairMain(repair_id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				String url = "/back_end/listAllRepairMain.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch(Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/listAllRepairMain.jsp");
				failureView.forward(req, res);
			}
			
		}
		
	}

}
