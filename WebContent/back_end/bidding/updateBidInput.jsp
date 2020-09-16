<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.bid_main.model.*"%>

<%
	BidMainVO bidMainVO = (BidMainVO) request.getAttribute("bidMainVO"); //BidMainServlet.java (Concroller) 存入req的bidMainVO物件 (包括幫忙取出的bidMainVO, 也包括輸入資料錯誤時的bidMainVO物件)
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>競標資料修改</title>
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
				<h4 class="page-title">競標管理</h4>
				<ol class="breadcrumb">
					<li><a href="bidManagement.jsp">競標管理</a></li>
					<li><a>修改競標資料</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->
			
			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>修改競標資訊</div>
						</h2>

						<div class="table-responsive">
							<FORM METHOD="post" ACTION="bid.do" name="form1">
								<table class="display table">
									<tr>
										<td>競標編號:</td>
										<td><%=bidMainVO.getBid_id()%></td>
									</tr>
									<tr>
										<td>競標標題:</td>
										<td><input type="TEXT" name="bid_title" size="50" value="<%=bidMainVO.getBid_title()%>" /></td>
									</tr>
									<tr>
										<td>競標敘述：</td>
										<td><textarea name="bid_des" rows="5" cols="50">${bidMainVO.bid_des}</textarea></td>
									</tr>
									
									<jsp:useBean id="bproSvc" scope="page" class="com.b2cpro_main.model.B2cproMainService" />
									<tr>
										<td>產品編號:</td>
										<td>
											<select size="1" name="pro_id">
												<c:forEach var="bproVO" items="${bproSvc.all}">
													<option value="${bproVO.pro_id}" ${(bidMainVO.pro_id==bproVO.pro_id)? 'selected':'' }> ${bproVO.pro_id}【${bproVO.pro_name}】</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td>起標價格:</td>
										<td><input type="TEXT" name="start_price" size="45" value="<%=bidMainVO.getStart_price()%>" /></td>
									</tr>
									<tr>
										<td>出價增額:</td>
										<td><input type="TEXT" name="incr" size="45" value="<%=bidMainVO.getIncr()%>" /></td>
									</tr>
									<tr>
										<td>競標狀態:</td>
										<td>
											<select name="status">
												<option value="1">正常競標</option>
												<option value="0">取消競標</option>
											</select>
										</td>
									</tr>
									
									<tr>
										<td>得標者:</td>
										<td>
											<input type="TEXT" name="winner" size="45" value="<%=(bidMainVO.getWinner() == null) ? "" : bidMainVO.getWinner()%>" />
										</td>
									</tr>
									<tr>
										<td>競標開始時間:</td>
										<td>
											<input type="TEXT" name="start_time" id="start_dateTime" size="45" value="<fmt:formatDate value="${bidMainVO.getStart_time()}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
										</td>
									</tr>
									<tr>
										<td>競標結束時間:</td>
										<td>
											<input type="TEXT" name="end_time" id="end_dateTime" size="45" value="<fmt:formatDate value="${bidMainVO.getEnd_time()}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
										</td>
									</tr>
									<tr>
										<td>競標建立時間:</td>
										<td><fmt:formatDate value="${bidMainVO.getEst_time()}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<input type="hidden" name="est_time" value="<%=bidMainVO.getEst_time()%>" />
									</tr>
								</table>
								<input type="hidden" name="action" value="update"> 
								<input type="hidden" name="bid_id" value="<%=bidMainVO.getBid_id()%>">
								<button type="submit" class="btn btn-info">送出修改</button>
							</FORM>
		
							<!-- 錯誤表列 -->
							<div margin = "30px">
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

<!-- 參考網站: https://xdsoft.net/jqplugins/datetimepicker/ -->
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
	// 日期選擇器
	var dateToday = new Date();
	$.datetimepicker.setLocale('zh'); // kr ko ja en
	$(function() {
		$('#start_dateTime').datetimepicker(
			{
				setDate: dateToday,
				minDate: dateToday,
				format : 'Y-m-d H:i:s',
				onShow : function() {
					this.setOptions({
						maxDate : $('#end_dateTime').val() ? $(
								'#end_dateTime').val() : false
					})
				},
				timepicker : true,
				step : 1
			});

		$('#end_dateTime').datetimepicker(
			{
				format : 'Y-m-d H:i:s',
				onShow : function() {
					this.setOptions({
						minDate : $('#start_dateTime').val() ? $(
								'#start_dateTime').val() : false
					})
				},
				timepicker : true,
				step : 1
			});
	});
</script>
</html>