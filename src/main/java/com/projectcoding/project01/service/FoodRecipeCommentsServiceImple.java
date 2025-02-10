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

		// 댓글 추가
		int insertResult = foodRecipeCommentsMapper.insert(foodRecipeCommentsVO);
		log.info(insertResult + "행 댓글 추가");
		
		// 댓글 수 증가
		int updateResult = foodRecipeBoardMapper.updateCommentsCount(foodRecipeCommentsVO.getFoodRecipeBoardId(), 1);
		log.info(updateResult + "행 게시판 댓글 수 증가");

		// 댓글 수 증가 로깅
		log.info("게시판 ID " + foodRecipeCommentsVO.getFoodRecipeBoardId() + "의 댓글 수가 증가하였습니다.");
		
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
	public int deleteComments(int foodRecipeCommentsId, int foodRecipeBoardId) {
		log.info("deleteComments()");

		// 댓글 삭제
		int deleteResult = foodRecipeCommentsMapper.delete(foodRecipeCommentsId);
		log.info(deleteResult + " 행 댓글 삭제");

		// 댓글 수 감소
		int updateResult = foodRecipeBoardMapper.updateCommentsCount(foodRecipeBoardId, -1);
		log.info(updateResult + "행 게시판 댓글 수 감소");

		// 댓글 수 감소 로깅
		log.info("게시판 ID " + foodRecipeBoardId + "의 댓글 수가 감소하였습니다.");
		
		return 1;
	}
}
