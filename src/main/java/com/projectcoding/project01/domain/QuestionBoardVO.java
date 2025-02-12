package com.projectcoding.project01.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuestionBoardVO {
    private int questionBoardId;
    private String questionBoardTitle;
    private String questionBoardContent;
    private String memberId;
    private Date questionBoardCreated;
    private int questionCommentsCount;
}


