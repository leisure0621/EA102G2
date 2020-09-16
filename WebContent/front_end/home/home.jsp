<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="M_Adnan" />
    <title>WE DO ASSEMBLE</title>

    <%@ include file="/front_end/headerSection.jsp" %>
    <%@ page import="com.text_main.model.*,java.util.stream.Collectors" %>
    <%
    TextMainService textMainService = new TextMainService();
    List<TextMainVO> textMainList = 
    		textMainService.getAll().stream()
            .filter(x -> x.getStatus() == 1)
            .collect(Collectors.toList())
            .subList(0, 2);
    pageContext.setAttribute("textMainList", textMainList);
    %>
    
    <style>
    
    .tp-banner-container .tp-banner > div {
	    padding-top: 25%;
	    position: absolute;
	    z-index: 1;
	    top: 0;
	    left: calc(50% - 330px/2);
	    width: 100%;
    }
    .tp-banner-container .btn {
	    width: 330px;
	    border-radius: 50px;
	    letter-spacing: 2px;
	    box-shadow: 0px 2px 4px rgb(45 58 75 / 0.5);
	}
	.tp-banner-container .btn.focus, 
	.tp-banner-container .btn:focus,
	.tp-banner-container .btn:hover {
		color: #2d3a4b;
		background-color: var(--headerBlackFontColor);
	}
	.home-slider .tp-banner-container .tp-banner img {
	    display: none!important;
	}
	header.light-head .navbar li.active a:before,
	header.light-head .navbar li:hover a:before{
	    width: 100%;
	    margin: 0px auto;
	    position: absolute;
	    content: "";
	    height: 6px;
	    top: 45%;
	    left: 0px;
	    z-index: -1;
	    background: var(--headerBlackFontColor);
	}
	header.light-head .navbar li a:before {
		top: 45%;
		height: 6px;
	}
	header.light-head .navbar li a:hover {
	    background: rgba(0,0,0,0);
	}
	header.light-head .navbar .nav > li a {
	    text-shadow: 1px 1px 0 black, -1px -1px 0 black;
	}
	video::-webkit-media-controls{
        display:none !important;
    }
    video {
    	filter: brightness(0.95);
    }
    .tp-simpleresponsive img {
    	display: none!important;
    }
    @media screen and (max-width: 768px) {
    	.tp-banner-container video {
    		display: none;
    	}
    	.home-slider .tp-banner-container .tp-banner img {
		    display: block!important;
		}
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
      <%@ include file="/front_end/headerMenu.jsp" %>

      <!--======= HOME MAIN SLIDER =========-->
      <section class="home-slider">
        <!-- SLIDE Start -->
        <div class="tp-banner-container">
          <div class="tp-banner">
          	<video src="<%=request.getContextPath() %>/front_end/assets/movie/mini.mp4" autoplay loop controls muted></video>
          	<img src="<%=request.getContextPath() %>/front_end/assets/images/video.png">
            <div>
                <a href="<%=request.getContextPath() %>/front_end/assemble/assemble.jsp" class="btn">開始組裝</a>
            </div>
          </div>
        </div>
      </section>

      <!-- Content -->
      <div id="content">
        <!-- Top Shop Feature  -->
        <div class="top-shop-feature">
          <div class="container">
            <div class="row">
              <!-- Item 1  -->
              <div class="col-md-6">
                <div class="text-right">
                  <img src="<%=request.getContextPath() %>/front_end/assets/images/P01.png" alt="" />
                  <article>
                    <h4>單一商品購買</h4>
                    <p>
			                         你需要的，我們都有。<br />
			                         任你比較。
                    </p>
                    <a href="<%=request.getContextPath() %>/front_end/b2cpro_main/b2c_Shop.jsp?whichPage=1" class="btn btn-small btn-round">進入3C商城</a>
                  </article>
                </div>
              </div>

              <!-- Item 2  -->
              <div class="col-md-6 light">
                <div class="text-left">
                  <img src="<%=request.getContextPath() %>/front_end/assets/images/P02.png" alt="" />
                  <article>
                    <h4>二手商品購買</h4>
                    <p>
		                                     販售你不需要的。<br />
		                                     購買你想要的。
                    </p>
                    <a href="<%=request.getContextPath() %>/front_end/c2cproMain/shop.jsp" class="btn btn-small btn-round">進入二手商城</a>
                  </article>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Knowledge Share -->
        <section class="light-gray-bg padding-top-150 padding-bottom-150">
          <div class="container">
            <!-- Main Heading -->
            <div class="heading text-center">
              <h4>一些需要知道的小知識</h4>
              <span
                >我們經常會再購買時遇到花了錢卻無法得到想要的電腦，或配了電腦確需退回換零件的情形。<br />有以上需求的都可以進討論區文章發言並詢問</span
              >
            </div>
            <div class="knowledge-share">
              <ul class="row">
              
                <!-- Post 1 -->
                <c:forEach var="textMainVO" items="${textMainList}">
                <li class="col-md-6">
                  <div class="date">
                    <span>
                    <fmt:formatDate pattern="MM" value="${textMainVO.est_time}" />
                    </span> 
                    <span class="huge">
                    <fmt:formatDate pattern="dd" value="${textMainVO.est_time}" />
                    </span>
                  </div>
                  <a href="#.">${textMainVO.title}</a>
                  <p>${textMainVO.content}</p>
                </li>
                </c:forEach>

              </ul>
            </div>
          </div>
        </section>

        <!-- About -->
        <section class="small-about padding-top-150 padding-bottom-150">
          <div class="container">
            <!-- Main Heading -->
            <div class="heading text-center">
              <h4>關於我們</h4>
              <p>
			                從一開始的電腦零件售賣，到許多顧客與朋友們對於電腦與零件的意見，<br />
			                得知很多購買主機或是已經配置好的電腦對於一般的使用者來說不是一件簡單的事情，<br />
			                但我們想做得更好、更棒！<br />
			                於是這就是我們的起源，一個想為了大家更方便而出現的平台"組機吧"。<br />
			                相信我們，你們會更好，我們也會做得更棒‧
              </p>
            </div>

            <!-- Social Icons -->
            <ul class="social_icons">
              <li>
                <a href="#."><i class="icon-social-facebook"></i></a>
              </li>
              <li>
                <a href="#."><i class="icon-social-twitter"></i></a>
              </li>
              <li>
                <a href="#."><i class="icon-social-tumblr"></i></a>
              </li>
              <li>
                <a href="#."><i class="icon-social-youtube"></i></a>
              </li>
              <li>
                <a href="#."><i class="icon-social-dribbble"></i></a>
              </li>
            </ul>
          </div>
        </section>

      </div>
	  <%@ include file="/front_end/footerMenu.jsp" %>
      <!--======= RIGHTS =========-->
    </div>
    <%@ include file="/front_end/footerSection.jsp" %>
    
  </body>
</html>
