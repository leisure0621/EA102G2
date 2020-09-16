<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="M_Adnan">
  <title>我的訂單</title>

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

    .shopping-cart .btn {
      font-size: 14px;
      padding: 5px 30px;
      height: 30px;
      line-height: inherit;
    }

    .shopping-cart th,
    .shopping-cart td {
      font-size: var(--font14px);
    }

    .cart-ship-info th:last-child, 
    .cart-ship-info td:last-child {
      width: 10%;
    }
    .statusChange {
      display: none;
    }
    
    .order-address {
        display: flex;
    }
    
    .order-address .country,
    .order-address .district,
    .order-address .zipcode{
        margin-right: 5px;
    }
    
    .order-address .district {
        width: 100px;
    }
    
    .order-address .zipcode {
        width: 100px;
    }
    
    .order-recipient input {
        width: 70%;
    }
    
    .bank,
    .order-bank .bank,
    .order-bank .recipient,
    .order-address .detail {
        width: 100%;
    }
    
    .order-payment select {
        width: calc(50% - 2px);
    }
    
    .bank,
    .order-payment select,
    .order-bank input,
    .order-address .detail,
    .order-address .country,
    .order-address .district,
    .order-address .zipcode,
    .order-recipient input {
        border: 1px solid var(--contentTitleColor);
	    height: 44px;
	    font-size: 11px;
	    padding: 0 10px;
	    letter-spacing: 1px;
	    display: inline-block;
	    font-weight: normal;
	    text-align: left;
	    -webkit-transition: all 0.4s ease-in-out;
	    -o-transition: all 0.4s ease-in-out;
	    transition: all 0.4s ease-in-out;
    }
    
    .bank,
    .order-address,
    .order-payment select,
    .order-bank,
    .order-recipient {
        margin-top: 15px;
    }
    
    .modal-footer {
	    display: flex;
	    flex-direction: row-reverse;
	}
	
	.statusChange {
	    margin-left: 5px;
	}
  </style>

</head>

<body>

  <%@ include file="/front_end/headerMenu.jsp" %>
  <%@ page import="com.text_main.model.*,java.util.stream.Collectors"%>
  <%@ page import="com.b2cso_main.model.*, com.b2cso_status.model.*"%>
  <%
  // 訂單主檔
  B2cso_mainService bSvc = new B2cso_mainService();
  List<B2cso_mainVO> list = 
      bSvc.getAll().stream()
      .filter(item -> item.getBuyer_id().equals(memVO.getMem_id()))
      .collect(Collectors.toList());
  request.setAttribute("list", list);
  // 訂單狀態
  B2cso_statusService bsSvc = new B2cso_statusService();
  List<B2cso_statusVO> bsList = bsSvc.getAll();
  request.setAttribute("bsList", bsList);
