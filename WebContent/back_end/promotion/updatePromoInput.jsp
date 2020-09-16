<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.promo_main.model.*"%>

<%
	PromoMainVO promoMainVO = (PromoMainVO) request.getAttribute("promoMainVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>修改促銷方案</title>
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
					<li><a href="promoManagement.jsp">促銷方案管理</a></li>
					<li><a>修改促銷方案</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->
			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>修改促銷方案</div>
						</h2>

						<div class="table-responsive">
							<FORM METHOD="post" ACTION="promo.do" name="form1">
								<table class="display table">
									<tr>
										<td>促銷編號:</td>
										<td>${promoMainVO.getPromo_id()}</td>
									</tr>
									<tr>
										<td>促銷名稱:</td>
										<td><input type="TEXT" name="promo_name" size="45" value="${promoMainVO.getPromo_name()}" /></td>
									</tr>
									<tr>
										<td>促銷開始日期:</td>
										<td><input type="TEXT" name="start_date" id="start_date" size="45" value="${promoMainVO.getStart_date()}" /></td>
									</tr>
									<tr>
										<td>促銷結束日期:</td>
										<td><input type="TEXT" name="end_date" id="end_date" size="45" value="${promoMainVO.getEnd_date()}" /></td>
									</tr>
									<jsp:useBean id="timeFormat" scope="page" class="java.util.Date" />
									<tr>
										<td>促銷建立時間:</td>
										<td>
											<fmt:formatDate value="${promoMainVO.getEst_time()}" pattern="yyyy-MM-dd HH:mm:ss" /> 
											<input type="hidden" name="est_time" size="45" value="${promoMainVO.getEst_time()}" />
										</td>
									</tr>
									<tr>
										<td>促銷狀態:</td>
										<td><select name="status">
											<option value="1">正常促銷</option>
											<option value="0">取消促銷</option>
										</select></td>
									</tr>


								</table>
								<input type="hidden" name="action" value="update">
								<input type="hidden" name="promo_id" value="${promoMainVO.getPromo_id()}"> 
								<button type="submit" class="btn btn-info">送出修改</button>
							</FORM>

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
<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back_end/assets/js/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/back_end/assets/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/back_end/assets/js/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

<script>
	//日期選擇器
	var dateToday = new Date();
	
	$.datetimepicker.setLocale('zh'); // kr ko ja en
	$(function() {
		$('#start_date').datetimepicker(
			{
				setDate: dateToday,
				minDate: dateToday,
				format : 'Y-m-d',
				onShow : function() {
					this.setOptions({
						maxDate : $('#end_date').val() ? $('#end_date').val() : false
					})
				},
				timepicker : false,
				step : 1
			});
		$('#end_date').datetimepicker(
			{
				format : 'Y-m-d',
				onShow : function() {
					this.setOptions({
						minDate : $('#start_date').val() ? $('#start_date').val() : false
					})
				},
				timepicker : false,
				step : 1
			});
	});

</script>
</html>