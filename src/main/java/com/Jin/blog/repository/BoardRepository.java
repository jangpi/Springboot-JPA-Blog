package com.Jin.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Jin.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	
    @Modifying
    @Query(value = "update Board set count = count + 1 where id = id", nativeQuery = true)
    int 조회수(Integer id);
}
