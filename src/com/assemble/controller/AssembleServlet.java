package com.assemble.controller;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Predicate;

import com.b2cpro_main.model.*;
import com.catagory.model.*;
import com.compatibility.model.*;
import com.pro_spec.model.ProSpecService;
import com.pro_spec.model.ProSpecVO;
import com.spec_detail.model.*;
import com.spec_main.model.SpecMainService;

@WebServlet("/front_end/assemble/assemble.do")
public class AssembleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AssembleServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");

		String action = req.getParameter("action");
		CatagoryService cSvc = new CatagoryService();
		B2cproMainService bSvc = new B2cproMainService();
		CompatibilityService compSvc = new CompatibilityService();
		SpecDetailService sdSvc = new SpecDetailService();
//		CompatibilityService cpSvc = new CompatibilityService();

		if ("selectAll".equals(action)) {
			int power = 0;
			Set<B2cproMainVO> pcbs = new LinkedHashSet<B2cproMainVO>();
			Set<B2cproMainVO> processors = new LinkedHashSet<B2cproMainVO>();
			Set<B2cproMainVO> drams = new LinkedHashSet<B2cproMainVO>();
			Set<B2cproMainVO> hdds = new LinkedHashSet<B2cproMainVO>();
			Set<B2cproMainVO> gpus = new LinkedHashSet<B2cproMainVO>();
			Set<B2cproMainVO> cases = new LinkedHashSet<B2cproMainVO>();
			Set<B2cproMainVO> pss = new LinkedHashSet<B2cproMainVO>();

			String pcbId = "CAT0003";
			String processorId = "CAT0004";
			String dramId = "CAT0005";
			String hddId = "CAT0006";
			String gpuId = "CAT0010";
			String caseId = "CAT0012";
			String psId = "CAT0013";

			String pcb = req.getParameter("pcb");
			String processor = req.getParameter("processor");
			String dram = req.getParameter("dram");
			String hdd = req.getParameter("hdd");
			String gpu = req.getParameter("gpu");
			String cas = req.getParameter("case");
			String ps = req.getParameter("ps");

			Set<B2cproMainVO> chosenPros = new LinkedHashSet<B2cproMainVO>();
			if (pcb != null && pcb.trim().length() != 0) {
				chosenPros.add(bSvc.findByPrimaryKey(pcb));
				power += getPowerConsumption(pcb);
			}
			if (processor != null && processor.trim().length() != 0) {
				chosenPros.add(bSvc.findByPrimaryKey(processor));
				power += getPowerConsumption(processor);
			}
			if (dram != null && dram.trim().length() != 0) {
				chosenPros.add(bSvc.findByPrimaryKey(dram));
			}
			if (hdd != null && hdd.trim().length() != 0) {
				chosenPros.add(bSvc.findByPrimaryKey(hdd));
			}
			if (gpu != null && gpu.trim().length() != 0) {
				chosenPros.add(bSvc.findByPrimaryKey(gpu));
				power += getPowerConsumption(gpu);
			}
			if (cas != null && cas.trim().length() != 0) {
				chosenPros.add(bSvc.findByPrimaryKey(cas));
			}
			if (ps != null && ps.trim().length() != 0) {
				chosenPros.add(bSvc.findByPrimaryKey(ps));
			}

//			for (B2cproMainVO aPro : chosenPros) {
//				System.out.println(aPro.getPro_name());
//			}
			Set<CompatibilityVO> allComps = new LinkedHashSet<CompatibilityVO>(compSvc.getAll());
			Set<CompatibilityVO> requiredComp = new LinkedHashSet<CompatibilityVO>();

			for (CompatibilityVO aComp : allComps) {
				for (B2cproMainVO aPro : chosenPros)
					for (ProSpecVO psVO : bSvc.getSpecByPro(aPro.getPro_id())) {
						if (aComp.getSpecDet_id1().equals(psVO.getSpecDet_id())
								|| aComp.getSpecDet_id2().equals(psVO.getSpecDet_id())) {
							requiredComp.add(aComp);
						}
					}
			}

			Set<B2cproMainVO> proVOs = new LinkedHashSet<B2cproMainVO>();
			for (B2cproMainVO aPro : bSvc.getAll()) {
//				System.out.println("pro=" + aPro.getPro_id());
//				System.out.println("---------------");
				for (ProSpecVO psVO : bSvc.getSpecByPro(aPro.getPro_id())) {
//					System.out.println("proSpec=" + psVO.getSpecDet_id());
//					System.out.println("---------------");
					for (CompatibilityVO aComp : requiredComp) {

//						System.out.println("comp 1:" + aComp.getSpecDet_id1());
//						System.out.println("comp 2:" + aComp.getSpecDet_id2());
						if (aComp.getSpecDet_id1().equals(psVO.getSpecDet_id())
								|| aComp.getSpecDet_id2().equals(psVO.getSpecDet_id())) {

							proVOs.add(aPro);
//							System.out.println(aPro.getPro_id() + "新增至proVOs");
						}
					}
				}
			}
//			for (B2cproMainVO aPro : proVOs) {
//				System.out.println(aPro.getPro_name());
//			}

			for (B2cproMainVO aPro : proVOs) {
				switch (aPro.getCat_id()) {
				case "CAT0003":
					pcbs.add(aPro);
					break;
				case "CAT0004":
					processors.add(aPro);
					break;
				case "CAT0005":
					drams.add(aPro);
					break;
				case "CAT0006":
					hdds.add(aPro);
					break;
				case "CAT0010":
					gpus.add(aPro);
					break;
				case "CAT0012":
					cases.add(aPro);
					break;
				}
			}
			for (B2cproMainVO aPro : cSvc.getB2cprosByCatId("CAT0013")) {
				for (ProSpecVO psVO : bSvc.getSpecByPro(aPro.getPro_id())) {

					if (sdSvc.findByPrimaryKey(psVO.getSpecDet_id()).getSpec_id().equals("SP0087") && Integer
							.parseInt(sdSvc.findByPrimaryKey(psVO.getSpecDet_id()).getDetail_des()) >= (power + 300)) {
						pss.add(aPro);
//						System.out.println(aPro.getPro_name() + "已被加入");
					}
				}
			}

//			System.out.println("pss=" + pss.size());

			if (pcbs.size() == 0) {
				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(pcbId)) {
					pcbs.add(aPro);
				}
			}
			if (processors.size() == 0) {
				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(processorId)) {
					processors.add(aPro);
				}
			}
			if (drams.size() == 0) {
				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(dramId)) {
					drams.add(aPro);
				}
			}
			if (hdds.size() == 0) {
				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(hddId)) {
					hdds.add(aPro);
				}
			}
			if (gpus.size() == 0) {
				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(gpuId)) {
					gpus.add(aPro);
				}
			}
			if (cases.size() == 0) {
				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(caseId)) {
					cases.add(aPro);
				}
			}
			if (pss.size() == 0) {
				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(psId)) {
					pss.add(aPro);
				}
			}

