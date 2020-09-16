
<div class="toolTip"></div>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--Begin core -->
<script src="<%=request.getContextPath()%>/back_end/assets/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/back_end/assets/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/back_end/assets/plugins/moment/moment.js"></script>
<script src="<%=request.getContextPath()%>/back_end/assets/js/jquery.slimscroll.js "></script>
<script src="<%=request.getContextPath()%>/back_end/assets/js/jquery.nicescroll.js"></script>
<script src="<%=request.getContextPath()%>/back_end/assets/js/functions.js"></script>
<script src="<%=request.getContextPath()%>/back_end/assets/js/moment-with-locales.js"></script>
<script src="<%=request.getContextPath()%>/back_end/assets/js/bootstrap-datetimepicker.js"></script>
<!-- End core -->
<script src="<%=request.getContextPath()%>/front_end/assets/js/websocket.js"></script>

<script>
	let notificationsLength = 0;
	let headerMessage = $('.header-section .notification-menu > li:nth-child(1)');
	headerMessage.find('.badge').text(notificationsLength);
	headerMessage.find('.title span').text(notificationsLength);
	
	// 接收 websocket
	webSocket.onmessage = function(event) {
		resObj = JSON.parse(event.data);

		// 有會員註冊
		if (resObj.register) {
			resObj = resObj.register;
			++notificationsLength;

			headerMessage.find('.badge').text(notificationsLength);
			headerMessage.find('.title span').text(notificationsLength);
			headerMessage.find('.notification-scroll-list').append(
					'<a class="list-group-item">' + '<div class="media">'
							+ '<div class="pull-left p-r-10">'
							+ '<em class="fa fa-user-plus noti-info"></em>'
							+ '</div>' + '<div class="media-body">'
							+ '<h5 class="media-heading">' + resObj.msg
							+ '</h5>' + '<p class="m-0">' + '<small>'
							+ resObj.email + '</small>' + '</p>' + '</div>'
							+ '</div>' + '</a>');

		}
		// 強制離職
	    if (resObj.resign) {
	      resObj = resObj.resign;
	      // 指定員工
	      if (resObj.emp_id == '${empVO.emp_id}') {
	    	  let style = 
	    		  'background: rgba(255 ,0 ,0 ,0.85);'+
				  'color: white;'+
				  'display: block;'+
				  'padding: 22px 70px;';
	    	  toolTip(resObj.msg, style);
	    	  // 登出
	    	  setTimeout(function(){$('.logout').click()}, 2000);
	      }
	    }
	}

	// header 提示
	headerMessage
			.click(function() {
				if ($(this).hasClass('open')) {
					notificationsLength = 0;
					$(this).find('.badge').text(notificationsLength);
					$(this).find('.title span').text(notificationsLength);
					$(this).find('.notification-scroll-list .list-group-item')
							.remove();
				}
			});

	// 提示方式2
	function toolTip(msg, style) {
		$('.toolTip').append('<div class="tips" style="'+style+'">' + msg + '</div>');
		$('.toolTip .tips').fadeIn(1000);
		$('.toolTip').click(function() {
// 			$(this).fadeOut(1000, function() {
// 				$('.toolTip .tips').remove();
// 			});
		});
	}
</script>