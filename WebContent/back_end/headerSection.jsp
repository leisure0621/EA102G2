<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="icon" href="<%=request.getContextPath()%>/back_end/assets/images/favicon.png" type="image/png">
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="<%=request.getContextPath()%>/back_end/assets/plugins/datatables/css/jquery.dataTables-custom.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/back_end/assets/css/icons.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/assets/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/assets/css/style.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/assets/css/responsive.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/assets/css/bootstrap-datetimepicker.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/back_end/assets/css/public.css" rel="stylesheet" />
<!-- END PAGE LEVEL STYLES -->

<!-- 登出驗證 -->
<script>
window.onload = function(){
    $('.logout').click(function(){
        $.ajax({
            url: '<%=request.getContextPath()%>/emp/emp.do',
            type : 'post',
            data : {
                action : 'logout'
            },
            datatype: 'json',
            success : function(res) {
            	console.log(res)
                location.replace('<%=request.getContextPath() %>/back_end/login/login.jsp');
            }
        });
    });
}
</script>