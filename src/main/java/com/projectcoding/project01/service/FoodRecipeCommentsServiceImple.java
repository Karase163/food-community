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
	private FoodRecipeCommentsMapper foodrecipecommentsMapper;
	
	@Autowired
	private FoodRecipeBoardMapper foodrecipeboardMapper;
	
	@Transactional(value = "transactionaManager")
	
	@Override
	public int createComments(FoodRecipeCommentsVO foodrecipecommentsVO) {
		log.info("createComments()");
		
		int insertResult = foodrecipecommentsMapper.insert(foodrecipecommentsVO);
		log.info(insertResult + "행 댓글 추가");
		
		int updateResult = foodrecipeboardMapper.updateCommentsCount(foodrecipecommentsVO.getFoodrecipeboardId(),1);
		log.info(updateResult + "행 게시판 댓글 수 수정");
		return 1;
	}

	@Override
	public List<FoodRecipeCommentsVO> getAllComments(int foodrecipeboardId) {
		log.info("getAllComments()");
		List<FoodRecipeCommentsVO> list = foodrecipecommentsMapper.selectListByBoardId(foodrecipeboardId);
		return list;
	}

	@Override
	public int updateComments(int foodrecipecommentsId, String foodrecipecommentsContent) {
		log.info("updateComments()");
		FoodRecipeCommentsVO commentsVO = new FoodRecipeCommentsVO();
		commentsVO.setFoodrecipecommentsId(foodrecipecommentsId);
		commentsVO.setFoodrecipecommentsContent(foodrecipecommentsContent);
		int updateResult = foodrecipecommentsMapper.update(commentsVO);
		return updateResult;
	}
	
	@Transactional(value = "transactionManager")
	@Override
	public int deleteComments(int commentsId, int boardId) {
		log.info("deleteComments()");
		int deleteResult = foodrecipecommentsMapper.delete(commentsId);
		
		log.info(deleteResult + " 행 댓글 삭제");
		int updateResult = foodrecipeboardMapper.updateCommentsCount(boardId, -1);
		log.info(updateResult + "행 게시판 댓글 수 수정");
		return 1;
	}

}
