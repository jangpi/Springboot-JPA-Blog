package com.Jin.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Jin.blog.dto.ReplySaveRequestDto;
import com.Jin.blog.dto.ResponseDto;
import com.Jin.blog.service.ReplyService;

@RestController
public class ReplyApiController {
	
	@Autowired
	private ReplyService replyService;
	
	// 데이터를 받을 때 컨트롤러에서 Dto를 만들어서 받는게 좋다.
	//dto 사용하지 않는 이유!!?
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto){ 
		replyService.댓글달기(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
		replyService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
