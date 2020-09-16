<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="M_Adnan" />
<title>二手商品詳情</title>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.c2cpro_main.model.*"%>
<%@ page import="com.spec_main.model.*"%>
<%@ include file="/front_end/headerSection.jsp"%>

<%
	C2cproMainVO c2cproMainVO = (C2cproMainVO) request.getAttribute("c2cproMainVO"); // 存入req的empVO物件
 String pro_id=request.getParameter("pro_id");
	if(pro_id != null){
		C2cproMainService C2cproMainSvc = new C2cproMainService();
		 c2cproMainVO=C2cproMainSvc.getOneC2cproMain(pro_id);
		pageContext.setAttribute("c2cproMainVO", c2cproMainVO);
		session.setAttribute("forword_page", request.getRequestURI()+"?pro_id="+c2cproMainVO.getPro_id());
	}
	
%>

<jsp:useBean id="c2cproDetailSvc" scope="page"
	class="com.c2cpro_detail.model.C2cproDetailService" />
<jsp:useBean id="specMainSvc" scope="page"
	class="com.spec_main.model.SpecMainService" />


<style>
.col-xs-6 {
	margin-top: 16px;
	margin-right: 82px;
	padding: 13px;
}
.col-md-7 {
       width: 36.333333%;
    margin-left: 146px;
    margin-right: 80px;
}

