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
<title>Add Specification Details into Specification</title>
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
							<div>新增規格細節</div>
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

										<form method="post"
											action="<%=request.getContextPath()%>/back_end/spec/spec.do">
											<table>
												<tr>
													<th colspan="2"><b style="color: blue">在【${catVO.cat_id}】${catVO.cat_des}</b>中新增
														<b style="color: blue">${spec.spec_des}</b> 的規格細節</th>
												</tr>
												<tr>
													<td>細節名稱：</td>
													<td><input type="text" name="detail_des"
														value='${sdVO==null ? sdVO.detail_des : ""}'></td>
												</tr>
											</table>
											<input type="hidden" name="spec_id" value="${spec.spec_id}">
											<input type="hidden" name="action" value="addSpecDetail">
											<input type="submit" value="送出新增">
										</form>
										<table class="table">
											<tr>
												<th>現有規格細節</th>
											</tr>
											<c:forEach var="detail" items="${details}">
												<tr>
													<td>${detail.detail_des}</td>
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
