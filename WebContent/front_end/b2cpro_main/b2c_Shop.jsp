<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.front_end_b2cpro_main.model.*"%>
<%@ page import="com.catagory.model.*"%>

<%
	front_B2cproMainService B2cproMainService = new front_B2cproMainService();
	List<front_B2cproMainVO> list = B2cproMainService.getAll();
	List<front_B2cproMainVO> list2 = (List<front_B2cproMainVO>) session.getAttribute("listCat");
	if (list2 != null) {
		System.out.println("list2.size()" + list2.size());
		list = list2;
		System.out.println("list.size()" + list.size());
	}
	pageContext.setAttribute("list", list);

	front_B2cproMainVO b2cpromainVO = (front_B2cproMainVO) request.getAttribute("b2cpromainVO");
%>



<%-- ----------${list==null}------${listCat==null}------ --%>
<jsp:useBean id="catagorySvc" scope="page"
	class="com.catagory.model.CatagoryService" />




<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="M_Adnan" />
<title>B2C_SHOP</title>

<%@ include file="/front_end/headerSection.jsp"%>



<style>
.papular-block .overlay {
	background: rgba(255, 255, 255, .15);
}

.shop-cate a:hover {
    cursor: pointer;
}

button {
	background: rgba(255, 255, 255, .15);
	border: 1px;
	width: 100%;
}
.news-letter {
	background: #fefeff;
}

.news-letter .ser {
	max-width: 770px;
	margin: 0 auto;
}

.news-letter .ser button {
	height: 36px;
	float: right;
	border: 1px solid #6d6a6a;
	color: #7b7070;
	font-size: 13px;
	font-weight: bold;
	background: none;
	width: 10%;
	margin-right: 528px;
}

.news-letter .ser input {
	height: 36px;
	width: 20%;
	padding: 0 20px;
	display: inline;
	font-size: 16px;
	float: left;
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
		<div id="content">
			<!-- Products -->
			<section class="shop-page padding-top-10 padding-bottom-50">
				<div class="container">


					<div class="row">
						<!-- Shop SideBar -->
						<div class="col-sm-3">

							<div class="shop-sidebar">
								<section class="news-letter padding-bottom-10">
									<input type="text" name="pro_name" placeholder="搜尋商品名稱"
										required />

								</section>
								<!-- Category -->
								<h5 class="shop-tittle margin-bottom-30">category</h5>
								<ul class="shop-cate">
									<li><a data-cat_id="${catagoryVO.cat_id}">全部<span></span></a></li>
									<c:forEach var="catagoryVO" items="${catagorySvc.all}">
										<li><a data-cat_id="${catagoryVO.cat_id}">${catagoryVO.cat_des}
												<input type="hidden" name="cat_id"
												value="${catagoryVO.cat_id}" /> <span></span>
										</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>


						<!-- Item Content -->
						<div class="col-sm-9">
							<div class="item-display">
								<div class="row">



									<%@ include file="page1.file"%>
									<!-- Products Select -->
									<div class="col-xs-6">
										<div class="pull-right"></div>
									</div>
								</div>
							</div>

							<!-- Popular Item Slide -->
							<div class="papular-block row">

								<c:forEach var="b2cpromainVO" items="${list}"
									begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
									<c:if test="${b2cpromainVO.status==1}">
										
										<!-- Item -->
										<div class="col-md-4">
											<div class="item">
											
												<FORM METHOD="post" id="form1" name="form1" ACTION="<%=request.getContextPath()%>/front_end/front_b2cpro_main/front_b2cpro_main.do">
												<button type="submit">
													<!-- Item img -->
													<!-- <div class="item-img" style="height: 200px;"> -->
													<div class="item-img">
														<input type="hidden" name="action" value="getOne_For_Display">
														<input type="hidden" name="pro_id" value="${b2cpromainVO.pro_id}">
														<img width="270px" height="352px" src="<%= request.getContextPath()%>/front_end/front_b2cpro_main/front_b2cpro_main.do?pro_id=${b2cpromainVO.pro_id}">
														<!-- yellow -->
														<div class="overlay">
															<div class="position-center-center"></div>
														</div>
													</div>
													<!-- Item Name -->
													<div class="item-name">
														<span class="pro_name">${b2cpromainVO.pro_name}</span>

													</div>
													<!-- Price -->
													<span class="price"><small>$</small> <fmt:formatNumber
															type="number" value="${b2cpromainVO.rrp}"
															maxFractionDigits="0" /></span>
												</button>
												</FORM>
												
											</div>
										</div>
										
									</c:if>
								</c:forEach>
							</div>
							<%@ include file="/front_end/b2cpro_main/page2.file"%>
						</div>

					</div>
				</div>
			</section>
		</div>

		<%@ include file="/front_end/footerMenu.jsp"%>
		<%@ include file="/front_end/footerSection.jsp"%>
	</div>


	<script>
	$(function(){
		$(".pro_name").each(function(){
            var maxwidth = 15;//設置最多顯示的字數
            var text=$(this).text();
            if($(this).text().length>maxwidth){
                $(this).text($(this).text().substring(0,maxwidth));
                $(this).html($(this).html()+"...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
            };
        });
		
		$('[name="pro_name"]').keydown(function(){
            if (event.keyCode == 13) {
            	data = {
           			pro_name: $('[name="pro_name"]').val(),
           			action: 'getLike_For_Display'
            	}
            	
                $.ajax({
                    url: '<%=request.getContextPath()%>/front_end/c2cproMain/c2cproMain.do',
                    type : 'post',
                    data : data,
                    datatype: 'json',
                    success: function(res) {
                    	window.location.reload();
                    }
                });
            };
        });
		$('.shop-cate a').click(function(){
			data={
				b2cCatpro:$(this).data('cat_id'),
				action:'getCat_For_Display'
			}
			$.ajax({
				url:'<%=request.getContextPath()%>/front_end/front_b2cpro_main/front_b2cpro_main.do',
				type : 'post',
				data : data,
				datatype : 'json',
				success : function(res) {
// 					window.location.reload();
					location.href = '<%=request.getContextPath() %>/front_end/b2cpro_main/b2c_Shop.jsp?whichPage=1';
				}
			});

		});

	});
	</script>
</body>


</html>
