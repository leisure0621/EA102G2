<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<header
	class="${'/front_end/home/home.jsp' eq pageContext.request.servletPath ? 'header-1 light-head': ''}">
	<div class="sticky">
		<div class="container">
			<!-- Logo -->
			<div class="logo">
				<a href="<%=request.getContextPath()%>/front_end/home/home.jsp">WE
					DO ASSEMBLE</a>
			</div>
			<nav class="navbar ownmenu">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#nav-open-btn"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"><i class="fa fa-navicon"></i></span>
					</button>
				</div>

				<!-- NAV -->
				<div class="collapse navbar-collapse" id="nav-open-btn">
					<ul class="nav">
						<li
							class="${'/front_end/home/home.jsp' eq pageContext.request.servletPath ? 'active': ''}">
							<a href="<%=request.getContextPath()%>/front_end/home/home.jsp">首頁</a>
						</li>

						<!-- MEGA MENU -->
						<li class="dropdown megamenu "><a href="#."
							class="dropdown-toggle" data-toggle="dropdown">商城</a>
							<div class="dropdown-menu">
								<div class="row">
									<!-- Shop Pages -->
									<div class="col-md-3">
										<h6>快速選購</h6>
										<ul>
											<li><a
												href="<%=request.getContextPath()%>/front_end/assemble/assemble.jsp">電腦組裝</a></li>
											<li><a
												href="<%=request.getContextPath()%>/front_end/c2cproMain/shop.jsp">二手商品</a></li>
											<li><a href="<%=request.getContextPath() %>/front_end/b2cpro_main/b2c_Shop.jsp?whichPage=1">3C商城</a></li>
											<li><a
												href="<%=request.getContextPath()%>/front_end/promotion/promoIndex.jsp">優惠購</a></li>
												<li><a href="<%=request.getContextPath() %>/front_end/repairApplication/addRepairMain.jsp">3C維修</a></li>
										</ul>
									</div>

									<!-- TOp Rate Products -->
									<div class="col-md-4">
										<h6 onclick="location.href='<%=request.getContextPath()%>/front_end/bidding/bidIndex.jsp'">競標商品</h6>
										<div class="top-rated">
											<ul>
												<c:forEach var="aBid" items="${bidList}">
							                        <li>
							                          <div class="media-left">
							                            <div class="cart-img">
							                              <a 
							                              	href="<%=request.getContextPath()%>/front_end/bidding/bidDetail.jsp?bid_id=${aBid.bid_id}&pro_id=${aBid.pro_id}"
							                              	style="background-image: url('<%=request.getContextPath()%>/showPic.do?pro_id=${aBid.pro_id}')"
							                              >
							                              </a>
							                            </div>
							                          </div>
							                          
							                          <div class="media-body">
							                            <h6 class="media-heading">${aBid.bid_title}</h6>
							                            <span class="price">$ ${aBid.start_price}<small>起</small></span>
							                          </div>
							                        </li>
						                        </c:forEach>
											</ul>
										</div>
									</div>

									<!-- New Arrival -->
									<div class="col-md-5">
										<h5>3C維修</h5>
										<img class="nav-img"
											src="<%=request.getContextPath()%>/front_end/assets/images/nav-img.png"
											alt="" />
										<p>
											將壞的，變成好的<br /> 我們的專業，滿足您對我們的期待
										</p>
										<a href="<%=request.getContextPath() %>/front_end/repairApplication/addRepairMain.jsp"  class="btn btn-small btn-round">快速維修</a>
									</div>
								</div>
							</div></li>

						<li><a
							href="<%=request.getContextPath()%>/front_end/assemble/assemble.jsp">電腦組裝</a>
						</li>
						<li><a
							href="<%=request.getContextPath()%>/front_end/textmain/textmain_index.jsp">討論區</a>
						</li>
					</ul>
				</div>

				<!-- Nav Right -->
				<div class="nav-right">
					<ul class="navbar-right">

						<!-- MSG INFO -->
						<c:if test="${memVO.authority > 0}">
							<li class="dropdown user-msg"><a class="dropdown-toggle"
								data-toggle="dropdown" role="button"> <i class="icon-energy"></i>
							</a>
								<ul class="dropdown-menu"></ul></li>
						</c:if>

						<!-- USER INFO -->
						<li class="dropdown user-acc"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown" role="button"><i
								class="icon-user"></i> </a>
							<ul class="dropdown-menu">
								<c:if test="${memVO.authority > 0}">
									<li><c:if test="${memVO.nickname.length() <= 0}">
											<h6>嗨！${memVO.first_name} ${memVO.last_name}</h6>
										</c:if> <c:if test="${memVO.nickname.length() > 0}">
											<h6>嗨！${memVO.nickname}</h6>
										</c:if></li>
									<li
										class="${'/front_end/membercenter/memberInformation.jsp' eq pageContext.request.servletPath ? 'active': ''}">
										<a
										href="<%=request.getContextPath()%>/front_end/membercenter/memberInformation.jsp">會員中心</a>
									</li>
								</c:if>

								<c:if test="${memVO.authority > 1}">
									<li><a
										href="<%=request.getContextPath()%>/front_end/c2cproMain/myshop.jsp">我的賣場</a>
									</li>
								</c:if>
								<c:if test="${memVO.authority > 1}">
									<li><a
										href="<%=request.getContextPath()%>/front_end/c2cproMain/c2cSeller.jsp">二手賣場訂單</a>
									</li>
								</c:if>
								<c:if
									test="${memVO.authority > 0}">
									<li><a
										href="<%=request.getContextPath()%>/front_end/c2cproMain/c2cbuyer.jsp">二手購買訂單</a>
									</li>
								</c:if>
								<c:if
									test="${memVO.authority > 0}">
									<li><a
										href="<%=request.getContextPath()%>/front_end/report/myReport.jsp">檢舉紀錄</a>
									</li>
								</c:if>

								<c:if
									test="${memVO.mem_id == null || memVO.mem_id.length() == 0}">
									<li
										class="${'/front_end/login/login.jsp' eq pageContext.request.servletPath ? 'active': ''}">
										<a
										href="<%=request.getContextPath()%>/front_end/login/login.jsp">登入會員</a>
									</li>
									<li
										class="${'/front_end/login/register.jsp' eq pageContext.request.servletPath ? 'active': ''}">
										<a
										href="<%=request.getContextPath()%>/front_end/login/register.jsp">註冊會員</a>
									</li>
									<li
										class="${'/front_end/login/forgetPassword.jsp' eq pageContext.request.servletPath ? 'active': ''}">
										<a
										href="<%=request.getContextPath()%>/front_end/login/forgetPassword.jsp">忘記密碼</a>
									</li>
								</c:if>
								<c:if test="${memVO.mem_id.length() > 0}">
									<li><a class="logout" onclick="logout()">登出會員</a></li>
								</c:if>
							</ul></li>

						<!-- USER BASKET -->
						<li class="dropdown user-basket"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-haspopup="true" aria-expanded="true"><i
								class="icon-basket-loaded"></i> </a>
							<ul class="dropdown-menu" data-mem_id="${memVO.mem_id}" data-mem_name="${memVO.first_name}${memVO.last_name}"></ul>
								
							</li>

						<!-- SEARCH BAR -->

					</ul>
				</div>
			</nav>
		</div>
	</div>
</header>