package com.projectcoding.project01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectcoding.project01.domain.FoodRecipeBoardVO;
import com.projectcoding.project01.service.FoodRecipeBoardService;
import com.projectcoding.project01.util.PageMaker;
import com.projectcoding.project01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value="/foodRecipeboard")
@Log4j
public class FoodRecipeBoardController {
    
    @Autowired
    private FoodRecipeBoardService foodRecipeboardService;

    // FoodRecipe 게시판 페이지 처리
    @RequestMapping("/foodRecipelist")
    public String foodRecipelist(Model model) {
        // 게시판 관련 데이터를 model에 추가하거나
        // 뷰를 반환하는 로직
        return "foodRecipelist";  // foodRecipelist.jsp 페이지 반환
    }
    
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
        return "redirect:/foodRecipeboard/foodRecipelist";
    }
    
    // list.jsp에서 선택된 게시글 번호를 바탕으로 게시글 상세 조회
    @GetMapping("/detail1")
    public void detail(Model model, Integer foodRecipeboardId) {
        log.info("detail1()");
        FoodRecipeBoardVO foodRecipeboardVO = foodRecipeboardService.getBoardById(foodRecipeboardId);
        log.info(foodRecipeboardVO);
        model.addAttribute("foodRecipeBoardVO", foodRecipeboardVO);
    }
    
    // 게시글 번호를 전송받아 상세 게시글 조회
    @GetMapping("/foodRecipemodify")
    public void foodRecipemodifyGET(Model model, Integer foodRecipeBoardId) {
        log.info("foodRecipemodifyGET()");
        FoodRecipeBoardVO foodRecipeboardVO = foodRecipeboardService.getBoardById(foodRecipeBoardId);
        log.info(foodRecipeboardVO);
        model.addAttribute("foodRecipeBoardVO", foodRecipeboardVO);
    }   
    
    // modify.jsp에서 데이터를 전송받아 게시글 수정
    @PostMapping("/foodRecipemodify")
    public String modifyPOST(FoodRecipeBoardVO foodRecipeboardVO) {
        log.info("foodRecipemodifyPOST");
        log.info("수정된 게시글 정보 : " + foodRecipeboardVO);
        int result = foodRecipeboardService.updateBoard(foodRecipeboardVO);
        log.info("result : " + result);
        return "redirect:/foodRecipeboard/foodRecipelist";
    }
    
    // detail.jsp에서 boardId를 전송받아 게시글 데이터 삭제
    @PostMapping("/project1/detial1/foodRecipeboard/delete")
    public String delete(@RequestParam("foodRecipeBoardId") Integer foodRecipeBoardId) {
        try {
            log.info("delete() - boardId = " + foodRecipeBoardId);
            
            // 게시글 삭제
            int result = foodRecipeboardService.deleteBoard(foodRecipeBoardId);
            log.info(result + "행 삭제");

            // 삭제 후 게시글 목록으로 리다이렉트
            return "redirect:/foodRecipeboard/foodRecipelist";
        } catch (Exception e) {
            log.error("게시글 삭제 중 오류 발생: ", e);
            return "redirect:/errorPage"; // 오류 처리 페이지로 리다이렉트
        }
    }
}