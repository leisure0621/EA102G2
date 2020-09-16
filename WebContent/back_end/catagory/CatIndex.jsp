<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ include file="/back_end/pageTags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>B2C商品主頁</title>


<style>
table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}

img {
	width: 50%;
	height: 50%;
}
</style>


</head>
<body>

	<table id="table-1">
		<tr>
			<td><h3>B2C商城管理</h3></td>
		</tr>
		<tr>
			<td><img
				src="<%=request.getContextPath()%>/back_end/catagory/images/hell.jpg"></td>
		</tr>
	</table>


	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<b>商品分類管理</b>
	<ul>
		<li><a
			href='<%=request.getContextPath()%>/back_end/catagory/addCatagory.jsp'>新增商品分類</a>
		</li>
		<li><a
			href='<%=request.getContextPath()%>/back_end/catagory/listAllCatagory.jsp'>分類列表</a>
		</li>
		<jsp:useBean id="catSvc" scope="page"
			class="com.catagory.model.CatagoryService" />



	</ul>
	<b>商品管理</b>
	<ul>
		<li><a
			href="<%=request.getContextPath()%>/back_end/b2cproduct/b2cpro.do?action=addProToCat&cat_id=0">
				新增商品</a></li>
		<li><a
			href="<%=request.getContextPath()%>/back_end/b2cproduct/listAllPro.jsp">
				所有商品列表</a></li>
		<li>

			<form method="post"
				action="<%=request.getContextPath()%>/back_end/catagory/cat.do">
				<b>分類產品查詢</b> <select size="1" name="cat_id">
					<option value="">請選擇</option>
					<c:forEach var="catVO" items="${catSvc.getAll()}">
						<option value="${catVO.cat_id}">
							${catVO.cat_id}【${catVO.cat_des}】</option>
					</c:forEach>
				</select> <input type="hidden" name="action" value="listProByCat">
						  <input type="submit" value="送出查詢">
			</form>
		</li>
	</ul>
	<b>規格管理</b>
	<ul>
		<li>
			<form method="post"
				action="<%=request.getContextPath()%>/back_end/catagory/cat.do">
				<b>分類規格查詢</b> <select size="1" name="cat_id">
					<option value="">請選擇</option>
					<c:forEach var="catVO" items="${catSvc.getAll()}">
						<option value="${catVO.cat_id}">
							${catVO.cat_id}【${catVO.cat_des}】</option>
					</c:forEach>
				</select> <input type="hidden" name="action" value="listSpecByCat"> <input
					type="submit" value="送出查詢">
			</form>
		</li>
	</ul>
	<b>相容性管理</b>
		<ul>
			<li>
				<a href="<%=request.getContextPath()%>/back_end/compatibility/addComp.jsp">新增相容性</a>
			</li>
			<li>
				<a href="<%=request.getContextPath()%>/back_end/compatibility/listAllComp.jsp">所有相容性列表</a>
			</li>
		</ul>
</body>
</html>