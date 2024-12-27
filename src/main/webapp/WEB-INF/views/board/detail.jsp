<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글</title>
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
</head>
<body>

	<input type="hidden" id="foodRecipeBoardId" value="3">

	<div style="text-align: center;">
		<input type="text" id="memberId" >
		<input type="text" id="foodRecipeCommentContent">
		<button id="btnAdd">작성</button>
	</div>

	<hr>
	<div style="text-align: center;">
		<div id="foodRecipeCommentsList"></div> <!-- 복수형으로 변경 -->
	</div>

	<script type="text/javascript">
		$(document).ready(function(){
			getAllFoodRecipeCommentsList(); // 댓글 목록 가져오기
		
			// 댓글 작성 기능
			$('#btnAdd').click(function(){
				var foodRecipeBoardId = $('#foodRecipeBoardId').val(); // 게시판 번호
				var memberId = $('#memberId').val(); // 작성자 ID
				var foodRecipeCommentContent = $('#foodRecipeCommentsContent').val(); // 댓글 내용
				// 댓글 데이터를 객체로 생성
				var obj = {
						'foodRecipeBoardId' : foodRecipeBoardId,
						'memberId' : memberId,
						'foodRecipeCommentsContent' : foodRecipeCommentsContent
				}
				console.log(obj);
				
				// 서버로 댓글을 전송하는 ajax 요청
				$.ajax({
					type : 'POST', // HTTP 메소드: POST
					url : '../foodRecipeCommentsList', // 댓글 작성 URL (복수형)
					headers : { // JSON 형태로 데이터를 보내기 위한 헤더 설정
						'Content-Type' : 'application/json' 
					}, 
					data : JSON.stringify(obj), // 객체를 JSON 형식으로 변환하여 전송
					success : function(result) { // 서버 응답 처리
						console.log(result);
						if(result == 1) {
							alert('댓글 입력 성공');
							getAllFoodRecipeComments(); // 댓글 목록 갱신
						}
					}
				});
			}); // end btnAdd.click()
			
			// 게시판 댓글 목록 가져오기
			function getAllFoodRecipeComments() {
				var boardId = $('#foodRecipeBoardId').val(); // 게시판 ID
				
				var url = '../foodRecipeCommentsList/all/' + foodRecipeBoardId; // 댓글 목록을 가져올 URL (복수형)
				$.getJSON(
					url, 		
					function(data) {
						// 서버에서 전송받은 댓글 목록을 data로 받음
						console.log(data);
						
						var list = ''; // HTML로 출력할 댓글 목록을 담을 문자열 변수
						
						// 각 댓글에 대해 반복하며 HTML 요소를 생성
						$(data).each(function(){
							// 댓글 작성 시간 문자열을 Date 객체로 변환
							var foodRecipeCommentDateCreated = new Date(this.foodRecipeCommentDateCreated);

							list += '<div class="foodRecipeComment_item" id="comment_' + this.foodRecipeCommentId + '">'
								+ '<pre>'
								+ '<input type="hidden" id="foodRecipeCommentId" value="'+ this.foodRecipeCommentId +'">'
								+ this.memberId
								+ '&nbsp;&nbsp;' 
								+ '<input type="text" id="foodRecipeCommentContent" value="'+ this.foodRecipeCommentContent +'">'
								+ '&nbsp;&nbsp;'
								+ foodRecipeCommentDateCreated
								+ '&nbsp;&nbsp;'
								+ '<button class="btn_update">수정</button>'
								+ '<button class="btn_delete">삭제</button>'
								+ '&nbsp;&nbsp;'
								+ '<button class="btn_reply">답글</button>'  <!-- 답글 버튼 추가 -->
								+ '</pre>'
								+ '<div class="replies"></div>'  <!-- 답글 목록을 넣을 div -->
								+ '</div>';
							
							// 댓글에 달린 답글을 표시
							if (this.replies) {
								$(this.replies).each(function() {
									list += '<div class="reply_item">'
										+ this.memberId + ' : ' + this.foodRecipeCommentContent
										+ '&nbsp;&nbsp;<button class="btn_reply_update">수정</button>'
										+ '&nbsp;&nbsp;<button class="btn_reply_delete">삭제</button>'
										+ '</div>';
								});
							}
						});
						
						$('#foodRecipeCommentslist').html(list); // HTML에 댓글 목록을 삽입
					}
				); 
			} 
			
			// 댓글 수정 기능
			$('#foodRecipeCommentslist').on('click', '.foodRecipeComment_item .btn_update', function(){
				var foodRecipeCommentId = $(this).prevAll('#foodRecipeCommentId').val(); // 댓글 ID
				var foodRecipeCommentContent = $(this).prevAll('#foodRecipeCommentContent').val(); // 댓글 내용
				
				// 댓글 수정 요청
				$.ajax({
					type : 'PUT', 
					url : '../foodRecipeComments/' + foodRecipeCommentId, // 수정 URL (복수형)
					headers : {
						'Content-Type' : 'application/json'
					},
					data : foodRecipeCommentContent, 
					success : function(result) {
						if(result == 1) {
							alert('댓글 수정 성공!');
							getAllFoodRecipeComments(); // 수정된 댓글 목록 갱신
						}
					}
				});
			}); 
			
			// 댓글 삭제 기능
			$('#foodRecipeCommentslist').on('click', '.foodRecipeComment_item .btn_delete', function(){
				var boardId = $('#boardId').val(); 
				var foodRecipeCommentId = $(this).prevAll('#foodRecipeCommentId').val(); // 삭제할 댓글 ID
				
				// 댓글 삭제 요청
				$.ajax({
					type : 'DELETE', 
					url : '../foodRecipeComments/' + foodRecipeCommentId + '/' + boardId, // 삭제 URL (복수형)
					headers : {
						'Content-Type' : 'application/json'
					},
					success : function(result) {
						if(result == 1) {
							alert('댓글 삭제 성공!');
							getAllFoodRecipeComments(); // 삭제 후 댓글 목록 갱신
						}
					}
				});
			}); 

			// 답글 작성 기능
			$('#foodRecipeCommentslist').on('click', '.foodRecipeComment_item .btn_reply', function(){
				var parentCommentId = $(this).closest('.foodRecipeComment_item').find('#foodRecipeCommentId').val(); // 부모 댓글 ID
				var memberId = $('#memberId').val(); // 작성자 ID
				var replyContent = prompt("답글을 입력하세요: "); // 사용자로부터 답글 입력 받기

				if(replyContent) {
					// 답글 데이터를 객체로 생성
					var replyObj = {
						'parentCommentId': parentCommentId,
						'memberId': memberId,
						'foodRecipeCommentContent': replyContent
					};
					
					// 답글 전송
					$.ajax({
						type : 'POST',
						url : '../foodRecipeComments/reply', // 답글 작성 URL
						headers : {
							'Content-Type' : 'application/json'
						},
						data : JSON.stringify(replyObj),
						success : function(result) {
							if(result == 1) {
								alert('답글 작성 성공!');
								getAllFoodRecipeComments(); // 댓글 목록 갱신
							}
						}
					});
				}
			}); 

			// 답글 수정 기능
			$('#foodRecipeCommentslist').on('click', '.foodRecipeComment_item .btn_reply_update', function(){
				var replyId = $(this).closest('.reply_item').data('reply-id'); // 답글 ID
				var updatedContent = prompt("답글을 수정하세요: ");
				if(updatedContent) {
					$.ajax({
						type: 'PUT',
						url: '../foodRecipeComments/reply/' + replyId,
						headers: {
							'Content-Type': 'application/json'
						},
						data: updatedContent,
						success: function(result) {
							if(result == 1) {
								alert('답글 수정 성공!');
								getAllFoodRecipeComments();
							}
						}
					});
				}
			});

			// 답글 삭제 기능
			$('#foodRecipeCommentslist').on('click', '.foodRecipeComment_item .btn_reply_delete', function(){
				var replyId = $(this).closest('.reply_item').data('reply-id'); // 답글 ID
				$.ajax({
					type: 'DELETE',
					url: '../foodRecipeComments/reply/' + replyId,
					success: function(result) {
						if(result == 1) {
							alert('답글 삭제 성공!');
							getAllFoodRecipeComments(); // 삭제 후 댓글 목록 갱신
						}
					}
				});
			});
		});
	</script>

</body>
</html>
