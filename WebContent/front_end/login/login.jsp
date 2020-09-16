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
      .forgetPassword {
        line-height: 46px;
      }
      .forgetPassword:hover {
        color: var(--headerBlackFontColor);
      }
      .shopping-cart li a.forgetPassword {
        color: #272727;
      }
      .shopping-cart li a.forgetPassword:hover, 
      .shopping-cart li a.forgetPassword:focus {
        color: #ffe115;
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
                    <h6>組機吧帳號登入</h6>
                    <div>
                      <ul class="row">
                        <!-- Email -->
                        <li class="col-md-12">
                          <label>
                            Email
                            <input
                              type="email"
                              name="email"
                              value=""
                              placeholder="test@test.com"
                            />
                          </label>
                        </li>

                        <!-- Password -->
                        <li class="col-md-12">
                          <label>
                            密碼
                            <input
                              type="password"
                              name="password"
                              value=""
                              placeholder="請輸入密碼..."
                            />
                          </label>
                        </li>

                        <!-- Login -->
                        <li class="col-md-4">
                          <button type="submit" class="btn login">登入</button>
                          
                        </li>
                        

                        <!-- Forget Password -->
                        <li class="col-md-4">
                        
                          <a
                            class="forgetPassword"
                            href="<%=request.getContextPath() %>/front_end/login/forgetPassword.jsp"
                            >忘記密碼 ?</a
                          >
                          
                        </li>

                      </ul>
                      <ul>
                        <li>　</li>
                        <li><button id="magic" class="btn">Magic~~~~</button></li>
                        <li>　</li>
                        <li><button id="222" class="btn">召喚222~</button></li>
                        <li>　</li>
                        <li><button id="333" class="btn">有請333~</button></li>
                      </ul>
                    </div>
                  </div>
					
                  <!-- SUB TOTAL -->
                  <div class="col-sm-5">
                    <h6>使用以下方式登入</h6>
                    <ul class="login-with">
                      <li>
                        <a onclick="login()"
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

      // 登入驗證
      $('.login').click(function () {
        data.email = $('[name="email"]').val();
        if (!data.email) {
          alert('帳號不能為空');
          return;
        }
        data.password = $('[name="password"]').val();
        if (!data.password) {
          alert('密碼不能為空');
          return;
        }
        data.action = 'login';
        loginAccount();
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

      // FB登入
      function login() {
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
                  loginAccount();
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

      // 登入會員
      function loginAccount() {
        $.ajax({
          url: url,
          type: 'post',
          data: data,
          success: function (res) {
       	    res = JSON.parse(res);
       	    // 登入失敗
       	    if(res.err) {
       	    	alert(res.err);
       	    	return;
       	    // 登入成功
       	    } else {
       	    	alert(res.data);
       	    }
        	// 成功 跳轉指定頁面
        	if('${forword_page}') {
        		location.replace('${forword_page}');
        	}
            // 成功 跳轉至前個歷史頁面 並清除堆棧
            else if (document.referrer) {
            	location.replace(document.referrer);
            }
            // 成功 跳轉至會員中心頁面 並清除堆棧
            else {
            	location.replace(
                  '<%=request.getContextPath() %>/front_end/membercenter/memberInformation.jsp'
                );
            }
          },
        });
        // 參考: https://www.zhangxinxu.com/wordpress/2017/02/js-page-url-document-referrer/
      }
      $('#magic').click(function (){
    	  $('[name="email"]').val("ea102g2@gmail.com");
      });
      $('#222').click(function (){
    	  $('[name="email"]').val("222@222.222");
    	  $('[name="password"]').val("222@222.222");
      });
      $('#333').click(function (){
    	  $('[name="email"]').val("333@333.333");
    	  $('[name="password"]').val("333@333.333");
      });
    </script>
  </body>
</html>
