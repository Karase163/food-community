package com.projectcoding.project01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectcoding.project01.domain.ReviewBoardVO;
import com.projectcoding.project01.persistence.ReviewBoardMapper;
import com.projectcoding.project01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service // @Component : Spring이 관리하는 객체
@Log4j
public class ReviewBoardServiceImple implements ReviewBoardService{
   
   @Autowired
   private ReviewBoardMapper reviewboardMapper;

   @Override
   public int createBoard(ReviewBoardVO reviewboardVO) {
      log.info("createBoard()");
      int result = reviewboardMapper.insert(reviewboardVO);
      return result;
   }

   @Override
   public List<ReviewBoardVO> getAllBoards() {
      log.info("getAllBoards()");
      return reviewboardMapper.selectList();
   }

   @Override
   public ReviewBoardVO getBoardById(int reviewboardId) {
      log.info("getBoardById()");
      return reviewboardMapper.selectOne(reviewboardId);
   }

   @Override
   public int updateBoard(ReviewBoardVO reviewboardVO) {
      log.info("updateBoard()");
      return reviewboardMapper.update(reviewboardVO);
   }

   @Override
   public int deleteBoard(int reviewboardId) {
      log.info("deleteBoard()");
      return reviewboardMapper.delete(reviewboardId);
   }

   @Override
   public List<ReviewBoardVO> getPagingBoards(Pagination pagination) {
      log.info("getPagingBoards()");
      return reviewboardMapper.selectListByPagination(pagination);
   }

   @Override
   public int getTotalCount() {
      log.info("getTotalCount()");
      return reviewboardMapper.selectTotalCount();
   }

}
