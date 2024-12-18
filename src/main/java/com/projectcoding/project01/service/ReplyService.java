package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.ReplyVO;

public interface ReplyService {
	
	int createReply(ReplyVO replyVO);
	
	List<ReplyVO> getAllReply(int commentsId);
	
	int updateReply(int replyId, String replyContent);
	
	int deleteReply(int replyId, int commentsId);
}
 


