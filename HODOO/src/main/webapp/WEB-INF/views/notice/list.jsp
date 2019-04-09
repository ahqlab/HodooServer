<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/tagLib.jsp"%>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<div class="board_area">
	<table class="board_table">
		<colgroup>
			<col style="width: 50px;">
			<col style="width: *%;">
			<col style="width: 97px;">
			<col style="width: 97px;">
			<col style="width: 60px;">
		</colgroup>
		<thead>
			<tr
				style="height: 50px; background: url('../../resources/image/technology/certification/board_title.jpg') no-repeat;">
				<th scope="row" class="first">번호</th>
				<th scope="row">제목</th>
				<th scope="row">작성자</th>
				<th scope="row">작성일</th>
				<th scope="row" class="last">조회</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="domain" items="${domainList}" varStatus="status">
				<tr style="cursor: pointer"
					onclick="javascript:document.location.href='${pageContext.request.contextPath}/notice/detail.do?id=${domain.noticeIdx}'">
					<td>${domain.noticeIdx}</td>
					<td>${domain.title}</td>
					<td>${domain.content}</td>
					<td><fmt:parseDate var="parsedDate"
							value="${domain.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /> <fmt:formatDate
							var="newFormattedDateString" value="${parsedDate}"
							pattern="yyyy-MM-dd HH:mm " /> ${newFormattedDateString}</td>
					<td>0</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="paginate">
		<%@ include file="/WEB-INF/views/common/pagination.jsp"%>
	</div>
	<div class="search_area">
		<form:form id="searchForm" commandName="domainParam" method="POST">
			<form:hidden path="currentPage" />
			<table style="border: solid #f5f5f5 1px; margin: 10px 0px 10px 0px;">
				<colgroup>
					<col style="width: *%;">
					<col style="width: 130px;">
					<col style="width: *%;">
				</colgroup>
				<tbody>
					<tr>
						<td style="text-align: right;"><form:select
								class="user_input" path="searchField" style="width: 92px;">
								<form:option value="title">제목</form:option>
								<form:option value="content">내용</form:option>
							</form:select></td>
						<td><form:input path="searchWord" class="user_input" /></td>
						<td style="text-align: left;"><input type="image" src="${pageContext.request.contextPath}/resources/image/customer/qna/search_btn.jpg" />
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
</div>
