<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.bid_main.model.*, com.bid_detail.model.*"%>

<%
	List<BidDetailVO> list = (List) request.getAttribute("bidDetailVO");
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<title>競標紀錄資料</title>
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
					<li><a href="bidDetailManagement.jsp">競標紀錄管理</a></li>
					<li><a href="listOneBidDetail.jsp">競標紀錄紀錄</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->

			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>競標紀錄資料</div>
						</h2>
						<div class="table-responsive">
							<table class="display table">
								<tr>
									<th>競標編號</th>
									<th>出價者</th>
									<th>出價金額</th>
									<th>出價時間</th>
								</tr>
								<jsp:useBean id="timeFormat" scope="page" class="java.util.Date" />
								<c:forEach var="bidDetailVO" items="${list}">
									<tr>
										<td>${bidDetailVO.bid_id}</td>
										<td>${bidDetailVO.mem_id}</td>
										<td>$ ${bidDetailVO.bid_price}</td>
										<td><fmt:formatDate value="${bidDetailVO.bid_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									</tr>
								</c:forEach>
							</table>

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
</html>