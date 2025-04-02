package com.kh.start.member.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.start.member.model.dto.ChangePasswordDTO;
import com.kh.start.member.model.dto.MemberDTO;
import com.kh.start.member.model.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;

	// 회원가입 자원 요청을 받으면서
	// 데이터들 받아서
	// 데이터 가공
	// 서비스로 넘기기
	// 서비스로부터 된걸 받아서
	// JSON 타입으로 상태코드와 함께 넘긴다.
	
	// /members => 이런 형태로 하기로 한게 약속
	// GET 						=> 조회 요청 (SELECT)
	// GET(/members/멤버번호) 	=> 멤버 번호로 조건을 걸어서 단일 조회 요청 (SELECT) 
	// POST						=> 데이터 생성 요청(INSERT)
	// PUT						=> 데이터 갱신 요청(UPDATE)
	// DELETE					=> 데이터 삭제 요청(DELETE)
	
	// 계층 구조로 식별할 때 /자원/PK
	// 요청 시 전달 값이 많을 때 /자원?키=값&키=값&키=값
	
	@PostMapping
	public ResponseEntity<?> signUp(@RequestBody @Valid MemberDTO member){
		
		memberService.signUp(member);
//		log.info("여기오닝? {}", member);
		
		return ResponseEntity.status(201).build();
	}
	
	/*
	 * 원래 비밀번호 :
	 * 바꿀 비밀번호 :
	 * 바꿀 비밀번호 확인 :
	 */
	
	
	// 회원 정보 수정(비밀번호 수정)
	@PutMapping
	public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDTO passwordEntity){
		log.info("비밀번호 잘넘어옴? : {} " , passwordEntity);
		memberService.changePassword(passwordEntity);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
		
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteByPassword(@RequestBody Map<String, String> request){
		
		// log.info("이게오나요? {} ", request);
		memberService.deleteByPassword(request.get("password"));
		return ResponseEntity.ok("okok");
	}
	
	
	
	
}