<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
</head>
<body>
<h1>新規投稿</h1><br />

<div class="header">
<div class="back"><a href="top">TOP画面に戻る</a></div>
</div>
<br />
<div class="main-contents">
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
<div class="form-area">
	<form action="newArticles" method="post">
		件名（50文字まで）<label for="titel"></label>
		<input name="titel" value="${error.getTitel()}" size="50" maxlength="50" id="titel"/> <br /><br />
		本文（1000文字まで）<br />
		<textarea name="text" cols="80" rows="20" maxlength="500" class="text" id="text"><c:out value="${error.getText()}"/></textarea>
		<br /><br />
		投稿の種類（10文字まで）<label for="category"></label>
		<input name="category" value="${error.getCategory()}" size="10" maxlength="10" id="category"/> <br />
		<h4>＊全項目記入必須です。</h4>
		<input type="submit" value="投稿する"><br /><br />
	</form>
</div>
</div>

<div class="copyright">Copyright©Minori Matsuyama</div>

</body>
</html>