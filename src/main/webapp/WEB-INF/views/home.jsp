<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="ko" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta charset="UTF-8">
<title>ddyggu's blog</title>
<link rel="icon" href="http://ddyggu30.cafe24.com/Resources/images/bg.ico" type="image/x-icon">
<link rel="shortcut icon" href="http://ddyggu30.cafe24.com/Resources/images/bg.ico" type="image/x-icon">
<link rel="stylesheet" href="/Resources/css/Reset.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/Resources/css/slider-style.css" />
<link rel="stylesheet" href="/Resources/css/Home.css" type="text/css">
<script src="http://code.jquery.com/jquery-1.8.3.js"/></script>
<script src="/Resources/js/common.js"/></script>
</head>
<body>
<div id="header-background">
	<header class="Properties">
		<h1><a href="/">ddyggu's blog</a></h1>
		<div id="navigation-wrap">
			<nav id="top-navigation">
			<ul class="Login-join">
				<li><a href="#">Login</a></li>	
				<li><a href="/memJoin">Join</a></li>
			</ul>
			</nav>
			<!--  
			<nav id="bottom-navigation">
				<ul class="nav">
					<li><a href="/menu1">MENU1</a></li>
					<li><a href="/menu2">MENU2</a></li>
					<li><a href="/menu3">MENU3</a></li>
					<li><a href="/menu4">MENU4</a></li>
					<li><a href="/menu5">MENU5</a></li>
				</ul>
			</nav>
			-->
		</div>
	</header>
</div>
<div id="content-background">
	<section id="content" class="Properties">
		<!-- Slider Start -->  
		<div id="wowslider-container1">
			<div class="ws_images">
				<ul>
					<li>
						<img src="/Resources/images/slider/main/Peaceful spectacle.jpg" alt="Peaceful spectacle" title="Peaceful spectacle" id="wows1_0"/>
					</li>
					<li>
						<img src="/Resources/images/slider/main/Stunning waterfall.jpg" alt="Stunning waterfall" title="Stunning waterfall" id="wows1_1"/>
					</li>
					<li>
						<img src="/Resources/images/slider/main/Space oddity.jpg" alt="Space oddity" title="Space oddity" id="wows1_2"/>
					</li>
					<li>
						<img src="/Resources/images/slider/main/Lush forest.jpg" alt="Lush forest" title="Lush forest" id="wows1_3"/>
					</li>
					<li>
						<img src="/Resources/images/slider/main/Cute baby cat.jpg" alt="Cute baby cat" title="Cute baby cat" id="wows1_4"/>
					</li>
				</ul>
			</div>	
			<div class="ws_bullets">
				<div>
					<a href="#" title="Peaceful spectacle"><img src="/Resources/images/slider/tooltips/Peaceful spectacle.jpg" alt="Peaceful spectacle"/>1</a>
					<a href="#" title="Stunning waterfall"><img src="/Resources/images/slider/tooltips/Stunning waterfall.jpg" alt="Stunning waterfall"/>2</a>
			 		<a href="#" title="Space oddity"><img src="/Resources/images/slider/tooltips/Space oddity.jpg" alt="Space oddity"/>3</a>
			 		<a href="#" title="Lush forest"><img src="/Resources/images/slider/tooltips/Lush forest.jpg" alt="Lush forest"/>4</a>
			 		<a href="#" title="Cute baby cat"><img src="/Resources/images/slider/tooltips/Cute baby cat.jpg" alt="Cute baby cat"/>5</a>
				</div>
			</div>
			<div class="ws_shadow"></div>
			<script type="text/javascript" src="/Resources/js/slider-core.js"></script>
			<script type="text/javascript" src="/Resources/js/slider-script.js"></script>
		</div>
		<!-- Slider End -->

		<article>
			<h1>Recently Updated</h1>
			<div id="border"></div>	
		</article>
	</section>
</div>
<div id="footer-background">
		<footer class="Properties" id="footer">
			<h3>구축 환경</h3> 
			<article>
				<p>JDK 1.7.x / Tomcat 7.0.x</p>  
				<p>JSP 2.2 / Servlet 3.0</p>
				<p>HTML5 / javascript / jquery</p>
				<p>Spring 3.1.1 / Mybatis-spring 1.2 / Maven</p>
				<p>MySQL 5.5.x UTF-8</p>  
			</article>
		</footer>
</div>
	<!--  
	<h1>Login page!</h1>
	<div id="header">
	</div>
	<form action="access" name="LogFrm" method="post"
		onsubmit="return formCheck();">
		<table>
			<tr>
				<td>아이디:</td>
				<td><input type="text" name="id" /></td>
			</tr>
			<tr>
				<td>비밀번호:</td>
				<td><input type="password" name="pwd" /></td>
			</tr>
			<tr>
				<td colspan=2><input type="submit" value="로그인" class="myfont" /> <input
					type="button" value="회원가입"
					onClick="javascript:location.href='/memJoin'"></td>
			</tr>
		</table>
	</form>
	-->
</body>
</html>
<!--
유효성 체크 코드를 넣었음. 하나라도 입력이 안됐다면 다음으로 넘어가지 않는다.
/SpringBoard/src/main/java/com/school/board/HomeController.java
으로가서 /join에 대한 처리를 작성한다.

-->