//			System.out.println(pcbs.size());
//			System.out.println(processors.size());
//			System.out.println(drams.size());
//			System.out.println(hdds.size());
//			System.out.println(gpus.size());
//			System.out.println(cases.size());
//			System.out.println(pss.size());

//			System.out.println(power);

			Map<String, Set<B2cproMainVO>> selectedPros = new LinkedHashMap<String, Set<B2cproMainVO>>();
			selectedPros.put("pcbs", pcbs);
			selectedPros.put("processors", processors);
			selectedPros.put("drams", drams);
			selectedPros.put("hdds", hdds);
			selectedPros.put("gpus", gpus);
			selectedPros.put("cases", cases);
			selectedPros.put("pss", pss);

			PrintWriter out = res.getWriter();
			// 回傳結果
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", selectedPros);
			jsonObject.put("status", 200);
			out.print(jsonObject);
		}

//		if ("selectCAT0003".equals(action)) {
//			JSONArray array = new JSONArray();
//			Set<B2cproMainVO> pcbs = new LinkedHashSet<>();
////			List<CompatibilityVO> comps = new LinkedList<CompatibilityVO>();
//			pcbs = (Set<B2cproMainVO>) session.getAttribute("pcbs");
//			if (pcbs == null || pcbs.size() == 0) {
//				pcbs = cSvc.getB2cprosByCatId(cat_id);
//			}
//			for (B2cproMainVO proVO : pcbs) {
//				JSONObject obj = new JSONObject();
//				obj.put("pro_id", proVO.getPro_id());
//				obj.put("pro_name", proVO.getPro_name());
//				obj.put("rrp", proVO.getRrp());
//				obj.put("cat_id", proVO.getCat_id());
//				array.put(obj);
//			}
//
//			PrintWriter out = res.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();
//
//		}
//		if ("selectCAT0004".equals(action)) {
//			JSONArray array = new JSONArray();
//			Set<B2cproMainVO> processors = new LinkedHashSet<>();
////			List<CompatibilityVO> comps = new LinkedList<CompatibilityVO>();
//
//			if (processors.size() == 0) {
//				processors = cSvc.getB2cprosByCatId(cat_id);
//			}
//			for (B2cproMainVO proVO : processors) {
//				JSONObject obj = new JSONObject();
//				obj.put("pro_id", proVO.getPro_id());
//				obj.put("pro_name", proVO.getPro_name());
//				obj.put("rrp", proVO.getRrp());
//				obj.put("cat_id", proVO.getCat_id());
//				array.put(obj);
//			}
//
//			PrintWriter out = res.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();
//
//		}
//		if ("selectCAT0005".equals(action)) {
//			JSONArray array = new JSONArray();
//			Set<B2cproMainVO> drams = new LinkedHashSet<>();
////			List<CompatibilityVO> comps = new LinkedList<CompatibilityVO>();
//
//			if (drams.size() == 0) {
//				drams = cSvc.getB2cprosByCatId(cat_id);
//			}
//			for (B2cproMainVO proVO : drams) {
//				JSONObject obj = new JSONObject();
//				obj.put("pro_id", proVO.getPro_id());
//				obj.put("pro_name", proVO.getPro_name());
//				obj.put("rrp", proVO.getRrp());
//				obj.put("cat_id", proVO.getCat_id());
//				array.put(obj);
//			}
//
//			PrintWriter out = res.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();
//
//		}
//		if ("selectCAT0006".equals(action)) {
//			JSONArray array = new JSONArray();
//			Set<B2cproMainVO> hdds = new LinkedHashSet<>();
////			List<CompatibilityVO> comps = new LinkedList<CompatibilityVO>();
//
//			if (hdds.size() == 0) {
//				hdds = cSvc.getB2cprosByCatId(cat_id);
//			}
//			for (B2cproMainVO proVO : hdds) {
//				JSONObject obj = new JSONObject();
//				obj.put("pro_id", proVO.getPro_id());
//				obj.put("pro_name", proVO.getPro_name());
//				obj.put("rrp", proVO.getRrp());
//				obj.put("cat_id", proVO.getCat_id());
//				array.put(obj);
//			}
//
//			PrintWriter out = res.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();
//
//		}
//		if ("selectCAT0010".equals(action)) {
//			JSONArray array = new JSONArray();
//			Set<B2cproMainVO> gpus = new LinkedHashSet<>();
////			List<CompatibilityVO> comps = new LinkedList<CompatibilityVO>();
//
//			if (gpus.size() == 0) {
//				gpus = cSvc.getB2cprosByCatId(cat_id);
//			}
//			for (B2cproMainVO proVO : gpus) {
//				JSONObject obj = new JSONObject();
//				obj.put("pro_id", proVO.getPro_id());
//				obj.put("pro_name", proVO.getPro_name());
//				obj.put("rrp", proVO.getRrp());
//				obj.put("cat_id", proVO.getCat_id());
//				array.put(obj);
//			}
//
//			PrintWriter out = res.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();
//
//		}
//		if ("selectCAT0012".equals(action)) {
//			JSONArray array = new JSONArray();
//			Set<B2cproMainVO> cases = new LinkedHashSet<>();
////			List<CompatibilityVO> comps = new LinkedList<CompatibilityVO>();
//
//			if (cases.size() == 0) {
//				cases = cSvc.getB2cprosByCatId(cat_id);
//			}
//			for (B2cproMainVO proVO : cases) {
//				JSONObject obj = new JSONObject();
//				obj.put("pro_id", proVO.getPro_id());
//				obj.put("pro_name", proVO.getPro_name());
//				obj.put("rrp", proVO.getRrp());
//				obj.put("cat_id", proVO.getCat_id());
//				array.put(obj);
//			}
//
//			PrintWriter out = res.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();
//
//		}
//		if ("selectCAT0013".equals(action)) {
//			JSONArray array = new JSONArray();
//			Set<B2cproMainVO> pss = new LinkedHashSet<>();
////			List<CompatibilityVO> comps = new LinkedList<CompatibilityVO>();
//
//			if (pss.size() == 0) {
//				pss = cSvc.getB2cprosByCatId(cat_id);
//			}
//			for (B2cproMainVO proVO : pss) {
//				JSONObject obj = new JSONObject();
//				obj.put("pro_id", proVO.getPro_id());
//				obj.put("pro_name", proVO.getPro_name());
//				obj.put("rrp", proVO.getRrp());
//				obj.put("cat_id", proVO.getCat_id());
//				array.put(obj);
//			}
//
//			PrintWriter out = res.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();
//
//		}
//		if ("addCAT0003".equals(action)) {
//			String processorId = "CAT0004";
//			String dramId = "CAT0005";
//			String hddId = "CAT0006";
//			String gpuId = "CAT0010";
//			String caseId = "CAT0012";
//			String psId = "CAT0013";
//
//			SpecDetailService sdSvc = new SpecDetailService();
//
//			ProSpecService psSvc = new ProSpecService();
//			String pro_id = req.getParameter("pro_id");
//			B2cproMainVO chosenPro = bSvc.findByPrimaryKey(pro_id);
//			Set<SpecDetailVO> proSpecs = new LinkedHashSet<SpecDetailVO>();
//			for (ProSpecVO psVO : bSvc.getSpecByPro(pro_id)) {
//				proSpecs.add(sdSvc.findByPrimaryKey(psVO.getSpecDet_id()));
//			}
//
//			List<CompatibilityVO> allComps = new LinkedList<CompatibilityVO>();
//			allComps = compSvc.getAll();
//			Set<CompatibilityVO> requiredComp = new LinkedHashSet<CompatibilityVO>();
//			Set<B2cproMainVO> proVOs = new LinkedHashSet<B2cproMainVO>();
//
//			// 將產品擁有的spec detail中的compatibility傳入requiredComp中
//			for (CompatibilityVO aComp : allComps) {
//				for (SpecDetailVO seVO : proSpecs) {
//					if (aComp.getSpecDet_id1().equals(seVO.getSpecdet_id())
//							|| aComp.getSpecDet_id2().contentEquals(seVO.getSpecdet_id())) {
//						requiredComp.add(aComp);
//					}
//				}
//			}
//
//			// 測測看有沒有抓到喔
////			for (CompatibilityVO aComp : requiredComp) {
////				System.out.println(sdSvc.findByPrimaryKey(aComp.getSpecDet_id1()).getDetail_des());
////				System.out.println(sdSvc.findByPrimaryKey(aComp.getSpecDet_id2()).getDetail_des());
////
////			}
//			// 用相容性找出擁有相容性特性的商品
//
//			for (B2cproMainVO aPro : bSvc.getAll()) {
//				for (ProSpecVO psVO : psSvc.findByPrimaryKey(aPro.getPro_id())) {
//					for (CompatibilityVO aComp : requiredComp) {
//						if (aComp.getSpecDet_id1().equals(psVO.getSpecDet_id())
//								|| aComp.getSpecDet_id2().equals(psVO.getSpecDet_id())) {
//							proVOs.add(aPro);
//						}
//					}
//				}
//			}
//			// 有沒有抓到產品
//
//			Set<B2cproMainVO> processors = new LinkedHashSet<B2cproMainVO>();
//			Set<B2cproMainVO> drams = new LinkedHashSet<B2cproMainVO>();
//			Set<B2cproMainVO> hdds = new LinkedHashSet<B2cproMainVO>();
//			Set<B2cproMainVO> gpus = new LinkedHashSet<B2cproMainVO>();
//			Set<B2cproMainVO> cases = new LinkedHashSet<B2cproMainVO>();
//			Set<B2cproMainVO> pss = new LinkedHashSet<B2cproMainVO>();
//
//			// 如果已經選取的話，把該分類的產品歸零
//			for (B2cproMainVO aPro : proVOs) {
//				switch (aPro.getCat_id()) {
//				case "CAT0004":
//					processors.add(aPro);
//					break;
//				case "CAT0005":
//					drams.add(aPro);
//					break;
//				case "CAT0006":
//					hdds.add(aPro);
//					break;
//				case "CAT0010":
//					gpus.add(aPro);
//					break;
//				case "CAT0012":
//					cases.add(aPro);
//					break;
//				case "CAT0013":
//					pss.add(aPro);
//					break;
//				}
//			}
//			// 若沒有找到，代表相容性無關，則加入所有該分類中的商品
//			if (processors.size() == 0) {
//				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(processorId)) {
//					processors.add(aPro);
//				}
//			}
//			if (drams.size() == 0) {
//				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(dramId)) {
//					drams.add(aPro);
//				}
//			}
//			if (hdds.size() == 0) {
//				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(hddId)) {
//					hdds.add(aPro);
//				}
//			}
//			if (gpus.size() == 0) {
//				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(gpuId)) {
//					gpus.add(aPro);
//				}
//			}
//			if (cases.size() == 0) {
//				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(caseId)) {
//					cases.add(aPro);
//				}
//			}
//			if (pss.size() == 0) {
//				for (B2cproMainVO aPro : cSvc.getB2cprosByCatId(psId)) {
//					pss.add(aPro);
//				}
//			}
//
//			Map allData = new LinkedHashMap();
////			allData.put("", value)
//			allData.put("processors", processors);
//			allData.put("drams", drams);
//			allData.put("hdds", hdds);
//			allData.put("gpus", gpus);
//			allData.put("cases", cases);
//			allData.put("pss", pss);
//			allData.put("chosenPro", chosenPro);
//
//			PrintWriter out = res.getWriter();
//			// 回傳結果
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("data", allData);
//			jsonObject.put("status", 200);
//			out.print(jsonObject);
//			// 依照相容性傳出每個category的品項和選中的品項傳到前端
//			// 前端再把left slide bar洗掉並填入類別名稱和數量、選取到的商品變色
//			// 問題:在點選left slife bar的category時，如何顯示相應的品項?
//
//		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	public int getPowerConsumption(String pro_id) {
		int powerConsumption = 0;

		B2cproMainService bSvc = new B2cproMainService();
		SpecDetailService sdSvc = new SpecDetailService();

		Set<SpecDetailVO> set = new LinkedHashSet<SpecDetailVO>();
		for (ProSpecVO psVO : bSvc.getSpecByPro(pro_id)) {
			set.add(sdSvc.findByPrimaryKey(psVO.getSpecDet_id()));
		}
		for (SpecDetailVO sdVO : set) {
			if (sdVO.getSpec_id().equals("SP0026") || sdVO.getSpec_id().equals("SP0034")
					|| sdVO.getSpec_id().equals("SP0067")) {
				powerConsumption = Integer.parseInt(sdVO.getDetail_des());
			}
		}
		return powerConsumption;
	}

}
