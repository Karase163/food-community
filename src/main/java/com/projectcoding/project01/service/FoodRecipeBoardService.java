package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.FoodRecipeBoardVO;
import com.projectcoding.project01.util.Pagination;

public interface FoodRecipeBoardService {
	int createBoard(FoodRecipeBoardVO foodRecipeboardVO);

	List<FoodRecipeBoardVO> getAllBoards();

	FoodRecipeBoardVO getBoardById(int foodRecipeboardId);

	int updateBoard(FoodRecipeBoardVO foodRecipeboardVO);

	int deleteBoard(int foodRecipeboardId);

	List<FoodRecipeBoardVO> getPagingBoards(Pagination pagination);

	int getTotalCount();
}
