<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--======= FOOTER =========-->
<footer>
  <div class="container">
    <!-- ABOUT Location -->
    <div class="col-md-4">
      <div class="about-footer">
        <p class="margin-bottom-30 logo">
          <a href="<%=request.getContextPath() %>/front_end/home/home.jsp"> WeDoAssemble</a>
        </p>
        <p><i class="icon-pointer"></i> 台北市大安區永志路 102 號</p>
        <p><i class="icon-call-end"></i> 0800-000-123</p>
        <p><i class="icon-envelope"></i> 00Dog@RuffRuffRuff.com</p>
      </div>
    </div>

    <!-- HELPFUL LINKS -->
    <div class="col-md-3">
      <h6>常見問題</h6>
      <ul class="link">
        <li><a href="<%=request.getContextPath() %>/front_end/ftcontent/commonProblem/index.jsp"> 常見問題</a></li>
        <li><a href="<%=request.getContextPath() %>/front_end/ftcontent/shoppingNotes/index.jsp"> 購物須知</a></li>
        <li><a href="<%=request.getContextPath() %>/front_end/ftcontent/computerRepair/index.jsp"> 商品維修</a></li>
        <li><a href="<%=request.getContextPath() %>/front_end/ftcontent/privacyStatement/index.jsp"> 隱私權聲明</a></li>
      </ul>
    </div>

    <!-- SHOP -->
    <div class="col-md-3">
      <h6>商城</h6>
      <ul class="link">
        <li><a href="<%=request.getContextPath() %>/front_end/assemble/assemble.jsp"> 電腦組裝</a></li>
        <li><a href="<%=request.getContextPath() %>/front_end/b2cpro_main/b2c_Shop.jsp?whichPage=1"> 商城新品</a></li>
        <li><a href="<%=request.getContextPath() %>/front_end/c2cproMain/shop.jsp"> 二手商城</a></li>
        <li><a href="<%=request.getContextPath() %>/front_end/bidding/bidIndex.jsp"> 商品競標</a></li>
        
       
      </ul>
    </div>
    <!-- MY ACCOUNT -->
    <div class="col-md-2">
      <h6>我的帳號</h6>
      <ul class="link">
    <c:if test="${memVO.mem_id == null || memVO.mem_id.length() == 0}">
		<li class="${'/front_end/login/login.jsp' eq pageContext.request.servletPath ? 'active': ''}">
			<a href="<%=request.getContextPath()%>/front_end/login/login.jsp">會員登入</a>
		</li>
	</c:if>
	<c:if test="${memVO.mem_id.length() > 0}">
		<li><a class="logout" onclick="logout()">會員登出</a></li>
	</c:if>
        <li><a href="<%=request.getContextPath() %>/front_end/membercenter/memberInformation.jsp"> 會員中心</a></li>
        <li><a href="<%=request.getContextPath() %>/front_end/membercenter/orderMain.jsp"> 我的訂單</a></li>
      </ul>
    </div>

    <!-- Rights -->

    <div class="rights">
      <p>© 2017 WeDoAssemble All right reserved.</p>
      <div class="scroll">
        <a href="#wrap" class="go-up"><i class="lnr lnr-arrow-up"></i></a>
      </div>
    </div>
  </div>
</footer>