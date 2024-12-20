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
public class FoodRecipeBoardVO {
	private int foodrecipeboardId;
	private String foodrecipeboardTitle;
	private String foodrecipeboardContent;
	private String memberId;
	private Date foodrecipeboardCreated;
}
