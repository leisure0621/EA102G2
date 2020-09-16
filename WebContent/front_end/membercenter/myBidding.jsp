<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page
	import="com.bid_main.model.*, com.bid_detail.model.*, java.text.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="M_Adnan">
	<title>我的競標</title>
	<%@ include file="/front_end/headerSection.jsp"%>
	<style>
		ul.login-with {
			padding-top: 19px;
		}
		
		.btn:focus {
			color: white;
		}
		
		.active a {
			color: var(- -headerBlackFontColor);
		}
	</style>

</head>
<body>

	<%@ include file="/front_end/headerMenu.jsp"%>

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
			<section
				class="member-center_information margin-top-23 margin-bottom-23 padding-bottom-23 padding-top-23">
				<div class="container">

					<!-- 會員中心功能 -->
					<div class="col-sm-3 leftSlide">
						<%@ include file="/front_end/membercenterLeftMenu.jsp"%>
					</div>

					<!-- 頁面資料 -->
					<div class="col-sm-9 rightSlide shopping-cart">
						<div class="cart-ship-info">
							<h6>我的競標</h6>

							<%
								// 取得會員所有下標紀錄
								BidDetailService bidDetailSvc = new BidDetailService();
								List<BidDetailVO> bdlist = bidDetailSvc.getByMemId(memVO.getMem_id());
							%>

							<h6>競標中</h6>
							<table class="display table">
								<thead>
									<tr>
										<th>競標編號</th>
										<th>出價金額</th>
										<th>出價時間</th>
									</tr>
								</thead>

								<jsp:useBean id="timeFormat" scope="page" class="java.util.Date" />
								<tbody>
									<%
										// 以會員下標紀錄篩選競標中的紀錄
										for (BidDetailVO aMemBid : bdlist) {
											BidMainService bidMainSvc = new BidMainService();
											BidMainVO bmVO = bidMainSvc.getOneBid(aMemBid.getBid_id());

											SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											java.util.Date nowTime = java.sql.Timestamp.valueOf(df.format(new java.util.Date()));
											java.util.Date endTime = java.sql.Timestamp.valueOf(df.format(bmVO.getEnd_time()));
											java.util.Date startTime = java.sql.Timestamp.valueOf(df.format(bmVO.getStart_time()));
											boolean flag = endTime.after(nowTime) && startTime.before(nowTime); // 是否當下還在期限內


											pageContext.setAttribute("aMemBid", aMemBid); // 競標中物件

											if (flag && aMemBid != null) {
									%>
									<tr>
										<td>${aMemBid.bid_id}</td>
										<td>${aMemBid.bid_price}</td>
										<td><fmt:formatDate value="${aMemBid.bid_time}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
									</tr>
									<%
										}
									}
									%>
								</tbody>
							</table>
							<h6>已結束的競標</h6>
							<table class="display table">
								<thead>
									<tr>
										<th>競標編號</th>
										<th>出價金額</th>
										<th>出價時間</th>
										<th>得標狀態</th>
									</tr>
								</thead>
								<tbody>
								<%
									// 以會員下標紀錄篩選競標結束的紀錄
									for (BidDetailVO aMemBid : bdlist) {
										BidMainService bidMainSvc = new BidMainService();
										BidMainVO bmVO = bidMainSvc.getOneBid(aMemBid.getBid_id());
										pageContext.setAttribute("bmVO", bmVO);

										// 取得最後得標的出價紀錄
										List<BidDetailVO> bdVOs = new BidDetailService().getByBidId(aMemBid.getBid_id());
										BidDetailVO lastBid = bdVOs.get(bdVOs.size()-1);
										
										pageContext.setAttribute("lastBid", lastBid); // 最後得標的出價紀錄

										SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										java.util.Date nowTime = java.sql.Timestamp.valueOf(df.format(new java.util.Date()));
										java.util.Date endTime = java.sql.Timestamp.valueOf(df.format(bmVO.getEnd_time()));
										java.util.Date startTime = java.sql.Timestamp.valueOf(df.format(bmVO.getStart_time()));
										boolean flag = endTime.after(nowTime) && startTime.before(nowTime); // 是否當下還在期限內

										pageContext.setAttribute("aMemPassBid", aMemBid); // 已結束競標物件
					
										if (!flag && aMemBid != null) {
								%>
									<tr>
										<td>${aMemPassBid.bid_id}</td>
										<td>${aMemPassBid.bid_price}</td>
										<td><fmt:formatDate value="${aMemPassBid.bid_time}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${bmVO.getWinner() eq memVO.getMem_id() && aMemPassBid.bid_price == lastBid.bid_price ? '已得標' : '未得標'}</td>
									</tr>

									<%
										}
									}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</section>
		</div>
		<%@ include file="/front_end/footerMenu.jsp"%>
	</div>
	<%@ include file="/front_end/footerSection.jsp"%>

</body>
</html>