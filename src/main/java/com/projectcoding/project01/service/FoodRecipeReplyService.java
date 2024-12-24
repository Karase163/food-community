package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.FoodRecipeReplyVO;

public interface FoodRecipeReplyService {
	
	int createReply(FoodRecipeReplyVO foodRecipeReplyVO); 
	
	List<FoodRecipeReplyVO> getAllReplies(int foodRecipeCommentsId); 
	
	int updateReply(int foodRecipeReplyId, String foodRecipeReplyContent); 
	
	int deleteReply(int foodRecipeReplyId, int foodRecipeCommentsId); 
}
