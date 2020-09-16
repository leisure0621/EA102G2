package com.bid_detail.controller;

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

import org.json.JSONArray;
import org.json.JSONObject;

import com.b2cpro_main.model.B2cproMainService;
import com.b2cpro_main.model.B2cproMainVO;
import com.bid_detail.model.BidDetailService;
import com.bid_detail.model.BidDetailVO;


@WebServlet("/back_end/bidding/bidDetail.do")
public class BidDetailSertvlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOneByBidIdToFront".equals(action)) { // 來自bidIndex.jsp的請求
			System.out.println("aaa");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String bid_id = req.getParameter("bid_id");
				String pro_id = req.getParameter("pro_id");

				/*************************** 2.開始查詢資料 *****************************************/
				BidDetailService bidDetailSvc = new BidDetailService();
				List<BidDetailVO> bidDetailVO = bidDetailSvc.getByBidId(bid_id);
				
				B2cproMainService bproSvc = new B2cproMainService();
				B2cproMainVO bproVO = bproSvc.findByPrimaryKey(pro_id);
				
				if (bidDetailVO == null || bproVO == null) {
					errorMsgs.add("查無資料");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/bidding/bidIndex.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				 
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				
				req.setAttribute("bidDetailVO", bidDetailVO); // 資料庫取出的bidDetailVO物件,存入req
				req.setAttribute("bproVO", bproVO);
				req.setAttribute("bid_id", bid_id);
				
				String url = "/front_end/bidding/bidDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 bidDetail.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/bidding/bidIndex.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOneByBidId".equals(action)) { // 來自bidDetail.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String bid_id = req.getParameter("bid_id");

				/*************************** 2.開始查詢資料 *****************************************/
				BidDetailService bidDetailSvc = new BidDetailService();
				List<BidDetailVO> bidDetailVO = bidDetailSvc.getByBidId(bid_id);
				if (bidDetailVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				 
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("bidDetailVO", bidDetailVO); // 資料庫取出的bidDetailVO物件,存入req
				String url = "/back_end/bidding/listOneBidDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneBidDetail.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自bidDetail.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String bid_id = req.getParameter("bid_id");
				
				String mem_id = req.getParameter("mem_id");
				if(mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("請登入會員");
				}
				
				Integer bid_price = null;
				try {
					bid_price = new Integer(req.getParameter("bid_price").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("下標金額請填數字.");
				}
				
				BidDetailVO newBDVO = new BidDetailVO();
				newBDVO.setBid_id(bid_id);
				newBDVO.setMem_id(mem_id);
				newBDVO.setBid_price(bid_price);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newBDVO", newBDVO); // 含有輸入格式錯誤的bidDetailVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/bidding/bidDetail.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				BidDetailService bidDetailSvc = new BidDetailService();
				newBDVO = bidDetailSvc.addBidDetail(bid_id, mem_id, bid_price);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				JSONObject vo = new JSONObject();
				vo.put("bid_id", newBDVO.getBid_id());
				vo.put("mem_id", newBDVO.getMem_id());
				vo.put("bid_price", newBDVO.getBid_price());
					
//				System.out.println(vo.toString());
				
				PrintWriter out = res.getWriter();
				out.write(vo.toString());
				out.flush();
				out.close();
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/bidding/bidDetail.jsp");
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
					session.setAttribute("bidDetailSearch", "");
				}
				else {
					session.setAttribute("bidDetailSearch", query);
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
