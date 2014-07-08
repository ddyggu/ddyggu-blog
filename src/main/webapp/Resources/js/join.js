/**
 *  join.vm - Jquery common function  */

$(document).ajaxStart(function() {
	$('#loader').show();
	}).ajaxStop(function() {
	$("#loader").hide();
});

//백스페이스 제한
$(document).on('keydown', function(event) {
	var doPrevent = false;
	if(event.keyCode === 8) {
		var d = event.srcElement || event.target;
		if(d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD' || d.tagName.toUpperCase() === 'TEXTAREA')) {
			doPrevent = d.readOnly || d.disabled;
		} 
		else {
			doPrevent = true;
		}
	}
	if(doPrevent) {
		event.preventDefault();
	}
});

function CallPostAjax(url, data, check_Object, Label_Id, success_message) {
	
	var check = check_Object;
	
	$.ajax({
		url : url,
		type : "POST",
		data : JSON.stringify(data),
		contentType : "application/json; charset=UTF-8",
		dataType : "json",
	  	success : function(data, status, xhr) {
	  		check.isCheck = true;
	  		if(Label_Id !== undefined && success_message !== undefined) {
	  			$("#"+Label_Id).html(success_message).css("color", "#1aabff");
	  		}
	  	},
	  	error : function(response, status, error) {
	  		var msg = $.parseJSON(response.responseText);
	  		for(var key in msg) {
	  			if(key == 'Exception') { location.href="/error"; }
	  			check.isCheck = false;
	  			check.message = msg[key];
	  			if(Label_Id !== undefined && success_message !== undefined) {
	  				$("#"+Label_Id).html(msg[key]).css("color", "red");
	  			}
	  			
	  		}
	  	}
	});
	
	return check;
}


