package com.compatibility.controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import com.catagory.model.*;
import com.compatibility.model.*;
import com.spec_main.model.*;
import com.spec_detail.model.*;

@WebServlet("/back_end/compatibility/comp.do")
public class CompatibilityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CompatibilityServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");

		CatagoryService cSvc = new CatagoryService();
		SpecMainService smSvc = new SpecMainService();
		JSONArray array = new JSONArray();
		if ("getCat".equals(action)) {
			String cat_id = req.getParameter("cat_id");
			Set<SpecMainVO> set = null;

			set = new LinkedHashSet<SpecMainVO>();
			set = cSvc.getSpecsByCatId(cat_id);

			for (SpecMainVO spec : set) {
				JSONObject obj = new JSONObject();
				obj.put("spec_id", spec.getSpec_id());
				obj.put("spec_des", spec.getSpec_des());
				array.put(obj);
			}
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}

		if ("getSpec".equals(action)) {
			Set<SpecDetailVO> set = null;
			String spec_id = req.getParameter("spec_id");
			set = new LinkedHashSet<SpecDetailVO>();
			set = smSvc.getSpecDBySpecId(spec_id);

			for (SpecDetailVO specD : set) {
				JSONObject obj = new JSONObject();
				obj.put("specdet_id", specD.getSpecdet_id());
				obj.put("detail_des", specD.getDetail_des());
				array.put(obj);
			}
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}

		if ("addComp".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String specDet_id1 = req.getParameter("specdet_id1");
				String specDet_id2 = req.getParameter("specdet_id2");
				CompatibilityVO cVO = new CompatibilityVO();
				cVO.setSpecDet_id1(specDet_id1);
				cVO.setSpecDet_id2(specDet_id2);
				CompatibilityService compSvc = new CompatibilityService();
				compSvc.insert(cVO);
				
				String url = "/back_end/compatibility/listAllComp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				String url = "/back_end/compatiblity/addComp.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);

			}
		}

	}

}
