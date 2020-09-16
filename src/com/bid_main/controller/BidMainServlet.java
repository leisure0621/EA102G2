package com.bid_main.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.bid_main.model.*;

@WebServlet("/back_end/bidding/bid.do")
public class BidMainServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOneForDisplay".equals(action)) { // 來自bidMainManagement.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String bid_id = req.getParameter("bid_id");
				bid_id = bid_id.toUpperCase();
				String bidIdReg = "^BD[(0-9)]{4}$";
				
				if (bid_id == null || bid_id.trim().length() == 0) {
					errorMsgs.add("競標編號請勿空白");
				} else if (!bid_id.trim().matches(bidIdReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("競標編號格式不正確, 只能是BD加四位數字");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/bidManagement.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/bidManagement.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				BidMainService bidMainSvc = new BidMainService();
				BidMainVO bidMainVO = bidMainSvc.getOneBid(bid_id);
				if (bidMainVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/bidManagement.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("bidMainVO", bidMainVO); // 資料庫取出的bidMainVO物件,存入req
				String url = "/back_end/bidding/listOneBid.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneBid.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/bidManagement.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOneForUpdate".equals(action)) { // 來自listAllBid.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String bid_id = req.getParameter("bid_id");

				/*************************** 2.開始查詢資料 ****************************************/
				BidMainService bidMainSvc = new BidMainService();
				BidMainVO bidMainVO = bidMainSvc.getOneBid(bid_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("bidMainVO", bidMainVO); // 資料庫取出的bidMainVO物件,存入req
				String url = "/back_end/bidding/updateBidInput.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_bid_input.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/bidManagement.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_bid_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String bid_id = req.getParameter("bid_id").trim();

				String bid_title = req.getParameter("bid_title");
				if (bid_title == null || bid_title.trim().length() == 0) {
					errorMsgs.add("競標標題請勿空白");
				}

				String bid_des = req.getParameter("bid_des").trim();
				if (bid_des == null || bid_des.trim().length() == 0) {
					errorMsgs.add("競標敘述請勿空白");
				}

				String pro_id = req.getParameter("pro_id").trim();
				if (pro_id == null || pro_id.trim().length() == 0) {
					errorMsgs.add("產品編號請勿空白");
				}

				Integer start_price = null;
				try {
					start_price = new Integer(req.getParameter("start_price").trim());
				} catch (NumberFormatException e) {
					start_price = 1;
					errorMsgs.add("起標價格請填數字.");
				}

				Integer incr = null;
				try {
					incr = new Integer(req.getParameter("incr").trim());
				} catch (NumberFormatException e) {
					incr = 10;
					errorMsgs.add("出價增額請填數字.");
				}

				Integer status = null;
				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					status = 1;
					errorMsgs.add("競標狀態請填數字, 1為正常競標, 0為取消競標.");
				}

				String winner = req.getParameter("winner").trim();
				winner = winner.toUpperCase();
				String winnerReg = "^MEM[(0-9)]{4}$";
				
				if (winner == null || winner.trim().length() == 0) {
					winner = "";
				}else if(!winner.trim().matches(winnerReg)){
					errorMsgs.add("得標者編號格式不正確, 只能是MEM加四位數字");
				}

				java.sql.Timestamp start_time = null;
				try {
					start_time = java.sql.Timestamp.valueOf(req.getParameter("start_time").trim());
				} catch (IllegalArgumentException e) {
					start_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入競標開始時間!");
				}

				java.sql.Timestamp end_time = null;
				try {
					end_time = java.sql.Timestamp.valueOf(req.getParameter("end_time").trim());
				} catch (IllegalArgumentException e) {
					end_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入競標結束時間!");
				}
						
				java.sql.Timestamp est_time = java.sql.Timestamp.valueOf(req.getParameter("est_time").trim());
		
				BidMainVO bidMainVO = new BidMainVO();
				bidMainVO.setBid_id(bid_id);
				bidMainVO.setBid_title(bid_title);
				bidMainVO.setBid_des(bid_des);
				bidMainVO.setPro_id(pro_id);
				bidMainVO.setStart_price(start_price);
				bidMainVO.setIncr(incr);
				bidMainVO.setStatus(status);
				bidMainVO.setWinner(winner);
				bidMainVO.setStart_time(start_time);
				bidMainVO.setEnd_time(end_time);
				bidMainVO.setEst_time(est_time);
				
					
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bidMainVO", bidMainVO); // 含有輸入格式錯誤的bidMainVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/updateBidInput.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				BidMainService bidMainSvc = new BidMainService();
				bidMainVO = bidMainSvc.update(bid_id, bid_title, bid_des, pro_id, start_price, incr, status, winner,
						start_time, end_time, est_time);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("bidMainVO", bidMainVO); // 資料庫update成功後,正確的的bidMainVO物件,存入req
				String url = "/back_end/bidding/listOneBid.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneBid.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/updateBidInput.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addBid.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String bid_title = req.getParameter("bid_title").trim();
				if (bid_title == null || bid_title.trim().length() == 0) {
					errorMsgs.add("競標標題 請勿空白");
				}

				String bid_des = req.getParameter("bid_des").trim();
				if (bid_des == null || bid_des.trim().length() == 0) {
					errorMsgs.add("競標敘述 請勿空白");
				}

				String pro_id = req.getParameter("pro_id").trim();
				if (pro_id == null || pro_id.trim().length() == 0) {
					errorMsgs.add("產品編號 請勿空白");
				}

				Integer start_price = null;

				try {
					start_price = new Integer(req.getParameter("start_price").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("起標價格請填數字");
				}

				Integer incr = null;

				try {
					incr = new Integer(req.getParameter("incr").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("出價增額請填數字");
				}

				Integer status = null;

				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					status = 1;
					errorMsgs.add("競標狀態請填數字");
				}

				java.sql.Timestamp start_time = null;
				try {
					start_time = java.sql.Timestamp.valueOf(req.getParameter("start_time").trim());
				} catch (IllegalArgumentException e) {
					start_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入競標起始時間");
				}

				java.sql.Timestamp end_time = null;
				try {
					end_time = java.sql.Timestamp.valueOf(req.getParameter("end_time").trim());
				} catch (IllegalArgumentException e) {
					end_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入競標結束時間");
				}

				BidMainVO bidMainVO = new BidMainVO();
				bidMainVO.setBid_title(bid_title);
				bidMainVO.setBid_des(bid_des);
				bidMainVO.setPro_id(pro_id);
				bidMainVO.setStart_price(start_price);
				bidMainVO.setIncr(incr);
				bidMainVO.setStatus(status);
				bidMainVO.setStart_time(start_time);
				bidMainVO.setEnd_time(end_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bidMainVO", bidMainVO); // 含有輸入格式錯誤的bidMainVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/addBid.jsp");
					failureView.forward(req, res);
					return;

				}

				/*************************** 2.開始新增資料 ***************************************/
				BidMainService bidMainSvc = new BidMainService();
				bidMainSvc.addBid(bid_title, bid_des, pro_id, start_price, incr, status, start_time, end_time);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/bidding/bidManagement.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交bidManagement.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/addBid.jsp");
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
						session.setAttribute("bidSearch", "");
					}
					else {
						session.setAttribute("bidSearch", query);
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

//		if ("delete".equals(action)) { // 來自listAllBid.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 ***************************************/
//				String bid_id = req.getParameter("bid_id");
//
//				/*************************** 2.開始刪除資料 ***************************************/
//				BidMainService bidMainSvc = new BidMainService();
//				bidMainSvc.deleteBidId(bid_id);
//
//				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//				String url = "/back_end/bidding/listAllBid.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/bidding/listAllBid.jsp");
//				failureView.forward(req, res);
//			}
//		}
	}

}
