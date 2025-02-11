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


// 비동기 방식이기 때문에 JSON 사용을 위한 @RestController 사용
@RestController // @Responsebody와 @Requestbody를 사용해서 JSON데이터의 변환을 자동으로 해주는 언노테이션
@RequestMapping(value = "/foodRecipeComments")
@Log4j
public class FoodRecipeCommentsRESTController {

	@Autowired
	private FoodRecipeCommentsService foodRecipeCommentsService;

	@PostMapping // POST : 음식 레시피 댓글 입력
	public ResponseEntity<Integer> createFoodRecipeComments(@RequestBody FoodRecipeCommentsVO foodRecipeCommentsVO) {
		log.info("createFoodRecipeComments()");
		log.info(foodRecipeCommentsVO);
		int result = foodRecipeCommentsService.createComments(foodRecipeCommentsVO);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@GetMapping("/all/{foodRecipeBoardId}")
	public ResponseEntity<List<FoodRecipeCommentsVO>> getAllFoodRecipeComments(@PathVariable("foodRecipeBoardId") int foodRecipeBoardId) {
	    log.info("getAllFoodRecipeComments()");
	    log.info("foodRecipeBoardId = " + foodRecipeBoardId);

	    List<FoodRecipeCommentsVO> list = foodRecipeCommentsService.getAllComments(foodRecipeBoardId);
	    return new ResponseEntity<List<FoodRecipeCommentsVO>>(list, HttpStatus.OK);
	}


	@PutMapping("/{foodRecipeCommentsId}") // PUT : 음식 레시피 댓글 수정
	public ResponseEntity<Integer> updateReply(@PathVariable("foodRecipeCommentsId") int foodRecipeCommentsId, 
		@RequestBody String foodRecipeCommentsContent) {
		log.info("updateFoodRecipeReply()");
		log.info("foodRecipeCommentsId = " + foodRecipeCommentsId);
		int result = foodRecipeCommentsService.updateComments(foodRecipeCommentsId, foodRecipeCommentsContent);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{foodRecipeCommentsId}/{foodRecipeBoardId}") // DELETE : 음식 레시피 댓글 삭제
	public ResponseEntity<Integer> deletefoodRecipeComments(
			@PathVariable("foodRecipeCommentsId") int foodRecipeCommentsId,
			@PathVariable("foodRecipeBoardId") int foodRecipeBoardId) {
		log.info("deletefoodRecipeComments()");
		log.info("foodRecipeCommentsId = " + foodRecipeCommentsId);
		log.info("foodRecipeBoardId = " + foodRecipeBoardId);

		int result = foodRecipeCommentsService.deleteComments(foodRecipeCommentsId, foodRecipeBoardId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}