$(document).ready(function() {
	
	$("#BirthYear").attr("value","0");
	$("#BirthMonth").attr("value","0");
	$("#BirthDay").attr("value","0");
	
	
	try {
		addInputHandler({ input : $("#id"), dataType:"AN", maxlength:20 });
		addInputHandler({ input : $("#email"), dataType:"ANS", maxlength:20 });
	} catch(e) {
		console.log(e);
	}
	
	/* 필수 입력값 검증결과 오브젝트 ({ isCheck : "검증 통과여부" , message : "검증 실패시 원인 메시지"}) */
	var idCheck = { isCheck : false, message : null };
	var passCheck = { isCheck : false, message : null };
	var nickCheck = { isCheck : false, message : null };
	var emailCheck = {isCheck : false, message : null };
	
	// 수정페이지 판단여부
	var modifyPage = false;
	if($("#id").is("[readOnly]")) { modifyPage = true; }
	
	// FireFox, Opera용 강제 keyup 이벤트
	if(modifyPage == false) { new Observe(document.getElementById('id')); };
	new Observe(document.getElementById("nickName"));	
	
	// 아이디 조건 확인 이벤트
	$("#id").bind("keyup", function(event, ui) {
		// 수정화면에선 작동중지
		if($("#id").is("[readOnly]")) { return false; }
		 
		var id = { id : $("#id").val() };
		 idCheck = CallPostAjax("/idCheck", id, idCheck, "idLabel", "사용가능한 아이디입니다.");
	});
	 
	
	// 비밀번호 조건 확인 이벤트
	$("#pwd").on("keyup", function() {
		 var pwd = $("#pwd").val();
		 var PwdPattern = /(?=.*\d)(?!.*\s)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&&*\(\)\|\\\+=\-\_\{\}\[\]\"\'\:\;\<\,\>\.\?\/]).{6,20}/g;
		 if(!PwdPattern.test(pwd)) {
			 var message = "영문 대문자,소문자,숫자,특수기호를 조합하여 6~20자 입력하여 주십시오";
			 $("#pwdLabel").html(message).css("color","red");
			 passCheck.isCheck = false;
			 passCheck.message = message;
		 } else {
			 $("#pwdLabel").html("사용가능한 비밀번호입니다.").css("color", "#1aabff");
			 passCheck.isCheck = true;
		 }
	});
	
	// 비밀번호 일치여부 확인 이벤트
	$("#confirmPwd").on('input', function() {
		if($("#pwd").val() == $("#confirmPwd").val()) {
			$("#pwdLabel").html("비밀번호 일치").css("color","#1aabff");
		} else {
			$("#pwdLabel").html("비밀번호 불일치").css("color","red");
		}
	});
	
	// 비밀번호 확인 이벤트
	$("#pwd").on("blur", function() {
		if($("#pwd").val() != $("#confirmPwd").val()) { $("#pwdLabel").html("비밀번호를 확인하여 주십시오").css("color","red"); }
	});
	
	
	// 비밀번호 확인 이벤트
	$("#confirmPwd").on("blur", function() {
		if(passCheck.isCheck == false) { $("#pwdLabel").html(passCheck.message).css("color","red"); }
		if($("#pwd").val() != $("#confirmPwd").val()) { $("#pwdLabel").html("비밀번호를 확인하여 주십시오").css("color","red"); }
		if(passCheck.isCheck == true && ($("#pwd").val() == $("#confirmPwd").val())) {
			$("#pwdLabel").html("비밀번호 일치, 사용가능").css("color","#1aabff");
		}
	});
	
	// 입력 제한 (글자 수) 이벤트
	$("#id, #pwd, #confirmPwd").on("input", function() {
		if($(this).val().length > 20) {
			alert("20자 이상 입력할 수 없습니다.");
			var inputVal = $(this).val();
			$(this).val(inputVal.substr(0,20));
		}
	});
	$("#email").on("input", function() {
		if($(this).val().length > 100) {
			alert("100자 이상 입력할 수 없습니다.");
			var inputVal = $(this).val();
			$(this).val(inputVal.substr(0,100));
		}
	});
	
	var nickObj = { id : null, nickName : null } ;
	
	// 닉네임 조건 확인 이벤트
	$("#nickName").bind("keyup", function(event, ui) {
		// 가입페이지인 경우 입력받은 닉네임 정보만 보낸다.
		 nickObj.nickName = $("#nickName").val();
		 // 수정페이지인 경우 아이디 정보도 함께 보낸다.
		 if(modifyPage == true) { nickObj.id = $("#id").val(); }
		 nickCheck = CallPostAjax("/nickCheck", nickObj, nickCheck, "nickLabel", "사용가능한 닉네임입니다.");
	});
	
	var emailObj = { id : null, email : null };
	
	// 이메일 조건 확인 이벤트
	$("#email").bind("keyup", function(event, ui) {
		// 가입페이지인 경우 입력받은 이메일 정보만 보낸다.
		emailObj.email = $("#email").val();
		// 수정페이지인 경우 아이디 정보도 함께 보낸다.
		 if(modifyPage == true) { emailObj.id = $("#id").val(); }
		 emailCheck = CallPostAjax("/emailCheck", emailObj, emailCheck, "emailLabel", "사용가능한 이메일입니다.");
	});
	
	
	if(modifyPage == true) {
		var id = $("#id").val();
		
		var idObj = { id : id };
		idCheck = CallPostAjax("/idCheck", idObj, idCheck);
		
		// keyup 이벤트가 없어도 자동으로 닉네임 검증오브젝트(nickCheck)에 검증결과 리턴 
		nickObj.nickName = $("#nickName").val();
		nickObj.id = id;
		nickCheck = CallPostAjax("/nickCheck", nickObj, nickCheck);
		
		// keyup 이벤트가 없어도 자동으로 이메일 검증오브젝트(emailCheck)에 검증결과 리턴
		emailObj.email = $("#email").val();
		emailObj.id = id; 
		emailCheck = CallPostAjax("/emailCheck", emailObj, emailCheck);
	}
	
	// 서브밋 확인
	$( "input[type=submit]" ).click(function(event) {
		
		// 수정페이지인 경우 id 검증 통과
		if(modifyPage == false) {
			if($("#id").val() == '')  { 
				alert("아이디는 필수항목입니다."); 
				return false; 
			}
			if(idCheck.isCheck == false) {
				alert("아이디 : " + idCheck.message);
				return false;
			}
		}

		if($("#pwd").val() == '') {  
			alert("비밀번호는 필수항목입니다."); 
			return false;
		}
		if($("#confirmPwd").val() == '') {  
			alert("비밀번호를 확인하여 주십시오"); 
			return false;
		}
		if(!($("#pwd").val() == $("#confirmPwd").val())) {  
			alert("비밀번호가 일치하지 않습니다."); 
			return false;
		}
		if(passCheck.isCheck == false) {
			alert("비밀번호 : " + passCheck.message);
			return false;
		}
		
		if($("#nickName").val() == '') {
			alert("닉네임은 필수항목입니다.");
			return false;
		}
		
		if(nickCheck.isCheck == false) {
			alert("닉네임 : " + nickCheck.message);
			return false;
		}
		
		if($("#email").val() == '') {
			alert("이메일은 필수항목입니다.");
			return false;
		}
		
		if(emailCheck.isCheck == false) {
			alert("이메일 : " + emailCheck.message);
			return false;
		}
		
		var phone = $("#phone").val();
		var pattern = /[^0-9\-]/g;
		if(pattern.test(phone)) {
			alert("전화번호는 숫자만 기입하여 주십시오");
			return false;
		}		 
		
	});		   
	
	$("#goBack").click(function() { location.href="/"; });
	$("#deactivate").click(function() { location.href="/Deactivate"; });
	
});





/* if(!$("#BirthYear option:selected").length) { 
alert("생년월일 - 연도를 선택하여 주십시오"); 
return false;
} */


/*
$(".errorColumn").html("");
var joinformData = $("#joinform").serializeArray();
var actionURL = $("#joinform").attr("action");
$.ajax({
	url : actionURL,
	type : "POST",
	data : joinformData,
	success : function(data, textStatus, xhr) {
		alert("전송 완료");
		location.href="/joinConfirm?id="+ $("#joinform #id").val();
	},
	error : function(response, status, error) {
		var errorMsg = $.parseJSON(response.responseText);
		for(var key in errorMsg) {
			if(key == 'Exception') {
				location.href="/error";
			}
			var selector = "#"+key;
			$(selector).parent().next("td").append("<p>"+errorMsg[key]+"</p>");
		}
		
	}
});
 */