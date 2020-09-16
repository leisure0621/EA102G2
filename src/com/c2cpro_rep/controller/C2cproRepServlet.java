package com.c2cpro_rep.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.c2cpro_main.model.C2cproMainService;
import com.c2cpro_main.model.C2cproMainVO;
import com.c2cpro_rep.model.C2cproRepService;

public class C2cproRepServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			String pro_id = req.getParameter("pro_id");
			C2cproMainService c2cproMainSvc = new C2cproMainService();
			C2cproMainVO c2cproMainVO = c2cproMainSvc.getOneC2cproMain(pro_id);
			ByteArrayInputStream bais = new ByteArrayInputStream(c2cproMainVO.getPhoto());
			if (bais.available() != 0) {
				BufferedInputStream in = new BufferedInputStream(bais);
				byte[] buf = new byte[4 * 1024];
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);

				}
				in.close();
				bais.close();

			} else {
				InputStream in = getServletContext().getResourceAsStream("/front_end/c2cproMain/images/tomcat.png");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();

			}

		} catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/front_end/c2cproMain/images/tomcat.png");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();

		}
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
//		if ("getOne_For_Display".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				String pro_id = req.getParameter("pro_id");
//				
//				if (pro_id == null || (pro_id.trim()).length() == 0) {
//					errorMsgs.add("請輸入商品編號");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				String proReg = "CP[0-9]{4}";
//
//				if (!pro_id.trim().matches(proReg)) {
//					errorMsgs.add("商品編號格式不正確");
//
//				}
//
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				/*************************** 2.開始查詢資料 *****************************************/
//				C2cproMainService c2cproMainSvc = new C2cproMainService();
//				C2cproMainVO c2cproMainVO = c2cproMainSvc.getOneC2cproMain(pro_id);
//				if (c2cproMainVO == null) {
//					errorMsgs.add("查無資料");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
//				req.setAttribute("c2cproMainVO", c2cproMainVO);
//
//				String url = "/front_end/c2cproMain/shopDetail.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
//				successView.forward(req, res);
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
//				failureView.forward(req, res);
//
//			}
//		}

		if ("insert_proRep".equals(action)) {

			String reported_content = req.getParameter("reported_content");
			String informant = req.getParameter("informant");
			String case_description = req.getParameter("case_description");
			byte[] picture = null;
			Integer process = 0;
			if ("notlogin".equals(informant)) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "請先登入");
				jsonObject.put("status", 200);
				out.print(jsonObject);
				

			} else {
				C2cproRepService c2cproRepSvc = new C2cproRepService();
				c2cproRepSvc.addC2cproRep(informant, reported_content, picture, case_description, process);

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "檢舉成功");
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		if ("update_proRep".equals(action)) {
			System.out.println("關鍵字");
			Map map = new HashMap();
			Integer process = new Integer(req.getParameter("process"));
			String rep_id = req.getParameter("rep_id");
			
			System.out.println(rep_id);
			System.out.println(process);
			C2cproRepService c2cproRepSvc = new C2cproRepService();
			c2cproRepSvc.updateC2cproRep(rep_id, process);
			Date date=new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String  day = sdf.format(date);
			System.out.println(day);
			if(process==1) {
				map.put("process","完成且處置" );
			}
			if(process==2) {
				map.put("process","完成且不處置" );
			}
			
			map.put("date",day);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", map);
			jsonObject.put("status", 200);
			out.print(jsonObject);
		}

	}
}
