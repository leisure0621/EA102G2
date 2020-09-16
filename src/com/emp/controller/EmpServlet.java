package com.emp.controller;

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.dept.model.DeptService;
import com.dept.model.DeptVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.emp_auth.model.EmpAuthService;
import com.emp_auth.model.EmpAuthVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;

public class EmpServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");
		
		String yymmddReg = "^\\d{4}[\\-/\\.](0?[1-9]|1[012])[\\-/\\.](0?[1-9]|[12][0-9]|3[01])$";
		String numReg = "[0-9]*";
		
		// 新增
		if ("insert".equals(action)) {
			List<String> reportMsgs = new LinkedList<String>();
			req.setAttribute("reportMsgs", reportMsgs);
			
			try {
				
				EmpService empSvc = new EmpService();
				String emp_firstname = null;
				if(req.getParameter("emp_firstname") == null || req.getParameter("emp_firstname").trim().length() == 0) {
					reportMsgs.add("姓氏不能為空");
				} else {
					emp_firstname = req.getParameter("emp_firstname");
				}
				String emp_lastname = null;
				if(req.getParameter("emp_lastname") == null || req.getParameter("emp_lastname").trim().length() == 0) {
					reportMsgs.add("名稱不能為空");
				} else {
					emp_lastname = req.getParameter("emp_lastname");
				}
				String dept_no = null;
				if(req.getParameter("dept_no") == null || req.getParameter("dept_no").trim().length() == 0) {
					reportMsgs.add("部門不能為空");
				} else {
					dept_no = req.getParameter("dept_no");
				}
				Integer salary = null;
				if (req.getParameter("salary") == null || req.getParameter("salary").trim().length() == 0) {
					reportMsgs.add("員工薪水不能為空");
				} else if(!req.getParameter("salary").trim().matches(numReg)) {
					reportMsgs.add("員工薪水需輸入數字");
				} else {
					salary = Integer.parseInt(req.getParameter("salary"));
				}
				java.sql.Date hiredate = null;
				if(req.getParameter("hiredate") == null || req.getParameter("hiredate").toString().trim().length() == 0) {
					reportMsgs.add("僱用日期不能為空");
				} else if(!req.getParameter("hiredate").trim().matches(yymmddReg)) {
					reportMsgs.add("僱用日期只能為YYYY年DD月MM日");
				} else {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate"));
				}
				
				if (!reportMsgs.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 400);
					out.print(jsonObject);
					return;
				}
				
				EmpVO empVO = new EmpVO();
				empVO.setEmp_firstname(emp_firstname);
				empVO.setEmp_lastname(emp_lastname);
				empVO.setDept_no(dept_no);
				empVO.setSalary(salary);
				empVO.setHiredate(hiredate);
				empVO.setStatus(1);
				
				// 新增員工
				empSvc.addEmp(empVO.getEmp_firstname(), empVO.getEmp_lastname(), empVO.getDept_no(), 
						empVO.getSalary(), empVO.getHiredate(), empVO.getStatus());
				// 新增權限
				EmpAuthService empAuthService = new EmpAuthService();
				String[] multi_auth = null;
				// 新增權限
				if(req.getParameterValues("multiAuth[]") != null) {
					multi_auth = req.getParameterValues("multiAuth[]");
					for (int i = 0; i < multi_auth.length; i++) {
						empAuthService.addEmpAuth(empSvc.getLatestEmp(), multi_auth[i]);
					}
				}
				
				reportMsgs.add("新增員工成功");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}catch(Exception e) {
				reportMsgs.add("新增員工失敗 "+e.getMessage());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 修改
		if ("update".equals(action)) {
			List<String> reportMsgs = new LinkedList<String>();
			req.setAttribute("reportMsgs", reportMsgs);
			try {
				String emp_id = req.getParameter("emp_id");
				// 取得員工
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_id);
				// 更新員工
				String emp_firstname = null;
				if(req.getParameter("emp_firstname") == null || req.getParameter("emp_firstname").trim().length() == 0) {
					emp_firstname = empVO.getEmp_firstname();
				} else {
					emp_firstname = req.getParameter("emp_firstname");
				}
				String emp_lastname = null;
				if(req.getParameter("emp_lastname") == null || req.getParameter("emp_lastname").trim().length() == 0) {
					emp_lastname = empVO.getEmp_lastname();
				} else {
					emp_lastname = req.getParameter("emp_lastname");
				}
				String dept_no = null;
				if(req.getParameter("dept_no") == null || req.getParameter("dept_no").trim().length() == 0) {
					dept_no = empVO.getDept_no();
				} else {
					dept_no = req.getParameter("dept_no");
				}
				Integer salary = null;
				if(!req.getParameter("salary").trim().matches(numReg)) {
					reportMsgs.add("員工薪水需輸入數字");
				} else {
					salary = Integer.parseInt(req.getParameter("salary"));
				}
				java.sql.Date hiredate = null;
				if(req.getParameter("hiredate") == null || req.getParameter("hiredate").toString().trim().length() == 0) {
					hiredate = empVO.getHiredate();
				} else if(!req.getParameter("hiredate").trim().matches(yymmddReg)) {
					reportMsgs.add("僱用日期只能為YYYY年DD月MM日");
				} else {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate"));
				}
				
				if (!reportMsgs.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 400);
					out.print(jsonObject);
					return;
				}
				
				empSvc.updateEmp(
						emp_id,emp_firstname, 
						emp_lastname, dept_no, 
						salary, hiredate, 
						empVO.getPassword(), empVO.getStatus());
				
				// 權限管理
				EmpAuthService empAuthService = new EmpAuthService();
				String[] multi_auth = null;
				// 清空權限
				List<EmpAuthVO> empAuthList = empAuthService.getOneEmpAuth(emp_id);
				for (EmpAuthVO empAuthVO : empAuthList) {
					empAuthService.delete(emp_id, empAuthVO.getAuth_id());
				}
				// 新增權限
				if(req.getParameterValues("multiAuth[]") != null) {
					multi_auth = req.getParameterValues("multiAuth[]");
					for (int i = 0; i < multi_auth.length; i++) {
						empAuthService.addEmpAuth(emp_id, multi_auth[i]);
					}
				}			
				
				// 回傳結果
				reportMsgs.add("修改成功");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("更新狀態失敗:" + e.getMessage());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 登入帳號
		if ("login".equals(action)) {
			List<String> reportMsgs = new LinkedList<String>();
			req.setAttribute("reportMsgs", reportMsgs);
			try {
				String emp_id = req.getParameter("emp_id");
				String emp_password = req.getParameter("emp_password");

				// 取得員工
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_id);
				List<EmpVO> list = new ArrayList<EmpVO>();
				list.add(empVO);
				// 取得權限
				EmpAuthService empAuthService = new EmpAuthService();
				List<EmpAuthVO> empAuthVO = empAuthService.getOneEmpAuth(emp_id);
				
				// 密碼不匹配
				if(!emp_password.equals(empVO.getPassword())) {
					reportMsgs.add("密碼輸入錯誤，請重新輸入");
				// 帳號無權限
				} else if(empVO.getStatus() == 0) {
					reportMsgs.add("已離職");
				}
				
				if (!reportMsgs.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("err", reportMsgs);
					jsonObject.put("status", 400);
					out.print(jsonObject);
					return;
				}
				
				// 成功 將資料存至 session
				HttpSession session = req.getSession();
				session.setAttribute("empVO", empVO);
				session.setAttribute("empAuthVO", empAuthVO);
				// 回傳結果
				reportMsgs.add("歡迎登入");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);				
			} catch (Exception e) {
				reportMsgs.add("無法取得帳號資料:" + e.getMessage());
				// 回傳結果
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
		
		// 取得單一員工資料 可改為清空資料重新給予
		if ("getOne_For_Update".equals(action)) {

			List<String> reportMsgs = new LinkedList<String>();
			req.setAttribute("reportMsgs", reportMsgs);

			try {
				Map map = new HashMap();
				String emp_id = req.getParameter("emp_id");

				// 取得員工
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_id);
				List<EmpVO> list = new ArrayList<EmpVO>();
				list.add(empVO);
				// 取得權限
				EmpAuthService empAuthService = new EmpAuthService();
				List<EmpAuthVO> empAuthVO = empAuthService.getOneEmpAuth(emp_id);
				// 取得部門名稱
				DeptService deptService = new DeptService();
				List<DeptVO> deptList = new ArrayList<DeptVO>();
				deptList.add(deptService.getOneDept(empVO.getDept_no()));
				// 組成 dataObject
			    map.put("emp", list);
			    map.put("emp_auth", empAuthVO);
			    map.put("dept", deptList);
			    
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", map);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("無法取得要修改的資料:" + e.getMessage());
				// 回傳結果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 修改權限
		if ("update_status".equals(action)) {
			List<String> reportMsgs = new LinkedList<String>();
			req.setAttribute("reportMsgs", reportMsgs);
			
			try {
				String emp_id = req.getParameter("emp_id");
				Integer status = Integer.parseInt(req.getParameter("status"));
				// 取得員工資料
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_id);
				// 更新員工狀態 在職/離職
				empSvc.updateEmp(
						emp_id, empVO.getEmp_firstname(), 
						empVO.getEmp_lastname(), empVO.getDept_no(), 
						empVO.getSalary(), empVO.getHiredate(), 
						empVO.getPassword(), status);
				if(status == 0) {
					// 刪除
					EmpAuthService empAuthService = new EmpAuthService();
					List<EmpAuthVO> empAuthList = empAuthService.getOneEmpAuth(emp_id);
					for (EmpAuthVO empAuthVO : empAuthList) {
						empAuthService.delete(emp_id, empAuthVO.getAuth_id());
					}
				}
				// 回傳狀態
				reportMsgs.add("更新成功");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			} catch (Exception e) {
				reportMsgs.add("更新權限失敗:" + e.getMessage());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("err", reportMsgs);
				jsonObject.put("status", 200);
				out.print(jsonObject);
			}
		}
		
		// 搜尋
		if ("search".equals(action)) {
			
			List<String> reportMsgs = new LinkedList<String>();
			req.setAttribute("reportMsgs", reportMsgs);
			
			try {
				
				// 將資料存至 session
				HttpSession session = req.getSession();
				String query = req.getParameter("query");
				if (query == null || query.trim().length() == 0) {
					session.setAttribute("empSearch", "");
				}
				else {
					session.setAttribute("empSearch", query);
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
