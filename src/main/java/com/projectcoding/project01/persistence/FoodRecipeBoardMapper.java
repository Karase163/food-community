package com.projectcoding.project01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.projectcoding.project01.util.Pagination;
import com.projectcoding.project01.domain.FoodRecipeBoardVO;

@Mapper
public interface FoodRecipeBoardMapper {
	
	int insert(FoodRecipeBoardVO foodrecipeboardVO); // 게시글 등록
	
	List<FoodRecipeBoardVO> selectList(); // 전체 게시글 조회

	FoodRecipeBoardVO selectOne(int foodRecipeBoardId); // 상세 게시글 조회

	int update(FoodRecipeBoardVO foodRecipeBoardVO); // 상세 게시글 수정

	int delete(int foodRecipeBoardId); // 상세 게시글 삭제

	int updateCommentsCount(@Param("foodRecipeBoardId") int foodRecipeBoardId, @Param("amount") int amount); // 댓글 수 변경
	
	List<FoodRecipeBoardVO> selectListByPagination(Pagination pagination);
	
	int selectTotalCount();
	
}


