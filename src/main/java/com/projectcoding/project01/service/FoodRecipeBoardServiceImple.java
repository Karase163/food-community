package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectcoding.project01.domain.FoodRecipeBoardVO;
import com.projectcoding.project01.persistence.FoodRecipeBoardMapper;
import com.projectcoding.project01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service // @Component : Spring이 관리하는 객체
@Log4j
public class FoodRecipeBoardServiceImple implements FoodRecipeBoardService{
   
   @Autowired
   private FoodRecipeBoardMapper foodRecipeboardMapper;

   @Override
   public int createBoard(FoodRecipeBoardVO foodrecipeboardVO) {
      log.info("createBoard()");
      int result = foodRecipeboardMapper.insert(foodrecipeboardVO);
      return result;
   }

   @Override
   public List<FoodRecipeBoardVO> getAllBoards() {
      log.info("getAllBoards()");
      return foodRecipeboardMapper.selectList();
   }

   @Override
   public FoodRecipeBoardVO getBoardById(int foodrecipeboardId) {
      log.info("getBoardById()");
      return foodRecipeboardMapper.selectOne(foodrecipeboardId);
   }

   @Override
   public int updateBoard(FoodRecipeBoardVO foodrecipeboardVO) {
      log.info("updateBoard()");
      return foodRecipeboardMapper.update(foodrecipeboardVO);
   }

   @Override
   public int deleteBoard(int foodrecipeboardId) {
      log.info("deleteBoard()");
      return foodRecipeboardMapper.delete(foodrecipeboardId);
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

}
