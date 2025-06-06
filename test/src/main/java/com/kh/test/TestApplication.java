package com.kh.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {
	/*
	 * AutoConfiguration(자동 구성)
	 * 
	 * SpringBoot의 핵심 기능
	 * 애플리케이션의 클래스패스에 존재하는 라이브러리 및 설정을 기반으로
	 * Bean을 자동으로 구성해줌
	 * 
	 * @EnableAutoConfigration
	 * 
	 * 스프링 부트의 자동구성을 활성화해주는 애노테이션
	 * @SpringBootApplication 내부에 포함되어 있기 때문에 작성할 일이 잘 없음
	 * 
	 * 동작순서
	 * 
	 * 어플리케이션 실행 -> @SpringBootApplication애노테이션이 붙은 클래스의
	 * 					 main메서드가 호출됨
	 * 자동구성 활성화 -> @EnableAutoConfiguration을 통해 자동구성을 활성화
	 * 
	 * @EnableAutoConfiguration
	 * @ConponentScan
	 * @Configuration
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
