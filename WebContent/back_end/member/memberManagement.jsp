<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.mem.model.*"%>
<%
    MemService memSvc = new MemService();
	String query = (String) session.getAttribute("memSearch");
	List<MemVO> list = null;
	if (query == null || query.trim().length() == 0) {
		list = memSvc.getAll();
	}
	else {
		list = memSvc.getQuery(query);
	}
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("memSearch", query);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<title>會員管理</title>

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
                <h4 class="page-title">會員管理</h4>
                <ol class="breadcrumb">
                    <li><a>會員管理</a></li>
                    <li><a>會員列表</a></li>
                </ol>
                <div class="clearfix"></div>
            </div>
            <!--End Page Title-->


            <!--Start row-->
            <div class="row">
                <div class="col-md-12">
                    <div class="white-box">
                        <h2 class="header-title">
	                        <div>會員列表</div>
	                        <div class="features">
	                            <input type="text" class="form-control" name="query" value="${memSearch}" placeholder="依建立時間、會員ID、姓名、mail搜尋"/>
	                        </div> 
                        </h2>
                        <div class="table-responsive">
                            <table id="memlist" class="display table">
                                <thead>
                                    <tr>
                                        <th>會員ID</th>
                                        <th>姓名</th>
                                        <th>暱稱</th>
                                        <th>手機</th>
                                        <th>Email</th>
                                        <th>地址</th>
                                        <th>修改</th>
                                        <th>權限</th>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <th>會員ID</th>
                                        <th>姓名</th>
                                        <th>暱稱</th>
                                        <th>手機</th>
                                        <th>Email</th>
                                        <th>地址</th>
                                        <th>修改</th>
                                        <th>權限</th>
                                    </tr>
                                </tfoot>
                                <tbody>
                                	<%@ include file="/back_end/page1.file" %> 
                                    <c:forEach var="memVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                                        <tr data-mem="${memVO.mem_id}">
                                            <td>${memVO.mem_id}</td>
                                            <td>${memVO.first_name}${memVO.last_name}</td>
                                            <td>${memVO.nickname}</td>
                                            <td>${memVO.mob}</td>
                                            <td>${memVO.email}</td>
                                            <td>${memVO.address}</td>
                                            <td>
                                                <div  
                                                	class="btn btn-info modify" data-toggle="modal" 
                                                    data-target="#popupForm"
                                                    data-mem_id="${memVO.mem_id}">修改
                                                </div>
                                            </td>
                                            <td>
                                                <select name="authority" data-mem_id="${memVO.mem_id}" class="form-control modifyAuthority">
                                                    <c:forEach var="i" begin="0" end="3" varStatus="loop">
                                                        <option value="${i}" ${(memVO.authority == i ) ? 'selected' : ''}>
                                                            <c:if test="${ i == 0 }">停權</c:if>
                                                            <c:if test="${ i == 1 }">會員</c:if>
                                                            <c:if test="${ i == 2 }">賣家</c:if>
                                                            <c:if test="${ i == 3 }">管理員</c:if>
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

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">修改會員資料</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                              <label class="col-sm-3 control-label">會員編號</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="mem_id"
                                  type="text"
                                  readonly
                                />
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-3 control-label"><span class="required">*</span>Email</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="email"
                                  placeholder="test@com.tw"
                                  type="email"
                                  required
                                />
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-3 control-label"><span class="required">*</span>姓</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="first_name"
                                  placeholder="輸入姓"
                                  type="text"
                                  required
                                />
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-3 control-label"><span class="required">*</span>名</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="last_name"
                                  placeholder="輸入名"
                                  type="text"
                                  required
                                />
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-3 control-label">暱稱</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="nickname"
                                  placeholder="輸入暱稱"
                                  type="text"
                                />
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-3 control-label">電話</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="tel"
                                  placeholder="輸入家裡電話"
                                  type="text"
                                />
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-3 control-label">手機</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="mob"
                                  placeholder="輸入手機號碼"
                                  type="text"
                                />
                              </div>
                            </div>
                            <div class="form-group hidden">
                              <label class="col-sm-3 control-label"><span class="required">*</span>密碼</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="password"
                                  placeholder="輸入密碼"
                                  type="text"
                                  required
                                />
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-3 control-label">銀行卡號</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="bank_account"
                                  placeholder="輸入銀行卡號"
                                  type="text"
                                />
                              </div>
                            </div>
                            
                            <div class="form-group">
                              <label class="col-sm-3 control-label">信用卡號</label>
                              <div class="col-sm-9">
                                <input
                                  class="form-control"
                                  name="credit_card"
                                  placeholder="輸入信用卡號"
                                  type="text"
                                />
                              </div>
                            </div>
                            
                            <div class="form-group">
                              <label class="col-sm-3 control-label">信用卡到期日</label>
                              <div class="col-sm-9">
                                <div class='input-group date credit_card_expires'>
                                    <input type='text' class="form-control" name="credit_card_expires"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                              </div>
                            </div>
                            
                            <div class="form-group">
                              <label class="col-sm-3 control-label">信用卡安全碼</label>
                              <div class="col-sm-9">
                                <input 
                                    class="form-control"
                                    type="number"
                                    placeholder="輸入信用卡安全碼"
                                    name="credit_card_cvc">
                              </div>
                            </div>
                            
                            <div class="form-group">
                              <label class="col-sm-3 control-label">賣場名稱</label>
                              <div class="col-sm-9">
                                <input 
                                    class="form-control"
                                    type="text"
                                    placeholder="輸入賣場名稱"
                                    name="shop_name">
                              </div>
                            </div>
                            
                            <div class="form-group">
                              <label class="col-sm-3 control-label">地址</label>
                              <div class="col-sm-9 address">
	                              <select class="country form-control" name="country"></select>
							      <select class="district form-control" name="district"></select>
							      <input class="zipcode form-control" name="zipcode" type="text" placeholder="郵遞區號" autocomplete="off" readonly />
							      <input class="detail form-control" name="address" type="text" placeholder="詳細地址" />
                              </div>
                            </div>
                            
                            <div class="form-group">
                              <label class="col-sm-3 control-label">權限</label>
                              <div class="col-sm-9">
                                <select name="authority" class="form-control">
                                    <c:forEach var="i" begin="0" end="3" varStatus="loop">
                                        <option value="${i}">
                                            <c:if test="${ i == 0 }">停權</c:if>
                                            <c:if test="${ i == 1 }">會員</c:if>
                                            <c:if test="${ i == 2 }">賣家</c:if>
                                            <c:if test="${ i == 3 }">管理員</c:if>
                                        </option>
                                    </c:forEach>
                                </select>
                              </div>
                            </div>
                            
                            <div class="form-group">
                              <label class="col-sm-3 control-label">建立時間</label>
                              <div class="col-sm-9">
                                <input type="text" class="form-control" name="est_time" readonly >
                              </div>
                            </div>
                            
                         </div>
                    </div>
                    <div class="modal-footer">
                        <div class="btn btn-danger" data-dismiss="modal">關閉</div>
                        <div class="btn btn-primary update">修改</div>
                    </div>
                </div>
            </div>
        </div>
        <!--END Popup Large Modal -->

	<%@ include file="/back_end/footerMenu.jsp" %>

    </div>
    <!--End main content -->

	<%@ include file="/back_end/footerSection.jsp" %>
    <script src="<%=request.getContextPath()%>/back_end/assets/js/dk-tw-citySelector.js"></script>
    
    <script>
    $(function () {
        let data = {};
        let url = '<%=request.getContextPath()%>/mem/mem.do';
        let add = {};
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
                	if(res) alert(res);
                },
            });
        });
        
        // 點擊修改按鈕 跳出彈窗
        $('.modify').click(function(){
        	data = {};
        	let name, element;
            data.mem_id = $(this).data("mem_id");
            data.action = 'getOne_For_Update';
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
                            else if(name === 'address') {
                            	// 初始化
                            	add = {};
                            	$('.address [name="address"], [name="address"]').val('');
                            	$('.address [name="country"]').data('selected', '');
                            	$('.address [name="district"]').data( 'selected', '');
                            	$('[name="country"] option').remove(); // 清空多餘城市
                            	$('[name="district"] option').remove();// 清空多餘區域
                            	$('.address').dk_tw_citySelector('.country', '.district', '.zipcode');
                            	if(res.address) {
                                	add.zip = res.address.slice(0, 3);
                                	add.blurryCountry = res.address.slice(3, 6);
                                	// 選城市
                                	add.country = $('[value^="'+add.blurryCountry+'"]').val();
                                	$('.address [name="country"]').data('selected', add.country);
                                	$('.address').dk_tw_citySelector('.country', '.district', '.zipcode');
                                	// 選區
                                	$('[name="country"] option').remove(); // 清空多餘城市
                                	add.district = $('[data-zip="'+add.zip+'"]').val();
                                	$('[name="district"] option').remove();// 清空多餘區域
                                	add.detail = res.address.slice(add.zip.length+add.district.length+add.country.length, res.address.length);
                                	$('.address [name="district"]').data( 'selected', add.district);
                                	$('.address').dk_tw_citySelector('.country', '.district', '.zipcode');
                                	// 填詳細地址
                                	$('.address [name="address"]').val(add.detail);
                            	}
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
        
        // 資料格式驗證 
        function dataVerification(action){
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
                } else if(name === 'country') {
                	data.address = $('#popupForm [name="country"]').val();
                } else if (name === 'district') {
                	data.address += $('#popupForm [name="district"]').val();
                } else if (name === 'zipcode') {
                	data.address = $('#popupForm [name="zipcode"]').val() + data.address;
                } else if (name === 'address') {
                	data.address += $('#popupForm [name="address"]').val();
                } else {
                    data[name] = element.val();
                }
            });
            data.action = action;
            return true;
        }
        
        // 修改資料
        $('.update').click(function(){
            if(dataVerification("update")) {
            	// 發送請求
                $.ajax({
                    url: url,
                    type : 'post',
                    data : data,
                    datatype: 'json',
                    success : function(res) {
                    	res = JSON.parse(res);
                    	
                        if(res.data) {
                        	alert(res.data);
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
                        	alert(res.err);
                        }                   
                    }
                });
            }
        });
        
        // 搜尋資料
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
    </script>
</body>
</html>
