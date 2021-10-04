package com.Jin.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// ORM -> Java(다른 언어 포함) Object를 Table로 Mapping해주는 기술
@Entity // User 클래스 가 MySQL에 테이블이 생성이 된다.
 // @DynamicInsert // insert시 null 인 컬럼을 제외 시켜준다.
public class User { 
	
	@Id //Primary key(기본키)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; //오라클(시퀀스), auto_increment
	
	@Column(nullable = false, length = 100, unique = true) //null값을 주면 안되니까 Column이라는 어노테이션 추가 컬럼, // unique = true 중복 값을 못넣게 하기
	private String username; // 아이디
	
	@Column(nullable = false, length = 100)	// 해쉬 암호화
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	// @ColumnDefault("'user'")
	 // 컬럼 디폴트 어노테이션 : " ' ' " 문자라고 확인을 해줘야 한다.
	// ADMIN, USER MANAGER
	// DB는 RoleType 이란게 없다.
	@Enumerated(EnumType.STRING) // Enum 이 String이라는 것을 알려줘야 한다.
	private RoleType role; // Enum을 쓰는게 좋다. -> Admin(관리자), user(일반유저), manager 권한을 준다.(도메인: 어떤 범위가 정해졌다.)
	
	private String oauth;	// kakao, google 어떤 로그인 방식인지 알아내기
	
	@CreationTimestamp // 시간 자동 입력 어노테이션, 직접시간을 넣으려면(Timestamp.valueOf(LocalDateTime.now())
	private Timestamp createDate;
	
}
