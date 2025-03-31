package com.kh.start.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigure {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		//return httpSecurity.formLogin().disable().build();
		/*
		 * return httpSecurity.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
		 
			
			@Override
			public void customize(FormLoginConfigurer<HttpSecurity> t) {
				
				t.disable();
			}
		}).build(); 
		
		Cross Site Request Forgery
		
		<img src="http://도메인/logout" />
		
		<form action="http://도메인/logout" action="post">
			<input type="hidden" value="admin" name="userId" />
			<button>버튼누르기</button>
		</form>
		
		*/
		return httpSecurity.formLogin(AbstractHttpConfigurer::disable)
							.httpBasic(AbstractHttpConfigurer::disable)
							.csrf(AbstractHttpConfigurer::disable)
							.build();
		
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
}
