<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.catagory.model.*,com.b2cpro_main.model.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>List Products by Category</title>

<%@ include file="/back_end/headerSection.jsp" %>
<script>
	window.onload = function(){
		$(".pro_des").each(function(){
			var maxwidth=15;//設置最多顯示的字數
			var text=$(this).text();
			if($(this).text().length>maxwidth){
			$(this).text($(this).text().substring(0,maxwidth));
			$(this).html($(this).html()+"...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
			};
		});
	}
</script>
</head>

<body class="sticky-header">

    <%@ include file="/back_end/leftSideMenu.jsp" %>

<!--     main content start -->
    <div class="main-content">

        <%@ include file="/back_end/headerMenu.jsp" %>
        
<!--         body wrapper start -->
        <div class="wrapper">

<!--             Start Page Title -->
            <div class="page-title-box">
                <h4 class="page-title">商品管理</h4>
                <ol class="breadcrumb">
                 	<li><a
						href="<%=request.getContextPath()%>/back_end/b2cproduct/listAllPro.jsp">所有產品列表</a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/b2cproduct/addPro.jsp">新增商品</a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/b2cproduct/searchProByCat.jsp">以分類查詢商品</a></li>
				</ol>
                <div class="clearfix"></div>
            </div>
<!--             End Page Title -->


<!--             Start row -->
            <div class="row">
                <div class="col-md-12">
                    <div class="white-box">
                        <h2 class="header-title">
                            <div>產品列表</div>
                            <div class="features">
	                            <form method="post" action="<%=request.getContextPath()%>/back_end/b2cproduct/b2cpro.do">
									<input type="hidden" name="action" value="addProToCat"> 
									<input type="hidden" name="cat_id" value="${catVO.cat_id}"> 
									<input type="submit" value="在分類中新增商品" class="btn btn-info">
								</form>
							</div> 
                        </h2>
                        <div class="table-responsive">
<!--                         	錯誤表列 -->
							<c:if test="${not empty errorMsgs}">
								<font style="color: red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color: red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
<!-- 							錯誤表列 End -->
                            <table id="emplist" class="display table">
                                <thead>
                                    <tr>
										<th>商品編號</th>
										<th>商品名稱</th>
										<th>商品圖片</th>
										<th>建議售價</th>
										<th>庫存</th>
										<th>製造商</th>
										<th>商品狀態</th>
										<th>商品描述</th>
										<th>建立時間</th>
										<th>修改</th>
										<th>顯示</th>
                                    </tr>
                                </thead>
                               
                                <tbody>
									<c:forEach var="aPro" items="${pros}">
									<tr>
										<td>${aPro.pro_id}</td>
										<td>${aPro.pro_name}</td>
										<td>
											<a href='<%=request.getContextPath()%>/back_end/b2cproduct/images/${aPro.pro_id}.png' target="_blank">
												<img width="50" height="50" src="<%= request.getContextPath()%>/back_end/b2cproduct/b2cpro.do?pro_id=${aPro.pro_id}">
											</a>
										</td>
										<td>${aPro.rrp}</td>
										<td>${aPro.stock}</td>
										<td>${aPro.vendor_id}</td>
										<td>${aPro.status eq 0? "下架":"上架"}</td>
										<td class='pro_des'>${aPro.pro_des}</td>
										<td><fmt:formatDate value="${aPro.est_time}" pattern="yyyy-MM-dd" /></td>
										<td>
											<form method="post" action="<%=request.getContextPath()%>/back_end/b2cproduct/b2cpro.do">
												<input type="hidden" name="pro_id" value="${aPro.pro_id}">
												<input type="hidden" name="action" value="getOneForUpdate">
												<input type="submit" value="修改商品資訊" class="btn btn-info">
											</form>
										</td>
										<td>
											<form method = "post" action = "<%=request.getContextPath()%>/back_end/spec/spec.do">
												<input type="hidden" name="pro_id" value="${aPro.pro_id}">
												<input type="hidden" name="action" value="getOneForDisplay">
												<input type="submit" value="顯示商品規格" class="btn btn-info">
											</form>
										</td>
									</tr>
									</c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
<!--             End row -->
        </div>
<!--         End Wrapper -->
        
    <%@ include file="/back_end/footerMenu.jsp" %>

    </div>
<!--     End main content -->

    <%@ include file="/back_end/footerSection.jsp" %>
</body>
</html>
