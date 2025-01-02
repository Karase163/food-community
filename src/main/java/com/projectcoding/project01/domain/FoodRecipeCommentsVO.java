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
    private int foodRecipeCommentsId;
    private int foodRecipeBoardId;
    private String MemberId;
    private String foodRecipeCommentsContent;
    private Date foodRecipeCommentsDateCreated;
}
