<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.bid_detail.model.* "%>

<%
	
	BidDetailService bidDetailSvc = new BidDetailService();
	String query = (String) session.getAttribute("bidDetailSearch");
	List<BidDetailVO> list = null;
	if (query == null || query.trim().length() == 0) {
		list = bidDetailSvc.getAll();
	}
	else {
		list = bidDetailSvc.getQuery(query);
	}
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("bidDetailSearch", query);

%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>競標紀錄管理</title>

<%@ include file="/back_end/headerSection.jsp"%>

<style>
tr {
	height: 50px;
}

.modal li {
	list-style: none;
}

.modal-body {
	display: inherit;
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
				<h4 class="page-title">競標紀錄管理</h4>
				<ol class="breadcrumb">
					<li><a href="bidManagement.jsp">競標管理</a></li>
					<li><a href="bidDetailManagement.jsp">競標紀錄管理</a></li>
					<li><a href="bidDetailManagement.jsp">競標紀錄列表</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->

			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>競標紀錄列表</div>
							<div class="features">
								<input type="text" class="form-control" name="query"
									value="${bidDetailSearch}" placeholder="依競標編號、會員編號搜尋" />
							</div>
						</h2>
						<div class="table-responsive">
							<table id="bidDetailList" class="display table">
								<thead>
									<tr>
										<th>競標編號</th>
										<th>下標會員編號</th>
										<th>出價金額</th>
										<th>出價時間</th>
									</tr>
								</thead>
								<tbody>
									<jsp:useBean id="timeFormat" scope="page" class="java.util.Date" />
									<%@ include file="page1.file"%>
									<c:forEach var="bidDetailVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<tr>
											<td>${bidDetailVO.bid_id}</td>
											<td>${bidDetailVO.mem_id}</td>
											<td>$ ${bidDetailVO.bid_price}</td>
											<td><fmt:formatDate value="${bidDetailVO.bid_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
		<!-- 錯誤表列  -->
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
		<%@ include file="/back_end/footerMenu.jsp"%>
	</div>
	<!--End main content -->
	<%@ include file="/back_end/footerSection.jsp"%>

</body>
<script>
	//搜尋資料
	$('.header-title [name="query"]').keydown(function(){
	    if (event.keyCode == 13) {
	    	data = {
	   			query: $(this).val(),
	   			action: 'search'
	    	}
	        $.ajax({
	            url: "<%=request.getContextPath()%>/back_end/bidding/bidDetail.do",
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