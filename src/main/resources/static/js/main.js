var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');

$(document).ready(function(){
	getCategoryNavigation();
});

function getCategoryNavigation(){
	const categoryNav = $('#categoryNav');
	$.ajax({
		url:"/api/categories",
		type : "GET",
		contentType : "application/json",
		beforeSend: function(xhr){
	        xhr.setRequestHeader(header, token);
	    },
		success : function(data, status) {
			var out = "";
			if(status == 'success'){		
				$.each(data, function(idx, item){
					out += "<li class='nav-item dropdown active ml-4 mr-4'>";
					out += "<a class='nav-link h5' href='/products/"+ item.name +"'>"+item.name+"</a>";
					out += "<ul class='list-unstyled text-center dropdown-menu'>";
					$.each(item.children, function(i, child){
						out += "<li class='nav-item active'>";
						out += "<a class='nav-link dropdown-item' href='/products/"+ child.name +"'>"+child.name+"</a></li>";
					});
					out += "</ul></li>";
					
				});
				categoryNav.html(out);
			}
		}			
	});
}

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

            addressForm.elements['postcode'].value= data.zonecode;
            addressForm.elements['address'].value = addr;
            addressForm.elements['detail-address'].focus();
        }
    }).open();
});