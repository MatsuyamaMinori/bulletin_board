<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<h1>BSG掲示板へようこそ！</h1>
<div class="loginform">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
		<hr size="10" color="#FFFF77">
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<form action="login" method="post">
	<label for="loginId">ログインID</label>
	<input name="loginId" value="${inputUser.getLoginId()}" id="loginId"/> <br /><br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/> <br /><br />

	<input type="submit" value="ログイン" /> <br />
</form>
</div>
<div class="copyrightlogin">Copyright©Minori Matsuyama</div>

</body>
</html>
