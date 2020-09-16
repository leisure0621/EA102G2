<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="M_Adnan" />
<title>新增詳情</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.c2cpro_main.model.*"%>
<%@ page import="com.c2cpro_detail.model.*"%>
<%@ page import="com.spec_main.model.*"%>
<%
	String pro_id = (String) request.getAttribute("pro_id");
	C2cproMainVO c2cproMainVO = (C2cproMainVO) request.getAttribute("c2cproMainVO");
	Set<SpecMainVO> setC2cCat = (Set) request.getAttribute("setC2cCat");
	List<B2cjoinDetailVO> detailList = (List) request.getAttribute("detailList");
%>
<jsp:useBean id="b2cproMainSvc" scope="page"
	class="com.b2cpro_main.model.B2cproMainService" />
<jsp:useBean id="proSpecSvc" scope="page"
	class="com.pro_spec.model.ProSpecService" />
<jsp:useBean id="specDetailSvc" scope="page"
	class="com.spec_detail.model.SpecDetailService" />
<%@ include file="/front_end/headerSection.jsp"%>



<style>
.addproDetails {
	border: none;
	color: #fff;
	display: inline-block;
	padding: 5px 5px;
	text-transform: uppercase;
	font-weight: bold;
	font-size: 18px;
	border-radius: 0px;
	font-family: 'Montserrat', sans-serif;
	line-height: 24px;
	background: #2d3a4b;
	letter-spacing: 1px;
	position: relative;
	z-index: 1;
	margin-top: 16px;
}

table {
	margin-left: auto;
	margin-right: auto;
	width: 100%;
}

#content {
	background: #fff;
	position: relative;
	z-index: 1;
}

.item-decribe form [type="submit"] {
	margin-bottom: 50px;
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

		<div class="container">

		<!-- Content -->
		<div id="content">
			<!-- Products -->
			<div class="row">
			<div class="item-decribe col-sm-12">
			
				<h4>商品詳情新增</h4>
				<FORM METHOD="post" ACTION="c2cproDetail.do" name="form1">
					<table>
						<tr>
							<input type="hidden" name="mem_id" size="45"
								value="${memVO.getMem_id()}" />

							<input type="hidden" name="pro_id" value="${pro_id}">
						</tr>
						<c:if test="${detailList!=null}">
							<div style="text-align: center; color: red">本商城已有相對的詳情資料，可以直接送出新增!!</div>
							<c:forEach var="B2cjoinDetailVO" items="${detailList}">
								<tr>
									<td>${B2cjoinDetailVO.spec_des}</td>
									<td><input type="TEXT" name="${B2cjoinDetailVO.spec_id}"
										size="45" value="${B2cjoinDetailVO.detail_des}" /></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${detailList==null}">

							<c:forEach var="specMainVo" items="${setC2cCat}">
								<tr>
									<td>${specMainVo.spec_des}:</td>
									<td><input type="TEXT" name="${specMainVo.spec_id}"
										size="45" /></td>
								</tr>
							</c:forEach>

						</c:if>
					</table>
					<div class="btn btn-primary addproDetails">讓子軒解決你的煩惱</div>
					<br> <input type="hidden" name="action" value="insert_confirm">
					<input type="submit" value="送出新增">

				</FORM>

		    </div>
			</div>
			<!-- Products  end -->
		</div>
		</div>
		
		<%@ include file="/front_end/footerMenu.jsp"%>
		<%@ include file="/front_end/footerSection.jsp"%>
	</div>
</body>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<script>
	$('.addproDetails').click(function() {
		$('[name="SP0061"]').val("RTX2080Ti");
		$('[name="SP0062"]').val("32.7");
		$('[name="SP0063"]').val("1755MHz");
		$('[name="SP0064"]').val("4352");
		$('[name="SP0065"]').val("1*HDMI / 3*DP/1*USB Type-C");
		$('[name="SP0066"]').val("11GB GDDR6");
		$('[name="SP0067"]').val("300");
	});
</script>

</html>
