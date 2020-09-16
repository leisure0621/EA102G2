<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="java.util.*"%>
<%@ page
	import="com.catagory.model.*,com.b2cpro_main.model.*,com.pro_spec.model.*,com.vendor.model.*"%>

<%
	B2cproMainVO proVO = (B2cproMainVO) request.getAttribute("proVO");
%>
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
<title>Update product</title>
<%@ include file="/back_end/headerSection.jsp"%>

<style>
#imgDisplay,#origin {
    width: 160px;
}
#product thead tr td:nth-child(1) {
	width: 150px;
}
#product thead tr td:nth-child(3) {
    width: 400px;
    vertical-align: top;
}
.table-responsive button, 
.table-responsive input[type="text"], 
.table-responsive select, 
.table-responsive textarea {
    width: 100%;
}
.table-responsive {
    width: 80%;
}
#onshelf {
    margin-left: 5px;
}
pre {
    height: 300px;
    width: 500px;
}
textarea {
    width: 500px;
    height: 300px;
}
</style>
</head>
<body class="sticky-header">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/back_end/assets/js/jquery.min.js"></script>

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

			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>商品詳情修改</div>
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
							<FORM METHOD="post" ACTION="b2cpro.do" name="form1"
								enctype="multipart/form-data">
								<table id="product" class="table">
									<thead>
										<tr>
											<td><b>項目</b></td>
											<td><b>原數值</b></td>
											<td><b>欲修改的數值</b></td>
										</tr>
										<tr>
											<td><b>商品編號：</b></td>
											<td colspan="2"><b>${proVO.pro_id}</b></td>
										<tr>
										<tr>
											<td><b>品名:</b></td>
											<td>${proVO.pro_name}</td>
											<td><input type="TEXT" name="pro_name" size="45"
												placeholder="請輸入商品名稱"
												value="<%=(proVO.getPro_name() == null) ? "" : proVO.getPro_name()%>" /></td>
										</tr>
										<tr>
											<td><b>商品類別：</b></td>
											<jsp:useBean id="catSvc" scope="page"
												class="com.catagory.model.CatagoryService" />
											<td>${proVO.cat_id}【${catSvc.findByPrimaryKey(proVO.cat_id).cat_des}】</td>
											<td><select size="1" name="cat_id">
													<option value="">請選擇</option>
													<c:forEach var="catVOs" items="${catSvc.getAll()}">
														<option value="${catVOs.cat_id}"
															${catVOs.cat_id==proVO.cat_id ? 'selected' : ''}>
															${catVOs.cat_id}【${catVOs.cat_des}】</option>
													</c:forEach>
											</select></td>
										</tr>

										<tr>
											<td><b>商品圖片：</b></td>
											<td><img id="origin"
												src="<%= request.getContextPath()%>/back_end/b2cproduct/b2cpro.do?pro_id=${proVO.pro_id}" />
											</td>
											<td id="newImg"><input type="file" name="picture"
												accept="image/*" onchange="loadFile(event)"><img
												id="imgDisplay" /></td>
										</tr>
										<tr>
											<td><b>建議售價：</b></td>
											<td>${proVO.rrp}</td>
											<td><input type="text" name="rrp" placeholder="請輸入價格"
												value="<%=(proVO.getRrp() == null) ? "" : proVO.getRrp()%>"></td>
										</tr>
										<tr>
											<td><b>庫存：</b></td>
											<td>${proVO.stock}</td>
											<td><input type="number" name="stock" placeholder="請設定庫存"
												value="<%=(proVO.getStock() == null) ? "" : proVO.getStock()%>"></td>
										</tr>
										<tr>
											<jsp:useBean id="vSvc" scope="page"
												class="com.vendor.model.VendorService" />
											<td><b>製造商：</b></td>
											<td>${proVO.vendor_id}【${vSvc.findByPrimaryKey(proVO.vendor_id).vendor_name}】</td>
											<td><select size="1" name="vendor_id">
													<option value="">請選擇</option>
													<c:forEach var="vendors" items="${vSvc.getAll()}">
														<option value="${vendors.vendor_id}"
															${vendors.vendor_id==proVO.vendor_id ? 'selected' : '' }>
															${vendors.vendor_id}【${vendors.vendor_name}】</option>
													</c:forEach>
											</select></td>
										</tr>
										<tr>
											<td><b>上架狀態：</b></td>
											<td colspan="2"><input type="radio" id="offshelf"
												name="status" value="0" ${proVO.status==0 ? 'checked' : '' }><label
												for="offshelf">下架</label> <input type="radio" id="onshelf"
												name="status" value="1" ${proVO.status==1 ? 'checked' : '' }><label
												for="onshelf">上架</label></td>
										</tr>
										<tr>
											<td><b>商品描述：</b></td>
											<td><pre>${proVO.pro_des}</pre></td>
											<td><textarea name="pro_des" rows="10" cols="50"
													placeholder="請輸入商品描述">${proVO.pro_des}</textarea></td>
										</tr>
								</table>
								<input type="hidden" name="pro_id" value="${proVO.pro_id}">
								<input type="hidden" name="action" value="update"> 
								<input type="submit" class="btn btn-info modify" value="確認修改">
								<input type="reset" class="btn btn-info modify" value="回復">

							</FORM>

							<script>
								var loadFile = function(event) {

									var output = document
											.getElementById('imgDisplay');
									output.src = URL
											.createObjectURL(event.target.files[0]);
									output.onload = function() {
										URL.revokeObjectURL(output.src) // free memory
									}
								};
							</script>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/back_end/footerMenu.jsp"%>

	</div>
	<%@ include file="/back_end/footerSection.jsp"%>
</body>
</html>