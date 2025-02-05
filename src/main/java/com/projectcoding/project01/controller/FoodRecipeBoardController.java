package com.projectcoding.project01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projectcoding.project01.domain.FoodRecipeBoardVO;
import com.projectcoding.project01.service.FoodRecipeBoardService;
import com.projectcoding.project01.util.PageMaker;
import com.projectcoding.project01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value="/board")
@Log4j
public class FoodRecipeBoardController {
    
    @Autowired
    private FoodRecipeBoardService foodRecipeboardService;
    
    // 전체 게시글 데이터를 foodRecipelist.jsp 페이지로 전송
    @GetMapping("/foodRecipelist")
    public void list(Model model, Pagination pagination) {
        log.info("foodRecipelist()");
        log.info("pagination = " + pagination);
        
        List<FoodRecipeBoardVO> foodRecipeboardList = foodRecipeboardService.getPagingBoards(pagination);
        
        PageMaker pageMaker = new PageMaker();
        pageMaker.setPagination(pagination);
        pageMaker.setTotalCount(foodRecipeboardService.getTotalCount());
        
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("foodRecipeboardList", foodRecipeboardList);
    }
    
    // register.jsp 페이지 호출
    @GetMapping("/foodReciperegister")
    public void registerGET() {
        log.info("foodReciperegisterGET()");
    }
    
    // register.jsp에서 전송받은 게시글 데이터를 저장
    @PostMapping("/foodReciperegister")
    public String registerPOST(FoodRecipeBoardVO foodRecipeboardVO) {
        log.info("foodReciperegisterPOST");
        log.info("foodRecipeBoardVO : " + foodRecipeboardVO);
        int result = foodRecipeboardService.createBoard(foodRecipeboardVO);
        log.info(result + "행 등록");
        return "redirect:/board/foodRecipelist";
    }
    
    // list.jsp에서 선택된 게시글 번호를 바탕으로 게시글 상세 조회
    @GetMapping("/detail")
    public void detail(Model model, Integer foodRecipeboardId) {
        log.info("detail()");
        FoodRecipeBoardVO foodRecipeboardVO = foodRecipeboardService.getBoardById(foodRecipeboardId);
        model.addAttribute("foodRecipeBoardVO", foodRecipeboardVO);
    }
    
    // 게시글 번호를 전송받아 상세 게시글 조회
    @GetMapping("/foodRecipemodify")
    public void foodRecipemodifyGET(Model model, Integer boardId) {
        log.info("foodRecipemodifyGET()");
        FoodRecipeBoardVO foodRecipeboardVO = foodRecipeboardService.getBoardById(boardId);
        model.addAttribute("foodRecipeBoardVO", foodRecipeboardVO);
    }   
    
    // modify.jsp에서 데이터를 전송받아 게시글 수정
    @PostMapping("/foodRecipemodify")
    public String modifyPOST(FoodRecipeBoardVO foodRecipeboardVO) {
        log.info("foodRecipemodifyPOST");
        int result = foodRecipeboardService.updateBoard(foodRecipeboardVO);
        log.info("result : " + result);
        return "redirect:/board/foodRecipelist";
    }
    
    // detail.jsp에서 boardId를 전송받아 게시글 데이터 삭제
    @PostMapping("/delete")
    public String delete(Integer boardId) {
        log.info("delete()");
        int result = foodRecipeboardService.deleteBoard(boardId);
        log.info(result + "행 삭제");
        return "redirect:/board/foodRecipelist";
    }	
    
    // 상세 게시글 페이지 호출
    @GetMapping("/detail1")
    public void detail1() {
        log.info("detail1()");
    }
}
