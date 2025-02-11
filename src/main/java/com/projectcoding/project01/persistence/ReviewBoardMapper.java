package com.projectcoding.project01.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.projectcoding.project01.domain.ReviewBoardVO;
import com.projectcoding.project01.util.Pagination;

import java.util.List;

@Mapper
public interface ReviewBoardMapper {
	
	int insert(ReviewBoardVO reviewboardVO); // 게시글 등록
	
	List<ReviewBoardVO> selectList(); // 전체 게시글 조회

	ReviewBoardVO selectOne(int reviewBoardId); // 상세 게시글 조회

	int update(ReviewBoardVO reviewBoardVO); // 상세 게시글 수정

	int delete(int reviewBoardId); // 상세 게시글 삭제

	int updateCommentsCount(@Param("reviewBoardId") int reviewBoardId, @Param("amount") int amount); // 댓글 수 변경
	
	List<ReviewBoardVO> selectListByPagination(Pagination pagination);
	
	int selectTotalCount();

}

