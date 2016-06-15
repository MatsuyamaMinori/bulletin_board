<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板</title>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
</head>
<body>
<h1>BSG掲示板</h1>

<div class="header">
		<a href="newArticles">新規投稿</a>　　
		<c:if test="${authorityTransition}">
			<a href="managementUsers">ユーザー管理</a>　　
		</c:if>
		<span style="float: right"><a href="logout">ログアウト</a></span>

		<c:if test="${ not empty authorityMessages }">
		<div class="authorityMessages">
		<hr size="10" color="#FFFF77">
			<ul>
				<c:forEach items="${authorityMessages}" var="authorityMessages">
					<li><c:out value="${authorityMessages}" />
				</c:forEach>
			</ul>
		</div>
		<c:remove var="authorityMessages" scope="session"/>
		</c:if>
</div>
<div class="search">
<form action="top" method="get">
<br/>
カテゴリーで検索する：<select name="categorySearch">
	<option value="all">全て見る</option>
	<c:forEach items="${categories}" var="categories">
	<c:if test="${search.category == categories}">
		<option value="${categories}" selected><c:out value="${categories}"></c:out></option>
	</c:if>
	<c:if test="${search.category != categories}">
		<option value="${categories}"><c:out value="${categories}"></c:out></option>
	</c:if>
	</c:forEach>
</select><br/><br/>
<label for="date">日付で検索する(例　2016年3月30日)：
<input name="beforeYear" value="${search.year1}" id="beforeYear" size="4" maxlength="4"/>年 <input name="beforeManth" value="${search.month1}" id="beforeManth" size="2" maxlength="2"/>月
<input name="beforeDate" value="${search.date1}" id="beforeDate" size="2" maxlength="2"/>日 から <input name="afterYear" value="${search.year2}" id="afterYear" size="4" maxlength="4"/>年
<input name="afterManth" value="${search.month2}" id="afterManth" size="2" maxlength="2"/>月 <input name="afterDate" value="${search.date2}" id="afterDate" size="2" maxlength="2"/> 日まで
</label><br />
・カテゴリー、日付検索はどちらか一方のみ入力でも検索可能です。<br />
・日付検索をする場合は年月日は必須記入です。<br />
・前後の日付どちらか片方が揃っていれば検索は可能です。<br /><br />
<input type="submit" value="検索する" /></form>
<c:if test="${ not empty searchMessages }">
		<div class="searchMessages">
		<hr size="10" color="#FFFF77">
			<ul>
				<c:forEach items="${searchMessages}" var="searchMessages">
					<li><c:out value="${searchMessages}" />
				</c:forEach>
			</ul>
		</div>
		<c:remove var="searchMessages" scope="session"/>
		</c:if>
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

<div class="form-area">