div.item-decribe {
    margin: 0;
}
.slides, .slides > li, .flex-control-nav, .flex-direction-nav {
    margin: revert;
    padding: 0px;
    list-style: none;
    list-style-position: initial;
    list-style-image: initial;
    list-style-type: none;
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

				<!-- SHOP DETAIL -->
				<div class="shop-detail">
					<div class="row">

						<!-- Popular Images Slider -->
						<div class="col-md-7">

							<!-- Images Slider -->

							<ul class="slides">
								<li data-thumb="images/large-img-1.jpg"><img
									class="img-responsive" width="250px" height="250px"
									src="<%= request.getContextPath()%>/front_end/c2cproMain/c2cproMain.do?pro_id=${c2cproMainVO.pro_id}"
									alt=""></li>
							</ul>
						</div>
						<!-- COntent -->
						<div class="col-md-5">
							<h4>${c2cproMainVO.pro_name}</h4>
							<span class="price">$<fmt:formatNumber type="number"
						value="${c2cproMainVO.price}" maxFractionDigits="0" />元</span>

							<!-- Short By -->
							<div class="some-info">
								<ul class="row margin-top-30">
									<li class="col-xs-5">
										<div class="quinty">
											<span class="price" >商品庫存: ${c2cproMainVO.quantity>0? c2cproMainVO.quantity :"缺貨中!!"} </span> 

										</div>
									</li>
									<!-- ADD TO CART -->
									<li class="col-xs-6">
									<button class="btn btn-primary" 
											data-toggle="modal" 
											data-target="#buy"
											${c2cproMainVO.quantity<=0? "disabled" :""}
											>直接購買</button></li>

								</ul>

							</div>
						</div>
					</div>
				</div>

				<!--======= PRODUCT DESCRIPTION =========-->
				<div class="item-decribe">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs animate fadeInUp" data-wow-delay="0.4s"
						role="tablist">
						<li role="presentation" class="active"><a href="#descr"
							role="tab" data-toggle="tab">商品描述</a></li>
						<li role="presentation"><a href="#tags" role="tab"
							data-toggle="tab">商品詳情</a></li>
						<li role="presentation"><a href="#review" role="tab"
							data-toggle="tab">我要檢舉</a></li>

					</ul>

					<!-- Tab panes -->
					<div class="tab-content animate fadeInUp" data-wow-delay="0.4s">
						<!-- 描述 -->
						<div role="tabpanel" class="tab-pane fade in active" id="descr">
							<p>${c2cproMainVO.description}</p>


						</div>



						<!-- 詳情-->
						<div role="tabpanel" class="tab-pane fade" id="tags">
							<ul>
								<c:forEach var="c2cproDetailVO"
									items="${c2cproDetailSvc.getOneC2cproDetail(c2cproMainVO.pro_id)}">
									<c:forEach var="specMainVO" items="${specMainSvc.all}">
										<c:if test="${specMainVO.spec_id==c2cproDetailVO.spec_id}">

											<li><p><span>${specMainVO.spec_des} : <c:out
														value="${c2cproDetailVO.spec_detail}" default="無"></c:out></span><p></li>
										</c:if>

									</c:forEach>
								</c:forEach>
							</ul>
						</div>
						<!-- 檢舉-->
						<div role="tabpanel" class="tab-pane fade" id="review">


							<form>
								<ul class="row">

									 <input type="hidden" name="reported_content" value="${c2cproMainVO.pro_id}" placeholder="${c2cproMainVO.pro_id}">
									
									 <input type="hidden" name="informant" value='${memVO.mem_id!=null? memVO.mem_id:"notlogin"}'>
									
									<li class="col-sm-12"><label> <p>檢舉描述</p> <textarea class="form-control w-100 res_cont" 
									style="resize:none;" name="case_description" id="comment" cols="30" rows="9" placeholder="Write Comment" required></textarea>
									
									</label></li>
									
									<li class="col-sm-6">
											<div class="btn btn-dark btn-small pull-right no-margin">送出</div>
											<input type="hidden" name="action" value="insert_proRep">
									</li>
								</ul>
							</form>
						</div>
					</div>
				</div>
			</div>
		</section>





		<%@ include file="/front_end/footerMenu.jsp"%>
		<%@ include file="/front_end/footerSection.jsp"%>
	</div>
	
	<!------------------------------------ 送出訂單------------------------------------->
<div class="modal fade" id="buy" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
  <FORM METHOD="post" ACTION="c2csoMain.do" name="form1">
  
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">成立訂單</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        
			
			<div class="form-group row">
  <label for="example-number-input" class="col-2 col-form-label" style=" margin-left: 20px;">購買數量</label>
  <div class="col-3">
    <input class="form-control" type="number" value="1" id="example-number-input" style="width: 19%;margin-left: 90px;margin-top: -34px;"
    name= "quantity" id="quantity_b" value="1" min="1" max="${c2cproMainVO.quantity}">
  </div>
</div>
		

			<div style="text-align:right;">
			單筆金額: <fmt:formatNumber type="number"
						value="${c2cproMainVO.price}" maxFractionDigits="0" />
			</div>
			<div class = "total" style="color:red; text-align:right;" >
			 總金額:<fmt:formatNumber type="number"
						value="${c2cproMainVO.price}" maxFractionDigits="0" />
			</div>
			
			
			
			<input type="hidden" name="mem_id" value="${memVO.mem_id}"> 
			<input type="hidden" name="action" value="insert_soMain"> 
			<input type="hidden" name="pro_id" value="${c2cproMainVO.pro_id}"> 
			<input type="hidden" name="forword_page" value="<%= request.getRequestURI() %>?pro_id=${c2cproMainVO.pro_id}"> 

<input type="hidden" name="buyer_id" value='${memVO.mem_id}'>

			<input type="hidden" name="status" value="CST0001">						
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <div  class="btn btn-primary buyc2cpro">確定</div>
      </div>
    </div>
    </FORM>
  </div>
</div>

	 <script>
		 
	 $(function(){
		
		 $('.buyc2cpro').click(function(){
			 $('[name="form1"]').submit();
		 });
		 
		 let data = {};
		 data.reported_content = '${c2cproMainVO.pro_id}';
		 
		 let url = '<%=request.getContextPath()%>/front_end/c2cproMain/c2cproRep.do';
		 
	     function clean(){
	    	 data.informant = '';
	    	 data.case_description = '';
	    	 data.action = '';
	    	 console.log("clean", data);
			 $('#review [name="case_description"]').val('');
	     }
	     
		 $('#review .no-margin').click(function(){
//			 data["case_description"] = $(this).data("reported_case_descriptioncontent");
			 data.case_description = $('#review [name="case_description"]').val();
			 data.informant = $('[name="informant"]').val();
			 data.action = 'insert_proRep';
			 console.log("new", data);
			 
			 $.ajax({
				 url: '<%=request.getContextPath()%>/front_end/c2cproMain/c2cproRep.do',
				 type: 'post',
				 data: data,
				 datatype: 'json',
				 success: function(res){
					 console.log(res)
					 res = JSON.parse(res).data;
					 alert(res);
					 clean();
				 }
			 
			 });
		 });
		 
		 $('[name= "quantity"]').change(function(){
			
		let total= $(this).val()*${c2cproMainVO.price};
		$(".total").text("總金額:" + total);
		
		
			 
		 });
		 
		 
	 });
	 
	 </script>
	
</body>
</html>
