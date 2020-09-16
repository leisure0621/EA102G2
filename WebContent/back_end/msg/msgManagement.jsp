<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.text_main.model.*"%>
<%@ page import="com.msg.model.*"%>

 <%
 	MsgService MsgService = new MsgService();
	List<MsgVO> list = MsgService.getAll();
	pageContext.setAttribute("list", list);
    %>
  <%
  MsgVO msgVO = (MsgVO) request.getAttribute("msgVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<title>會員修改</title>
<style>
table {
    border-spacing: 0;
    border-collapse: collapse;
    table-layout: fixed;
}
</style>
<%@ include file="/back_end/headerSection.jsp" %>

</head>

<body class="sticky-header">

	<%@ include file="/back_end/leftSideMenu.jsp" %>

    <!-- main content start-->
    <div class="main-content">

		<%@ include file="/back_end/headerMenu.jsp" %>
		
        <!--body wrapper start-->
        <div class="wrapper">

            <!--Start Page Title-->
            <div class="page-title-box">
                <h4 class="page-title">討論區管理</h4>
                <ol class="breadcrumb">
                    <li><a>留言管理</a></li>
                    <li><a>留言列表</a></li>
                </ol>
                <div class="clearfix"></div>
            </div>
            <!--End Page Title-->


            <!--Start row-->
            <div class="row">
                <div class="col-md-12">
                    <div class="white-box">
                        <h2 class="header-title">
                        	<div>討論區列表</div>
	                        <div class="features">
	                           <FORM METHOD="post" ACTION="textmain.do" >
        <input type="text" name="text_id" placeholder="請輸入文章編號" >
        <input type="hidden" name="action" value="getOne_For_Display1">
        <input type="submit" style="display : none">
    </FORM>
                        </h2>
                        <div class="table-responsive">
                            <table id="memlist" class="display table">
                                <thead>
                                    <tr>
                                    	<th>留言ID</th>
                                        <th>文章ID</th>
                                        <th>會員編號</th>
                                        <th>留言內容</th>
                                        <th>建立時間</th>
                                        <th>權限</th>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <th>留言ID</th>
                                        <th>文章ID</th>
                                        <th>會員編號</th>
                                        <th>留言內容</th>
                                        <th>建立時間</th>
                                        <th>權限</th>
                                    </tr>
                                </tfoot>
                                <tbody>
                                	<%@ include file="/back_end/page1.file" %> 
                                    <c:forEach var="msgVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                                        <tr data-mem="${msgVO.text_id}">
                                        	<td>${msgVO.msg_id}</td>
                                            <td>${msgVO.text_id}</td>
                                            <td>${msgVO.author_id}</td>
                                            <td>${msgVO.content}<br></td>
                                            <td>${msgVO.est_time}</td>
                                           
                                            <td>
                                                <select name="authority" data-mem_id="${msgVO.text_id}" class="form-control modifyAuthority">
                                                    <c:forEach var="i" begin="0" end="1" varStatus="loop">
                                                        <option value="${i}" ${(msgVO.status == i ) ? 'selected' : ''}>
                                                            <c:if test="${ i == 0 }">隱藏</c:if>
                                                            <c:if test="${ i == 1 }">顯示</c:if>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <%@ include file="/back_end/page2.file" %>
                        </div>
                    </div>
                </div>
            </div>
            <!--End row-->
        </div>
        <!-- End Wrapper-->

        <!-- Popup Large Modal -->
        <div id="popupForm" class="modal fade" role="dialog">
            <div class="modal-dialog modal-lg">

                
            </div>
        </div>
        <!--END Popup Large Modal -->

	<%@ include file="/back_end/footerMenu.jsp" %>

    </div>
    <!--End main content -->

	<%@ include file="/back_end/footerSection.jsp" %>

    <script>

    $(function () {
        let data = {};
        let url = '<%=request.getContextPath()%>/mem/mem.do';
        // 修改權限
        $('.modifyAuthority').change(function(){
            data = {
                mem_id: $(this).data("mem_id"),
                authority: $(this).find("option:selected").val(),
                action: 'update_authority'
            }
            $.ajax({
                url: url,
                type : 'post',
                data : data,
                datatype: 'json',
                success : function(res) {
                	res = JSON.parse(res).data;
                    alert(res);
                },
            });
        });
        // 點擊修改按鈕 跳出彈窗
        $('.modify').click(function(){
        	let name, element;
            data = {
                mem_id: $(this).data("mem_id"),
                action: 'getOne_For_Update',
            }
            $.ajax({
                url: url,
                type : 'post',
                data : data,
                datatype: 'json',
                success : function(res) {
                	res = JSON.parse(res).data[0];
                    if(res) {
                        $("#popupForm [name]").each(function(){
                            name = $(this).attr('name');
                            element = $(this);
                            if(res.credit_card && name === 'credit_card_expires') {
                            	element.val(res[name].split('-')[0]+'-'+res[name].split('-')[1]);
                            }
                            else if(!res.credit_card && (name === 'credit_card_cvc' || name === 'credit_card_expires')) {
                            	element.val('');
                            }
                            else if(name === 'est_time'){
                                let est = res[name].split(':');
                                element.val(est[0]+':'+est[1]);
                            }
                            else if(name === 'authority') {
                                $('#popupForm [name="'+name+'"] option[value="'+res[name]+'"]').attr('selected', 'selected');
                            }
                            else {
                            	element.val(res[name]);
                            }
                        });
                    }
                },
            });
        });
        // 時間選擇器
        $('.credit_card_expires').datetimepicker({
            format: 'YYYY-MM',
            locale: moment.locale('zh-tw')
        });
        // 修改資料
        $('.update').click(function(){
            let emailReg = '(.)@(.)';
            let name, element;
            let credit_card = $('#popupForm [name="credit_card"]').val();
            if (!$('[name="email"]').val().match(emailReg)) {
                alert("電子郵件地址必須包括 ( @ 和 . )");
                return;
            }
            if (!$('[name="first_name"]').val()) {
                alert("姓氏不能為空");
                return;
            }
            if (!$('[name="last_name"]').val()) {
                alert("名稱不能為空");
                return;
            }
            // 添加 修改資料
            $("#popupForm [name]").each(function(){
                name = $(this).attr('name');
                element = $(this);
                
                if(name === 'authority' && $("#popupForm [name="+name+"] option:selected").val()) {
                	data.authority = $("#popupForm [name="+name+"] option:selected").val();
                } else if(!credit_card && name === 'credit_card_expires') {
                	data.credit_card_expires = "1990-01-01";
                } else if(credit_card && name === 'credit_card_expires') {
                	data.credit_card_expires = $('#popupForm [name="credit_card_expires"]').val()+"-01";
                } else if(!credit_card && name === 'credit_card_cvc') {
                	data.credit_card_cvc = 0;
                } else {
                    data[name] = element.val();
                }
            });
            data.action = "update";
            // 發送請求
            $.ajax({
                url: url,
                type : 'post',
                data : data,
                datatype: 'json',
                success : function(res) {
                	res = JSON.parse(res).data;
                	console.log(res)
                    if(res) {
                        // 更新資料
                        $('[data-mem="'+data.mem_id+'"] > td:nth-child(2)').text(data.first_name+data.last_name);
                        $('[data-mem="'+data.mem_id+'"] > td:nth-child(3)').text(data.nickname);
                        $('[data-mem="'+data.mem_id+'"] > td:nth-child(4)').text(data.mob);
                        $('[data-mem="'+data.mem_id+'"] > td:nth-child(5)').text(data.email);
                        $('[data-mem="'+data.mem_id+'"] > td:nth-child(6)').text(data.address);
                        $('[data-mem="'+data.mem_id+'"] > td:nth-child(8) option[value="'+data.authority+'"]').attr('selected', 'selected');
                        // 關閉彈窗
                        $('[data-dismiss="modal"]').click();
                    }
                    else {
                        alert("修改失敗");
                    }                   
                }
            });
        });
        $('.header-title [name="query"]').keydown(function(){
            if (event.keyCode == 13) {
            	data = {
           			query: $(this).val(),
           			action: 'search'
            	}
                $.ajax({
                    url: url,
                    type : 'post',
                    data : data,
                    datatype: 'json',
                    success : function(res) {
                    	window.location.reload();
                    }
                });
            };
        });
    });
    
    $(function(){
		$(".content").each(function(){
            var maxwidth=15;//設置最多顯示的字數
            var text=$(this).text();
            if($(this).text().length>maxwidth){
                $(this).text($(this).text().substring(0,maxwidth));
                $(this).html($(this).html()+"...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
            };
        });
    
		$(function () {
	        $("td").on("click",function() {
	            if (this.offsetWidth < this.scrollWidth) {
	                var that = this;
	                var text = $(this).text();
	                layer.alert(text);
	            }
	        });
	    })

    </script>
</body>
</html>
