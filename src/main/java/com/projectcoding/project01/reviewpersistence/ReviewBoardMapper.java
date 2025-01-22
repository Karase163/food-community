package com.projectcoding.project01.reviewpersistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import com.projectcoding.project01.reviewdomain.ReviewBoardVO;

import java.util.List;

@Mapper
public interface ReviewBoardMapper {
	
	List<ReviewBoardVO> selectList(); // 전체 게시글 조회

	ReviewBoardVO selectOne(int reviewBoardId); // 상세 게시글 조회

	int update(ReviewBoardVO reviewBoardVO); // 상세 게시글 수정

	int delete(int reviewBoardId); // 상세 게시글 삭제

	int updateCommentsCount(@Param("reviewBoardId") int reviewBoardId, @Param("amount") int amount); // 댓글 수 변경
	

}