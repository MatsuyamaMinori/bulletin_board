<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー管理</title>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">

	<style type="text/css">
	.usersTable th {
	background-color: #FFA500;
	}
	.usersTable {
	text-align: center;
	}

	.usersTable td {
	padding:3px 2px;
	}
	</style>

</head>
<body>
<h1>ユーザー管理</h1>
<div class="header">
		<a href="signup">新規ユーザー登録</a>
		<span style="float: right"><a href="top">TOP画面に戻る</a></span>
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
<c:if test="${ not empty compMessages }">
		<div class="compMessages">
			<ul>
				<c:forEach items="${compMessages}" var="compMessage">
					<li><c:out value="${compMessage}" />
				</c:forEach>
			</ul>
		<hr size="10" color="#FFFF77">
		</div>
		<c:remove var="compMessages" scope="session"/>
	</c:if>
<div class="user">
<Center>
	<table class="color" border=1>
	<tr><td style="border-style: none;">＊ユーザー機能停止中：</td><td width="100" height="25" bgcolor="#FFB6C1"></td></tr>
	</table>
	<table class="usersTable" border=1>
		<tr ><th width="100">ログインID</th><th width="150">ユーザー名</th>
		<th width="100">所属</th><th width="100">役職</th>
		<th width="50">停止・復活</th><th width="50">削除</th></tr>

		<c:forEach items="${user}" var="user">
		<c:if test="${user.getStoped() == 1}">
		<tr bgcolor="#FFB6C1"></c:if>
		<c:if test="${user.getStoped() == 0}">
		<tr bgcolor="white"></c:if>
		<td><c:out value="${user.getLoginId()}" /></td>
		<td><a href="settings?userId=${user.getId()}"><c:out value="${user.getName()}" /></a></td>
			<td><c:out value="${user.getBranchName()}" /></td><td><c:out value="${user.getJobTitleName()}" /></td>

		<c:if test="${loginUser.getId() != user.getId()}">
			<td><c:if test="${user.getStoped() == 0}">
					<form method="post" action="stopedUser" onSubmit="return stopedUser()">
					<input name="userId" value="${user.getId()}" type="hidden" id="userId"/>
					<input name="name" value="${user.getName()}" type="hidden" id="name"/>
					<input type="submit" value="停止">
					</form>
					<script>
						function stopedUser(){
							if(window.confirm("この社員のユーザー機能を停止します。よろしいですか?")) {
								return true;
							} else {
								window.alert("ユーザー機能の停止がキャンセルされました。");
								return false;
							}
						}
					</script>
				</c:if>
				<c:if test="${user.getStoped() == 1}">
					<form method="post" action="recoveryUser" onSubmit="return recoveryUser()">
					<input name="userId" value="${user.getId()}" type="hidden" id="userId"/>
					<input name="name" value="${user.getName()}" type="hidden" id="name"/>
					<input type="submit" value="復活">
					</form>
					<script>
						function recoveryUser(){
							if(window.confirm("この社員のユーザー機能を復活します。よろしいですか?")) {
								return true;
							} else {
								window.alert("ユーザー機能の復活キャンセルされました。");
								return false;
							}
						}
					</script>
					</c:if>
			</td>

			<td><form method="post" action="deleteUser" onSubmit="return deleteUser()">
				<input name="userId" value="${user.getId()}" type="hidden" id="userId"/>
				<input name="name" value="${user.getName()}" type="hidden" id="name"/>
				<input type="submit" value="削除">
				</form>
				<script>
					function deleteUser(){
						if(window.confirm("この社員のユーザー情報を削除します。よろしいですか?")) {
							return true;
						} else {
							window.alert("ユーザーの情報削除がキャンセルされました。");
							return false;
						}
					}
				</script>
			</td></c:if>
			<c:if test="${loginUser.getId() == user.getId()}">
			<td></td><td></td>
			</c:if>
			</tr>
		</c:forEach>
	</table></Center>
</div></div>
<div class="copyright">Copyright©Minori Matsuyama</div>

</body>
</html>