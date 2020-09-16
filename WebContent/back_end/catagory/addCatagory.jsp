<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.catagory.model.*"%>
<%
	CatagoryVO catVO = (CatagoryVO) request.getAttribute("catVO");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<title>商品分類管理</title>

<%@ include file="/back_end/headerSection.jsp"%>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/back_end/assets/plugins/jquery-multi-select/css/multi-select.css" />
<style>
input.btn.btn-info {
	margin-right: 1153px;
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
				<h4 class="page-title">商品分類管理</h4>
				<ol class="breadcrumb">
					<li><a
						href="<%=request.getContextPath()%>/back_end/catagory/listAllCatagory.jsp">商品分類列表</a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/catagory/addCatagory.jsp">新增商品分類</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->
			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<FORM METHOD="post" ACTION="cat.do" name="form1">
						<div class="white-box">
							<h2 class="header-title">
								<div>新增商品分類</div>
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
											<label class="col-sm-3 control-label">分類名稱</label>
											<div class="col-sm-9">
												<input class="form-control" type="TEXT" name="cat_des"
													name="cat_des" size="45"
													value="<%=(catVO == null) ? "" : catVO.getCat_des()%>"
													placeholder="請輸入分類名稱" />
											</div>
										</div>

									</div>
								</div>
								<div class="modal-footer">
									<input type="hidden" name="action" value="insert"> <input
										type="submit" value="新增" class="btn btn-info">
								</div>
					</FORM>
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