package com.projectcoding.project01.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    // 필드 이름 수정
    private List<FoodRecipeAttachDTO> foodRecipeAttachList;  // 필드 이름 수정

    // getter 메소드 수정
    public List<FoodRecipeAttachDTO> getFoodRecipeAttachList() {
        if (foodRecipeAttachList == null) {
            foodRecipeAttachList = new ArrayList<FoodRecipeAttachDTO>();
        }
        return foodRecipeAttachList;
    }
}
