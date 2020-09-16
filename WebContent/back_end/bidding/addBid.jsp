<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.bid_main.model.*"%>

<%
	BidMainVO bidMainVO = (BidMainVO) request.getAttribute("bidMainVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<title>競標資料新增</title>
	
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
					<li><a href="addBid.jsp">新增競標</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->
			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>新增競標</div>
						</h2>
						
						<div class="table-responsive">
							<FORM METHOD="post" ACTION="bid.do" name="form1">
								<table class="display table">
									<tr>
										<td>競標標題:</td>
										<td><input type="TEXT" name="bid_title" size="50"value="${bidMainVO.bid_title}" /></td>
									</tr>
									<tr>
										<td>競標敘述：</td>
										<td><textarea name="bid_des" rows="5" cols="50"> ${bidMainVO.bid_des}</textarea></td>
									</tr>
									<jsp:useBean id="bproSvc" scope="page" class="com.b2cpro_main.model.B2cproMainService" />
									<tr>
										<td>產品編號:</td>
										<td>
											<select size="1" name="pro_id">
												<option>請選擇</option>
												<c:forEach var="bproVO" items="${bproSvc.all}">
													<option value="${bproVO.pro_id}">${bproVO.pro_id}【${bproVO.pro_name}】</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td>起標價格:</td>
										<td><input type="TEXT" name="start_price" value="${bidMainVO.start_price}" /></td>
									</tr>
									<tr>
										<td>出價增額:</td>
										<td><input type="TEXT" name="incr" value="${bidMainVO.incr}" /></td>
									</tr>
									<tr>
										<td>競標狀態:</td>
										<td>
											<select name="status">
												<option value="1" selected>正常競標</option>
												<option value="0">取消競標</option>
											</select>
										</td>
									</tr>
									<tr>
										<td>競標開始時間:</td>
										<td><input type="TEXT" name="start_time" id="start_dateTime" /></td>
									</tr>
									<tr>
										<td>競標結束時間:</td>
										<td><input type="TEXT" name="end_time" id="end_dateTime"></td>
									</tr>

								</table>
								<input type="hidden" name="action" value="insert">
								<button type="submit" class="btn btn-info">送出新增</button>
								<button type="button" class="btn btn-info" id="magicBtn">神奇小按鈕</button>
							</FORM>

							<!-- 錯誤表列 -->
							<div margin="30px">
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

<%
	java.sql.Timestamp start_time = null;
	try {
		start_time = bidMainVO.getStart_time();
	} catch (Exception e) {
		start_time = new java.sql.Timestamp(System.currentTimeMillis());
	}

	java.sql.Timestamp end_time = null;
	try {
		end_time = bidMainVO.getEnd_time();
	} catch (Exception e) {
		end_time = new java.sql.Timestamp(System.currentTimeMillis());
	}
%>

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
						maxDate : $('#end_dateTime').val() ? $('#end_dateTime').val() : false
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
						minDate : $('#start_dateTime').val() ? $('#start_dateTime').val() : false
					})
				},
				timepicker : true,
				step : 1
			});
	});
	// 神奇小按鈕
	$('#magicBtn').click(function(){
		$('[name = "bid_title"]').val('WD 1TB 行動硬碟限時三分鐘下殺500!!!');
		$('[name = "bid_des"]').val('USB3.0 2.5吋行動硬碟，WD三年原廠保固，可設定密碼、快速自動備份及還原，原價2299現在500起!!!');
		$('[name = "pro_id"]').val('BP0031');
		$('[name = "start_price"]').val('500');
		$('[name = "incr"]').val('200');
		$('[name = "status"]').val('1');
// 		$('[name = "start_time"]').val('2020-09-17 10:00:00');
// 		$('[name = "end_time"]').val('2020-09-17' 10:03:00);
	});
	
</script>
</html>