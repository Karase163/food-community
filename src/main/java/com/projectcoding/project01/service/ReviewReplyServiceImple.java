package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.domain.ReviewReplyVO;
import com.projectcoding.project01.persistence.ReviewCommentsMapper;
import com.projectcoding.project01.persistence.ReviewReplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReviewReplyServiceImple implements ReviewReplyService {

    @Autowired
    private ReviewReplyMapper reviewReplyMapper;

    @Autowired
    private ReviewCommentsMapper reviewCommentsMapper;

    @Transactional(value = "transactionManager")

    @Override
    public int createReply(ReviewReplyVO reviewReplyVO) {
        log.info("createReply()");
        
        int insertReplyResult = reviewReplyMapper.insert(reviewReplyVO);
        log.info(insertReplyResult + "행 답글 추가");
        int updateReplyCountResult = reviewCommentsMapper.updateReplyCount(reviewReplyVO.getReviewCommentsId(), 1); // 댓글 ID로 수정
        log.info(updateReplyCountResult + "행 댓글 답글 수 수정");
        return updateReplyCountResult;
    }

    @Override
    public List<ReviewReplyVO> getAllReplies(int reviewCommentsId) {
        log.info("getAllReplies()");
        List<ReviewReplyVO> list = reviewReplyMapper.selectListByCommentsId(reviewCommentsId);
        return list;
    }
    
    @Override
    public int updateReply(int reviewReplyId, String reviewReplyContent) {
        log.info("updateReply()");
        
        ReviewReplyVO replyVO = new ReviewReplyVO();
        replyVO.setReviewReplyId(reviewReplyId);
        replyVO.setReviewReplyContent(reviewReplyContent);
        
        int updateReplyResult = reviewReplyMapper.update(replyVO);
        return updateReplyResult; 
    }

    @Transactional(value = "transactionManager")
    @Override
    public int deleteReply(int reviewReplyId, int reviewCommentsId) {
        log.info("deleteReply()");
        
        int deleteReplyResult = reviewReplyMapper.delete(reviewReplyId);
        log.info(deleteReplyResult + "행 답글 삭제");
        int updateReplyCountResult = reviewCommentsMapper.updateReplyCount(reviewCommentsId, -1);
        log.info(updateReplyCountResult + "행 댓글 답글 수 수정");

        return updateReplyCountResult; 
        
        
 
    }
}
