package com.Jin.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
	
	@Id //(Primaiy key(기본키))
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링 전략
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content;
	
	@ColumnDefault("0") // 조회수 컬럼 
	private int count;
	
	@ManyToOne(fetch = FetchType.EAGER) // fetch : 무조건 들고와야 하기 때문에 패치 전략이 필요하다.
	// 한 사람의 유저는 (One), 게시판을 여러 개 작성 가능 하다.(Many)
	@JoinColumn(name = "userId") // FK(외레키) 등록을 해준다. DB는 오브젝트를 저장할 수 없다.
	private User user;
	
	@OneToMany (mappedBy = "board", fetch = FetchType.EAGER) // 무조건 들고 올 때 EAGER 전략 
	// 필요할 때 LAZY 전략을 쓰면 된다.
	// mappedBy 가 있으면 연관관계의 주인이 아니다. 난 FK가 아닙니다. DB에 컬럼 X
	private List<Reply> reply;
	
	@CreationTimestamp // 등록시 날짜, 시간 자동 등록
	private Timestamp createDate;
}
