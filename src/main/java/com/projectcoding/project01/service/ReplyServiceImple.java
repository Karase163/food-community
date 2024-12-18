package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.domain.ReplyVO;
import com.projectcoding.project01.persistence.BoardMapper;
import com.projectcoding.project01.persistence.ReplyMapper;
import com.projectcoding.project01.persistence.CommentsMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImple implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private CommentsMapper commentMapper;

    @Transactional(value = "transactionManager")
    @Override
    public int createReply(ReplyVO replyVO) {
        log.info("createReply()");

        // 답글 추가
        int insertResult = replyMapper.insert(replyVO);
        log.info(insertResult + " 행 답글 추가");

        // 해당 댓글의 BOARD_ID를 찾기 위해 COMMENT_ID를 사용하여 Comment 테이블에서 BOARD_ID를 조회
        int boardId = commentMapper.selectBoardIdByCommentId(replyVO.getCommentsId());
        log.info("게시판 ID: " + boardId);

        // 게시판의 댓글 수 업데이트
        int updateResult = boardMapper.updateCommentsCount(boardId, 1);  // 댓글 수 증가
        log.info(updateResult + " 행 게시판 댓글 수 수정");

        return 1;  // 성공적으로 수행된 경우 1 반환
    }

    @Override
    public List<ReplyVO> getAllReply(int commentsId) {
        log.info("getAllReply()");

        // 댓글에 달린 모든 답글 조회
        List<ReplyVO> list = replyMapper.selectListByCommentsId(commentsId);
        return list;
    }

    @Transactional(value = "transactionManager")
    @Override
    public int updateReply(int replyId, String replyContent) {
        log.info("updateReply()");

        // 답글 수정
        ReplyVO replyVO = new ReplyVO();
        replyVO.setReplyId(replyId);
        replyVO.setReplyContent(replyContent);
        int updateResult = replyMapper.update(replyVO);

        log.info(updateResult + " 행 답글 수정");
        return updateResult;  // 수정된 행 수 반환
    }

    @Transactional(value = "transactionManager")
    @Override
    public int deleteReply(int replyId, int commentsId) {
        log.info("deleteReply()");

        // 답글 삭제
        int deleteResult = replyMapper.delete(replyId);
        log.info(deleteResult + " 행 답글 삭제");

        // 댓글 삭제 후 게시판의 댓글 수 수정 (1 감소)
        int updateResult = boardMapper.updateCommentsCount(commentsId, -1);  // 댓글 수 감소
        log.info(updateResult + " 행 게시판 댓글 수 수정");

        return 1;  // 성공적으로 수행된 경우 1 반환
    }
}
