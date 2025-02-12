package com.projectcoding.project01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectcoding.project01.domain.QuestionBoardVO;
import com.projectcoding.project01.service.QuestionBoardService;
import com.projectcoding.project01.util.PageMaker;
import com.projectcoding.project01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value="/questionboard")
@Log4j
public class QuestionBoardController {
    
    @Autowired
    private QuestionBoardService questionboardService;
    
    // 전체 게시글 데이터를 foodRecipelist.jsp 페이지로 전송
    @GetMapping("/questionlist")
    public void list(Model model, Pagination pagination) {
        log.info("questionlist()");
        log.info("pagination = " + pagination);
        
        List<QuestionBoardVO> questionboardList = questionboardService.getPagingBoards(pagination);
        
        PageMaker pageMaker = new PageMaker();
        pageMaker.setPagination(pagination);
        pageMaker.setTotalCount(questionboardService.getTotalCount());
        
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("questionboardList", questionboardList);
    }
    
    // register.jsp 페이지 호출
    @GetMapping("/questionregister")
    public void registerGET() {
        log.info("questionregisterGET()");
    }
    
    // register.jsp에서 전송받은 게시글 데이터를 저장
    @PostMapping("/questionregister")
    public String registerPOST(QuestionBoardVO questionboardVO) {
        log.info("questionregisterPOST");
        log.info("questionBoardVO : " + questionboardVO);
        int result = questionboardService.createBoard(questionboardVO);
        log.info(result + "행 등록");
        return "redirect:/questionboard/questionlist";
    }
    
    // list.jsp에서 선택된 게시글 번호를 바탕으로 게시글 상세 조회
    @GetMapping("/detail3")
    public void detail(Model model, Integer questionboardId) {
        log.info("detail3()");
        QuestionBoardVO questionboardVO = questionboardService.getBoardById(questionboardId);
        log.info(questionboardVO);
        model.addAttribute("questionBoardVO", questionboardVO);
    }
    
    // 게시글 번호를 전송받아 상세 게시글 조회
    @GetMapping("/questionmodify")
    public void foodRecipemodifyGET(Model model, Integer questionBoardId) {
        log.info("questionmodifyGET()");
        QuestionBoardVO questionboardVO = questionboardService.getBoardById(questionBoardId);
        log.info(questionboardVO);
        model.addAttribute("questionBoardVO", questionboardVO);
    }   
    
    // modify.jsp에서 데이터를 전송받아 게시글 수정
    @PostMapping("/questionmodify")
    public String modifyPOST(QuestionBoardVO questionboardVO) {
        log.info("questionmodifyPOST");
        log.info("수정된 게시글 정보 : " + questionboardVO);
        int result = questionboardService.updateBoard(questionboardVO);
        log.info("result : " + result);
        return "redirect:/questionboard/questionlist";
    }
    
    // detail.jsp에서 boardId를 전송받아 게시글 데이터 삭제
    @PostMapping("/project1/detial3/questionboard/delete")
    public String delete(@RequestParam("questionBoardId") Integer questionBoardId) {
        try {
            log.info("delete() - boardId = " + questionBoardId);
            
            // 게시글 삭제
            int result = questionboardService.deleteBoard(questionBoardId);
            log.info(result + "행 삭제");

            // 삭제 후 게시글 목록으로 리다이렉트
            return "redirect:/questionboard/questionlist";
        } catch (Exception e) {
            log.error("게시글 삭제 중 오류 발생: ", e);
            return "redirect:/errorPage"; // 오류 처리 페이지로 리다이렉트
        }
    }
}