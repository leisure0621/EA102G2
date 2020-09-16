<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="textmainSvc" scope="page" class="com.text_main.model.TextMainService" />
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="M_Adnan">
  <title>WE DO ASSEMBLE</title>

  <%@ include file="/front_end/headerSection.jsp"%>
  <%@ page import="com.msg.model.*"%>
  <%@ page import="com.text_main.model.*"%>
  <%@ page import="com.text_rep.model.*"%>
  <%
		MsgService MsgService = new MsgService();
		List<MsgVO> list = MsgService.getAll();
		pageContext.setAttribute("list", list);
		
		MsgVO msgVO = (MsgVO) request.getAttribute("msgVO");
		TextRepVO textrepVO = (TextRepVO) request.getAttribute("textrepVO");
		
		TextMainVO textmainVO = (TextMainVO) request.getAttribute("textmainVO");
  %>
  <style>
    .tp-banner-container .tp-banner>div {
      padding-top: 25%;
      position: absolute;
      z-index: 1;
      top: 0;
      left: calc(50% - 330px/ 2);
      width: 100%;
    }

    .tp-banner-container .btn {
      width: 330px;
      border-radius: 50px;
      letter-spacing: 2px;
      box-shadow: 0px 2px 4px rgb(45 58 75/ 0.5);
    }

    .tp-banner-container .btn.focus,
    .tp-banner-container .btn:focus,
    .tp-banner-container .btn:hover {
      color: #2d3a4b;
      background-color: var(- -headerBlackFontColor);
    }

    .home-slider .tp-banner-container .tp-banner img {
      display: none !important;
    }

    header.light-head .navbar li.active a:before,
    header.light-head .navbar li:hover a:before {
      width: 100%;
      margin: 0px auto;
      position: absolute;
      content: "";
      height: 6px;
      top: 45%;
      left: 0px;
      z-index: -1;
      background: var(- -headerBlackFontColor);
    }

    header.light-head .navbar li a:before {
      top: 45%;
      height: 6px;
    }

    header.light-head .navbar li a:hover {
      background: rgba(0, 0, 0, 0);
    }

    header.light-head .navbar .nav>li a {
      text-shadow: 1px 1px 0 black, -1px -1px 0 black;
    }

    video::-webkit-media-controls {
      display: none !important;
    }

    video {
      filter: brightness(0.95);
    }

    .tp-simpleresponsive img {
      display: none !important;
    }

    @media screen and (max-width: 768px) {
      .tp-banner-container video {
        display: none;
      }

      .home-slider .tp-banner-container .tp-banner img {
        max-height: 350px;
        display: block !important;
      }
      .row {
	    margin-right: 0;
	    margin-left: 0;
	  }
    }

    .comments form .form-control {
      display: inline-block;
      width: 95%;
      height: 40px;
      margin-top: 5px;
      border-radius: 0px;
      box-shadow: none;
      background: none;
      border: 1px solid #2d3a4b;
    }

    element.style {
      font-size: 12pt;
      height: 120px;
      width: 720px;
    }

    .blog-list article .btn {
      margin-top: 0;
      font-size: 14px;
      font-weight: normal;
      padding: 2px 30px;
      height: 40px;
      line-height: 25px;
    }

    .form-control {
      width: 100%!important;
      margin-bottom: 12px;
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


    <!--======= SUB BANNER =========-->


    <!-- Content -->

    <!-- Blog List -->

    <section class="blog-list blog-list-3 single-post padding-top-100 padding-bottom-100">
      <div class="container">
        <div class="row">
          <div class="">
            <%@ include file="/front_end/msg/page1.file"%>
            <!-- Article -->
            <article>
              <!-- Post Img -->
              <img class="img-responsive"
                src="${pageContext.request.contextPath}/front_end/msg/assets/images/blog-list-img-2.jpg" alt="">
              <!-- Tittle -->
              <div class="post-tittle left">
                <h2 class="tittle">${textmainVO.title}</h2>
                <!-- Post Info -->
                <span>
                  <i class="primary-color icon-user"></i>${textmainVO.author_id }</span>
                <!-- 								<span> -->
                <span>
                  <i class="primary-color icon-calendar"></i>
                  <fmt:formatDate value="${textmainVO.est_time}" pattern="yyyy/MM/dd HH:mm" /></span>
                <button type="submit" class="btn btn-primary"
                  onclick="window.location.href='<%=request.getContextPath()%>/front_end/textmain/addtext_rep.jsp?text_id=${textmainVO.text_id}'">檢舉</button>
                <input type="hidden" name="text_id" value="${textmainVO.text_id}">

              </div>
              <!-- Post Content -->
              <div class="text-left">
                <p>${textmainVO.content }</p>

              </div>
            </article>
            <!-- ADMIN info -->
            <div class="admin-info">



              <!--=======  COMMENTS =========-->
              <div class="comments margin-top-80">
                <h5 class="shop-tittle margin-bottom-30">COMMENTS</h5>
                <ul class="media-list padding-left-15">
                  <!--=======  COMMENTS =========-->
                  <li class="media">
                    <c:forEach var="msgVO" items="${list}">
                      <c:if test="${msgVO.text_id==textmainVO.text_id}">
                        <c:if test="${msgVO.status==1}">
                          <div class="media-body" style="display: block">
                            <h6 class="media-heading">${msgVO.author_id}
                              <span>
                                <i class="icon-clock primary-color"></i>
                                <fmt:formatDate value="${msgVO.est_time}" pattern="yyyy/MM/dd HH:mm" /></span>
                            </h6>
                            <p>${msgVO.content}</p>
                          </div>
                        </c:if>
                      </c:if>
                    </c:forEach>
                  </li>
                </ul>

                <!--=======  LEAVE COMMENTS =========-->
                <div>
                  <c:if test="${not empty errorMsgs}">
                    <font style="color: red">請修正以下錯誤:</font>
                    <ul>
                      <c:forEach var="message" items="${errorMsgs}">
                        <li style="color: red">${message}</li>
                      </c:forEach>
                    </ul>
                  </c:if>
                </div>
                <h5 class="shop-tittle margin-top-80">POST YOUR COMMENTS</h5>



                <form class="padding-left-15" METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/msg/msg.do"
                  name="form1">
                  <div>
                    <%=memVO.getMem_id()%><input type="hidden" name="author_id" value="${textmainVO.author_id}" />
                    </label>
                    <label>
                      <input type="text" class="form-control" name="content" id="message"
                        style="font-size: 12pt; height: 120px; width: 600px;" placeholder="請輸入留言內容"
                        value="<%=(msgVO == null) ? "" : msgVO.getContent()%>" />
                    </label>
                    <input type="hidden" name="action" value="insert">
                    <input type="hidden" name="text_id" value="${textmainVO.text_id}">
                    <input type="submit" class="btn" value="送出新增">
                    <input class="btn btn-info" id="666" type="button" value="神奇小按鈕">
                </FORM>


                </form>
              </div>
            </div>
            </article>
            <hr>

            <!-- SIDE BACR BANER -->
            <div class="side-bnr margin-top-50">
              <img class="img-responsive" src="images/sidebar-bnr.jpg" alt="">
              <div class="position-center-center"></div>
            </div>
          </div>
        </div>
      </div>
  </div>
  </section>

  <!-- Culture BLOCK -->
  <section class="cultur-block">

    <!-- Culture Text -->

  </section>


  <!--======= FOOTER =========-->
  <%@ include file="/front_end/footerMenu.jsp"%>
  <%@ include file="/front_end/footerSection.jsp"%>
<script>
$('#666').click(function(){
 	$('[name="content"]').val('電腦上的問題我一律建議丟掉');
 });
</script>

</body>

</html>