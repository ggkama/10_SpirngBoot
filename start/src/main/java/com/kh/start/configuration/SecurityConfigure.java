package com.kh.start.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kh.start.configuration.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfigure {

	private final JwtFilter filter;
	// 스프링 시큐리티에 필터체인을 정의하는 메서드
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		//return httpSecurity.formLogin().disable().build(); 과거에 사용하던 코드
		
		/* 그 다음에 쓰는 코드(너무 김)
		return httpSecurity.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>> () {
			@Override
			public void customize(FormLoginConfigurer<HttpSecurity> t) {
				t.disable();
			}
		}).build();
		
		
		Cross Site Request Foregery
		
		ex) <img src="http://우리도메인/logout" />
			<form action="http://우리도메인/logout" action= "post">
				<input type="hidden" value="admin" name="userId">
				<button>눌러보세용~</button>
			</form>
			
		이런 식으로 공격 할 수도 있음
		*/
		
		return httpSecurity.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(requests -> {
					requests.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/refresh", "/members").permitAll(); // 포스트 요청으로 오는 저 두개 요청은 다 허가
					requests.requestMatchers("/admin/**").hasRole("ADMIN"); // 어드민으로 시작하는 요청은 반드시 롤에 어드민이어야함					
					requests.requestMatchers(HttpMethod.GET, "/uploads/**", "/boards/**", "/comments/**").permitAll(); // 모든유저에게 사진, 게시글, 댓글 허용
					requests.requestMatchers(HttpMethod.PUT, "/members", "/boards/**").authenticated(); // 포스트 요청으로 오는 members는 걸러줘야함?
					requests.requestMatchers(HttpMethod.DELETE, "/members", "/boards/**").authenticated();
					requests.requestMatchers(HttpMethod.POST,  "/boards", "/comments").authenticated(); //
				})
				 .sessionManagement(manager ->
                 manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				 .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				 .build(); 
				
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig ) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	
}