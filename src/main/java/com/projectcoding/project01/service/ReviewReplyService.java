package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.ReviewReplyVO;

public interface ReviewReplyService {
	
	int createReply(ReviewReplyVO reviewReplyVO); 
	
	List<ReviewReplyVO> getAllReplies(int reviewCommentsId); 
	
	int updateReply(int reviewReplyId, String reviewReplyContent); 
	
	int deleteReply(int reviewReplyId, int reviewCommentsId); 
	
}