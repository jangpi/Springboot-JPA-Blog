package com.Jin.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.Jin.blog.model.KakaoProfile;
import com.Jin.blog.model.OAuthToken;
import com.Jin.blog.model.User;
import com.Jin.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입을 할 수 있는 경로를 /auth/**
// 그냥 주소 / 이면 index.jsp 허용
// static이하에 있는 /js/**,css/**, /image/**

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {	// @ResponseBody : 데이터를 리턴해주는 컨트롤러 함수
		
		// POST방식으로 key=value 타입의 데이터를 요청(카카오쪽 으로)
		// Retrofit2(안드로이드에서 많이씀)
		// OkHttp
		// RestTemplate
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브잭트 생성
		HttpHeaders headers = new HttpHeaders();
		// Content 타입에 담는다는 의미 : 내가 전송할 http body key=value 형태로 알려주는 것
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); 
		
		// Httpbody에 데이터를 담을 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "35a74e573902361562dcc6f53ce81cd2");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// kakaoTokenRequest -> body데이터와, Header 데이터를 담을 엔티티가 된다.
		// 한마디로 HttpHeader, HttpBody를 하나의 오브젝트에 담는 것
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);	
		
		// exchange -> HttpEntity 오브젝트를 넣어준다. kakaoTokenRequest<- 여기에
		// Http 요청하기 -> POST, response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
					"https://kauth.kakao.com/oauth/token",
					HttpMethod.POST,
					kakaoTokenRequest,
					String.class
				);
		
		
		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// java에서 처리하기 위하여 java오브젝트로 바꾸는 것
		System.out.println("카카오 엑세스 토큰: "+oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브잭트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " +oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); 
		
		// kakaoTokenRequest -> body데이터와, Header 데이터를 담을 엔티티가 된다.
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);	
		
		// exchange -> HttpEntity 오브젝트를 넣어준다. kakaoTokenRequest<- 여기에
		// Http 요청하기 -> POST, response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
					"https://kapi.kakao.com/v2/user/me",
					HttpMethod.POST,
					kakaoProfileRequest2,
					String.class
				);
		System.out.println(response2.getBody());
	
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try { 
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// User 오브젝트 : username, password, email
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		// UUID 란?? 중복되지 않는어떤 특정 값을 만들어내는 알고리즘
		System.out.println("블로그서버 패스워드 : " + cosKey);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		// 가입자 or 비가입자 체크 해서 처리
		System.out.println(kakaoUser.getUsername());
		User originUser = 	userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("기존 회원이 아닙니다.");
			userService.회원가입(kakaoUser);
		}
		
		System.out.println("자동 로그인을 진행합니다.");
		// 로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
	
	// @AuthenticationPrincipal PrincipalDetail principal
	@GetMapping("/user/updateForm")
	public String updateForm() {	
		return "user/updateForm";
	}
}
