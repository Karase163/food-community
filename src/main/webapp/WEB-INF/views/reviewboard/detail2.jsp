<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <!-- jquery 라이브러리 import -->
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <meta charset="UTF-8">
    <title>${reviewBoardVO.reviewBoardTitle }</title>
</head>
<body>

    <!-- 게시글 보기 -->
    <h2>글 보기</h2>
    <div>
        <p>글 번호 : ${reviewBoardVO.reviewBoardId}</p>
    </div>
    <div>
        <p>제목 : </p>
        <p>${reviewBoardVO.reviewBoardTitle}</p> <!-- 제목 -->
    </div>
    <div>
        <p>작성자 : ${reviewBoardVO.memberId}</p>
        
        <fmt:formatDate value="${reviewBoardVO.reviewBoardCreated}"
                        pattern="yyyy-MM-dd HH:mm:ss" var="reviewBoardCreated"/>
        <p>작성일 : ${reviewBoardCreated }</p>
    </div>
    <div>
        <textarea rows="20" cols="120" readonly>${reviewBoardVO.reviewBoardContent }</textarea>
    </div>

    

    <!-- 게시글 관련 버튼 -->
    <button onclick="location.href='reviewlist'">글 목록</button>
    <button onclick="location.href='reviewmodify?reviewBoardId=${reviewBoardVO.reviewBoardId}'">글 수정</button>
    <button id="deletereviewBoard">글 삭제</button>
    <form id="deleteForm" action="delete" method="POST">
        <input type="hidden" name="reviewBoardId" value="${reviewBoardVO.reviewBoardId}">
    </form>

    <script type="text/javascript">
        $(document).ready(function(){
            $('#deletereviewBoard').click(function(){
                if(confirm('삭제하시겠습니까?')){
                    $('#deleteForm').submit(); // form 데이터 전송
                }
            });
        });
    </script>

    <!-- 댓글 및 답글 관련 코드 -->
    <input type="hidden" id="reviewBoardId" value="${reviewBoardVO.reviewBoardId}">

    <h3>댓글 추가</h3>
    <div style="text-align: center;">
        <input type="text" id="memberId" placeholder="작성자 ID"> 
        <input type="text" id="reviewCommentsContent" placeholder="댓글 내용">
        <button id="btnAdd">댓글 작성</button>
    </div>

    <hr>
    <div style="text-align: center;">
        <div id="reviewComments"></div>
    </div>

    <script type="text/javascript">
        $(document).ready(function(){
            getAllreviewComments(); // 댓글 목록 가져오기

            // 댓글 작성 기능
            $('#btnAdd').click(function(){
                var reviewBoardId = $('#reviewBoardId').val(); 
                var memberId = $('#memberId').val(); 
                var reviewCommentsContent = $('#reviewCommentsContent').val();

                // 댓글 데이터를 객체로 생성
                var obj = {
                        'reviewBoardId' : reviewBoardId,
                        'memberId' : memberId,
                        'reviewCommentsContent' : reviewCommentsContent
                }
                console.log('댓글 작성 데이터:', obj);

                // 서버로 댓글을 전송하는 ajax 요청
                $.ajax({
                    type : 'POST', 
                    url : '../reviewComments', 
                    headers : { 
                        'Content-Type' : 'application/json' 
                    }, 
                    data : JSON.stringify(obj), 
                    success : function(result) { 
                        console.log('서버 응답:', result);
                        if(result == 1) {
                            alert('댓글 입력 성공');
                            getAllReviewComments(); // 댓글 목록 갱신
                        }
                    }
                });
            }); 

            // 게시판 댓글 목록 가져오기
            function getAllReviewComments() {
                var reviewBoardId = $('#reviewBoardId').val(); 

                var url = '../reviewComments/all/' + reviewBoardId;
                $.getJSON(
                    	url,         
                    function(data) {
                        var list = ''; 

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
                                + '<div class="replies"></div>' 
                                + '</pre>'
                                + '</div>';
                        });

                        $('#reviewComments').html(list); 

                        $(data).each(function() {
                            getRepliesForComments(this.reviewCommentsId); 
                        });
                    }
                ); 
            }

            // 댓글 수정 기능
            $('#reviewComments').on('click', '.reviewComments_item .btn_update', function(){
                var reviewCommentsId = $(this).prevAll('#reviewCommentsId').val(); 
                var reviewCommentsContent = $(this).prevAll('#reviewCommentsContent').val(); 

                $.ajax({
                    type : 'PUT', 
                    url : '../reviewComments/' + reviewCommentsId,
                    headers : {
                        'Content-Type' : 'application/json'
                    },
                    data : reviewCommentsContent, 
                    success : function(result) {
                        if(result == 1) {
                            alert('댓글 수정 성공!');
                            getAllReviewComments();
                        }
                    }
                });
            }); 
            
            // 댓글 삭제 기능
            $('#reviewComments').on('click', '.reviewComments_item .btn_delete', function(){
                var reviewCommentsId = $(this).prevAll('#reviewCommentsId').val(); 
                var reviewBoardId = $('#reviewBoardId').val(); 

                $.ajax({
                    type : 'DELETE', 
                    url : '../reviewComments/' + reviewCommentsId + '/' + reviewBoardId,
                    headers : {
                        'Content-Type' : 'application/json'
                    },
                    success : function(result) {
                        if(result == 1) {
                            alert('댓글 삭제 성공!');
                            getAllReviewComments();
                        }
                    }
                });
            }); 

            // 답글 작성 기능
            $('#reviewComments').on('click', '.reviewComments_item .btn_reply', function() {
                var reviewCommentsId = $(this).closest('.reviewComments_item').find('#reviewCommentsId').val();
                var memberId = 'mafumafu'; 
                var reviewReplyContent = prompt("답글을 입력하세요: ");

                if (reviewReplyContent) {
                    var replyObj = {
                        'reviewCommentsId': reviewCommentsId,
                        'memberId': memberId,
                        'reviewReplyContent': reviewReplyContent
                    };

                    $.ajax({
                        type: 'POST',
                        url: '../reviewReply', 
                        headers: {
                            'Content-Type': 'application/json' 
                        },
                        data: JSON.stringify(replyObj), 
                        success: function(result) {
                            if (result == 1) {
                                alert('답글 작성 성공!');
                                getRepliesForComments(reviewCommentsId); 
                            }
                        }
                    });
                }
            });

            // 선택된 댓글에 대한 답글 목록을 가져오는 함수
            function getRepliesForComments(reviewCommentsId) {
                $.getJSON('../reviewReply/all/' + reviewCommentsId, function(replies) {
                    var replyList = '';
                    $.each(replies, function(index, reply) {
                        replyList += '<div class="replyItem">'
                            + '<p>' + reply.memberId + ': ' + reply.reviewReplyContent + '</p>'
                            + '<button class="btn_reply_update">수정</button>'
                            + '<button class="btn_reply_delete">삭제</button>'
                            + '<input type="hidden" id="reviewReplyId" value="' + reply.reviewReplyId + '">'
                            + '</div>';
                    });
                    $('#reviewComments').find('#reviewCommentsId[value="' + reviewCommentsId + '"]')
                        .closest('.reviewComments_item')
                        .find('.replies')
                        .html(replyList); 
                });
            }

            // 답글 수정 기능
            $('#reviewComments').on('click', '.reviewComments_item .replyItem .btn_reply_update', function() {
                var reviewReplyId = $(this).closest('.replyItem').find('#reviewReplyId').val();
                var reviewReplyContent = prompt("답글을 수정하세요: ", $(this).closest('.replyItem').find('p').text());

                if (reviewReplyContent) {
                    $.ajax({
                        type: 'PUT',
                        url: '../reviewReply/' + reviewReplyId, 
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        data: reviewReplyContent, 
                        success: function(result) {
                            if (result == 1) {
                                alert('답글 수정 성공!');
                                getRepliesForComments(reviewReplyId); 
                            }
                        }
                    });
                }
            });

            // 답글 삭제 기능
            $('#reviewComments').on('click', '.reviewComments_item .replyItem .btn_reply_delete', function() {
                var reviewReplyId = $(this).closest('.replyItem').find('#reviewReplyId').val();  // 답글 ID
                var reviewCommentsId = $(this).closest('.reviewComments_item').find('#reviewCommentsId').val();  // 댓글 ID 추가

                $.ajax({
                    type: 'DELETE',
                    url: '../reviewReply/' + reviewReplyId + '/' + reviewCommentsId,  
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    success: function(result) {
                        if (result == 1) {
                            alert('답글 삭제 성공!');
                            getRepliesForComments(reviewCommentsId);  // 댓글 ID를 사용해 답글 목록 갱신
                        }    
                    }
                });
            });
        });
    </script>
</body>
</html>