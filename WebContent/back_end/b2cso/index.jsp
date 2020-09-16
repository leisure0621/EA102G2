<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>B2C訂單管理</title>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.b2cso_main.model.*, com.b2cso_status.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	B2cso_mainService bSvc = new B2cso_mainService();
	List<B2cso_mainVO> list = new LinkedList<B2cso_mainVO>();
	list = bSvc.getAll();
	request.setAttribute("list", list);
	
	B2cso_statusService b2cso_statusService = new B2cso_statusService();
	List<B2cso_statusVO> b2cso_statusList = new LinkedList<B2cso_statusVO>();
	b2cso_statusList = b2cso_statusService.getAll();
	request.setAttribute("b2cso_statusList", b2cso_statusList);
%>
<%@ include file="/back_end/headerSection.jsp"%>
<title>Insert title here</title>
</head>
<body class="sticky-header">
	<%@ include file="/back_end/leftSideMenu.jsp"%>

	<!-- main content start-->
	<div class="main-content">
		<%@ include file="/back_end/headerMenu.jsp"%>
		<div class="wrapper">
			<!--Start Page Title-->
			<div class="page-title-box">
				<h4 class="page-title">B2C訂單管理</h4>
				<ol class="breadcrumb">
					<li><a>B2C訂單列表</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>B2C訂單列表</div></h2>
							<div class="table-responsive">
							<jsp:useBean id="sSvc" class="com.b2cso_status.model.B2cso_statusService"></jsp:useBean>
								<table class="display table">
									<thead>
										<tr>
											<th>訂單編號</th>
											<th>訂單類別</th>
											<th>買家會員編號</th>
											<th>寄送方式</th>
											<th>付款方式</th>
											<th>收件人</th>
											<th>寄送地址</th>
											<th>總額</th>
											<th>訂單建立日期</th>
											<th>訂單狀態</th>
										</tr>
									</thead>
									<tbody>
										<%@ include file="/back_end/page1.file"%>
										<c:forEach
										var="aSO"
										items="${list}" 
										begin="<%=pageIndex%>"
										end="<%=pageIndex+rowsPerPage-1%>"
										>
											<tr>
											<td>${aSO.so_id}</td>
											<c:choose>
												<c:when test = "${aSO.type eq '1'}">
													<td>B2C</td>
												</c:when>
												<c:when test = "${aSO.type eq '2'}">
													<td>組裝</td>
												</c:when>
												<c:when test = "${aSO.type eq '3'}">
													<td>競標</td>
												</c:when>
											</c:choose>
											<td>${aSO.buyer_id}</td>
											<c:choose>
												<c:when test = "${aSO.delivery eq '1'}">
													<td>宅配到府</td>
												</c:when>
												<c:when test = "${aSO.delivery eq '2'}">
													<td>便利商店取貨</td>
												</c:when>
												<c:when test = "${aSO.delivery eq '3'}">
													<td>現場取貨</td>
												</c:when>
											</c:choose>
											<td>${aSO.pay_via eq '1'? '信用卡' : '現金'} </td>
											<td>${aSO.recipient}</td>
											<td>${aSO.del_address}</td>
											<td>${aSO.amount}</td>
											<td><fmt:formatDate value="${aSO.est_time}" pattern="yyyy-MM-dd" /></td>
											<td>
												<select class="changeStatus form-control" data-so_id="${aSO.so_id}">
												<c:forEach
												  var="b2cso_statusVO"
												  items="${b2cso_statusList}" 
												>
												  <option 
												    value="${ b2cso_statusVO.status_id }"
												    ${b2cso_statusVO.status_id eq sSvc.getOneB2Cso_status(aSO.status).status_id ? 'selected' : ''}
												  >${b2cso_statusVO.status_des}</option>
												</c:forEach>
												</select>
											</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<th>訂單編號</th>
											<th>訂單類別</th>
											<th>買家會員編號</th>
											<th>寄送方式</th>
											<th>付款方式</th>
											<th>收件人</th>
											<th>寄送地址</th>
											<th>總額</th>
											<th>訂單建立日期</th>
											<th>訂單狀態</th>
										</tr>
									</tfoot>
								</table>
								<%@ include file="/back_end/page2.file" %>
              				</div>
						</div>
					</div>
				</div>
			</div>
			<%@ include file="/back_end/footerMenu.jsp" %>
		</div>
		<%@ include file="/back_end/footerSection.jsp" %>
		<script>
		$('.changeStatus').on('change', function(){
			let data = {};
			data.action = 'changeStatus';
			data.so_id = $(this).data('so_id');
			data.status = $(this).val();
			$.ajax({
	              type: "POST",
	              url: "<%=request.getContextPath()%>/b2cso/b2cso.do",
	              data: data,
	              datatype: 'json',
	              success: function (res) {
	                res = JSON.parse(res);
	                if (res.data) {
	                  res = res.data;
	                  alert("修改成功");
	                  location.reload();
	                } else {
	                  res = res.err;
	                  alert("修改失敗");
	                }
	              }
	          });
	    });
		</script>
</body>
</html>