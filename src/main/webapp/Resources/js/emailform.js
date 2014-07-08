/**
 * 
 */

$(document).ready(function() {
	
	$('#emailCheck').submit(function(event) {
		if($('#email').val()=='') {
			alert("이메일 주소를 입력하여 주십시오");
			return false;
		}
		
		$('#wait').css('display','block');
		var emailform = $('#emailCheck').serializeArray();
		var actionUrl = $('#emailCheck').attr('action');
		
		$.ajax({
			url : actionUrl ,
			type : "POST",
			data : emailform,
			success : function(data, status, xhr) {
				 alert("메일이 발송되었습니다.");
				 location.href="/login";
			},
			error : function(response, status, error) {
				$('#wait').css('display','none');
				var msg = $.parseJSON(response.responseText);
				for(var key in msg) {
		  			alert(msg[key]);
		  		}
			}
		});
		event.preventDefault();
	});
});