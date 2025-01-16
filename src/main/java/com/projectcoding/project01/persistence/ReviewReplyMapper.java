package com.projectcoding.project01.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.projectcoding.project01.domain.ReviewReplyVO;

import java.util.List;

@Mapper
public interface ReviewReplyMapper {

    int insert(ReviewReplyVO reviewReplyVO); 
    
    List<ReviewReplyVO> selectListByCommentsId(int reviewCommentsId);
    
    int update(ReviewReplyVO reviewReplyVO); 
    
    int delete(int reviewReplyId); 
    
}