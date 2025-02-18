package com.projectcoding.project01.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectcoding.project01.domain.FoodRecipeBoardVO;
import com.projectcoding.project01.domain.FoodRecipeAttach;
import com.projectcoding.project01.domain.FoodRecipeAttachDTO;
import com.projectcoding.project01.persistence.FoodRecipeBoardMapper;
import com.projectcoding.project01.persistence.FoodRecipeAttachMapper;
import com.projectcoding.project01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FoodRecipeBoardServiceImple implements FoodRecipeBoardService {
   
   @Autowired
   private FoodRecipeBoardMapper foodRecipeboardMapper;
   
   @Autowired
   private FoodRecipeAttachMapper foodRecipeAttachMapper;

   @Transactional(value = "transactionManager") 
   @Override
   public int createBoard(FoodRecipeBoardVO foodrecipeboardVO) {
      log.info("createBoard()");
      log.info("foodrecipeboardVO = " + foodrecipeboardVO);
      
      // 게시글 등록
      int insertBoardResult = foodRecipeboardMapper.insert(foodrecipeboardVO);
      log.info(insertBoardResult + "행 게시글 등록");
   
      List<FoodRecipeAttachDTO> attachList = foodrecipeboardVO.getFoodRecipeAttachList();

      int insertAttachResult = 0;
      for(FoodRecipeAttachDTO attachDTO : attachList) {
         insertAttachResult += foodRecipeAttachMapper.insert(toEntity(attachDTO));
      }
      log.info(insertAttachResult + "행 파일 정보 등록");
      
      return 1;
   } // end createBoard()

   @Override
   public List<FoodRecipeBoardVO> getAllBoards() {
      log.info("getAllBoards()");
      return foodRecipeboardMapper.selectList();
   }

   @Override
   public FoodRecipeBoardVO getBoardById(int foodrecipeboardId) {
      log.info("getBoardById()");
      log.info("foodrecipeboardId = " + foodrecipeboardId);
      
      // 게시글 조회
      FoodRecipeBoardVO foodrecipeboardVO = foodRecipeboardMapper.selectOne(foodrecipeboardId);
      
      // 첨부파일 조회
      List<FoodRecipeAttach> attachList = foodRecipeAttachMapper.selectByBoardId(foodrecipeboardId);
      List<FoodRecipeAttachDTO> attachDTOList = attachList.stream().map(this::toDTO).collect(Collectors.toList());
      
      foodrecipeboardVO.setFoodRecipeAttachList(attachDTOList); // 첨부파일 리스트 설정
      return foodrecipeboardVO;
   }

   @Transactional(value = "transactionManager") 
   @Override
   public int updateBoard(FoodRecipeBoardVO foodrecipeboardVO) {
      log.info("updateBoard()");
      log.info("foodrecipeboardVO = " + foodrecipeboardVO);
      
      // 게시글 수정
      int updateBoardResult = foodRecipeboardMapper.update(foodrecipeboardVO);
      log.info(updateBoardResult + "행 게시글 정보 수정");
      
      // 기존 첨부파일 삭제
      int deleteAttachResult = foodRecipeAttachMapper.delete(foodrecipeboardVO.getFoodRecipeBoardId());
      log.info(deleteAttachResult + "행 파일 정보 삭제");
      
      // 새로운 첨부파일 등록
      List<FoodRecipeAttachDTO> attachList = foodrecipeboardVO.getFoodRecipeAttachList();
      int insertAttachResult = 0;
      for(FoodRecipeAttachDTO attachDTO : attachList) {
         attachDTO.setFoodRecipeBoardId(foodrecipeboardVO.getFoodRecipeBoardId()); // 게시글 번호 적용
         insertAttachResult += foodRecipeAttachMapper.insertModify(toEntity(attachDTO));
      }
      log.info(insertAttachResult + "행 파일 정보 등록");
      return 1;
   } // end updateBoard()

   @Transactional(value = "transactionManager") 
   @Override
   public int deleteBoard(int foodrecipeboardId) {
      log.info("deleteBoard()");
      log.info("foodrecipeboardId = " + foodrecipeboardId);
      
      // 게시글 삭제
      int deleteBoardResult = foodRecipeboardMapper.delete(foodrecipeboardId);
      log.info(deleteBoardResult + "행 게시글 정보 삭제");
      
      // 첨부파일 삭제
      int deleteAttachResult = foodRecipeAttachMapper.delete(foodrecipeboardId);
      log.info(deleteAttachResult + "행 파일 정보 삭제");
      return 1;
   }

   @Override
   public List<FoodRecipeBoardVO> getPagingBoards(Pagination pagination) {
      log.info("getPagingBoards()");
      return foodRecipeboardMapper.selectListByPagination(pagination);
   }

   @Override
   public int getTotalCount() {
      log.info("getTotalCount()");
      return foodRecipeboardMapper.selectTotalCount();
   }

   // FoodRecipeAttachDTO를 FoodRecipeAttach로 변환하는 메서드
   private FoodRecipeAttach toEntity(FoodRecipeAttachDTO attachDTO) {
      FoodRecipeAttach attach = new FoodRecipeAttach();
      attach.setFoodRecipeAttachId(attachDTO.getFoodRecipeAttachId());
      attach.setFoodRecipeBoardId(attachDTO.getFoodRecipeBoardId());
      attach.setFoodRecipeAttachPath(attachDTO.getFoodRecipeAttachPath());
      attach.setFoodRecipeAttachRealName(attachDTO.getFoodRecipeAttachRealName());
      attach.setFoodRecipeAttachChgName(attachDTO.getFoodRecipeAttachChgName());
      attach.setFoodRecipeAttachExtension(attachDTO.getFoodRecipeAttachExtension());
      attach.setFoodRecipeAttachCreated(attachDTO.getFoodRecipeAttachCreated());
      return attach;
   }
   
   // FoodRecipeAttach를 FoodRecipeAttachDTO로 변환하는 메서드
   private FoodRecipeAttachDTO toDTO(FoodRecipeAttach attach) {
      FoodRecipeAttachDTO attachDTO = new FoodRecipeAttachDTO();
      attachDTO.setFoodRecipeAttachId(attach.getFoodRecipeAttachId());
      attachDTO.setFoodRecipeBoardId(attach.getFoodRecipeBoardId());
      attachDTO.setFoodRecipeAttachPath(attach.getFoodRecipeAttachPath());
      attachDTO.setFoodRecipeAttachRealName(attach.getFoodRecipeAttachRealName());
      attachDTO.setFoodRecipeAttachChgName(attach.getFoodRecipeAttachChgName());
      attachDTO.setFoodRecipeAttachExtension(attach.getFoodRecipeAttachExtension());
      attachDTO.setFoodRecipeAttachCreated(attach.getFoodRecipeAttachCreated());
      return attachDTO;
   }
}
