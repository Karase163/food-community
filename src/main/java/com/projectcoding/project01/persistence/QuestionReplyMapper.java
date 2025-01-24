package com.projectcoding.project01.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.projectcoding.project01.domain.QuestionReplyVO;

import java.util.List;

@Mapper
public interface QuestionReplyMapper {

    int insert(QuestionReplyVO questionReplyVO); 
    
    List<QuestionReplyVO> selectListByCommentsId(int questionCommentsId);
    
    int update(QuestionReplyVO questionReplyVO); 
    
    int delete(int questionReplyId); 
    
}