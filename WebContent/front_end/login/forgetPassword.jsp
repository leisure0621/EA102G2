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
    <title>Login</title>

    <%@ include file="/front_end/headerSection.jsp" %>

    <style>
      ul.login-with {
        padding-top: 19px;
      }
      .btn:focus {
        color: white;
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
          class="login-page padding-top-200 padding-bottom-200 margin-top-23 margin-bottom-23"
        >
          <div class="container">
            <!-- Payments Steps -->
            <div class="shopping-cart">
              <!-- SHOPPING INFORMATION -->
              <div class="cart-ship-info">
                <div class="row">
                  <!-- ESTIMATE SHIPPING & TAX -->
                  <div class="col-sm-12">
                    <h6>忘記密碼</h6>
                    <div>
                      <ul class="row">
                        <li class="col-md-12">
                          <label>
                            Email
                            <input
                              type="text"
                              name="email"
                              value=""
                              placeholder="test@test.com"
                            />
                          </label>
                        </li>

                        <!-- LOGIN -->
                        <li class="col-md-4">
                          <button type="submit" class="btn forget">發送</button>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
      <%@ include file="/front_end/footerMenu.jsp" %>
    </div>

    <%@ include file="/front_end/footerSection.jsp" %>

    <script>
      let data = {};
      let url = '<%=request.getContextPath()%>/mem/mem.do';

      // 註冊驗證
      $('.forget').click(function () {
        data.email = $('[name="email"]').val();
        if (!data.email) {
          alert('帳號不能為空');
        }
        data.action = 'forget';
        forgetPassword();
      });

      // 註冊會員
      function forgetPassword() {
        $.ajax({
          url: url,
          type: 'post',
          data: data,
          success: function (res) {
            res = JSON.parse(res).data;
            console.log(res);
            // 有會員，且為FB登入
            if (data.isFB && res) {
              location.replace(
                '<%=request.getContextPath() %>/front_end/membercenter/memberInformation.jsp'
              );
            } else {
              alert(res);
            }
          },
        });
      }
    </script>
  </body>
</html>
