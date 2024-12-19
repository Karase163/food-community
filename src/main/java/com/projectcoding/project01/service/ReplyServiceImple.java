package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.domain.ReplyVO;
import com.projectcoding.project01.persistence.BoardMapper;
import com.projectcoding.project01.persistence.ReplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImple implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private BoardMapper boardMapper;

    // 답글 추가
    @Transactional(value = "transactionManager")
    @Override
    public int createReply(ReplyVO replyVO) {
        log.info("createReply()");
        
        // 답글 추가 
        int insertResult = replyMapper.insert(replyVO);
        log.info(insertResult + "행 답글 추가");
        
        int updateResult = boardMapper.updateReplyCount(replyVO.getBoardId(), 1);
        log.info(updateResult + "행 게시판 답글 수 수정");
        
        return 1; 
    }

    // 특정 답글에 달린 모든 답글 조회
    @Override
    public List<ReplyVO> getAllReply(int commentsId) {
        log.info("getAllReplies()");
        List<ReplyVO> list = replyMapper.selectListByCommentsId(commentsId);
        return list;
    }
    

    
    
    // 답글 수정
    @Override
    public int updateReply(int replyId, String replyContent) {
        log.info("updateReply()");
        
        ReplyVO replyVO = new ReplyVO();
        replyVO.setReplyId(replyId);
        replyVO.setReplyContent(replyContent);
        
        int updateResult = replyMapper.update(replyVO);
        return updateResult; // 수정된 행 수 반환
    }

    // 답글 삭제
    @Transactional(value = "transactionManager")
    @Override
    public int deleteReply(int replyId, int boardId) {
        log.info("deleteReply()");
        
        // 답글 삭제
        int deleteResult = replyMapper.delete(replyId);
        log.info(deleteResult + "행 답글 삭제");
        
        // 게시글의 답글 수 감소
        int updateResult = boardMapper.updateReplyCount(boardId, -1);
        log.info(updateResult + "행 게시판 답글 수 수정");
        
        return 1; // 1은 성공적으로 삭제된 행의 수
    }
}
