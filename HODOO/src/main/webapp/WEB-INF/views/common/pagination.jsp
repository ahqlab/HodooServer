<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="paginate_complex">
	<c:if test="${page.priorPageGroup > 0}">
		<a href="javascript:movePage(1)" class="direction prev"><span></span><span></span> Prev End</a>
	</c:if>

	<c:if test="${page.priorPageGroup > 0}">
		<a href="javascript:movePage(${page.priorPageGroup})" class="direction prev"><span></span> Prev</a>
	</c:if>

	<!-- 페이징 -->
	<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}"
		step="1">
		<c:if test="${page.currentPage != i }">
			<a href="javascript:movePage(${i})">${i}</a>
		</c:if>
		<c:if test="${page.currentPage == i }">
			<strong> ${i}</strong>
		</c:if>
	</c:forEach>

	<c:if test="${page.nextPageGroup > 0}">
		<a href="javascript:movePage(${page.nextPageGroup})" class="direction next">Next <span></span></a>
	</c:if>

	<c:if test="${page.nextPageGroup > 0}">
		<a href="javascript:movePage(${page.pageCount})" class="direction next">Next End <span></span><span></span></a>
	</c:if>
</div>