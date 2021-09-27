package com.Jin.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Transactional	//아래 public int 회원가입을 트렌젝션으로 묶는다. 전체가 성공하면 commit, 실패하면 rollback
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 1234원문
		String encPassword = encoder.encode(rawPassword);	// 해쉬가 된다.
		user.setPassword(encPassword);
		userRepository.save(user);
		user.setRole(RoleType.USER);
	}
}
