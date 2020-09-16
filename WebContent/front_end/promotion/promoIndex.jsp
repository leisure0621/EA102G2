<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.promo_main.model.*, java.util.*, java.text.*, java.io.*"%>

<jsp:useBean id="b2cproSvc" scope="page" class="com.b2cpro_main.model.B2cproMainService" />
<jsp:useBean id="pmSvc" scope="page" class="com.promo_main.model.PromoMainService" />
<jsp:useBean id="pdSvc" scope="page" class="com.promo_detail.model.PromoDetailService" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>促銷商品瀏覽</title>
	<%@ include file="/front_end/headerSection.jsp"%>
	<style>
		.on-sale {
			background: red;
		}
		
		.on-sale span {
			font-size: 18px;
			font-weight: normal;
		}
		
		.papular-block .overlay {
			background: rgba(255, 255, 255, .15);
		}
		
		button {
			background: rgba(255, 255, 255, .15);
			border: 1px;
		}
		
		.shop-page .item {
			margin-bottom: 30px;
			position: relative;
		}
		
		.papular-block .item-img {
			position: relative;
			overflow: hidden;
		}
		
		[name="pro_name"] {
			border: 1px solid #2d3a4b;
			width: 60px;
			height: 44px;
			float: left;
			width: 100%;
			font-size: 11px;
			padding: 0 10px;
			letter-spacing: 1px;
			display: inline-block;
			font-weight: normal;
			text-align: left;
			-webkit-transition: all 0.4s ease-in-out;
			-moz-transition: all 0.4s ease-in-out;
			-o-transition: all 0.4s ease-in-out;
			-ms-transition: all 0.4s ease-in-out;
			transition: all 0.4s ease-in-out;
		}
		
		.active a {
			color: pink;
		}
		
		.item-name {
			font-size: 18px;
			font-weight: bold;
			z-index: 9;
			display: inline-block;
			width: 100%;
			text-align: center;
			margin-top: 20px;
			color: #2d3a4b;
			-webkit-transition: 0.4s ease-in-out;
			-moz-transition: 0.4s ease-in-out;
			-ms-transition: 0.4s ease-in-out;
			-o-transition: 0.4s ease-in-out;
			transition: 0.4s ease-in-out;
		}
	</style>

</head>
<body class="sticky-header">
	<%@ include file="/front_end/headerMenu.jsp"%>

	<!-- main content start-->
	<div class="main-content">
		<!-- LOADER -->
		<div id="loader">
			<div class="position-center-center">
				<div class="ldr"></div>
			</div>
		</div>

		<!-- Wrap -->
		<div id="wrap">

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

			<!-- Content -->
			<div id="content">

				<!-- Products -->
				<section class="shop-page padding-top-100 padding-bottom-100">
					<div class="container">
						<div class="row">

							<!-- Item Content -->
							<div class="col-sm-12">

								<!-- Popular Item Slide -->
								<div class="papular-block row">

									<%
										PromoMainService promoMainSvc = new PromoMainService();
										List<PromoMainVO> list = promoMainSvc.getAll();
										pageContext.setAttribute("list", list);

										for (PromoMainVO aPromo : list) {
											SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
											java.util.Date nowDate = java.sql.Date.valueOf(df.format(new java.util.Date()));
											java.util.Date endDate = java.sql.Date.valueOf(df.format(aPromo.getEnd_date()));
											java.util.Date startDate = java.sql.Date.valueOf(df.format(aPromo.getStart_date()));
											boolean flag = endDate.after(nowDate) && startDate.before(nowDate);

											if (flag) {
												pageContext.setAttribute("promo_id", aPromo.getPromo_id());
									%>
									<!-- Item -->
									<c:forEach var="aPromoDetailVO" items="${pdSvc.all}">
										<c:if test="${aPromoDetailVO.promo_id == promo_id}">

											<c:forEach var="aProVO" items="${b2cproSvc.all}">
												<c:if test="${aProVO.pro_id==aPromoDetailVO.pro_id}">
												
													<div class="col-md-4">
														<form method="post" id="form1" name="form1" action="<%=request.getContextPath()%>/showDetail.do">
															<button type="submit">
																<div class="item">
																	<!-- Sale Tags -->
																	<div class="on-sale">
																		<span>ON SALE</span>
																	</div>
																	<!-- Item img -->
																	<div class="item-img">
																		<input type="hidden" name="action" value="getProductForDisplay" /> 
																		<input type="hidden" name="promo_id" value="${aPromoDetailVO.promo_id}" />
																		<input type="hidden" name="pro_id" value="${aPromoDetailVO.pro_id}" /> 
																		<img class="img" width="270" height="352" src="<%= request.getContextPath()%>/front_end/front_b2cpro_main/front_b2cpro_main.do?pro_id=${aProVO.pro_id}">
																		<!-- yellow -->
																		<div class="overlay">
																			<div class="position-center-center"></div>
																		</div>
																	</div>

																	<!-- Item Name -->
																	<div class="item-name">
																		<span class="pro_name">${aProVO.pro_name}</span>
																	</div>

																	<!-- Price -->
																	<span class="pro_price">$ ${aPromoDetailVO.promo_price}</span>
																</div>

															</button>
														</form>

													</div>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>

									<%
										}
									}
									%>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
			<!-- End Content -->
		</div>
		<!-- End Wrap -->
		<%@ include file="/front_end/footerSection.jsp"%>
		<%@ include file="/front_end/footerMenu.jsp"%>
	</div>
	<!--End main content -->
	
<script>
	// 字數過長設置
	$(function(){
		$(".pro_name").each(function(){
	        var maxwidth=20;//設置最多顯示的字數
	        var text=$(this).text();
	        if($(this).text().length>maxwidth){
	            $(this).text($(this).text().substring(0,maxwidth));
	            $(this).html($(this).html()+"...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
	        };
		});
	});

</script>
</body>
</html>