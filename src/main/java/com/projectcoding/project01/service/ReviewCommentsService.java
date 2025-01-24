package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.ReviewCommentsVO;


public interface ReviewCommentsService {

    int createComments(ReviewCommentsVO reviewCommentsVO);

    List<ReviewCommentsVO> getAllComments(int reviewBoardId); 

    int updateComments(int reviewCommentsId, String reviewCommentsContent); 

    int deleteComments(int reviewCommentsId, int reviewBoardId);

}