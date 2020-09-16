 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.b2cpro_main.model.*, com.bid_main.model.*, com.bid_detail.model.*" %>

<%
		String pro_id = request.getParameter("pro_id");
		System.out.println("pro_id="+pro_id);
		String bid_id = request.getParameter("bid_id");
		System.out.println("bid_id="+bid_id);
		
		B2cproMainService proSvc = new B2cproMainService();
		B2cproMainVO aProVO = proSvc.findByPrimaryKey(pro_id);
		pageContext.setAttribute("bproVO",aProVO);
		
		BidMainService bidMainSvc = new BidMainService();
		BidMainVO bidMainVO = bidMainSvc.getOneBid(bid_id);
		pageContext.setAttribute("bidMainVO",bidMainVO);
		
		BidDetailService biddSvc = new BidDetailService();
		List<BidDetailVO> bidDetailVO = biddSvc.getByBidId(bid_id);
		pageContext.setAttribute("bidDetailVO", bidDetailVO);
		
		if(pro_id != null){
			session.setAttribute("forword_page", request.getRequestURI()+"?bid_id="+bidMainVO.getBid_id()+"&pro_id="+aProVO.getPro_id());
		}
	%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>競標商品詳情瀏覽</title>
	<%@ include file="/front_end/headerSection.jsp"%>

	<style>
		:root{
		   --width200px: 200px;
		}
		
		input#bid_price {
			float: right;
			margin-right: 110px;
			width: 14em;
		}
		
		#bidBtn {
		    border: none;
		    color: #fff;
		    padding: 5px 50px;
		    text-transform: uppercase;
		    font-weight: bold;
		    font-size: 18px;
		    line-height: 46px;
		    background: #2d3a4b;
		    letter-spacing: 1px;
		    position: relative;
		    z-index: 1;
		    height: 50px;
		    margin-top: 15px;
		    width: var(--width200px);
		}
		#bidBtn:hover {
		    background: #ffe115;
		    color: #a94442;
		}
		
		.shop-detail .item-owner li span {
			color: #999;
		}
		
		.item-decribe {
			margin: 50px 150px;
		}
		
		/* 價錢框 */
		.addModey {
			display: flex;
		    width: var(--width200px);
		}
		
		.addModey>div {
			border: 1px solid #2d3a4b;
			width: 60px;
			height: 44px;
			width: 100%;
			/* font-size: 11px; */
			padding: 0 10px;
			letter-spacing: 1px;
			display: inline-block;
			font-weight: normal;
			text-align: left;
			margin-top: 10px;
			-webkit-transition: all 0.4s ease-in-out;
			-o-transition: all 0.4s ease-in-out;
			transition: all 0.4s ease-in-out;
			line-height: 35px;
			text-align: center;
		}
		
		.addModey>div:nth-child(1), .addModey>div:nth-child(2) {
			margin-right: 3px;
		}
		
		.addModey>div:nth-child(1), .addModey>div:nth-child(3) {
		    width: 50px;
		    height: 34px;
		    font-weight: 700;
		    text-shadow: 0 1px black, 0px 0 black;
		}
		
		.addModey>div:nth-child(1):after,
		.addModey>div:nth-child(3):after {
		    position: absolute;
		    margin-top: -3px;
		}
		
		.addModey>div:nth-child(1):after {
		    content: "-";
		    margin-left: -3px;
		}
		
		.addModey>div:nth-child(3):after {
			content: "+";
		    margin-left: -6px;
		}
		
		.addModey>input {
		    margin: 0;
		    margin-top: 10px;
		    margin-right: 3px;
		    border: 1px solid #2d3a4b;
		    padding: 5px 10px;
		    width: 100%;
		}
		
		.red {
			color: rgb(255, 0, 0);
			font-size: 12px;
		}
		
		#clockbox svg {
			width: 20px;
		    margin-bottom: -8px;
		}
	</style>
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>	
</head>

