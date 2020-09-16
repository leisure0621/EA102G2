package com.c2cpro_main.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.b2cpro_main.model.B2cproMainService;
import com.b2cpro_main.model.B2cproMainVO;
import com.c2cpro_main.model.B2cjoinDetailVO;
import com.c2cpro_main.model.C2cproMainService;
import com.c2cpro_main.model.C2cproMainVO;
import com.catagory.model.CatagoryService;
import com.spec_main.model.SpecMainVO;
@MultipartConfig
public class C2cproMainServlet extends HttpServlet {
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
				InputStream in = getServletContext().getResourceAsStream("/front_end/c2cproMain/images/defult.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
				
				
			}
			
			
		}catch(Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/front_end/c2cproMain/images/defult.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
			
		}
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String pro_id = req.getParameter("pro_id");
				
				if (pro_id == null || (pro_id.trim()).length() == 0) {
					errorMsgs.add("請輸入商品編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				String proReg = "CP[0-9]{4}";

				if (!pro_id.trim().matches(proReg)) {
					errorMsgs.add("商品編號格式不正確");

				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				C2cproMainService c2cproMainSvc = new C2cproMainService();
				C2cproMainVO c2cproMainVO = c2cproMainSvc.getOneC2cproMain(pro_id);
				if (c2cproMainVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("c2cproMainVO", c2cproMainVO);

				String url = "/front_end/c2cproMain/shopDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
				failureView.forward(req, res);

			}
		}
		
		if ("getLike_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			res.setContentType("text/html; charset=UTF-8");
			try {
				String pro_name = req.getParameter("pro_name");
				System.out.println(pro_name);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				HttpSession session = req.getSession();
				session.removeAttribute("listc2cCat");
				if (pro_name == null || pro_name.trim().length() == 0) {
					session.setAttribute("searchC2cproMain", "");
				}
				else {
					session.setAttribute("searchC2cproMain", pro_name);
				}
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/select_page.jsp");
				failureView.forward(req, res);

			}
		}
		if("getCat_For_Display".equals(action)) {
			HttpSession session = req.getSession();
			session.removeAttribute("listc2cCat");
			List<String> errorMsgs = new LinkedList<String>();
			
			List<C2cproMainVO> listc2cCat= new ArrayList<C2cproMainVO>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String cat_id = req.getParameter("c2cCatpro");
				System.out.println(cat_id);
				C2cproMainService c2cproMainSvc = new C2cproMainService();
				List<C2cproMainVO> listall=c2cproMainSvc.getAll();
				for(C2cproMainVO c2cproMainVO:listall) {
					String cat=c2cproMainVO.getCat_id();
					if(cat.equals(cat_id)) {
						listc2cCat.add(c2cproMainVO);
					}
				}
				
				for(C2cproMainVO c2cproMainVO:listall) {
					System.out.println(c2cproMainVO.getPro_id());
					
				}
				System.out.println("----------");
				
				
				if (cat_id == null || cat_id.trim().length() == 0) {
					session.setAttribute("searchC2cproMain", "");
				}
				else {
					session.setAttribute("listc2cCat", listc2cCat);
					for(C2cproMainVO c2cproMainVO:listc2cCat) {
						System.out.println(c2cproMainVO.getPro_id());
						
					}
				}
			}catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/listAllC2cproMain.jsp");
				failureView.forward(req, res);
			}
			
			
		}
		
		
		
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String pro_id = req.getParameter("pro_id");

				/*************************** 2.開始查詢資料 ****************************************/
				C2cproMainService c2cproMainSvc = new C2cproMainService();
				C2cproMainVO c2cproMainVO = c2cproMainSvc.getOneC2cproMain(pro_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("c2cproMainVO", c2cproMainVO);

				String url = "/front_end/c2cproMain/updateC2cproMain.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/listAllC2cproMain.jsp");
				failureView.forward(req, res);
			}

		}
		
		
		if("update_proStatus".equals(action)) {
			
			res.setContentType("text/html; charset=UTF-8");
			String pro_id = req.getParameter("pro_id");
			Integer status = new Integer(req.getParameter("status"));
			System.out.println("pro_id "+pro_id);
			System.out.println("status "+status);
			C2cproMainService c2cproMainSvc = new C2cproMainService();			
			c2cproMainSvc.update_proStatus(pro_id, status);
			PrintWriter out = res.getWriter();
			JSONObject jsonObject = new JSONObject();
			Map map = new HashMap();
			
			if(status==1) {
				map.put("succe", "保持上架");
				map.put("process", 2);
			}else if(status==0) {
				map.put("succe", "強制下架");
				map.put("process", 1);
			}
			
			jsonObject.put("data", map);
			jsonObject.put("status", 200);
			
			
			out.print(jsonObject);
		}
		
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {

				String pro_id = req.getParameter("pro_id");
				//------------------------------------------------------
				String mem_id = req.getParameter("mem_id");
				//------------------------------------------------------
				String pro_name = req.getParameter("pro_name");
				if (pro_name == null || pro_name.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				}
				//------------------------------------------------------
				String cat_id = req.getParameter("cat_id");
				String proReg = "CAT[0-9]{4}";

				if (!cat_id.trim().matches(proReg)) {
					errorMsgs.add("請選擇商品分類");

				}
				//------------------------------------------------------
				Integer quantity = null;				
				try {
					 quantity = new Integer(req.getParameter("quantity").trim());
				}catch(NumberFormatException e) {
					errorMsgs.add("數量請填數字.");
				}			
				if (quantity == null) {
					errorMsgs.add("商品數量: 請勿空白");
				}
				//------------------------------------------------------
				Double price = null;
				try {
					price = new Double(req.getParameter("price").trim());
				} catch (NumberFormatException e) {
					
					errorMsgs.add("價格請填數字.");
				}
				//------------------------------------------------------
				C2cproMainService c2cproMainSvc1 = new C2cproMainService();
				byte[] photo = null;				
				Part getphoto =  req.getPart("photo");				
				InputStream ips =getphoto.getInputStream();	
				if(ips.available()!=0) {
					byte[] buf = new byte[ips.available()];				
					ips.read(buf);	
					photo = buf;
					ips.close();
				}else {
					photo = c2cproMainSvc1.getOneC2cproMain(pro_id).getPhoto();
				}
				
				//------------------------------------------------------
				String description = req.getParameter("description");
				if (description == null || description.trim().length() == 0) {
					errorMsgs.add("商品描述: 請勿空白");
				}
				//------------------------------------------------------
				Integer status = new Integer(req.getParameter("status"));
				//------------------------------------------------------
				Integer delivery = null;
				try {
					delivery = new Integer(req.getParameter("delivery"));
				}catch(NumberFormatException e) {
					errorMsgs.add("請選擇取貨方式");
				}	
				//------------------------------------------------------
				C2cproMainVO c2cproMainVO = new C2cproMainVO();
				c2cproMainVO.setPro_id(pro_id);
				c2cproMainVO.setMem_id(mem_id);
				c2cproMainVO.setPro_name(pro_name);
				c2cproMainVO.setCat_id(cat_id);
				c2cproMainVO.setQuantity(quantity);
				c2cproMainVO.setPrice(price);
				c2cproMainVO.setPhoto(photo);
				c2cproMainVO.setDescription(description);
				c2cproMainVO.setStatus(status);
				c2cproMainVO.setDelivery(delivery);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("c2cproMainVO", c2cproMainVO);
					req.setAttribute("error_id",pro_id);
					
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/c2cproMain/myshop.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 *****************************************/
				C2cproMainService c2cproMainSvc = new C2cproMainService();
				c2cproMainSvc.updateC2cproMain(pro_id, mem_id, pro_name, cat_id, quantity, price, photo, description,
						status, delivery);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/c2cproMain/myshop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/updateC2cproMain.jsp");
				failureView.forward(req, res);
			}

		}

		if ("insert".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String mem_id = req.getParameter("mem_id");
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.put("mem_id","會員編號: 請勿空白");
				}
				//------------------------------------------------------				
				String pro_name = req.getParameter("pro_name");
				if (pro_name == null || pro_name.trim().length() == 0) {
					errorMsgs.put("pro_name","商品名稱: 請勿空白");
				}
				//------------------------------------------------------				
				String cat_id = req.getParameter("cat_id");
				String proReg = "CAT[0-9]{4}";

				if (!cat_id.trim().matches(proReg)) {
					errorMsgs.put("cat_id","請選擇商品分類");

				}
				//------------------------------------------------------
				Integer quantity = null;				
				try {
					 quantity = new Integer(req.getParameter("quantity"));
				}catch(NumberFormatException e) {
					
					errorMsgs.put("quantity","請勿空白,數量請填數字");
				}			
				if (quantity == null) {
					
					errorMsgs.put("quantity","商品數量: 請勿空白");
				}
				//------------------------------------------------------
				Double price = null;
				try {
					price = new Double(req.getParameter("price").trim());
				} catch (NumberFormatException e) {
					
					errorMsgs.put("price","價格請填數字.");
				}
				//------------------------------------------------------
				byte[] photo = null;				
				Part getphoto =  req.getPart("photo");				
				InputStream ips =getphoto.getInputStream();				
				photo = new byte[ips.available()];				
				ips.read(photo);				
				ips.close();

				//------------------------------------------------------
				String description = req.getParameter("description");
				if (description == null || description.trim().length() == 0) {
					errorMsgs.put("description","商品描述請勿空白");
				}
				//------------------------------------------------------
				Integer delivery = null;
				try {
					delivery = new Integer(req.getParameter("delivery"));
				}catch(NumberFormatException e) {
					errorMsgs.put("delivery","請選擇取貨方式");
				}	
				//------------------------------------------------------
				C2cproMainVO c2cproMainVO = new C2cproMainVO();
				c2cproMainVO.setMem_id(mem_id);
				c2cproMainVO.setPro_name(pro_name);
				c2cproMainVO.setCat_id(cat_id);
				c2cproMainVO.setQuantity(quantity);
				c2cproMainVO.setPrice(price);
				c2cproMainVO.setPhoto(photo);
				c2cproMainVO.setDescription(description);
				c2cproMainVO.setDelivery(delivery);

				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("c2cproMainVO", c2cproMainVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/addC2cproMain.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 *****************************************/
				C2cproMainService c2cproMainSvc = new C2cproMainService();
				String pro_id = c2cproMainSvc.insertWithSpecs(c2cproMainVO);
				
				
				/*************************** 3.查詢詳情,準備轉交(Send the Success view) ***********/
	
				B2cproMainService b2cproMainSvc = new B2cproMainService();
				List<B2cproMainVO> b2cproMainVO = b2cproMainSvc.getAll();
				for (B2cproMainVO list : b2cproMainVO) {// 查詢名稱是否有跟B2C商品相同
					String bname = list.getPro_name();
					String cname = c2cproMainVO.getPro_name();

					if (cname.equals(bname)) {// 有的話就轉交商品詳情的資料

						System.out.println("有比對到相關名稱!");
						C2cproMainService c2cproMainSvc1 = new C2cproMainService();
						List<B2cjoinDetailVO> detailList = c2cproMainSvc1.getDetail(c2cproMainVO.getPro_name());
						req.setAttribute("detailList", detailList);
						System.out.println("既有資料轉交!");
					}

				}

				CatagoryService catagoryService = new CatagoryService();
				Set<SpecMainVO> setC2cCat = catagoryService.getSpecsByCatId(c2cproMainVO.getCat_id());
				CatagoryService catagoryService1 = new CatagoryService();
				Set<B2cproMainVO> setB2cMain = catagoryService1.getB2cprosByCatId(c2cproMainVO.getCat_id());

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				//req.setAttribute("c2cproMainVO", c2cproMainVO);
				System.out.println(pro_id);
				req.setAttribute("pro_id",pro_id);
				req.setAttribute("setC2cCat", setC2cCat);
				req.setAttribute("setB2cMain", setB2cMain);
				String url = "/front_end/c2cproMain/addC2cproDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/addC2cproMain.jsp");
				failureView.forward(req, res);
			}

		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String pro_id = req.getParameter("pro_id");
				C2cproMainService c2cproMainSvc = new C2cproMainService();
				c2cproMainSvc.deleteC2cproMain(pro_id);

				String url = "/front_end/c2cproMain/listAllC2cproMain.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/c2cproMain/listAllC2cproMain.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
