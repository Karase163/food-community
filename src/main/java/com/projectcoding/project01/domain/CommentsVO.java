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
public class CommentsVO {
	private int commentsId;
	private int boardId;
	private String memberId;
	private String commentsContent;
	private Date commentsDateCreated;

}
