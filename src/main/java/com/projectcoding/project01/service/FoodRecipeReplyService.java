package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.FoodRecipeReplyVO;

public interface FoodRecipeReplyService {
	
	int createReply(FoodRecipeReplyVO foodrecipereplyVO);
	
	List<FoodRecipeReplyVO> getAllReply(int foodrecipecommentsId);
	
	int updateReply(int foodrecipereplyId, String foodrecipereplyContent);
	
	int deleteReply(int foodrecipereplyId, int foodrecipecommentsId);
}
 


