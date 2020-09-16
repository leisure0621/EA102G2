<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.c2cso_rep.model.*"%>
<%
	C2csoRepService main = new C2csoRepService();
	List<C2csoRepVO> list = main.getAll();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<title>檢舉審核</title>

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
				<h4 class="page-title">C2C檢舉管理</h4>
				<ol class="breadcrumb">
					<li><a>檢舉管理</a></li>
					<li><a>檢舉列表</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->


			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>訂單檢舉列表</div>
							<div class="features">
								<input type="text" class="form-control" name="query"
									value="${query}" placeholder="" />
							</div>
						</h2>
						<div class="table-responsive">
							<table id="memlist" class="display table">
								<thead>
									<tr>
										<th>檢舉單編號</th>
										<th>檢舉人會員編號</th>
										<th>被檢舉訂單編號</th>
										<th>檢舉描述</th>
										<th>處理進度</th>
										<th>建立時間</th>
										<th>完成時間</th>

									</tr>
								</thead>
<!-- 								<tfoot> -->
<!-- 									<tr> -->
<!-- 										<th>檢舉單編號</th> -->
<!-- 										<th>檢舉人會員編號</th> -->
<!-- 										<th>被檢舉訂單編號</th> -->
<!-- 										<th>檢舉描述</th> -->
<!-- 										<th>處理進度</th> -->
<!-- 										<th>建立時間</th> -->
<!-- 										<th>完成時間</th> -->
<!-- 									</tr> -->
<!-- 								</tfoot> -->
								<tbody>
<%@ include file="/back_end/page1.file" %>
									<c:forEach var="c2csoRepVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                                        <tr data-mem="${memVO.mem_id}">
									<tr>
										<td>${c2csoRepVO.rep_id}</td>
										<td>${c2csoRepVO.informant}</td>
										<td>${c2csoRepVO.so_id}</td>
										<td>${c2csoRepVO.case_description}</td>
										<td>${c2csoRepVO.process}</td>
										<td>${c2csoRepVO.est_time}</td>
										<td>${c2csoRepVO.finish_time}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
<%@ include file="/back_end/page2.file" %>
						</div>
					</div>
				</div>

				<!--End row-->
			</div>
			<!-- End Wrapper-->

			<!-- Popup Large Modal -->

			<!--END Popup Large Modal -->

			<%@ include file="/back_end/footerMenu.jsp"%>

		</div>
		<!--End main content -->

		<%@ include file="/back_end/footerSection.jsp"%>

		<script>
			
		</script>
</body>
</html>
