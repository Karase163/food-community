package com.projectcoding.project01.questioncontroller;

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

import com.projectcoding.project01.questiondomain.QuestionReplyVO;
import com.projectcoding.project01.questionservice.QuestionReplyService;

import lombok.extern.log4j.Log4j;


// 비동기 방식이기 때문에 JSON 사용을 위한 @RestController 사용
@RestController // @Responsebody와 @Requestbody를 사용해서 JSON데이터의 변환을 자동으로 해주는 언노테이션
@RequestMapping(value = "/questionReply")
@Log4j
public class QuestionReplyRESTController {

	@Autowired
	private QuestionReplyService questionReplyService;

	@PostMapping // POST : 음식 레시피 답글 입력
	public ResponseEntity<Integer> createQuestionReply(@RequestBody QuestionReplyVO questionReplyVO) {
		log.info("createQuestionReply()");
		log.info(questionReplyVO);
		int result = questionReplyService.createReply(questionReplyVO);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@GetMapping("/all/{questionCommentsId}") // GET : 음식 레시피 답글 선택(all)
	public ResponseEntity<List<QuestionReplyVO>> readyAllQuestionComments(@PathVariable("questionCommentsId") int questionCommentsId) {
		log.info("readyAllQuestionReply()");
		log.info("questionCommentsId = " + questionCommentsId);

		List<QuestionReplyVO> list = questionReplyService.getAllReplies(questionCommentsId);
		return new ResponseEntity<List<QuestionReplyVO>>(list, HttpStatus.OK);
	}

	@PutMapping("/{questionReplyId}") // PUT : 음식 레시피 답글 수정
	public ResponseEntity<Integer> updateReply(@PathVariable("questionReplyId") int questionReplyId, 
		@RequestBody String questionReplyContent) {
		log.info("updateQuestionReply()");
		log.info("questionReplyId = " + questionReplyId);
		int result = questionReplyService.updateReply(questionReplyId, questionReplyContent);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{questionReplyId}/{questionCommentsId}") // DELETE : 음식 레시피 답글 삭제
	public ResponseEntity<Integer> deleteReply(@PathVariable("questionReplyId") int questionReplyId,
			@PathVariable("questionCommentsId") int questionCommentsId) {
		log.info("deletequestionReply()");
		log.info("questionReplyId = " + questionReplyId);

		int result = questionReplyService.deleteReply(questionReplyId, questionCommentsId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}