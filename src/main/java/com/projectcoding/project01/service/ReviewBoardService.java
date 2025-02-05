package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.ReviewBoardVO;
import com.projectcoding.project01.util.Pagination;

public interface ReviewBoardService {
	int createBoard(ReviewBoardVO reviewboardVO);

	List<ReviewBoardVO> getAllBoards();

	ReviewBoardVO getBoardById(int reviewboardId);

	int updateBoard(ReviewBoardVO reviewboardVO);

	int deleteBoard(int reviewboardId);

	List<ReviewBoardVO> getPagingBoards(Pagination pagination);

	int getTotalCount();
}
