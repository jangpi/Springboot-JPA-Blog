package com.Jin.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Jin.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	
}
