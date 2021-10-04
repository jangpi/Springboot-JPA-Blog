package com.Jin.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Jin.blog.model.RoleType;
import com.Jin.blog.model.User;
import com.Jin.blog.repository.UserRepository;

@Service	// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해준다. IoC(메모리를 대신 띄어준다)
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		
		// orElseGet(()-> 만약에 회원을 찾는데 없으면 빈 객체를 리턴해라 new User();
		User user = userRepository.findByUsername(username).orElseGet(()->{	
			return new User();
		});
		return user;
	}
	
	@Transactional	//아래 public int 회원가입을 트렌젝션으로 묶는다. 전체가 성공하면 commit, 실패하면 rollback
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 1234원문
		String encPassword = encoder.encode(rawPassword);	// 해쉬가 된다.
		user.setPassword(encPassword);
		userRepository.save(user);
		user.setRole(RoleType.USER);
	}
	
	@Transactional
	public void 회원수정(User user) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 UPDATE문을 날려준다.
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		// Validate 체크 => oauth 필드에 값이 없으면 수정가능 예) oauth("kakao")
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		
		// 끝날때 회원수정 함수 종료시 = 서비스 종료 = 트렌젝션이 종료 = commit 자동으로 됨
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
	}
	
	
}
