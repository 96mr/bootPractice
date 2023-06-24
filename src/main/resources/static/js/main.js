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