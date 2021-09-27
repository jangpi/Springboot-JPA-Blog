package com.Jin.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Jin.blog.model.Board;
import com.Jin.blog.model.User;
import com.Jin.blog.repository.BoardRepository;

@Service	
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional	
	public void 글쓰기(Board board, User user) {	// title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
}
