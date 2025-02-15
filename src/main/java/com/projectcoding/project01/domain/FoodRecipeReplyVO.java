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
public class FoodRecipeReplyVO {

	private int foodRecipeReplyId; 
	private int foodRecipeCommentsId; 
	private String foodRecipeReplyContent; 
	private String memberId;
}
