<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">username:</label> 
			<input type="username" name="username" class="form-control" placeholder="아이디를 입력해주세요." id="username">
		</div>
		<div class="form-group">
			<label for="password">Password:</label> 
			<input type="password" name="password"class="form-control" placeholder="비밀번호를 입력해주세요." id="password">
		</div>

		<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>
</div>

<%@include file="../layout/footer.jsp"%>
