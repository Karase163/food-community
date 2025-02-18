package com.projectcoding.project01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.projectcoding.project01.domain.FoodRecipeAttach;

@Mapper
public interface FoodRecipeAttachMapper {
    int insert(FoodRecipeAttach foodRecipeattach);
    List<FoodRecipeAttach> selectByBoardId(int foodRecipeBoardId);
    FoodRecipeAttach selectByAttachId(int foodRecipeAttachId);
    int insertModify(FoodRecipeAttach foodrecipeattach);
    int delete(int foodRecipeBoardId);
    List<FoodRecipeAttach> selectOldList();
}