<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.bid_main.model.*, com.bid_detail.model.* "%>

<%

	BidMainService bidMainSvc = new BidMainService();
	String query = (String) session.getAttribute("bidSearch");
	List<BidMainVO> list = null;
	if (query == null || query.trim().length() == 0) {
		list = bidMainSvc.getAll();
	}
	else {
		list = bidMainSvc.getQuery(query);
	}
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("bidSearch", query);

%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<title>競標管理</title>
	<%@ include file="/back_end/headerSection.jsp"%>
	
	<style>
		.status {
		    border: 0px;
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
				<h4 class="page-title">競標管理</h4>
				<ol class="breadcrumb">
					<li><a href="bidManagement.jsp">競標管理</a></li>
					<li><a href="bidManagement.jsp">競標列表</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->

			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>競標列表</div>
							<div class="features">
							<div  class="btn btn-info add" onclick="location.href='addBid.jsp'">新增競標</div>
								<input type="text" class="form-control" name="query" value="${bidSearch}" placeholder="依競標編號、競標內容、商品貨號搜尋" />
							</div>
						</h2>
						<%-- 錯誤表列 --%>
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
						<div class="table-responsive">
							<table id="bidList" class="display table">
								<thead>
									<tr>
										<th>競標編號</th>
										<th>競標標題</th>
										<th>商品貨號</th>
										<th>競標狀態</th>
										<th>得標會員編號</th>
										<th>競標開始時間</th>
										<th>競標結束時間</th>
										<th>修改競標資訊</th>
										<th>查看內容</th>
									</tr>
								</thead>
								<tbody>
									<jsp:useBean id="timeFormat" scope="page" class="java.util.Date" />

									<%@ include file="page1.file"%>
									<c:forEach var="bidMainVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<tr>
											<td>${bidMainVO.bid_id}</td>
											<td class = "bid_title">${bidMainVO.bid_title}</td>
											<td>${bidMainVO.pro_id}</td>
											<c:if test="${bidMainVO.status == 0}">
												 <td class="status" value="0">取消競標</td>
											</c:if>
											<c:if test="${bidMainVO.status == 1}">
												 <td class="status" value="1">正常競標</td>
											</c:if>
											<c:if test="${bidMainVO.status == 2}">
												 <td class="status" value="2">結束競標</td>
											</c:if> 
											<td>${bidMainVO.winner}</td>
											<td class="start_time"><fmt:formatDate value="${bidMainVO.start_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="end_time"><fmt:formatDate value="${bidMainVO.end_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>
												<FORM class="updateStatus" METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/bidding/bid.do" style="margin-bottom: 0px;">
													<button class="btn btn-info update" name="${bidMainVO.start_time}" value="${bidMainVO.end_time}">修改</button>
													<input type="hidden" name="bid_id" value="${bidMainVO.bid_id}"> 
													<input type="hidden" name="action" value="getOneForUpdate">
												</FORM>
											</td>
											<td>
												<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/bidding/bid.do" style="margin-bottom: 0px;">
													<button type="submit" class="btn btn-info">查看內容</button>
													<input type="hidden" name="action" value="getOneForDisplay" />
													<input type="hidden" name="bid_id" value="${bidMainVO.bid_id}" />
												</Form>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%@ include file="page2.file"%>
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

<script>
	// 字數過多限制
	$(function() {
		$('.bid_title').each(function() {
			var maxwidth = 15;//設置最多顯示的字數
			var text = $(this).text();
			if ($(this).text().length > maxwidth) {
				$(this).text($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() + "...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
			};
		});
	});
	
		// 只能修改尚未開始的競標
		$( document ).ready(function(){
		// 正常競標狀態需判斷為未開始前才能修改
	 	$('.update').each(function(){
			 
			 var start_time = $(this).attr('name');
			 start_time = new Date(Date.parse(start_time)).getTime();
			 
			 var end_time = $(this).attr('value');
			 end_time = new Date(Date.parse(end_time)).getTime();
			 
			 var now = new Date().getTime(); //今天時間
			 
			 if(now > start_time || end_time < now ){
				 $(this).prop("disabled",true);
			 } 
		 });
	 });
	 	
	 // 搜尋資料
	    $('.header-title [name="query"]').keydown(function(){
	        if (event.keyCode == 13) {
	        	data = {
	       			query: $(this).val(),
	       			action: 'search'
	        	}
	            $.ajax({
	                url: "<%=request.getContextPath()%>/back_end/bidding/bid.do",
	                type : 'post',
	                data : data,
	                datatype: 'json',
	                success : function(res) {
	                	window.location.reload();
	                }
	            });
	        };
	    });
		
</script>
</html>