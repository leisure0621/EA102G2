$('.logout') &&
  $('.logout').click(function () {
    let path = {};
    path.pathname = window.location.pathname;
    path.url = '/' + path.pathname.split('/')[1] + '/emp/emp.do';
    path.replaceUrl =
      '/' +
      path.pathname.split('/')[1] +
      '/' +
      path.pathname.split('/')[2] +
      '/home/home.jsp';

    let data = {
      action: 'logout',
    };

    $.ajax({
      url: path.url,
      type: 'post',
      data: data,
      datatype: 'json',
      success: function (res) {
        location.replace(path.replaceUrl);
      },
    });
  });
