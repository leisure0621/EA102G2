<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <meta name="description" content="" />
  <meta name="author" content="M_Adnan" />
  <title>賣家訂單</title>

  <%@ include file="/front_end/headerSection.jsp"%>
  <%@ page import="com.c2cso_main.model.*"%>
  <%@ page import="com.c2cso_status.model.*"%>
  <%@ page import="com.c2cpro_main.model.*"%>
  <jsp:useBean id="c2cproMainSvc" scope="page" class="com.c2cpro_main.model.C2cproMainService" />
  <%
C2csoMainService main = new C2csoMainService();
List<C2csoMainVO> solist = main.getAll();
List<C2cproMainVO> prolist=c2cproMainSvc.getAll();

List<C2csoMainVO> list= new ArrayList<C2csoMainVO>();

for(C2csoMainVO so:solist){
	for(C2cproMainVO pro :prolist){
		if(so.getPro_id().equals(pro.getPro_id())){
			if(pro.getMem_id().equals(memVO.getMem_id())){
				list.add(so);
			}
			
		}
	}
}
pageContext.setAttribute("list", list);
%>

  <jsp:useBean id="c2csoStatusSvc" scope="page" class="com.c2cso_status.model.C2csoStatusService" />
  <style>
    .list-group {
      padding-left: 0px;
    }

    .btn {
      border: none;
      color: #fff;
      display: inline-block;
      padding: 0px 7px;
      text-transform: uppercase;
      font-weight: bold;
      font-size: 16px;
      border-radius: 0px;
      line-height: 40px;
      background: #415b7d;
      letter-spacing: 1px;
      position: relative;
      z-index: 1;
    }

    .btn:focus {
      color: #fffafa;
      outline: none;
      outline: none;
      outline-offset: 0px;
    }
  </style>

</head>

