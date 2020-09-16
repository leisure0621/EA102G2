<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="M_Adnan">
<title>個人資訊</title>

<%@ include file="/front_end/headerSection.jsp" %>

<style>
ul.login-with {
    padding-top: 19px;
}
.btn:focus {
    color: white;
}
.active a {
    color: var(--headerBlackFontColor);
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
    <section class="member-center_information margin-top-23 margin-bottom-23 padding-bottom-23 padding-top-23">
      <div class="container"> 
        
        <!-- 會員中心功能 -->
        <div class="col-sm-3 leftSlide">
        	<%@ include file="/front_end/membercenterLeftMenu.jsp" %>
        </div>
        
        <!-- 頁面資料 -->
        <div class="col-sm-9 rightSlide shopping-cart">
       		<%@ include file="/front_end/membercenter/informationForm.jsp" %>
        </div>
        
      </div>
    </section>

  </div>
  
<%@ include file="/front_end/footerMenu.jsp" %>
  
</div>

<%@ include file="/front_end/footerSection.jsp" %>
<!-- 地址三級 -->
<script src="<%=request.getContextPath()%>/front_end/assets/js/dk-tw-citySelector.js"></script> 

<script>
$( document ).ready(function() {
	let credit_card = {};
	let add = {};
	let data = {};
	
	initializeForm();
	
	// 修改資料
    $('.update').click(function(){
        let emailReg = '(.)@(.)';
        let numReg = '([0-9])';
        let name, element;
        let credit_card = $('.memberForm [name="credit_card"]').val();
        if (!$('[name="email"]').val().match(emailReg)) {
            alert("電子郵件地址必須包括 ( @ 和 . )");
            return;
        }
        if (!$('[name="first_name"]').val()) {
            alert("姓氏不能為空");
            return;
        }
        if (!$('[name="last_name"]').val()) {
            alert("名稱不能為空");
            return;
        }
    	if ($('[name="tel"]').val() && !$('[name="tel"]').val().match(numReg)) {
            alert("電話號需為數字");
            return;
        }
       	if ($('[name="mob"]').val() && !$('[name="mob"]').val().match(numReg)) {
            alert("手機號需為數字");
            return;
        }
        if ($('[name="bank_account"]').val() && !$('[name="bank_account"]').val().match(numReg)) {
        	alert("銀行卡號需為數字");
            return;
        }
        if ($('[name="credit_card"]').val() && !$('[name="credit_card"]').val().match(numReg)) {
            alert("信用卡號需為數字");
            return;
        }
       	if ($('[name="credit_card_cvc"]').val() && !$('[name="credit_card_cvc"]').val().match(numReg)) {
            alert("安全碼需為數字");
            return;
        }
        if (!$('[name="password"]').val()) {
            alert("密碼不能為空");
            return;
        }
        
        data.authority = '${memVO.getAuthority()}';
        
        // 添加 修改資料
        $(".memberForm [name]").each(function(){
            name = $(this).attr('name');
            element = $(this);
            
            if(!credit_card && name === 'credit_card_expires') {
            	data.credit_card_expires = "1990-01-01";
            } else if(credit_card && name === 'credit_card_expires') {
            	data.credit_card_expires = $('.memberForm [name="credit_card_expires"]').val()+"-01";
            } else if(!credit_card && name === 'credit_card_cvc') {
            	data.credit_card_cvc = 0;
            } else if(name === 'country') {
            	data.address = $('.memberForm [name="country"]').val();
            } else if (name === 'district') {
            	data.address += $('.memberForm [name="district"]').val();
            } else if (name === 'zipcode') {
            	data.address = $('.memberForm [name="zipcode"]').val() + data.address;
            } else if (name === 'address') {
            	data.address += $('.memberForm [name="address"]').val();
            } else if (name === 'bank_account' && data.authority != 0) {
            	data.authority = ($('.memberForm [name="bank_account"]').val()) ? '2' : '1';
            	data.bank_account = $('.memberForm [name="bank_account"]').val();
            } else {
                data[name] = element.val();
            }
        });
        data.mem_id = '${memVO.getMem_id()}';
        data.action = "update";
        console.log(data)
        // 發送請求
        $.ajax({
            url: '<%=request.getContextPath()%>/mem/mem.do',
            type : 'post',
            data : data,
            datatype: 'json',
            success : function(res) {
            	res = JSON.parse(res);
            	if(res.data) alert(res.data);
            	else alert(res.err);
            }
        });
    });
    
});

//初始化
function initializeForm() {
	// 信用卡
	if('${memVO.getCredit_card()}'){
		$('.memberForm [name="credit_card"]').val('${memVO.getCredit_card()}');
		$('.memberForm [name="credit_card_expires"]').val('${memVO.getCredit_card_expires()}' ? '${memVO.getCredit_card_expires()}'.split('-')[0]+'-'+'${memVO.getCredit_card_expires()}'.split('-')[1] : '');
		$('.memberForm [name="credit_card_cvc"]').val(('${memVO.getCredit_card_cvc()}' !== '0' && '${memVO.getCredit_card_cvc()}') ? '${memVO.getCredit_card_cvc()}' : '');
	}
	// 地址
	$('.memberForm [name="address"], [name="address"]').val('');
    $('.memberForm [name="country"]').data('selected', '');
    $('.memberForm [name="district"]').data( 'selected', '');
    $('.memberForm [name="country"] option').remove();
    $('.memberForm [name="district"] option').remove();
    $('.memberForm').dk_tw_citySelector('.country', '.district', '.zipcode');
	if('${memVO.getAddress()}') {
		add = {};
		add.zip = '${memVO.getAddress()}'.slice(0, 3);
		add.blurryCountry = '${memVO.getAddress()}'.slice(3, 6);
    	// 選城市
    	add.country = $('[value^="'+add.blurryCountry+'"]').val();
    	$('.memberForm [name="country"]').data('selected', add.country);
    	$('.memberForm').dk_tw_citySelector('.country', '.district', '.zipcode');
    	// 選區
    	$('.memberForm [name="country"] option').remove(); // 清空多餘城市
    	add.district = $('[data-zip="'+add.zip+'"]').val();
    	$('.memberForm [name="district"] option').remove();// 清空多餘區域
    	add.detail = '${memVO.getAddress()}'.slice(add.zip.length+add.district.length+add.country.length, '${memVO.getAddress()}'.length);
    	$('.memberForm [name="district"]').data( 'selected', add.district);
    	$('.memberForm').dk_tw_citySelector('.country', '.district', '.zipcode');
    	// 填詳細地址
    	$('.memberForm [name="address"]').val(add.detail);
	}
	
}
$('#magic').click(function(){
	$('[name="first_name"]').val("吳");
	$('[name="last_name"]').val("永痣");
	$('[name="nickname"]').val("GOD");
	$('.detail').val('大平街七段550號');
	$('[name="password"]').val("1");
});
</script>

</body>
</html>