package com.kh.start.token.model.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kh.start.auth.util.JwtUtil;
import com.kh.start.exception.GlobalExceptionHandler;
import com.kh.start.token.model.dao.TokenMapper;
import com.kh.start.token.model.vo.RefreshToken;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 1. AccessToken 만들기. 2. RefreshToken 만들기
// 3. RefreshToken Table에 INSERT하기
// 4. 만료기간이 끝난 refreshToken DELETE하기
// 5. 사용자가 RefreshToken을 가지고 증명하려 할 때 DB가서 조회해오기

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final GlobalExceptionHandler globalExceptionHandler;
   
   // 여기service 역할 : 로그인 중 아이디 패스워드 인증 성공 후 토큰 생성하러 옴
   
   private final JwtUtil tokenUtil;
   private final TokenMapper tokenMapper;
   
   
   @Override
   public Map<String, String> generateToken(String username, Long memberNo) {
      
      
      // 1,2번 // ACCESS, REFRESH토큰 생성하기
      Map<String, String> tokens = createToken(username);
      
      
      // 3번 만들어진 refresh를 DB에 저장(memberNo, refresehToken, expiration 필요)
      saveToken(tokens.get("refreshToken"),memberNo);
      
      
      // 4번 // 기간이 지난 토큰 삭제하기, 현재시간을 매개변수로 넣음
      tokenMapper.deleteExpiredRefreshToken(System.currentTimeMillis());
      
      
      
      
      return tokens;
   }
   
   
   // 3. 토큰 DB에 저장
   private void saveToken(String refreshToken, Long memberNo) {
      
      RefreshToken token = RefreshToken.builder()
                              .token(refreshToken)
                              .memberNo(memberNo)
                              .expiration(System.currentTimeMillis()+36000000L*72)
                              .build();
      tokenMapper.saveToken(token);
            
   }
   
   
   public Map<String, String> createToken(String username) {
      String accessToken = tokenUtil.getAccessToken(username);
      String refreshToken = tokenUtil.getRefreshToken(username);
      
      Map<String, String> tokens = new HashMap();
      tokens.put("accessToken", accessToken);
      tokens.put("refreshToken", refreshToken);
      
      return tokens;
   }
   
   @Override
   public Map<String, String> refreshToken(String refreshToken){
	   
	   RefreshToken token = RefreshToken.builder()
			   							.token(refreshToken)
			   							.build();
	   RefreshToken responseToken = tokenMapper.findByToken(token);
	   // 오늘 숙제 1. JwtUtil을 이용하여 refreshToken을 Parsing한 뒤
	   //			 MEMBER_NO 및 token값을 이용하여 SELECT하는 문으로 수정하기	   
	   // 오늘 숙제 2. 예외발생시 예외처리 핸들러 커스텀 익셉션 클래스를 구현하여
	   // 			 예외처리기가 처리하게 하기
	   if(responseToken == null || token.getExpiration() < System.currentTimeMillis()) {
		   throw new RuntimeException("유효하지 않은 토큰");
	   }
	   
	   String username = getUsernameByToken(refreshToken);
	   long memberNo = responseToken.getMemberNo();
	   
	   return generateToken(username, memberNo);
	   
	   
   }
   
   private String getUsernameByToken(String refreshToken) {
	   Claims claims = tokenUtil.parseJwt(refreshToken);
	   return claims.getSubject();
	   
	   
   }

   
}
