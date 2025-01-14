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
    private int foodRecipeBoardId;
    private String foodRecipeBoardTitle;
    private String foodRecipeBoardContent;
    private String memberId;
    private Date foodRecipeBoardCreated;
    private int foodRecipeCommentsCount;
}
