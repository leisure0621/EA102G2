package com.b2cpro_main.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.catagory.model.*;
import com.spec_main.model.*;
import java.util.*;

@WebServlet("/back_end/b2cproduct/B2cproAjax.do")
public class B2cproMainAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public B2cproMainAjax() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		String cat_id = req.getParameter("cat_id");
		CatagoryService cSvc = new CatagoryService();
		JSONArray array = new JSONArray();
		if ("getSelect".equals(action)) {

			Set<SpecMainVO> set = null;
			if (cat_id != null && cat_id.trim().length() != 0) {
				set = new LinkedHashSet<SpecMainVO>();
				set = cSvc.getSpecsByCatId(cat_id);
			}
			for (SpecMainVO spec : set) {
				JSONObject obj = new JSONObject();
				obj.put("spec_id", spec.getSpec_id());
				obj.put("spec_des", spec.getSpec_des());
				array.put(obj);
			}
		}

		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(array.toString());
		out.flush();
		out.close();

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
