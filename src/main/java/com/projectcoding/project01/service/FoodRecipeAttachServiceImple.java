package com.projectcoding.project01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectcoding.project01.domain.FoodRecipeAttach;
import com.projectcoding.project01.domain.FoodRecipeAttachDTO;
import com.projectcoding.project01.persistence.FoodRecipeAttachMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FoodRecipeAttachServiceImple implements FoodRecipeAttachService {

    @Autowired
    private FoodRecipeAttachMapper foodRecipeattachMapper;

    @Override
    public FoodRecipeAttachDTO getAttachById(int foodRecipeAttachId) {
        // 로그에 foodRecipeAttachId 값 기록
        log.info("getAttachById() - foodRecipeAttachId: " + foodRecipeAttachId);
        return toDTO(foodRecipeattachMapper.selectByAttachId(foodRecipeAttachId));
    }

    // Attach를 AttachDTO로 변환하는 메서드
    private FoodRecipeAttachDTO toDTO(FoodRecipeAttach foodRecipeAttach) {
        FoodRecipeAttachDTO attachDTO = new FoodRecipeAttachDTO();
        attachDTO.setFoodRecipeAttachId(foodRecipeAttach.getFoodRecipeAttachId());
        attachDTO.setFoodRecipeBoardId(foodRecipeAttach.getFoodRecipeBoardId());
        attachDTO.setFoodRecipeAttachPath(foodRecipeAttach.getFoodRecipeAttachPath());
        attachDTO.setFoodRecipeAttachRealName(foodRecipeAttach.getFoodRecipeAttachRealName());
        attachDTO.setFoodRecipeAttachChgName(foodRecipeAttach.getFoodRecipeAttachChgName());
        attachDTO.setFoodRecipeAttachExtension(foodRecipeAttach.getFoodRecipeAttachExtension());
        attachDTO.setFoodRecipeAttachCreated(foodRecipeAttach.getFoodRecipeAttachCreated());

        return attachDTO;
    }
}
