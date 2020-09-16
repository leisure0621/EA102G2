package com.c2cso_main.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.c2cpro_main.model.C2cproMainService;
import com.c2cpro_main.model.C2cproMainVO;
import com.c2cso_main.model.C2csoMainService;
import com.c2cso_main.model.C2csoMainVO;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import ecpay.payment.integration.domain.InvoiceObj;

public class C2csoMainServlet extends HttpServlet{
	

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("insert_soMain".equals(action)) {
		 
			String pro_id = req.getParameter("pro_id");
			String buyer_id = req.getParameter("buyer_id");
			Integer quantity = new Integer(req.getParameter("quantity"));
			String mem_id = req.getParameter("mem_id");
			String status = req.getParameter("status");
			String forword_page = req.getParameter("forword_page");
			System.out.println("----------");
			System.out.println(buyer_id);
			System.out.println("----------");

			
				    System.out.println(pro_id);
					System.out.println(buyer_id);
					System.out.println(quantity);
					System.out.println(status);
					
					if(mem_id == null || mem_id.trim().length() == 0) {
						HttpSession session = req.getSession();
						String url = "/front_end/login/login.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); // 跳登入頁面
						session.setAttribute("forword_page", forword_page);
						successView.forward(req, res);
					} else {
						C2cproMainService c2cproMainSvc = new C2cproMainService();
						C2cproMainVO c2cproMainVO = c2cproMainSvc.getOneC2cproMain(pro_id);
						c2cproMainSvc.updateC2cproMain(pro_id, c2cproMainVO.getMem_id(), c2cproMainVO.getPro_name(), c2cproMainVO.getCat_id(), 
								(c2cproMainVO.getQuantity()-quantity), c2cproMainVO.getPrice(), c2cproMainVO.getPhoto(), c2cproMainVO.getDescription(), 
														c2cproMainVO.getStatus(), c2cproMainVO.getDelivery());
						
						
						C2csoMainService c2csoMainSvc = new C2csoMainService();
						c2csoMainSvc.addC2csoMain(pro_id, buyer_id, quantity, status);
						String url = "/front_end/c2cproMain/c2cbuyer.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
						successView.forward(req, res);
					}
					
					
					
					

			
		}
		
		if("so_Shipment".equals(action)) {
			String so_id = req.getParameter("so_id");
			System.out.println(so_id);
			String status = "CST0003";
			C2csoMainService c2csoMainService = new C2csoMainService();
			C2csoMainVO	c2csoMainVO=c2csoMainService.getOneC2csoMain(so_id);
			c2csoMainService.updateC2csoMain(so_id, c2csoMainVO.getPro_id(), c2csoMainVO.getBuyer_id(), c2csoMainVO.getQuantity(), status);
			res.setContentType("text/html; charset=UTF-8");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();			
			JSONObject json  = new JSONObject();
			json.put("data", "已出貨");
			json.put("status", 200);			
			out.print(json);
			

		}
		if("so_checkout".equals(action)) {
			String mem_id = req.getParameter("mem_id");
			String pro_id = req.getParameter("pro_id");
			String so_id = req.getParameter("so_id");
			String seller_id = req.getParameter("seller_id");
			Double price = new Double (req.getParameter("price"));
			
			InvoiceObj invoice = null;
			System.out.println(pro_id);
			System.out.println(so_id);
			System.out.println(price.intValue());
			
			System.out.println(new String(req.getRequestURL()));
			System.out.println("-------new String(req.getRequestURL())--------");
			AllInOne allInOne = new AllInOne("");
			AioCheckOutOneTime obj = new AioCheckOutOneTime();
			Date date = new Date();
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			System.out.println(sdf.format(date));
			
			obj.setMerchantTradeNo(so_id+date.getTime());
			obj.setCustomField1(so_id);//自訂欄位 放置訂單編號
			obj.setCustomField2(mem_id);
			obj.setCustomField3(seller_id);
			obj.setMerchantTradeDate(sdf.format(date));			
			obj.setTotalAmount(String.valueOf(price.intValue()));
			obj.setTradeDesc("test Description"); 
			obj.setItemName(pro_id);				
			obj.setReturnURL(new String(req.getRequestURL()));
			obj.setNeedExtraPaidInfo("N");
			obj.setRedeem("N");
			obj.setOrderResultURL(req.getScheme()+"://"
					+req.getServerName()+":"
					+req.getServerPort()+"/"
					+req.getContextPath()
					+"/front_end/c2cproMain/c2cbuyer.jsp");			
			obj.setChooseSubPayment("Credit");
			res.setContentType("text/html; charset=UTF-8");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			String form = allInOne.aioCheckOut(obj, invoice);
			out.println("<HTML>");
			out.println("<HEAD><TITLE></TITLE></HEAD>");
			out.println("<BODY>");
			out.print(form);
			out.println("</BODY></HTML>");

		}
		String	RtnMsg = req.getParameter("RtnMsg");
		System.out.println("---------------------");
		System.out.println("RtnMsg:"+RtnMsg);
		if(RtnMsg != null) {
			Integer	RtnCode = new Integer(req.getParameter("RtnCode"));
			String so_id = req.getParameter("CustomField1");
			String mem_id = req.getParameter("CustomField2");
			System.out.println(so_id);
			System.out.println(RtnMsg);
			System.out.println("mem_id:"+mem_id);
			if(RtnCode == 1) {
				System.out.println("成功回傳1");
				C2csoMainService c2csoMainService = new C2csoMainService();
				C2csoMainVO	c2csoMainVO=c2csoMainService.getOneC2csoMain(so_id);
				String status =  "CST0002";
				
				c2csoMainService.updateC2csoMain(so_id, c2csoMainVO.getPro_id(), c2csoMainVO.getBuyer_id(), c2csoMainVO.getQuantity(), status);
				
				
			}else {
				System.out.println("交易異常");
			}
			
		}
		
		
		
	}
}
