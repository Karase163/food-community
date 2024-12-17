<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글</title>
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
</head>
<body>

	<input type="hidden" id="boardId" value="3">

	<div style="text-align: center;">
		<input type="text" id="memberId" >
		<input type="text" id="commentsContent">
		<button id="btnAdd">작성</button>
	</div>

	<hr>
	<div style="text-align: center;">
		<div id="replies"></div>
	</div>

	<script type="text/javascript">
		$(document).ready(function(){
			getAllComments(); // 함수 호출		
			
			// 댓글 작성 기능
			$('#btnAdd').click(function(){
				var boardId = $('#boardId').val(); // 게시판 번호 데이터
				var memberId = $('#memberId').val(); // 작성자 데이터
				var commentsContent = $('#commentsContent').val(); // 댓글 내용
				// javascript 객체 생성
				var obj = {
						'boardId' : boardId,
						'memberId' : memberId,
						'commentsContent' : commentsContent
				}
				console.log(obj);
				
				// $.ajax로 송수신
				$.ajax({
					type : 'POST', // 메서드 타입
					url : '../comments', // url
					headers : { // 헤더 정보
						'Content-Type' : 'application/json' // json content-type 설정 header를 설정하지 않으면 데이터 전송에 문제가 생긴다.
					}, 
					data : JSON.stringify(obj), // JSON으로 변환
					success : function(result) { // 전송 성공 시 서버에서 result 값 전송
						console.log(result);
						if(result == 1) {
							alert('댓글 입력 성공');
							getAllReply(); // 함수 호출		
						}
					}
				});
			}); // end btnAdd.click()
			
			// 게시판 댓글 전체 가져오기
			function getAllReply() {
				var boardId = $('#boardId').val();
				
				var url = '../comments/all/' + boardId;
				$.getJSON(
					url, 		
					function(data) {
						// data : 서버에서 전송받은 list 데이터가 저장되어 있음.
						// getJSON()에서 json 데이터는 
						// javascript object로 자동 parsing됨.
						console.log(data);
						
						var list = ''; // 댓글 데이터를 HTML에 표현할 문자열 변수
						
						// $(컬렉션).each() : 컬렉션 데이터를 반복문으로 꺼내는 함수
						$(data).each(function(){
							// this : 컬렉션의 각 인덱스 데이터를 의미
							console.log(this);
						  
							// 전송된 commentsDateCreated는 문자열 형태이므로 날짜 형태로 변환이 필요
							var commentsDateCreated = new Date(this.commentsDateCreated);

							list += '<div class="comments_item">'
								+ '<pre>'
								+ '<input type="hidden" id="commentsId" value="'+ this.commentsId +'">'
								+ this.memberId
								+ '&nbsp;&nbsp;' // 공백
								+ '<input type="text" id="commentsContent" value="'+ this.commentsContent +'">'
								+ '&nbsp;&nbsp;'
								+ commentsDateCreated
								+ '&nbsp;&nbsp;'
								+ '<button class="btn_update" >수정</button>'
								+ '<button class="btn_delete" >삭제</button>'
								+ '</pre>'
								+ '</div>';
						}); // end each()
							
						$('#commentsList').html(list); // 저장된 데이터를 commentsList div 표현
					} // end function()
				); // end getJSON()
			} // end getAllReply()
			
			// 수정 버튼을 클릭하면 선택된 댓글 수정
			$('#commentsList').on('click', '.comments_item .btn_update', function(){
				console.log(this);
				
				// 선택된 댓글의 commentsId, commentsContent 값을 저장
				// prevAll() : 선택된 노드 이전에 있는 모든 형제 노드를 접근
				var commentsId = $(this).prevAll('#commentsId').val();
				var commentsContent = $(this).prevAll('#commentsContent').val();
				console.log("선택된 댓글 번호 : " + commentsId + ", 댓글 내용 : " + commentsContent);
				
				// ajax 요청
				$.ajax({
					type : 'PUT', 
					url : '../comments/' + commentsId,
					headers : {
						'Content-Type' : 'application/json'
					},
					data : commentsContent, 
					success : function(result) {
						console.log(result);
						if(result == 1) {
							alert('댓글 수정 성공!');
							getAllComments();
						}
					}
				});
				
			}); // end commentsList.on()
			
			// 삭제 버튼을 클릭하면 선택된 댓글 삭제
			$('#commentsList').on('click', '.comments_item .btn_delete', function(){
				console.log(this);
				var boardId = $('#boardId').val(); // 게시판 번호 데이터
				var commentsId = $(this).prevAll('#commentsId').val();
				
				// ajax 요청
				$.ajax({
					type : 'DELETE', 
					url : '../comments/' + commentsId + '/' + boardId, 
					headers : {
						'Content-Type' : 'application/json'
					},
					success : function(result) {
						console.log(result);
						if(result == 1) {
							alert('댓글 삭제 성공!');
							getAllComments();
						}
					}
				});
			}); // end commentsList.on()		

		}); // end document()
	</script>

</body>
</html>






