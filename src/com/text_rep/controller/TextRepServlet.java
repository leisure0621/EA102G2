package com.text_rep.controller;

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

import com.text_rep.model.TextRepService;
import com.text_rep.model.TextRepVO;
import com.text_main.model.TextMainService;
import com.text_main.model.TextMainVO;

public class TextRepServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		
		
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			String rep_id = req.getParameter("rep_id");
			TextRepService textrepSvc = new TextRepService();
			TextRepVO textrepVO = textrepSvc.getOneRep(rep_id);
			ByteArrayInputStream bais = new ByteArrayInputStream(textrepVO.getPicture());
			if(bais.available()!= 0) {
				BufferedInputStream in  = new BufferedInputStream(bais);
				byte[] buf = new byte[4*1024];
				int len;
				while((len = in.read(buf))!=-1) {
					out.write(buf, 0, len);
					
				}
					in.close();
					bais.close();
				
			}else {
				InputStream in = getServletContext().getResourceAsStream("/front_end/c2cproMain/images/tomcat.png");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
				
				
			}
			
			
		}catch(Exception e) {
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
if ("insertrep".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("進入新增");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				String text_id = req.getParameter("text_id");
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (text_id == null || text_id.trim().length() == 0) {
//					errorMsgs.add("�峹�s��: �ФŪť�");
//				} else if(!text_id.trim().matches(enameReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
//					errorMsgs.add("�峹�s��: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
//	            }
				String text_id = req.getParameter("text_id").trim();
				System.out.println("text_id1: " + text_id);
				
				String informant = req.getParameter("informant").trim();
				if (informant == null || informant.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
				
				
				String case_description = req.getParameter("case_description").trim();
				if (case_description == null || case_description.trim().length() == 0) {
						errorMsgs.add("檢舉描述請勿空白");
					}
				
			
					
				Integer process = 0;
//				try {
//					status = new Integer(req.getParameter("status").trim());
//				} catch (NumberFormatException e) {
//					status = 0;
//					errorMsgs.add("狀態碼請勿空白");
//				}
				
 
				TextRepVO textrepVO = new TextRepVO();
				textrepVO.setText_id(text_id);
				textrepVO.setInformant(informant);
				textrepVO.setCase_description(case_description);
				textrepVO.setProcess(process);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("TextRepVO", textrepVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/textmain/textmain_index.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				TextRepService textrepSvc = new TextRepService();
				textrepVO = textrepSvc.addtextRep(text_id,informant,case_description);
				req.setAttribute("textrepVO", textrepVO);
				TextMainService TextMainSvc = new TextMainService();
				TextMainVO TextMainVO = TextMainSvc.getOneText_main(text_id);
				req.setAttribute("textmainVO", TextMainVO);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front_end/msg/msg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/textmain/textmain_index.jsp");
				failureView.forward(req, res);
			}
		}	
//		if("insert_textRep".equals(action)) {
//			
//			
//			String text_id = req.getParameter("text_id");
//			String informant = req.getParameter("informant");
//			String case_description =req.getParameter("case_description");
//			byte[] picture = null;
//			Integer process = 0;
//			
//			TextRepService textrepSvc = new TextRepService();
//			textrepSvc.addtextRep(informant,picture, text_id, case_description, process);
//			
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("data", "檢舉成功");
//			jsonObject.put("status",200);
//			out.print(jsonObject);
//		}
		if("update_textrepProcess".equals(action)) {
			Map map = new HashMap();
			Integer process = new Integer(req.getParameter("process"));
			System.out.println("123456");
			String rep_id = req.getParameter("rep_id");
			System.out.println("rep_id" + rep_id);
			System.out.println("process" + process);
			TextRepService textrepSvc = new TextRepService();
			textrepSvc.update_textrepProcess(rep_id,process);
			Date date=new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String  day = sdf.format(date);
			if(process==1) {
				map.put("process","完成且處置" );
			}
			if(process==2) {
				map.put("process","完成且不處置" );
			}
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", "map");
			jsonObject.put("status", 200);
			out.print(jsonObject);
		}

		
	}
}
