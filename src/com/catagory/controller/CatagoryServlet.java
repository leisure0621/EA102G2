package com.catagory.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catagory.model.*;
import com.spec_main.model.SpecMainVO;
import com.b2cpro_main.model.*;

@WebServlet("/back_end/catagory/cat.do")
public class CatagoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CatagoryServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) {
			CatagoryService cSvc = new CatagoryService();
			List<String> errorMsgs = new LinkedList<String>();
			List<String> catNames = new ArrayList<String>();
			List<CatagoryVO> catList = new LinkedList<CatagoryVO>();

			catList = cSvc.getAll();
			for (CatagoryVO temp : catList)
				catNames.add(temp.getCat_des());
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String cat_des = req.getParameter("cat_des").trim();
				if (cat_des == null || cat_des.trim().length() == 0) {
					errorMsgs.add("名稱不可為空白");
				} else if (catNames.contains((String) cat_des)) {
					errorMsgs.add("資料庫已有重複之類別");
				}
				CatagoryVO catVO = new CatagoryVO();
				catVO.setCat_des(cat_des);
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("catVO", catVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/addCatagory.jsp");
					failureView.forward(req, res);
					return;
				}

				cSvc.insert(catVO);
				String url = "/back_end/catagory/listAllCatagory.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/addCatagory.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOneForUpdate".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				CatagoryService cSvc = new CatagoryService();
				String cat_id = req.getParameter("cat_id").trim();
				CatagoryVO originCatVO = new CatagoryVO();
				originCatVO = cSvc.findByPrimaryKey(cat_id);
				req.setAttribute("originCatVO", originCatVO);
				String url = "/back_end/catagory/updateCatagory.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("發生錯誤 " + e.getMessage());
				String url = "/back_end/catagory/updateCatagory.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}

		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			String originalCatId = req.getParameter("originalCatId").trim();
			CatagoryService cSvc = new CatagoryService();

			List<CatagoryVO> catList = new LinkedList<CatagoryVO>();
			catList = cSvc.getAll();

			List<String> catNames = new ArrayList<String>();

			CatagoryVO originCatVO = new CatagoryVO();
			originCatVO = cSvc.findByPrimaryKey(originalCatId);

			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("originCatVO", originCatVO);

			for (CatagoryVO temp : catList)
				catNames.add(temp.getCat_des());

			try {
				String cat_des = req.getParameter("cat_des").trim();
				if (cat_des == null || cat_des.trim().length() == 0) {
					errorMsgs.add("分類名稱不能為空白");
				} else if (cat_des.equals(originCatVO.getCat_des())) {
					errorMsgs.add("與原名稱相同");
				} else if (catNames.contains(cat_des)) {
					errorMsgs.add("資料庫已有重複之類別");
				}

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("cat_des", cat_des);
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/updateCatagory.jsp");
					failureView.forward(req, res);
					return;
				}
				CatagoryVO catVO = new CatagoryVO();
				catVO.setCat_id(originCatVO.getCat_id());
				catVO.setCat_des(cat_des);
				cSvc.update(catVO);

				String url = "/back_end/catagory/listAllCatagory.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/updateCatagory.jsp");
				failureView.forward(req, res);
			}

		}
		if ("listProByCat".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String cat_id = req.getParameter("cat_id");
			if (cat_id == null || cat_id.trim().length() == 0) {
				errorMsgs.add("請選擇一個分類");
				String url = "/back_end/catagory/CatIndex.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			try {
				CatagoryService cSvc = new CatagoryService();
				CatagoryVO catVO = cSvc.findByPrimaryKey(cat_id);

				Set<B2cproMainVO> pros = new LinkedHashSet<B2cproMainVO>();
				pros = cSvc.getB2cprosByCatId(cat_id);

				req.setAttribute("catVO", catVO);
				req.setAttribute("pros", pros);

				String url = "/back_end/catagory/listProByCat.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("點選的資料已不存在，請重新整理" + e.getMessage());
				String url = "/back_end/catagory/listAllCatagory.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}

		}
		if ("listSpecByCat".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String cat_id = req.getParameter("cat_id");
			if (cat_id.equals("-1")) {
				errorMsgs.add("請選擇一個商品分類");
				String url = "/back_end/spec/SpecIndex.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			try {
				CatagoryService cSvc = new CatagoryService();
				Set<SpecMainVO> catSpecs = new LinkedHashSet<SpecMainVO>();
				CatagoryVO catVO = new CatagoryVO();
				catVO = cSvc.findByPrimaryKey(cat_id);
				catSpecs = cSvc.getSpecsByCatId(cat_id);
				req.setAttribute("catVO", catVO);
				req.setAttribute("catSpecs", catSpecs);
				String url = "/back_end/catagory/listSpecByCat.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("點選的資料已不存在，請重新整理 " + e.getMessage());
				String url = "/back_end/catagory/CatIndex.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
		}
	}
}
