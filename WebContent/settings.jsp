<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー編集画面</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>ユーザー編集</h1>
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

<form action="settings" method="post"><br />
	<label for="loginId">ログインID（半角英数字6文字以上20文字以内）</label>
	<input name="loginId" value="${settingUser.loginId}" id="loginId"/> <br />

	<label for="name">アカウント名（10文字以下）</label>
	<input name="name" value="${settingUser.name}" id="name"/><br />

	<label for="password">パスワード（記号を含む半角文字6文字以上255文字以下）＊変更しない場合、入力の必要はありません。</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="checkPassword">確認のため、パスワードをもう一度入力してください。＊パスワードを変更しない場合、入力の必要はありません。</label>
	<input name="checkPassword" type="password" id="checkPassword"/> <br />
<br />
<c:if test="${loginUser.getId() != settingUser.getId()}">
所属
<select name="branchId">
	<option value=0>選択してください。</option>
	<c:forEach items="${branches}" var="branches">
	<c:if test="${settingUser.branchId == branches.getBranchId()}">
		<option value="${branches.getBranchId()}" selected><c:out value="${branches.getBranchName()}"></c:out></option></c:if>
	<c:if test="${settingUser.branchId != branches.getBranchId()}">
		<option value="${branches.getBranchId()}"><c:out value="${branches.getBranchName()}"></c:out></option></c:if>
	</c:forEach>
</select><br/>
<br />

役職
<select name="jobTitleId">
	<option value=0>選択してください。</option>
	<c:forEach items="${JobTitles}" var="JobTitles">
	<c:if test="${settingUser.jobTitleId == JobTitles.getJobTitleId()}">
		<option value="${JobTitles.getJobTitleId()}" selected><c:out value="${JobTitles.getJobTitleName()}"></c:out></option></c:if>
	<c:if test="${settingUser.jobTitleId != JobTitles.getJobTitleId()}">
		<option value="${JobTitles.getJobTitleId()}"><c:out value="${JobTitles.getJobTitleName()}"></c:out></option></c:if>
	</c:forEach>
</select><br/>
<br /></c:if>
<c:if test="${loginUser.getId() == settingUser.getId()}">
<input name="branchId" value="${settingUser.getBranchId()}" type="hidden" id="branchId"/>
<input name="jobTitleId" value="${settingUser.getJobTitleId()}" type="hidden" id="jobTitleId"/>
</c:if>

<input name="userId" value="${settingUser.getId()}" type="hidden" id="userId"/>
	<input type="submit" value="登録" /> <br />

</form>
<div class="copyright">Copyright©Minori Matsuyama</div>
</div>
</body>
</html>
