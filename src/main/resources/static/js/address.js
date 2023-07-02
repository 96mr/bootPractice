const addressForm = $('#address-form');
const btnForm = $("button[name='submit-address']");

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
				$('#detailAddress').text(data.detailAddress);
				$('#add-address-form').text("배송지 변경");
				btnForm.val("update");
				btnForm.text("수정");
			}else{
				$('#postcode').text("배송지를 등록해주세요.");
				$('#add-address-form').text("배송지 등록");
				btnForm.val("insert");
				btnForm.text("추가");
			}
		}
	});
});

$('#daum-address').on("click",function(){
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

            $("input[name='postcode']").val(data.zonecode);
            $("input[name='address']").val(addr);
            $("input[name='detailAddress']").focus();
        }
    }).open();
});

document.getElementById("add-address-form").addEventListener("click", function(){
	if(addressForm.hasClass('d-none')){
		addressForm.removeClass('d-none');
	}else{
		addressForm.addClass('d-none');
	}
});
btnForm.on("click", function(){
	var addressData = {
			address1 : $("input[name='postcode']").val(),
			address2 : $("input[name='address']").val(),
			address3 : $("input[name='detailAddress']").val()
	}
	addressForm[0].reset();
	if(btnForm.val() == 'insert'){
		addressFormAjax(addressData, "/api/address", "POST");
	}else if(btnForm.val() == 'update'){
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
		success : function(data, status) {
			if(status == 'success'){
				$("#postcode").text(data.postcode);
				$("#address").text(data.address);
				$("#detailAddress").text(data.detailAddress);
				if(addressForm.hasClass('d-none')){
					addressForm.removeClass('d-none');
				}else{
					addressForm.addClass('d-none');
				}
			}
		}
	});
}