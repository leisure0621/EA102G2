<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!-- header section start-->
<div class="header-section">
  <a class="toggle-btn"><i class="fa fa-bars"></i></a>

  <!--notification menu start -->
  <div class="menu-right">
    <ul class="notification-menu">
      <li>
        <a
          href="#"
          class="btn btn-default dropdown-toggle info-number"
          data-toggle="dropdown"
        >
          <i class="fa fa-tasks"></i>
          <span class="badge"></span>
        </a>
        <div class="dropdown-menu dropdown-menu-head pull-right">
          <h5 class="title">你尚有 <span>0</span> 條最新消息需查看</h5>
          <ul class="dropdown-list">
            <li class="notification-scroll-list notification-list"></li>
            <li class="last"></li>
          </ul>
        </div>
      </li>

      <li>
        <a
          href="#"
          class="btn btn-default dropdown-toggle"
          data-toggle="dropdown"
        >
          Hi ! ${empVO.emp_lastname} <span class="caret"></span>
        </a>
        <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
          <li>
            <a class="logout"><i class="fa fa-lock"></i>登出</a>
          </li>
        </ul>
      </li>
    </ul>
  </div>
  <!--notification menu end -->
</div>
<!-- header section end-->
