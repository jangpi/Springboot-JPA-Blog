package com.Jin.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.Jin.blog.config.auth.PrincipalDetail;

// 인증이 안된 사용자들이 출입을 할 수 있는 경로를 /auth/**
// 그냥 주소 / 이면 index.jsp 허용
// static이하에 있는 /js/**,css/**, /image/**

@Controller
public class UserController {
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	// @AuthenticationPrincipal PrincipalDetail principal
	@GetMapping("/user/updateForm")
	public String updateForm() {	
		return "user/updateForm";
	}
}
