package com.mem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.mail.model.MailService;
import com.mem.model.MemService;
import com.mem.model.MemVO;

public class MemServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		String action = null;
		String body = null;
		
		if(req.getParameter("action") != null) {
			action = req.getParameter("action");
		}
		if((body = req.getReader().readLine()) != null) {
			JSONObject paraJsonObj = new JSONObject(body);
			action = paraJsonObj.getString("action");
		}
		
		String emailReg = "^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
		String numReg = "[0-9]*";
		
		// 取得單一會員資料
		if ("getOne_For_Update".equals(action)) {

			List<String> reportMsgs = new LinkedList<String>();

			try {

				String mem_id = req.getParameter("mem_id");

				// 設定連線
				MemService memSvc = new MemService();
				// 取得單筆資料
				MemVO memVO = memSvc.getOneMem(mem_id);
				// 設定資料
				List<MemVO> map = new ArrayList<MemVO>();
				map.add(memVO);

				req.setAttribute("memVO", memVO);
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", map);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("無法取得會員資料:" + e.getMessage());
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}

		// 更新權限
		if ("update_authority".equals(action)) {
			// 設定錯誤紀錄
			List<String> reportMsgs = new LinkedList<String>();
			try {
				String mem_id = req.getParameter("mem_id");
				Integer authority = Integer.parseInt(req.getParameter("authority"));

				// 取得會員資料
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_id);
				// 更新權限
				memVO.setAuthority(authority);
				memSvc.updateMem(mem_id, memVO.getFirst_name(), memVO.getLast_name(), memVO.getNickname(),
						memVO.getTel(), memVO.getMob(), memVO.getEmail(), memVO.getPassword(), memVO.getShop_name(),
						memVO.getCredit_card(), memVO.getCredit_card_expires(), memVO.getCredit_card_cvc(),
						memVO.getBank_account(), memVO.getAddress(), authority);
				// 設定回傳訊息
				reportMsgs.add("修改成功");
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				// 紀錄錯誤信息
				reportMsgs.add("修改權限失敗:" + e.getMessage());
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}

		}

		// 更新會員資料
		if ("update".equals(action)) {
			
			List<String> reportMsgs = new LinkedList<String>();

			try {
				MemService memSvc = new MemService();
				
				String mem_id = req.getParameter("mem_id").trim();
				if (mem_id == null || req.getParameter("mem_id").trim().length() == 0) {
					reportMsgs.add("需登入");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				}
				String first_name = req.getParameter("first_name");
				if (first_name == null || first_name.trim().length() == 0) {
					reportMsgs.add("姓氏不能為空");
				}
				String last_name = req.getParameter("last_name");
				if (last_name == null || last_name.trim().length() == 0) {
					reportMsgs.add("名稱不能為空");
				}
				String nickname = req.getParameter("nickname");
				String tel = req.getParameter("tel");
				if ((tel != null || tel.trim().length() != 0) && !tel.trim().matches(numReg)) {
					reportMsgs.add("電話號碼只能為數字");
				}
				String mob = req.getParameter("mob");
				if ((mob != null || mob.trim().length() != 0) && !mob.trim().matches(numReg)) {
					reportMsgs.add("手機號碼只能為數字");
				}
				String email = req.getParameter("email");
				if (email == null || email.trim().length() == 0) {
					reportMsgs.add("Email 不能為空");
				} else if(!email.trim().matches(emailReg)){
					reportMsgs.add("Email 格式須為 test@test.com");
				}
				String password = req.getParameter("password");
				if (password == null || password.trim().length() == 0) {
					reportMsgs.add("密碼不能為空");
				}
				String shop_name = req.getParameter("shop_name");
				String credit_card = req.getParameter("credit_card");
				if ((credit_card != null || credit_card.trim().length() != 0) && !credit_card.trim().matches(numReg)) {
					reportMsgs.add("信用卡號只能為數字");
				}
				java.sql.Date credit_card_expires = java.sql.Date.valueOf(req.getParameter("credit_card_expires"));
				Integer credit_card_cvc = new Integer(req.getParameter("credit_card_cvc"));
				if ((credit_card_cvc != null || req.getParameter("credit_card_cvc").trim().length() != 0) && !credit_card.trim().matches(numReg)) {
					reportMsgs.add("信用卡號安全碼只能為數字");
				}
				String bank_account = req.getParameter("bank_account");
				if ((bank_account != null || bank_account.trim().length() != 0) && !bank_account.trim().matches(numReg)) {
					reportMsgs.add("銀行卡號只能為數字");
				}
				String address = req.getParameter("address");
				Integer authority = new Integer(req.getParameter("authority"));
				if (req.getParameter("authority") == null || req.getParameter("authority").trim().length() == 0) {
					reportMsgs.add("權限不能為空");
				}
				
				MemVO memVO = new MemVO();
				memVO.setMem_id(mem_id);
				memVO.setFirst_name(first_name);
				memVO.setLast_name(last_name);
				memVO.setNickname(nickname);
				memVO.setTel(tel);
				memVO.setMob(mob);
				memVO.setEmail(email);
				memVO.setPassword(password);
				memVO.setShop_name(shop_name);
				memVO.setCredit_card(credit_card);
				memVO.setCredit_card_expires((java.sql.Date) credit_card_expires);
				memVO.setCredit_card_cvc(credit_card_cvc);
				memVO.setBank_account(bank_account);
				memVO.setAddress(address);
				memVO.setAuthority(authority);

				if (!reportMsgs.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				}

				memVO = memSvc.updateMem(mem_id, first_name, last_name, nickname, tel, mob, email, password, shop_name,
						credit_card, credit_card_expires, credit_card_cvc, bank_account, address, authority);

				reportMsgs.add("修改成功");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("修改資料失敗:" + e.getMessage());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}

		// 新增
		if ("insert".equals(action)) {

			List<String> reportMsgs = new LinkedList<String>();
			HttpSession session = req.getSession();
			
			try {
				MemService memSvc = new MemService();
				MemVO memVO = new MemVO();				
				
				String first_name = req.getParameter("first_name");
				if (first_name == null || first_name.trim().length() == 0) {
					reportMsgs.add("姓氏不能為空");
				}
				String last_name = req.getParameter("last_name");
				if (last_name == null || last_name.trim().length() == 0) {
					reportMsgs.add("名稱不能為空");
				}
				String nickname = req.getParameter("nickname");
				String tel = req.getParameter("tel");
				if ((tel != null || tel.trim().length() != 0) && !tel.trim().matches(numReg)) {
					reportMsgs.add("電話號碼只能為數字");
				}
				String mob = req.getParameter("mob");
				if ((mob != null || mob.trim().length() != 0) && !mob.trim().matches(numReg)) {
					reportMsgs.add("手機號碼只能為數字");
				}
				String email = req.getParameter("email");
				if (email == null || email.trim().length() == 0) {
					reportMsgs.add("Email 不能為空");
				} else if(!email.trim().matches(emailReg)){
					reportMsgs.add("Email 格式須為 test@test.com");
				} else if(memSvc.getEmails(email).size() > 0 && !"true".equals(req.getParameter("isFB"))) {
					// 有帳號 非FB登入
					reportMsgs.add("已有此帳號，請直接登入");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				} else if (memSvc.getEmails(email).size() > 0 && "true".equals(req.getParameter("isFB"))) {
					String memid = memSvc.getEmails(email).get(0).getMem_id();
					// 有帳號 且為FB登入
					session.setAttribute("memVO", memSvc.getOneMem(memid));
					reportMsgs.add("登入成功");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("data", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				}
				String shop_name = req.getParameter("shop_name");
				String credit_card = req.getParameter("credit_card");
				if ((credit_card != null || credit_card.trim().length() != 0) && !credit_card.trim().matches(numReg)) {
					reportMsgs.add("信用卡號只能為數字");
				}
				java.sql.Date credit_card_expires = java.sql.Date.valueOf(req.getParameter("credit_card_expires"));
				Integer credit_card_cvc = new Integer(req.getParameter("credit_card_cvc"));
				String bank_account = req.getParameter("bank_account");
				if ((credit_card != null || credit_card.trim().length() != 0) && !credit_card.trim().matches(numReg)) {
					reportMsgs.add("銀行卡號只能為數字");
				}
				String address = req.getParameter("address");
				Integer authority = new Integer(req.getParameter("authority"));
				
				memVO.setFirst_name(first_name);
				memVO.setLast_name(last_name);
				memVO.setNickname(nickname);
				memVO.setTel(tel);
				memVO.setMob(mob);
				memVO.setEmail(email);
				memVO.setShop_name(shop_name);
				memVO.setCredit_card(credit_card);
				memVO.setCredit_card_expires((java.sql.Date) credit_card_expires);
				memVO.setCredit_card_cvc(credit_card_cvc);
				memVO.setBank_account(bank_account);
				memVO.setAddress(address);
				memVO.setAuthority(authority);

				if (!reportMsgs.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				}


				memSvc.addMem(first_name, last_name, nickname, tel, mob, email, shop_name, credit_card,
						credit_card_expires, credit_card_cvc, bank_account, address, authority);
				
				// FB 登入註冊
				if("true".equals(req.getParameter("isFB"))) {
					String memid = memSvc.getEmails(email).get(0).getMem_id();
					session.setAttribute("memVO", memSvc.getOneMem(memid));
				}
				// 非FB登入 發送註冊成功信件
				else {
					MailService mailService = new MailService();
					String subject = "恭喜您註冊成功!";
					String messageText = "Hello! " + (memVO.getFirst_name() + memVO.getLast_name()) + " 請謹記此密碼: " + memSvc.getEmails(email).get(0).getPassword() + "\n" +" (已經啟用)";
					mailService.sendMail(memVO.getEmail(), subject, messageText);
				}
				
				// 回傳 json
				reportMsgs.add("註冊成功");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				session.setAttribute("memVO", "");
				reportMsgs.add("新增資料失敗 "+e.getMessage());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 登出
		if ("logout".equals(action)) {
			
			List<String> reportMsgs = new LinkedList<String>();
			HttpSession session = req.getSession();
			
			try {
				session.invalidate();
				JSONObject jsonObject = new JSONObject();
				reportMsgs.add("登出成功");
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}catch(Exception e) {
				reportMsgs.add("登出失敗 "+e.getMessage());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 忘記密碼
		if ("forget".equals(action)) {
			
			List<String> reportMsgs = new LinkedList<String>();
			
			try {
				String email = req.getParameter("email");
				if (email == null || email.trim().length() == 0) {
					reportMsgs.add("Email 不能為空");
				}
				
				if (!reportMsgs.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				}
				
				// 發送註冊成功信件
				MemService memSvc = new MemService();
				MemVO memVO = new MemVO();
				memVO = memSvc.getEmails(email).get(0);
				MailService mailService = new MailService();
				String subject = "這是您的密碼，請小心保存";
				String messageText = "Hello! " + (memVO.getFirst_name() + memVO.getLast_name()) + " 請謹記此密碼: " + memVO.getPassword(); 

				mailService.sendMail(memVO.getEmail(), subject, messageText);
				
				JSONObject jsonObject = new JSONObject();
				reportMsgs.add("信件已發送");
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}catch(Exception e) {
				reportMsgs.add("信件發送失敗 "+e.getMessage());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 登入
		if ("login".equals(action)) {
			
			List<String> reportMsgs = new LinkedList<String>();
			HttpSession session = req.getSession();
			
			MemService memSvc = new MemService();
			MemVO memVO = new MemVO();
			
			try {
				String isFB = req.getParameter("isFB");
				
				// Email
				String email = req.getParameter("email");
				if (email == null || email.trim().length() == 0) {
					reportMsgs.add("Email 不能為空");
				} else if(!email.trim().matches(emailReg)){
					reportMsgs.add("Email 格式須為 test@test.com");
				} else if(memSvc.getEmails(email).size() <= 0) {
					reportMsgs.add("無此帳號，請註冊新會員");
				}
				
				// Password
				String password = req.getParameter("password");
				if (!"true".equals(isFB) && (password == null || password.trim().length() == 0)) {
					reportMsgs.add("密碼不能為空");
				}
				
				// 回傳錯誤信息
				if (!reportMsgs.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				}
				
				memVO = memSvc.getEmails(email).get(0);
				
				// 登入成功
				if( ("true".equals(isFB) && memVO.getMem_id().length() > 0) || 
					(!"true".equals(isFB) && memVO.getPassword().equals(password))) {
					
					session.setAttribute("memVO", memVO);
					reportMsgs.add("登入成功");
					
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("data", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
				}
				// 登入失敗
				else {
					reportMsgs.add("帳號密碼錯誤，請重新輸入");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("data", reportMsgs);
					jsonObject.put("status", 200);
					out.print(jsonObject);
					return;
				}
			}catch(Exception e) {
				reportMsgs.add("登入失敗 "+ e.getMessage());
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}

		// 刪除
		if ("delete".equals(action)) {
			
			List<String> reportMsgs = new LinkedList<String>();

			try {

				String mem_id = req.getParameter("mem_id");
				MemService memSvc = new MemService();
				memSvc.deleteMem(mem_id);
				
				reportMsgs.add("刪除成功");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("刪除資料失敗:" + e.getMessage());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 搜尋
		if ("search".equals(action)) {
			
			List<String> reportMsgs = new LinkedList<String>();
			try {
				// 將資料存至 session
				HttpSession session = req.getSession();
				String query = req.getParameter("query");
				if (query == null || query.trim().length() == 0) {
					session.setAttribute("memSearch", "");
				}
				else {
					session.setAttribute("memSearch", query);
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
		}
		
		out.flush();
		out.close();
	}
}
