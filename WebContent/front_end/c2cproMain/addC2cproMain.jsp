<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="M_Adnan" />
<title>新增商品</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.c2cpro_main.model.*"%>

<%
	C2cproMainVO c2cproMainVO = (C2cproMainVO) request.getAttribute("c2cproMainVO");
%>
<jsp:useBean id="catagorySvc" scope="page"
	class="com.catagory.model.CatagoryService" />
<%@ include file="/front_end/headerSection.jsp"%>



<style>
.addproNews, .addproNews2{
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
.item-decribe {
    margin-top: 200px;
}

table {
	margin-left: auto;
	margin-right: auto;
}

.bootstrap-select.btn-group .dropdown-toggle .filter-option {
	color: #2d3a4b;
	font-size: 16px;
}

.shop-detail .quinty .btn {
	border: 1px solid #eaeaea;
}

#content {
	background: #fff;
	position: relative;
	z-index: 1;
}

.shop-detail {
    width: 100%;
}

.item-decribe form input {
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
			
			<h4>商品資料新增</h4>

	
			
				<FORM METHOD="post" ACTION="c2cproMain.do" name="form1"
					enctype="multipart/form-data">



					<input type="hidden" name="mem_id" size="45"
						value="${memVO.getMem_id()}" />

					<table class="shop-detail">
						<tr>
							<td>商品圖片:</td>
							<td><img width=100px height=100px id="ImgDisplay" /><input
								type="file" style="width: 82px;
												    padding-left: 0px;
												    padding-right: 0px;
												    height: 27px;" 
								name="photo" accept="image/*"
								onchange="loadFile(event)"></td>
						</tr>



						<tr>
							<td>商品名稱:</td>
							<td><input type="TEXT" name="pro_name" size="45"
								value="<%=(c2cproMainVO == null) ? "" : c2cproMainVO.getPro_name()%>" /></td>
								<td style="color: red">${errorMsgs.pro_name}</td>
						</tr>

						<tr>
							<td>商品分類:</td>
							<td><select size="1" name="cat_id" class="row margin-top-6 quinty selectpicker category">
									<option value="0">請選擇</option>
									
									<c:forEach var="catagoryVO" items="${catagorySvc.all}">
										<option value="${catagoryVO.cat_id}"
											${catagoryVO.cat_id==c2cproMainVO.cat_id ? "selected" :""}>${catagoryVO.cat_des}
									</c:forEach>
							</select></td>
							<td style="color: red">${errorMsgs.cat_id}</td>
						</tr>

						<tr>
							<td>商品數量:</td>
							<td><input type="TEXT" name="quantity" size="45"
								value='${ (c2cproMainVO == null) ? "" : c2cproMainVO.quantity}' /></td>
								<td style="color: red">${errorMsgs.quantity}</td>
						</tr>
						<tr>
							<td>商品價格:</td>
							<td><input type="TEXT" name="price" size="45"
								value='${ (c2cproMainVO == null) ? "" : c2cproMainVO.price}' /></td>
								<td style="color: red">${errorMsgs.price}</td>
						</tr>


						<tr>
							<td>商品描述:</td>
							<td><textarea name="description" rows="6" placeholder="商品描述"><%=(c2cproMainVO == null) ? "" : c2cproMainVO.getDescription()%> </textarea></td>
							<td style="color: red">${errorMsgs.description}</td>
						</tr>
						<tr>
							<td>取貨方式:</td>
							<td><select size="1" name="delivery"
								class="row margin-top-6 quinty selectpicker delivery">
									<option>請選擇</option>
									<option value="1" ${c2cproMainVO.delivery==1 ? "selected" :""}>宅配到府</option>
									<option value="2" ${c2cproMainVO.delivery==2 ? "selected" :""}>便利店取貨</option>

							</select></td>
							<td style="color: red">${errorMsgs.delivery}</td>
						</tr>




					</table>
					<div  class="btn btn-primary addproNews">讓子軒解決你的問題</div>
					<div  class="btn btn-primary addproNews2">讓子軒解決你的困難</div>
					<br> <input type="hidden" name="action" value="insert">
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
<script>
	var loadFile = function(event) {
		var output = document.getElementById('ImgDisplay');
		output.src = URL.createObjectURL(event.target.files[0]);
		output.onload = function() {
			URL.revokeObjectURL(output.src) // free memory
		}
	};
	
	$('.addproNews').click(function(){
 		$('[name="pro_name"]').val("微星GeForce RTX 2080 Ti 顯示卡");
		$('[name="quantity"]').val("10");
		$('[name="price"]').val("10000");
		$('[name="description"]').val(
			"長久以來 MSI電競顯卡深受廣大玩家信賴與肯定，MSI GeForce RTX 30"+
			"電競系列顯示卡再次塑造全新外觀設計，且"+
			"不間斷在極致效能、安靜低躁和高效散熱中追求完美平衡。現在"+
			"，您可以藉由性能強大的 MSI GeForce RTX 30 電競系列顯卡，在遊戲虛擬世界中競速靜玩，隨心所欲。");
		// 自動選擇分類
		let catName = $('[value="CAT0010"]').text().trim();
		$('.category .filter-option').text(catName);
		$('[value="CAT0010"]').attr("selected", true);
		
		let delivery = $('[value="1"]').text().trim();
		$('.delivery .filter-option').text(delivery);
 		$('[value="1"]').attr("selected", true);
		
	});
	$('.addproNews2').click(function(){
 		$('[name="pro_name"]').val("Intel i5-9600K");
		$('[name="quantity"]').val("5");
		$('[name="price"]').val("5000");
		$('[name="description"]').val(
			"Core i5 9600K具備6核心6線程，相對於Core i3來講擁有更好的多線程和多任務性能，"+
			"因此在專業設計方面擁有更好的性能表現。從測試結果來看，如果主流玩家偶爾需要完成一些視覺設計的工作，"+
			"Core i5 9600K是完全可以勝任的，特別是單核心性能，我們可以看到在Cinebench R15中，Core i5 9600K單核分達到了203，"+
			"和旗艦的差距其實非常小，完全達到了一流的水平，如果再有一定幅度的超頻，更是可以比肩旗艦了，這對於經常使用PhotoShop這樣特別吃單核心性能的軟體的用戶來說是很有實用價值的。");
		// 自動選擇分類
		let catName2 = $('[value="CAT0004"]').text().trim();
		$('.category .filter-option').text(catName2);
		$('[value="CAT0004"]').attr("selected", true);
		
		let delivery = $('[value="1"]').text().trim();
		$('.delivery .filter-option').text(delivery);
 		$('[value="1"]').attr("selected", true);
		
	});
</script>

</html>
