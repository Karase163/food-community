package com.projectcoding.project01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectcoding.project01.domain.QuestionCommentsVO;
import com.projectcoding.project01.service.QuestionCommentsService;

import lombok.extern.log4j.Log4j;


// 비동기 방식이기 때문에 JSON 사용을 위한 @RestController 사용
@RestController // @Responsebody와 @Requestbody를 사용해서 JSON데이터의 변환을 자동으로 해주는 언노테이션
@RequestMapping(value = "/questionComments")
@Log4j
public class QuestionCommentsRESTController {

	@Autowired
	private QuestionCommentsService questionCommentsService;

	@PostMapping // POST : 음식 레시피 댓글 입력
	public ResponseEntity<Integer> createQuestionComments(@RequestBody QuestionCommentsVO questionCommentsVO) {
		log.info("createQuestionComments()");
		log.info(questionCommentsVO);
		int result = questionCommentsService.createComments(questionCommentsVO);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@GetMapping("/all/{questionCommentsId}") // GET : 음식 레시피 댓글 선택(all)
	public ResponseEntity<List<QuestionCommentsVO>> readyAllQuestionComments(@PathVariable("questionCommentsId") int questionCommentsId) {
		log.info("readyAllQuestionReply()");
		log.info("questionCommentsId = " + questionCommentsId);

		List<QuestionCommentsVO> list = questionCommentsService.getAllComments(questionCommentsId);
		return new ResponseEntity<List<QuestionCommentsVO>>(list, HttpStatus.OK);
	}

	@PutMapping("/{questionCommentsId}") // PUT : 음식 레시피 댓글 수정
	public ResponseEntity<Integer> updateReply(@PathVariable("questionCommentsId") int questionCommentsId, 
		@RequestBody String questionCommentsContent) {
		log.info("updateQuestionReply()");
		log.info("questionCommentsId = " + questionCommentsId);
		int result = questionCommentsService.updateComments(questionCommentsId, questionCommentsContent);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{questionCommentsId}/{questionBoardId}") // DELETE : 음식 레시피 댓글 삭제
	public ResponseEntity<Integer> deleteReply(@PathVariable("questionCommentsId") int questionCommentsId,
			@PathVariable("questionBoardId") int questionBoardId) {
		log.info("deleteQuestionComments()");
		log.info("questionCommentsId = " + questionCommentsId);

		int result = questionCommentsService.deleteComments(questionCommentsId, questionCommentsId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}