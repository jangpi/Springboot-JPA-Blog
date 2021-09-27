package com.Jin.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

// Getter, Setter
@Data

// 생성자
//@AllArgsConstructor
// 빈생성자
@NoArgsConstructor
// final 하는 이유 불변성을 위하여
//@RequiredArgsConstructor
public class Member {
	
	// private 로 만드는 이유 : 
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	

}
