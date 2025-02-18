package com.projectcoding.project01.domain;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter 
@Setter
@ToString 
public class FoodRecipeAttach {
	private int foodRecipeAttachId;
	private int foodRecipeBoardId;
	private String foodRecipeAttachPath;
	private String foodRecipeAttachRealName;
	private String foodRecipeAttachChgName;
	private String foodRecipeAttachExtension;
	private Date foodRecipeAttachCreated;
}