package com.projectcoding.project01.questionservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.questiondomain.QuestionCommentsVO;
import com.projectcoding.project01.questionpersistence.QuestionBoardMapper;
import com.projectcoding.project01.questionpersistence.QuestionCommentsMapper;

import lombok.extern.log4j.Log4j;
@Service
@Log4j
public class QuestionCommentsSeviceImple implements QuestionCommentsService {
	
	@Autowired
	private QuestionCommentsMapper questionCommentsMapper;
	
	@Autowired
	private QuestionBoardMapper questionBoardMapper;
	
	@Transactional(value = "transactionManager")
	
	@Override
	public int createComments(QuestionCommentsVO questionCommentsVO) {
		log.info("createComments()");
					
		int insertResult = questionCommentsMapper.insert(questionCommentsVO);
		log.info(insertResult + "행 댓글 추가");
		
		int updateResult = questionBoardMapper.updateCommentsCount(questionCommentsVO.getQuestionBoardId(), 1);
		log.info(updateResult + "행 게시판 댓글 수 수정");
		return 1;
	}

	@Override
	public List<QuestionCommentsVO> getAllComments(int questionBoardId) {
		log.info("getAllComments()");
		List<QuestionCommentsVO> list = questionCommentsMapper.selectListByBoardId(questionBoardId);
		return list;
	}

	@Override
	public int updateComments(int questionCommentsId, String questionCommentsContent) {
		log.info("updateComments()");
		QuestionCommentsVO commentsVO = new QuestionCommentsVO();
		commentsVO.setQuestionCommentsId(questionCommentsId);
		commentsVO.setQuestionCommentsContent(questionCommentsContent);
		int updateResult = questionCommentsMapper.update(commentsVO);
		return updateResult;
	}
	
	@Transactional(value = "transactionManager")
	@Override
	public int deleteComments(int questionCommentsId, int questionBoardId) {
		log.info("deleteComments()");
		int deleteResult = questionCommentsMapper.delete(questionCommentsId);
		
		log.info(deleteResult + " 행 댓글 삭제");
		int updateResult = questionBoardMapper.updateCommentsCount(questionBoardId, -1);
		log.info(updateResult + "행 게시판 댓글 수 수정");
		return 1;
	}
}