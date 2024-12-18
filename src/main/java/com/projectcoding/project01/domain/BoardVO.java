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
public class BoardVO {
	private int boardId;
	private String boardTitle;
	private String boardContent;
	private String memberId;
	private Date boardDateCreated;
	private int reply_Count;	
	private int tabId;
	private String tabName;

}