%>

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
            <div class="cart-ship-info">
              <h6>我的訂單</h6>
              <ul class="row">

                <!-- First Name -->
                <div class="col-md-12">
                  <table class="table">
                    <thead>
                      <tr>
                        <th>訂單編號</th>
                        <th>訂單分類</th>
                        <th>訂單金額</th>
                        <th>訂單狀態</th>
                        <th>訂單詳情</th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
                      <%@ include file="/front_end/page1.file"%>
                      <c:forEach var="b2cso_main" items="${list}" begin="<%=pageIndex%>"
                        end="<%=pageIndex+rowsPerPage-1%>">
                        <tr so_id="${b2cso_main.so_id}">
                          <td class="bsoId">${b2cso_main.so_id}</td>
                          <td>
                            <c:forEach var="i" begin="1" end="3" varStatus="loop">
                              <c:if test="${ i == b2cso_main.type }">
                                <c:if test="${ i == 1 }">B2C訂單</c:if>
                                <c:if test="${ i == 2 }">組裝訂單</c:if>
                                <c:if test="${ i == 3 }">競標訂單</c:if>
                              </c:if>
                            </c:forEach>
                          </td>
                          <td>${b2cso_main.amount}</td>
                          <c:forEach var="bsStatus" items="${bsList}">
                            <c:if test="${ bsStatus.status_id eq b2cso_main.status }">
                              <td>
                                ${bsStatus.status_des}
                              </td>
                            </c:if>
                          </c:forEach>
                          <td>
                            <a class="readDetail" data-so_id="${b2cso_main.so_id}" 
                               data-toggle="modal" 
                               data-target="#orderDetailModal">查看詳情</a>
                          </td>
                          
                          <td>
                            <c:if test="${b2cso_main.status == 'BST0001'}">
                              <a class="modify" 
                                 data-so_id="${b2cso_main.so_id}"
                                 data-toggle="modal" 
                                 data-target="#orderDetailModal">結帳去</a>
                          	</c:if>
                          </td>
                          
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                  <%@ include file="/front_end/page2.file"%>

                </div>
              </ul>
            </div>
          </div>

        </div>
      </section>

    </div>

    <%@ include file="/front_end/footerMenu.jsp" %>
  </div>
  
  <!-- Modal Start -->
  <div class="modal fade" id="orderDetailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel"></h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <ul class="order-bank">
            <!-- 收貨人姓名 -->
            <input type="text" class="recipient" name="recipient" value="" placeholder="請輸入收貨人姓名...">
          </ul>
          <ul class="order-address">
            <!-- 縣市 -->
            <select class="country" name="country"></select>
            <!-- 區域 -->
            <select class="district" name="district"></select>
            <!-- 郵區 -->
            <input type="text" class="zipcode" name="zipcode" value="" placeholder="郵遞區號" readonly>
            <!-- 地址 -->
            <input type="text" class="detail" name="address" value="" placeholder="請輸入詳細地址...">
          </ul>
          <ul class="order-payment">
            <!-- 取貨方式 -->
            <select class="delivery" name="delivery">
              <option value="0">請選擇取貨方式</option>
              <option value="1">宅配到府</option>
              <option value="2">便利商店取貨</option>
              <option value="3">現場取貨</option>
            </select>
            <!-- 付款方式 -->
            <select class="pay_via" name="pay_via">
              <option value="0">請選擇付款方式</option>
              <option value="1">信用卡付款</option>
              <option value="2">現金付款</option>
           	</select>
           	<!-- 銀行卡號 -->
            <input type="text" class="bank" name="bank" value="" placeholder="請輸入信用卡號...">
          </ul>
        </div>
        <div class="modal-footer">
          <div class="btn btn-primary statusChange">確認結帳</div>
          <div class="btn btn-secondary" data-dismiss="modal">關閉</div>
        </div>
      </div>
    </div>
  </div>
  <!-- Modal End -->
  
  <%@ include file="/front_end/footerSection.jsp" %>
  <!-- 地址三級 -->
  <script src="<%=request.getContextPath()%>/front_end/assets/js/dk-tw-citySelector.js"></script>

  <script>
    $(document).ready(function () {
      let data = {};
      let order = {};
      
      // 開啓結帳窗口
      $('.modify').click(function(){
          data.so_id = $(this).data('so_id');
          data.action = "getDetail";
          $('.products').remove();
          $('.modal-body').find('.order-address').show();
          $('.modal-body').find('.order-payment').show();
          $('.modal-body').find('.order-bank').show();
          $('.modal-body').find('.bank').hide();
          $('.statusChange').show();
          // 初始化地址
          $('#orderDetailModal').dk_tw_citySelector('.country', '.district', '.zipcode');
          // 訂單標題
          $('#orderDetailModal').find('.modal-title').text("結帳 "+data.so_id);
          $.ajax({
              type: "POST",
              url: "<%=request.getContextPath()%>/b2cso/b2cso.do",
              data: data,
              datatype: 'json',
              success: function (res) {
                res = JSON.parse(res);
                if(res.data) {
                  res = res.data;
                  order = res.order;
                  // 訂單詳情
                  for(let i = 0;i<res.order_detail.length;i++) {
                    $('#orderDetailModal').find('.modal-body').prepend(
                       '<ul class="products">'+
                       '<li><span>商品名稱:</span><span>'+res.order_list[i].pro_name+'</span></li>'+
                       '<li><span>商品數量:</span><span>'+res.order_detail[i].quantity+'</span></li>'+
                       '<li><span>商品價格:</span><span>'+res.order_detail[i].price+'</span></li>'+
                       '</ul>'
                     );
                  }
                } else {
                  alert(res.err);
                }
              }
          });
      });
      
      // 信用卡付款
      $('.pay_via').on('change', function(){
    	data.pay_via = $(this).val();
    	if(data.pay_via === '0' || data.pay_via === '2') {
    		$('.modal-body').find('.bank').hide();
        } else if (data.pay_via === '1') {
        	$('.modal-body').find('.bank').show();
        }
      });
      
      // 修改訂單
      $('.statusChange').click(function () {
        data = {};
        data.so_id = order.so_id;
        data.recipient = $('.recipient').val();
        if(!data.recipient) {
        	alert('請輸入收貨人姓名');
        	return;
        }
        data.country = $('.country').val();
        if(!data.country) {
        	alert('請選擇城市');
        	return;
        }
        data.district = $('.district').val();
        data.zipcode = $('.zipcode').val();
        if(!data.district) {
        	alert('請選擇區域');
        	return;
        }
        data.detail = $('.detail').val();
        if(!data.detail) {
        	alert('請輸入詳細地址');
        	return;
        }
        data.delivery = $('.delivery').val();
        if(data.delivery === '0') {
        	alert('請選擇收貨方式');
        	return;
        }
        data.del_address = data.zipcode + data.country + data.district + data.detail;
        data.action = "updateOrder";
        data.pay_via = $('.modal-body').find('.pay_via').val();
        if(data.pay_via === '0') {
        	alert('請選擇付款方式');
        	return;
       	// 信用卡
        } else if(data.pay_via === '1'){
        	data.status = "BST0001"; // 處理中
        // 現金
        } else if(data.pay_via === '2'){
        	data.status = "BST0001"; // 處理中
        }
        data.order_bank = $('.modal-body').find('.bank').val();
        if(data.pay_via === '1' && !data.order_bank) {
        	alert('請輸入信用卡');
        	return;
        }
        upload();
      });
      
      // 更新
      function upload(){
    	  $.ajax({
              type: "POST",
              url: "<%=request.getContextPath()%>/b2cso/b2cso.do",
              data: data,
              datatype: 'json',
              success: function (res) {
                res = JSON.parse(res);
                if (res.data) {
                  res = res.data;
                  alert("付款成功");
                  location.reload();
                } else {
                  res = res.err;
                  alert("付款失敗");
                }
              }
          });
      }
      
      // 查看訂單資料
      $('.readDetail').click(function(){
        data.so_id = $(this).data('so_id');
        data.action = "getDetail";
        $('.products').remove();
        $('.modal-body').find('.order-address').hide();
        $('.modal-body').find('.order-payment').hide();
        $('.modal-body').find('.order-bank').hide();
        $('.modal-body').find('.bank').hide();
        $('.statusChange').hide();
        // 訂單標題
        $('#orderDetailModal').find('.modal-title').text("訂單編號 "+data.so_id);
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/b2cso/b2cso.do",
            data: data,
            datatype: 'json',
            success: function (res) {
              res = JSON.parse(res);
              if(res.data) {
                res = res.data;
                  // 訂單詳情
                  for(let i = 0;i<res.order_detail.length;i++) {
                    $('#orderDetailModal').find('.modal-body').append(
                       '<ul class="products">'+
                       '<li><span>商品名稱:</span><span>'+res.order_list[i].pro_name+'</span></li>'+
                       '<li><span>商品數量:</span><span>'+res.order_detail[i].quantity+'</span></li>'+
                       '<li><span>商品價格:</span><span>'+res.order_detail[i].price+'</span></li>'+
                       '</ul>'
                     );
                  }
              } else {
                alert(res.err);
              }
            }
          });
      });
    });
  </script>

</body>

</html>