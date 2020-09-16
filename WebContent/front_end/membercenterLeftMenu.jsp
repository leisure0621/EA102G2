<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h5 class="shop-tittle margin-top-49 margin-bottom-30">會員中心</h5>
<ul class="shop-cate">

	<c:if test="${ memVO.authority > 0 }">
		<li class="${'/front_end/membercenter/memberInformation.jsp' eq pageContext.request.servletPath ? 'active': ''}">
			<a
			href="<%=request.getContextPath()%>/front_end/membercenter/memberInformation.jsp">
				個人訊息</a>
		</li>
		
		<li class="${'/front_end/membercenter/orderMain.jsp' eq pageContext.request.servletPath ? 'active': ''}">
			<a
			href="<%=request.getContextPath()%>/front_end/membercenter/orderMain.jsp">
				我的訂單</a>
		</li>
	</c:if>
	

	<c:if test="${ memVO.authority > 1 }">
		<li
			class="${'/front_end/c2cproMain/myshop.jsp' eq pageContext.request.servletPath ? 'active': ''}">
			<a
			href="<%=request.getContextPath()%>/front_end/c2cproMain/myshop.jsp">二手我的賣場</a>
		</li>
	</c:if>

	<c:if test="${ memVO.authority > 0 }">
		<li
			class="${'/front_end/c2cproMain/c2cbuyer.jsp' eq pageContext.request.servletPath ? 'active': ''}">
			<a
			href="<%=request.getContextPath()%>/front_end/c2cproMain/c2cbuyer.jsp">二手購買訂單</a>
		</li>
		<li
			class="${'/front_end/report/myReport.jsp' eq pageContext.request.servletPath ? 'active': ''}">
			<a href="<%=request.getContextPath()%>/front_end/report/myReport.jsp">檢舉紀錄</a>
		</li>
	</c:if>

	<c:if test="${memVO.authority > 1}">
		<li
			class="${'/front_end/c2cproMain/c2cSeller.jsp' eq pageContext.request.servletPath ? 'active': ''}">
			<a
			href="<%=request.getContextPath()%>/front_end/c2cproMain/c2cSeller.jsp">二手賣場訂單</a>
		</li>
	</c:if>
	
	<c:if test="${ memVO.authority > 0 }">
	<li
		class="${'/front_end/membercenter/myBidding.jsp' eq pageContext.request.servletPath ? 'active': ''}">
		<a
		href="<%=request.getContextPath()%>/front_end/membercenter/myBidding.jsp">
			我的競標</a>
	</li>
	<li
		class="${'/front_end/repairApplication/repairList.jsp' eq pageContext.request.servletPath ? 'active': ''}">
		<a
		href="<%=request.getContextPath()%>/front_end/repairApplication/repairList.jsp">
			查詢維修進度</a>
	</li>
	</c:if>
</ul>