package com.c2cpro_detail.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.b2cpro_main.model.B2cproMainService;
import com.b2cpro_main.model.B2cproMainVO;
import com.c2cpro_detail.model.C2cproDetailService;
import com.c2cpro_detail.model.C2cproDetailVO;
import com.c2cpro_main.model.B2cjoinDetailVO;
import com.c2cpro_main.model.C2cproMainService;
import com.c2cpro_main.model.C2cproMainVO;
import com.catagory.model.CatagoryService;
import com.spec_main.model.SpecMainVO;

public class C2cproDetailServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//		if ("insert".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//			try {
//				/*************************** 1.接收請求參數 ****************************************/
//				String pro_id = req.getParameter("pro_id");
//
//				/*************************** 2.開始查詢資料 ****************************************/
////				C2cproDetailService c2cproDetailSvc = new C2cproDetailService();
////				List<C2cproDetailVO> list = c2cproDetailSvc.getOneC2cproDetail(pro_id);
//				C2cproMainService c2cproMainSvc = new C2cproMainService();
//				C2cproMainVO c2cproMainVO = c2cproMainSvc.getOneC2cproMain(pro_id);
//				B2cproMainService b2cproMainSvc = new B2cproMainService();
//				List<B2cproMainVO> b2cproMainVO = b2cproMainSvc.getAll();
//				for (B2cproMainVO list : b2cproMainVO) {// 查詢名稱是否有跟B2C商品相同
//					String bname = list.getPro_name();
//					String cname = c2cproMainVO.getPro_name();
//
//					if (cname.equals(bname)) {// 有的話就轉交商品詳情的資料
//
//						System.out.println("有比對到相關名稱!");
//						C2cproMainService c2cproMainSvc1 = new C2cproMainService();
//						List<B2cjoinDetailVO> detailList = c2cproMainSvc1.getDetail(c2cproMainVO.getPro_name());
//						req.setAttribute("detailList", detailList);
//						System.out.println("既有資料轉交!");
//					}
//
//				}
//
//				CatagoryService catagoryService = new CatagoryService();
//				Set<SpecMainVO> setC2cCat = catagoryService.getSpecsByCatId(c2cproMainVO.getCat_id());
//				CatagoryService catagoryService1 = new CatagoryService();
//				Set<B2cproMainVO> setB2cMain = catagoryService1.getB2cprosByCatId(c2cproMainVO.getCat_id());
//
//				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
////				req.setAttribute("list", list);
//
//				req.setAttribute("c2cproMainVO", c2cproMainVO);
//				req.setAttribute("setC2cCat", setC2cCat);
//				req.setAttribute("setB2cMain", setB2cMain);
//				String url = "/front_end/c2cproMain/addC2cproDetail.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
//				failureView.forward(req, res);
//			}
//
//		}

		if ("insert_confirm".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				System.out.println("詳情新增進入");
				String pro_id = req.getParameter("pro_id");
				System.out.println(pro_id);
				System.out.println("在詳情抓到pro_id了");
				C2cproMainService c2cproMainSvc = new C2cproMainService();
				C2cproMainVO c2cproMainVO = c2cproMainSvc.getOneC2cproMain(pro_id);
				System.out.println("抓到c2cproMainVO了");
				CatagoryService catagoryService = new CatagoryService();
				System.out.println("抓到catagoryService了");

				Set<SpecMainVO> setC2cCat1 = catagoryService.getSpecsByCatId(c2cproMainVO.getCat_id());
				System.out.println(setC2cCat1);
				System.out.println("抓到setC2cCat了");
				for (SpecMainVO set : setC2cCat1) {
					String spec_detail = req.getParameter(set.getSpec_id());
					String spec_id = set.getSpec_id();
					C2cproDetailService c2cproDetailSvc = new C2cproDetailService();
					c2cproDetailSvc.addC2cproDetail(pro_id, spec_id, spec_detail);
				}

				System.out.println("完成新增，進行轉交");
				String url = "/front_end/c2cproMain/myshop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);
				System.out.println("完成轉交");
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/myshop.jsp");
				failureView.forward(req, res);
			}

		}

		if ("update1".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				System.out.println("修改進入");
				String pro_id = req.getParameter("pro_id");
				System.out.println(pro_id);
				System.out.println("抓到pro_id了");
				C2cproDetailService c2cproDetailSvc = new C2cproDetailService();
				List<C2cproDetailVO> list = c2cproDetailSvc.getOneC2cproDetail(pro_id);
				/*************************** 2.開始修改資料 *****************************************/
				for (C2cproDetailVO c2cproDetailVO : list) {

					String spec_id = c2cproDetailVO.getSpec_id();
					String spec_detail = req.getParameter(c2cproDetailVO.getSpec_id());
					C2cproDetailService c2cproDetailSvc1 = new C2cproDetailService();
					c2cproDetailSvc1.updateC2cproDetail(pro_id, spec_id, spec_detail);

				}
				System.out.println("完成修改，進行轉交");

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/c2cproMain/myshop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/myshop.jsp");
				failureView.forward(req, res);
			}

		}

	}

}
