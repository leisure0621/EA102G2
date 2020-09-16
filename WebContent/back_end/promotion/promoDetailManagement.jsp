<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.promo_detail.model.* "%>

<%
	PromoDetailService promoDetailSvc = new PromoDetailService();
	String query = (String) session.getAttribute("promoDetailSearch");
	List<PromoDetailVO> list = null;
	if (query == null || query.trim().length() == 0) {
		list = promoDetailSvc.getAll();
	}
	else {
		list = promoDetailSvc.getQuery(query);
	}
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("promoDetailSearch", query);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>促銷商品管理</title>
	<%@ include file="/back_end/headerSection.jsp"%>
	
	<style>
		tr {
			height: 50px;
		}
		
		.modal li {
			list-style: none;
		}
		
		.modal-body {
		    display: inherit;
		    text-align: center;
		}
	</style>

</head>

<body class="sticky-header">
	<%@ include file="/back_end/leftSideMenu.jsp"%>

	<!-- main content start-->
	<div class="main-content">
		<%@ include file="/back_end/headerMenu.jsp"%>

		<!--body wrapper start-->
		<div class="wrapper">

			<!--Start Page Title-->
			<div class="page-title-box">
				<h4 class="page-title">促銷商品管理</h4>
				<ol class="breadcrumb">
					<li><a href="promoManagement.jsp">促銷管理</a></li>
					<li><a href="promoDetailManagement.jsp">促銷商品管理</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->

			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>促銷商品列表</div>
							<div class="features">
								<div  class="btn btn-info add" onclick="location.href='addPromoDetail.jsp'">新增商品</div>
								<input type="text" class="form-control" name="query" value="${promoDetailSearch}" placeholder="依促銷編號、商品編號搜尋" />
							</div>
						</h2>
						<div class="table-responsive">
							<table class="display table">
								<thead>
									<tr>
										<th>促銷編號</th>
										<th>產品編號</th>
										<th>產品名稱</th>
										<th>促銷價格</th>
										<th>修改</th>
										<th>刪除</th>
									</tr>
								</thead>
								<tbody>
									<%@ include file="page1.file"%>
									<jsp:useBean id="bproSvc" scope="page" class="com.b2cpro_main.model.B2cproMainService" />
									<c:forEach var="aPromoDetailVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<tr>
											<td>${aPromoDetailVO.promo_id}</td>
											
											<c:forEach var="bproVO" items="${bproSvc.all}">
												<c:if test="${aPromoDetailVO.pro_id eq bproVO.pro_id}">
													<td>${bproVO.pro_id} </td>
													<td class="pro_name" title="${bproVO.pro_name}">${bproVO.pro_name.length() > 40 ? bproVO.pro_name.substring(0, 40) : bproVO.pro_name}${bproVO.pro_name.length() > 40 ? '...':''}</td>
												</c:if>
											</c:forEach>
											
											<td>$ ${aPromoDetailVO.promo_price}</td>
											<td>
												<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/promotion/promoDetail.do" style="margin-bottom: 0px;">
													<input class="btn btn-info" type="submit" value="修改"> 
													<input type="hidden" name="promo_id" value="${aPromoDetailVO.promo_id}"> 
													<input type="hidden" name="pro_id" value="${aPromoDetailVO.pro_id}"> 
													<input type="hidden" name="action" value="getOneForUpdateDisplay">
												</FORM>
											</td>
											<td>
											<button type="button" class="btn btn-info" data-toggle="modal" data-target="#delete${aPromoDetailVO.promo_id}">
											  刪除</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%@ include file="page2.file"%>
						</div>
					</div>
				</div>
			</div>
			<!--End row-->
		</div>
		<!-- End Wrapper -->
		
		<!-- delete Modal -->
		<c:forEach var="aPromoDetailVO" items="${list}" >
		<div class="modal fade" id="delete${aPromoDetailVO.promo_id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">刪除促銷商品</h5>
		      </div>
		      <div class="modal-body">
		       		<p>是否確定要刪除此促銷商品？</p>
				<button type="button" class="btn btn-primary mr-5 del" id="order" name="${aPromoDetailVO.promo_id}" value="${aPromoDetailVO.pro_id}">確定</button>
		        <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
		      </div>
		      <div class="modal-footer">
		      </div>
		    </div>
		  </div>
		</div>
		</c:forEach>
		<!-- 錯誤表列  -->
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
		<%@ include file="/back_end/footerMenu.jsp"%>
	</div>
	<!--End main content -->
	<%@ include file="/back_end/footerSection.jsp"%>

    <script>
	// 刪除資料
	$('.del').click(function(){
		$.ajax({
			url : '<%=request.getContextPath()%>/back_end/promotion/promoDetail.do',
			type : 'post',
			data : {
				action : 'delete',
				promo_id : $(this).attr('name'),
				pro_id : $(this).attr('value')
			},
			datatype : 'json',
			success : function(res){
				alert("已刪除成功!");
				window.location.reload();
			}
			
		});
	});
	// 搜尋資料
    $('.header-title [name="query"]').keydown(function(){
        if (event.keyCode == 13) {
        	data = {
       			query: $(this).val(),
       			action: 'search'
        	}
            $.ajax({
                url: "<%=request.getContextPath()%>/back_end/promotion/promoDetail.do",
                type : 'post',
                data : data,
                datatype: 'json',
                success : function(res) {
                	window.location.reload();
                }
            });
        };
    });
</script>
</body>
</html>