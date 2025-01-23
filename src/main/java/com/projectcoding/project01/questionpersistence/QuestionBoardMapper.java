package com.projectcoding.project01.questionpersistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.projectcoding.project01.questiondomain.QuestionBoardVO;

import java.util.List;

@Mapper
public interface QuestionBoardMapper {
	
	List<QuestionBoardVO> selectList(); // 전체 게시글 조회

	QuestionBoardVO selectOne(int questionBoardId); // 상세 게시글 조회

	int update(QuestionBoardVO questionBoardVO); // 상세 게시글 수정

	int delete(int questionBoardId); // 상세 게시글 삭제

	int updateCommentsCount(@Param("questionBoardId") int questionBoardId, @Param("amount") int amount); // 댓글 수 변경

}