<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.text_main.model.*"%>
    <%@ page import="com.msg.model.*"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page import="java.util.*"%>
    <%
	TextMainService TextMainService = new TextMainService();
    List<TextMainVO> list = TextMainService.getAll();
    pageContext.setAttribute("list",list);
	
    TextMainVO list1 = TextMainService.getOneText_main("text_id");
    pageContext.setAttribute("list1",list1);
    %>
    <%
	TextMainVO textmainVO= (TextMainVO) request.getAttribute("textmainVO"); //EmpServlet.java(Concroller)
%>

	<%    
	MsgVO msgVO= (MsgVO) request.getAttribute("msgVO"); 
%>
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


<%@ include file="/front_end/headerSection.jsp" %>
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
    textarea.form-control {
	    height: auto;
	    margin-top: 15px;
	    margin-bottom: 15px;
	    border-radius: 0;
	    min-height: 250px;
	    border: 1px solid #2d3a4b;
	    font-size: 11px;
	}
	input#name {
	    border-radius: 0;
	    border: 1px solid #2d3a4b;
	    font-size: 11px;
	    width: 60px;
	    height: 44px;
	    float: left;
	    width: 100%;
	    padding: 0 10px;
	    letter-spacing: 1px;
	    display: inline-block;
	    font-weight: normal;
	    text-align: left;
	    margin-top: 10px;
	    -webkit-transition: all 0.4s ease-in-out;
	    -moz-transition: all 0.4s ease-in-out;
	    -o-transition: all 0.4s ease-in-out;
	    -ms-transition: all 0.4s ease-in-out;
	    transition: all 0.4s ease-in-out;
	}
    </style>
</head>

<!-- Wrap -->
<div id="wrap"> 
  
  <!-- header -->
  <%@ include file="/front_end/headerMenu.jsp" %>
        
  
                  
              
         
  
  <!--======= SUB BANNER =========-->

  
  <!-- Content -->
  <div id="content"> 
    
    <!-- Blog List -->
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
   
    <section class="blog-list padding-top-100 padding-bottom-100">
      <div class="container">
       <h5>新增文章</h5>
        <div class="row">
          <div class="col-md-12"> 
          
            <!-- Article -->
          <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/textmain/textmain.do" name="form1">
                <ul class="row">
                  <input type="hidden" name="author_id" value="${memVO.mem_id}"/>
                  
                  <li class="col-sm-12">
                      <input type="text" class="form-control" name="title" id="name" placeholder="請輸入文章標題..."
                      		 value="<%= (textmainVO==null)? "" : textmainVO.getTitle()%>" />
                  </li>
                  <li class="col-sm-12">
                      <textarea type="text" 
                      class="form-control" 
                      name="content" 
                      id="message" 
                      placeholder="請輸入文章內容..."><%= (textmainVO==null)? "" : textmainVO.getContent()%></textarea>
                  </li>
                  <li class="col-sm-12">
                    <input type="hidden" name="action" id="newtext" value="insert">
					<input type="submit" class="btn" id="newtext" value="送出新增">
					<input class="btn btn-info" id="666" type="button" value="神奇小按鈕">
                  </li>
                </ul>
              </form>
              <!-- Post Content -->
              <div class="text-left">
            
            </article>
            
            
        
            
           
            
           
          </div>
          
        
            
              <!-- SIDE BACR BANER -->
              <div class="side-bnr margin-top-50"> <img class="img-responsive" src="images/sidebar-bnr.jpg" alt="">
                <div class="position-center-center"> 
                 
                </div>
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
    
    <!-- News Letter -->
  </div>
 
  <!--======= FOOTER =========-->
	<%@ include file="/front_end/footerMenu.jsp"%>
		<%@ include file="/front_end/footerSection.jsp"%>
		<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
		<script>
		document.getElementById("newtext").addEventListener("click",function(){
			Swal.fire({
				  position: 'top-end',
				  icon: 'success',
				  title: 'Your work has been saved',
				  showConfirmButton: false,
				  timer: 1500
				});
		});
		 $('#666').click(function(){
	         	$('[name="title"]').val('預算80K組電腦，求解 ');
	         	$('[name="content"]').val('各位大大安安，小弟想組一台電腦，我不會組電腦，我就爛');
	         });
		</script>
</body>
</html>