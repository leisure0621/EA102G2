<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.repair_main.model.*"%>
<%@ page import="com.repair_status.model.*"%>
<%@ page import="com.catagory.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%
	RepairMainVO rmVO = (RepairMainVO) request.getAttribute("rmVO"); //RepairMainServlet.java(Controller), 存入req的rmVO物件
	
%>

<%
	RepairStatusService rsSvc = new RepairStatusService();
	RepairStatusVO rsVO = rsSvc.getOneRepairStatus(rmVO.getStatus_id());

	CatagoryService catSvc = new CatagoryService();
	CatagoryVO catVO = catSvc.findByPrimaryKey(rmVO.getCat_id());
%>


<div class="cart-ship-info">
	<h6>維修單主檔資料</h6>

	<ul class="row memberForm">


			<table class="display table">
			
			<thead>
			
				<tr>
					<th>維修單號</th>
					<th>申請會員編號</th>
<!-- 					<th>維修商品分類</th> -->
					<th>維修品名稱</th>
					<th>問題描述</th>
					<th>維修單狀態編號</th>
					<th>申請時間</th>
					<th>總金額</th>
					<th>收貨地址</th>
					<th>收貨人</th>
					<!-- 				<th>付款方式</th> -->
					<!-- 				<th>收貨方式</th> -->
				</tr>
				
				</thead>
				
				<tbody>
				
				<tr>
					<td><%=rmVO.getRepair_id()%></td>
					<td><%=rmVO.getMem_id()%></td>

<%-- 					<td><%=rmVO.getCat_id()%> 【<%=catVO.getCat_des()%>】</td> --%>

					<%--     <td><%=rmVO.getCat_id()%></td> --%>

					<td><%=rmVO.getPro_name()%></td>
					<td><%=rmVO.getDescription()%></td>

					<td>【<%=rsVO.getStatus_des()%>】</td>

					<%--     <td><%=rmVO.getStatus_id()%></td> --%>

					<td> <fmt:formatDate value="${rmVO.est_time}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>

<%-- 					<td><%=rmVO.getEst_time()%></td> --%>
					
					<td><%=rmVO.getAmount()%></td>
					<td><%=rmVO.getDev_address()%></td>
					<td><%=rmVO.getRecipient()%></td>
					<%-- 				<td><%=rmVO.getPay_via()%></td> --%>
					<%-- 				<td><%=rmVO.getDelivery()%></td> --%>
				</tr>
				
				</tbody>
				
			</table>
	</ul>

</div>








