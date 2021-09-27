package com.Jin.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Jin.blog.model.User;

// DAO
// 자동 Bean 등록
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{
	
	
	
}


// JPA Naming 쿼리
// SELECT * FROM user WHERE username = ?1 AND password = ?2; => Spring JPA Naming 전략
// User findByUsernameAndPassword(String username, String password);

// @Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2; ", nativeQuery = true)
// User login(String username, String password);