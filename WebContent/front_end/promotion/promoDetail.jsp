<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.b2cpro_main.model.*, com.promo_main.model.*, com.promo_detail.model.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>促銷商品瀏覽</title>

	<%
		String pro_id = request.getParameter("pro_id");
		String promo_id = request.getParameter("promo_id");
		
		B2cproMainService proSvc = new B2cproMainService();
		B2cproMainVO aProVO = proSvc.findByPrimaryKey(pro_id);
		pageContext.setAttribute("aProVO",aProVO);
		
		PromoMainService promSvc = new PromoMainService();
		PromoMainVO promoMainVO = promSvc.getByPrimaryKey(promo_id);
		pageContext.setAttribute("promoMainVO",promoMainVO);
		
		PromoDetailService prodSvc = new PromoDetailService();
		PromoDetailVO promoDetailVO = prodSvc.getOneForUpdate(promo_id, pro_id);
		pageContext.setAttribute("promoDetailVO", promoDetailVO);
		
		if(pro_id != null){
			session.setAttribute("forword_page", request.getRequestURI()+"?pro_id="+aProVO.getPro_id()+"&promo_id="+promoMainVO.getPromo_id());
		}
	%>

	<%@ include file="/front_end/headerSection.jsp"%>

	<style>
		div .addToKart {
			border: none;
			color: #fff;
			display: inline-block;
			padding: 5px 30px;
			text-transform: uppercase;
			font-weight: bold;
			font-size: 18px;
			font-family: 'Montserrat', sans-serif;
			line-height: 46px;
			background: #2d3a4b;
			letter-spacing: 1px;
			position: relative;
			z-index: 1;
			margin: 10px;
			margin-left: 10px;
		}
		
		#quantity {
		    width: 155px;
		    height: 56px;
		    padding: 15px;
	    }
		
		.addToKart:hover {
		    background: #ffe115;
		    color: #a94442;
		}
		
		.shop-detail .item-owner li span {
			color: #999;
		}
		
		.item-decribe {
			margin: 50px 150px;
		}
		
		span.original_price {
			margin: 10px 15px;
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

					<!-- Content -->
					<div id="content">

						<!-- Popular Products -->
						<section class="padding-top-100 padding-bottom-100">
							<div class="container">

								<!-- SHOP DETAIL -->
								<div class="shop-detail">
									<div class="row">

										<!-- Popular Images Slider -->
										<div class="col-md-7">


											<!-- Images -->
											<div class="images-slider">
												<ul class="slides">
													<li data-thumb="images/large-img-1.jpg">
														<img class="img" width="450" height="500" src="<%= request.getContextPath()%>/front_end/front_b2cpro_main/front_b2cpro_main.do?pro_id=${aProVO.pro_id}">
													</li>												
												</ul>
											</div>
										</div>

										<!-- Content -->
										<div class="col-md-5">

											<h4>${aProVO.pro_name}</h4>
											<span class="original_price" style="font-style: italic; text-decoration: line-through;">$ ${aProVO.rrp}</span> 
											<span class="promo_price" style="color: red">$ ${promoDetailVO.promo_price}</span>
											<ul class="item-owner">
												<li>促銷名稱： <span>${promoMainVO.promo_name}</span></li>
												<li>商品編號： <span>${promoDetailVO.pro_id}</span></li>
												<li>商品名稱： <span>${aProVO.pro_name}</span></li>
												<li>促銷開始時間： <span>${promoMainVO.start_date}</span></li>
												<li>促銷結束時間： <span>${promoMainVO.end_date}</span></li>
												<li>庫存量： <span> 剩餘 ${aProVO.stock} 個</span></li>
											</ul>
											
											<!--  購物車按鈕 -->
												<input id="quantity" type="number" value="1" min="1" max="${aProVO.stock}"/> 
												<button type="button" class="addToKart" data-pro_id="${aProVO.pro_id}"
														data-pro_name="${aProVO.pro_name}" data-cat_id="${aProVO.cat_id}"
														data-price="${promoDetailVO.promo_price}">加入購物車</button>
										</div>
									</div>
								</div>
								<!--======= PRODUCT DESCRIPTION =========-->
				<div class="item-decribe">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs animate fadeInUp" data-wow-delay="0.4s" role="tablist">
						<li role="presentation" class="active">
							<a href="#proDes" role="tab" data-toggle="tab">商品資訊</a>
						</li>
						<li role="presentation">
							<a href="#proSpec" role="tab" data-toggle="tab">商品規格</a>
						</li>

					</ul>

					<!-- Tab panes -->
					<div class="tab-content animate fadeInUp" data-wow-delay="0.4s">
						<!-- 商品資訊 -->
						<div role="tabpanel" class="tab-pane fade in active" id="proDes">
							<p><strong>商品資訊</strong></p>

								<li>
									<p>商品編號： ${aProVO.pro_id}</p>
								</li>
								<li>
									<p>商品名稱： ${aProVO.pro_name}</p>
								</li>
								<li>
									<jsp:useBean id="catSvc" scope="page" class="com.catagory.model.CatagoryService" />
									<p>商品類別：${catSvc.findByPrimaryKey(aProVO.cat_id).cat_des}</p>
								</li>
								<li>
									<jsp:useBean id="venSvc" scope="page" class="com.vendor.model.VendorService" />
									<p>製造商：${venSvc.findByPrimaryKey(aProVO.vendor_id).vendor_name}</p>
								</li>
								<br><p><strong>商品描述</strong></p>
<pre>${aProVO.pro_des}</pre>
							
						</div>

						<!-- 商品規格 -->
						<div role="tabpanel" class="tab-pane fade in " id="proSpec">
								<jsp:useBean id="proSpecSvc" scope="page" class="com.pro_spec.model.ProSpecService" /> 
								<jsp:useBean id="specMSvc" scope="page" class="com.spec_main.model.SpecMainService" /> 
								<jsp:useBean id="specDSvc" scope="page" class="com.spec_detail.model.SpecDetailService" />
<pre>					
<c:forEach var="proSpec" items="${proSpecSvc.findByPrimaryKey(aProVO.pro_id)}">
<!-- got specDet_id -->
${specMSvc.findByPrimaryKey(specDSvc.findByPrimaryKey(proSpec.specDet_id).spec_id).spec_des}：${specDSvc.findByPrimaryKey(proSpec.specDet_id).detail_des}
</c:forEach>
</pre>	
						</div>


						<!-- TAGS -->
						<div role="tabpanel" class="tab-pane fade" id="tags"></div>
					</div>
				</div>
							</div>
						</section>
					</div>

				</div>

				

				<%@ include file="/front_end/footerSection.jsp"%>
				<%@ include file="/front_end/footerMenu.jsp"%>
	</div>
	<!--End main content -->

	<script>
		// 加入購物車
		$('.addToKart').click(function(){
			let data = {
			    	pro_id : $(this).data('pro_id'),
			    	pro_name: $(this).data('pro_name'),
			    	cat_id : $(this).data('cat_id'),
			    	quality: parseInt($('#quantity').val()),
			    	price : $(this).data('price') * parseInt($('#quantity').val())
			};
			console.log(data);
			addtoUserBasket([data], backet, imgSrc);
			addToCar(backet);
		});
	</script>
</body>
</html>