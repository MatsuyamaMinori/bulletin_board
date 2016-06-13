<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー登録</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>新規社員登録</h1>
<div class="header">
<span style="float: right"><a href="managementUsers">ユーザー管理画面に戻る</a></span>
<br/>
</div>
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
<form action="signup" method="post"><br />
	<label for="loginId">ログインID（半角英数字6文字以上20文字以内）</label>
	<input name="loginId" value="${error.loginId}" id="loginId"/> <br />

	<label for="name">アカウント名（10文字以下）</label>
	<input name="name" value="${error.name}" id="name"/><br />

	<label for="password">パスワード（記号を含む半角文字6文字以上255文字以下）</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="checkPassword">確認のため、パスワードをもう一度入力してください。</label>
	<input name="checkPassword" type="password" id="checkPassword"/> <br />
<br />
所属
<select name="branchId">
	<option value=0>選択してください。</option>
	<c:forEach items="${branches}" var="branches">
	<c:if test="${error.branchId == branches.getBranchId()}">
		<option value="${branches.getBranchId()}" selected><c:out value="${branches.getBranchName()}"></c:out></option></c:if>
	<c:if test="${error.branchId != branches.getBranchId()}">
		<option value="${branches.getBranchId()}"><c:out value="${branches.getBranchName()}"></c:out></option></c:if>
	</c:forEach>
</select><br/>
<br />

役職
<select name="jobTitleId">
	<option value=0>選択してください。</option>
	<c:forEach items="${JobTitles}" var="JobTitles">
	<c:if test="${error.jobTitleId == JobTitles.getJobTitleId()}">
		<option value="${JobTitles.getJobTitleId()}" selected><c:out value="${JobTitles.getJobTitleName()}"></c:out></option></c:if>
	<c:if test="${error.jobTitleId != JobTitles.getJobTitleId()}">
		<option value="${JobTitles.getJobTitleId()}"><c:out value="${JobTitles.getJobTitleName()}"></c:out></option></c:if>
	</c:forEach>
</select><br/>
<br />

<input type="submit" value="登録" /> <br /><br />

</form>
<div class="copyright">Copyright©Minori Matsuyama</div>
</div>
</body>
</html>
