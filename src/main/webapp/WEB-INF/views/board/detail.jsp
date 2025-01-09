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
		<input type="text" id="memberId"> <input type="text"
			id="foodRecipeCommentsContent">
		<button id="btnAdd">작성</button>
	</div>

	<hr>
	<div style="text-align: center;">
		<div id="foodRecipeComments"></div>
	</div>

	<script type="text/javascript">
		$(document).ready(function(){
			getAllFoodRecipeComments(); // 댓글 목록 가져오기
		
			// 댓글 작성 기능
			$('#btnAdd').click(function(){
				var foodRecipeBoardId = $('#foodRecipeBoardId').val(); // 게시판 번호
				var memberId = $('#memberId').val(); // 작성자 ID
				var foodRecipeCommentsContent = $('#foodRecipeCommentsContent').val(); // 댓글 내용
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
					url : '../foodRecipeComments', // 댓글 작성 URL
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
				var foodRecipeBoardId = $('#foodRecipeBoardId').val(); // 게시판 ID
				
				var url = '../foodRecipeComments/all/' + foodRecipeBoardId; // 댓글 목록을 가져올 URL 
				$.getJSON(
					url, 		
					function(data) {
						// 서버에서 전송받은 댓글 목록을 data로 받음
						console.log(data);
						
						var list = ''; // HTML로 출력할 댓글 목록을 담을 문자열 변수
						
						// 각 댓글에 대해 반복하며 HTML 요소를 생성
						$(data).each(function(){
							// 댓글 작성 시간 문자열을 Date 객체로 변환
							var foodRecipeCommentsCreated = new Date(this.foodRecipeCommentsCreated);

							list += '<div class="foodRecipeComments_item">'
								+ '<pre>'
								+ '<input type="hidden" id="foodRecipeCommentsId" value="'+ this.foodRecipeCommentsId +'">'
								+ this.memberId
								+ '&nbsp;&nbsp;' 
								+ '<input type="text" id="foodRecipeCommentsContent" value="'+ this.foodRecipeCommentsContent +'">'
								+ '&nbsp;&nbsp;'
								+ foodRecipeCommentsCreated
								+ '&nbsp;&nbsp;'
								+ '<button class="btn_update">수정</button>'
								+ '<button class="btn_delete">삭제</button>'
								+ '&nbsp;&nbsp;'
								+ '<button class="btn_reply">답글</button>'  <!-- 답글 버튼 추가 -->
								+ '</pre>'
								+ '</div>';
						});
						
						$('#foodRecipeComments').html(list); // HTML에 댓글 목록을 삽입
					}
				); 
			} 
			
			// 댓글 수정 기능
			$('#foodRecipeComments').on('click', '.foodRecipeComments_item .btn_update', function(){
				console.log(this);

				var foodRecipeCommentsId = $(this).prevAll('#foodRecipeCommentsId').val(); // 댓글 ID
				var foodRecipeCommentsContent = $(this).prevAll('#foodRecipeCommentsContent').val(); // 댓글 내용
				console.log("선택된 댓글 번호 : " + foodRecipeCommentsId + ", 댓글 내용 : " + foodRecipeCommentsContent);

				// 댓글 수정 요청
				$.ajax({
					type : 'PUT', 
					url : '../foodRecipeComments/' + foodRecipeCommentsId, // 수정 URL 
					headers : {
						'Content-Type' : 'application/json'
					},
					data : foodRecipeCommentsContent, 
					success : function(result) {
						if(result == 1) {
							alert('댓글 수정 성공!');
							getAllFoodRecipeComments(); // 수정된 댓글 목록 갱신
						}
					}
				});
			}); 
			
			// 댓글 삭제 기능
			$('#foodRecipeComments').on('click', '.foodRecipeComments_item .btn_delete', function(){
				console.log(this);

				var foodRecipeBoardId = $('#foodRecipeBoardId').val(); 
				var foodRecipeCommentsId = $(this).prevAll('#foodRecipeCommentsId').val(); // 삭제할 댓글 ID
				
				// 댓글 삭제 요청
				$.ajax({
					type : 'DELETE', 
					url : '../foodRecipeComments/' + foodRecipeCommentsId + '/' + foodRecipeBoardId, // 삭제 URL 
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
			$('#foodRecipeComments').on('click', '.foodRecipeComments_item .btn_reply', function() {
			    var foodRecipeCommentsId = $(this).closest('.foodRecipeComments_item').find('#foodRecipeCommentsId').val();
			    var memberId = $('#memberId').val(); 
			    var foodRecipeReplyContent = prompt("답글을 입력하세요: ");

			    if (foodRecipeReplyContent) {
			        // 답글 데이터를 객체로 생성
			        var replyObj = {
			            'foodRecipeCommentsId': foodRecipeCommentsId,
			            'memberId': memberId,
			            'foodRecipeReplyContent': foodRecipeReplyContent
			        };

			        // 답글 전송
			        $.ajax({
			            type: 'POST',
			            url: '../foodRecipeComments/foodRecipeReply', // 답글 작성 URL
			            headers: {
			                'Content-Type': 'application/json' // JSON 형식으로 보내기 위한 헤더
			            },
			            data: JSON.stringify(replyObj), // 객체를 JSON으로 변환
			            success: function(result) {
			                if (result == 1) {
			                    alert('답글 작성 성공!');
			                    getAllFoodRecipeComments(); // 댓글 목록 갱신
			                }
			            }
			        });
			    }
			});
		});
	</script>

</body>
</html>
