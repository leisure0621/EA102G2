package com.front_end_b2cpro_main.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.*;
import java.io.*;

import java.util.*;

import com.front_end_b2cpro_main.model.*;
import com.front_end_b2cpro_main.model.front_B2cproMainVO;
import com.front_end_b2cpro_main.model.front_B2cproMainService;
import com.catagory.model.*;
import com.spec_main.model.*;


@MultipartConfig
public class FrontendB2cproMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontendB2cproMainServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			String pro_id = req.getParameter("pro_id");
			front_B2cproMainService bSvc = new front_B2cproMainService();
			front_B2cproMainVO proVO = bSvc.findByPrimaryKey(pro_id);
			ByteArrayInputStream bais = new ByteArrayInputStream(proVO.getPicture());
			if (bais.available() != 0) {
				BufferedInputStream bis = new BufferedInputStream(bais);
				byte[] buf = new byte[4 * 1024];
				int len;
				while ((len = bis.read(buf)) != -1) {
					out.write(buf, 0, len);

				}
				bis.close();
				bais.close();

			} else {
				InputStream is = getServletContext().getResourceAsStream("/back_end/b2cproduct/images/picNotFound.png");
				byte[] b = new byte[is.available()];
				is.read(b);
				out.write(b);
				is.close();

			}

		} catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/back_end/b2cproduct/images/picNotFound.png");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();

		}
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		
		
		if("getCat_For_Display".equals(action)) {
			System.out.println("進入");
			HttpSession session = req.getSession();
			session.removeAttribute("list");
			List<String> errorMsgs = new LinkedList<String>();
			
			List<front_B2cproMainVO> listCat= new ArrayList<front_B2cproMainVO>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String cat_id = req.getParameter("b2cCatpro");
				System.out.println("cat_id : " + cat_id);
				front_B2cproMainService b2cproMainSvc = new front_B2cproMainService();
				List<front_B2cproMainVO> listall = b2cproMainSvc.getAll();
				for(front_B2cproMainVO b2cproMainVO:listall) {
					String cat=b2cproMainVO.getCat_id();
					if(cat.equals(cat_id)) {
						listCat.add(b2cproMainVO);
					}
				}
				
				for(front_B2cproMainVO b2cproMainVO:listall) {
					System.out.println(b2cproMainVO.getPro_id());
					
				}
				System.out.println("----------");
				
				
				if (cat_id == null || cat_id.trim().length() == 0) {
					session.setAttribute("searchB2cproMain", "");
					session.setAttribute("listCat", null);
				}
				else {
					session.setAttribute("listCat", listCat);
					for(front_B2cproMainVO b2cproMainVO:listCat) {
						System.out.println(b2cproMainVO.getPro_id());
						
					}
				}
			}catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/b2cpro_main/b2c_Shop.jsp");
				failureView.forward(req, res);
			}
			
			
		}
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String pro_id = req.getParameter("pro_id");
				if (pro_id == null || (pro_id.trim()).length() == 0) {
					errorMsgs.add("請輸入商品編號");
				}
				/*************************** 2.開始查詢資料 *****************************************/
				front_B2cproMainService b2cproMainSvc = new front_B2cproMainService();
				front_B2cproMainVO b2cproMainVO = b2cproMainSvc.findByPrimaryKey(pro_id);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/b2cpro_main/b2c_Shop.jsp?whichPage=1");
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("b2cpromainVO", b2cproMainVO);
				String url = "/front_end/b2cpro_main/b2cShop_Detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/b2cpro_main/b2c_Shop.jsp?whichPage=1");
				failureView.forward(req, res);

			}
		}
		
		
		
	}

}
