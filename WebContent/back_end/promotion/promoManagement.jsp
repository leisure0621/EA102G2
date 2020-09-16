<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.promo_main.model.* "%>
<%
	PromoMainService promoMainSvc = new PromoMainService();
	String query = (String) session.getAttribute("promoSearch");
	List<PromoMainVO> list = null;
	if (query == null || query.trim().length() == 0) {
		list = promoMainSvc.getAll();
	}
	else {
		list = promoMainSvc.getQuery(query);
	}
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("promoSearch", query);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>促銷方案管理</title>
	<%@ include file="/back_end/headerSection.jsp"%>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>
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
				<h4 class="page-title">促銷方案管理</h4>
				<ol class="breadcrumb">
					<li><a href="promoManagement.jsp">促銷管理</a></li>
					<li><a href="promoManagement.jsp">促銷方案列表</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->

			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>促銷列表</div>
							<div class="features">
								<div  class="btn btn-info add" onclick="location.href='addPromo.jsp'">新增促銷</div>
								<input type="text" class="form-control" name="query" value="${promoSearch}" placeholder="依促銷編號、促銷名稱搜尋" />
							</div>

						</h2>
						<div class="table-responsive">
							<table class="display table">
								<thead>
									<tr>
										<th>促銷編號</th>
										<th>促銷名稱</th>
										<th>促銷開始日期</th>
										<th>促銷結束日期</th>
										<th>促銷狀態</th>
										<th>修改</th>
										<th>查看促銷商品</th>
									</tr>
								</thead>
								<tbody>
									<jsp:useBean id="timeFormat" scope="page" class="java.util.Date" />

									<%@ include file="page1.file"%>
									<c:forEach var="promoMainVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<tr>
											<td>${promoMainVO.promo_id}</td>
											<td>${promoMainVO.promo_name}</td>
											<td>${promoMainVO.start_date}</td>
											<td>${promoMainVO.end_date}</td>
											<td>${promoMainVO.status == 1 ? "正常促銷": "取消促銷"}</td>
											<td>
												<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/promotion/promo.do" style="margin-bottom: 0px;">
													<button type="submit" class="btn btn-info update" name="${promoMainVO.start_date}" value="${promoMainVO.end_date}">修改</button>
													<input type="hidden" name="promo_id" value="${promoMainVO.promo_id}"> 
													<input type="hidden" name="action" value="getOneForUpdate">
												</FORM>
											</td>
											<td>
												<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/promotion/promoDetail.do" style="margin-bottom: 0px;">
													<button type="submit" class="btn btn-info">查看</button>
													<input type="hidden" name="promo_id" value="${promoMainVO.promo_id}"> 
													<input type="hidden" name="action" value="getByPromoIdForDisplay">
												</FORM>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%@ include file="page2.file"%>
						</div>
					</div>
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
			<!--End row-->
		</div>
		<!-- End Wrapper -->
		<%@ include file="/back_end/footerMenu.jsp"%>
	</div>
	<!--End main content -->
	<%@ include file="/back_end/footerSection.jsp"%>

</body>
<script>
	// 只能修改未開始的促銷
	$( document ).ready(function(){
 		$('.update').each(function(){
		 
			 var start_date = $(this).attr('name');
			 start_date = new Date(Date.parse(start_date)).getTime();
			 
			 var end_date = $(this).attr('value');
			 end_date = new Date(Date.parse(end_date)).getTime();
			 
			 var now = new Date().getTime(); //今天時間
		 
			 if(now > start_date || end_date < now ){
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
                url: "<%=request.getContextPath()%>/back_end/promotion/promo.do",
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