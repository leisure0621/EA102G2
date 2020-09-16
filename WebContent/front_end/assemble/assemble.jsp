<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.catagory.model.*,com.b2cpro_main.model.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="M_Adnan">

<title>簡單組裝 一秒上手</title>

<%@ include file="/front_end/headerSection.jsp"%>

<style>
.assemble {
	right: 15px;
	top: 200px;
	position: fixed;
	width: 300px;
	padding: 15px;
	border-radius: 5px;
	box-shadow: 0 5px 6px rgba(0, 0, 0, .1);
	z-index: 2;
	background: white;
	max-height: 500px;
	overflow: auto;
}

.assemble .total {
	font-weight: bold;
}

.assemble>div {
	padding-left: 15px;
	padding-right: 15px;
	margin-bottom: 15px;
}

.assemble>div:nth-child(1) {
	font-size: var(--font20px);
	font-weight: bold;
	border-bottom: 0;
}

.assemble>div:nth-child(1):before {
	content: "";
	height: 6px;
	width: 6px;
	background: #ffe115;
	position: absolute;
	left: 15px;
	top: 22px;
}

.assemble img {
	width: 75px;
	margin-right: 15px;
}

.assemble [cat_id] span:nth-child(2) {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 140px;
    display: inline-block;
}

.assemble [cat_id] span:nth-child(3) {
    display: block;
    width: 100%;
    border-bottom: 1px solid #ddd;
    padding-bottom: 5px;
    padding-left: 5px;
}

.assemble .assemble-btn.btn {
	font-size: 14px;
	padding: 3px 15px;
	height: 40px;
	line-height: 35px;
	width: 100%;
	display: none;
}

ul.login-with {
	padding-top: 19px;
}

.btn:focus {
	color: white;
}

.active a {
	color: var(- -headerBlackFontColor);
}

.papular-block .overlay {
	background: rgba(255, 255, 255, .15);
}

button {
	background: rgba(255, 255, 255, .15);
	border: 1px;
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
	text-align: center;
}

