package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.domain.QuestionReplyVO;
import com.projectcoding.project01.persistence.QuestionCommentsMapper;
import com.projectcoding.project01.persistence.QuestionReplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class QuestionReplyServiceImple implements QuestionReplyService {

    @Autowired
    private QuestionReplyMapper questionReplyMapper;

    @Autowired
    private QuestionCommentsMapper questionCommentsMapper;

    @Transactional(value = "transactionManager")

    @Override
    public int createReply(QuestionReplyVO questionReplyVO) {
        log.info("createReply()");
        
        int insertReplyResult = questionReplyMapper.insert(questionReplyVO);
        log.info(insertReplyResult + "행 답글 추가");
        int updateReplyCountResult = questionCommentsMapper.updateReplyCount(questionReplyVO.getQuestionCommentsId(), 1); // 댓글 ID로 수정
        log.info(updateReplyCountResult + "행 댓글 답글 수 수정");
        return updateReplyCountResult;
    }
    
    @Override
    public List<QuestionReplyVO> getAllReplies(int questionCommentsId) {
        log.info("getAllReplies()");
        List<QuestionReplyVO> list = questionReplyMapper.selectListByCommentsId(questionCommentsId);
        return list;
    }
    
    @Override
    public int updateReply(int questionReplyId, String questionReplyContent) {
        log.info("updateReply()");
        
        QuestionReplyVO replyVO = new QuestionReplyVO();
        replyVO.setQuestionReplyId(questionReplyId);
        replyVO.setQuestionReplyContent(questionReplyContent);
        
        int updateReplyResult = questionReplyMapper.update(replyVO);
        return updateReplyResult; 
    }

    @Transactional(value = "transactionManager")
    @Override
    public int deleteReply(int questionReplyId, int questionCommentsId) {
        log.info("deleteReply()");
        
        int deleteReplyResult = questionReplyMapper.delete(questionReplyId);
        log.info(deleteReplyResult + "행 답글 삭제");
        int updateReplyCountResult = questionCommentsMapper.updateReplyCount(questionCommentsId, -1);
        log.info(updateReplyCountResult + "행 댓글 답글 수 수정");

        return updateReplyCountResult; 
        
        
 
    }
}
