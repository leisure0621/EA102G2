<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.promo_main.model.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>促銷方案</title>
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
					<li><a href="listOnePromo.jsp">促銷方案詳情</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->
			
			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>促銷方案</div>
						</h2>

						<div class="table-responsive">
							<table class="display table">
								<thead>
									<tr>
										<th>促銷編號</th>
										<th>促銷名稱</th>
										<th>促銷開始日期</th>
										<th>促銷結束日期</th>
										<th>促銷建立時間</th>
										<th>促銷狀態</th>
										<th>查看促銷商品</th>
									</tr>
								</thead>
								<tbody>
									<jsp:useBean id="timeFormat" scope="page" class="java.util.Date" />
									<tr>
										<td>${promoMainVO.promo_id}</td>
										<td>${promoMainVO.promo_name}</td>
										<td>${promoMainVO.start_date}</td>
										<td>${promoMainVO.end_date}</td>
										<td><fmt:formatDate value="${promoMainVO.est_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${promoMainVO.status == 1 ? '正常促銷' : '取消促銷'}</td>
										<td>
											<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/promotion/promoDetail.do" style="margin-bottom: 0px;">
												<button type="submit" class="btn btn-info">查看</button>
												<input type="hidden" name="promo_id" value="${promoMainVO.promo_id}"> 
												<input type="hidden" name="action" value="getByPromoIdForDisplay">
											</FORM>
										</td>
									</tr>
								</tbody>
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