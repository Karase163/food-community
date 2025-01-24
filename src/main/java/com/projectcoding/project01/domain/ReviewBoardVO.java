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

public class ReviewBoardVO {
	private int reviewBoardId;
	private String reviewBoardTitle;
	private String reviewBoardContent;
	private String memberId;
	private Date reviewBoardCreated;
	private int reviewCommentsCount;
}