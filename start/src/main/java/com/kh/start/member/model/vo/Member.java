package com.kh.start.member.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

/*
 * Value Object
 * V	 O
 * 특징 : 불변성 => setter가 없음.
 * 		  VO 안에는 비즈니스 로직이 들어갈 수 있다.
 * 
 * @Value => 롬복에서 지원해주는 애노테이션
 */

@Value
@Getter
@Builder
public class Member {
	
	private Long memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String role;
	
}