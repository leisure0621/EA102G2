<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<title>首頁</title>

<%@ include file="/back_end/headerSection.jsp" %>

<style>
.home .col-md-12 {
    text-align: center;
    padding-top: calc(100%/4 - 203px/2);
}
.home h1 {
    font-size: 135px;
}
.home .col-md-12 * {
    color: white;
    text-shadow: 0px 0px 14px black, 0px 0px 14px black;
}
.home .wrapper {
    background-image: url(<%=request.getContextPath()%>/back_end/assets/images/video.png);
}
</style>

</head>

<body class="sticky-header home">

    <%@ include file="/back_end/leftSideMenu.jsp" %>

    <!-- main content start-->
    <div class="main-content">

        <%@ include file="/back_end/headerMenu.jsp" %>
        
        <!--body wrapper start-->
        <div class="wrapper">

            <!--Start row-->
            <div class="row">
                <div class="col-md-12">
                    <h1>歡迎登入</h1>
                    <h5>Welcome To Login</h5>
                </div>
            </div>
            <!--End row-->

        </div>
        <!-- End Wrapper-->

    <%@ include file="/back_end/footerMenu.jsp" %>

    </div>
    <!--End main content -->

    <%@ include file="/back_end/footerSection.jsp" %>

</body>
</html>
