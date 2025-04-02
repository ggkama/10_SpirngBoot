package com.kh.start.auth.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.kh.start.auth.model.vo.CustomUserDetails;
import com.kh.start.exception.CustomAuthenticationException;
import com.kh.start.member.model.dto.MemberDTO;
import com.kh.start.token.model.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final AuthenticationManager authenticationManager;
//	private final JwtUtil jwtUtil;
	private final TokenService tokenService;
	
	@Override
	public Map<String, String> login(MemberDTO member) {
		
		// 1. 유효성 검사
		
		// 2. 아이디가 db에 존재하는가?
		
		// 3. 비밀번호는 컬럼에 존재하는 암호문이 사용자가 입력한 평문으로 만들어진 것이 맞는가
		
		// 사용자 인증
		
		Authentication authentication = null;
		
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getMemberPw()));
		}catch(AuthenticationException e) {
			throw new CustomAuthenticationException("아이디 또는 비밀번호 잘못 입력");
		}
		
		// SecurityContextHolder 안에
		// SecurityContext 안에
		// Principal 안에 값이 저장됨.
		// 그래서 호출
		CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
		
		log.info("로그인 성공....");
		log.info("인증에 성공한 사용자의 정보 : {}", user);
		
		
//		--------------------------------------------------------------------------------------------------------------------------
		
		// 토큰 발급
		// JWT
		// Access 
		
		/*
		 * String accessToken = jwtUtil.getAccessToken(user.getUsername()); String
		 * refreshToken = jwtUtil.getRefreshToken(user.getUsername());
		 */
		
		Map<String, String> loginResponse = tokenService.generateToken(user.getUsername()
																	  ,user.getMemberNo());
		
		/*
		 * log.info("생성된 {} 의 accessToken 값 : {}", user.getUsername(), accessToken);
		 * log.info("생성된 {} 의 refreshToken 값 : {}", user.getUsername(), refreshToken);
		 */
		
		// 해시코드가 다르면 다른 객체다 O => 같은 값으로 해시 돌리면 항상 같은 결과가 나옴
		// 해시코드가 같으면 같은 객체다 X => 해시충돌이 일어날 수 있어서 => 비둘기집 원리 ㅋㅋㅋㅋㅋ
		
		loginResponse.put("memberId", user.getUsername());
		loginResponse.put("memberName", user.getMemberName());
		
		return loginResponse;
	}

}