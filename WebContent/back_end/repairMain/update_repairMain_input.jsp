<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.repair_main.model.*"%>
<%@ include file="/back_end/pageTags.jsp"%>

<%
	RepairMainVO rmVO = (RepairMainVO) request.getAttribute("rmVO");
	//RepairMainServlet.java (Concroller) 存入req的rmVO物件 (包括幫忙取出的rmVO, 也包括輸入資料錯誤時的rmVO物件)
%>

<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>維修單主檔資料修改</title>

<%@ include file="/back_end/headerSection.jsp"%>

<style>
</style>

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



			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>修改維修單主檔資料:</div>
						</h2>

						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>


						<div class="table-responsive">
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/repairMain/repairMain.do" name="form1">
								<table class="display table">
									<tr>
										<td>維修單號:<font color=red><b>*</b></font></td>
										<td><%=rmVO.getRepair_id()%></td>
									</tr>
									
									<tr>
										<td>申請會員編號:<font color=red><b>*</b></font></td>
										<td><%=rmVO.getMem_id()%></td>
									</tr>
									
<!-- 									<tr> -->
<!-- 										<td>申請會員編號:</td> -->
<!-- 										<td><input type="TEXT" name="mem_id" size="20" -->
<%-- 											value="<%=rmVO.getMem_id()%>" /></td> --%>
<!-- 									</tr> -->


									<jsp:useBean id="catSvc" scope="page"
										class="com.catagory.model.CatagoryService" />
									<tr>
										<td>維修商品分類代號:<font color=red><b>*</b></font></td>
										<td><select size="1" name="cat_id">
												<c:forEach var="catVO" items="${catSvc.all}">
													<option value="${catVO.cat_id}"
														${(rmVO.cat_id==catVO.cat_id)?'selected':''}>${catVO.cat_des}
												</c:forEach>

										</select></td>
									</tr>


									<tr>
										<td>維修品名稱:</td>
										<td><input type="TEXT" name="pro_name" size="20"
											value="<%=rmVO.getPro_name()%>" /></td>
									</tr>
									<tr>
										<td>問題描述:</td>
										<td><textarea name="description" rows="6"
												placeholder="問題描述"><%=(rmVO == null) ? "" : rmVO.getDescription()%> </textarea></td>
									</tr>


									<jsp:useBean id="rsSvc" scope="page"
										class="com.repair_status.model.RepairStatusService" />
									<tr>
										<td>維修單狀態:<font color=red><b>*</b></font></td>
										<td><select size="1" name="status_id">
												<c:forEach var="rsVO" items="${rsSvc.all}">
													<option value="${rsVO.status_id}"
														${(rmVO.status_id==rsVO.status_id)?'selected':''}>${rsVO.status_des}
												</c:forEach>

										</select></td>
									</tr>

									<tr>
										<td>總金額:<font color=red><b>*</b></font></td>
										<td><input type="TEXT" name="amount" size="10"
											value="<%=rmVO.getAmount()%>" /></td>
									</tr>
									<tr>
										<td>收貨地址:</td>
										<td><input type="TEXT" name="dev_address" size="35"
											value="<%=rmVO.getDev_address()%>" /></td>
									</tr>
									<tr>
										<td>收貨人:</td>
										<td><input type="TEXT" name="recipient" size="20"
											value="<%=rmVO.getRecipient()%>" /></td>
									</tr>

									<!-- 					<tr> -->
									<!-- 						<td>付款方式:</td> -->
									<!-- 						<td><input type="TEXT" name="pay_via" size="20" -->
									<%-- 							value="<%=rmVO.getPay_via()%>" /></td> --%>
									<!-- 					</tr> -->
									<!-- 					<tr> -->
									<!-- 						<td>收貨方式:</td> -->
									<!-- 						<td><input type="TEXT" name="delivery" size="20" -->
									<%-- 							value="<%=rmVO.getDelivery()%>" /></td> --%>
									<!-- 					</tr> -->

								</table>
								<br> 
								<input type="hidden" name="action" value="update">
								<input type="hidden" name="repair_id" value="<%=rmVO.getRepair_id()%>">
								<input type="hidden" name="mem_id" value="<%=rmVO.getMem_id()%>">
								<input type="hidden" name="pay_via" value="1.0">
								<input type="hidden" name="delivery" value="1.0"> 
								<input type="submit" value="送出修改">
							</FORM>
						</div>
					</div>
				</div>
			</div>
			<!--End row-->

		</div>

		<!-- End Wrapper -->

		<%@ include file="/back_end/footerMenu.jsp"%>

	</div>
	<!--End main content -->



	<%@ include file="/back_end/footerSection.jsp"%>
</body>
</html>












