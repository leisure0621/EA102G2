<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back_end/pageTags.jsp" %>
<%@ page import="com.dept.model.*, com.emp_auth_detail.model.*"%>
<%
    String query = (String) session.getAttribute("empSearch");
    // 員工
    List<EmpVO> list = null;
    if (query == null || query.trim().length() == 0) {
        list = empSvc.getQuery("");
    }
    else {
        list = empSvc.getQuery(query);
    }
    pageContext.setAttribute("empSearch",query);
    pageContext.setAttribute("list",list);
    // 部門
    DeptService deptSvc = new DeptService();
    List<DeptVO> deptList = deptSvc.getAll();
    pageContext.setAttribute("deptList",deptList);
    
    // 權限
    EmpAuthDetailService empAuthDetailSvc = new EmpAuthDetailService();
    List<EmpAuthDetailVO> dmpAuthDetailVOList = empAuthDetailSvc.getAll();
    pageContext.setAttribute("dmpAuthDetailVOList",dmpAuthDetailVOList);
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>員工管理</title>

    <link
      rel="stylesheet"
      type="text/css"
      href="<%=request.getContextPath()%>/back_end/assets/plugins/jquery-multi-select/css/multi-select.css"
    />
    <%@ include file="/back_end/headerSection.jsp" %>

    <style>
      .cover:after {
        content: '';
        width: 100%;
        height: 100%;
        position: absolute;
        z-index: 0;
        top: 0;
        left: 0;
      }
      .btn.btn-magic {
  		  background-color: black;
  		  color: white;
}
    </style>
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
          <h4 class="page-title">員工管理</h4>
          <ol class="breadcrumb">
            <li><a>員工管理</a></li>
            <li><a>員工列表</a></li>
          </ol>
          <div class="clearfix"></div>
        </div>
        <!--End Page Title-->

        <!--Start row-->
        <div class="row">
          <div class="col-md-12">
            <div class="white-box">
              <h2 class="header-title">
                <div>員工列表</div>
                <div class="features">
                  <div
                    class="btn btn-info add"
                    data-toggle="modal"
                    data-target="#popupForm"
                    data-emp_id="${empVO.emp_id}"
                  >新增
                  </div>
                  <input
                    type="text"
                    class="form-control"
                    name="query"
                    value="${empSearch}"
                    placeholder="依僱用時間、員工ID、姓、名搜尋"
                  />
                </div>
              </h2>
              <div class="table-responsive">
                <table id="emplist" class="display table">
                  <thead>
                    <tr>
                      <th>員工ID</th>
                      <th>員工姓名</th>
                      <th>員工部門</th>
                      <th>員工薪水</th>
                      <th>僱用日期</th>
                      <th>修改</th>
                      <th>在職</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th>員工ID</th>
                      <th>員工姓名</th>
                      <th>員工部門</th>
                      <th>員工薪水</th>
                      <th>僱用日期</th>
                      <th>修改</th>
                      <th>在職</th>
                    </tr>
                  </tfoot>
                  <tbody>
                    <%@ include file="/back_end/page1.file" %>
                    <c:forEach
                      var="empVO"
                      items="${list}"
                      begin="<%=pageIndex%>"
                      end="<%=pageIndex+rowsPerPage-1%>"
                    >
                      <tr data-emp="${empVO.emp_id}">
                        <td>${empVO.emp_id}</td>
                        <td>${empVO.emp_firstname}${empVO.emp_lastname}</td>
                        <td>
                          <c:forEach var="deptVO" items="${deptList}">
                            <c:if test="${ empVO.dept_no == deptVO.dept_id }"
                              >${deptVO.dept_des}</c:if
                            >
                          </c:forEach>
                        </td>
                        <td>${empVO.salary}</td>
                        <td>${empVO.hiredate}</td>
                        <td>
                          <div
                            class="btn btn-info modify"
                            data-toggle="modal"
                            data-target="#popupForm"
                            data-emp_id="${empVO.emp_id}"
                          >修改
                          </div>
                        </td>
                        <td>
                          <div
                            class="status ${(empVO.status == 1 ) ? 'work' : ''}"
                            data-emp_id="${empVO.emp_id}"
                          >
                            <div></div>
                          </div>
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
              <button type="button" class="close" data-dismiss="modal">
                &times;
              </button>
              <h4 class="modal-title">修改員工資料</h4>
            </div>
            <div class="modal-body">
              <div class="form-horizontal">
                <div class="form-group">
                  <label class="col-sm-3 control-label">員工編號</label>
                  <div class="col-sm-9">
                    <input
                      class="form-control"
                      name="emp_id"
                      type="text"
                      readonly
                    />
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-sm-3 control-label">員工姓氏</label>
                  <div class="col-md-9">
                    <input
                      class="form-control"
                      name="emp_firstname"
                      id="lastName"
                      type="text"
                    />
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-sm-3 control-label">員工名稱</label>
                  <div class="col-md-9">
                    <input
                      class="form-control"
                      name="emp_lastname"
                      type="text"
                    />
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-sm-3 control-label">員工部門</label>
                  <div class="col-md-9">
                    <select name="dept_no" class="form-control">
                      <c:forEach var="deptVO" items="${deptList}">
                        <option value="${deptVO.dept_id}">
                          ${deptVO.dept_des}
                        </option>
                      </c:forEach>
                    </select>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-sm-3 control-label">員工薪水</label>
                  <div class="col-sm-9">
                    <input class="form-control" name="salary" type="text" />
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-sm-3 control-label">僱用日期</label>
                  <div class="col-sm-9">
                    <div class="input-group date hiredate">
                      <input type="text" class="form-control" name="hiredate" />
                      <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                      </span>
                    </div>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-sm-3 control-label">權限</label>
                  <div class="col-md-9">
                    <select
                      multiple="multiple"
                      class="multi-select"
                      id="my_multi_select1"
                      name="my_multi_select1[]"
                    >
                      <c:forEach
                        var="dmpAuthDetailVO"
                        items="${dmpAuthDetailVOList}"
                      >
                        <option value="${dmpAuthDetailVO.auth_id}">
                          ${dmpAuthDetailVO.auth_des}
                        </option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <div class="btn btn-magic">神奇小按鈕</div>
              <div class="btn btn-danger" data-dismiss="modal">關閉</div>
              <div class="btn btn-primary update">修改</div>
              <div class="btn btn-primary insert">新增</div>
            </div>
          </div>
        </div>
      </div>
      <!--END Popup Large Modal -->

      <%@ include file="/back_end/footerMenu.jsp" %>
    </div>
    <!--End main content -->

    <%@ include file="/back_end/footerSection.jsp" %>
    <script
      type="text/javascript"
      src="<%=request.getContextPath()%>/back_end/assets/plugins/jquery-multi-select/js/jquery.multi-select.js"
    ></script>
    <script src="<%=request.getContextPath()%>/back_end/assets/pages/multi-select-init.js"></script>

    <script>
      $(function () {
        let data = {};
        let yymmddReg = '^([0-9]{4})[.-]{1}([0-9]{1,2})[.-]{1}([0-9]{1,2})$';
        let numReg = '[0-9]';
        let url = '<%=request.getContextPath()%>/emp/emp.do';
        let ifReadonly =
          '[name="emp_firstname"],[name="emp_lastname"],[name="salary"],[name="hiredate"]';
        let cleanData =
          '[name="emp_id"],[name="emp_firstname"],[name="emp_lastname"],[name="salary"],[name="hiredate"]';
        let currentPage = '<%=request.getRequestURI()%>';

        // 點擊 修改在/離職 樣式
        $('.status').click(function () {
       	  // 在職
          if ($(this).hasClass('work')) {
            $(this).removeClass('work');
          // 離職 
          } else {
            $(this).addClass('work');
          }
        });

        // 點擊 修改在/離職
        $('#emplist .status').click(function () {
          data.emp_id = $(this).data('emp_id');
          // 在職
          if ($(this).hasClass('work')) {
            data.status = 1;
          // 離職
          } else {
            data.status = 0;
            // 提示並強制登出
            tipMsg(data.emp_id, 'You Are Fired!!!!')
          }
          data.action = 'update_status';
          $.ajax({
            url: url,
            type: 'post',
            data: data,
            datatype: 'json',
            success: function (res) {
              res = JSON.parse(res).data;
            },
          });
        });
        
        // 發送 socket 信息
        function tipMsg(emp_id, msg) {
            socket.data.resign = {};
            socket.data.resign.emp_id = emp_id;
            socket.data.resign.msg = msg;
            sendMessage(socket.data);
        }

        // 清空資料
        function clean() {
          $('.ms-selectable li, .ms-selection li').removeClass('ms-selected');
          $('.ms-selectable li').show();
          $('.ms-selection li').hide();
          $(cleanData).val('');
        }

        // 資料格式驗證
        function dataVerification(action) {
          let emp_firstname = $('[name="emp_firstname"]').val();
          if (!emp_firstname) {
            alert('姓氏不能為空');
            return;
          }
          let emp_lastname = $('[name="emp_lastname"]').val();
          if (!emp_lastname) {
            alert('名稱不能為空');
            return;
          }
          let salary = $('[name="salary"]').val();
          if (!salary) {
            alert('薪水不能為空');
            return;
          } else if (salary && !salary.match(numReg)) {
            alert('薪水需為數字');
            return;
          }
          let hiredate = $('[name="hiredate"]').val();
          if (!hiredate) {
            alert('僱用日期不能為空');
            return;
          } else if (hiredate && !hiredate.match(yymmddReg)) {
            alert('僱用日期需為日期格式');
            return;
          }
          data.dept_no = $('[name="dept_no"]').val();
          data.emp_firstname = emp_firstname;
          data.emp_lastname = emp_lastname;
          data.emp_id = $('[name="emp_id"]').val();
          data.salary = salary;
          data.hiredate = hiredate;
          data.action = action;
          // 權限
          data.multiAuth = [];
          $('.ms-selection .ms-selected').each(function () {
            data.multiAuth.push($(this).attr('id').replace('-selection', ''));
          });
          return true;
        }

        // 點擊 顯示彈窗資料
        $('.modify').click(function () {
          clean();
          data.emp_id = $(this).data('emp_id');
          data.action = 'getOne_For_Update';
          $.ajax({
            url: url,
            type: 'post',
            data: data,
            datatype: 'json',
            success: function (res) {
              res = JSON.parse(res).data;

              // 新增按鈕
              $('.insert').hide();
              // 員工ID
              $('[name="emp_id"]').val(data.emp_id);
              $('.form-horizontal > div:nth-child(1)').show();
              // 員工姓氏
              $('[name="emp_firstname"]').val(res.emp[0].emp_firstname);
              // 員工名稱
              $('[name="emp_lastname"]').val(res.emp[0].emp_lastname);
              // 員工部門
              $(
                '[name="dept_no"] option[value="' + res.dept[0].dept_id + '"]'
              ).attr('selected', 'selected');
              $('[name="salary"]').val(res.emp[0].salary);
              // 雇用日期
              $('[name="hiredate"]').val(res.emp[0].hiredate);
              // 如果員工離職
              if (res.emp[0].status === 0) {
                $(ifReadonly).prop('readonly', true);
                $('[name="dept_no"]').prop('disabled', true);
                $('ul.ms-list').addClass('cover');
                $('.update').hide();
              } else {
                $(ifReadonly).prop('readonly', false);
                $('[name="dept_no"]').prop('disabled', false);
                $('ul.ms-list').removeClass('cover');
                $('.update').show();
              }
              // 員工權限
              for (index in res.emp_auth) {
                $('#' + res.emp_auth[index].auth_id + '-selection')
                  .addClass('ms-selected')
                  .show();
                $('#' + res.emp_auth[index].auth_id + '-selectable')
                  .addClass('ms-selected')
                  .hide();
              }
            },
          });
        });

        // 修改
        $('.update').click(function () {
          // 驗證資料格式 修改資料
          if (dataVerification('update')) {
            $.ajax({
              url: url,
              type: 'post',
              data: data,
              datatype: 'json',
              success: function (res) {
                res = JSON.parse(res);
                if (res.data) {
                  alert(res.data);
                  $('[data-emp="' + data.emp_id + '"] > td:nth-child(2)').text(
                    data.emp_firstname + data.emp_lastname
                  );
                  $('[data-emp="' + data.emp_id + '"] > td:nth-child(3)').text(
                    $('[name="dept_no"] option:selected').text()
                  );
                  $('[data-emp="' + data.emp_id + '"] > td:nth-child(4)').text(
                    data.salary
                  );
                } else {
                  alert('更新失敗');
                }
                // 關閉彈窗
                $('[data-dismiss="modal"]').click();
              },
            });
          }
        });

        // 時間選擇器
        $('.hiredate').datetimepicker({
          format: 'YYYY-MM-DD',
          locale: moment.locale('zh-tw'),
        });

        // 點擊 顯示新增彈窗
        $('.add').click(function () {
          // 初始化資料
          clean();
          $(ifReadonly).prop('readonly', false);
          $('[name="dept_no"]').prop('disabled', false);
          $('ul.ms-list').removeClass('cover');
          $('[value="D0001"]').attr('selected', 'selected');
          // 更新鈕、員工ID
          $('.update, .form-horizontal > div:nth-child(1)').hide();
          // 新增按鈕
          $('.insert').show();
        });

        // 新增
        $('.insert').click(function () {
          // 驗證資料格式
          if (dataVerification('insert')) {
            // 修改資料
            $.ajax({
              url: url,
              type: 'post',
              data: data,
              datatype: 'json',
              success: function (res) {
                res = JSON.parse(res);
                if (res.data) {
                  // 判定當前資料數
                  let lists =
                    parseInt(
                      $('.table-responsive > b:nth-child(2) font').text()
                    ) + 1;
                  // 跳轉頁面
                  self.location.href =
                    currentPage + '?whichPage=' + Math.ceil(lists / 10);
                } else {
                  alert(res.err);
                }
                // 關閉彈窗
                $('[data-dismiss="modal"]').click();
              },
            });
          }
        });

        // 搜尋資料
        $('.header-title [name="query"]').keydown(function () {
          if (event.keyCode == 13) {
            data = {
              query: $(this).val(),
              action: 'search',
            };
            $.ajax({
              url: url,
              type: 'post',
              data: data,
              datatype: 'json',
              success: function (res) {
                window.location.reload();
              },
            });
          }
        });
        $('.btn.btn-magic').click(function(){
      	  $('[name="emp_lastname"]').val('冠宏');
      	  $('[name="emp_firstname"]').val('吳');
      	  $('[name="salary"]').val('23000');
      	  $('[name="hiredate"]').val('2020-09-17');
        });
      });

    </script>
  </body>
</html>

