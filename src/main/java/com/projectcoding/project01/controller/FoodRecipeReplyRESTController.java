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

import com.projectcoding.project01.domain.FoodRecipeReplyVO;
import com.projectcoding.project01.service.FoodRecipeReplyService;

import lombok.extern.log4j.Log4j;


// 비동기 방식이기 때문에 JSON 사용을 위한 @RestController 사용
@RestController // @Responsebody와 @Requestbody를 사용해서 JSON데이터의 변환을 자동으로 해주는 언노테이션
@RequestMapping(value = "/foodRecipeReply")
@Log4j
public class FoodRecipeReplyRESTController {

	@Autowired
	private FoodRecipeReplyService foodRecipeReplyService;

	@PostMapping // POST : 음식 레시피 답글 입력
	public ResponseEntity<Integer> createFoodRecipeReply(@RequestBody FoodRecipeReplyVO foodRecipeReplyVO) {
		log.info("createFoodRecipeReply()");
		log.info(foodRecipeReplyVO);
		int result = foodRecipeReplyService.createReply(foodRecipeReplyVO);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@GetMapping("/all/{foodRecipeCommentsId}") // GET : 음식 레시피 답글 선택(all)
	public ResponseEntity<List<FoodRecipeReplyVO>> readyAllFoodRecipeComments(@PathVariable("foodRecipeCommentsId") int foodRecipeCommentsId) {
		log.info("readyAllFoodRecipeReply()");
		log.info("foodRecipeCommentsId = " + foodRecipeCommentsId);

		List<FoodRecipeReplyVO> list = foodRecipeReplyService.getAllReplies(foodRecipeCommentsId);
		return new ResponseEntity<List<FoodRecipeReplyVO>>(list, HttpStatus.OK);
	}

	@PutMapping("/{foodRecipeReplyId}") // PUT : 음식 레시피 답글 수정
	public ResponseEntity<Integer> updateReply(@PathVariable("foodRecipeReplyId") int foodRecipeReplyId, 
		@RequestBody String foodRecipeReplyContent) {
		log.info("updateFoodRecipeReply()");
		log.info("foodRecipeReplyId = " + foodRecipeReplyId);
		int result = foodRecipeReplyService.updateReply(foodRecipeReplyId, foodRecipeReplyContent);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{foodRecipeReplyId}/{foodRecipeCommentsId}") // DELETE : 음식 레시피 답글 삭제
	public ResponseEntity<Integer> deleteReply(@PathVariable("foodRecipeReplyId") int foodRecipeReplyId,
			@PathVariable("foodRecipeCommentsId") int foodRecipeCommentsId) {
		log.info("deleteFoodRecipeReply()");
		log.info("foodRecipeReplyId = " + foodRecipeReplyId);

		int result = foodRecipeReplyService.deleteReply(foodRecipeReplyId, foodRecipeCommentsId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}

