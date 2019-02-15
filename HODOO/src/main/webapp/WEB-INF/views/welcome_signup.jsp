<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<c:set value="${pageContext.request.contextPath }" var="ctx"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no">
<title>HODOO - 회원가입을 축하합니다</title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/welcome_signup.css"/>
</head>
<body>
	<div id="wrap">
		<p class="welcome">welcome</p>
		<h1>HODOO</h1>
		<img src="${ctx }/img/illust.png"/>
		<h2>회원가입을 축하합니다!</h2>
		<p>
			<b>HODOO</b>에 가입해주셔서 감사합니다.<br/>
			이제 설치된 <b>HODOO</b> 앱으로 돌아가셔서<br/>
			서비스를 즐기시면 됩니다.
		</p>
	</div>
</body>
</html>