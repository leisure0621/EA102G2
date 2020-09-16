<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.c2cpro_main.model.*"%>
<%@ page import="java.util.stream.Collectors"%>
<%@ include file="/front_end/headerSection.jsp"%>
<%

	C2cproMainService main = new C2cproMainService();
	List<C2cproMainVO> prolist = main.getAll();
	
	List<C2cproMainVO> list= new ArrayList<C2cproMainVO>();	
	list = prolist.stream()
			.filter(p->p.getMem_id().equals(memVO.getMem_id()))
			.collect(Collectors.toList());
	
			pageContext.setAttribute("list", list);
	
	String error_id = (String) request.getAttribute("error_id");
	String addproMain = (String) request.getAttribute("addproMain"); //錯誤新增

%>
<jsp:useBean id="catagorySvc" scope="page"
	class="com.catagory.model.CatagoryService" />
<jsp:useBean id="c2cproDetailSvc" scope="page"
	class="com.c2cpro_detail.model.C2cproDetailService" />
<jsp:useBean id="specMainSvc" scope="page"
	class="com.spec_main.model.SpecMainService" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="M_Adnan" />
<title>我的賣場</title>




<style>
.bootstrap-select.btn-group .dropdown-toggle .filter-option {
	color: #2d3a4b;
	font-size: 16px;
}
#add{
    position: absolute;
    right: 150px;
    }
.btn {
    border: none;
    color: #fff;
    display: inline-block;
    padding: 0px 7px;
    text-transform: uppercase;
    font-weight: bold;
    font-size: 16px;
    border-radius: 0px;
    line-height: 40px;
    background: #415b7d;
    letter-spacing: 1px;
    position: relative;
    z-index: 1;
}

