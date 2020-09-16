<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <meta name="description" content="" />
  <meta name="author" content="M_Adnan" />
  <title>維修單主檔新增</title>

  <%@ include file="/front_end/headerSection.jsp"%>
  <%@ page import="com.repair_main.model.*"%>
  <%
	RepairMainVO rmVO = (RepairMainVO) request.getAttribute("rmVO");
  %>

  <style>
    .addproNews {
      border: none;
      color: #fff;
      display: inline-block;
      padding: 5px 5px;
      text-transform: uppercase;
      font-weight: bold;
      font-size: 18px;
      border-radius: 0px;
      font-family: 'Montserrat', sans-serif;
      line-height: 24px;
      background: #2d3a4b;
      letter-spacing: 1px;
      position: relative;
      z-index: 1;
      margin-top: 16px;
    }

    table {
      margin-left: auto;
      margin-right: auto;
      width: 100%;
    }

    td:nth-child(1) {
      width: 20%;
    }

    .bootstrap-select.btn-group .dropdown-toggle .filter-option {
      color: #2d3a4b;
      font-size: 16px;
    }

    .shop-detail .quinty .btn {
      border: 1px solid #eaeaea;
    }

    #content {
      background: #fff;
      position: relative;
      z-index: 1;
      margin: 78px;
    }
  </style>

</head>

<body class="home">

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

    <div class="item-decribe margin-bottom-100">
      <div class="container">
        <div class="row">
          <div class="col-sm-12">
            <h4>申請維修</h4>
            <FORM METHOD="post" ACTION="repairMain.do" name="form1">
              <table>
                <tr>
                  <td>申請會員編號:<font color=red><b>*</b></font>
                  </td>
                  <td><%=memVO.getMem_id()%></td>
                </tr>
                <%-- 後台寫 --%>
                <td><input type="hidden" name="cat_id" size="30"
                    value="<%=(rmVO == null) ? "CAT0009" : rmVO.getCat_id()%>" />
                </td>
                </tr>

                <tr>
                  <td>維修品名稱:</td>
                  <td><input type="TEXT" id="rmPro_name" name="pro_name" size="30"
                      value="<%=(rmVO == null) ? "" : rmVO.getPro_name()%>" /></td>
                  <td style="color: red">${mErrorMsgs.pro_name}</td>
                </tr>
                <tr>
                  <td>問題描述:</td>
                  <td><textarea id="rmDes" name="description" rows="6"
                      placeholder="問題描述"><%=(rmVO == null) ? "" : rmVO.getDescription()%> </textarea></td>
                  <td style="color: red">${mErrorMsgs.description}</td>
                </tr>

                <tr>
                  <%-- 後台寫 --%>
                  <td><input type="hidden" name="status_id" size="20"
                      value="<%=(rmVO == null) ? "RST0001" : rmVO.getStatus_id()%>" /></td>
                </tr>
                <tr>
                  <%-- 後台寫 --%>
                  <td><input type="hidden" name="amount" size="20"
                      value="<%=(rmVO == null) ? "0.0" : rmVO.getAmount()%>" />
                  </td>
                </tr>


                <tr>
                  <td>收貨地址:</td>
                  <td><input type="TEXT" id="rmAdd" name="dev_address" size="40"
                      value="<%=(rmVO == null) ? "" : rmVO.getDev_address()%>" /></td>
                  <td style="color: red">${mErrorMsgs.dev_address}</td>
                </tr>


                <tr>
                  <td>收貨人:</td>
                  <td><input type="TEXT" id="rmRecipient" name="recipient" size="20"
                      value="<%=(rmVO == null) ? "" : rmVO.getRecipient()%>" /></td>
                  <td style="color: red">${mErrorMsgs.recipient}</td>
                </tr>


                <tr>
                  <!-- 					<td>付款方式:</td> -->
                  <td><input type="hidden" name="pay_via" size="20"
                      value="<%=(rmVO == null) ? "1" : rmVO.getPay_via()%>" />
                  </td>
                </tr>
                <tr>
                  <!-- 					<td>收貨方式:</td> -->
                  <td><input type="hidden" name="delivery" size="20"
                      value="<%=(rmVO == null) ? "2" : rmVO.getDelivery()%>" />
                  </td>
                </tr>

                <tr>
                  <td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal"
                      style="margin-top: 20px; margin-bottom: 20px;">送出新增</button></td>
                </tr>

                <tr>
                  <td>
                    <button type="button" id="mBtn" style="margin-top: 20px; margin-bottom: 20px;">神奇小按鈕</button>
                  </td>
                </tr>
              </table>

              <!-- Modal -->
              <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog" role="document">
                  <div class="modal-content">
                    <div class="modal-header">

                      <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">是否確定資料正確送出維修申請？</div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                      <input type="hidden" name="action" value="insert"> <input type="hidden" name="mem_id"
                        value="<%=memVO.getMem_id()%>">
                      <button type="submit" class="btn btn-primary">確定新增</button>
                    </div>
                  </div>
                </div>
              </div>

            </FORM>
          </div>
        </div>
      </div>
    </div>
    <%@ include file="/front_end/footerMenu.jsp"%>

  </div>
  <%@ include file="/front_end/footerSection.jsp"%>


</body>

<scipt src='http://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js'></scipt>

<script>
  $("#mBtn").click(function () {
    $("#rmPro_name").val("CPU");
    $("#rmDes").val("電腦開不了機，一點反應也沒有，就想送來檢查一下");
    $("#rmAdd").val("桃園市桃園區八張二路二段433巷250弄780號");
    $("#rmRecipient").val("纓京塚丈");
  });
</script>

</html>