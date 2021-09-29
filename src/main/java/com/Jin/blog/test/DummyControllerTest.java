package com.Jin.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Jin.blog.model.RoleType;
import com.Jin.blog.model.User;
import com.Jin.blog.repository.UserRepository;

// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController // 응답만 해주는 컨트롤러
public class DummyControllerTest {
	
	@Autowired // 의존성 주입(DI) : 특정 객체에 필요한 객체를 외부로부터 받아와 연결하는것
	private UserRepository userRepository;
	
	// save 함수는 ID를 전달하지 않으면 insert를 해주고
	// save 함수는 id를 전달하면 해당id에 대한 데이터가 있으면 update 해주고
	// save 함수는 id를 전달하면 해당id에 대한 데이터가 없으면 insert를 해준다.
	// email, password
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException  e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제 되었습니다." + id;
	}
	
	@Transactional // 함수 종료시 자동 Commit
	@PutMapping("/dummy/user/{id}")
	//json 데이터를 요청 -> java Object(MessageConverter의 Jackson라이브러리가 반환해서)데이터로 받아짐 
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " +  requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패 하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// userRepository.save(user);
		
		// 더티체킹을 하면  트렌젝션이 변경을 감지 -> 영속성을 만들어 준 후 -> DB에 수정을 날려준다.
		return user;
	}
	
	// http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		
		return userRepository.findAll();
	}
	
	// 한 페이지당 2건의 데이터를 리턴 받기
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size =2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pageingUser = userRepository.findAll(pageable);
		
		List<User> users = pageingUser.getContent();
		return pageingUser;
	}
	
	//{id} 주소로 파마레터를 전달 받을 수 있음.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		// user/4 번을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null 될 것이다.
		// 그럼 return null 이 리턴 됩니다. 그럼 프로그램에 문제가 있다.
		// Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return을 해라.
		// findByid().get() -> return일이 전혀 없다,
		
		// 람다식
//		User user = userRepository.findById(id).orElseThrow(()-> {
//			return new IllegalArgumentException("해당 사용자는 없습니다. ");
//		});
//		return user;
	
		 User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		 
		 @Override 
		 public IllegalArgumentException get() { 
			 return new IllegalArgumentException("해당 유저는 없습니다. "); 
		 	}
		}); 
		  
		 // 요청 : 웹 브라우저
		 // user 객체 = 자바 오브젝트
		 // 변환 (웹브라우저가 이해 할 수 있는 데이터) -> json(Gson	라이브러리)
		 // 스프링부트 = 	MessageConverter 라는 애가 응답시에 자동 작동
		 // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter 가 Jackson 라이브러리를 호출해서
		 // user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		  return user; 
}
	
	// http://localhost:8000/blog/dummy/join (요청)
	// http body 에 username, password, emaill 데이터를 	가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) { // key = value (약속된 규칙)
		
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println(user.getRole());
		System.out.println(user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료 되었습니다.";
	}
}
