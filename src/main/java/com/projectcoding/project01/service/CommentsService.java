package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.CommentsVO;

public interface CommentsService {
	
	int createComments(CommentsVO commentsVO);
	
	List<CommentsVO> getAllComments(int boardId);
	
	int updateComments(int commentsId, String commentsContent);
	
	int deleteComments(int commentsId, int boardId);
}

