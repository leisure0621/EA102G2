<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*,com.mem.model.*" %>
<%@ page import="com.bid_main.model.*, java.util.stream.Collectors" %>
<% 
    // 會員
	MemService memSvc = new MemService();
	MemVO memVO = (MemVO) session.getAttribute("memVO");
	if(memVO != null){
		pageContext.setAttribute("memVO", memSvc.getOneMem(memVO.getMem_id()));
	}
	// 購物車
	String cartList = (String) session.getAttribute("cartList");
	pageContext.setAttribute("cartList", cartList);
	// 競標品
    BidMainService bidmSvc = new BidMainService();
    List<BidMainVO> bidList = bidmSvc.getAll().stream().filter(e -> e.getStatus().equals(1)).collect(Collectors.toList());
    bidList = bidList.subList(bidList.size()-2, bidList.size());
    pageContext.setAttribute("bidList", bidList);
%>

<link rel="icon" type="image/png" href="<%=request.getContextPath() %>/front_end/assets/img/favicon.png"/>
<!-- SLIDER REVOLUTION 4.x CSS SETTINGS -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/front_end/assets/rs-plugin/css/settings.css" media="screen" />

<!-- Bootstrap Core CSS -->
<link href="<%=request.getContextPath() %>/front_end/assets/css/bootstrap.min.css" rel="stylesheet" />

<!-- Custom CSS -->
<link href="<%=request.getContextPath() %>/front_end/assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/front_end/assets/css/ionicons.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/front_end/assets/css/main.css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/front_end/assets/css/style.css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/front_end/assets/css/responsive.css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/front_end/assets/css/public.css" rel="stylesheet" type="text/css" />
<!-- JavaScripts -->
<script src="<%=request.getContextPath() %>/front_end/assets/js/modernizr.js"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

