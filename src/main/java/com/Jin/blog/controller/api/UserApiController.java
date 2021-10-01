package com.Jin.blog.controller.api;

import java.security.Security;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Jin.blog.config.auth.PrincipalDetail;
import com.Jin.blog.dto.ResponseDto;
import com.Jin.blog.model.User;
import com.Jin.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService; // 디펜더시 인젝션
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// 회원가입
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {	// username, password, email
		System.out.println("UserApiController : save 호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){	//json 데이터를 받아줘야 하기 때문에 리퀘스트 바디를 걸어 준다.
		userService.회원수정(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
		// 하지만 세션값은 변경되지 않은 상태이기 때문에. 우리가 직접 세션 값 을 변경	
	
		// 세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
