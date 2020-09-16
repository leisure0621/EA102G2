<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.msg.model.*"%>
<%@ page import="com.text_main.model.*"%>
<%@ page import="com.text_rep.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%
	String text_id = request.getParameter("text_id");
	pageContext.setAttribute("text_id",text_id);

	TextRepVO textrepVO = (TextRepVO) request.getAttribute("textrepVO");

	TextMainVO textmainVO = (TextMainVO) request.getAttribute("textmainVO");
%>
<jsp:useBean id="textmainSvc" scope="page"
	class="com.text_main.model.TextMainService" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="M_Adnan">
<title>WE DO ASSEMBLE</title>

<%@ include file="/front_end/headerSection.jsp"%>
<style>
.tp-banner-container .tp-banner>div {
	padding-top: 25%;
	position: absolute;
	z-index: 1;
	top: 0;
	left: calc(50% - 330px/ 2);
	width: 100%;
}

.tp-banner-container .btn {
	width: 330px;
	border-radius: 50px;
	letter-spacing: 2px;
	box-shadow: 0px 2px 4px rgb(45 58 75/ 0.5);
}

.tp-banner-container .btn.focus, .tp-banner-container .btn:focus,
	.tp-banner-container .btn:hover {
	color: #2d3a4b;
	background-color: var(- -headerBlackFontColor);
}

.home-slider .tp-banner-container .tp-banner img {
	display: none !important;
}

header.light-head .navbar li.active a:before, header.light-head .navbar li:hover a:before
	{
	width: 100%;
	margin: 0px auto;
	position: absolute;
	content: "";
	height: 6px;
	top: 45%;
	left: 0px;
	z-index: -1;
	background: var(- -headerBlackFontColor);
}

header.light-head .navbar li a:before {
	top: 45%;
	height: 6px;
}

header.light-head .navbar li a:hover {
	background: rgba(0, 0, 0, 0);
}

header.light-head .navbar .nav>li a {
	text-shadow: 1px 1px 0 black, -1px -1px 0 black;
}

video::-webkit-media-controls {
	display: none !important;
}

video {
	filter: brightness(0.95);
}

.tp-simpleresponsive img {
	display: none !important;
}

@media screen and (max-width: 768px) {
	.tp-banner-container video {
		display: none;
	}
	.home-slider .tp-banner-container .tp-banner img {
		max-height: 350px;
		display: block !important;
	}
}

.comments form .form-control {
	display: inline-block;
	width: 95%;
	height: 40px;
	margin-top: 5px;
	border-radius: 0px;
	box-shadow: none;
	background: none;
	border: 1px solid #2d3a4b;
}

element.style {
	font-size: 12pt;
	height: 120px;
	width: 720px;
}
input {
    border: 1px solid #2d3a4b;
    padding: 0 10px;
    height: 44px;
}
table {
    width: 100%;
}
[name="case_description"] {
    margin-top: 15px;
    width: 100%;
    padding: 10px;
    font-size: 11px;
    min-height: 350px;
}
[type="submit"] {
    line-height: 25px;
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

		<!-- Blog List -->
		
		
			<class="blog-list blog-list-3 single-post padding-top-100 padding-bottom-100">
			<div class="container">
				<div class="row">
					<div class="col-md-9">
						<tr>
							<td>
								<h3>檢舉內容</h3>
							</td>
						</tr>
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>

						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/back_end/textrep/textrep.do"
							name="form1">
							<table>
								<tr>
									<td colspan="2"><textarea type="text" 
									    placeholder="輸入檢舉描述"
									    name="case_description" size="45"
										/><%=(textrepVO == null) ? "" : textrepVO.getCase_description()%></textarea></td>
								</tr>

							</table>
							<br> <input type="hidden" name="action" value="insertrep">
							<input type="hidden" name="text_id" value="${text_id}">
							<input type="hidden" name="informant" value="${memVO.mem_id}">
							<input type="submit" class="btn" value="送出新增">
							<input class="btn btn-info" id="666" type="button" value="神奇小按鈕1">
							<input class="btn btn-info" id="777" type="button" value="神奇小按鈕2">
						</FORM>
						<!-- ADMIN info -->





							<!--=======  LEAVE COMMENTS =========-->




							<hr>

							<!-- SIDE BACR BANER -->
							<div class="side-bnr margin-top-50">
								<img class="img-responsive" src="images/sidebar-bnr.jpg" alt="">
								<div class="position-center-center"></div>
							</div>
					</div>
				</div>
			</div>
		</section>
	</div>

	<!-- Culture BLOCK -->
	<section class="cultur-block">

		<!-- Culture Text -->

	</section>
</body>

<!--======= FOOTER =========-->
<%@ include file="/front_end/footerMenu.jsp"%>
<%@ include file="/front_end/footerSection.jsp"%>
<script>
$('#666').click(function(){
 	$('[name="case_description"]').val('公然販售垃圾');
 });
 
$('#777').click(function(){
 	$('[name="case_description"]').val('公然聚賭');
 });
</script>

</body>
</html>