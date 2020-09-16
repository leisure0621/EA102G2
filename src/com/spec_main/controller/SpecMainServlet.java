package com.spec_main.controller;

import java.io.IOException;
import java.util.*;
import com.spec_detail.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.b2cpro_main.model.B2cproMainService;
import com.b2cpro_main.model.B2cproMainVO;
import com.catagory.model.*;
import com.pro_spec.model.ProSpecService;
import com.pro_spec.model.ProSpecVO;
import com.spec_detail.model.SpecDetailVO;
import com.spec_main.model.*;

@WebServlet("/back_end/spec/spec.do")
public class SpecMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SpecMainServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("ChooseCatToInsert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String cat_id = req.getParameter("cat_id");
				CatagoryService cSvc = new CatagoryService();
				CatagoryVO catVO = new CatagoryVO();
				catVO = cSvc.findByPrimaryKey(cat_id);
				SpecMainVO specVO = new SpecMainVO();
				Set<SpecMainVO> catSpecs = new LinkedHashSet<SpecMainVO>();
				catSpecs = cSvc.getSpecsByCatId(cat_id);

				if (!errorMsgs.isEmpty()) {

					req.setAttribute("catVO", catVO);
					req.setAttribute("catSpecs", catSpecs);
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/listSpecByCat.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("specVO", specVO);
				req.setAttribute("catVO", catVO);
				req.setAttribute("catSpecs", catSpecs);
				String url = "/back_end/spec/addSpec.jsp";
				RequestDispatcher SuccessView = req.getRequestDispatcher(url);
				SuccessView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				String url = "/back_end/catagory/listSpecByCat.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}

		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			CatagoryService cSvc = new CatagoryService();
			SpecMainService sSvc = new SpecMainService();
			Set<SpecMainVO> catSpecs = new LinkedHashSet<SpecMainVO>();
			String cat_id = req.getParameter("cat_id");
			catSpecs = cSvc.getSpecsByCatId(cat_id);
			List<String> specNames = new LinkedList<String>();
			for (SpecMainVO aSpec : catSpecs) {
				specNames.add(aSpec.getSpec_des());
			}
			try {
				String spec_des = req.getParameter("spec_des");
				if (spec_des == null || spec_des.trim().length() == 0) {
					errorMsgs.add("規格描述不可為空");
				} else if (specNames.contains(spec_des)) {
					errorMsgs.add("已有重複之規格");
				}
				if (cat_id.equals("0")) {
					errorMsgs.add("請選擇一個分類");
				}

				SpecMainVO specVO = new SpecMainVO();
				specVO.setCat_id(cat_id);
				specVO.setSpec_des(spec_des);
				CatagoryVO catVO = new CatagoryVO();
				catVO = cSvc.findByPrimaryKey(cat_id);
				if (!errorMsgs.isEmpty()) {

					catSpecs = cSvc.getSpecsByCatId(cat_id);

					req.setAttribute("catVO", catVO);
					req.setAttribute("catSpecs", catSpecs);
					req.setAttribute("specVO", specVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/spec/addSpec.jsp");
					failureView.forward(req, res);
					return;
				}
				sSvc.insert(specVO);
				catSpecs = cSvc.getSpecsByCatId(cat_id);

				req.setAttribute("catVO", catVO);
				req.setAttribute("catSpecs", catSpecs);

				String url = "/back_end/catagory/listSpecByCat.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				CatagoryVO catVO = new CatagoryVO();
				catVO = cSvc.findByPrimaryKey(cat_id);
				req.setAttribute("catVO", catVO);
				req.setAttribute("catSpecs", catSpecs);
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/listSpecByCat.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOneForDisplay".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String pro_id = req.getParameter("pro_id");
			try {
				B2cproMainService bSvc = new B2cproMainService();
				SpecDetailService SDSvc = new SpecDetailService();
				B2cproMainVO proVO = bSvc.findByPrimaryKey(pro_id);
				Set<ProSpecVO> proSpecs = new LinkedHashSet<ProSpecVO>();
				proSpecs = bSvc.getSpecByPro(pro_id);
				List<SpecDetailVO> details = new ArrayList<SpecDetailVO>();
				for (ProSpecVO temp : proSpecs) {
					details.add(SDSvc.findByPrimaryKey(temp.getSpecDet_id()));
				}

				req.setAttribute("proVO", proVO);
				req.setAttribute("details", details);
				String url = "/back_end/spec/listSpecByPro.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				String url = "/back_end/catagory/listProByCat.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
		if ("getOneForUpdate".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			CatagoryService cSvc = new CatagoryService();
			B2cproMainService bSvc = new B2cproMainService();
			SpecDetailService sdSvc = new SpecDetailService();
			ProSpecService psSvc = new ProSpecService();
			String pro_id = req.getParameter("pro_id");

			B2cproMainVO proVO = new B2cproMainVO();
			proVO = bSvc.findByPrimaryKey(pro_id);
			try {

				cSvc.getSpecsByCatId(proVO.getCat_id());
				Set<SpecMainVO> catSpecs = new LinkedHashSet<SpecMainVO>();
				catSpecs = cSvc.getSpecsByCatId(proVO.getCat_id());

				List<ProSpecVO> proSpecs = new ArrayList<ProSpecVO>();

				proSpecs = psSvc.findByPrimaryKey(pro_id);

				List<SpecDetailVO> specDetails = new ArrayList<SpecDetailVO>();
				for (ProSpecVO psVO : proSpecs) {
					specDetails.add(sdSvc.findByPrimaryKey(psVO.getSpecDet_id()));
				}
				Map<String, String> specMap = new LinkedHashMap<String, String>();
				if (specDetails.size() != 0) {
					for (SpecMainVO aCatSpec : catSpecs) {
						for (SpecDetailVO aDetail : specDetails) {
							if (aDetail.getSpec_id().equals(aCatSpec.getSpec_id())) {
								specMap.put(aCatSpec.getSpec_id(), aDetail.getSpecdet_id());
							} else if (!specMap.containsKey(aCatSpec.getSpec_id())) {
								specMap.put(aCatSpec.getSpec_id(), "");
							}
						}
					}
				} else {
					for (SpecMainVO aCatSpec : catSpecs) {
						specMap.put(aCatSpec.getSpec_id(), "");
					}
				}
//				System.out.println("specMapSize = " + specMap.size());
//				System.out.println("specDetail Size = " + specDetails.size());
//				System.out.println("proSpecs Size = " + proSpecs.size());
//				System.out.println("catSpecs Size = " + catSpecs.size());
//				for (Map.Entry<String, String> entry : specMap.entrySet()) {
//					System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
//				}
				req.setAttribute("specMap", specMap);
				req.setAttribute("proVO", proVO);
				String url = "/back_end/spec/updateProSpec.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				Set<ProSpecVO> proSpecs = new LinkedHashSet<ProSpecVO>();
				proSpecs = bSvc.getSpecByPro(pro_id);
				List<SpecDetailVO> details = new ArrayList<SpecDetailVO>();
				for (ProSpecVO temp : proSpecs) {
					details.add(sdSvc.findByPrimaryKey(temp.getSpecDet_id()));
				}

				req.setAttribute("proVO", proVO);
				req.setAttribute("details", details);
				String url = "/back_end/spec/listSpecByPro.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			B2cproMainService bSvc = new B2cproMainService();
			CatagoryService cSvc = new CatagoryService();
			ProSpecService psSvc = new ProSpecService();
			SpecMainService smSvc = new SpecMainService();
			List<ProSpecVO> proSpecs = new LinkedList<ProSpecVO>();
			SpecDetailService sdSvc = new SpecDetailService();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// 轉交至listSpecsByPro的詳情清單
				List<SpecDetailVO> details = new ArrayList<SpecDetailVO>();

				String pro_id = req.getParameter("pro_id");
				B2cproMainVO proVO = new B2cproMainVO();
				proVO = bSvc.findByPrimaryKey(pro_id);

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
				psSvc.deleteSpecsFromPro(pro_id);
				for (ProSpecVO psVO : proSpecs) {
					psSvc.insert(psVO);
				}

				// 轉畫面
				for (ProSpecVO temp : proSpecs) {
					details.add(sdSvc.findByPrimaryKey(temp.getSpecDet_id()));
				}

				req.setAttribute("proVO", proVO);
				req.setAttribute("details", details);
				String url = "/back_end/spec/listSpecByPro.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				// 補forward
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/CatIndex.jsp");
				failureView.forward(req, res);
			}
		}
		if ("listDetailBySpec".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				SpecMainService smSvc = new SpecMainService();
				CatagoryService cSvc = new CatagoryService();
				String spec_id = req.getParameter("spec_id");
				SpecMainVO specVO = smSvc.findByPrimaryKey(spec_id);
				CatagoryVO catVO = cSvc.findByPrimaryKey(specVO.getCat_id());

				Set<SpecDetailVO> specDetails = new LinkedHashSet<SpecDetailVO>();
				specDetails = smSvc.getSpecDBySpecId(spec_id);
				req.setAttribute("catVO", catVO);
				req.setAttribute("specVO", specVO);
				req.setAttribute("specDetails", specDetails);

				String url = "/back_end/spec/listDetailBySpec.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/CatIndex.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getSpecMainToAdd".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			SpecMainService smSvc = new SpecMainService();
			CatagoryService cSvc = new CatagoryService();
			try {
				String spec_id = req.getParameter("spec_id");
				SpecMainVO spec = smSvc.findByPrimaryKey(spec_id);
				Set<SpecDetailVO> details = new LinkedHashSet<SpecDetailVO>();
				details = smSvc.getSpecDBySpecId(spec_id);
				CatagoryVO catVO = new CatagoryVO();
				catVO = cSvc.findByPrimaryKey(spec.getCat_id());

				req.setAttribute("details", details);
				req.setAttribute("catVO", catVO);
				req.setAttribute("spec", spec);

				String url = "/back_end/spec/addSpecDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/CatIndex.jsp");
				failureView.forward(req, res);
			}

		}
		if ("addSpecDetail".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				CatagoryService cSvc = new CatagoryService();
				SpecMainService smSvc = new SpecMainService();
				String spec_id = req.getParameter("spec_id");
				Set<String> detail_dess = new LinkedHashSet<String>();
				for (SpecDetailVO sdVO : smSvc.getSpecDBySpecId(spec_id)) {
					detail_dess.add(sdVO.getDetail_des());
				}

				String detail_des = req.getParameter("detail_des");
				if (detail_des == null || detail_des.trim().isEmpty()) {
					errorMsgs.add("不可為空白");
				} else if (detail_dess.contains(detail_des.trim())) {
					errorMsgs.add("已有相同屬性");
				}
				
				SpecDetailVO sdVO = new SpecDetailVO();
				sdVO.setDetail_des(detail_des.trim());
				sdVO.setSpec_id(spec_id);
				
				if (!errorMsgs.isEmpty()) {
					
					SpecMainVO spec = smSvc.findByPrimaryKey(spec_id);
					Set<SpecDetailVO> details = new LinkedHashSet<SpecDetailVO>();
					details = smSvc.getSpecDBySpecId(spec_id);
					CatagoryVO catVO = new CatagoryVO();
					catVO = cSvc.findByPrimaryKey(spec.getCat_id());

					req.setAttribute("details", details);
					req.setAttribute("catVO", catVO);
					req.setAttribute("spec", spec);
					
					req.setAttribute("sdVO", sdVO);	
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/spec/addSpecDetail.jsp");
					failureView.forward(req, res);
					return;
				}


				SpecDetailService sdSvc = new SpecDetailService();
				sdSvc.insert(sdVO);
				
				SpecMainVO specVO = smSvc.findByPrimaryKey(spec_id);
				CatagoryVO catVO = cSvc.findByPrimaryKey(specVO.getCat_id());

				Set<SpecDetailVO> specDetails = new LinkedHashSet<SpecDetailVO>();
				specDetails = smSvc.getSpecDBySpecId(spec_id);

				req.setAttribute("catVO", catVO);
				req.setAttribute("specVO", specVO);
				req.setAttribute("specDetails", specDetails);
				
				String url = "/back_end/spec/listDetailBySpec.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/catagory/CatIndex.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
