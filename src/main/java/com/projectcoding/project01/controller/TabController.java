package com.projectcoding.project01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TabController {

	// 기본 페이지 URL - 모든 탭이 포함된 페이지
	@RequestMapping("/tabs")
	public String showTabs() {
		return "tabs"; // tabs.jsp 페이지를 반환
	}

	// FoodRecipe 게시판 탭 클릭 시 리다이렉트
	@RequestMapping("/foodRecipe")
	public String foodRecipe() {
		return "redirect:/project01/foodRecipeboard/foodRecipelist"; // 절대 경로로 리다이렉트
	}

	// Review 게시판 탭 클릭 시 리다이렉트
	@RequestMapping("/review")
	public String review() {
		return "redirect:/project01/reviewboard/reviewlist"; // 절대 경로로 리다이렉트
	}

	// Question 게시판 탭 클릭 시 리다이렉트
	@RequestMapping("/question")
	public String question() {
		return "redirect:/project01/questionboard/questionlist"; // 절대 경로로 리다이렉트
	}
}