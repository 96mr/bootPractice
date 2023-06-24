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
				document.getElementById('postcode').innerText = data.postcode;
				document.getElementById('address').innerText = data.address;
				document.getElementById('detail-address').innerText = data.detail_address;
				document.getElementById("add-address-form").innerText = "배송지 변경";
				btnForm.value = "update";
				btnForm.innerText = "수정";
			}else{
				document.getElementById('postcode').innerText = "배송지를 추가해주세요";
				document.getElementById("add-address-form").innerText = "배송지 추가";
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
document.getElementById("daum-address").addEventListener("click", function(){
	new daum.Postcode({
        oncomplete: function(data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            addr = data.roadAddress;

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraAddr !== ''){
                extraAddr = ' (' + extraAddr + ')';
            }
            // 조합된 참고항목을 해당 필드에 넣는다.
            addr += extraAddr;

            addressForm.elements['postcode'].value= data.zonecode;
            addressForm.elements['address'].value = addr;
            addressForm.elements['detail-address'].focus();
        }
    }).open();
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