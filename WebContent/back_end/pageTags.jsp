<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*, com.emp_auth.model.*"%>
<% 
	EmpService empSvc = new EmpService();
	EmpVO empVO = (EmpVO) session.getAttribute("empVO");
	if(empVO != null){
		pageContext.setAttribute("empVO", empSvc.getOneEmp(empVO.getEmp_id()));
	}
  List<EmpAuthVO> empAuthVO = (List<EmpAuthVO>) session.getAttribute("empAuthVO");
  pageContext.setAttribute("empAuthVOList", empAuthVO);
%>