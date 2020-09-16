<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="M_Adnan" />
    <title>隱私權聲明</title>

    <%@ include file="/front_end/headerSection.jsp" %>
    
    <style>
    h4 {
	    margin-bottom: 25px;
	    margin-top: 25px;
	}
    </style>
  </head>
  <body>
    <%@ include file="/front_end/headerMenu.jsp" %>

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
          class="margin-bottom-50"
        >
          <div class="container">
            <!-- Payments Steps -->
            <div class="privacy-statement">
              <!-- SHOPPING INFORMATION -->
              <div class="privacy-statement-info">
                <div class="row">
                  <!-- ESTIMATE SHIPPING & TAX -->
                  <div class="col-sm-12">
                    <%@ include file="/front_end/ftcontent/privacyStatement/privacy.file" %>
                  </div>
                  <!-- ESTIMATE SHIPPING & TAX -->
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
      <%@ include file="/front_end/footerMenu.jsp" %>
    </div>

    <%@ include file="/front_end/footerSection.jsp" %>
  </body>
</html>
