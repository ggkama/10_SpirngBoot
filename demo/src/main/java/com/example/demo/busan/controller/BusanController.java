package com.example.demo.busan.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.busan.model.dto.Comment;
import com.example.demo.busan.model.service.BusanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("busans")
@RequiredArgsConstructor
public class BusanController {
	
	private final BusanService busanService;
	@GetMapping
	public ResponseEntity<String> getBusanFoods(@RequestParam(name="pageNo", defaultValue="1")int pageNo) {

		
		String responseData = busanService.requestGetBusan(pageNo); 
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<String> getBusanDetail(@PathVariable(name="id") int id){
		String response = busanService.requestGetBusanDetail(id);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/comments") // 식당번호, 내용 => 가공
	public ResponseEntity<?> save(@RequestBody Comment comment){
		// log.info("댓글 : {}", comment);
		busanService.saveComment(comment);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/comments/{id}")
	public ResponseEntity<List<Comment>> getComments(@PathVariable(name="id") Long id){
		List<Comment> comments = busanService.selectCommentList(id);
		return ResponseEntity.ok(comments);
	}
	
	
	// 4절하기 부트로 바꿔서 돌리기
	
	
}