<body>
  <!-- LOADER -->
  <div id="loader">
    <div class="position-center-center">
      <div class="ldr"></div>
    </div>
  </div>

  <!-- Wrap -->
  <div id="wrap">
    <!-- header -->
    <%@ include file="/front_end/headerMenu.jsp"%>
    <!-- Content -->
    <div id="content"></div>
    <!-- Popular Products -->
    <section class="member-center_information margin-top-23 margin-bottom-23 padding-bottom-23 padding-top-23">
      <div class="container">
        <div class="col-sm-3 leftSlide">
          <%@ include file="/front_end/membercenterLeftMenu.jsp" %>
        </div>
        <!-- Item Content -->
        <div class="col-sm-9">
          <h5 class="cart-ship-info" style="margin-top:50px; 
									margin-bottom:30px;">二手賣場訂單</h5>

          <!--======= PRODUCT DESCRIPTION =========-->
          <div class="papular-block row">
            <table class="table">
              <thead>
                <tr>
                  <th>訂單編號</th>
                  <th>商品編號</th>
                  <th>訂單狀態編號</th>
                  <th>訂單詳情</th>
                  <th>狀態</th>
                </tr>
              </thead>
               <%@ include file="/front_end/page1.file"%>
              <c:forEach var="c2csoMainVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                <tr id="tr${c2csoMainVO.so_id}">
                  <td>${c2csoMainVO.so_id}</td>
                  <td>${c2csoMainVO.pro_id}</td>
                  <td>${c2csoStatusSvc.getOneC2csoStatus(c2csoMainVO.status).status_des}</td>
                  <td>
                    <div  data-toggle="modal" data-target="#${c2csoMainVO.so_id}">
                      出貨資訊
                    </div>
                  </td>
                  <td>
                    <!-- 												<FORM METHOD="post" ACTION="c2csoMain.do" > -->
                    <!-- 												<input type="hidden" name="action" value="so_Shipment">	 -->
                    <%-- 												<input type="hidden" name="so_id" value="${c2csoMainVO.so_id}">												 --%>
                    <div  class="shipment" data-so_id="${c2csoMainVO.so_id}">
                      出貨
                    </div>
                    <!-- 												</FORM> -->
                  </td>
                </tr>
              </c:forEach>
            </table>
             <%@ include file="/front_end/page2.file"%>
          </div>
        </div>
      </div>
    </section>
  </div>



  <!------------------------------------ 訂單資訊 ------------------------------------->
  <c:forEach var="c2csoMainVO" items="${list}">
    <div class="modal fade" id="${c2csoMainVO.so_id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
      aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">出貨資訊</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">

            <jsp:useBean id="memBuyerSvc" scope="page" class="com.mem.model.MemService" />

            <ul class="list-group list-group-flush">



              <li class="list-group-item">建立時間:
                <fmt:formatDate value="${c2csoMainVO.est_time}" pattern="yyyy/MM/dd HH:mm" />
              </li>
              <li class="list-group-item">訂單編號: ${c2csoMainVO.so_id} </li>
              <li class="list-group-item">商品編號: ${c2csoMainVO.pro_id} </li>
              <li class="list-group-item">商品名稱: ${c2cproMainSvc.getOneC2cproMain(c2csoMainVO.pro_id).pro_name} </li>
              <li class="list-group-item" id="buyer" value="${c2csoMainVO.buyer_id}">買家編號: ${c2csoMainVO.buyer_id} </li>
              <li class="list-group-item">買家姓名: ${memBuyerSvc.getOneMem(c2csoMainVO.buyer_id).first_name}
                ${memBuyerSvc.getOneMem(c2csoMainVO.buyer_id).last_name} </li>
              <li class="list-group-item">買家信箱: ${memBuyerSvc.getOneMem(c2csoMainVO.buyer_id).email} </li>
              <li class="list-group-item">買家電話: ${memBuyerSvc.getOneMem(c2csoMainVO.buyer_id).tel} </li>
              <li class="list-group-item">買家地址: ${memBuyerSvc.getOneMem(c2csoMainVO.buyer_id).address} </li>
              <li class="list-group-item">訂單狀態: ${c2csoStatusSvc.getOneC2csoStatus(c2csoMainVO.status).status_des} </li>
              <li class="list-group-item">購買數量: ${c2csoMainVO.quantity} 個</li>
              <li class="list-group-item">總金額:
                <fmt:formatNumber type="number"
                  value="${c2cproMainSvc.getOneC2cproMain(c2csoMainVO.pro_id).price*(c2csoMainVO.quantity)}"
                  maxFractionDigits="0" /> 元</li>



            </ul>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>

          </div>
        </div>
      </div>
    </div>
  </c:forEach>
  <%@ include file="/front_end/footerMenu.jsp"%>
  <%@ include file="/front_end/footerSection.jsp"%>
  <script>
    $(function () {
      let data = {};

      $(".case_description").each(function () {
        var maxwidth = 4;//設置最多顯示的字數
        var text = $(this).text();
        if ($(this).text().length > maxwidth) {
          $(this).text($(this).text().substring(0, maxwidth));
          $(this).html($(this).html() + "...");//如果字數超過最大字數，超出部分用...代替，並且在後面加上點擊展開的鏈接；
        }
        ;
      });

      $('.shipment').click(function () {
        data.action = "so_Shipment";
        data.so_id = $(this).data("so_id");
        data.buyer_id = $('.fade#' + data.so_id + ' [id="buyer"]').attr('value');
        console.log(data);
        $.ajax({
          url: '<%=request.getContextPath()%>/front_end/c2cproMain/c2csoMain.do',
          type: 'post',
          data: data,
          datatype: 'json',
          success: function (res) {
            res = JSON.parse(res).data;
            $('#tr' + data.so_id + ' > td:nth-child(3)').text(res);
            news = {};
            news.msg = res;
            news.buyer_id = data.buyer_id;
            c2cseller(news);
          }
        });

      });
      //websocket
      function c2cseller(news) {
        socket.data.c2cseller = {};
        socket.data.c2cseller.msg = news.msg;
        socket.data.c2cseller.buyer = news.buyer_id;
        sendMessage(socket.data);
        console.log(socket.data);
      }
    });


  </script>


</body>

</html>