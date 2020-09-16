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

@WebServlet("/showDetail.do")
public class ShowDetail extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getProductForDisplay".equals(action)) { // 來自promoIndex.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String promo_id = req.getParameter("promo_id");
				String pro_id = req.getParameter("pro_id");

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/promotion/promoIndex.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				PromoDetailService promoDetailSvc = new PromoDetailService();
				PromoDetailVO promoDetailVO = promoDetailSvc.getOneForUpdate(promo_id, pro_id);

				PromoMainService promoMainSvc = new PromoMainService();
				PromoMainVO promoMainVO = promoMainSvc.getByPrimaryKey(promo_id);

				if (promoDetailVO == null || promoMainVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/promotion/promoIndex.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("promoDetailVO", promoDetailVO); // 資料庫取出的promoDetailVO物件, 存入req
				req.setAttribute("promoMainVO", promoMainVO);

				String url = "/front_end/promotion/promoDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 promoDetail.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/promotion/promoIndex.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
