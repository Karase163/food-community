package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.domain.FoodRecipeReplyVO;
import com.projectcoding.project01.persistence.FoodRecipeBoardMapper;
import com.projectcoding.project01.persistence.FoodRecipeReplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FoodRecipeReplyServiceImple implements FoodRecipeReplyService {

    @Autowired
    private FoodRecipeReplyMapper foodRecipeReplyMapper;

    @Autowired
    private FoodRecipeBoardMapper foodRecipeBoardMapper;

    @Transactional(value = "transactionManager")

    @Override
    public int createReply(FoodRecipeReplyVO foodRecipeReplyVO) {
        log.info("createReply()");
        
        int insertReplyResult = foodRecipeReplyMapper.insert(foodRecipeReplyVO);
        log.info(insertReplyResult + "행 답글 추가");
        
        int updateReplyCountResult = foodRecipeBoardMapper.updateReplyCount(foodRecipeReplyVO.getFoodRecipeBoardId(), 1); // 수정: 변수명 변경
        log.info(updateReplyCountResult + "행 게시판 답글 수 수정");
        return 1; 
    }

    @Override
    public List<FoodRecipeReplyVO> getAllReplies(int foodRecipeCommentsId) {
        log.info("getAllReplies()");
        List<FoodRecipeReplyVO> list = foodRecipeReplyMapper.selectListByCommentsId(foodRecipeCommentsId);
        return list;
    }
    
    @Override
    public int updateReply(int foodRecipeReplyId, String foodRecipeReplyContent) {
        log.info("updateReply()");
        
        FoodRecipeReplyVO replyVO = new FoodRecipeReplyVO();
        replyVO.setFoodRecipeReplyId(foodRecipeReplyId);
        replyVO.setFoodRecipeReplyContent(foodRecipeReplyContent);
        
        int updateReplyResult = foodRecipeReplyMapper.update(replyVO);
        return updateReplyResult; 
    }

    @Transactional(value = "transactionManager")
    @Override
    public int deleteReply(int foodRecipeReplyId, int foodRecipeBoardId) {
        log.info("deleteReply()");
        
        int deleteReplyResult = foodRecipeReplyMapper.delete(foodRecipeReplyId);
        log.info(deleteReplyResult + "행 답글 삭제");
        int updateReplyCountResult = foodRecipeBoardMapper.updateReplyCount(foodRecipeBoardId, -1);
        log.info(updateReplyCountResult + "행 게시판 답글 수 수정");
        
        return 1; 
    }
}
