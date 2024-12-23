package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.domain.FoodRecipeCommentsVO;
import com.projectcoding.project01.persistence.FoodRecipeBoardMapper;
import com.projectcoding.project01.persistence.FoodRecipeCommentsMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FoodRecipeCommentsServiceImple implements FoodRecipeCommentsService {
	
	@Autowired
	private FoodRecipeCommentsMapper foodRecipeCommentsMapper;
	
	@Autowired
	private FoodRecipeBoardMapper foodRecipeBoardMapper;
	
	@Transactional(value = "transactionManager")
	
	@Override
	public int createComments(FoodRecipeCommentsVO foodRecipeCommentsVO) {
		log.info("createComments()");
					
		int insertResult = foodRecipeCommentsMapper.insert(foodRecipeCommentsVO);
		log.info(insertResult + "행 댓글 추가");
		
		int updateResult = foodRecipeBoardMapper.updateCommentsCount(foodRecipeCommentsVO.getFoodRecipeBoardId(), 1);
		log.info(updateResult + "행 게시판 댓글 수 수정");
		return 1;
	}

	@Override
	public List<FoodRecipeCommentsVO> getAllComments(int foodRecipeBoardId) {
		log.info("getAllComments()");
		List<FoodRecipeCommentsVO> list = foodRecipeCommentsMapper.selectListByBoardId(foodRecipeBoardId);
		return list;
	}

	@Override
	public int updateComments(int foodRecipeCommentsId, String foodRecipeCommentsContent) {
		log.info("updateComments()");
		FoodRecipeCommentsVO commentsVO = new FoodRecipeCommentsVO();
		commentsVO.setFoodRecipeCommentsId(foodRecipeCommentsId);
		commentsVO.setFoodRecipeCommentsContent(foodRecipeCommentsContent);
		int updateResult = foodRecipeCommentsMapper.update(commentsVO);
		return updateResult;
	}
	
	@Transactional(value = "transactionManager")
	@Override
	public int deleteComments(int commentsId, int boardId) {
		log.info("deleteComments()");
		int deleteResult = foodRecipeCommentsMapper.delete(commentsId);
		
		log.info(deleteResult + " 행 댓글 삭제");
		int updateResult = foodRecipeBoardMapper.updateCommentsCount(boardId, -1);
		log.info(updateResult + "행 게시판 댓글 수 수정");
		return 1;
	}
}
