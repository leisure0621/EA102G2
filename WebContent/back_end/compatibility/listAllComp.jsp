<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page
	import="com.b2cpro_main.model.*,com.catagory.model.*,com.spec_main.model.*,com.spec_detail.model.*,com.compatibility.model.*"%>


<%@ include file="/back_end/pageTags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<title>List All Comp</title>
<%@ include file="/back_end/headerSection.jsp"%>
</head>
<body class="sticky-header">
	<%@ include file="/back_end/leftSideMenu.jsp"%>
	<div class="main-content">
		<%@ include file="/back_end/headerMenu.jsp"%>
		<div class="wrapper">
			<div class="page-title-box">
				<h4 class="page-title">相容性管理</h4>
				<ol class="breadcrumb">
					<li><a
						href="<%=request.getContextPath()%>/back_end/compatibility/listAllComp.jsp">所有相容性列表</a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/compatibility/addComp.jsp">綁定相容性</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<jsp:useBean id="cSvc" scope="page"
				class="com.catagory.model.CatagoryService" />
			<jsp:useBean id="smSvc" scope="page"
				class="com.spec_main.model.SpecMainService" />
			<jsp:useBean id="sdSvc" scope="page"
				class="com.spec_detail.model.SpecDetailService" />
			<jsp:useBean id="compSvc" scope="page"
				class="com.compatibility.model.CompatibilityService" />

			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>所有相容性列表</div>
						</h2>
						<div class="table-responsive">
							<!--                         	錯誤表列 -->
							<c:if test="${not empty errorMsgs}">
								<font style="color: red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color: red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
							<div class="table-wrap">
								<table class="table">
									<thead>
										<tr>
											<th>商品分類</th>
											<th>規格</th>
											<th>規格內容</th>
											<th>相容分類</th>
											<th>相容規格</th>
											<th>相容規格內容</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="comp" items="${compSvc.getAll()}">
											<tr>
												<td>${cSvc.findByPrimaryKey(smSvc.findByPrimaryKey(sdSvc.findByPrimaryKey(comp.specDet_id1).spec_id).cat_id).cat_des}</td>
												<td>${smSvc.findByPrimaryKey(sdSvc.findByPrimaryKey(comp.specDet_id1).spec_id).spec_des}</td>
												<td>${sdSvc.findByPrimaryKey(comp.specDet_id1).detail_des}</td>
												<td>${cSvc.findByPrimaryKey(smSvc.findByPrimaryKey(sdSvc.findByPrimaryKey(comp.specDet_id2).spec_id).cat_id).cat_des}</td>
												<td>${smSvc.findByPrimaryKey(sdSvc.findByPrimaryKey(comp.specDet_id2).spec_id).spec_des}</td>
												<td>${sdSvc.findByPrimaryKey(comp.specDet_id2).detail_des}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/back_end/footerMenu.jsp"%>

	</div>
	<%@ include file="/back_end/footerSection.jsp"%>
</body>
</html>