<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>維修單主檔管理</title>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.repair_main.model.*"%>
<%
	RepairMainService rmSvc = new RepairMainService();
	List<RepairMainVO> list = rmSvc.getAll();
	pageContext.setAttribute("list", list);

	RepairMainVO rmVO = (RepairMainVO) request.getAttribute("rmVO");
%>

<jsp:useBean id="rsSvc" scope="page"
	class="com.repair_status.model.RepairStatusService" />
<jsp:useBean id="catSvc" scope="page"
	class="com.catagory.model.CatagoryService" />
<%@ include file="/back_end/headerSection.jsp"%>

</head>

<body class="sticky-header">
	<%@ include file="/back_end/leftSideMenu.jsp"%>

	<!-- main content start-->
	<div class="main-content">
		<%@ include file="/back_end/headerMenu.jsp"%>

		<!--body wrapper start-->
		<div class="wrapper">

			<!--Start Page Title-->
			<div class="page-title-box">
				<h4 class="page-title">維修單主檔管理</h4>
				<ol class="breadcrumb">
					<li><a>維修單主檔管理</a></li>
					<li><a>維修單主檔列表</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->

			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>維修列表</div>

						</h2>
						<div class="table-responsive">
							<table id="bidList" class="display table">
								<thead>
									<tr>
										<th>維修單號</th>
										<th>申請會員編號</th>
										<th>維修商品分類代號</th>
										<th>維修品名稱</th>
										<th>問題描述</th>
										<th>維修單狀態編號</th>
										<th>申請時間</th>
										<th>總金額</th>
										<th>收貨地址</th>
										<th>收貨人</th>
										<!-- 							<th>付款方式</th> -->
										<!-- 							<th>收貨方式</th> -->
										<th>修改</th>

									</tr>
								</thead>
								<tbody>

									<%@ include file="page1.file"%>
									<c:forEach var="rmVO" items="${list}" begin="<%=pageIndex%>"
										end="<%=pageIndex+rowsPerPage-1%>">

										<tr>
											<td>${rmVO.repair_id}</td>
											<td>${rmVO.mem_id}</td>
											<td><c:forEach var="catVO" items="${catSvc.all}">
													<c:if test="${rmVO.cat_id==catVO.cat_id}">
					  	  	  								${catVO.cat_id}【${catVO.cat_des}】
					  	  	  						</c:if>
												</c:forEach></td>
											<td>${rmVO.pro_name}</td>
											<td>${rmVO.description}</td>
											<td><c:forEach var="rsVO" items="${rsSvc.all}">
													<c:if test="${rmVO.status_id==rsVO.status_id}">
								  	  	  				 【${rsVO.status_des}】 
								  	  	  			</c:if>
												</c:forEach></td>
											<td><fmt:formatDate value="${rmVO.est_time}"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>${rmVO.amount}</td>
											<td>${rmVO.dev_address}</td>
											<td>${rmVO.recipient}</td>
											<%-- 								<td>${rmVO.pay_via}</td> --%>
											<%-- 								<td>${rmVO.delivery}</td> --%>
											<td>
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/back_end/repairMain/repairMain.do"
													style="margin-bottom: 0px;">

													<button type="submit" class="btn btn-info modify">修改</button>
													<input type="hidden" name="repair_id"
														value="${rmVO.repair_id}"> <input type="hidden"
														name="action" value="getOne_For_Update">
												</FORM></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%@ include file="page2.file"%>
						</div>
					</div>
				</div>

			</div>
		</div>
		<%@ include file="/back_end/footerMenu.jsp"%>
	</div>
	<!--End main content -->

	<%@ include file="/back_end/footerSection.jsp"%>


</body>
</html>











