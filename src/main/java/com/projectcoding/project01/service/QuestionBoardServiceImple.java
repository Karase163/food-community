package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectcoding.project01.domain.QuestionBoardVO;
import com.projectcoding.project01.persistence.QuestionBoardMapper;
import com.projectcoding.project01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service // @Component : Spring이 관리하는 객체
@Log4j
public class QuestionBoardServiceImple implements QuestionBoardService{
   
   @Autowired
   private QuestionBoardMapper boardMapper;

   @Override
   public int createBoard(QuestionBoardVO questionboardVO) {
      log.info("createBoard()");
      int result = boardMapper.insert(questionboardVO);
      return result;
   }

   @Override
   public List<QuestionBoardVO> getAllBoards() {
      log.info("getAllBoards()");
      return boardMapper.selectList();
   }

   @Override
   public QuestionBoardVO getBoardById(int questionboardId) {
      log.info("getBoardById()");
      return boardMapper.selectOne(questionboardId);
   }

   @Override
   public int updateBoard(QuestionBoardVO questionboardVO) {
      log.info("updateBoard()");
      return boardMapper.update(questionboardVO);
   }

   @Override
   public int deleteBoard(int questionboardId) {
      log.info("deleteBoard()");
      return boardMapper.delete(questionboardId);
   }

   @Override
   public List<QuestionBoardVO> getPagingBoards(Pagination pagination) {
      log.info("getPagingBoards()");
      return boardMapper.selectListByPagination(pagination);
   }

   @Override
   public int getTotalCount() {
      log.info("getTotalCount()");
      return boardMapper.selectTotalCount();
   }

}
