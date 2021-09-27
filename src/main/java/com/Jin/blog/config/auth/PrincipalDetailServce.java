package com.Jin.blog.config.auth;

import java.nio.file.attribute.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Jin.blog.model.User;
import com.Jin.blog.repository.UserRepository;

@Service // Bean 등록
public class PrincipalDetailServce implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	// 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
	// password 부분 처리는 알아서 한다.
	// 해당 username DB에 있는지만 확인 해준다. 그 확인은 loadUserByUsername <- 여기서함
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User Principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
				});
		return new PrincipalDetail(Principal); // 시큐리티 세션에 유저정보가 저장이 된다.
	}
}