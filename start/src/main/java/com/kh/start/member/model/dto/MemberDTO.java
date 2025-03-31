package com.kh.start.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class MemberDTO {
	
	private Long memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String role;

		
	
	
	
}
