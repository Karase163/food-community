package com.projectcoding.project01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectcoding.project01.domain.ReviewBoardVO;
import com.projectcoding.project01.service.ReviewBoardService;
import com.projectcoding.project01.util.PageMaker;
import com.projectcoding.project01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value="/reviewboard")
@Log4j
public class ReviewBoardController {
    
    @Autowired
    private ReviewBoardService reviewboardService;
    
    // 전체 게시글 데이터를 foodRecipelist.jsp 페이지로 전송
    @GetMapping("/reviewlist")
    public void list(Model model, Pagination pagination) {
        log.info("reviewlist()");
        log.info("pagination = " + pagination);
        
        List<ReviewBoardVO> reviewboardList = reviewboardService.getPagingBoards(pagination);
        
        PageMaker pageMaker = new PageMaker();
        pageMaker.setPagination(pagination);
        pageMaker.setTotalCount(reviewboardService.getTotalCount());
        
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("reviewboardList", reviewboardList);
    }
    
    // register.jsp 페이지 호출
    @GetMapping("/reviewregister")
    public void registerGET() {
        log.info("reviewregisterGET()");
    }
    
    // register.jsp에서 전송받은 게시글 데이터를 저장
    @PostMapping("/reviewregister")
    public String registerPOST(ReviewBoardVO reviewboardVO) {
        log.info("reviewregisterPOST");
        log.info("reviewBoardVO : " + reviewboardVO);
        int result = reviewboardService.createBoard(reviewboardVO);
        log.info(result + "행 등록");
        return "redirect:/reviewboard/reviewlist";
    }
    
    // list.jsp에서 선택된 게시글 번호를 바탕으로 게시글 상세 조회
    @GetMapping("/detail2")
    public void detail(Model model, Integer reviewboardId) {
        log.info("detail2()");
        ReviewBoardVO reviewboardVO = reviewboardService.getBoardById(reviewboardId);
        log.info(reviewboardVO);
        model.addAttribute("reviewBoardVO", reviewboardVO);
    }
    
    // 게시글 번호를 전송받아 상세 게시글 조회
    @GetMapping("/reviewmodify")
    public void reviewmodifyGET(Model model, Integer reviewBoardId) {
        log.info("reviewmodifyGET()");
        ReviewBoardVO reviewboardVO = reviewboardService.getBoardById(reviewBoardId);
        log.info(reviewboardVO);
        model.addAttribute("reviewBoardVO", reviewboardVO);
    }   
    
    // modify.jsp에서 데이터를 전송받아 게시글 수정
    @PostMapping("/reviewmodify")
    public String modifyPOST(ReviewBoardVO reviewboardVO) {
        log.info("reviewmodifyPOST");
        log.info("수정된 게시글 정보 : " + reviewboardVO);
        int result = reviewboardService.updateBoard(reviewboardVO);
        log.info("result : " + result);
        return "redirect:/reviewboard/reviewlist";
    }
    
    // detail.jsp에서 boardId를 전송받아 게시글 데이터 삭제
    @PostMapping("/project1/detial2/reviewboard/delete")
    public String delete(@RequestParam("reviewBoardId") Integer reviewBoardId) {
        try {
            log.info("delete() - boardId = " + reviewBoardId);
            
            // 게시글 삭제
            int result = reviewboardService.deleteBoard(reviewBoardId);
            log.info(result + "행 삭제");

            // 삭제 후 게시글 목록으로 리다이렉트
            return "redirect:/reviewboard/reviewlist";
        } catch (Exception e) {
            log.error("게시글 삭제 중 오류 발생: ", e);
            return "redirect:/errorPage"; // 오류 처리 페이지로 리다이렉트
        }
    }
}