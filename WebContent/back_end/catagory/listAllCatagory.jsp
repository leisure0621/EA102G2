
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.catagory.model.*"%>
<%
	CatagoryService cSvc = new CatagoryService();
List<CatagoryVO> list = cSvc.getAll();
pageContext.setAttribute("list", list);
%>
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
<title>所有分類</title>
<%@ include file="/back_end/headerSection.jsp"%>

<style>
.table th:nth-child(1) {
	width: 300px;
}
</style>
</head>
<body class="sticky-header">
	<%@ include file="/back_end/leftSideMenu.jsp"%>
	<div class="main-content">
		<%@ include file="/back_end/headerMenu.jsp"%>
		<div class="wrapper">
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
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤：</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<div class="white-box">
				<h2 class="header-title">商品分類列表</h2>
				<div class="table-wrap">
					<table class="table">
						<thead>
							<tr>
								<th>分類編號</th>
								<th>分類名稱</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="catVO" items="${list}">
								<tr>
									<td>${catVO.cat_id}</td>
									<td><a
										href="<%= request.getContextPath()%>/back_end/catagory/cat.do?action=getOneForUpdate&cat_id=${catVO.cat_id}">
											${catVO.cat_des} </a></td>
								</tr>

							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<%@ include file="/back_end/footerMenu.jsp"%>

	</div>
	<%@ include file="/back_end/footerSection.jsp"%>
</body>
</html>