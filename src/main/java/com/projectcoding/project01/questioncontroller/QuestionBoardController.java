package com.projectcoding.project01.questioncontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value="/board")
@Log4j
public class QuestionBoardController {
	
	@GetMapping("/detail3")
	public void detail() {
		log.info("detail3()");
	}
}


