package com.kh.start.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChangePasswordDTO {
	
	@Size(min=4, max=15, message="비밀번호는 4~ 15자 사이 입력")
	@NotBlank(message="현재 비밀번호를 꼭 입력해 주세요")
	private String currentPassword;
	
	@Size(min=4, max=15, message="비밀번호는 4~ 15자 사이 입력")
	@NotBlank(message="새 비밀번호를 꼭 입력해 주세요")
	private String newPassword;
	
}