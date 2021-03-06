<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.catagory.model.*"%>
<%@ page import="com.spec_main.model.*"%>
<%@ include file="/back_end/pageTags.jsp"%>
<!DOCTYPE html>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>Add Spec into Category</title>
<%
	CatagoryVO catVO = (CatagoryVO) request.getAttribute("catVO");
	SpecMainVO specVO = (SpecMainVO) request.getAttribute("specVO");
	LinkedHashSet<SpecMainVO> catSpecs = (LinkedHashSet) request.getAttribute("catSpecs");
%>


<%@ include file="/back_end/headerSection.jsp"%>

<style>
td {
	width: 150px;
	text-align: center;
}

th {
	text-align: center;
}

input.btn.btn-info {
	margin-left: 380px;
}
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
				<h4 class="page-title">規格管理</h4>
				<ol class="breadcrumb">
					<li><a
						href="<%=request.getContextPath()%>/back_end/spec/SpecIndex.jsp">分類規格查詢</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->
			<!--Start row-->
			<div class="row">
				<div class="col-md-12">

					<div class="white-box">
						<h2 class="header-title">
							<div>新增商品規格</div>
							<div class="features"></div>
						</h2>
						<div class="table-responsive">
							<c:if test="${not empty errorMsgs}">
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color: red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
							<div class="modal-body">
								<div class="form-horizontal">
									<div class="form-group">
										<FORM METHOD="post" ACTION="spec.do" name="form1">
											<table class="table">
												<tr>
													<td>產品分類：</td>
													<td>${catVO.cat_id}【${catVO.cat_des}】</td>
												</tr>
												<tr>
													<td>規格名稱：</td>
													<td><input type="TEXT" name="spec_des" size="45"
														value="<%=(specVO.getSpec_des() == null) ? "" : specVO.getSpec_des()%>" /></td>
												</tr>
											</table>
											<input type="hidden" name="cat_id" value="${catVO.cat_id}">
											<input type="hidden" name="action" value="insert"> <input
												type="submit" class="btn btn-info" value="送出新增">
										</FORM>
										<br>
										<table id="showSpecs" class="table">
											<tr>
												<th colspan="2">目前商品分類中所有的規格</th>
											</tr>
											<c:forEach var="catSpec" items="${catSpecs}">
												<tr>
													<td>${catSpec.spec_id}</td>
													<td>${catSpec.spec_des}</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
			<!--End row-->
		</div>
		<!-- End Wrapper-->

		<%@ include file="/back_end/footerMenu.jsp"%>

	</div>
	<!--End main content -->

	<%@ include file="/back_end/footerSection.jsp"%>
</body>
</html>
