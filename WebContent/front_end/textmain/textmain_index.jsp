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
<%
	List<String> imageList = new ArrayList<String>();
	imageList.add("/front_end/textmain/assets/images/01.jpg");
	imageList.add("/front_end/textmain/assets/images/02.jpg");
	imageList.add("/front_end/textmain/assets/images/03.jpg");
	imageList.add("/front_end/textmain/assets/images/04.jpg");
	imageList.add("/front_end/textmain/assets/images/05.jpg");
	Iterator<String> it = imageList.iterator();
	
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
    .text-image {
	    width: 100%;
	    height: 646px;
	    background-position: center;
	    background-size: contain;
	    background-repeat: no-repeat;
	    background-color: black;
	    margin-top: 15px;
	    margin-bottom: 15px;
	}
    @media screen and (max-width: 768px) {
    	.tp-banner-container video {
    		display: none;
    	}
    	.home-slider .tp-banner-container .tp-banner img {
    		max-height: 350px;
		    display: block!important;
		}
	}
	img.img-responsive{
	width:100%;
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
   
    <section class="blog-list padding-top-100 padding-bottom-100">
      <div class="container">
        <div class="row">
          <div class="col-md-12"> 
          
            <!-- Article -->
            <%@ include file="page1.file" %>
         <button type="button" class="btn btn-warning" onclick="javascript:location.href='<%=request.getContextPath()%>/front_end/textmain/addNewText.jsp'">新增文章</button>
            <c:forEach var="textmainVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
            <c:if test="${textmainVO.status==1}">
              <!-- Post Img --> 
              <%
              	if(it.hasNext()){
              		String imgStr = it.next();
              		System.out.println(imgStr);
              		 %>
              		 
              		 
              <div 
	              class="text-image" 
	              style="background-image: url('${pageContext.request.contextPath}<%=imgStr %>')" >
              </div>
           <% }%>
            <FORM METHOD="post" action="<%=request.getContextPath()%>/front_end/textmain/textmain.do">
              <!-- Tittle -->
              
              <div class="post-tittle left">
               <a href="./textmain.do?text_id=${textmainVO.text_id}&action=getOne_For_Display1"  class="tittle">${textmainVO.title}
		       <input type="hidden" name="action" value="getOne_For_Display1">
               <input type="hidden" name="text_id" value="${textmainVO.text_id}">
              	</a>
                <!-- Post Info --> 
                <span><i class="primary-color icon-user"></i>${textmainVO.author_id}</span> <span><i class="primary-color icon-calendar"></i><fmt:formatDate value="${textmainVO.est_time}"
													pattern="yyyy/MM/dd HH:mm" /></span> 
                
                </div>
                </FORM>
              <!-- Post Content -->
              <div class="text-left">
                <p>${textmainVO.content}</p>
                  
                <a href="./textmain.do?text_id=${textmainVO.text_id}&action=getOne_For_Display1"  class="btn">READ MORE</a> </div>
                </c:if>
            </c:forEach>
            </article>
            
            
        
            
           
            
            <!-- Pagination -->
            <ul class="pagination in-center">
              <li><a href="<%=request.getRequestURI()%>?whichPage=<%=whichPage-1%>"><i class="fa fa-angle-left"></i></a></li>
              <li class="active"><a href="<%=request.getRequestURI()%>?whichPage=1">1</a></li>
              <li ><a href="<%=request.getRequestURI()%>?whichPage=2">2</a></li>
              <li><a href="<%=request.getRequestURI()%>?whichPage=<%=whichPage+1%>"><i class="fa fa-angle-right"></i></a></li>
            </ul>
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
</body>
</html>