<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="M_Adnan" />
<title>檢舉紀錄</title>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.c2cpro_rep.model.*"%>
<%@ page import="com.c2cso_rep.model.*"%>
<%@ page import="java.util.stream.Collectors"%>
<%@ include file="/front_end/headerSection.jsp"%>
<%
	C2cproRepService main = new C2cproRepService();
	List<C2cproRepVO> replist = main.getAll();
	List<C2cproRepVO> list= new ArrayList<C2cproRepVO>();	
	list = replist.stream()
			.filter(p->p.getInformant().equals(memVO.getMem_id()))
			.collect(Collectors.toList());
	
	pageContext.setAttribute("list", list);
		
%>
<jsp:useBean id="c2cproMainSvc" scope="page" class="com.c2cpro_main.model.C2cproMainService" />


<style>
.slides, .slides>li, .flex-control-nav, .flex-direction-nav {
	margin: revert;
	padding: 0px;
	list-style: none;
	list-style-position: initial;
	list-style-image: initial;
	list-style-type: none;
}

.padding-top-100 {
	padding-top: 0px !important;
}

.item-decribe {
	margin-top: 39px;
}
.item-decribe form input {
    width: 5%;
    margin-top: 0px;
    padding: 0 0px;
    display: inline-block;
    height: 27px;
    border: 1px solid #ebebeb;
}
.item-decribe form {
    margin-top: 0px;
}
</style>

</head>
<body>
	<!-- LOADER -->
	<div id="loader">
		<div class="position-center-center">
			<div class="ldr"></div>
		</div>
	</div>

	<!-- Wrap -->
	<div id="wrap">
		<!-- header -->
		<%@ include file="/front_end/headerMenu.jsp"%>

		<!-- Content -->
		<div id="content"></div>

		<!-- Popular Products -->
		<section class="member-center_information margin-top-23 margin-bottom-23 padding-bottom-23 padding-top-23">
			<div class="container">
		<div class="col-sm-3 leftSlide">
        	<%@ include file="/front_end/membercenterLeftMenu.jsp" %>
        </div>
				<!-- Item Content -->
				<div class="col-sm-9">
				<h5 class="cart-ship-info" style="margin-top:50px; margin-bottom:30px;">檢舉紀錄</h5>
					<!--======= PRODUCT DESCRIPTION =========-->
					<!-- 商品檢舉 -->
							<div role="tabpanel" class="tab-pane fade in active" id="pro_Rep">
								<table class="table">
								<thead>
									<tr>
										<th>檢舉單編號</th>
										<th>被檢舉商品</th>
										<th>檢舉描述</th>
										<th>處理進度</th>
										<th>建立時間</th>
										<th>完成時間</th>
									</tr>
									</thead>
					              <%@ include file="/front_end/page1.file"%>
									<c:forEach var="c2cproRepVO" items="${list}"
										begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

										<tr>
											<td>${c2cproRepVO.rep_id}</td>
											<td class="text_length">${c2cproMainSvc.getOneC2cproMain(c2cproRepVO.reported_content).pro_name}</td>																					
											<td class="text_length">${c2cproRepVO.case_description}</td>
											<td><c:if test="${c2cproRepVO.process==0}">未處理</c:if> 
											<c:if test="${c2cproRepVO.process==1}">完成且處置</c:if> 
											<c:if test="${c2cproRepVO.process==2}">完成且不處置</c:if></td>
											<td><fmt:formatDate value="${c2cproRepVO.est_time}" pattern="yyyy/MM/dd" /></td>
											<td><fmt:formatDate value="${c2cproRepVO.finish_time}" pattern="yyyy/MM/dd HH:mm" /></td>
										</tr>
									</c:forEach>
								</table>
					              <%@ include file="/front_end/page2.file"%>
							</div>
					</div>
				</div>
				</section>
			</div>
		
		<%@ include file="/front_end/footerMenu.jsp"%>
		<%@ include file="/front_end/footerSection.jsp"%>
	
	<script>
		$(".text_length").each(function() {
			var maxwidth = 6;//設置最多顯示的字數
			var text = $(this).text();
			if ($(this).text().length > maxwidth) {
				$(this).text($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() + "...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
			};
		})
	</script>

</body>
</html>
