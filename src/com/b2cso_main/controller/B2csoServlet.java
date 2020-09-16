package com.b2cso_main.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.b2cso_detail.model.B2cso_detailService;
import com.b2cso_detail.model.B2cso_detailVO;
import com.b2cso_main.model.B2cso_mainService;
import com.b2cso_main.model.B2cso_mainVO;
import com.dept.model.DeptService;
import com.dept.model.DeptVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.emp_auth.model.EmpAuthService;
import com.emp_auth.model.EmpAuthVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;

public class B2csoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		// 設定參數
		String action = null;
		String body = null;
		JSONObject paraJsonObj = null;
		// 取值 使用單個參數
		if(req.getParameter("action") != null) {
			action = req.getParameter("action");
		}
		// 取值 使用字串結構
		if((body = req.getReader().readLine()) != null) {
			paraJsonObj = new JSONObject(body);
			action = paraJsonObj.getString("action");
			System.out.println("body: "+ body);
		}
		
		// 取得訂單詳情
		if("getDetail".equals(action)) {
			List<String> reportMsgs = new LinkedList<String>();

			try {
				Map map = new HashMap();
				String so_id = null;
				if(req.getParameter("so_id") == null || req.getParameter("so_id").trim().length() == 0) {
					reportMsgs.add("需輸入訂單號");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				} else {
					so_id = req.getParameter("so_id");
				}
				
				// 取得訂單
				B2cso_mainService b2cso_mainService = new B2cso_mainService();
				// 取得訂單詳情
				B2cso_detailService b2cso_detailService = new B2cso_detailService();
				B2cproMainService b2cproMainService = new B2cproMainService();
				List<B2cso_detailVO> b2cso_detailVO= b2cso_detailService.getOneDetails(so_id);
				// 取得商品資訊
				List<B2cproMainVO> proList = new ArrayList<B2cproMainVO>();
				if(b2cso_detailVO.size() > 0) {
					for(int i = 0;i<b2cso_detailVO.size();i++) {
						proList.add(b2cproMainService.findByPrimaryKey(b2cso_detailVO.get(i).getPro_id()));
					}
				}
				// 待輸出資訊
				map.put("order", b2cso_mainService.getOneB2csoMain(so_id));
				map.put("order_detail", b2cso_detailVO);
				map.put("order_list", proList);
				
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", map);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("無法取得訂單詳情:" + e.getMessage());
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 加入購物車
		if("addToCar".equals(action)) {
			List<String> reportMsgs = new LinkedList<String>();

			try {
				HttpSession session = req.getSession();
				JSONArray productList = paraJsonObj.getJSONArray("productList");
				
				session.setAttribute("cartList", productList.toString());
				
				reportMsgs.add("加入購物車成功");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("無法加入購物車:" + e.getMessage());
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 加入訂單
		if("addOrder".equals(action)) {
			List<String> reportMsgs = new LinkedList<String>();
			
			try {
				B2cso_mainService b2cso_mainService = new B2cso_mainService();
				
				// 會員ID
				String buyer_id = null;
				if(paraJsonObj.getString("mem_id") == null || paraJsonObj.getString("mem_id").trim().length() == 0) {
					reportMsgs.add("需登入會員");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				} else {
					buyer_id = paraJsonObj.getString("mem_id");
				}
				// 訂單狀態 組裝單
				Integer type = 0;
				if(paraJsonObj.getInt("type") <= 0) {
					reportMsgs.add("需給訂單狀態");
				} else {
					type = paraJsonObj.getInt("type");
				}
				// 訂單總額
				Integer amount = 0;
				if(paraJsonObj.getInt("total") <= 0) {
					reportMsgs.add("需提供總金額");
				} else {
					amount = paraJsonObj.getInt("total");
				}
				// 訂單狀態 處理中
				String status = "BST0001";
				// 取貨方式 現場取貨
				Integer delivery = 0;
				System.out.println(paraJsonObj.getInt("delivery"));
				if(type == 2) {
					// 組裝單
					delivery = 3;
				} else if(paraJsonObj.getInt("delivery") <= 0) {
					reportMsgs.add("需提供取貨方式");
				} else {
					delivery = paraJsonObj.getInt("delivery");
				}
				// 取貨地址 現場取貨 預設空
				String del_address = null;
				if(type == 2) {
					// 組裝單
					del_address = "";
				} else if(paraJsonObj.getString("del_address") == null || paraJsonObj.getString("del_address").trim().length() == 0) {
					reportMsgs.add("需提供取貨地址");
				} else {
					del_address = paraJsonObj.getString("del_address");
				}
				// 取貨人名
				String recipient = null;
				if(paraJsonObj.getString("mem_name") == null || paraJsonObj.getString("mem_name").trim().length() == 0) {
					reportMsgs.add("需提供取貨人名");
				} else {
					recipient = paraJsonObj.getString("mem_name");
				}
				// 付款方式 現金 現場取
				Integer pay_via = 0;
				if(type == 2) {
					// 組裝單
					pay_via = 2;
				} else if(paraJsonObj.getInt("pay_via") <= 0) {
					reportMsgs.add("需提供付款方式");
				} else {
					pay_via = paraJsonObj.getInt("pay_via");
				}
				
				if (!reportMsgs.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				}
				
				// 新增訂單
				b2cso_mainService.insert(
					type, buyer_id, status, delivery, 
					amount, del_address, recipient, pay_via);
				
				// 新增訂單詳情
				B2cso_detailService b2cso_detailService = new B2cso_detailService();
				JSONArray productList = paraJsonObj.getJSONArray("productList");
				if(productList.length() > 0) {
					for (int i = 0; i < productList.length(); i++) {
						b2cso_detailService.addDetail( 
							productList.getJSONObject(i).getInt("price"), 
							productList.getJSONObject(i).getString("pro_id"), 
							productList.getJSONObject(i).getInt("quality"), 
							b2cso_mainService.getLatestSoId()
						);
					}
				}
				
				// 非競標單
				if(type > 0 && type != 2) {
					HttpSession session = req.getSession();
					session.setAttribute("cartList", "");
				}
				
				reportMsgs.add("新增訂單成功");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("無法產生訂單:" + e.getMessage());
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 修改訂單
		if("updateOrder".equals(action)) {
			List<String> reportMsgs = new LinkedList<String>();
			try {
				String so_id = null;
				if(req.getParameter("so_id") == null || req.getParameter("so_id").trim().length() == 0) {
					reportMsgs.add("訂單編號不能為空");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				} else {
					so_id = req.getParameter("so_id");
				}
				String status = null;
				if(req.getParameter("status") == null || req.getParameter("status").trim().length() == 0) {
					reportMsgs.add("訂單狀態不能為空");
				} else {
					status = req.getParameter("status");
				}
				String recipient = null;
				if(req.getParameter("recipient") == null || req.getParameter("recipient").trim().length() == 0) {
					reportMsgs.add("收貨人姓名不能為空");
				} else {
					recipient = req.getParameter("status");
				}
				String del_address = null;
				if(req.getParameter("del_address") == null || req.getParameter("del_address").trim().length() == 0) {
					reportMsgs.add("地址不能為空");
				} else {
					del_address = req.getParameter("del_address");
				}
				Integer delivery = 0;
				if(req.getParameter("delivery") == null || req.getParameter("delivery").trim().length() == 0) {
					reportMsgs.add("收貨方式");
				} else {
					delivery = Integer.parseInt(req.getParameter("delivery"));
				}
				Integer pay_via = 0;
				if(req.getParameter("pay_via") == null || req.getParameter("pay_via").trim().length() == 0) {
					reportMsgs.add("收貨方式");
				} else {
					pay_via = Integer.parseInt(req.getParameter("pay_via"));
				}
				
				// 取得訂單資料
				B2cso_mainService bSvc = new B2cso_mainService();
				List<B2cso_mainVO> deptList = new ArrayList<B2cso_mainVO>();
				B2cso_mainVO bsoVO = bSvc.getOneB2csoMain(so_id);
				
				// 修改訂單狀態
				bsoVO.setStatus(status);
				bSvc.update(status, bsoVO.getAmount(), del_address, pay_via, so_id);
				
				JSONObject jsonObject = new JSONObject();
				reportMsgs.add("訂單修改成功");
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("無法修改訂單狀態:" + e.getMessage());
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 修改訂單狀態
		if ("changeStatus".equals(action)) {
			List<String> reportMsgs = new LinkedList<String>();
			
			try {
				String so_id = null;
				if(req.getParameter("so_id") == null || req.getParameter("so_id").trim().length() == 0) {
					reportMsgs.add("訂單編號不能為空");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				} else {
					so_id = req.getParameter("so_id");
				}
				String status = null;
				if(req.getParameter("status") == null || req.getParameter("status").trim().length() == 0) {
					reportMsgs.add("訂單狀態不能為空");
				} else {
					status = req.getParameter("status");
				}
				
				if (!reportMsgs.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				}
				
				// 取得訂單資料
				B2cso_mainService bSvc = new B2cso_mainService();
				List<B2cso_mainVO> deptList = new ArrayList<B2cso_mainVO>();
				B2cso_mainVO bsoVO = bSvc.getOneB2csoMain(so_id);
				
				// 修改訂單狀態
				bsoVO.setStatus(status);
				bSvc.update(bsoVO.getStatus(), bsoVO.getAmount(), bsoVO.getDel_address(), bsoVO.getPay_via(), bsoVO.getSo_id());
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", deptList);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("無法修改訂單狀態:" + e.getMessage());
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		out.flush();
		out.close();
	}
}
