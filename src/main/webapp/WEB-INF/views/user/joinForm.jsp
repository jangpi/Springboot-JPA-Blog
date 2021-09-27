<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@include file="../layout/header.jsp"%>

<div class="container">
<form>
	<div class="form-group">
		<label for="username">username:</label> <input type="username" class="form-control" placeholder="아이디를 입력해주세요." id="username">
	</div>
	<div class="form-group">
		<label for="password">Password:</label> <input type="password" class="form-control" placeholder="비밀번호를 입력해주세요." id="password">
	</div>
	<div class="form-group">
		<label for="email">Email:</label> <input type="email" class="form-control" placeholder="이메일을 입력해주세요." id="email">
	</div>
	<button id="btn-save" class="btn btn-primary">회원가입완료</button>
</form>
</div>

<script src="/js/user.js"></script>
<%@include file="../layout/footer.jsp"%>
