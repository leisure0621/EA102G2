<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="cart-ship-info">
	<h6>個人信息</h6>
	<ul class="row memberForm">
	
		<!-- First Name -->
		<li class="col-md-4">
		  <label> 姓氏
		    <input type="text" name="first_name" value="${memVO.getFirst_name()}" placeholder="請輸入姓氏...">
		  </label>
		</li>
		
		<!-- Last Name -->
		<li class="col-md-4">
		  <label> 名稱
		    <input type="text" name="last_name" value="${memVO.getLast_name()}" placeholder="請輸入名稱...">
		  </label>
		</li>
		
		<!-- Nick Name -->
		<li class="col-md-4">
		  <label> 暱稱
		    <input type="text" name="nickname" value="${memVO.getNickname()}" placeholder="請輸入暱稱...">
		  </label>
		</li>
		
		<!-- Telephone -->
		<li class="col-md-6">
		  <label> 電話
		    <input type="text" name="tel" value="${memVO.tel}" placeholder="請輸入電話...">
		  </label>
		</li>
		
		<!-- Mobile -->
		<li class="col-md-6">
		  <label> 手機
		    <input type="text" name="mob" value="${memVO.mob}" placeholder="請輸入手機...">
		  </label>
		</li>
		
		<!-- Email -->
		<li class="col-md-12">
		  <label> Email
		    <input type="text" name="email" value="${memVO.email}" readonly>
		  </label>
		</li>
		
		<!-- Bank Account -->
		<li class="col-md-12">
		  <label> 銀行卡號
		    <input type="text" name="bank_account" value="${memVO.bank_account}" placeholder="請輸入銀行卡號...">
		  </label>
		</li>
		
		<!-- Bank Account -->
		<li class="col-md-6">
		  <label> 信用卡號
		    <input type="text" name="credit_card" value="${memVO.credit_card}" placeholder="請輸入信用卡號...">
		  </label>
		</li>
		
		<!-- Bank Account -->
		<li class="col-md-3">
		  <label> 信用卡到期日
		    <input type="text" name="credit_card_expires" placeholder="YYYY-MM...">
		  </label>
		</li>
		
		<!-- Bank Account -->
		<li class="col-md-3">
		  <label> 信用卡安全碼
		    <input type="text" name="credit_card_cvc" placeholder="請輸入安全碼...">
		  </label>
		</li>
		
		<!-- Country -->
		<li class="col-md-4">
		  <label> 縣市
		    <select class="country" name="country">
		    </select>
		  </label>
		</li>
		
		<!-- Country -->
		<li class="col-md-4">
		  <label> 區域
		    <select class="district" name="district">
		    </select>
		  </label>
		</li>
		
		<!-- Bank Account -->
		<li class="col-md-4">
		  <label> 郵區
		    <input type="text" class="zipcode" name="zipcode" value="" placeholder="郵遞區號" readonly>
		  </label>
		</li>
		
		<!-- Address -->
		<li class="col-md-12">
		  <label> 詳細地址
		    <input type="text" class="detail" name="address" value="" placeholder="請輸入詳細地址...">
		  </label>
		</li>
		
		<!-- Password -->
		<li class="col-md-12">
		  <label> 密碼
		    <input type="text" name="password" value="${memVO.getPassword()}" placeholder="請輸入密碼...">
		  </label>
		</li>
		
		
		<!-- Submit -->
		<li class="col-md-4">
		    <div class="btn update">修改</div>
		</li>
		<li class="col-md-4">
		    <div class="btn" id="magic">神奇~~</div>
		</li>
	
	</ul>
</div>