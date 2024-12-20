package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.FoodRecipeCommentsVO;

public interface FoodRecipeCommentsService {
	
	int createComments(FoodRecipeCommentsVO foodrecipecommentsVO);
	
	List<FoodRecipeCommentsVO> getAllComments(int foodrecipeboardId);
	
	int updateComments(int foodrecipecommentsId, String foodrecipecommentsContent);
	
	int deleteComments(int foodrecipecommentsId, int foodrecipeboardId);
}