.btn:focus {
	color: #fffafa;
	outline: none;
	outline: none;
	outline-offset: 0px;
} 
.item-decribe {
    margin-top: -51px;
}
.addpro {
    font-size: 27px;
    color: #415b7d;
    background-color: #FFF;
}
.table>tbody>tr>td{
vertical-align: middle;

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
			<section class="shop-page padding-top-100 padding-bottom-100">
				<div class="container">
 		<div class="col-sm-3 leftSlide">
        	<%@ include file="/front_end/membercenterLeftMenu.jsp" %>
        </div>
					<!-- Item Content -->
					<div class="col-sm-9">
							<h5 class="cart-ship-info" 
							style="margin-top:50px; 
									margin-bottom:30px;">二手我的賣場</h5>
						<!-- Popular Item Slide -->
						<div class="papular-block row">

							<table class="table">
								<thead>
									<tr>
										<th scope="col">商品編號</th>
										<th scope="col">商品名稱</th>
										<th scope="col">價格</th>
										<th scope="col">圖片</th>
										<th scope="col">狀態</th>									
										<th scope="col">建立時間</th>
										<th scope="col">修改</th>
										<th scope="col">
											<button type="button" 
												class="btn btn-primary addpro" 		
												onclick="location.href='addC2cproMain.jsp'">
											<i class="fa fa-plus-circle"></i></button>
										</th>

									</tr>
								</thead>
								<tbody>
									<%@ include file="/front_end/page1.file"%>
									<c:forEach var="c2cproMainVO" items="${list}" begin="<%=pageIndex%>"
										end="<%=pageIndex+rowsPerPage-1%>">

										<tr>

											<td>${c2cproMainVO.pro_id}</td>

											<td class="pro_name">${c2cproMainVO.pro_name}</td>


											<td><fmt:formatNumber type="number"
													value="${c2cproMainVO.price}" maxFractionDigits="0" /></td>
										
											<td><img width="50" height="50"
												src="<%= request.getContextPath()%>/front_end/c2cproMain/c2cproMain.do?pro_id=${c2cproMainVO.pro_id}"></td>

											<td>${(c2cproMainVO.status==1)?"上架":"下架"}</td>



											<td><fmt:formatDate value="${c2cproMainVO.est_time}"
													pattern="yyyy/MM/dd" /></td>

											<td>

												<div data-toggle="modal"
													data-target="#${c2cproMainVO.pro_id}">改資訊</div>

											</td>
											<td>

												<div data-toggle="modal"
													data-target="#d${c2cproMainVO.pro_id}">改規格</div>
											</td>


										</tr>

									</c:forEach>
								</tbody>
							</table>
								<%@ include file="/front_end/page2.file"%>
						</div>


					</div>
				</div>
			</section>
		</div>

	</div>




	<!------------------------------------ 修改資訊 ------------------------------------->
	<div>
		<c:forEach var="c2cproMainVO" items="${list}">
			<div class="modal fade" id="${c2cproMainVO.pro_id}" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalLongTitle"
				aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLongTitle">修改商品資訊</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
											
						<%-- 錯誤表列 --%>
						<c:if test="${error_id.equals(c2cproMainVO.pro_id)}">
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
						</c:if>
						<div class="item-decribe">
						<FORM METHOD="post" ACTION="c2cproMain.do" name="form1"
							enctype="multipart/form-data">
							<div class="modal-body">
								<table >
									<tr>
										<td>商品編號:</td>
										<td>${c2cproMainVO.pro_id}</td>
										<input type="hidden" name="pro_id"
											value="${c2cproMainVO.pro_id}">
									</tr>
									<tr>
										<td>賣家會員編號:</td>
										<td>${c2cproMainVO.mem_id}</td>
										<input type="hidden" name="mem_id"
											value="${c2cproMainVO.mem_id}">
									</tr>									
									<tr>
										<td>商品分類:</td>
										<td><c:forEach var="catagoryVO"
												items="${catagorySvc.all}">
												<c:if test="${catagoryVO.cat_id.equals(c2cproMainVO.cat_id)}">
													${catagoryVO.cat_des}
													<input type="hidden" name="cat_id"
														value="${c2cproMainVO.cat_id}">
												</c:if>
											</c:forEach></td>
									
									</tr>
									<tr>
										<td>選擇商品圖片:</td>
										<td><img
											src="<%= request.getContextPath()%>/front_end/c2cproMain/c2cproMain.do?pro_id=${c2cproMainVO.pro_id}"
											width=100px height=100px id="ImgDisplay" /><input
											type="file" 
											style="width: 74px;
												    padding-left: 0px;
												    padding-right: 0px;
												    height: 27px;"
											name="photo" accept="image/*"
											onchange="loadFile(event)"></td>
									</tr>
									<tr>
										<td>商品名稱:</td>
										<td><input type="TEXT" name="pro_name" size="45"
											value="${c2cproMainVO.pro_name}" /></td>
									</tr>
									<tr>
										<td>商品數量:</td>
										<td><input type="TEXT" name="quantity" size="45"
											value='${ (c2cproMainVO == null) ? "" : c2cproMainVO.quantity}' /></td>
									</tr>
									<tr>
										<td>商品價格:</td>
										<td><input type="TEXT" name="price" size="45"
											value='${ (c2cproMainVO == null) ? "" : c2cproMainVO.price}' /></td>
									</tr>

									
									<tr>
										<td>商品描述:</td>
										<td><textarea name="description" rows="6"
												placeholder="商品描述">${c2cproMainVO.description}</textarea></td>
									</tr>
									<tr>
										<td>商品狀態:</td>
										<td><select size="1" name="status" class="row margin-top-6 quinty selectpicker">
												<option value="1" ${c2cproMainVO.status==1 ? "selected" :""}>上架</option>
												<option value="0" ${c2cproMainVO.status==0 ? "selected" :""}>下架</option>

										</select></td>
									</tr>

									<tr>
										<td>取貨方式:</td>
										<td><select size="1" name="delivery" class="row margin-top-6 quinty selectpicker">

												<option value="1"
													${c2cproMainVO.delivery==1 ? "selected" :""}>宅配到府</option>
												<option value="2"
													${c2cproMainVO.delivery==2 ? "selected" :""}>便利店取貨</option>

										</select></td>

									</tr>

								</table>

							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">返回</button>
								<input type="hidden" name="action" value="update">
								<button type="submit" class="btn btn-primary">送出修改</button>
							</div>
						</FORM>
						</div>
					</div>
				</div>
			</div>
			<!----------------------------------修改規格------------------------------------------- -->
			<div class="modal fade" id="d${c2cproMainVO.pro_id}" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">修改商品規格</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<FORM METHOD="post" ACTION="c2cproDetail.do" name="form1">

							<div class="modal-body">
								<ul>
									<c:forEach var="c2cproDetailVO"
										items="${c2cproDetailSvc.getOneC2cproDetail(c2cproMainVO.pro_id)}">
										<c:forEach var="specMainVO" items="${specMainSvc.all}">
											<c:if test="${specMainVO.spec_id.equals(c2cproDetailVO.spec_id)}">
												 <div class="form-group row">
												    <label for="colFormLabelSm" class="col-sm-12 col-form-label col-form-label-sm">${specMainVO.spec_des}</label>
												    <div class="col-sm-12">
												      <input type="TEXT" class="form-control form-control-sm" id="colFormLabelSm" placeholder="col-form-label-sm"
												      name="${c2cproDetailVO.spec_id}" size="45"
													value='<c:out value="${c2cproDetailVO.spec_detail}" default="無"></c:out>'>
												    </div>
												  </div>
											</c:if>

										</c:forEach>
									</c:forEach>
								</ul>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">返回</button>
								<input type="hidden" name="action" value="update1"> <input
									type="hidden" name="pro_id" value="${c2cproMainVO.pro_id}">
								<button type="submit" class="btn btn-primary">送出修改</button>
							</div>
						</FORM>
					</div>
				</div>
			</div>
		</c:forEach>

	</div>
	<%@ include file="/front_end/footerMenu.jsp"%>
	<%@ include file="/front_end/footerSection.jsp"%>
	<script>
		$('[data-target="#${error_id}"]').click();
		$('[data-target="#${addproMain}"]').click();
		
		var loadFile = function(event) {
			var output = document.getElementById('ImgDisplay');
			output.src = URL.createObjectURL(event.target.files[0]);
			output.onload = function() {
				URL.revokeObjectURL(output.src) // free memory
			}
		};
		$(".pro_name").each(function(){
            var maxwidth=10;//設置最多顯示的字數
            var text=$(this).text();
            if($(this).text().length>maxwidth){
                $(this).text($(this).text().substring(0,maxwidth));
                $(this).html($(this).html()+"...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
            };
        });
	</script>



</body>


</html>
