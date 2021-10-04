<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id }" />
		<div class="form-group">
			<label for="username">username:</label> 
			<input type="text" value="${principal.user.username }" class="form-control" placeholder="아이디를 입력해주세요." id="username" readonly>
		</div>

		<c:if test="${empty principal.user.oauth }">
			<div class="form-group">
				<label for="password">Password:</label>
				 <input type="password" class="form-control" placeholder="비밀번호를 입력해주세요." id="password">
			</div>
		</c:if>
		
		<div class="form-group">
			<label for="email">Email:</label> 
			<input type="email" value="${principal.user.email }" class="form-control" placeholder="이메일을 입력해주세요." id="email" readonly="readonly">
		</div>
	</form>

	<!-- ajax 버튼이 잘 안먹혔다. 이유는 form 밖에 버튼을 안빼놔서 이기 때문이다. -->
	<button id="btn-update" class="btn btn-primary">수정완료</button>
</div>

<script src="/js/user.js"></script>
<%@include file="../layout/footer.jsp"%>
