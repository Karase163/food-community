package com.projectcoding.project01.questiondomain;

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
public class QuestionCommentsVO {
	
    private int questionCommentsId;
    private int questionBoardId;
    private String MemberId;
    private String questionCommentsContent;
    private Date questionCommentsCreated;
    private int questionReplyCount;

}		
