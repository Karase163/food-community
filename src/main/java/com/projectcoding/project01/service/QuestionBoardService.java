package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.QuestionBoardVO;
import com.projectcoding.project01.util.Pagination;

public interface QuestionBoardService {
	int createBoard(QuestionBoardVO questionboardVO);

	List<QuestionBoardVO> getAllBoards();

	QuestionBoardVO getBoardById(int questionboardId);

	int updateBoard(QuestionBoardVO questionboardVO);

	int deleteBoard(int QuestionboardId);

	List<QuestionBoardVO> getPagingBoards(Pagination pagination);

	int getTotalCount();
}
