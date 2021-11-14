<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@include file="layout/header.jsp"%>

<div class="container">

	<c:forEach var="board" items="${boards.content }">
		<div class="card m-2">
			<div class="card-body">
				<h5 class="card-title">${board.title }</h5>
				<a href="/board/${board.id }" class="btn btn-primary">자세히보기</a>
				<div class="text-right">조회수 ${board.count }</div>
				<div class="text-right">${board.user.username } | ${board.createDate }</div>
			</div>
		</div>
	</c:forEach>
	
	<ul class="pagination justify-content-center">
	
	<c:choose>
		<c:when test="${boards.first }">
			<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li>
		</c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${boards.last }">
			<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li>
		</c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li>
		</c:otherwise>
	</c:choose>
	</ul>
	
</div>

<br />
<%@include file="layout/footer.jsp"%>


