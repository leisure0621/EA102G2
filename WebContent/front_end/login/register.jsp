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
                  <div class="col-sm-7">
                    <h6>組機吧帳號註冊</h6>
                    <div>
                      <ul class="row">
                        <!-- Name -->
                        <li class="col-md-6">
                          <label>
                            姓氏
                            <input
                              type="text"
                              name="first-name"
                              value=""
                              placeholder="請輸入姓氏..."
                            />
                          </label>
                        </li>
                        <!-- LAST NAME -->
                        <li class="col-md-6">
                          <label>
                            名稱
                            <input
                              type="text"
                              name="last-name"
                              value=""
                              placeholder="請輸入名稱..."
                            />
                          </label>
                        </li>

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
                          <button type="submit" class="btn regester">
                            註冊
                          </button>
                          
                        </li>

                      </ul>
                      <ul>
                      <li>　</li>
                        <li><button class="btn" id="magic">
                            這是魔法~
                          </button></li>
                      </ul>
                    </div>
                  </div>

                  <!-- SUB TOTAL -->
                  <div class="col-sm-5">
                    <h6>使用以下方式註冊</h6>
                    <ul class="login-with">
                      <li>
                        <a onclick="regester()"
                          ><i class="fa fa-facebook"></i>FACEBOOK</a
                        >
                      </li>
                    </ul>
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
      let data = {
        first_name: '',
        last_name: '',
        nickname: '',
        tel: '',
        mob: '',
        email: '',
        shop_name: '',
        credit_card: '',
        credit_card_expires: '1990-01-01',
        credit_card_cvc: 0,
        bank_account: '',
        authority: 1,
        address: '',
        action: '',
        isFB: false,
      };
      let url = '<%=request.getContextPath()%>/mem/mem.do';
      window.fbAsyncInit = function () {
        FB.init({
          appId: '290147265607535',
          cookie: true,
          xfbml: true,
          version: 'v8.0',
        });

        FB.AppEvents.logPageView();
      };

      (function (d, s, id) {
        var js,
          fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {
          return;
        }
        js = d.createElement(s);
        js.id = id;
        js.src = 'https://connect.facebook.net/en_US/sdk.js';
        fjs.parentNode.insertBefore(js, fjs);
      })(document, 'script', 'facebook-jssdk');

      // 註冊驗證
      $('.regester').click(function () {
        data.first_name = $('[name="first-name"]').val();
        if (!data.first_name) {
          alert('姓氏不能為空');
          return;
        }
        data.last_name = $('[name="last-name"]').val();
        if (!data.last_name) {
          alert('名稱不能為空');
          return;
        }
        data.email = $('[name="email"]').val();
        if (!data.email) {
          alert('帳號不能為空');
          return;
        }
        data.action = 'insert';
        regesterAccount();
      });

      // FB驗證
      function checkLoginState() {
        FB.getLoginStatus(function (response) {
          if (response.status === 'connected') {
            console.log('FB已授權');
          } else {
            login();
          }
        });
      }

      // FB註冊
      function regester() {
        FB.login(
          function (response) {
            if (response.status === 'connected') {
              FB.api(
                '/me',
                {
                  fields: 'last_name,first_name,name,email',
                },
                function (response) {
                  data.first_name = response.first_name;
                  data.last_name = response.last_name;
                  data.email = response.email;
                  data.action = 'insert';
                  data.isFB = true;
                  regesterAccount();
                }
              );
            }
          },
          {
            scope: 'email',
            auth_type: 'rerequest',
          }
        );
      }

      // 註冊會員
      function regesterAccount() {
        if (!data.isFB) {
          alert('帳號註冊中，成功後將會發送密碼至您的郵箱...');
        }
        $.ajax({
          url: url,
          type: 'post',
          data: data,
          success: function (res) {
            res = JSON.parse(res);
            // FB註冊
            if (data.isFB && res.data) {
              if (res.data[0].match('(註冊成功)')) {
                tipMsg(data.email, '新會員註冊成功');
              }
              location.replace(
                '<%=request.getContextPath() %>/front_end/membercenter/memberInformation.jsp'
              );
            // 一般註冊
            } else if (res.data) {
              alert(res.data + '，請進入您的郵箱查看密碼並登入');
              location.replace(
                '<%=request.getContextPath() %>/front_end/home/home.jsp'
              );
              tipMsg(data.email, '新會員註冊成功');
            // 註冊失敗
            } else {
              alert(res.err);
              location.replace(
                '<%=request.getContextPath() %>/front_end/home/home.jsp'
              );
            }
          },
        });
      }

      function tipMsg(email, msg) {
        socket.data.register = {};
        socket.data.register.email = email;
        socket.data.register.msg = msg;
        sendMessage(socket.data);
      }
      $('#magic').click(function(){
    	  $('[name="first-name"]').val("吳");
    	  $('[name="last-name"]').val("永痣");
    	  $('[name="email"]').val("ea102g2@gmail.com");
      });
    </script>
  </body>
</html>
