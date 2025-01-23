package com.projectcoding.project01.questionservice;

import java.util.List;

import com.projectcoding.project01.questiondomain.QuestionReplyVO;

public interface QuestionReplyService {
	
	int createReply(QuestionReplyVO questionReplyVO); 
	
	List<QuestionReplyVO> getAllReplies(int questionCommentsId); 
	
	int updateReply(int questionReplyId, String questionReplyContent); 
	
	int deleteReply(int questionReplyId, int questionCommentsId); 
	
}
