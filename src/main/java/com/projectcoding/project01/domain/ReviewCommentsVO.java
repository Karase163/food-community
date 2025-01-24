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

public class ReviewCommentsVO {
	private int reviewCommentsId;
    private int reviewBoardId;
    private String MemberId;
    private String reviewCommentsContent;
    private Date reviewCommentsCreated;
    private int reviewReplyCount;
}