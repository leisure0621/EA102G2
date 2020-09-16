<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="<%=request.getContextPath()%>/front_end/assets/js/jquery-1.11.3.min.js"></script>
<script src="<%=request.getContextPath()%>/front_end/assets/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/front_end/assets/js/own-menu.js"></script>
<script src="<%=request.getContextPath()%>/front_end/assets/js/jquery.lighter.js"></script>
<script src="<%=request.getContextPath()%>/front_end/assets/js/owl.carousel.min.js"></script>

<!-- SLIDER REVOLUTION 4.x SCRIPTS  -->
<script type="text/javascript" src="<%=request.getContextPath()%>/front_end/assets/rs-plugin/js/jquery.tp.t.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/front_end/assets/rs-plugin/js/jquery.tp.min.js"></script>
<script src="<%=request.getContextPath()%>/front_end/assets/js/main.js"></script>
<script src="<%=request.getContextPath()%>/front_end/assets/js/websocket.js"></script>
<script src="<%=request.getContextPath()%>/front_end/assets/js/logout.js"></script>
<script src="<%=request.getContextPath()%>/front_end/assets/js/public.js"></script>
<script src="<%=request.getContextPath()%>/front_end/assets/js/cart.js"></script>

<script>
  let headerMessage = $('.nav-right .navbar-right > li.user-msg');
  // 接收 websocket 信息
  webSocket.onmessage = function (event) {
    resObj = JSON.parse(event.data);
    
    // 賣家有訂單出貨通知給買家
    if (resObj.c2cseller) {
      resObj = resObj.c2cseller;
      if (resObj.buyer == '${memVO.mem_id}') {

        // 顯示有通知
        headerMessage.find('.dropdown-toggle').addClass('active');
        headerMessage.find('.dropdown-menu').show();
        // 添加新訊息
        headerMessage.find('.dropdown-menu').append(
          '<li class="msg">' + '<a>'
          + '<i class="icon-feed"></i>' + "親愛的 " + '${memVO.nickname}' + " 您本次購買的商品" + resObj.msg
          + '</a>' + '</li>');
      }
    }
    //買家有付款通知給賣家
    if (resObj.c2cbuyer) {
      resObj = resObj.c2cbuyer;
      if (resObj.toSeller == '${memVO.mem_id}') {

        // 顯示有通知
        headerMessage.find('.dropdown-toggle').addClass('active');
        headerMessage.find('.dropdown-menu').show();
        // 添加新訊息
        headerMessage.find('.dropdown-menu').append(
          '<li class="msg">' + '<a>'
          + '<i class="icon-feed"></i>' + resObj.msg
          + '</a>' + '</li>');
      }
    }

    //後台有處理檢舉通知給檢舉人
    if (resObj.c2creport) {
      resObj = resObj.c2creport;
      if (resObj.informant == '${memVO.mem_id}') {

        // 顯示有通知
        headerMessage.find('.dropdown-toggle').addClass('active');
        headerMessage.find('.dropdown-menu').show();
        // 添加新訊息
        headerMessage.find('.dropdown-menu').append(
          '<li class="msg">' + '<a>'
          + '<i class="icon-feed"></i>' + "親愛的 " + '${memVO.nickname}' + resObj.msg
          + '</a>' + '</li>');
      }
    }

    // 有人下標
    if (resObj.bid) {
      resObj = resObj.bid;
      // 顯示有通知
      headerMessage.find('.dropdown-toggle').addClass('active');
      headerMessage.find('.dropdown-menu').show();
      // 添加新訊息
      headerMessage.find('.dropdown-menu').append(
        '<li class="msg">' + '<a>' + '<i class="icon-feed"></i>'
        + resObj.msg + '</a>' + '</li>');
      // 收到下標信息 重整
      setTimeout(function(){
    	  window.location.reload();
   	  }, 5000);
    }
  }
  headerMessage.find('.dropdown-toggle').click(function () {
    // 顯示通知
    if ($(this).hasClass('active')) {
      $(this).removeClass('active');
      headerMessage.find('.dropdown-menu').show();
      // 移除消息
    } else {
      headerMessage.find('.dropdown-menu li').remove();
      headerMessage.find('.dropdown-menu').hide();
    }
  })

  // 購物車
  let cartList = ('${cartList}') ? (JSON.parse('${cartList}').length ? JSON.parse('${cartList}') : '') : '';
  let backet = $('.user-basket > .dropdown-menu');
  let imgSrc = '<%=request.getContextPath()%>/showPic.do?pro_id=';
  //加入購物車
  addtoUserBasket(cartList, backet, imgSrc);
</script>