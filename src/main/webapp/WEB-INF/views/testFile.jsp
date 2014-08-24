<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<style>
	.column-style { border:1px solid black; }
</style>
<script src="http://code.jquery.com/jquery-1.8.3.js"/></script>
<script type="text/javascript" src="Resources/se_editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
var oEditors1 = [];
var oEditors2 = [];

function applyEditor(object, holder) {
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: object,
		elPlaceHolder: holder,
		sSkinURI: "Resources/se_editor/SmartEditor2Skin.html",	
		htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
			fOnBeforeUnload : function(){
				//alert("완료!");
			}
		}, //boolean
		fOnAppLoad : function(){
			//예제 코드
			//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
		},
		fCreator: "createSEditor2"
	});
}

// 추가 글꼴 목록
//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

function pasteHTML() {
	var sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
	oEditors.getById["contents"].exec("PASTE_HTML", [sHTML]);
}

function showHTML() {
	var sHTML = oEditors.getById["contents"].getIR();
	alert(sHTML);
}

// editor의 내용을 textarea에 적용시켜 submit 시켜주는 함수.
function submitContents(elClickedObj) {
	oEditors1.getById["contents1"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
	oEditors2.getById["contents2"].exec("UPDATE_CONTENTS_FIELD", []);
	
	// 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("ir1").value를 이용해서 처리하면 됩니다.
	
	try {
		elClickedObj.form.submit();
	} catch(e) {}
}

function setDefaultFont() {
	var sDefaultFont = '궁서';
	var nFontSize = 24;
	oEditors.getById["contents"].setDefaultFont(sDefaultFont, nFontSize);
}


// 에디터 적용시키기 
$(document).ready(function() {
	applyEditor(oEditors1, "contents1");
	applyEditor(oEditors2, "contents2");
});

</script>
</head>
<body>
<form name="test" id="test" action="/writeTest" method="post" encType="multipart/form-data" onSubmit="return submitContents();">
<table style="width:800px; border:1px solid black;">
	
	<tr>
		<td class="column-style">아이디</td>
		<td class="column-style"><input type="text" name="writer" id="writer"></td>
	</tr>
	<tr>
		<td class="column-style">제목</td>
		<td class="column-style"><input type="text" name="title" id="title"></td>
	</tr>
	<tr>
		<td class="column-style">내용1</td>
		<td class="column-style"><textarea id="contents1" name="contents1" rows="20" cols="80"></textarea></td>
	</tr>
	<tr>
		<td class="column-style">내용2</td>
		<td class="column-style"><textarea id="contents2" name="contents2" rows="20" cols="80"></textarea></td>
	</tr>
	<tr>
		<td class="column-style">파일1</td>
		<td class="column-style"><input type="file" id="file1" name="file1"></td>
	</tr>
	<tr>
		<td class="column-style">파일2</td>
		<td class="column-style"><input type="file" id="file2" name="file2"></td>
	</tr>
	<tr>
		<td style="text-align:right;" colspan="2"><input type="submit" value="등록"></td>
	</tr>
	
</table>
</form>
</body>
</html> 