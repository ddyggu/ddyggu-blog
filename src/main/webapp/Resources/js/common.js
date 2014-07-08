/**
 *  home.jsp - Jquery common function 
 */
 

function ShowHideTableRow(rowSelector, show, callback)
{
    var childCellsSelector = $(rowSelector).children("td");
	var ubound = childCellsSelector.length - 1;
	var lastCallback = null;

    childCellsSelector.each(function(i)
    {
        // Only execute the callback on the last element.
        if (ubound == i)
            lastCallback = callback;

        if (show)
        {
            $(this).hide().fadeIn(999, lastCallback);
        }
        else
        {
            $(this).fadeOut(999, lastCallback);
        }
    });
}


$(document).ajaxStart(function() {
	$('#loader').show();
	}).ajaxStop(function() {
	$("#loader").hide();
});

$(document).ready(function() {
	$("#goBack").click(function() { location.href="/"; });
	
	$("#search-submit").click(function() {
		if($("#keyword").val() == '') {
			alert('검색어를 입력해주십시오');
			return false;
		}
		
		$("#boardSearch-form").submit();
	});
	
	$("#boardSearch-form").submit(function() {
		if($("#keyword").val() == '') {
			alert('검색어를 입력해주십시오');
			return false;
		}
	});
	
	// initialise plugin
	var mainMenu = $('#main-dropdown').superfish({
		//add options here if required
	});

	// buttons to demonstrate Superfish's public methods
	$('.destroy').on('click', function(){
		mainMenu.superfish('destroy');
	});

	$('.init').on('click', function(){
		mainMenu.superfish();
	});

	$('.open').on('click', function(){
		mainMenu.children('li:first').superfish('show');
	});

	$('.close').on('click', function(){
		mainMenu.children('li:first').superfish('hide');
	});
	
});

/*
function formCheck() {
	var length = document.forms[0].length - 1;

	for ( var i = 0; i < length - 1; i++) {
		if (document.forms[0][i].value == null
				|| document.forms[0][i].value == "") {

			alert(document.forms[0][i].name + "을/를 입력하세요.");
			document.forms[0][i].focus();
			return false;
		}
	}
}

// submit과 button에 jQuery UI 적용! - 0820
// http://pgm-progger.blogspot.kr/2012/11/jquery-button-jquery-ui.html
	$(function(){
	  $( ":submit" ).button().click(function( event ) {
			var url = "access";    
			$(location).attr('href',url);
		  });
	  $( ":button" ).button().click(function( event ) {
	    //그냥 이대로 두면, 알아서 버튼 등에 onclick 등으로 설정한대로 버튼이 동작합니다.
	    event.preventDefault(); 
	  });
	  //$( ":button, :submit" ).css({"padding":"2px 5px 2px 5px", "font-size":"9pt"});
	  // padding : 북, 서, 남, 동 순서
	  $( ":button, :submit" ).css({"padding":"6px 10px 6px 10px", "font-size":"9pt"});
	});
*/