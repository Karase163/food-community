package com.projectcoding.project01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.projectcoding.project01.domain.FoodRecipeBoardVO;

@Mapper
public interface FoodRecipeBoardMapper {
	
	List<FoodRecipeBoardVO> selectList(); // 전체 게시글 조회
	
	FoodRecipeBoardVO selectOne(int foodrecipeboardId); // 상세 게시글 조회
	
	int update(FoodRecipeBoardVO foodrecipeboardVO); // 상세 게시글 수정
	
	int delete(int foodrecipeboardId); // 상세 게시글 삭제
	
	int updateCommentsCount(@Param("foodrecipeboardId") int boardId,@Param("amount") int amount); // 댓글 수 변경
	
	int updateReplyCount(@Param("foodrecipeboardId") int boardId, @Param("amount") int amount); // 답글 수 변경
	
	
}