.shop-page {
	min-height: 1159px;
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

.shop-cate a {
	cursor: pointer;
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

.addToCart {
	cursor: pointer;
	font-size: 14px;
	padding: 3px 15px;
	height: 40px;
	line-height: 35px;
	margin-top: 10px;
}

.papular-block {
	margin-top: 50px;
}
</style>

</head>
<body>
	<div id="loader">
		<div class="position-center-center">
			<div class="ldr"></div>
		</div>
	</div>
	<div id="wrap">
		<%@ include file="/front_end/headerMenu.jsp"%>
		<!-- Content -->
		<div id="content"></div>
		<section class="shop-page padding-top-10 padding-bottom-50">
			<div class="container">
				<div class="row">

					<!-- Shop SideBar -->
					<div class="col-sm-3">
						<div class="shop-sidebar">
							<h5 class="shop-tittle margin-bottom-30">請選購零件</h5>
							<ul class="shop-cate">
								<li class="pcbs"><a data-cat_id="CAT0003">主機板 <span></span></a></li>
								<li class="processors"><a data-cat_id="CAT0004">處理器 <span></span></a></li>
								<li class="drams"><a data-cat_id="CAT0005">記憶體 <span></span></a></li>
								<li class="hdds"><a data-cat_id="CAT0006">硬碟 <span></span></a></li>
								<li class="gpus"><a data-cat_id="CAT0010">顯示卡 <span></span></a></li>
								<li class="cases"><a data-cat_id="CAT0012">機殼 <span></span></a></li>
								<li class="pss"><a data-cat_id="CAT0013">電源供應器 <span></span></a></li>
							</ul>
						</div>
					</div>

					<div class="col-sm-9 contentProduct">
						<!-- Popular Item Slide Start-->
						<div class="papular-block row"></div>
						<!-- Popular Item Slide End-->
					</div>
				</div>
			</div>
		</section>
		<div class="assemble" data-mem_id="${memVO.mem_id}" data-mem_name="${memVO.first_name}${memVO.last_name}">
			<div>電腦組裝</div>
			<div cat_id="CAT0003" data-pro_id=""></div>
			<div cat_id="CAT0004" data-pro_id=""></div>
			<div cat_id="CAT0005" data-pro_id=""></div>
			<div cat_id="CAT0006" data-pro_id=""></div>
			<div cat_id="CAT0010" data-pro_id=""></div>
			<div cat_id="CAT0012" data-pro_id=""></div>
			<div cat_id="CAT0013" data-pro_id=""></div>
			<div class="total"></div>
			<div class="assemble-btn btn add">開始組裝</div>
		</div>
		<%@ include file="/front_end/footerMenu.jsp"%>
		<%@ include file="/front_end/footerSection.jsp"%>
	</div>
	<script>
	
	$(document).ready(function(){
		setProName();
		papularBlock();
		
		let assembleData = {}, productList = [];
		let publicBlock = $('.shop-page .contentProduct .papular-block');
		let assemble = $('.assemble');
		let checkoutbtn = $('.assemble-btn');
		let assembleUrl = "<%=request.getContextPath()%>/front_end/assemble/assemble.do";
		let total = 0;	
	
		// 開始組裝
		checkoutbtn.on('click', function(){
			if(checkoutbtn.hasClass('add')) {
				checkoutbtn.removeClass('add');
				sendData(assemble, 'addOrder', 2, 3, "無", 0);
			}
		})
		
		// 資料取得
        function getData(action){
			let data = {}
			// 篩選用
			assembleData.action = action ? action : 'selectAll';
			assembleData.pcb = assemble.find('[cat_id="CAT0003"]').data('pro_id');
			assembleData.processor = assemble.find('[cat_id="CAT0004"]').data('pro_id');
			assembleData.dram =assemble.find('[cat_id="CAT0005"]').data('pro_id');
			assembleData.hdd = assemble.find('[cat_id="CAT0006"]').data('pro_id');
			assembleData.gpu = assemble.find('[cat_id="CAT0010"]').data('pro_id');
			assembleData.case = assemble.find('[cat_id="CAT0012"]').data('pro_id');
			assembleData.ps = assemble.find('[cat_id="CAT0013"]').data('pro_id');
			
			if (assembleData.pcb && assembleData.processor && assembleData.dram && assembleData.hdd && assembleData.case && assembleData.ps) {
				assemble.find('.assemble-btn').show();
			}
        }
		
        getData();
		// 取得所有資料 
		$.ajax({
			type:'POST',
			url: assembleUrl,
			data: assembleData,
			datatype:'json',
			success:function(res){
				res = JSON.parse(res).data;
				
				$('.cases').find('span').text('');
				$('.drams').find('span').text('');
				$('.gpus').find('span').text('');
				$('.hdds').find('span').text('');
				$('.pcbs').find('span').text('');
				$('.processors').find('span').text('');
				$('.pss').find('span').text('');
				
				Object.keys(res).filter(item => {
					$('.' + item).find('span').text(res[item].length);
					return item;
				});
				showProducts(res["pcbs"]);
			}
		});
		
		// 顯示商品
		function showProducts(res){
			$.each(res,function(i,item){
				publicBlock.append(
					`<div class="col-md-4">
						<!-- Item -->
						<div class="item">
						<a href="<%=request.getContextPath()%>/front_end/assemble/assembleDetail.jsp?pro_id=`+ item.pro_id +`" target="_blank">
								<div class="item-img">
									<img width="270px" height="352px" src="` + imgSrc + item.pro_id +`">
									<!-- yellow -->
									<div class="overlay">
										<div class="position-center-center"></div>
									</div>
								</div>
								<!-- Item Name -->
								<div class="item-name">
									<span class="pro_name">` + item.pro_name + `</span>
								</div>
								<!-- Price -->
								<span class="price"><small>$</small>` + item.rrp + `</span>
							</a>
							<span class="addToCart btn" 
							data-pro_id="` + item.pro_id + `"
							data-pro_name="` + item.pro_name + `"
							data-cat_id="` + item.cat_id + `"
							data-price="` + item.rrp + `"
							data-quality="1"
							>加入選購</span>
						</div>
					</div>`
				);
				setProName();
			});
		}
		
		// 商品名稱縮短
		function setProName(){
			$(".pro_name").each(function(){
	            var maxwidth=15;//設置最多顯示的字數
	            var text=$(this).text();
	            if($(this).text().length>maxwidth){
	                $(this).text($(this).text().substring(0,maxwidth));
	                $(this).html($(this).html()+"...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
	            };
	        });
		}
		
		// 加入商品
		function papularBlock() {
			//老爸區域---事件---綁定的子層級---方法
			$(".papular-block").on('click', ".addToCart", function() {
				// 相容檢查
			    let data = {
		    		action :"add"+$(this).data('cat_id'),
		    		pro_id : $(this).data('pro_id'),
		    		pro_name: $(this).data('pro_name'),
		    		cat_id : $(this).data('cat_id'),
		    		price : $(this).data('price'),
		    		quality: $(this).data('quality')
				};
				
				// 加入組裝列表
				assemble.find('[cat_id="'+data.cat_id+'"]')
				.data('pro_id', data.pro_id)
				.data('cat_id', data.cat_id)
				.data('pro_name', data.pro_name)
				.data('price', data.price)
				.data('quality', data.quality)
				.html(
				  '<img src="'+imgSrc+data.pro_id+'">' +
				  '<span>'+data.pro_name+'</span>'+
				  '<span>$'+data.price+'</span>'
				);
				
				// 選購商品 隱藏分類商品相關按鈕				
				$('[data-cat_id="'+ data.cat_id +'"]').hide();
				
				total += parseInt(data.price);
				assemble.find('.total').text('總額：$'+total).data('total', total);
				
				// clean Data
				$('.cases').find('span').text('');
				$('.drams').find('span').text('');
				$('.gpus').find('span').text('');
				$('.hdds').find('span').text('');
				$('.pcbs').find('span').text('');
				$('.processors').find('span').text('');
				$('.pss').find('span').text('');
				
				getData();
				
				// 更新長度
				$.ajax({
					type:"POST",
					url: "<%=request.getContextPath()%>/front_end/assemble/assemble.do",
					data : assembleData,
					datatype : 'json',
					success : function(res) {
						res = JSON.parse(res).data;
						
						Object.keys(res).filter(item => {
							$('.' + item).find('span').text(res[item].length);
							return item;
						});
					}
				});
				
			});
		}
		
		// 取得分類內容
		let catData = {};
		$('.shop-cate a').click(function(){
			catData.action = "select"+$(this).data('cat_id');
			catData.cat_id = $(this).data('cat_id');
			publicBlock.find('.col-md-4').remove();
			
			getData();
			$.ajax({
				type:"POST",
				url: "<%=request.getContextPath()%>/front_end/assemble/assemble.do",
				data : assembleData,
				datatype : 'json',
				success : function(res) {
					res = JSON.parse(res);
					
					if(res.data) {
						res = res.data;
						$.each(res,function(i,item){
							// 清空
							publicBlock.find('.col-md-4').remove();
							
							// 添加
 							showProducts(res[$('.shop-cate [data-cat_id="' + catData.cat_id + '"]').parent().attr('class')]);

							// 限制文字長度 
							setProName();
						});
					}
					
				}
			});
 		});
	});
	
	</script>
</body>
</html>