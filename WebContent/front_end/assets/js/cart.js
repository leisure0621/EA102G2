let total = 0;

// 顯示購物車商品
function addtoUserBasket(products, element, imageUrl) {
  if (products) {
    let samPro;
    if(backet.data('total')) {
    	total += parseInt(backet.data('total'));
    }
    // 商品加入購物車
    Object.keys(products).forEach(function (item) {
    	// 修改位置
        samPro = element.find('[data-pro_id="'+products[item].pro_id+'"]');
        console.log('[data-pro_id="'+products[item].pro_id+'"]')
        // 修改數量
        let qty;
        console.log(samPro.data('quality'))
        if(!samPro.data('quality')) {
        	qty = products[item].quality;
        } else {
        	qty = parseInt(samPro.data('quality')) + products[item].quality;
        }
        // 修改金額
        let price = products[item].price;
        total += products[item].price * products[item].quality;
    	
    	backet.find('.total')
		      .data('total',  + total)
		      .text('總額：$' + total);
    	
        // 數值
        samPro.data('quality', qty);
        samPro.find('.qty').text("數量: "+ qty);
        samPro.data('price', products[item].price);
        samPro.find('.price').text("＄"+ price);
		// 非重覆商品
		if(!inCart(products[item].pro_id)) {
			backet.prepend(
              '<li cat_id="' + products[item].cat_id + '"' +
              'data-pro_id="' + products[item].pro_id + '"' +
              'data-pro_name="' + products[item].pro_name + '"' +
              'data-price="' + products[item].price + '"' +
              'data-quality="' + qty + '"' +
              '>' +
              '<div class="media-left">' +
              '<div class="cart-img">' +
              '<a>' +
              '<img class="media-object img-responsive" src="' + imageUrl + products[item].pro_id + '">' +
              '</a>' +
              '</div>' +
              '</div>' +
              '<div class="media-body">' +
              '<h6 class="media-heading">' + products[item].pro_name + '</h6>' +
              '<span class="price">＄' + price + '</span>' +
              '<span class="qty">數量: ' + qty + '</span>' +
              '</div>' +
              '</li>'
          );
		}
    });
    // 購物車 總額與結帳鈕
    if (!backet.find('h5').length) {
      backet.append(
        '<li>' +
        '<h5 class="text-center total" data-total="' + total + '">總額：$' + total + '</h5>' +
        '</li>' +
        '<li class="margin-0">' +
        '<div class="row">' +
        '<div class="col-xs-12">' +
        '<a class="btn cart-checkout">結帳</a>' +
        '</div>' +
        '</div>' +
        '</li>'
      );
    }
  }
  // 下拉選單有值 顯示
  if (element.find('[cat_id]').length > 0) {
    element.css("height", "300px").css("overflow-y", "auto").css("padding", "20px");
    $('.icon-basket-loaded').addClass('active');
  }
  else {
    element.css("height", 0).css("overflow", "hidden").css("padding", 0);
  }
}

// 檢查重覆商品
function inCart(pro_id){
	let count = 0;
	backet.find('[cat_id]').each(function(){
        // 搜尋 是重覆商品
        if($(this).data('pro_id') === pro_id) {
        	count = 1;
            // 修改數量
            return;
        } 
    });
	return count;
}

// 添加購物車列表項
function prependCartList(element, item, list, imageUrl) {
	element.prepend(
      '<li cat_id="' + list[item].cat_id + '"' +
      'data-pro_id="' + list[item].pro_id + '"' +
      'data-pro_name="' + list[item].pro_name + '"' +
      'data-price="' + list[item].price + '"' +
      'data-quality="' + (list[item].quality ? list[item].quality : 1) + '"' +
      '>' +
      '<div class="media-left">' +
      '<div class="cart-img">' +
      '<a>' +
      '<img class="media-object img-responsive" src="' + imageUrl + list[item].pro_id + '">' +
      '</a>' +
      '</div>' +
      '</div>' +
      '<div class="media-body">' +
      '<h6 class="media-heading">' + list[item].pro_name + '</h6>' +
      '<span class="price">＄' + list[item].price + '</span>' +
      '<span class="qty">數量: ' + (list[item].quality ? list[item].quality : 1) + '</span>' +
      '</div>' +
      '</li>'
    );
}

// 加入購物車 重新計算購物車現有商品
function addToCar(element) {
  let path = {};
  path.pathname = window.location.pathname;
  path.memberInformation = '/' + path.pathname.split('/')[1] + '/front_end/membercenter/memberInformation.jsp';
  path.cartUrl = '/' + path.pathname.split('/')[1] + '/b2cso/b2cso.do';
  productList = [];

  element.find('[cat_id]').each(function () {
    if ($(this).data("pro_id")) {
      productList.push({
        pro_id: $(this).data("pro_id"),
        cat_id: $(this).data("cat_id"),
        pro_name: $(this).data("pro_name"),
        price: $(this).data("price"),
        cat_id: $(this).data("cat_id"),
        quality: $(this).data("quality")
      });
    }
  });
  console.log("addToCar", productList)
  // 加入購物車
  fetch(path.cartUrl, {
    body: JSON.stringify({
      productList: productList,
      action: "addToCar"
    }),
    method: 'POST'
  }).then(function (response) {
    if (!response.ok) throw new Error(response.statusText);
    return response.json();
  }).then(function (json) {
    // 新增失敗
    if (json.err) {
      json = json.err;
    }
    // 新增成功
    else if (json.data) {
      json = json.data;
    }
    $('.assemble-btn').addClass('add');
    return json;
  });
}

// 發送資料 成立訂單
function sendData(element, action, type, delivery, del_address, pay_via) {
  let path = {};
  path.pathname = window.location.pathname;
  path.memberInformation = '/' + path.pathname.split('/')[1] + '/front_end/membercenter/orderMain.jsp';
  path.cartUrl = '/' + path.pathname.split('/')[1] + '/b2cso/b2cso.do';

  // 取得商品列表
  productList = [];
  element.find('[cat_id]').each(function () {
    if ($(this).data("pro_id")) {
      productList.push({
        pro_id: $(this).data("pro_id"),
        cat_id: $(this).data("cat_id"),
        pro_name: $(this).data("pro_name"),
        price: parseInt($(this).data("price")) * parseInt($(this).data("quality")),
        cat_id: $(this).data("cat_id"),
        quality: $(this).data("quality")
      });
    }
  });

  // 組成完整資訊
  let data = {
    productList: productList,
    total: element.find('.total').data('total'),
    mem_id: element.data('mem_id'),
    mem_name: element.data('mem_name'),
    type: type,
    delivery: delivery,
    del_address: del_address,
    pay_via: pay_via,
    action: action
  };
  // 有會員 新增訂單
  if (data.mem_id) {
    // 新增訂單
    fetch(path.cartUrl, {
      body: JSON.stringify(data),
      method: 'POST'
    }).then(function (response) {
      if (!response.ok) throw new Error(response.statusText);
      return response.json();
    }).then(function (json) {
      console.log("json", json);
      // 新增失敗
      if (json.err) {
        json = json.err;
        alert(json);
      }
      // 新增成功
      else if (json.data) {
        json = json.data;
        alert(json);
        // 進入會員中心
        location.href = path.memberInformation;
      }
      $('.assemble-btn').addClass('add');
      return json;
    });
  } else {
    alert('如需組裝請先登入會員');
    location.href = path.memberInformation;
  }
}

// 購物車
$('.user-basket > .dropdown-menu').on('click', '.cart-checkout', function () {
  sendData(backet, 'addOrder', 1, 3, "無", 2);
});