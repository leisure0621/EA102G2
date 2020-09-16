$(document).ready(categoryActive);


function categoryActive(){
	var hi = document.getElementById("hi");
	var cateli = document.getElementById("cateli");
	for(var i = 0 ; i < cateli.childNodes.length ; i++){
		
		if(hi.value == cateli.childNodes[i].innerText){
			console.log(i);
			console.log(cateli.childNodes[i].innerText);
			var a = cateli.childNodes[i].childNodes[1].childNodes[3];
			console.log(a);
			a.className = "active";
			console.log("success")
		}
	}
}


$(document).ready(buyIt);
function buyIt(){
	console.log('是在哈囉');
	var buyIt =  $("#buyIt");
	buyIt.addEventListener("onclick",function(){
		$("#getOneForOrder").submit();
	});
	
}

function sub(obj) {
	var ff = obj.parentNode.parentNode//抓取父元素的父元素
	ff.submit();
}

function subCategory(obj){
	var ff = obj.parentNode;
	ff.submit();
}

