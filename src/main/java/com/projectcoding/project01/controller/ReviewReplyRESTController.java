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

import com.projectcoding.project01.domain.ReviewReplyVO;
import com.projectcoding.project01.service.ReviewReplyService;

import lombok.extern.log4j.Log4j;


// 비동기 방식이기 때문에 JSON 사용을 위한 @RestController 사용
@RestController // @Responsebody와 @Requestbody를 사용해서 JSON데이터의 변환을 자동으로 해주는 언노테이션
@RequestMapping(value = "/reviewReply")
@Log4j
public class ReviewReplyRESTController {

	@Autowired
	private ReviewReplyService reviewReplyService;

	@PostMapping // POST : 음식 레시피 답글 입력
	public ResponseEntity<Integer> createReviewReply(@RequestBody ReviewReplyVO reviewReplyVO) {
		log.info("createReviewReply()");
		log.info(reviewReplyVO);
		int result = reviewReplyService.createReply(reviewReplyVO);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@GetMapping("/all/{reviewCommentsId}") // GET : 음식 레시피 답글 선택(all)
	public ResponseEntity<List<ReviewReplyVO>> readyAllReviewComments(@PathVariable("reviewCommentsId") int reviewCommentsId) {
		log.info("readyAllReviewReply()");
		log.info("reviewCommentsId = " + reviewCommentsId);

		List<ReviewReplyVO> list = reviewReplyService.getAllReplies(reviewCommentsId);
		return new ResponseEntity<List<ReviewReplyVO>>(list, HttpStatus.OK);
	}

	@PutMapping("/{reviewReplyId}") // PUT : 음식 레시피 답글 수정
	public ResponseEntity<Integer> updateReply(@PathVariable("reviewReplyId") int reviewReplyId, 
		@RequestBody String reviewReplyContent) {
		log.info("updateReviewReply()");
		log.info("reviewReplyId = " + reviewReplyId);
		int result = reviewReplyService.updateReply(reviewReplyId, reviewReplyContent);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{reviewReplyId}/{reviewCommentsId}") // DELETE : 음식 레시피 답글 삭제
	public ResponseEntity<Integer> deleteReply(@PathVariable("reviewReplyId") int reviewReplyId,
			@PathVariable("reviewCommentsId") int reviewCommentsId) {
		log.info("deleteReviewReply()");
		log.info("reviewReplyId = " + reviewReplyId);

		int result = reviewReplyService.deleteReply(reviewReplyId, reviewCommentsId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}