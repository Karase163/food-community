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

	<input type="hidden" id="reviewBoardId" value="2">

	<div style="text-align: center;">
		<input type="text" id="memberId"> 
		<input type="text" id="reviewCommentsContent">
		<button id="btnAdd">작성</button>
	</div>

	<hr>
	<div style="text-align: center;">
		<div id="reviewComments"></div>
	</div>

	<script type="text/javascript">
    $(document).ready(function(){
        getAllReviewComments(); // 댓글 목록 가져오기

        // 댓글 작성 기능
        $('#btnAdd').click(function(){
            var reviewBoardId = $('#reviewBoardId').val(); // 게시판 번호
            var memberId = $('#memberId').val(); // 작성자 ID
            var reviewCommentsContent = $('#reviewCommentsContent').val(); // 댓글 내용
            // 댓글 데이터를 객체로 생성
            var obj = {
                    'reviewBoardId' : reviewBoardId,
                    'memberId' : memberId,
                    'reviewCommentsContent' : reviewCommentsContent
            }
            console.log('댓글 작성 데이터:', obj);

            // 서버로 댓글을 전송하는 ajax 요청
            $.ajax({
                type : 'POST', // HTTP 메소드: POST
                url : '../reviewComments', // 댓글 작성 URL
                headers : { // JSON 형태로 데이터를 보내기 위한 헤더 설정
                    'Content-Type' : 'application/json' 
                }, 
                data : JSON.stringify(obj), // 객체를 JSON 형식으로 변환하여 전송
                success : function(result) { // 서버 응답 처리
                    console.log('서버 응답:', result);
                    if(result == 1) {
                        alert('댓글 입력 성공');
                        getAllReviewComments(); // 댓글 목록 갱신
                    }
                }
            });
        }); // end btnAdd.click()

        // 게시판 댓글 목록 가져오기
        function getAllReviewComments() {
            var reviewBoardId = $('#reviewBoardId').val(); // 게시판 ID

            var url = '../reviewComments/all/' + reviewBoardId; // 댓글 목록을 가져올 URL 
            $.getJSON(url, function(data) {
                // 서버에서 전송받은 댓글 목록을 data로 받음
                console.log('댓글 목록 데이터:', data);

                var list = ''; // HTML로 출력할 댓글 목록을 담을 문자열 변수

                // 각 댓글에 대해 반복하며 HTML 요소를 생성
                $(data).each(function(){
                    var reviewCommentsCreated = new Date(this.reviewCommentsCreated);

                    list += '<div class="reviewComments_item">'
                        + '<pre>'
                        + '<input type="hidden" id="reviewCommentsId" value="'+ this.reviewCommentsId +'">'
                        + this.memberId
                        + '&nbsp;&nbsp;' 
                        + '<input type="text" id="reviewCommentsContent" value="'+ this.reviewCommentsContent +'">'
                        + '&nbsp;&nbsp;'
                        + reviewCommentsCreated
                        + '&nbsp;&nbsp;'
                        + '<button class="btn_update">수정</button>'
                        + '<button class="btn_delete">삭제</button>'
                        + '&nbsp;&nbsp;'
                        + '<button class="btn_reply">답글</button>'
                        + '<div class="replies"></div>'  <!-- 답글 표시 영역 추가 -->
                        + '</pre>'
                        + '</div>';
                });

                $('#reviewComments').html(list); // HTML에 댓글 목록을 삽입

                // 각 댓글에 대해 답글 목록을 가져오는 부분을 분리하여 처리
                $(data).each(function() {
                    getRepliesForComment(this.reviewCommentsId); // 개별 댓글의 답글 목록을 갱신
                });
            }); 
        }

        // 댓글 수정 기능
        $('#reviewComments').on('click', '.reviewComments_item .btn_update', function(){
            var reviewCommentsId = $(this).prevAll('#reviewCommentsId').val(); // 댓글 ID
            var reviewCommentsContent = $(this).prevAll('#reviewCommentsContent').val(); // 댓글 내용

            // 댓글 수정 요청
            $.ajax({
                type : 'PUT', 
                url : '../reviewComments/' + reviewCommentsId, // 수정 URL 
                headers : {
                    'Content-Type' : 'application/json'
                },
                data : reviewCommentsContent, 
                success : function(result) {
                    console.log('댓글 수정 응답:', result);
                    if(result == 1) {
                        alert('댓글 수정 성공!');
                        getAllReviewComments(); // 댓글 목록만 갱신
                    }
                }
            });
        }); 
        
        // 댓글 삭제 기능
        $('#reviewComments').on('click', '.reviewComments_item .btn_delete', function(){
            var reviewBoardId = $('#reviewBoardId').val(); 
            var reviewCommentsId = $(this).prevAll('#reviewCommentsId').val(); // 삭제할 댓글 ID
            
            // 댓글 삭제 요청
            $.ajax({
                type : 'DELETE', 
                url : '../reviewComments/' + reviewCommentsId + '/' + reviewBoardId, // 삭제 URL 
                headers : {
                    'Content-Type' : 'application/json'
                },
                success : function(result) {
                    console.log('댓글 삭제 응답:', result);
                    if(result == 1) {
                        alert('댓글 삭제 성공!');
                        getAllReviewComments(); // 댓글 목록 갱신 (새로고침 없이 갱신)
                    }
                }
            });
        }); 
        
        // 답글 작성 기능
        $('#reviewComments').on('click', '.reviewComments_item .btn_reply', function() {
            var reviewCommentsId = $(this).closest('.reviewComments_item').find('#reviewCommentsId').val();
            var memberId = 'user'; // TODO : 로그인 기능 완성하면, 로그인된 사용자 아이디를 가져오도록 구현
            var reviewReplyContent = prompt("답글을 입력하세요: ");

            if (reviewReplyContent) {
                // 답글 데이터를 객체로 생성
                var reviewObj = {
                    'reviewCommentsId': reviewCommentsId,
                    'memberId': memberId,
                    'reviewReplyContent': reviewReplyContent
                };
                console.log('답글 작성 데이터:', reviewObj);

                // 답글 전송
                $.ajax({
                    type: 'POST',
                    url: '../reviewReply', // 답글 작성 URL
                    headers: {
                        'Content-Type': 'application/json' // JSON 형식으로 보내기 위한 헤더
                    },
                    data: JSON.stringify(reviewObj), // 객체를 JSON으로 변환
                    success: function(result) {
                        console.log('답글 작성 응답:', result);
                        if (result == 1) {
                            alert('답글 작성 성공!');
                            getRepliesForComment(reviewCommentsId); // 답글 목록만 갱신
                        }
                    }
                });
            }
        });

        // 선택된 댓글에 대한 답글 목록을 가져오는 함수
        function getRepliesForComment(reviewCommentsId) {
            $.getJSON('../reviewReply/all/' + reviewCommentsId, function(replies) {
                var replyList = '';
                console.log('댓글에 대한 답글 목록:', replies);
                $.each(replies, function(index, reply) {
                    replyList += '<div class="replyItem">'
                        + '<p>' + reply.memberId + ': ' + reply.reviewReplyContent + '</p>'
                        + '<button class="btn_reply_update">수정</button>'
                        + '<button class="btn_reply_delete">삭제</button>'
                        + '<input type="hidden" id="reviewReplyId" value="' + reply.reviewReplyId + '">'
                        + '</div>';
                });
                // 댓글에 해당하는 답글 목록을 업데이트
                $('#reviewComments').find('#reviewCommentsId[value="' + reviewCommentsId + '"]')
                    .closest('.reviewComments_item')
                    .find('.replies')
                    .html(replyList);  // 답글 영역에 동적으로 내용 삽입
            });
        }

        // 답글 수정 기능
        $('#reviewComments').on('click', '.reviewComments_item .replyItem .btn_reply_update', function() {
            var reviewReplyId = $(this).closest('.replyItem').find('#reviewReplyId').val();
            var reviewReplyContent = prompt("답글을 수정하세요: ", $(this).closest('.replyItem').find('p').text());

            if (reviewReplyContent) {
                // 수정된 답글 전송
                $.ajax({
                    type: 'PUT',
                    url: '../reviewReply/' + reviewReplyId, // 답글 수정 URL
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: reviewReplyContent,
                    success: function(result) {
                        console.log('답글 수정 응답:', result);
                        if (result == 1) {
                            alert('답글 수정 성공!');
                            getRepliesForComment(reviewReplyId); // 답글 목록 갱신
                        }
                    }
                });
            }
        });

        // 답글 삭제 기능
        $('#reviewComments').on('click', '.reviewComments_item .replyItem .btn_reply_delete', function() {
            var reviewReplyId = $(this).closest('.replyItem').find('#reviewReplyId').val();
            var reviewCommentsId = $(this).closest('.reviewComments_item').find('#reviewCommentsId').val();  // 댓글 ID
        
            // 답글 삭제 요청
            $.ajax({
                type: 'DELETE',
                url: '../reviewReply/' + reviewReplyId + '/' + reviewCommentsId, // 답글 삭제 URL
                headers: {
                    'Content-Type': 'application/json'
                },
                success: function(result) {
                    console.log('답글 삭제 응답:', result);
                    if (result == 1) {
                        alert('답글 삭제 성공!');
                        getRepliesForComment(reviewCommentsId);  // 해당 댓글에 대한 답글 목록 갱신
                    }
                }
            });
        });
    });
    </script>

</body>
</html>
