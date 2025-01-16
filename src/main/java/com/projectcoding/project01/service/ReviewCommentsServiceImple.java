package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.domain.ReviewCommentsVO;
import com.projectcoding.project01.persistence.ReviewBoardMapper;
import com.projectcoding.project01.persistence.ReviewCommentsMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReviewCommentsServiceImple implements ReviewCommentsService {
	
	@Autowired
	private ReviewCommentsMapper reviewCommentsMapper;
	
	@Autowired
	private ReviewBoardMapper reviewBoardMapper;
	
	@Transactional(value = "transactionManager")
	
	@Override
	public int createComments(ReviewCommentsVO reviewCommentsVO) {
		log.info("createComments()");
					
		int insertResult = reviewCommentsMapper.insert(reviewCommentsVO);
		log.info(insertResult + "행 댓글 추가");
		
		int updateResult = reviewBoardMapper.updateCommentsCount(reviewCommentsVO.getReviewBoardId(), 1);
		log.info(updateResult + "행 게시판 댓글 수 수정");
		return 1;
	}

	@Override
	public List<ReviewCommentsVO> getAllComments(int reviewBoardId) {
		log.info("getAllComments()");
		List<ReviewCommentsVO> list = reviewCommentsMapper.selectListByBoardId(reviewBoardId);
		return list;
	}

	@Override
	public int updateComments(int reviewCommentsId, String reviewCommentsContent) {
		log.info("updateComments()");
		ReviewCommentsVO commentsVO = new ReviewCommentsVO();
		commentsVO.setReviewCommentsId(reviewCommentsId);
		commentsVO.setReviewCommentsContent(reviewCommentsContent);
		int updateResult = reviewCommentsMapper.update(commentsVO);
		return updateResult;
	}
	
	@Transactional(value = "transactionManager")
	@Override
	public int deleteComments(int reviewCommentsId, int reviewBoardId) {
		log.info("deleteComments()");
		int deleteResult = reviewCommentsMapper.delete(reviewCommentsId);
		
		log.info(deleteResult + " 행 댓글 삭제");
		int updateResult = reviewBoardMapper.updateCommentsCount(reviewBoardId, -1);
		log.info(updateResult + "행 게시판 댓글 수 수정");
		return 1;
	}
}
