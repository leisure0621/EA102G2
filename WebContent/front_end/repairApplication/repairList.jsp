<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="M_Adnan">
<title>維修單查詢</title>

<%@ include file="/front_end/headerSection.jsp" %>

<style>
ul.login-with {
    padding-top: 19px;
}
.btn:focus {
    color: white;
}
.active a {
    color: var(--headerBlackFontColor);
}
</style>

</head>
<body>

<%@ include file="/front_end/headerMenu.jsp" %>

<!-- LOADER -->
<div id="loader">
  <div class="position-center-center">
    <div class="ldr"></div>
  </div>
</div>

<!-- Wrap -->
<div id="wrap"> 
  
  <!-- Content -->
  <div id="content"> 
    
    <!--======= PAGES INNER =========-->
    <section class="member-center_information margin-top-23 margin-bottom-23 padding-bottom-23 padding-top-23">
      <div class="container"> 
        
        <!-- 會員中心功能 -->
        <div class="col-sm-3 leftSlide">
        	<%@ include file="/front_end/membercenterLeftMenu.jsp" %>
        </div>
        
        <!-- 頁面資料 -->
        <div class="col-sm-9 rightSlide shopping-cart">
       		<%@ include file="/front_end/repairApplication/rmSelect_page.jsp" %>
        </div>
        
      </div>
    </section>

  </div>
  
<%@ include file="/front_end/footerMenu.jsp" %>
  
</div>

<%@ include file="/front_end/footerSection.jsp" %>

</body>
</html>
