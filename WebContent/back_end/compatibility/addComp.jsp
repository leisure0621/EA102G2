<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page
	import="com.b2cpro_main.model.*,com.catagory.model.*,com.spec_main.model.*,com.spec_detail.model.*,com.compatibility.model.*"%>

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
<title>Add New Comp</title>
<%@ include file="/back_end/headerSection.jsp"%>
<jsp:useBean id="cSvc" scope="page"
	class="com.catagory.model.CatagoryService" />

</head>
<body class="sticky-header">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/back_end/assets/js/jquery.min.js"></script>
	<script type="text/javascript">

	
	$(document).ready(function(){
		let data = {};
		$('#catagory').change(function(){
			data.cat_id = $(this).val();
			data.action = "getCat";
			console.log(data);
			$.ajax({
				type:"GET",
				url: "<%=request.getContextPath()%>/back_end/compatibility/comp.do",
				data : data,
				datatype : 'json',
				success : function(data) {
					clearSelect();
					data = JSON.parse(data);
					$.each(data,function(i,item) {
						$('#specs').append('<option value="'+data[i].spec_id+'">【'+ data[i].spec_id+ '】'+ data[i].spec_des+'</option>');
					})
				},
				error : function() {alert("有錯啦!")}
			})
		})
		$('#specs').change(function(){
			data.spec_id = $(this).val();
			data.action = "getSpec";
			console.log(data);
			$.ajax({
				type:"GET",
				url: "<%=request.getContextPath()%>/back_end/compatibility/comp.do",
				data : data,
				datatype : 'json',
				success : function(data) {
					clearSelect2();
					data = JSON.parse(data);
					$.each(data,function(i,item) {
						$('#specDs').append('<option value="'+data[i].specdet_id+'">【'+ data[i].specdet_id+ '】'+ data[i].detail_des+'</option>');
					})
				},
				error : function() {alert("有錯啦!")}
			})
		})
		$('#catagory2').change(function(){
			data.cat_id = $(this).val();
			data.action = "getCat";
			console.log(data);
			$.ajax({
				type:"GET",
				url: "<%=request.getContextPath()%>/back_end/compatibility/comp.do",
				data : data,
				datatype : 'json',
				success : function(data) {
					clearSelect3();
					data = JSON.parse(data);
					$.each(data,function(i,item) {
						$('#specs2').append('<option value="'+data[i].spec_id+'">【'+ data[i].spec_id+ '】'+ data[i].spec_des+'</option>');
					})
				},
				error : function() {alert("有錯啦!")}
			})
		})
		$('#specs2').change(function(){
			data.spec_id = $(this).val();
			data.action = "getSpec";
			console.log(data);
			$.ajax({
				type:"GET",
				url: "<%=request.getContextPath()%>/back_end/compatibility/comp.do",
				data : data,
				datatype : 'json',
				success : function(data) {
					clearSelect4();
					data = JSON.parse(data);
					$.each(data,function(i,item) {
						$('#specDs2').append('<option value="'+data[i].specdet_id+'">【'+ data[i].specdet_id+ '】'+ data[i].detail_des+'</option>');
					})
				},
				error : function() {alert("有錯啦!")}
			})
		})
	});
	
	

		function clearSelect() {
			$('#specs').empty();
			$('#specs').append("<option value='-1'>請選擇</option>");
			$('#specDs').empty();
			$('#specDs').append("<option value='-1'>請選擇</option>");
		}
		function clearSelect2(){
			$('#specDs').empty();
			$('#specDs').append("<option value='-1'>請選擇</option>");
		}
		function clearSelect3() {
			$('#specs2').empty();
			$('#specs2').append("<option value='-1'>請選擇</option>");
			$('#specDs2').empty();
			$('#specDs2').append("<option value='-1'>請選擇</option>");
		}
		function clearSelect4(){
			$('#specDs2').empty();
			$('#specDs2').append("<option value='-1'>請選擇</option>");
		}
	</script>
	<%@ include file="/back_end/leftSideMenu.jsp"%>
	<div class="main-content">
		<%@ include file="/back_end/headerMenu.jsp"%>
		<div class="wrapper">
			<div class="page-title-box">
				<h4 class="page-title">相容性管理</h4>
				<ol class="breadcrumb">
					<li><a
						href="<%=request.getContextPath()%>/back_end/compatibility/listAllComp.jsp">所有相容性列表</a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/compatibility/addComp.jsp">綁定相容性</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
		<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>所有相容性列表</div>
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
	
	<form method='post' action='comp.do'>
	<p>請選擇規格內容</p>
		<br>
	<select size="1" name="cat_id" id="catagory">
		<option value="0">請選擇</option>
		<c:forEach var="catVOs" items="${cSvc.getAll()}">
			<option value="${catVOs.cat_id}"
				${catVOs.cat_id==proVO.cat_id ? 'selected' : ''}>
				【${catVOs.cat_id}】${catVOs.cat_des}</option>
		</c:forEach>
	</select>
	<select id='specs'>
		<option value="-1">請選擇</option>
	</select>
	<select id='specDs' name='specdet_id1'>
		<option value="-1">請選擇</option>
	</select>
	<br>
	<br>
	<p>請選擇欲配對的規格內容</p>
		<br>
	<select size="1" name="cat_id" id="catagory2">
		<option value="0">請選擇</option>
		<c:forEach var="catVOs" items="${cSvc.getAll()}">
			<option value="${catVOs.cat_id}"
				${catVOs.cat_id==proVO.cat_id ? 'selected' : ''}>
				【${catVOs.cat_id}】${catVOs.cat_des}</option>
		</c:forEach>
	</select>
	<select id='specs2'>
		<option value="-1">請選擇</option>
	</select>
	
	<select id='specDs2' name='specdet_id2'>
		<option value="-1">請選擇</option>
	</select>
	<br>
	<br>
	<input type="hidden" name="action" value="addComp">
	<input type="submit" class="btn btn-info" value="綁定相容性">
	</form>	</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/back_end/footerMenu.jsp"%>

	<%@ include file="/back_end/footerSection.jsp"%>
</body>
</html>