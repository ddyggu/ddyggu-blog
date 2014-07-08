/**
 * 
 */
$(document).ready(function(){
	$('#deactivate').submit(function() {
		if($('#password').val()=='') {
			alert("비밀번호를 입력하여 주십시오");
			return false;
		}
		if(!confirm("정말로 탈퇴하시겠습니까?")) {
			return false;
		}	
		
	});
});