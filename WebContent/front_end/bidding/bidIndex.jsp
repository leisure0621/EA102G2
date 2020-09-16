<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bid_main.model.*,java.util.*, java.text.*"%>

<jsp:useBean id="bdSvc" scope="page" class="com.bid_detail.model.BidDetailService" />
<jsp:useBean id="b2cproSvc" scope="page" class="com.b2cpro_main.model.B2cproMainService" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>競標商品瀏覽</title>
	<%@ include file="/front_end/headerSection.jsp"%>

	<style>
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
								<div class="item-display">
									<div class="row">
										<div class="col-xs-6">
											<span class="product-num">Showing 1 - 10 of products</span>
										</div>
									</div>
								</div>

								<!-- Popular Item Slide -->
								<div class="papular-block row">
									<%
										BidMainService bidMainSvc = new BidMainService();
										List<BidMainVO> list = bidMainSvc.getAll();
										pageContext.setAttribute("bidList", list);

										for (BidMainVO aBid : list) {
											SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											java.util.Date nowTime = java.sql.Timestamp.valueOf(df.format(new java.util.Date()));
											java.util.Date endTime = java.sql.Timestamp.valueOf(df.format(aBid.getEnd_time()));
											java.util.Date startTime = java.sql.Timestamp.valueOf(df.format(aBid.getStart_time()));
											boolean flag = endTime.after(nowTime) && startTime.before(nowTime);

											if (flag) {
												pageContext.setAttribute("aBid", aBid);
									%>

									<!-- Item -->

									<c:forEach var="aProVO" items="${b2cproSvc.all}">
										<c:if test="${aProVO.pro_id==aBid.getPro_id()}">

											<div class="col-md-4">
												<form method="post" id="form1" name="form1" action="<%=request.getContextPath()%>/front_end/bidding/FrontBidDetailSertvlet.do">
													<button type="submit">
														<div class="item">

															<!-- Item img -->
															<div class="item-img">
																<input type="hidden" name="action" value="getOneByBidIdToFront" /> 
																<input type="hidden" name="bid_id" value="${aBid.getBid_id()}" /> 
																<input type="hidden" name="pro_id" value="${aProVO.pro_id}" />
																<img class="img" width="270" height="352" src="<%= request.getContextPath()%>/front_end/front_b2cpro_main/front_b2cpro_main.do?pro_id=${aProVO.pro_id}">
															</div>
															<!-- Item Name -->
															<div class="item-name">
																<span class="bid_title">${aBid.getBid_title()}</span>
															</div>
															<!-- Price -->
															<span class="price">$ ${aBid.getStart_price()} <small>起標</small></span>
														</div>
													</button>
												</form>
											</div>
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
		// 字數過多限制
		$(function() {
			$('.bid_title').each(function() {
				var maxwidth = 20;//設置最多顯示的字數
				var text = $(this).text();
				if ($(this).text().length > maxwidth) {
					$(this).text($(this).text().substring(0, maxwidth));
					$(this).html($(this).html() + "...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
				};
			});
		});
		
		
	</script>
</body>
</html>