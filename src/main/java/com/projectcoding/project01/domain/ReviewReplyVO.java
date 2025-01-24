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
public class ReviewReplyVO {
	private int reviewReplyId;
    private int reviewCommentsId;
    private String reviewReplyContent;
    private String memberId;
}