package com.projectcoding.project01.reviewdomain;
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
public class ReviewReplyVO {
	private int reviewReplyId;
    private int reviewCommentsId;
    private String reviewReplyContent;
    private String memberId;
    private Date reviewReplyCreated;
}