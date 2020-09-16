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
import javax.servlet.http.Part;
import javax.servlet.*;
import java.io.*;

import java.util.*;

import com.b2cpro_main.model.*;
import com.catagory.model.*;
import com.pro_spec.model.ProSpecService;
import com.pro_spec.model.ProSpecVO;
import com.spec_detail.model.SpecDetailService;
import com.spec_detail.model.SpecDetailVO;
import com.spec_main.model.*;

@WebServlet("/showPic.do")
@MultipartConfig
public class ShowPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowPic() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			String pro_id = req.getParameter("pro_id");
			B2cproMainService bSvc = new B2cproMainService();
			B2cproMainVO proVO = bSvc.findByPrimaryKey(pro_id);
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
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	doGet(req,res);
	}
}
