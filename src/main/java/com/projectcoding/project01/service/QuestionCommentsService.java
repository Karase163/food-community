package com.projectcoding.project01.service;

import java.util.List;

import com.projectcoding.project01.domain.QuestionCommentsVO;


public interface QuestionCommentsService {

    int createComments(QuestionCommentsVO questionCommentsVO);

    List<QuestionCommentsVO> getAllComments(int questionBoardId); 

    int updateComments(int questionCommentsId, String questionCommentsContent); 

    int deleteComments(int questionCommentsId, int questionBoardId);

}



