<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--Start left side Menu-->
<div class="left-side sticky-left-side">
	<!--logo-->
	<div class="logo">
		<a href="<%=request.getContextPath()%>/back_end/home/home.jsp">
		<img 
		src="<%=request.getContextPath()%>/back_end/assets/images/favicon.png"
		style="width: 35px;margin-top: -10px;margin-right: 4px;filter: sepia(1) drop-shadow(2px 1px 0px white);"
		/>組機吧</a>
	</div>
	<div class="logo-icon text-center">
		<a href="<%=request.getContextPath()%>/back_end/home/home.jsp"><img
			src="<%=request.getContextPath()%>/back_end/assets/images/logo-icon.png"
			alt=""></a>
	</div>
	<!--logo-->

	<div class="left-side-inner">
		<!--Sidebar nav-->
		<ul class="nav nav-pills nav-stacked custom-nav">
			<li
				class="menu-list ${'/back_end/home/home.jsp' eq pageContext.request.servletPath ? 'nav-active': ''}">
				<a href="#"> <i class="icon-grid"></i> <span>首頁</span>
			</a>
				<ul class="sub-menu-list">
					<li class="active"><a
						href="<%=request.getContextPath()%>/back_end/home/home.jsp">首頁</a></li>
				</ul>
			</li>
			<c:forEach var="empAuthVO" items="${empAuthVOList}">
        	<c:if test="${ 'AUT0001' eq empAuthVO.auth_id }">
			<li
				class="menu-list ${'/back_end/emp/empManagement.jsp' eq pageContext.request.servletPath ? 'nav-active': ''}">
				<a href="#"> <i class="icon-grid"></i> <span>員工管理</span>
			</a>
				<ul class="sub-menu-list">
					<li class="active"><a
						href="<%=request.getContextPath()%>/back_end/emp/empManagement.jsp">員工管理</a></li>
				</ul>
			</li>
			</c:if>
		
			<c:if test="${ 'AUT0002' eq empAuthVO.auth_id }">
			<li
				class="menu-list ${'/back_end/member/memberManagement.jsp' eq pageContext.request.servletPath ? 'nav-active': ''}">
				<a href="#"> <i class="icon-grid"></i> <span>會員管理</span>
			</a>
				<ul class="sub-menu-list">
					<li class="active"><a
						href="<%=request.getContextPath()%>/back_end/member/memberManagement.jsp">會員管理</a></li>
				</ul>
			</li>
			</c:if>
		
			<c:if test="${ 'AUT0003' eq empAuthVO.auth_id }">
			<li
				class="menu-list ${
								 ('/back_end/catagory/listAllCatagory.jsp' eq pageContext.request.servletPath)
								|| ('/back_end/catagory/listSpecByCat.jsp' eq pageContext.request.servletPath)
								|| ('/back_end/catagory/listProByCat.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/catagory/updateCatagory.jsp' eq pageContext.request.servletPath) 
            						|| ('/back_end/catagory/addCatagory.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/compatibility/addComp.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/compatibility/listAllComp.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/spec/addSpec.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/spec/addSpecDetail.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/spec/SpecIndex.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/spec/listDetailBySpec.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/spec/listSpecByPro.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/spec/updateProSpec.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/b2cproduct/addPro.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/b2cproduct/listAllPro.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/b2cproduct/searchProByCat.jsp' eq pageContext.request.servletPath)
            						|| ('/back_end/b2cproduct/updatePro.jsp' eq pageContext.request.servletPath)
            						 ? 'nav-active': ''}">

				<a href="#"> <i class="icon-grid"></i> <span>商城管理</span>
			</a>
				<ul class="sub-menu-list">
					<li
						${('/back_end/catagory/addCatagory.jsp' eq pageContext.request.servletPath)
            				 || ('/back_end/catagory/updateCatagory.jsp' eq pageContext.request.servletPath) 
            				 || ('/back_end/catagory/listAllCatagory.jsp' eq pageContext.request.servletPath)?'class="active"':''}>
						<a
						href="<%=request.getContextPath()%>/back_end/catagory/listAllCatagory.jsp">商品分類管理</a>
					</li>
				</ul>
				<ul class="sub-menu-list">
					<li
						${('/back_end/catagory/listProByCat.jsp' eq pageContext.request.servletPath)
							|| ('/back_end/b2cproduct/addPro.jsp' eq pageContext.request.servletPath)
			  				|| ('/back_end/b2cproduct/listAllPro.jsp' eq pageContext.request.servletPath)
            				|| ('/back_end/b2cproduct/searchProByCat.jsp' eq pageContext.request.servletPath)
            				|| ('/back_end/b2cproduct/updatePro.jsp' eq pageContext.request.servletPath)
            				|| ('/back_end/spec/listSpecByPro.jsp' eq pageContext.request.servletPath)
            				|| ('/back_end/spec/updateProSpec.jsp' eq pageContext.request.servletPath) ? 'class="active"' : ''}>
						<a href="<%=request.getContextPath()%>/back_end/b2cproduct/listAllPro.jsp">商品管理</a>
					</li>
				</ul>
				<ul class="sub-menu-list">
					<li
						${ ('/back_end/spec/addSpec.jsp' eq pageContext.request.servletPath)
            				|| ('/back_end/spec/addSpecDetail.jsp' eq pageContext.request.servletPath)
            				|| ('/back_end/spec/SpecIndex.jsp' eq pageContext.request.servletPath)
            				|| ('/back_end/spec/listDetailBySpec.jsp' eq pageContext.request.servletPath)
							|| ('/back_end/catagory/listSpecByCat.jsp' eq pageContext.request.servletPath)? 'class="active"' : ''}>
						<a href="<%=request.getContextPath()%>/back_end/spec/SpecIndex.jsp">規格管理</a>
					</li>
				</ul>
				<ul class="sub-menu-list">
					<li
						${ ('/back_end/compatibility/addComp.jsp' eq pageContext.request.servletPath)
            				|| ('/back_end/compatibility/listAllComp.jsp' eq pageContext.request.servletPath) ? 'class="active"' : '' }>
						<a href="<%=request.getContextPath()%>/back_end/compatibility/listAllComp.jsp">相容性管理</a>
					</li>
				</ul>
			</li>
			</c:if>
		
			<c:if test="${ 'AUT0004' eq empAuthVO.auth_id }">
				<li
					class="menu-list ${('/back_end/promotion/promoManagement.jsp' eq pageContext.request.servletPath) 
	            					|| ('/back_end/promotion/addPromo.jsp'eq pageContext.request.servletPath)
	            					|| ('/back_end/promotion/updatePromoInput.jsp'eq pageContext.request.servletPath) 
	            					|| ('/back_end/promotion/promoDetailManagement.jsp'eq pageContext.request.servletPath)
	            					|| ('/back_end/promotion/addPromoDetail.jsp'eq pageContext.request.servletPath)
	            					|| ('/back_end/promotion/updatePromoDetailInput.jsp'eq pageContext.request.servletPath) 
	            					|| ('/back_end/promotion/listOnePromoDetail.jsp'eq pageContext.request.servletPath) ? 'nav-active': ''}">
					<a href="#"> <i class="icon-grid"></i> <span>促銷管理</span> </a>
					<ul class="sub-menu-list">
						<li
							class="${('/back_end/promotion/promoManagement.jsp' eq pageContext.request.servletPath) 
	                			  || ('/back_end/promotion/addPromo.jsp' eq pageContext.request.servletPath)
	                			  || ('/back_end/promotion/updatePromoInput.jsp' eq pageContext.request.servletPath) ? 'active': ''}">
							<a
							href="<%=request.getContextPath()%>/back_end/promotion/promoManagement.jsp">促銷方案管理</a>
						</li>
						<li
							class="${('/back_end/promotion/promoDetailManagement.jsp' eq pageContext.request.servletPath) 
	                    		  || ('/back_end/promotion/addPromoDetail.jsp' eq pageContext.request.servletPath) 
	                    		  || ('/back_end/promotion/updatePromoDetailInput.jsp'eq pageContext.request.servletPath) 
	                    		  || ('/back_end/promotion/listOnePromoDetail.jsp'eq pageContext.request.servletPath) ? 'active': ''}">
							<a
							href="<%=request.getContextPath()%>/back_end/promotion/promoDetailManagement.jsp">促銷商品管理</a>
						</li>
					</ul>
				</li>
			</c:if>
		
			<c:if test="${ 'AUT0005' eq empAuthVO.auth_id }">
				<li
					class="menu-list ${('/back_end/bidding/bidManagement.jsp' eq pageContext.request.servletPath)
	           						|| ('/back_end/bidding/addBid.jsp' eq pageContext.request.servletPath)
	           						|| ('/back_end/bidding/listOneBid.jsp' eq pageContext.request.servletPath)
	           						|| ('/back_end/bidding/updateBidInput.jsp' eq pageContext.request.servletPath)
	           						|| ('/back_end/bidding/bidDetailManagement.jsp' eq pageContext.request.servletPath) 
	           						|| ('/back_end/bidding/listOneBidDetail.jsp' eq pageContext.request.servletPath) ? 'nav-active': ''}">
					<a href="#"> <i class="icon-grid"></i> <span>競標管理</span> </a>
					<ul class="sub-menu-list">
						<li
							class="${('/back_end/bidding/bidManagement.jsp' eq pageContext.request.servletPath) 
	                			  || ('/back_end/bidding/addBid.jsp' eq pageContext.request.servletPath)
	                		      || ('/back_end/bidding/listOneBid.jsp' eq pageContext.request.servletPath) 
	                			  || ('/back_end/bidding/updateBidInput.jsp' eq pageContext.request.servletPath) ? 'active': ''}">
							<a
							href="<%=request.getContextPath()%>/back_end/bidding/bidManagement.jsp">競標管理</a>
						</li>
						<li
							class="${('/back_end/bidding/bidDetailManagement.jsp' eq pageContext.request.servletPath) 
	                    		  || ('/back_end/bidding/listOneBidDetail.jsp' eq pageContext.request.servletPath) ? 'active': ''}">
							<a
							href="<%=request.getContextPath()%>/back_end/bidding/bidDetailManagement.jsp">競標紀錄管理</a>
						</li>
	
					</ul>
				</li>
			</c:if>

			<c:if test="${ 'AUT0006' eq empAuthVO.auth_id }">
				<li
					class="menu-list ${'/back_end/report/c2cproRep.jsp' eq pageContext.request.servletPath ? 'nav-active': ''}">
					<a href="#"> <i class="icon-grid"></i> <span>檢舉管理</span> </a>
					<ul class="sub-menu-list">
						<li class="active"><a
							href="<%=request.getContextPath()%>/back_end/report/c2cproRep.jsp">商品檢舉</a></li>
					</ul>
				</li>
			</c:if>
			
			<c:if test="${ 'AUT0007' eq empAuthVO.auth_id }">
				<li
					class="menu-list ${'/back_end/repairMain/listAllRepairMain.jsp' eq pageContext.request.servletPath ? 'nav-active': ''}">
					<a href="#"> <i class="icon-grid"></i> <span>維修單主檔管理</span>
				</a>
					<ul class="sub-menu-list">
						<li class="active"><a
							href="<%=request.getContextPath()%>/back_end/repairMain/listAllRepairMain.jsp">維修單主檔管理</a></li>
					</ul>
				</li>
			</c:if>
			
			<c:if test="${ 'AUT0008' eq empAuthVO.auth_id }">
				<li
					class="menu-list ${'/back_end/textmain_rep/text_mainRep.jsp' eq pageContext.request.servletPath ? 'nav-active': ''}">
					<a href="#"> <i class="icon-grid"></i> <span>討論區管理</span> </a>
					<ul class="sub-menu-list">
						<li class="active"><a
							href="<%=request.getContextPath()%>/back_end/textmain_rep/text_mainRep.jsp">文章檢舉管理</a></li>
					</ul>
				</li>
			</c:if>
			
			<c:if test="${ 'AUT0003' eq empAuthVO.auth_id }">
				<li
					class="menu-list ${'/back_end/b2cso/index.jsp' eq pageContext.request.servletPath ? 'nav-active': ''}">
					<a href="#"> <i class="icon-grid"></i> <span>訂單管理</span> </a>
					<ul class="sub-menu-list">
						<li class="active"><a
							href="<%=request.getContextPath()%>/back_end/b2cso/index.jsp">訂單管理</a></li>
					</ul>
				</li>
			</c:if>
			
			
			
		  </c:forEach>
		</ul>
		<!--End sidebar nav-->
	</div>
</div>
<!--End left side menu-->