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

// * RESTful url과 의미
// /foodRecipeComments (POST)		 : 음식 레시피 댓글 추가(insert)
// /foodRecipeComments/all/숫자 (GET) 	 : 해당 레시피 번호(boardId)의 모든 댓글 검색(select)
// /foodRecipeComments/숫자 (PUT)		 : 해당 댓글 번호(replyId)의 내용을 수정(update)
// /foodRecipeComments/숫자 (DELETE)	 : 해당 댓글 번호(replyId)의 내용을 삭제(delete)

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

	@GetMapping("/all/{foodRecipeBoardId}") // GET : 음식 레시피 댓글 선택(all)
	public ResponseEntity<List<FoodRecipeCommentsVO>> readyAllFoodRecipeComments(@PathVariable("foodRecipeBoardId") int foodRecipeBoardId) {
		log.info("readyAllFoodRecipeComments()");
		log.info("foodRecipeBoardId = " + foodRecipeBoardId);

		List<FoodRecipeCommentsVO> list = foodRecipeCommentsService.getAllComments(foodRecipeBoardId);
		// ResponseEntity<T> : T의 타입은 프론트 side로 전송될 데이터의 타입으로 선언
		return new ResponseEntity<List<FoodRecipeCommentsVO>>(list, HttpStatus.OK);
	}

	@PutMapping("/{foodRecipeCommentsId}") // PUT : 음식 레시피 댓글 수정
	public ResponseEntity<Integer> updateComment(@PathVariable("foodRecipeCommentsId") int foodRecipeCommentsId, 
		@RequestBody String foodRecipeCommentsContent) {
		log.info("updateFoodRecipeComments()");
		log.info("foodRecipeCommentsId = " + foodRecipeCommentsId);
		int result = foodRecipeCommentsService.updateComments(foodRecipeCommentsId, foodRecipeCommentsContent);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{foodRecipeCommentsId}/{foodRecipeBoardId}") // DELETE : 음식 레시피 댓글 삭제
	public ResponseEntity<Integer> deleteComments(@PathVariable("foodRecipeCommentsId") int foodRecipeCommentsId,
			@PathVariable("foodRecipeBoardId") int foodRecipeBoardId) {
		log.info("deleteFoodRecipeComments()");
		log.info("foodRecipeCommentsId = " + foodRecipeCommentsId);

		int result = foodRecipeCommentsService.deleteComments(foodRecipeCommentsId, foodRecipeBoardId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}
