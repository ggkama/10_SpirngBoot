package com.kh.start.board.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.start.auth.model.vo.CustomUserDetails;
import com.kh.start.auth.service.AuthService;
import com.kh.start.board.model.dao.BoardMapper;
import com.kh.start.board.model.dto.BoardDTO;
import com.kh.start.board.model.vo.Board;
import com.kh.start.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	private final AuthService authService;
	private final FileService fileService;
	
	@Override
	public void save(BoardDTO board, MultipartFile file) {

		// board에는 게시글 정보
		// file 파일이 존재할 경우 파일의 정보
		
		//Authentication auth =
		//SecurityContextHolder.getContext().getAuthentication();
		
		
		
		CustomUserDetails user = authService.getUserDetails();		
		Long memberNo = user.getMemberNo();
		// board.setBoardWriter(String.valueOf(memberNo));
		Board requestData = null;
		
		
		// 첨부파일 관련된 값
		if(file != null && !file.isEmpty()) {
			
			// 이름바꾸기
			
			// 원본파일명 뽑기
			
			// 저장위치
			
			// 파일올리는 메서드 호출
			String filePath = fileService.store(file);
			// board File관련 필드 값 set
			// board.setBoardFileUrl(filePath);
			requestData = Board.builder()
					 .boardTitle(board.getBoardTitle())
					 .boardContent(board.getBoardContent())
					 .boardWriter(String.valueOf(memberNo))
					 .boardFileUrl(filePath)
					 .build();
			
		} else {
			requestData = Board.builder()
							   .boardTitle(board.getBoardTitle())
							   .boardContent(board.getBoardContent())
							   .boardWriter(String.valueOf(memberNo))
							   .build();
		}
		
		boardMapper.save(requestData);
		
	}

	@Override
	public List<BoardDTO> findall(int pageNo) {
		int size = 3;
		RowBounds rowBounds = new RowBounds(pageNo * size, size);
		return boardMapper.findAll(rowBounds);
	}

	@Override
	public BoardDTO findById(Long boardNo) {
		// BoardDTO board = getBoardOrThrow(boardNo);
		return getBoardOrThrow(boardNo); 
	}

	private BoardDTO getBoardOrThrow(Long boardNo) {
		BoardDTO board = boardMapper.findById(boardNo);
		if(board == null) {
			throw new RuntimeException("존재하지 않는 게시글 요청!");
		}
		return board;
	}
	
	@Override
	public BoardDTO update(BoardDTO board, MultipartFile file) {
		
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.store(file);
			board.setBoardFileUrl(filePath);
		}
		
		boardMapper.update(board);
		return board;
	}

	@Override
	public void deleteById(Long boardNo) {
		boardMapper.deleteById(boardNo);
		

	}

}
