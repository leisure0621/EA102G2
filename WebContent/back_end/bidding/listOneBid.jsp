<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.bid_main.model.*"%>

<%
	BidMainVO bidMainVO = (BidMainVO) request.getAttribute("bidMainVO"); 
%>

<!DOCTYPE html>
<html lang="en">
<head>
 	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>競標資料</title>
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
					<li><a href="listOneBid.jsp">競標詳情</a></li>
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
								<button type="button" class="btn btn-info add" onclick="location.href='addBid.jsp'">新增競標</button>
								<input type="text" class="form-control" name="query" value="${query}" placeholder="依競標編號、競標時間、商品貨號搜尋" />
							</div>
						</h2>

						<div class="table-responsive">
							<table class="display table">
								<jsp:useBean id="timeFormat" scope="page" class="java.util.Date" />
								<tr>
									<td>競標編號：</td>
									<td>${bidMainVO.getBid_id()}</td>
								</tr>
								<tr>
									<td>競標標題：</td>
									<td>${bidMainVO.getBid_title()}</td>
								</tr>
								<tr>
									<td>競標描述：</td>
									<td>${bidMainVO.getBid_des()}</td>
								</tr>
									<td>商品貨號：</td>
									<td>${bidMainVO.getPro_id()}</td>
								<tr>
									<td>起標價格：</td>
									<td>$ ${bidMainVO.getStart_price()}</td>
								</tr>
								<tr>
									<td>出價增額：</td>
									<td>$ ${bidMainVO.getIncr()}</td>
								</tr>
								<tr><td>競標狀態：</td>
									<c:if test="${bidMainVO.status == 0}">
										 <td>取消競標</td>
									</c:if>
									<c:if test="${bidMainVO.status == 1}">
										 <td>正常競標</td>
									</c:if>
									<c:if test="${bidMainVO.status == 2}">
										 <td>結束競標</td>
									</c:if>
								</tr>
								<tr>
									<td>得標會員編號：</td>
									<td>${bidMainVO.getWinner()}</td>
								</tr>
								<tr>
									<td>競標開始時間：</td>
									<td><fmt:formatDate value="${bidMainVO.getStart_time()}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>

								<tr>
									<td>競標結束時間：</td>
									<td><fmt:formatDate value="${bidMainVO.getEnd_time()}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
								<tr>
									<td>競標建立時間：</td>
									<td><fmt:formatDate value="${bidMainVO.getEst_time()}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
								<tr>
									<td>查看競標紀錄</td>
									<td>
										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/bidding/bidDetail.do" style="margin-bottom: 0px;">
											<button type="submit" class="btn btn-info">查看詳情</button>
											<input type="hidden" name="action" value="getOneByBidId" />
											<input type="hidden" name="bid_id" value="${bidMainVO.bid_id}" />
										</Form>
									</td>
								</tr>
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