<div class="article">
	<c:forEach items="${article}" var="article">

		<div class="articles">
			<div class="id">No.<c:out value="${article.getId()}" /></div><br/>
			<div class="category">〈カテゴリー〉　<c:out value="${article.getCategory()}" /></div><br/>
			<div class="titel"><b>〈件名〉　<c:out value="${article.getTitel()}" /></b></div><br/>
			<hr width="100%" align="left">
			<div class="text"><pre><c:out value="${article.getText()}" /></pre></div><br/>
			<div class="user">投稿者[　<c:out value="${article.getName()}" />　]</div>
			<div class="date">投稿日時：<fmt:formatDate value="${article.getInsertDate()}"
			 pattern="yyyy年MM月dd日 HH:mm:ss" /></div>
		</div><br/>

		<c:if test ="${ deleteAll || article.getBranchId() == loginUser.getBranchId() && deleteBranch || (article.getUserId() == loginUser.getId())}">
			<form method="post" action="deleteArticles" onSubmit="return deleteAticle()">
				<input name="articleId" value="${article.getId()}" type="hidden" id="articleId"/>
				<input name="titel" value="${article.getTitel()}" type="hidden" id="titel"/>
				<input name="branchId" value="${article.getBranchId()}" type="hidden" id="branchId"/>
				<input name="userId" value="${article.getUserId()}" type="hidden" id="userId"/>
				<input name="category" value="${search.category}" type="hidden" id="category"/>
				<input name="beforeYear" value="${search.year1}" type="hidden" id="beforeYear"/>
				<input name="beforeManth" value="${search.month1}" type="hidden" id="beforeManth"/>
				<input name="beforeDate" value="${search.date1}" type="hidden" id="beforeDate"/>
				<input name="afterYear" value="${search.year2}" type="hidden" id="afterYear"/>
				<input name="afterManth" value="${search.month2}" type="hidden" id="afterManth"/>
				<input name="afterDate" value="${search.date2}" type="hidden" id="afterDate"/>
				<input type="submit" value="投稿削除">
			</form>
			<script>
				function deleteAticle(){
					if(window.confirm("投稿を削除します。よろしいですか?")) {
						return true;
					} else {
						window.alert("キャンセルされました。");
						return false;
					}
				}
			</script>
			<br/>
		</c:if>
		<hr width="100%" align="left">

		コメント（500文字まで）
		<hr width="65%" align="left"><br/>

		<form action="newComment" method="post">
			<input name="articleId" value="${article.getId()}" type="hidden" id="articleId"/>
			<input name="titel" value="${article.getTitel()}" type="hidden" id="titel"/>
			<input name="category" value="${search.category}" type="hidden" id="category"/>
			<input name="beforeYear" value="${search.year1}" type="hidden" id="beforeYear"/>
			<input name="beforeManth" value="${search.month1}" type="hidden" id="beforeManth"/>
			<input name="beforeDate" value="${search.date1}" type="hidden" id="beforeDate"/>
			<input name="afterYear" value="${search.year2}" type="hidden" id="afterYear"/>
			<input name="afterManth" value="${search.month2}" type="hidden" id="afterManth"/>
			<input name="afterDate" value="${search.date2}" type="hidden" id="afterDate"/>
			<div class="commentNew">
			<textarea name="commenttext" cols="40" rows="15" class="commenttext"><c:out value="${error.text}" /></textarea>
			<br /></div>
			<input type="submit" value="コメントを送る"><br /><br />
		</form><hr width="65%" align="left">

		<c:forEach items="${comment}" var="comment">
			<c:if test="${comment.getArticleId() == article.getId()}">
				<div class="comment">
				<div class="commenttext"><pre><c:out value="${comment.getText()}"/></pre></div><br/>
				<div class="user">投稿者[　<c:out value="${comment.getName()}" />　]</div>
				<div class="date">投稿日時：<fmt:formatDate value="${comment.getInsertDate()}" pattern="yyyy年MM月dd日 HH:mm:ss" /></div>
<br/>
			<c:if test ="${ deleteAll || comment.getBranchId() == loginUser.getBranchId() && deleteBranch || (comment.getUserId() == loginUser.getId())}">
			<form method="post" action="deleteComment" onSubmit="return deleteCommentOnly()">
				<input name="commentId" value="${comment.getId()}" type="hidden" id="commentId"/>
				<input name="titel" value="${article.getTitel()}" type="hidden" id="titel"/>
				<input name="name" value="${comment.getName()}" type="hidden" id="name"/>
				<input name="branchId" value="${comment.getBranchId()}" type="hidden" id="branchId"/>
				<input name="userId" value="${comment.getUserId()}" type="hidden" id="userId"/>
				<input name="category" value="${search.category}" type="hidden" id="category"/>
				<input name="beforeYear" value="${search.year1}" type="hidden" id="beforeYear"/>
				<input name="beforeManth" value="${search.month1}" type="hidden" id="beforeManth"/>
				<input name="beforeDate" value="${search.date1}" type="hidden" id="beforeDate"/>
				<input name="afterYear" value="${search.year2}" type="hidden" id="afterYear"/>
				<input name="afterManth" value="${search.month2}" type="hidden" id="afterManth"/>
				<input name="afterDate" value="${search.date2}" type="hidden" id="afterDate"/>
				<input type="submit" value="コメント削除">
			</form>
			<script>
				function deleteCommentOnly(){
					if(window.confirm("コメントを削除します。よろしいですか?")) {
						return true;
					} else {
						window.alert("キャンセルされました。");
						return false;
					}
				}
			</script>
			</c:if>

			</div><hr width="65%" align="left">
			</c:if>
		</c:forEach><hr size="10" color="#FFFF77">

	</c:forEach>
</div></div></div>

<div class="copyright">Copyright©Minori Matsuyama</div>

</body>
</html>