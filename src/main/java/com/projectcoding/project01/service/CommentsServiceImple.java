package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.domain.CommentsVO;
import com.projectcoding.project01.persistence.BoardMapper;
import com.projectcoding.project01.persistence.CommentsMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CommentsServiceImple implements CommentsService {
	
	@Autowired
	private CommentsMapper commentsMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Transactional(value = "transactionaManager")
	
	@Override
	public int createComments(CommentsVO commentsVO) {
		log.info("createComments()");
		int insertResult = commentsMapper.insert(commentsVO);
		log.info(insertResult + "행 댓글 추가");
		
		int updateResult = boardMapper.updateCommentsCount(commentsVO.getBoardId(),1);
		log.info(updateResult + "행 게시판 수정");
		return 1;
	}

	@Override
	public List<CommentsVO> getAllComments(int boardId) {
		log.info("getAllComments()");
		List<CommentsVO> list = commentsMapper.selectListByBoardId(boardId);
		return list;
	}

	@Override
	public int updateComments(int commentsId, String commentsContent) {
		log.info("updateComments()");
		CommentsVO commentsVO = new CommentsVO();
		commentsVO.setCommentsId(commentsId);
		commentsVO.setCommentsContent(commentsContent);
		int updateResult = commentsMapper.update(commentsVO);
		return updateResult;
	}
	
	@Transactional(value = "transactionManager")
	@Override
	public int deleteComments(int commentsId, int boardId) {
		log.info("deleteComments()");
		int deleteResult = commentsMapper.delete(commentsId);
		
		log.info(deleteResult + " 행 댓글 삭제");
		int updateResult = boardMapper.updateCommentsCount(boardId, -1);
		log.info(updateResult + "행 게시판 수정");
		return 1;
	}

}