<body class="sticky-header">
	<%@ include file="/front_end/headerMenu.jsp"%>

	<!-- main content start-->
	<div class="main-content">

		<!-- LOADER -->
		<div id="loader">
			<div class="position-center-center">
				<div class="ldr"></div>
				<!-- 錯誤表列 -->
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
												<img class="img" width="450" height="500" src="<%= request.getContextPath()%>/front_end/front_b2cpro_main/front_b2cpro_main.do?pro_id=${bproVO.pro_id}">
											</li>
										</ul>
									</div>
								</div>

								<!-- Content -->
								<div class="col-md-5">

									<h4>${bproVO.pro_name}</h4>

									<c:choose>
										<c:when test="${fn:length(bidDetailVO) != 0}">
											<c:forEach var="BidDVO" items="${bidDetailVO}" varStatus="aBidD">
												<c:if test="${aBidD.last}">
													<span>$ ${BidDVO.bid_price} </span>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<span>$ ${bidMainVO.start_price} </span>
										</c:otherwise>
									</c:choose>

									<p></p>
									<ul class="item-owner">
										<li>
                   							<div class="countdown" id="clockbox"> 
                   								<svg width="2em" height="2em" viewBox="0 0 16 16" class="bi bi-alarm" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
							                      <path fill-rule="evenodd"	d="M8 15A6 6 0 1 0 8 3a6 6 0 0 0 0 12zm0 1A7 7 0 1 0 8 2a7 7 0 0 0 0 14z" />
							                      <path fill-rule="evenodd"	d="M8 4.5a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-.053.224l-1.5 3a.5.5 0 1 1-.894-.448L7.5 8.882V5a.5.5 0 0 1 .5-.5z" />
							                      <path	d="M.86 5.387A2.5 2.5 0 1 1 4.387 1.86 8.035 8.035 0 0 0 .86 5.387zM11.613 1.86a2.5 2.5 0 1 1 3.527 3.527 8.035 8.035 0 0 0-3.527-3.527z" />
							                      <path fill-rule="evenodd"	d="M11.646 14.146a.5.5 0 0 1 .708 0l1 1a.5.5 0 0 1-.708.708l-1-1a.5.5 0 0 1 0-.708zm-7.292 0a.5.5 0 0 0-.708 0l-1 1a.5.5 0 0 0 .708.708l1-1a.5.5 0 0 0 0-.708zM5.5.5A.5.5 0 0 1 6 0h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5z" />
							                      <path d="M7 1h2v2H7V1z" />
	                   							</svg> 
                   								倒數 
                   								<span class="day">00</span>天
                   								<span class="hour">00</span>時
                   								<span class="minute">00</span>分
                   								<span class="second">00</span>秒
                   							</div>
                   								
                   						</li>
										<li>競標編號 ： <span> ${bidMainVO.bid_id}</span></li>
										<li>競標標題 ： 
											<span>${bidMainVO.bid_title}</span>
										</li>
										<li>競標描述： 
											<span>${bidMainVO.bid_des}</span>
										</li>
										<li>商品編號： 
											<span> ${bidMainVO.pro_id}</span>
										</li>
										<li>起標價： 
											<span>$ ${bidMainVO.start_price}</span>
										</li>
										<li>出價增額： 
											<span> $ ${bidMainVO.incr}</span>
										</li>
										<li>競標開始時間： 
											<span> <fmt:formatDate value="${bidMainVO.start_time}" pattern="yyyy-MM-dd HH:mm" /></span>
										</li>
										<li>競標結束時間：
											<span class="bidEndTime"> <fmt:formatDate value="${bidMainVO.end_time}" pattern="yyyy-MM-dd HH:mm" /></span>
										</li>
									</ul>
									
									<!-- BID Start-->
									<c:choose>
										<c:when test="${fn:length(bidDetailVO) != 0}">
											<c:forEach var="BidDVO" items="${bidDetailVO}"
												varStatus="aBidD">
												<c:if test="${aBidD.last}">
													<c:set var="lastPrice" scope="page" value="${BidDVO.bid_price + bidMainVO.incr}" />
														<label>請輸入出價金額:</label>
														<div class="addModey">
															<div class="reduce"></div>
																<input class="money" name="bid_price" value="${lastPrice}" />
															<div class="plus"></div>
														</div>
												</c:if>
											</c:forEach>
										</c:when>
										
										<c:otherwise>
											<c:set var="startPrice" scope="page" value="${bidMainVO.start_price + bidMainVO.incr}" />
												<label>請輸入出價金額:</label>
												<div class="addModey">
													<div class="reduce"></div>
														<input class="money" name="bid_price" value="${startPrice}" />
													<div class="plus"></div>
												</div>
										</c:otherwise>
									</c:choose>

									<button type="button" class="btn btn-primary" id="bidBtn" data-toggle="modal" data-target="#bid">我要出價</button>
									<!-- BID End-->
								</div>
							</div>
						</div>
						<!--======= PRODUCT DESCRIPTION =========-->
		<div class="item-decribe">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs animate fadeInUp" data-wow-delay="0.4s" role="tablist">
				<li role="presentation" class="active">
					<a href="#proDescr" role="tab" data-toggle="tab">商品資訊</a>
				</li>
				<li role="presentation">
					<a href="#proSpec" role="tab" data-toggle="tab">商品規格</a>
				</li>
				<li role="presentation">
					<a href="#bidRecord" role="tab" data-toggle="tab">出價紀錄</a>
				</li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content animate fadeInUp" data-wow-delay="0.4s">
				<!-- 商品資訊 -->
				<div role="tabpanel" class="tab-pane fade in active" id="proDescr">
					<p>
						<strong>商品資訊</strong>
					</p>

					<li>
						<p>商品編號： ${bproVO.pro_id}</p>
					</li>
					<li>
						<p>商品名稱： ${bproVO.pro_name}</p>
					</li>
					<li><jsp:useBean id="catSvc" scope="page" class="com.catagory.model.CatagoryService" />
						<p>商品類別：${catSvc.findByPrimaryKey(bproVO.cat_id).cat_des}</p>
					</li>
					<li><jsp:useBean id="venSvc" scope="page" class="com.vendor.model.VendorService" />
						<p>製造商：${venSvc.findByPrimaryKey(bproVO.vendor_id).vendor_name}</p>
					</li>
					<p>
						<br> <strong>商品描述</strong>
					</p>
