<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false"%>
 
<link href="<c:url value='/Resources/css/joinform.css'/>" rel="stylesheet" type="text/css" />
<script>

// Backspace 방지
document.onkeydown = function(e) {
	var key = (e) ? e.keyCode : event.keyCode;
	if(key==8) {
		if(confirm("입력을 취소하고 되돌아갑니까?")) {
			location.href="/";
		} else {
			if(e) { 
				e.preventDefault();
			} else {
				event.keyCode = 0;
				event.returnValue = false;
			}
		}
	}
}

$(function() {
			
		   var idcheck = false; 
								
			  $( "input[type=submit]" ).click(function(event) {
				  
				  if(idcheck===false) { 
					  alert("아이디를 중복체크 해주십시오"); 
				  	  return false;
				  }
				  
				  $("input").parent().next("td").html("");
			  		var joinformData = $("#joinform").serializeArray();
			  		var actionURL = $("#joinform").attr("action");
			  		$.ajax({
			  			url : actionURL,
			  			type : "POST",
			  			data : joinformData,
			  			success : function(data, textStatus, xhr) {
			  				alert("전송 완료");
			  				location.href="/";
			  			},
			  			error : function(response, status, error) {
			  				var errorMsg = $.parseJSON(response.responseText);
							for(var key in errorMsg) {
			  					var selector = "input[id="+key+"]";
			  					$(selector).parent().next("td").append("<p>"+errorMsg[key]+"</p>");
			  				}
							
			  			}
			  		});
			  		event.preventDefault();
			  });
			  
			  $(":button[id=DuplicateCheck]").click(function() {
				  var id = $("input[id=id]").val();
				  $.ajax({
					url : "/idCheck" ,
					type : "GET",
					data : "id="+id,
				  	success : function(data, status, xhr) {
				  		idcheck = data == 'true';
				  		if(confirm("사용가능한 아이디입니다. 사용하시겠습니까?")) {
				  			$("#id").prop("readonly", true).css('background-color','#cecdc9');	
				  		} else {
				  			$("#id").prop("readonly", false).css('background-color','white');
				  			idcheck = false;
				  		}
				  		
				  	},
				  	error : function(response, status, error) {
				  		idcheck = response.responseText == 'true';
				  		alert("이미 사용중인 아이디입니다.");
				  	}
				  });
			 });
			  
			  $("#goBack").click(function() { location.href="/"; });
			  
});
				
</script>
<section id="joinform-wrap">
		<h1> </h1>
		<form:form id="joinform" method="post"  action="/memJoin" modelAttribute="members" cssClass="joinform">	
				<fieldset>
				<legend> 회원 가입 </legend>
				<table>
					<tr><td class="inputColumn">
					<form:label path="id">아이디 </form:label><br/>
					<form:input path="id" value="ddyggu"/>
					<input type="button" id="DuplicateCheck" value="중복체크"/>
				    </td>
				    <td class="errorColumn">
				    <form:errors path="id" cssClass="error"/>
				    </td>
				    </tr>
				    
				    <tr>
				    	<td class="inputColumn">
				    		<form:label path="pwd"> 비밀번호 </form:label><br/>
				    		<form:input path="pwd" type="password" value="dkepsdmsDK30!"/><br/>
				    	</td>
				    	<td class="errorColumn">
				    		<form:errors path="pwd" cssClass="error"/>
				   		</td>
				    </tr>
				    
				    <tr>
				    	<td class="inputColumn">
				    		<form:label path="mname">이름 </form:label><br/>
				    		<form:input path="mname" value="임종현"/><br/>
				    	</td>
				    	<td class="errorColumn">
				    	 	<form:errors path="mname" cssClass="error"/>
				     	</td>
				    </tr>
				    
				    <tr>
				    	<td class="inputColumn">
				    		<form:label path="BirthYear"> 생년월일 </form:label><br/>
				    		<form:input path="BirthYear" value="1984" class="YearOfBirth"/>년 
				    		<form:input path="BirthMonth" value="" class="MonthOfBirth"/>월 
				    		<form:input path="BirthDay" value="" class="DayOfBirth"/>일 <br/> 
				    	</td>
				    	<td class="errorColumn">  
				    	  	<form:errors path="BirthYear" cssClass="error"/><br/>
				    	  	<form:errors path="BirthMonth" cssClass="error"/><br/>
				    	  	<form:errors path="BirthDay" cssClass="error"/><br/>
				    	 </td>
				    </tr>
				    
				    <tr>
				    	<td class="inputColumn">
				    		<form:label path="addr"> 주소 </form:label><br/>
				    		<form:input path="addr" value="경기도 안산시" />
				    	</td>
				   		 <td class="errorColumn">
				    		<form:errors path="addr" cssClass="error"/>
				    	</td>
				    </tr>
				    
				    <tr>
				    	<td class="inputColumn">
				    		<form:label path="phone"> 전화번호 </form:label><br/>
				    		<form:input path="phone" value="010-7187-3449"/>
				    	</td>
				    	<td class="errorColumn">  
				    		<form:errors path="phone" cssClass="error"/>
				    
				    	</td>
				    </tr>
				 </table>
				 </fieldset>
				<p class="buttons">
					<input type="submit"  value="회원가입" /> 
					<input type="button" id="goBack" value="돌아가기"/>
				</p>
		</form:form>
</section>