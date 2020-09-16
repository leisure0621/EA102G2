package com.promo_main.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.promo_main.model.*;

@WebServlet("/back_end/promotion/promo.do")
public class PromoMainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOneForDisplay".equals(action)) { // 來自promoManagement.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String promo_id = req.getParameter("promo_id");
				promo_id = promo_id.toUpperCase();
				String promoIdReg = "^PRM[(0-9)]{4}$";
				
				if (promo_id == null || promo_id.trim().length() == 0) {
					errorMsgs.add("促銷編號請勿空白");
				} else if (!promo_id.trim().matches(promoIdReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("促銷編號格式不正確, 只能是PRM加四位數字");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/promoManagement.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				PromoMainService promoMainSvc = new PromoMainService();
				PromoMainVO promoMainVO = (PromoMainVO) promoMainSvc.getByPrimaryKey(promo_id);
				if (promoMainVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/promoManagement.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("promoMainVO", promoMainVO); // 資料庫取出的promoMainVO物件,存入req
				String url = "/back_end/promotion/listOnePromo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOnePromo.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/promoManagement.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOneForUpdate".equals(action)) { // 來自listAllPromo.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String promo_id = req.getParameter("promo_id");

				/*************************** 2.開始查詢資料 ****************************************/
				PromoMainService promoMainSvc = new PromoMainService();
				PromoMainVO promoMainVO = promoMainSvc.getByPrimaryKey(promo_id);
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("promoMainVO", promoMainVO); // 資料庫取出的promoMainVO物件,存入req
				String url = "/back_end/promotion/updatePromoInput.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 updatePromoInput.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/promoManagement.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自updatePromoInput.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String promo_id = req.getParameter("promo_id").trim();
				
				String promo_name = req.getParameter("promo_name");
				if (promo_name == null || promo_name.trim().length() == 0) {
					errorMsgs.add("促銷名稱請勿空白");
				}
				java.sql.Date start_date = null;
				try {
					start_date = java.sql.Date.valueOf(req.getParameter("start_date").trim());
				} catch (IllegalArgumentException e) {
					start_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入促銷開始日期! ");
				}
				java.sql.Date end_date = null;
				try {
					end_date = java.sql.Date.valueOf(req.getParameter("end_date").trim());
				} catch (IllegalArgumentException e) {
					end_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入促銷結束日期! ");
				}
				
				java.sql.Timestamp est_time = java.sql.Timestamp.valueOf(req.getParameter("est_time").trim());
				
				Integer status = null;
				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					status = 1;
					errorMsgs.add("促銷狀態請填數字, 1為正常促銷, 0為取消促銷.");
				}
				
				PromoMainVO promoMainVO = new PromoMainVO();
				promoMainVO.setPromo_id(promo_id);
				promoMainVO.setPromo_name(promo_name);
				promoMainVO.setStart_date(start_date);
				promoMainVO.setEnd_date(end_date);
				promoMainVO.setEst_time(est_time);
				promoMainVO.setStatus(status);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("promoMainVO", promoMainVO); // 含有輸入格式錯誤的promoMainVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/updatePromoInput.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				PromoMainService promoMainSvc = new PromoMainService();
				promoMainVO = promoMainSvc.update(promo_id, promo_name, start_date, end_date, est_time, status);
				
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("promoMainVO", promoMainVO); // 資料庫update成功後,正確的的promoMainVO物件,存入req
				String url = "/back_end/promotion/listOnePromo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOnePromo.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/updatePromoInput.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { // 來自addPromo.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String promo_name = req.getParameter("promo_name").trim();
				if (promo_name == null || promo_name.trim().length() == 0) {
					errorMsgs.add("促銷名稱 請勿空白");
				}

				java.sql.Date start_date = null;
				try {
					start_date = java.sql.Date.valueOf(req.getParameter("start_date").trim());
				} catch (IllegalArgumentException e) {
					start_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入促銷開始日期");
				}
				
				java.sql.Date end_date = null;
				try {
					end_date = java.sql.Date.valueOf(req.getParameter("end_date").trim());
				} catch (IllegalArgumentException e) {
					end_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入促銷開始日期");
				}
				
				Integer status = null;

				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("促銷狀態請填數字, 1為正常促銷, 0為取消促銷.");
				}
				
				PromoMainVO promoMainVO = new PromoMainVO();
				promoMainVO.setPromo_name(promo_name);
				promoMainVO.setStart_date(start_date);
				promoMainVO.setEnd_date(end_date);
				promoMainVO.setStatus(status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("promoMainVO", promoMainVO); // 含有輸入格式錯誤的promoMainVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/addPromo.jsp");
					failureView.forward(req, res);
					return;

				}

				/*************************** 2.開始新增資料 ***************************************/
				PromoMainService promoMainSvc = new PromoMainService();
				promoMainSvc.addPromo(promo_name, start_date, end_date, status);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/promotion/promoManagement.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllPromo.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/addPromo.jsp");
				failureView.forward(req, res);
			}
		}

		// 搜尋
		if ("search".equals(action)) {
			PrintWriter out = res.getWriter();
			List<String> reportMsgs = new LinkedList<String>();
			try {
				// 將資料存至 session
				HttpSession session = req.getSession();
				String query = req.getParameter("query");
				if (query == null || query.trim().length() == 0) {
					session.setAttribute("promoSearch", "");
				}
				else {
					session.setAttribute("promoSearch", query);
				}
				reportMsgs.add("搜尋成功");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("搜尋資料失敗:" + e.getMessage());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
			
			out.flush();
			out.close();
		}
		
		
	
	}

}
