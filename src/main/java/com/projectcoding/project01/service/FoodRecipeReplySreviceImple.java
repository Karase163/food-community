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
public class FoodRecipeReplySreviceImple implements FoodRecipeReplyService {

    @Autowired
    private FoodRecipeReplyMapper replyMapper;

    @Autowired
    private FoodRecipeBoardMapper boardMapper;

    @Transactional(value = "transactionManager")
    @Override
    public int createReply(FoodRecipeReplyVO foodrecipereplyVO) {
        log.info("createReply()");
        
        int insertResult = replyMapper.insert(foodrecipereplyVO);
        log.info(insertResult + "행 답글 추가");
        
        int updateResult = boardMapper.updateReplyCount(foodrecipereplyVO.getFoodrecipeboardId(), 1);
        log.info(updateResult + "행 게시판 답글 수 수정");
        return 1; 
    }

    @Override
    public List<FoodRecipeReplyVO> getAllReply(int commentsId) {
        log.info("getAllReplies()");
        List<FoodRecipeReplyVO> list = replyMapper.selectListByCommentsId(commentsId);
        return list;
    }
    
    @Override
    public int updateReply(int replyId, String replyContent) {
        log.info("updateReply()");
        
        FoodRecipeReplyVO replyVO = new FoodRecipeReplyVO();
        replyVO.setReplyId(replyId);
        replyVO.setReplyContent(replyContent);
        
        int updateResult = replyMapper.update(replyVO);
        return updateResult; 
    }

    @Transactional(value = "transactionManager")
    @Override
    public int deleteReply(int replyId, int boardId) {
        log.info("deleteReply()");
        
        int deleteResult = replyMapper.delete(replyId);
        log.info(deleteResult + "행 답글 삭제");
        int updateResult = boardMapper.updateReplyCount(boardId, -1);
        log.info(updateResult + "행 게시판 답글 수 수정");
        
        return 1; 
    }
}
