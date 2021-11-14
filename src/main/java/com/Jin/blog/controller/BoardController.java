package com.Jin.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Jin.blog.repository.BoardRepository;
import com.Jin.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 컨트롤러에서 세션을 어떻게 찾는지??, 세션 접근방법
	// @AuthenticationPrincipal PrincipalDetail principal
	
	// @Controller 은 작동 할 때 viewResolver 작동!! -> 해당 index 페이지로 model 정보를 들고 간다.
	// viewResolver 이란 ?  return값 앞 뒤로 프리픽스, 서픽스를 붙힌다.
	// model 이란 Request 정보이다.
	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size =3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {	
		
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index";		
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		
		model.addAttribute("count", boardService.조회수(id));
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	// User권한 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
