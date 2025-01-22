package com.projectcoding.project01.reviewpersistence;

import org.apache.ibatis.annotations.Mapper;

import com.projectcoding.project01.reviewdomain.ReviewReplyVO;

import java.util.List;

@Mapper
public interface ReviewReplyMapper {

    int insert(ReviewReplyVO reviewReplyVO); 
    
    List<ReviewReplyVO> selectListByCommentsId(int reviewCommentsId);
    
    int update(ReviewReplyVO reviewReplyVO); 
    
    int delete(int reviewReplyId); 
    
}