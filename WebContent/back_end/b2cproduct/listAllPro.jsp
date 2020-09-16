<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.catagory.model.*,com.b2cpro_main.model.*"%>
<%
	B2cproMainService bSvc = new B2cproMainService();
List<B2cproMainVO> pros = bSvc.getAll();
pageContext.setAttribute("pros", pros);
%>
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
<title>List All Products</title>
<%@ include file="/back_end/headerSection.jsp"%>
</head>
<body class="sticky-header">
	<%@ include file="/back_end/leftSideMenu.jsp"%>
	<div class="main-content">
		<%@ include file="/back_end/headerMenu.jsp"%>
		<div class="wrapper">
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

			<div class="white-box">
				<h2 class="header-title">
					<div>所有產品列表</div>
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
									<th>商品編號</th>
									<th>商品名稱</th>
									<th>商品照片</th>
									<th>建議售價</th>
									<th>庫存</th>
									<th>製造商</th>
									<th>商品狀態</th>
									<th>商品建立時間</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<%@ include file="pages/page1.file"%>
								<c:forEach var="aPro" items="${pros}" begin="<%=pageIndex%>"
									end="<%=pageIndex+rowsPerPage-1 %>">
									<tr>
										<td>${aPro.pro_id}</td>
										<td>${aPro.pro_name}</td>
										<td><a
											href='<%=request.getContextPath()%>/back_end/b2cproduct/images/${aPro.pro_id}.png'
											target="_blank"> <img width="50" height="50"
												src="<%= request.getContextPath()%>/back_end/b2cproduct/b2cpro.do?pro_id=${aPro.pro_id}">
										</a></td>
										<td>${aPro.rrp}</td>
										<td>${aPro.stock}</td>
										<td>${aPro.vendor_id}</td>
										<td>${aPro.status eq 0? "下架":"上架"}</td>
										<td><fmt:formatDate value="${aPro.est_time}"
												pattern="yyyy-MM-dd" /></td>
										<td>
											<form method="post"
												action="<%=request.getContextPath()%>/back_end/b2cproduct/b2cpro.do">
												<input type="hidden" name="pro_id" value="${aPro.pro_id}">
												<input type="hidden" name="action" value="getOneForUpdate">
												<input type="submit" class="btn btn-info" value="修改商品資訊">
											</form>
										</td>

									</tr>

								</c:forEach>
							</tbody>
						</table>
						<%@ include file="pages/page2.file"%>
					</div>
				</div>
			</div>
			</div>
			<%@ include file="/back_end/footerMenu.jsp"%>
			<%@ include file="/back_end/footerSection.jsp"%>
</body>
</html>