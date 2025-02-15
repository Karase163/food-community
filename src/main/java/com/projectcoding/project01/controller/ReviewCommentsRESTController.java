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

import com.projectcoding.project01.domain.ReviewCommentsVO;
import com.projectcoding.project01.service.ReviewCommentsService;

import lombok.extern.log4j.Log4j;


// 비동기 방식이기 때문에 JSON 사용을 위한 @RestController 사용
@RestController // @Responsebody와 @Requestbody를 사용해서 JSON데이터의 변환을 자동으로 해주는 언노테이션
@RequestMapping(value = "/reviewComments")
@Log4j
public class ReviewCommentsRESTController {

	@Autowired
	private ReviewCommentsService reviewCommentsService;

	@PostMapping // POST : 음식 레시피 댓글 입력
	public ResponseEntity<Integer> createReviewComments(@RequestBody ReviewCommentsVO reviewCommentsVO) {
		log.info("createReviewComments()");
		log.info(reviewCommentsVO);
		int result = reviewCommentsService.createComments(reviewCommentsVO);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@GetMapping("/all/{reviewBoardId}")
	public ResponseEntity<List<ReviewCommentsVO>> getAllReviewComments(@PathVariable("reviewBoardId") int reviewBoardId) {
	    log.info("getAllReviewComments()");
	    log.info("reviewBoardId = " + reviewBoardId);

	    List<ReviewCommentsVO> list = reviewCommentsService.getAllComments(reviewBoardId);
	    return new ResponseEntity<List<ReviewCommentsVO>>(list, HttpStatus.OK);
	}


	@PutMapping("/{reviewCommentsId}") // PUT : 음식 레시피 댓글 수정
	public ResponseEntity<Integer> updateReply(@PathVariable("reviewCommentsId") int reviewCommentsId, 
		@RequestBody String reviewCommentsContent) {
		log.info("updatereviewReply()");
		log.info("reviewCommentsId = " + reviewCommentsId);
		int result = reviewCommentsService.updateComments(reviewCommentsId, reviewCommentsContent);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{reviewCommentsId}/{reviewBoardId}") // DELETE : 음식 레시피 댓글 삭제
	public ResponseEntity<Integer> deletereviewComments(
			@PathVariable("reviewCommentsId") int reviewCommentsId,
			@PathVariable("reviewBoardId") int reviewBoardId) {
		log.info("deletereviewComments()");
		log.info("reviewCommentsId = " + reviewCommentsId);
		log.info("reviewBoardId = " + reviewBoardId);

		int result = reviewCommentsService.deleteComments(reviewCommentsId, reviewBoardId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}