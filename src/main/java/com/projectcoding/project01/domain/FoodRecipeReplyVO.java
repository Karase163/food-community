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
public class FoodRecipeReplyVO {

	private int foodrecipereplyId; 
	private int foodrecipecommentsId; 
	private int foodrecipeboardId; 
	private String foodrecipereplyContent; 
	private String foodrecipememberId;
	private Date foodrecipereplyDateCreated;

}
