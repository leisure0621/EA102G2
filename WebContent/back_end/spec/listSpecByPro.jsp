<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page
	import="com.catagory.model.*,com.b2cpro_main.model.*, com.spec_main.model.*, com.spec_detail.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<%@ include file="/back_end/headerSection.jsp"%>
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
<title>Product Specifications</title>

</head>
<body class="sticky-header">


	<%@ include file="/back_end/leftSideMenu.jsp"%>

	<!--     main content start -->
	<div class="main-content">

		<%@ include file="/back_end/headerMenu.jsp"%>

		<!--         body wrapper start -->
		<div class="wrapper">

			<!--             Start Page Title -->
			<div class="page-title-box">
				<h4 class="page-title">商品管理</h4>
				<ol class="breadcrumb">
					<li><a
						href="<%=request.getContextPath()%>/back_end/b2cproduct/listAllPro.jsp">所有產品列表</a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/b2cproduct/addPro.jsp">新增商品</a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/b2cproduct/searchProByCat.jsp">以分類查詢商品</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--             End Page Title -->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>產品列表</div>
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
							<table id="product" class="display table">
								<thead>
									<tr>
										<td colspan="2">【${proVO.pro_id}】 <b id="header">${proVO.pro_name}</b>
											的規格詳情
										</td>
									</tr>
									<tr>
										<td><b>規格名稱</b></td>
										<td><b>內容</b></td>
									</tr>
								</thead>
								<tbody>
									<jsp:useBean id="SMSvc"
										class="com.spec_main.model.SpecMainService" />
									<c:forEach var="aSpec" items="${details}">
										<tr>
											<td>【${aSpec.spec_id}】${SMSvc.findByPrimaryKey(aSpec.spec_id).spec_des}</td>
											<td>${aSpec.detail_des}</td>
										</tr>
									</c:forEach>
							</table>
							<form method="post"
								action="<%=request.getContextPath()%>/back_end/spec/spec.do">
								<input type="hidden" name="pro_id" value="${proVO.pro_id}">
								<input type="hidden" name="action" value="getOneForUpdate">
								<input type="submit" value="修改詳情">
							</form>
						</div>
					</div>
				</div>
			</div>
			<!--             End row -->
		</div>
		<!--         End Wrapper -->

		<%@ include file="/back_end/footerMenu.jsp"%>

	</div>
	<!--     End main content -->

	<%@ include file="/back_end/footerSection.jsp"%>
</body>
</html>