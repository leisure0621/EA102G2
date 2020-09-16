package com.b2cpro_main.controller;

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

@WebServlet("/back_end/b2cproduct/b2cpro.do")
@MultipartConfig
public class B2cproMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public B2cproMainServlet() {
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
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("addProToCat".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			CatagoryService cSvc = new CatagoryService();
			B2cproMainVO proVO = new B2cproMainVO();

			Set<SpecMainVO> catSpecs = new LinkedHashSet<SpecMainVO>();
			try {
				String cat_id = req.getParameter("cat_id").trim();
				proVO.setCat_id(cat_id);
				catSpecs = cSvc.getSpecsByCatId(cat_id);

				req.setAttribute("proVO", proVO);
				req.setAttribute("catSpecs", catSpecs);

				RequestDispatcher successView = req.getRequestDispatcher("/back_end/b2cproduct/addPro.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得欲新增的商品分類頁面" + e.getMessage());
				String cat_id = req.getParameter("cat_id").trim();

				Set<B2cproMainVO> pros = new LinkedHashSet<B2cproMainVO>();
				pros = cSvc.getB2cprosByCatId(cat_id);
				req.setAttribute("pros", pros);

				CatagoryVO catVO = cSvc.findByPrimaryKey(cat_id);
				req.setAttribute("catVO", catVO);

				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/listProByCat.jsp");
				failureView.forward(req, res);
			}

		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {

				B2cproMainService bSvc = new B2cproMainService();
				SpecMainService smSvc = new SpecMainService();
				ProSpecService psSvc = new ProSpecService();
				SpecDetailService sdSvc = new SpecDetailService();
				List<String> proNames = new LinkedList<String>();
				for (B2cproMainVO aPro : bSvc.getAll()) {
					proNames.add(aPro.getPro_name());
				}

				String pro_name = req.getParameter("pro_name");
				if (pro_name == null || pro_name.trim().length() == 0) {
					errorMsgs.add("產品名稱不可為空");
				} else if (proNames.contains(pro_name)) {
					errorMsgs.add("已有同樣產品名稱");
				}
				String cat_id = req.getParameter("cat_id");
				if (cat_id == null || cat_id.trim().length() == 0) {
					errorMsgs.add("商品分類不可為空");
				} else if (cat_id.equals("0")) {
					errorMsgs.add("請選擇一個商品分類");
				}
				byte[] picture = null;
				Part part = req.getPart("picture");
				if (part == null || part.getSize() == 0) {
					errorMsgs.add("請上傳一張圖片");
				}
				Integer rrp = null;
				try {
					rrp = new Integer(req.getParameter("rrp").trim());
					if (rrp <= 0) {
						errorMsgs.add("建議售價必須為正整數");
					}
				} catch (NumberFormatException e) {
					rrp = 0;
					errorMsgs.add("建議售價必須為正整數");
				}
				Integer stock = 0;
				try {
					stock = new Integer(req.getParameter("stock").trim());
					if (stock < 0) {
						errorMsgs.add("庫存必須大於零");
					}
				} catch (NumberFormatException e) {
					stock = 0;
					errorMsgs.add("庫存必須為正整數");
				}

				String vendor_id = req.getParameter("vendor_id");
				if (vendor_id == null || vendor_id.trim().length() == 0) {
					errorMsgs.add("請選擇廠商");
				}
				Integer status = null;

				try {
					status = Integer.parseInt(req.getParameter("status").trim());
					if (!(status == 0 || status == 1)) {
						status = 0;
						errorMsgs.add("請選擇上架或是下架");
					}
				} catch (NumberFormatException e) {
					errorMsgs.add("請選擇商品狀態");
				}
				String pro_des = req.getParameter("pro_des");

				B2cproMainVO proVO = new B2cproMainVO();
				proVO.setPro_name(pro_name);
				proVO.setCat_id(cat_id);

				InputStream is = part.getInputStream();
				picture = new byte[is.available()];
				is.read(picture);
				is.close();

				CatagoryService cSvc = new CatagoryService();

				proVO.setPicture(picture);

				proVO.setRrp(rrp);
				proVO.setStock(stock);
				proVO.setVendor_id(vendor_id);
				proVO.setStatus(status);
				proVO.setPro_des(pro_des);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("proVO", proVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/b2cproduct/addPro.jsp");
					failureView.forward(req, res);
					return;
				}

				String pro_id = bSvc.insertWithProId(proVO);
				String dir = getServletContext().getRealPath("/back_end/b2cproduct/images");
				String fileName = pro_id + ".png";
				part.write(dir + "/" + fileName);

				// 傳入Spec群
				List<ProSpecVO> proSpecs = new LinkedList<ProSpecVO>();
				// 取出一個cat中的spec
				// Category中的specs
				Set<SpecMainVO> catSpecs = cSvc.getSpecsByCatId(proVO.getCat_id());

				// 將更改後的specID和detail_des存入specMap中
				Map<String, String> specMap = new LinkedHashMap<String, String>();
				for (SpecMainVO smVO : catSpecs) {
					String getSpecD = req.getParameter(smVO.getSpec_id());
					specMap.put(smVO.getSpec_id().trim(), getSpecD.trim());
				}

				for (Map.Entry<String, String> entry : specMap.entrySet()) {
					// 在loop中先做一個在spec中所有specDetail的表
					Set<SpecDetailVO> set = smSvc.getSpecDBySpecId(entry.getKey());

					Set<String> detailDess = new LinkedHashSet<String>();
					// 先比對detail_des有無一樣的值，若有就取出相應的specdet_id輸入
					for (SpecDetailVO sdVO : set) {
						// 將所有的detail_des加入detailDess中
						detailDess.add(sdVO.getDetail_des());
						// 若在該detail list中有找到和detail一樣的值
						if (sdVO.getDetail_des().equals(entry.getValue())) {
							// 不新增spec_detail，加入proSpec的list中
							ProSpecVO psVO = new ProSpecVO();
							psVO.setPro_id(pro_id);
							psVO.setSpecDet_id(sdVO.getSpecdet_id());
							proSpecs.add(psVO);
						}
					}
					// 若detailDess中不包含他輸入的值
					if (entry.getValue().length() != 0 && !detailDess.contains(entry.getValue())) {
						// 就新增一個specDetail
						SpecDetailVO sdVO = new SpecDetailVO();
						sdVO.setSpec_id(entry.getKey());
						sdVO.setDetail_des(entry.getValue());
						String specDet_id = sdSvc.insertWithPK(sdVO);
						// 一樣加入proSpec的list中
						ProSpecVO psVO = new ProSpecVO();
						psVO.setPro_id(pro_id);
						psVO.setSpecDet_id(specDet_id);
						proSpecs.add(psVO);
					}
				}
				// 測測看
//				for (ProSpecVO psVO : list) {
//					System.out.println(psVO.getPro_id());
//					System.out.println(psVO.getSpecDet_id());
//				}
				// 將原本的proSpec刪除，並用forEachLoop將值依序加入
				for (ProSpecVO psVO : proSpecs) {
					psSvc.insert(psVO);
				}

				// 轉交
				CatagoryVO catVO = cSvc.findByPrimaryKey(cat_id);

				Set<B2cproMainVO> pros = new LinkedHashSet<B2cproMainVO>();
				pros = cSvc.getB2cprosByCatId(cat_id);

				req.setAttribute("catVO", catVO);
				req.setAttribute("pros", pros);
				RequestDispatcher successView = req.getRequestDispatcher("/back_end/catagory/listProByCat.jsp"); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/b2cproduct/addPro.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOneForUpdate".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String pro_id = req.getParameter("pro_id");
			B2cproMainService bSvc = new B2cproMainService();
			CatagoryService cSvc = new CatagoryService();
			try {
				B2cproMainVO proVO = bSvc.findByPrimaryKey(pro_id);
				req.setAttribute("proVO", proVO);

				RequestDispatcher successView = req.getRequestDispatcher("/back_end/b2cproduct/updatePro.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				B2cproMainVO proVO = bSvc.findByPrimaryKey(pro_id);
				req.setAttribute("proVO", proVO);
				CatagoryVO catVO = cSvc.findByPrimaryKey(proVO.getCat_id());
				req.setAttribute("catVO", catVO);
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/listProByCat.jsp");
				failureView.forward(req, res);
			}

		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String pro_id = req.getParameter("pro_id");

				B2cproMainService bSvc = new B2cproMainService();
				List<String> proNames = new LinkedList<String>();
				for (B2cproMainVO aPro : bSvc.getAll()) {
					proNames.add(aPro.getPro_name());
				}

				String pro_name = req.getParameter("pro_name");
				proNames.remove(pro_name);
				if (pro_name == null || pro_name.trim().length() == 0) {
					errorMsgs.add("產品名稱不可為空");
				} else if (proNames.contains(pro_name)) {
					errorMsgs.add("已有同樣產品名稱");
				}
				String cat_id = req.getParameter("cat_id");
				if (cat_id == null || cat_id.trim().length() == 0) {
					errorMsgs.add("商品分類不可為空");
				}
				byte[] picture = null;
				Part part = req.getPart("picture");
				Integer rrp = null;
				try {
					rrp = new Integer(req.getParameter("rrp").trim());
					if (rrp <= 0) {
						errorMsgs.add("建議售價必須為正整數");
					}
				} catch (NumberFormatException e) {
					rrp = 0;
					errorMsgs.add("請填入數字");
				}
				
				Integer stock = null;
				try {
					stock = new Integer(req.getParameter("stock").trim());
					if (stock <= 0) {
						errorMsgs.add("庫存必須不小於0");
					}
				} catch (NumberFormatException e) {
					stock = 0;
					errorMsgs.add("庫存必須為正整數");
				}
				
				
				String vendor_id = req.getParameter("vendor_id");
				if (vendor_id == null || vendor_id.trim().length() == 0) {
					errorMsgs.add("請選擇廠商");
				}
				Integer status = null;
				try {
					status = Integer.parseInt(req.getParameter("status").trim());
					if (!(status == 0 || status == 1)) {
						status = 0;
						errorMsgs.add("請選擇上架或是下架");
					}
				} catch (NumberFormatException e) {
					errorMsgs.add("請選擇商品狀態");
				}
				String pro_des = req.getParameter("pro_des");
				B2cproMainVO orgPro = new B2cproMainVO();
				orgPro = bSvc.findByPrimaryKey(pro_id);
				B2cproMainVO newPro = new B2cproMainVO();
				newPro.setPro_id(pro_id);
				newPro.setPro_name(pro_name);
				newPro.setCat_id(cat_id);
				if (part != null && part.getSize() != 0) {
					InputStream is = part.getInputStream();
					picture = new byte[is.available()];
					is.read(picture);
					is.close();
					newPro.setPicture(picture);
				} else {
					newPro.setPicture(orgPro.getPicture());
				}
				newPro.setRrp(rrp);
				newPro.setStock(stock);
				newPro.setVendor_id(vendor_id);
				newPro.setStatus(status);
				newPro.setPro_des(pro_des);

				if (!errorMsgs.isEmpty()) {
					// 如果沒有成功
					req.setAttribute("proVO", newPro);
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/b2cproduct/updatePro.jsp");
					failureView.forward(req, res);
					return;
				}

				bSvc.update(newPro);
				String dir = getServletContext().getRealPath("/back_end/b2cproduct/images");
				String fileName = pro_id + ".png";

				FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
				if (picture != null && picture.length != 0) {
					fos.write(newPro.getPicture());
				} else {
					fos.write(orgPro.getPicture());
				}
				fos.close();

				CatagoryService cSvc = new CatagoryService();
				CatagoryVO catVO = cSvc.findByPrimaryKey(cat_id);
				Set<B2cproMainVO> pros = new LinkedHashSet<B2cproMainVO>();
				pros = cSvc.getB2cprosByCatId(cat_id);
				req.setAttribute("catVO", catVO);
				req.setAttribute("pros", pros);
				RequestDispatcher successView = req.getRequestDispatcher("/back_end/catagory/listProByCat.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/b2cproduct/updatePro.jsp");
				failureView.forward(req, res);
			}

		}
	}

}
