package com.Jin.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 스프링이 com.Jin.blog 패키지 이하를 스캔해서  모든 파일을  메모리에 new 하는 것이 아니다.
						// 특정 어노테이션이 붙어있는  클래스 파일을 new(Ioc)해서 스프링 컨테이너에서관리 해준다.
public class BlogControllerTest {
	
	// http://localhost:8080/test/hello
	@GetMapping(value="/test/hello")
	public String Hello() {
		return "<h1>Hello spring boot</h1>";
	}
}