<pre>${bproVO.pro_des}</pre>
				</div>

				<!-- 商品規格 -->
				<div role="tabpanel" class="tab-pane fade in" id="proSpec">
					<ul>
						<jsp:useBean id="proSpecSvc" scope="page" class="com.pro_spec.model.ProSpecService" />
						<jsp:useBean id="specMSvc" scope="page" class="com.spec_main.model.SpecMainService" />
						<jsp:useBean id="specDSvc" scope="page" class="com.spec_detail.model.SpecDetailService" />

<pre>
<c:forEach var="proSpec"
items="${proSpecSvc.findByPrimaryKey(bproVO.pro_id)}">
<!-- got specDet_id -->
${specMSvc.findByPrimaryKey(specDSvc.findByPrimaryKey(proSpec.specDet_id).spec_id).spec_des}：${specDSvc.findByPrimaryKey(proSpec.specDet_id).detail_des}
</c:forEach>
</pre>
					</ul>
				</div>

				<!-- 出價紀錄 -->
				<div role="tabpanel" class="tab-pane fade in" id="bidRecord">
					<table class="table">
						<thead class="thead-dark">
							<tr>
								<th scope="col">#</th>
								<th scope="col">出價者</th>
								<th scope="col">出價金額</th>
								<th scope="col">出價時間</th>
							</tr>
						</thead>
						<tbody>
							<%
								int row = 0;
							%>
							<c:forEach var="aBidDVO" items="${bidDetailVO}">
								<tr>
									<th scope="row"><%=++row%></th>
									<!-- MEM000 1 -->
									<td>*****${fn:substring(aBidDVO.mem_id,6, -1)}</td>
									<td>$ ${aBidDVO.bid_price}</td>
									<td><fmt:formatDate value="${aBidDVO.bid_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
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

	<!-- 下標modal -->
	<div class="modal fade" id="bid" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">

			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">
						${(memVO.mem_id != null && memVO.mem_id.length() != 0) ? '我要出價' : '請先登入'}
					</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					</button>
				</div>
				<div class="modal-body">

					<ul>
						<c:if test="${memVO.mem_id != null && memVO.mem_id.length() != 0 }">
							<li>Hi! ${memVO.last_name} 會員</li>
							<li>您的下標金額為： $ 
								<span class="price"></span>
							</li>

							<li>貼心提醒：確定送出後無法修改</li>
						</c:if>
						<c:if test="${memVO.mem_id == null || memVO.mem_id.length() == 0}">
							<li>請先登入</li>
							<li>您尚未登入，請先登入或註冊會員，才可使用此競標功能!</li>
						</c:if>
					</ul>

				</div>
				<div class="modal-footer">
					<FORM METHOD="post" name="form1">
						<c:choose>
							<c:when test="${memVO.mem_id != null && memVO.mem_id.length() != 0}">
								<button type="button" class="btn btn-secondary " data-dismiss="modal">取消</button>
								<div class="btn btn-primary submitBid" data-dismiss="modal">確定下標</div>

							</c:when>
							<c:otherwise>
								<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
								<div class="btn btn-primary ${memVO.mem_id.length() > 0 ? '' : 'login'}" data-dismiss="modal">確定跳轉</div>
							</c:otherwise>
						</c:choose>
					</FORM>
				</div>
			</div>

		</div>
	</div>
</body>

