<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.c2cpro_rep.model.*"%>
<%@ page import="com.c2cpro_main.model.*"%>
<%
	C2cproRepService main = new C2cproRepService();
	List<C2cproRepVO> list = main.getAll();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<title>檢舉審核</title>

<%@ include file="/back_end/headerSection.jsp"%>
<style>
.modal-dialog {
	width: 750px;
	margin: 100px auto;
}

.form-control {
	width: 58%;
}

.btn:focus {
	color: #fffafa;
	outline: none;
	outline: none;
	outline-offset: 0px;
}

.status {
	width: 193px;
	height: 0px;
	border-radius: 14px;
	border: none;
}
.white-box {
    height: 600px;
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
				<h4 class="page-title">C2C檢舉管理</h4>
				<ol class="breadcrumb">
					<li><a>檢舉管理</a></li>
					<li><a>檢舉列表</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->


			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>商品檢舉列表</div>
							<div class="features"></div>
						</h2>
						<div class="table-responsive">
							<table id="memlist" class="display table">
								<thead>
									<tr>
										<th>檢舉單編號</th>
										<th>建立時間</th>
										<th>處理進度</th>
										<th>完成時間</th>
										<th>詳情</th>
										<th>處理</th>

									</tr>
								</thead>

								<tbody>
									<%@ include file="/back_end/page1.file"%>
									<c:forEach var="c2cproRepVO" items="${list}"
										begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

										<tr data-rep_id="${c2cproRepVO.rep_id}">
											<td>${c2cproRepVO.rep_id}</td>
											<td><fmt:formatDate value="${c2cproRepVO.est_time}"
													pattern="yyyy/MM/dd" /></td>
<!-- 											<td><select name="authority" -->
<%-- 												data-rep_id="${c2cproRepVO.rep_id}" --%>
<!-- 												class="form-control modifyAuthority"> -->
<%-- 													<c:forEach var="i" begin="0" end="2" varStatus="loop"> --%>
<%-- 														<option value="${i}" --%>
<%-- 															${(c2cproRepVO.process == i ) ? 'selected' : ''}> --%>




<!-- 														</option> -->
<%-- 													</c:forEach> --%>
<!-- 												</select></td> -->
											<td class="process">
															<c:if test="${c2cproRepVO.process==0}">未處理</c:if> 
															<c:if test="${c2cproRepVO.process==1}">完成且處置</c:if> 
														<c:if test="${c2cproRepVO.process==2}">完成且不處置</c:if></td>
											<td><fmt:formatDate value="${c2cproRepVO.finish_time}"
													pattern="yyyy/MM/dd HH:mm" /></td>
											<td>
												<button type="button" class="btn btn-info modify"
													data-toggle="modal" data-target="#d${c2cproRepVO.rep_id}">詳情</button>
											</td>
											<td>

												<button type="button" class="btn btn-info modify"
													data-toggle="modal" data-target="#${c2cproRepVO.rep_id}"
													data-pro_id="${c2cproRepVO.reported_content}"
													data-rep_id="${c2cproRepVO.rep_id}"
													data-informant="${c2cproRepVO.informant}">處理</button>

											</td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%@ include file="/back_end/page2.file"%>
						</div>
					</div>
				</div>

				<!--End row-->
			</div>
			<!-- End Wrapper-->

			<!-- Popup Large Modal -->

			<!--END Popup Large Modal -->

			

		</div>
		<!--End main content -->
		<%@ include file="/back_end/footerMenu.jsp"%>

		<!--------------------------------查看詳情------------------------------------------- -->

		<c:forEach var="c2cproRepVO" items="${list}">
			<div class="modal fade" id="d${c2cproRepVO.rep_id}" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="exampleModalLabel">檢舉描述</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" style="margin-left: 50px;margin-right: 50px;">



							<ul class="list-group list-group-flush">

								<li>檢舉人會員編號: ${c2cproRepVO.informant}</li>
								<br>
								<li>被檢舉商品編號: ${c2cproRepVO.reported_content}</li>
								<br>
								<li>檢舉描述: ${c2cproRepVO.case_description}</li>



							</ul>
						</div>
							<div class="modal-footer">
							
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
							
						</div>
					</div>
				</div>
			</div>

			<!--------------------------------處理商品------------------------------------------- -->
			<div class="modal fade" id="${c2cproRepVO.rep_id}" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="exampleModalLabel">處理商品</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" style="margin-left: 50px;margin-right: 50px;">
							<jsp:useBean id="c2cproMainSvc" scope="page"
								class="com.c2cpro_main.model.C2cproMainService" />




							<ul>

								<li><img width="100px" height="100px"
									src="<%= request.getContextPath()%>/front_end/c2cproMain/c2cproMain.do?pro_id=${c2cproMainSvc.getOneC2cproMain(c2cproRepVO.reported_content).pro_id}"></li>
								<br>
								<li>商品編號:
									${c2cproMainSvc.getOneC2cproMain(c2cproRepVO.reported_content).pro_id}</li>
								<br>
								<li>商品名稱:
									${c2cproMainSvc.getOneC2cproMain(c2cproRepVO.reported_content).pro_name}</li>
								<br>
								<li>價格:<fmt:formatNumber type="number"
										value="${c2cproMainSvc.getOneC2cproMain(c2cproRepVO.reported_content).price}"
										maxFractionDigits="0" /> 元
								</li>
								<br>
								<li>商品描述:
									${c2cproMainSvc.getOneC2cproMain(c2cproRepVO.reported_content).description}</li>
								<br>

							</ul>
						</div>
						<div class="modal-footer">
							<div class="status">
								商品狀態: <select size="1" name="status">
									<option value="1"
										${c2cproMainSvc.getOneC2cproMain(c2cproRepVO.reported_content).status==1 ? "selected" :""}>保持上架</option>
									<option value="0"
										${c2cproMainSvc.getOneC2cproMain(c2cproRepVO.reported_content).status==0 ? "selected" :""}>強制下架</option>

								</select>
							</div>
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary update_proStatus">確定</button>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>

		<%@ include file="/back_end/footerSection.jsp"%>





		<script>
		let data = {};
		
		$('.modify').click(function(){
			data = {};
			// data-pro_id
			data.pro_id = $(this).data('pro_id');
			data.rep_id = $(this).data('rep_id');
			data.informant=$(this).data('informant');
		});
		
		$('.update_proStatus').click(function(){
			data.status = $('#'+ data.rep_id +' [name="status"]').val();
			data.action = 'update_proStatus';
			
			$.ajax({
				url:'<%=request.getContextPath()%>/front_end/c2cproMain/c2cproMain.do',
				type:'post',
				data:data,
				datatype:'json',
				success : function(res) {
					res = JSON.parse(res).data;
					
//  					alert(res.succe);
					
 					success_update(res);
 					

					$('[data-dismiss="modal"]').click();
					 
				}
			});
			
		});
	
		 function success_update(res){
			 
 			 data.process = res.process;
			 data.action='update_proRep';
			 console.log(data);
			 $.ajax({
				 url:'<%=request.getContextPath()%>/front_end/c2cproMain/c2cproRep.do',
				type : 'post',
				data : data,
				datatype : 'json',
				success : function(res) {
					res = JSON.parse(res).data;
console.log(res);
					
					
					 					
 					$('[data-rep_id='+ data.rep_id+ ']>td:nth-child(3)').text(res.process);
					$('[data-rep_id='+ data.rep_id+ ']>td:nth-child(4)').text(res.date);
					
					 news = {};
			            news.msg = "辛苦了!忙著上課，還要忙檢舉，<br>但還是感謝您照顧我們的網站，祝您一生平安";
			            news.informant = data.informant;
					c2creport(news);
				}

			});

		}
		 
		//websocket
	      function c2creport(news) {
	        socket.data.c2creport = {};
	        socket.data.c2creport.msg = news.msg;
	        socket.data.c2creport.informant = news.informant;
	        sendMessage(socket.data);
	        console.log(socket.data);
		}
		</script>
</body>
</html>
