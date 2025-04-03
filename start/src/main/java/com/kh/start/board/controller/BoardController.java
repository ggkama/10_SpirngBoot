package com.kh.start.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.start.board.model.dto.BoardDTO;
import com.kh.start.board.model.service.BoardService;
import com.kh.start.configuration.filter.JwtFilter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

	private final BoardService boardService;
	
	@PostMapping
	public ResponseEntity<?> save(@Valid BoardDTO board,
								  @RequestParam(name="file", required=false) MultipartFile file){
		//log.info("게시글 정보 : {}, 파일 정보 : {}", board, file.getOriginalFilename());
		
		boardService.save(board,file);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	@GetMapping
	public ResponseEntity<List<BoardDTO>> findAll(@RequestParam(name="page",
																defaultValue="0")
													int page){
		return ResponseEntity.ok(boardService.findall(page));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BoardDTO> findById(@PathVariable(name="id") 
											 @Min(value=1, message ="너무작음")Long boardNo){
		return ResponseEntity.ok(boardService.findById(boardNo));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BoardDTO> update(@PathVariable(name="id") Long boardNo,
											BoardDTO board,
											@RequestParam(name="file" , required = false) MultipartFile file){
	board.setBoardNo(boardNo);
	// log.info("{}",board);
	return ResponseEntity.status(HttpStatus.CREATED).body(boardService.update(board, file));
}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name="id") Long boardNo){
		boardService.deleteById(boardNo);
		return ResponseEntity.ok().build();
	}

	
	
}


