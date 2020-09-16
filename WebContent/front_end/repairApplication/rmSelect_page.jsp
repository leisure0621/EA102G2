<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.repair_main.model.*"%>
<%@ page import="com.repair_status.model.*"%>
<%@ page import="com.mem.model.*"%>

<%
	
	RepairMainVO rmVO = (RepairMainVO) request.getAttribute("rmVO");//RepairMainServlet.java(Controller), 存入req的rmVO物件
	
	if(rmVO != null){
		RepairMainService rmSvc = new RepairMainService();
		List<RepairMainVO> list = rmSvc.findByMem_id(rmVO.getMem_id());
		pageContext.setAttribute("list", list);
	}
	RepairMainService rmSvc = new RepairMainService();
	List<RepairMainVO> list = rmSvc.findByMem_id(memVO.getMem_id());
	pageContext.setAttribute("memList", list);
	
	
%>

<jsp:useBean id="rsSvc" scope="page" class="com.repair_status.model.RepairStatusService" />
<jsp:useBean id="catSvc" scope="page" class="com.catagory.model.CatagoryService" />

<div class="cart-ship-info">

  <h6>已申請的維修單</h6>

  <ul class="row memberForm">
    <div class="col-md-12">
      <table class="display table">
        <thead>
          <tr>
            <th>維修品名稱</th>
            <th>問題描述</th>
            <th>維修單狀態</th>
            <th>申請時間</th>
            <th>總金額</th>
            <th>收貨地址</th>
            <th>收貨人</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="rmVO" items="${memList}">
            <tr>
              <td>${rmVO.pro_name}</td>
              <td title="${rmVO.description}">${rmVO.description.length() > 5 ? rmVO.description.substring(0, 5) : rmVO.description}${rmVO.description.length() > 5 ? '...':''}</td>
              <td>
                <c:forEach var="rsVO" items="${rsSvc.all}">
                  <c:if test="${rmVO.status_id==rsVO.status_id}">${rsVO.status_des}</c:if>
                </c:forEach>
              </td>
              <td>
                <fmt:formatDate value="${rmVO.est_time}" pattern="yyyy-MM-dd HH:mm:ss" />
              </td>
              <td>
                <c:choose>
                  <c:when test="${rmVO.amount == 0.0}">待報價</c:when>
                  <c:otherwise>${rmVO.amount}</c:otherwise>
                </c:choose>
              </td>
              <td title="${rmVO.dev_address}">${rmVO.dev_address.length() > 5 ? rmVO.dev_address.substring(0, 5) : rmVO.dev_address}${rmVO.dev_address.length() > 5 ? '...':''}</td>
              <td>${rmVO.recipient}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </ul>
</div>