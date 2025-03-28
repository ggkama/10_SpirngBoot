package com.example.demo.busan.model.service;

import java.util.List;

import com.example.demo.busan.model.dto.Comment;

public interface BusanService {

	String requestGetBusan(int pageNo);

	String requestGetBusanDetail(int pk);
	
	void saveComment(Comment comment); 
	
	List<Comment> selectCommentList(Long seq); 
	
	
}
