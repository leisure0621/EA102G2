<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.dept.model.*, com.emp_auth_detail.model.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<title>登入</title>

<%@ include file="/back_end/headerSection.jsp" %>

<style>
.logo {
    height: 100%;
}
</style>

</head>

<body class="sticky-header">

<!--Start login Section-->
 <section class="login-section">
      <div class="container">
          <div class="row">
              <div class="login-wrapper">
                  <div class="login-inner">
                      
                      <div class="logo">
                        <img src="<%=request.getContextPath()%>/back_end/assets/images/logo.png"  alt="logo"/>
                      </div>
                       
                      <div class="form">
                          <div class="form-group">
                              <input type="text" class="form-control"  placeholder="請輸入您的工號" name="emp_id">
                          </div>
                          
                          <div class="form-group">
                              <input type="password" class="form-control"  placeholder="請輸入您的密碼" name="password">
                          </div>
                          
                          <div class="form-group">
                              <div class="btn btn-primary btn-block login" >登入</div>
                          </div>
                          
                      </div>
                      
                       <div class="copy-text"> 
                        <p class="m-0">2020 &copy; Meter admin</p>
                       </div>
                   
                  </div>
              </div>
              
          </div>
      </div>
 </section>
<!--End login Section-->

<!--Begin core plugin -->
<script src="<%=request.getContextPath()%>/back_end/assets/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/back_end/assets/js/bootstrap.min.js"></script>
<!-- End core plugin -->

<script>
$(function(){
	let data = {};
	let url = '<%=request.getContextPath()%>/emp/emp.do';
	function dataVerification(action) {
		data.emp_id = $('[name="emp_id"]').val();
		if(!data.emp_id) {
			alert('工號不能為空');
			return;
		}
        data.emp_password = $('[name="password"]').val();
        if(!data.emp_password) {
            alert('密碼不能為空');
            return;
        }
        data.action = action;
        return true;
	}
	// 登入
	$('.login').click(function(){
		if(dataVerification('login')) {
			$.ajax({
	            url: url,
	            type : 'post',
	            data : data,
	            datatype: 'json',
	            success : function(res) {
	                res = JSON.parse(res);
	                if (res.data){
	                	location.replace('<%=request.getContextPath() %>/back_end/home/home.jsp');
	                } else {
	                	alert(res.err);
	                }
	            },
	        });
		}
	});
});
</script>

</body>

</html>
