package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.FoodRecipeCommentsVO;

public interface FoodRecipeCommentsService {

    int createComments(FoodRecipeCommentsVO foodRecipeCommentsVO);

    List<FoodRecipeCommentsVO> getAllComments(int foodRecipeBoardId); 

    int updateComments(int foodRecipeCommentsId, String foodRecipeCommentsContent); 

    int deleteComments(int foodRecipeCommentsId, int foodRecipeBoardId);

}