<script>
	//價錢框
	let timeout;
	let addModey = $('.addModey');
	let money = {};
	
	money.total = parseInt(addModey.find('.money').val());
	money.min = money.total;
	money.add = ${bidMainVO.incr};
	money.deduct = ${bidMainVO.incr};
	money.changeTime = 60;
	
	// 增加
	addModey.find('.plus').on('mousedown', function () {
	  console.log(1);
	  timeout = setInterval(function () {
	    money.total += money.add;
	    addModey.find('.money').val(money.total);
	  },  money.changeTime);
	});
	addModey.find('.plus').on('mouseup mousemove', function () {
	  clearTimeout(timeout);
	});
	// 減少
	addModey.find('.reduce').on('mousedown', function () {
	  timeout = setInterval(function () {
	    if (money.total <= money.min) {
	      clearTimeout(timeout);
	      return;
	    }
	    money.total -= money.deduct;
	    addModey.find('.money').val(money.total);
	  },  money.changeTime);
	});
	addModey.find('.reduce').on('mouseup mousemove', function () {
	  clearTimeout(timeout);
	});
	// 驗證
	addModey.find('.money').on('focusout keypress', function (e) {
	  //  按Enter驗證
	  if(e.which == 13){
		  var numRegex = /[0-9]/;
		  var price = parseInt($('.money').val());
		  // 移除提示文字
		  $('.red').remove();
		  // 非數字
		  if(!numRegex.test(price)){
				
		      // 驗證提示
		      addModey.after('<div class="red">出價請輸入數字</div>');
			  $('#bidBtn').prop("disabled",true);
			  return;
		  // 數字 金額過小
		  } else if(numRegex.test(price) && price < money.min) {
		      // 移除提示文字
		      $('.red').remove();
		      // 添加提示文字
		      addModey.after('<div class="red">出價金額不可小於' + money.min + '</div>');
		      $('#bidBtn').prop("disabled",true);
		 	  return;
 	      // 數字 金額正常
		  } else if (numRegex.test(price) && price >= money.min){
		      // 移除提示文字
		      $('.red').remove();
		      $('#bidBtn').prop("disabled",false);
		  }
	  }
	});
</script>

<script>
	// 已結標不能下標
	window.onload = function(){
	　　if('${bidMainVO.status}' != 1){
		 $('#bidBtn').prop("disabled",true);
	　　}
	}
	
	$('#bidBtn').click(function(){
			// 會員有登入時
		if(!!'${memVO.mem_id}'){
			// 將會員出價金額顯示在在modal視窗 
			$('.price').html($('.money').val());
		};
	});
	
	// 未登入時點選跳轉
	$('.login').click(function(){
		location.href = '<%=request.getContextPath()%>/front_end/login/login.jsp';
	});
	
	
	// 確定下標後送出資料
	$('.submitBid').click(function(){
		$.ajax({
			url : '<%=request.getContextPath()%>/front_end/bidding/FrontBidDetailSertvlet.do',
			type : 'post',
			data : {
					action : 'insert',
					bid_id : '${bidMainVO.bid_id}',
					mem_id : '${memVO.mem_id}',
					bid_price : $('[name = "bid_price"]').val(),
			},
			datatype : 'json',
			success : function(res) {
				console.log("submitBid", res)		
			    res = JSON.parse(res);
				console.log(res.mem_id);
				console.log(res.bid_price);
				tipMsg('有會員在${bidMainVO.bid_id}競標出價'+ res.bid_price);
				
				swal({
				    title: '您已出價成功',
				    text: '2秒後回到競標商品。',
				    timer: 1200,
				    type: 'success'
				}).then(
				    function () {},
				    // handling the promise rejection
				    function (dismiss) {
				        if (dismiss === 'timer') {
				        	
				        	window.location.reload();
				        }
				    }
				)
			}
		});

	});

	// 推播到前台
	function tipMsg(msg) {
		socket.data.bid = {};
		socket.data.bid.msg = msg;
		sendMessage(socket.data);
	}

	document.addEventListener('touchstart', function(event){
		if(event.cancelable){
			if(!event.defaultPrevented){
				event.preventDefault();
			}
		}
	}, false);
	
	// 倒數計時器
	var expiryTime = $('.bidEndTime').html(); // 競標截止時間
	
	$(function(){ 
	  countDown(expiryTime,"#clockbox"); 
	}); 
	
	function countDown(time,id){ 
	  var day_elem=$(id).find('.day'); 
	  var hour_elem=$(id).find('.hour'); 
	  var minute_elem=$(id).find('.minute'); 
	  var second_elem=$(id).find('.second'); 
	  var end_time = new Date(time).getTime();
	  var sys_second = (end_time-new Date().getTime())/1000; 
	  
	  var timer = setInterval(function(){ 
	    if(sys_second>1) { 
	      sys_second -= 1; 
	      
	      let days = (sys_second > 0 ? sys_second/3600 : 0 );
	      let minutes = (sys_second > 0 ? sys_second/60 : 0 );
	      
	      var day = days > 0 ? Math.floor(days/24) : 0; 
	      var hour = days > 0 ? Math.floor(days%24) : 0; 
	      var minute = minutes > 0 ? Math.floor(minutes%60) : 0; 
	      var second = Math.floor(sys_second%60);
	      
	      $(day_elem).text(day);
	      $(hour_elem).text(hour<10?"0"+hour:hour);
	      $(minute_elem).text(minute<10?"0"+minute:minute); 
	      $(second_elem).text(second<10?"0"+second:second);
	    } 
	    else { 
	      clearInterval(timer); 
	    } 
	  }, 1000);
	}
</script>
</html>