package com.Jin.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
