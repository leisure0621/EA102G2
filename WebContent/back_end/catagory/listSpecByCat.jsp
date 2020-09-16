<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.catagory.model.*,com.b2cpro_main.model.*"%>
<%@ page import="com.spec_main.model.*"%>
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
<title>List Specs by Category</title>

<%@ include file="/back_end/headerSection.jsp"%>


<style>
.form-horizontal {
	width: 100%;
}

th {
	text-align: center;
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
							<div>分類規格查詢</div>
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

							<table class="table">
								<tr>
									<th colspan="2"><b>${catVO.cat_id }【${catVO.cat_des}】</b>包含的所有規格</th>
									<td><form method="post"
											action="<%=request.getContextPath()%>/back_end/spec/spec.do">
											<input type="hidden" name="cat_id" value="${catVO.cat_id}">
											<input type="hidden" name="action" value="ChooseCatToInsert">
											<input type="submit" class="btn btn-info" value="新增規格">
										</form></td>
								</tr>
								<tr>
									<td>規格編號</td>
									<td>規格描述</td>
									<c:forEach var="catSpec" items="${catSpecs}">
										<tr>
											<td>${catSpec.spec_id}</td>
											<td>${catSpec.spec_des}</td>

											<td>
												<form method="post"
													action="<%=request.getContextPath()%>/back_end/spec/spec.do">
													<input type="hidden" name="spec_id"
														value="${catSpec.spec_id}"> <input type="hidden"
														name="action" value="listDetailBySpec"> <input
														type="submit" class="btn btn-info" value="查看所有規格細節">
												</form>
											</td>

										</tr>
									</c:forEach>
								</tr>
							</table>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!--End row-->
		<!-- End Wrapper-->

		<%@ include file="/back_end/footerMenu.jsp"%>

	</div>
	<!--End main content -->

	<%@ include file="/back_end/footerSection.jsp"%>
</body>
</html>