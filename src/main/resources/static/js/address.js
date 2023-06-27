const addressForm = document.forms['address-form'];
const btnForm = addressForm.elements['submit-address'];

$(document).ready(function(){
	$.ajax({
		url : "/api/address",
		contentType : "application/json",
		beforeSend: function(xhr){
	        xhr.setRequestHeader(header, token);
	    },
		success : function(data, status) {
			if(data.postcode != null){
				$('#postcode').text(data.postcode);
				$('#address').text(data.address);
				$('#detail-address').text(data.detail_address);
				$('#add-address-form').text("배송지 변경");
				btnForm.value = "update";
				btnForm.innerText = "수정";
			}else{
				$('#postcode').text("배송지를 등록해주세요.");
				$('#add-address-form').text("배송지 등록");
				btnForm.value = "insert";
				btnForm.innerText = "추가";
			}
		}
	});
});

document.getElementById("add-address-form").addEventListener("click", function(){
	let addressFormClass = addressForm.classList;
	if(addressFormClass.contains('d-none')){
		addressFormClass.remove('d-none');
	}else{
		addressFormClass.add('d-none');
	}
});
btnForm.addEventListener("click", function(){
	var addressData = {
			address1 : addressForm.elements['postcode'].value,
			address2 : addressForm.elements['address'].value,
			address3 : addressForm.elements['detail-address'].value
	}
	addressForm.reset();
	if(btnForm.value == 'insert'){
		addressFormAjax(addressData, "/api/address", "POST");
	}else if(btnForm.value == 'update'){
		addressFormAjax(addressData, "/api/address", "PUT");
	}
});
function addressFormAjax(data, url, type){
	$.ajax({
		data : JSON.stringify(data),
		type : type,
		url : url,
		contentType : "application/json",
		beforeSend: function(xhr){
	        xhr.setRequestHeader(header, token);
	    },
		success : function(data) {
			document.getElementById('postcode').innerText = data.postcode;
			document.getElementById('address').innerText = data.address;
			document.getElementById('detail-address').innerText = data.detail_address;
		}
	});
}