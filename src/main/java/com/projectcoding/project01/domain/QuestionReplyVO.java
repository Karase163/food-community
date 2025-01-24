package com.projectcoding.project01.domain;

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
public class QuestionReplyVO {
	
	private int questionReplyId; 
	private int questionCommentsId; 
	private String questionReplyContent; 
	private String memberId;
}
