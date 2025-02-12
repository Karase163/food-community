<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <!-- jquery 라이브러리 import -->
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <meta charset="UTF-8">
    <title>${questionBoardVO.questionBoardTitle }</title>
</head>
<body>

    <!-- 게시글 보기 -->
    <h2>글 보기</h2>
    <div>
        <p>글 번호 : ${questionBoardVO.questionBoardId}</p>
    </div>
    <div>
        <p>제목 : </p>
        <p>${questionBoardVO.questionBoardTitle}</p> <!-- 제목 -->
    </div>
    <div>
        <p>작성자 : ${questionBoardVO.memberId}</p>
        
        <fmt:formatDate value="${questionBoardVO.questionBoardCreated}"
                        pattern="yyyy-MM-dd HH:mm:ss" var="questionBoardCreated"/>
        <p>작성일 : ${questionBoardCreated }</p>
    </div>
    <div>
        <textarea rows="20" cols="120" readonly>${questionBoardVO.questionBoardContent }</textarea>
    </div>

    

    <!-- 게시글 관련 버튼 -->
    <button onclick="location.href='questionlist'">글 목록</button>
    <button onclick="location.href='questionmodify?questionBoardId=${questionBoardVO.questionBoardId}'">글 수정</button>
    <button id="deletequestionBoard">글 삭제</button>
    <form id="deleteForm" action="project1/detial3/questionboard/delete" method="POST">
        <input type="hidden" name="questionBoardId" value="${questionBoardVO.questionBoardId}">
    </form>

    <script type="text/javascript">
        $(document).ready(function(){
            $('#deletequestionBoard').click(function(){
                if(confirm('삭제하시겠습니까?')){
                    $('#deleteForm').submit(); // form 데이터 전송
                }
            });
        });
    </script>

    <!-- 댓글 및 답글 관련 코드 -->
    <input type="hidden" id="questionBoardId" value="${questionBoardVO.questionBoardId}">
    

    <h3>댓글 추가</h3>
    <div style="text-align: center;">
        <input type="text" id="memberId" placeholder="작성자 ID"> 
        <input type="text" id="questionCommentsContent" placeholder="댓글 내용">
        <button id="btnAdd">댓글 작성</button>
    </div>

    <hr>
    <div style="text-align: center;">
        <div id="questionComments"></div>
    </div>

    <script type="text/javascript">
        $(document).ready(function(){
            getAllQuestionComments(); // 댓글 목록 가져오기

            // 댓글 작성 기능
            $('#btnAdd').click(function(){
                var questionBoardId = $('#questionBoardId').val(); 
                var memberId = $('#memberId').val(); 
                var questionCommentsContent = $('#questionCommentsContent').val();

                // 댓글 데이터를 객체로 생성
                var obj = {
                        'questionBoardId' : questionBoardId,
                        'memberId' : memberId,
                        'questionCommentsContent' : questionCommentsContent
                }
                console.log('댓글 작성 데이터:', obj);

                // 서버로 댓글을 전송하는 ajax 요청
                $.ajax({
                    type : 'POST', 
                    url : '../questionComments', 
                    headers : { 
                        'Content-Type' : 'application/json' 
                    }, 
                    data : JSON.stringify(obj), 
                    success : function(result) { 
                        console.log('서버 응답:', result);
                        if(result == 1) {
                            alert('댓글 입력 성공');
                            getAllQuestionComments(); // 댓글 목록 갱신
                        }
                    }
                });
            }); 

            // 게시판 댓글 목록 가져오기
            function getAllQuestionComments() {
                var questionBoardId = $('#questionBoardId').val(); 

                var url = '../questionComments/all/' + questionBoardId;
                $.getJSON(
                    	url,         
                    function(data) {
                        var list = ''; 

                        $(data).each(function(){
                            var questionCommentsCreated = new Date(this.questionCommentsCreated);

                            list += '<div class="questionComments_item">'
                                + '<pre>'
                                + '<input type="hidden" id="questionCommentsId" value="'+ this.questionCommentsId +'">'
                                + this.memberId
                                + '&nbsp;&nbsp;' 
                                + '<input type="text" id="questionCommentsContent" value="'+ this.questionCommentsContent +'">'
                                + '&nbsp;&nbsp;'
                                + questionCommentsCreated
                                + '&nbsp;&nbsp;'
                                + '<button class="btn_update">수정</button>'
                                + '<button class="btn_delete">삭제</button>'
                                + '&nbsp;&nbsp;'
                                + '<button class="btn_reply">답글</button>'
                                + '<div class="replies"></div>' 
                                + '</pre>'
                                + '</div>';
                        });

                        $('#questionComments').html(list); 

                        $(data).each(function() {
                            getRepliesForComments(this.questionCommentsId); 
                        });
                    }
                ); 
            }

            // 댓글 수정 기능
            $('#questionComments').on('click', '.questionComments_item .btn_update', function(){
                var questionCommentsId = $(this).prevAll('#questionCommentsId').val(); 
                var questionCommentsContent = $(this).prevAll('#questionCommentsContent').val(); 

                $.ajax({
                    type : 'PUT', 
                    url : '../questionComments/' + questionCommentsId,
                    headers : {
                        'Content-Type' : 'application/json'
                    },
                    data : questionCommentsContent, 
                    success : function(result) {
                        if(result == 1) {
                            alert('댓글 수정 성공!');
                            getAllQuestionComments();
                        }
                    }
                });
            }); 
            
            // 댓글 삭제 기능
            $('#questionComments').on('click', '.questionComments_item .btn_delete', function(){
                var questionCommentsId = $(this).prevAll('#questionCommentsId').val(); 
                var questionBoardId = $('#questionBoardId').val(); 

                $.ajax({
                    type : 'DELETE', 
                    url : '../questionComments/' + questionCommentsId + '/' + questionBoardId,
                    headers : {
                        'Content-Type' : 'application/json'
                    },
                    success : function(result) {
                        if(result == 1) {
                            alert('댓글 삭제 성공!');
                            getAllQuestionComments();
                        }
                    }
                });
            }); 

            // 답글 작성 기능
            $('#questionComments').on('click', '.questionComments_item .btn_reply', function() {
                var questionCommentsId = $(this).closest('.questionComments_item').find('#questionCommentsId').val();
                var memberId = 'sakata'; 
                var questionReplyContent = prompt("답글을 입력하세요: ");

                if (questionReplyContent) {
                    var replyObj = {
                        'questionCommentsId': questionCommentsId,
                        'memberId': memberId,
                        'questionReplyContent': questionReplyContent
                    };

                    $.ajax({
                        type: 'POST',
                        url: '../questionReply', 
                        headers: {
                            'Content-Type': 'application/json' 
                        },
                        data: JSON.stringify(replyObj), 
                        success: function(result) {
                            if (result == 1) {
                                alert('답글 작성 성공!');
                                getRepliesForComments(questionCommentsId); 
                            }
                        }
                    });
                }
            });

            // 선택된 댓글에 대한 답글 목록을 가져오는 함수
            function getRepliesForComments(questionCommentsId) {
                $.getJSON('../questionReply/all/' + questionCommentsId, function(replies) {
                    var replyList = '';
                    $.each(replies, function(index, reply) {
                        replyList += '<div class="replyItem">'
                            + '<p>' + reply.memberId + ': ' + reply.questionReplyContent + '</p>'
                            + '<button class="btn_reply_update">수정</button>'
                            + '<button class="btn_reply_delete">삭제</button>'
                            + '<input type="hidden" id="questionReplyId" value="' + reply.questionReplyId + '">'
                            + '</div>';
                    });
                    $('#questionComments').find('#questionCommentsId[value="' + questionCommentsId + '"]')
                        .closest('.questionComments_item')
                        .find('.replies')
                        .html(replyList); 
                });
            }

            // 답글 수정 기능
            $('#questionComments').on('click', '.questionComments_item .replyItem .btn_reply_update', function() {
                var questionReplyId = $(this).closest('.replyItem').find('#questionReplyId').val();
                var questionReplyContent = prompt("답글을 수정하세요: ", $(this).closest('.replyItem').find('p').text());

                if (questionReplyContent) {
                    $.ajax({
                        type: 'PUT',
                        url: '../questionReply/' + questionReplyId, 
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        data: questionReplyContent, 
                        success: function(result) {
                            if (result == 1) {
                                alert('답글 수정 성공!');
                                getRepliesForComments(questionReplyId); 
                            }
                        }
                    });
                }
            });

            // 답글 삭제 기능
            $('#questionComments').on('click', '.questionComments_item .replyItem .btn_reply_delete', function() {
                var questionReplyId = $(this).closest('.replyItem').find('#questionReplyId').val();  // 답글 ID
                var questionCommentsId = $(this).closest('.questionComments_item').find('#questionCommentsId').val();  // 댓글 ID 추가

                $.ajax({
                    type: 'DELETE',
                    url: '../questionReply/' + questionReplyId + '/' + questionCommentsId,  
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    success: function(result) {
                        if (result == 1) {
                            alert('답글 삭제 성공!');
                            getRepliesForComments(questionCommentsId);  // 댓글 ID를 사용해 답글 목록 갱신
                        }    
                    }
                });
            });
        });
    </script>
</body>
</html>