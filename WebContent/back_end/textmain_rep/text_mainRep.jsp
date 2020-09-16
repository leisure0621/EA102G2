<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.text_rep.model.*"%>
<%@ page import="com.text_main.model.*"%>
<%
	MemService memSvc = new MemService();
	String query = (String) session.getAttribute("query");
	List<MemVO> list = null;
	if (query == null || query.trim().length() == 0) {
		list = memSvc.getQuery("");
	} else {
		list = memSvc.getQuery(query);
	}
	pageContext.setAttribute("list", list);
%>
<%
	TextRepService TextRepSvc = new TextRepService();
	List<TextRepVO> list1 = TextRepSvc.getAll();
	pageContext.setAttribute("list1", list1);
%>
<%
	TextRepVO textrepVO = (TextRepVO) request.getAttribute("textrepVO"); //EmpServlet.java(Concroller)
%>
<jsp:useBean id="textrepSvc" scope="page"
	class="com.text_rep.model.TextRepService" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<title>會員修改</title>
<style>
table {
	border-spacing: 0;
	border-collapse: collapse;
	table-layout: fixed;
}

button, input, optgroup, select, textarea {
	margin: 16px;
	font: inherit;
	color: inherit;
}

.status {
	width: 68px;
	height: 28px;
	border-radius: 25px;
}
</style>
<%@ include file="/back_end/headerSection.jsp"%>

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
				<h4 class="page-title">討論區管理</h4>
				<ol class="breadcrumb">
					<li><a>文章管理</a></li>
					<li><a>文章列表</a></li>
				</ol>
				<div class="clearfix"></div>
			</div>
			<!--End Page Title-->
			<!--Start row-->
			<div class="row">
				<div class="col-md-12">
					<div class="white-box">
						<h2 class="header-title">
							<div>討論區列表</div>
							
						</h2>
						<div class="table-responsive">
							<table id="memlist" class="display table">
								<thead>
									<tr>
										<th>檢舉單編號</th>
										<th>檢舉文章編號</th>
										<th>檢舉會員編號</th>
										<th>處理進度</th>
										<th>完成時間</th>
										<th>建立時間</th>
										<th>詳情</th>
										<th>處理</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="textrepVO" items="${list1}">
										<tr data-mem="${textrepVO.rep_id}">
											<td>${textrepVO.rep_id}</td>
											<td>${textrepVO.text_id}</td>
											<td>${textrepVO.informant}</td>
											<td class="process">
															<c:if test="${textrepVO.process==0}">未處理</c:if> 
															<c:if test="${textrepVO.process==1}">完成且處置</c:if> 
														    <c:if test="${textrepVO.process==2}">完成且不處置</c:if></td>
											<td><fmt:formatDate value="${textrepVO.finish_time}"
													pattern="yyyy/MM/dd HH:mm" /></td>		    
											<td><fmt:formatDate value="${textrepVO.est_time}"
													pattern="yyyy/MM/dd HH:mm" /></td>
											
											<td>
												<button type="button" class="btn btn-info modify"
													data-toggle="modal" data-target="#d${textrepVO.rep_id}">詳情</button>
											</td>
											<td>
												<button type="button" class="btn btn-info modify"
													data-toggle="modal" data-target="#eee${textrepVO.rep_id}"
													data-text_id="${textrepVO.text_id}"
													data-rep_id="${textrepVO.rep_id}">處理</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!--End row-->
		</div>
		<!-- End Wrapper-->

		<!-- Popup Large Modal -->
		<div id="popupForm" class="modal fade" role="dialog">
			<div class="modal-dialog modal-lg"></div>
		</div>
		<!--END Popup Large Modal -->

		<%@ include file="/back_end/footerMenu.jsp"%>

	</div>
	<!--End main content -->

	<%@ include file="/back_end/footerSection.jsp"%>
	
	<c:forEach var="textrepVO" items="${list1}">
		<div class="modal fade" id="d${textrepVO.rep_id}" tabindex="-1"
			role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">檢舉描述</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" style="margin-left: 6px;">
						<ul>
							<li>檢舉人會員編號: ${textrepVO.informant}</li>
							<li>被檢舉文章編號: ${textrepVO.text_id}</li>
							<li>檢舉描述: ${textrepVO.case_description}</li>
						</ul>
					</div>

				</div>
			</div>
		</div>
		<!--------------------------------處理檢舉------------------------------------------- -->
		<div class="modal fade" id="eee${textrepVO.rep_id}" tabindex="-1"
			role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">處理檢舉</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" style="margin-left: 6px;">
						<jsp:useBean id="textmainSvc" scope="page"
							class="com.text_main.model.TextMainService" />
						<ul>
							<li>文章編號:
								${textmainSvc.getOneText_main(textrepVO.text_id).text_id}</li>
							<li>文章標題:
								${textmainSvc.getOneText_main(textrepVO.text_id).title}</li>
							<li>文章內容:
								${textmainSvc.getOneText_main(textrepVO.text_id).content}</li>
							<li>發文會員編號:
								${textmainSvc.getOneText_main(textrepVO.text_id).author_id}</li>
							<li>文章狀態: 
								<select size="1" name="status">
									<option value="1"
										${textmainSvc.getOneText_main(textrepVO.text_id).status == 1 ? "selected" :""}>維持顯示</option>
									<option value="0"
										${textmainSvc.getOneText_main(textrepVO.text_id).status == 0 ? "selected" :""}>強制移除</option>
								</select>
							</li>	
						</ul>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary update_textStatus">確定</button>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
	<script>
    let data = {};
	
	$('.modify').click(function(){
		data = {};
		// data-pro_id
		data.text_id = $(this).data('text_id');
		data.rep_id = $(this).data('rep_id');
		console.log(data.text_id, data.rep_id);
	
	});
	
	$('.update_textStatus').click(function(){
	//	$('[name="status"]').val();
// 		data.status = $('#eee'+ data.rep_id +' [name="status"]').val();
		data.status = $('#eee'+ data.rep_id +' [name="status"]').val();
		console.log(data.status);
		data.action = 'update_textStatus';
		console.log(data);
		$.ajax({
			url:'<%=request.getContextPath()%>/front_end/textmain/textmain.do',
			type:'post',
			data:data,
			datatype:'json',
			success : function(res) {
				res = JSON.parse(res).data;
			//	alert(res);
		success_update(res);
		window.location.reload();
		
		$('[data-dismiss="modal"]').click();
		
			}
		});
			

	});
	function success_update(res){
		 
		 data.process = res.process;
		 data.action='update_textrepProcess';
		 console.log(data);
		 $.ajax({
			 url:'<%=request.getContextPath()%>/back_end/textrep/textrep.do',
			type : 'post',
			data : data,
			datatype : 'json',
			success : function(res) {
				res = JSON.parse(res).data;
console.log(res);
				
				
				 					
				$('[data-rep_id='+ data.rep_id+ ']>td:nth-child(3)').text(res.process);
				$('[data-rep_id='+ data.rep_id+ ']>td:nth-child(4)').text(res.date);
			}

		});
	}
// 	 $('.modifyAuthority').change(function(){
// 		 data={
// 				rep_id:$(this).data("rep_id"),
// 				process:$(this).find("option:selected").val(),
// 				action:'update_proRep'
// 		 }
// 		 console.log(data);
// 		 $.ajax({
<%-- 			 url:'<%=request.getContextPath()%>/front_end/c2cproMain/c2cproRep.do', --%>
// 			type : 'post',
// 			data : data,
// 			datatype : 'json',
// 			success : function(res) {
// 				res = JSON.parse(res).data;
// 				alert(res);
// 				console.log(data.finish_time);
// 				// 					if(res){
// 				// 						$('[data-rep_id="'+c2cproRepVO.rep_id+'"]>td:nth-child(4)').text(data.finish_time);
// 				// 					} 
// 			}

// 		});

// 	});
	</script>
</body>
</html>
