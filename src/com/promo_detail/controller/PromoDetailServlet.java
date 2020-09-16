package com.promo_detail.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.promo_detail.model.*;
import com.promo_main.model.PromoMainService;
import com.promo_main.model.PromoMainVO;

@WebServlet("/back_end/promotion/promoDetail.do")
public class PromoDetailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getByPromoIdForDisplay".equals(action)) { // 來自listAllPromo.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String promo_id = req.getParameter("promo_id");
				
				/*************************** 2.開始查詢資料 *****************************************/
				PromoDetailService promoDetailSvc = new PromoDetailService();
				List<PromoDetailVO> promoDetailVO = promoDetailSvc.getByPromoId(promo_id);
				if (promoDetailVO == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/promoManagement.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("isList", true);
				req.setAttribute("promoDetailVO", promoDetailVO);
				req.setAttribute("promo_id", promo_id);
				
				String url = "/back_end/promotion/listOnePromoDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOnePromoDetail.jsp
				successView.forward(req, res);

				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/promoManagement.jsp");
				failureView.forward(req, res);
			}
		} 

		if ("insert".equals(action)) { // 來自addPromoDetail.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String promo_id = req.getParameter("promo_id");
				if(promo_id == null || promo_id == "-1") {
					errorMsgs.add("請選擇促銷編號");
				}
				
				String pro_id = req.getParameter("pro_id");

				if(pro_id == null || pro_id == "-1") {
					errorMsgs.add("請選擇產品編號");
				}
				
				Integer promo_price = null;
				try {
					promo_price = new Integer(req.getParameter("promo_price"));
				} catch (NumberFormatException e) {
					errorMsgs.add("促銷價格請填數字");
				}

				PromoDetailVO promoDetailVO = new PromoDetailVO();
				promoDetailVO.setPromo_id(promo_id);
				promoDetailVO.setPro_id(pro_id);
				promoDetailVO.setPromo_price(promo_price);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("promoDetailVO", promoDetailVO); // 含有輸入格式錯誤的promoDetailVO物件, 也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/addPromoDetail.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				PromoDetailService promoDetailSvc = new PromoDetailService();
				promoDetailSvc.addPromoDetail(promo_id, pro_id, promo_price);
				
				PromoDetailVO pdVOs = promoDetailSvc.getOneForUpdate(promo_id, pro_id);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("promoDetailVO", pdVOs);
				req.setAttribute("isList", false);
				
				String url = "/back_end/promotion/listOnePromoDetail.jsp";  // 新增成功後轉交listOnePromoDetail
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交promoDetailManagement
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/addPromoDetail.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("getOneForUpdateDisplay".equals(action)) { // 來自listAllPromoDetail.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String promo_id = req.getParameter("promo_id");
				String pro_id = req.getParameter("pro_id");
				
				/*************************** 2.開始查詢資料 ****************************************/
				PromoDetailService promoDetailSvc = new PromoDetailService();
				PromoDetailVO promoDetailVO = promoDetailSvc.getOneForUpdate(promo_id, pro_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("promoDetailVO", promoDetailVO); // 資料庫取出的promoDetailVO物件, 存入req
				RequestDispatcher successView = req.getRequestDispatcher("/back_end/promotion/updatePromoDetailInput.jsp"); // 成功轉交updatePromoInput.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			}catch(Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/promoDetailManagement.jsp");
				failureView.forward(req, res);;
			}
		}

		if("update".equals(action)) {  // 來自updatePromoDetailInput.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String promo_id = req.getParameter("promo_id").trim();
				
				String pro_id = req.getParameter("pro_id").trim();
				if(pro_id == null || pro_id.trim().length() == 0) {
					errorMsgs.add("產品編號請勿空白");
				}
				
				Integer promo_price = null;
				try {
					promo_price = new Integer(req.getParameter("promo_price"));
				}catch(NumberFormatException e) {
					errorMsgs.add("促銷價格請填數字");
				}
				
				PromoDetailVO promoDetailVO = new PromoDetailVO();
				
				promoDetailVO.setPromo_id(promo_id);
				promoDetailVO.setPro_id(pro_id);
				promoDetailVO.setPromo_price(promo_price);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("promoDetailVO", promoDetailVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/updatePromoDetailInput.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/*************************** 2.開始修改資料 *****************************************/
				PromoDetailService promoDetailSvc = new PromoDetailService();
				promoDetailVO = promoDetailSvc.update(promo_id, pro_id, promo_price);
				
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				
				req.setAttribute("promoDetailVO", promoDetailVO);
				req.setAttribute("isList", false);
				req.setAttribute("promo_id", promo_id);
				String url = "/back_end/promotion/listOnePromoDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後, 轉交listOnePromoDetail.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			}catch(Exception e) {
				errorMsgs.add("修改資料失敗:" +e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/updatePromoDetailInput.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String promo_id = req.getParameter("promo_id");
				String pro_id = req.getParameter("pro_id");

				/*************************** 2.開始刪除資料 ***************************************/
				PromoDetailService promoDetailSvc = new PromoDetailService();
				promoDetailSvc.deletePromoDetail(promo_id, pro_id);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("isList", true);
				req.setAttribute("promo_id", promo_id);
				String url = "/back_end/promotion/promoDetailManagement.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後, 轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗: " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/promotion/promoDetailManagement.jsp");
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
					session.setAttribute("promoDetailSearch", "");
				}
				else {
					session.setAttribute("promoDetailSearch", query);
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
