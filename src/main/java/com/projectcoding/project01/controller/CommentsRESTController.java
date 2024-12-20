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

import com.projectcoding.project01.domain.FoodRecipeCommentsVO;
import com.projectcoding.project01.service.FoodRecipeCommentsService;
import lombok.extern.log4j.Log4j;

@RestController 
@RequestMapping(value = "/comments")
@Log4j
public class CommentsRESTController {

	@Autowired
	private FoodRecipeCommentsService foodrecipecommentsService;

	@PostMapping // POST : 댓글 입력
	public ResponseEntity<Integer> createComments(@RequestBody FoodRecipeCommentsVO commentsVO) {
		log.info("createComments()");
		log.info(commentsVO);
		int result = commentsService.createComments(commentsVO);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@GetMapping("/all/{boardId}") // GET : 댓글 선택(all)
	public ResponseEntity<List<FoodRecipeCommentsVO>> readyAllComments(@PathVariable("boardId") int boardId) {
		// @PathVariable("boardId") : {boardId} 값을 설정된 변수에 저장
		log.info("readyAllComments()");
		log.info("boardId = " + boardId);

		List<FoodRecipeCommentsVO> list = commentsService.getAllComments(boardId);
		return new ResponseEntity<List<FoodRecipeCommentsVO>>(list, HttpStatus.OK);
	}
	
	@PutMapping("/{commentsId}") // PUT : 댓글 수정
	public ResponseEntity<Integer> updateReply(@PathVariable("commentsId") int commentsId, @RequestBody String commentsContent) {
		log.info("updateComments()");
		log.info("commentsId = " + commentsId);
		int result = commentsService.updateComments(commentsId, commentsContent);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{commentsId}/{boardId}") // DELETE : 댓글 삭제
	public ResponseEntity<Integer> deleteComments(@PathVariable("commentsId") int commentsId,
			@PathVariable("boardId") int boardId) {
		log.info("deleteComments()");
		log.info("commentsId = " + commentsId);

		int result = commentsService.deleteComments(commentsId, boardId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}


