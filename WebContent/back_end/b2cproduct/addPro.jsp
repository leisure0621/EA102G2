<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back_end/pageTags.jsp"%>
<%@ page import="java.util.*"%>
<%@ page
	import="com.catagory.model.*,com.b2cpro_main.model.*,com.pro_spec.model.*,com.vendor.model.*"%>
<%
	B2cproMainVO proVO = (B2cproMainVO) request.getAttribute("proVO");
	if (proVO == null) {
		proVO = new B2cproMainVO();
	}
%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <title>Add New Product</title>
    <%@ include file="/back_end/headerSection.jsp"%>
    <style>
        #onshelf {
            margin-left: 5px;
        }

        thead tr td:nth-child(1) {
            width: 150px;
        }
        


        #imgDisplay {
            width: 160px;
            margin-top: 15px;
            margin : {
            	top : 15px;
            }
        }
    </style>
</head>

<body class="sticky-header">
    <script type="text/javascript" src="<%=request.getContextPath()%>/back_end/assets/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            let data = {};
            $('#catagory').change(function () {
                data.cat_id = $(this).val();
                data.action = "getSelect";
                console.log(data);
                $.ajax({
                    type: "GET",
                    url: "<%=request.getContextPath()%>/back_end/b2cproduct/B2cproAjax.do",
					data : data,
                    datatype: 'json',
                    success: function (
                        data) {
                        clearSelect();
                        data = JSON.parse(data);
                        $.each(
							data,
							function (i, item) {
							    $('#specs').append(
								'<tr><td>【' + 
								data[i].spec_id + 
								'】：' + 
								data[i].spec_des + 
								'　<input type="text" name="' + 
								data[i].spec_id + '"></td></tr>');
                       	 })
                   	 }
                })
            })
        });

        // 	<input list="cars" />
        // 		<datalist id="cars">
        // 			<option value="BMW">
        // 			<option value="Ford">
        // 			<option value="Volvo">
        // 		</datalist>

        function clearSelect() {
            $('#specs').empty();
        }
    </script>

    <%@ include file="/back_end/leftSideMenu.jsp"%>
    <div class="main-content">
        <%@ include file="/back_end/headerMenu.jsp"%>
        <div class="wrapper">
            <div class="page-title-box">
                <h4 class="page-title">商品管理</h4>
                <ol class="breadcrumb">
                    <li><a href="<%=request.getContextPath()%>/back_end/b2cproduct/listAllPro.jsp">所有產品列表</a></li>
                    <li><a href="<%=request.getContextPath()%>/back_end/b2cproduct/addPro.jsp">新增商品</a></li>
                    <li><a href="<%=request.getContextPath()%>/back_end/b2cproduct/searchProByCat.jsp">以分類查詢商品</a></li>
                </ol>
                <div class="clearfix"></div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="white-box">
                        <h2 class="header-title">
                            <div>新增商品</div>
                        </h2>
                        <div class="table-responsive">
                            <!--                         	錯誤表列 -->
                            <c:if test="${not empty errorMsgs}">
                                <font style="color: red">請修正以下錯誤:</font>
                                <ul>
                                    <c:forEach var="message" items="${errorMsgs}">
                                        <li style="color: red">${message}</li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            <FORM METHOD="post" ACTION="b2cpro.do" name="form1" enctype="multipart/form-data">
                                <div class="table-wrap">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <td><b>品名:</b></td>
                                                <td><input type="TEXT" name="pro_name" size="45" placeholder="請輸入商品名稱"
                                                        value="<%=(proVO.getPro_name() == null) ? "" : proVO.getPro_name()%>" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><b>產品類別：</b></td>
                                                <td>
                                                    <jsp:useBean id="catSvc" scope="page"
                                                        class="com.catagory.model.CatagoryService" /> <select size="1"
                                                        name="cat_id" id="catagory">
                                                        <option value="0">請選擇</option>
                                                        <c:forEach var="catVOs" items="${catSvc.getAll()}">
                                                            <option value="${catVOs.cat_id}"
                                                                ${catVOs.cat_id==proVO.cat_id ? 'selected' : '' }>
                                                                ${catVOs.cat_id}【${catVOs.cat_des}】</option>
                                                        </c:forEach>
                                                    </select></td>
                                            </tr>

                                            <tr>
                                                <td><b>產品圖片：</b></td>
                                                <td>
                                                
                                                	<input type="file" name="picture" accept="image/*"
                                                        onchange="loadFile(event)">  <img id="imgDisplay" />
                                                	<img id="imgDisplay" />
                                                        
                                                </td>
                                                        
                                            </tr>
                                        </thead>
                                    </table>
                                  


                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <td><b>建議售價：</b></td>
                                                <td><input type="text" name="rrp" placeholder="請輸入價格"
                                                        value="<%=(proVO.getRrp() == null) ? "" : proVO.getRrp()%>">
                                                </td>
                                            </tr>
                                             <tr>
                                                <td><b>現有庫存：</b></td>
                                                <td><input type="number" name="stock" placeholder="請設定庫存"
                                                        value="<%=(proVO.getStock() == null) ? 0 : proVO.getStock()%>">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><b>製造商：</b></td>
                                                <td>
                                                    <jsp:useBean id="vSvc" scope="page"
                                                        class="com.vendor.model.VendorService" /> <select size="1"
                                                        name="vendor_id">
                                                        <option value="">請選擇</option>
                                                        <c:forEach var="vendors" items="${vSvc.getAll()}">
                                                            <option value="${vendors.vendor_id}"
                                                                ${vendors.vendor_id==proVO.vendor_id ? 'selected' : ''
                                                                }>
                                                                ${vendors.vendor_id}【${vendors.vendor_name}】</option>
                                                        </c:forEach>
                                                    </select></td>
                                            </tr>
                                            <tr>
                                                <td><b>上架狀態：</b></td>



                                                <td><input type="radio" id="offshelf" name="status" value="0"
                                                        checked><label for="offshelf">下架</label>
                                                    <input type="radio" id="onshelf" name="status" value="1"><label
                                                        for="onshelf">上架</label></td>
                                            </tr>
                                            <tr>
                                                <td><b>商品描述：</b></td>
                                                <td><textarea name="pro_des" rows="10" cols="50"
                                                        placeholder="請輸入商品描述">${proVO.pro_des}</textarea></td>
                                            </tr>
                                        </thead>
                                    </table>

                                    <table id='specs' class="table">

                                    </table>
                                </div>
                                <input type="hidden" name="action" value="insert">
                                
                                <input class="btn btn-info" type="submit" value="送出新增">
								<input class="btn btn-info" id="magic" type="button" value="廠商大大您好我叫周奕安">
                            </FORM>

                            <script>
                                var loadFile = function (event) {
                                    var output = document
                                        .getElementById('imgDisplay');
                                    output.src = URL
                                        .createObjectURL(event.target.files[0]);
                                    output.onload = function () {
                                        URL.revokeObjectURL(output.src) // free memory
                                    }
                                };
                                $('#magic').click(function(){
                                	$('[name="pro_name"]').val('初音未來');
                                	$('[name="rrp"]').val('10');
                                	$('[name="stock"]').val('10');
                                	$('[name="pro_des"]').val('就只是個軟體，沒什麼好說的');
                                });
                            </script>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/back_end/footerMenu.jsp"%>

    </div>
    <%@ include file="/back_end/footerSection.jsp"%>
</body>

</html>