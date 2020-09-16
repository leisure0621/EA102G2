<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.promo_main.model.*"%>
<%@ page import="com.promo_detail.model.*"%>

<%
	String promo_id = request.getParameter("promo_id");
	pageContext.setAttribute("promo_id", promo_id);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>促銷商品資料</title>
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
				<h4 class="page-title">促銷管理</h4>
				<ol class="breadcrumb">
					<li><a href="promoDetailManagement.jsp">促銷商品管理</a></li>
					<li><a href="listOnePromoDetail.jsp">促銷商品列表</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->
			
			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>促銷商品列表</div>
							<div class="features">
								<button type="button" class="btn btn-info" onclick="location.href='addPromoDetail.jsp?promo_id=${promo_id}'">新增商品</button>
							</div>
						</h2>
						<div class="table-responsive">
							<table class="display table">
								<thead>
									<tr>
										<th>促銷編號</th>
										<th>產品編號</th>
										<th>產品名稱</th>
										<th>促銷價格</th>
									</tr>
								</thead>
								<jsp:useBean id="bproSvc" scope="page" class="com.b2cpro_main.model.B2cproMainService" />
								<tbody>
									<!-- 判斷是否為list -->
									<c:choose>
										<c:when test="${isList}">
											<c:forEach var="aPromoDetail" items="${promoDetailVO}">
												<tr>
													<td>${aPromoDetail.promo_id}</td>
													<c:forEach var="bproVO" items="${bproSvc.all}">
														<c:if test="${aPromoDetail.pro_id eq bproVO.pro_id}">
															<td>${aPromoDetail.pro_id} </td>
															<td>【${bproVO.pro_name}】</td>
														</c:if>
													</c:forEach>
													
													<td>$ ${aPromoDetail.promo_price}</td>
												</tr>
											</c:forEach>
										</c:when>
										
										<c:otherwise> 
											<tr>
												<td>${promoDetailVO.promo_id}</td>
												<c:forEach var="bproVO" items="${bproSvc.all}">
													<c:if test="${promoDetailVO.pro_id eq bproVO.pro_id}">
														<td>${promoDetailVO.pro_id} </td>
														<td>【${bproVO.pro_name}】</td>
													</c:if>
												</c:forEach>
												
												<td>$ ${promoDetailVO.promo_price}</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>

							<!-- 錯誤表列 -->
							<div>
								<c:if test="${not empty errorMsgs}">
									<font style="color: red">請修正以下錯誤:</font>
									<ul>
										<c:forEach var="message" items="${errorMsgs}">
											<li style="color: red">${message}</li>
										</c:forEach>
									</ul>
								</c:if>
							</div>

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