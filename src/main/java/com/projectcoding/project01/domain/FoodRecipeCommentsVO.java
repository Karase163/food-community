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
public class FoodRecipeCommentsVO {
	private int foodrecipecommentsId;
	private int foodrecipeboardId;
	private String foodrecipememberId;
	private String foodrecipecommentsContent;
	private Date foodrecipecommentsCreated;

}
