<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <!-- jquery 라이브러리 import -->
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <meta charset="UTF-8">
    <title>${foodRecipeBoardVO.foodRecipeBoardTitle }</title>
</head>
<body>

    <!-- 게시글 보기 -->
    <h2>글 보기</h2>
    <div>
        <p>글 번호 : ${foodRecipeBoardVO.foodRecipeBoardId}</p>
    </div>
    <div>
        <p>제목 : </p>
        <p>${foodRecipeBoardVO.foodRecipeBoardTitle}</p> <!-- 제목 -->
    </div>
    <div>
        <p>작성자 : ${foodRecipeBoardVO.memberId}</p>
        
        <fmt:formatDate value="${foodRecipeBoardVO.foodRecipeBoardCreated}"
                        pattern="yyyy-MM-dd HH:mm:ss" var="foodRecipeBoardCreated"/>
        <p>작성일 : ${foodRecipeBoardCreated }</p>
    </div>
    <div>
        <textarea rows="20" cols="120" readonly>${foodRecipeBoardVO.foodRecipeBoardContent }</textarea>
    </div>

    

    <!-- 게시글 관련 버튼 -->
    <button onclick="location.href='foodRecipelist'">글 목록</button>
    <button onclick="location.href='foodRecipemodify?foodRecipeBoardId=${foodRecipeBoardVO.foodRecipeBoardId}'">글 수정</button>
    <button id="deletefoodRecipeBoard">글 삭제</button>
    <form id="deleteForm" action="delete" method="POST">
        <input type="hidden" name="foodRecipeBoardId" value="${foodRecipeBoardVO.foodRecipeBoardId}">
    </form>

    <script type="text/javascript">
        $(document).ready(function(){
            $('#deletefoodRecipeBoard').click(function(){
                if(confirm('삭제하시겠습니까?')){
                    $('#deleteForm').submit(); // form 데이터 전송
                }
            });
        });
    </script>

    <!-- 댓글 및 답글 관련 코드 -->
    <input type="hidden" id="foodRecipeBoardId" value="${foodRecipeBoardVO.foodRecipeBoardId}">

    <h3>댓글 추가</h3>
    <div style="text-align: center;">
        <input type="text" id="memberId" placeholder="작성자 ID"> 
        <input type="text" id="foodRecipeCommentsContent" placeholder="댓글 내용">
        <button id="btnAdd">댓글 작성</button>
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
                var foodRecipeBoardId = $('#foodRecipeBoardId').val(); 
                var memberId = $('#memberId').val(); 
                var foodRecipeCommentsContent = $('#foodRecipeCommentsContent').val();

                // 댓글 데이터를 객체로 생성
                var obj = {
                        'foodRecipeBoardId' : foodRecipeBoardId,
                        'memberId' : memberId,
                        'foodRecipeCommentsContent' : foodRecipeCommentsContent
                }
                console.log('댓글 작성 데이터:', obj);

                // 서버로 댓글을 전송하는 ajax 요청
                $.ajax({
                    type : 'POST', 
                    url : '../foodRecipeComments', 
                    headers : { 
                        'Content-Type' : 'application/json' 
                    }, 
                    data : JSON.stringify(obj), 
                    success : function(result) { 
                        console.log('서버 응답:', result);
                        if(result == 1) {
                            alert('댓글 입력 성공');
                            getAllFoodRecipeComments(); // 댓글 목록 갱신
                        }
                    }
                });
            }); 

            // 게시판 댓글 목록 가져오기
            function getAllFoodRecipeComments() {
                var foodRecipeBoardId = $('#foodRecipeBoardId').val(); 

                var url = '../foodRecipeComments/all/' + foodRecipeBoardId;
                $.getJSON(
                    	url,         
                    function(data) {
                        var list = ''; 

                        $(data).each(function(){
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
                                + '<button class="btn_reply">답글</button>'
                                + '<div class="replies"></div>' 
                                + '</pre>'
                                + '</div>';
                        });

                        $('#foodRecipeComments').html(list); 

                        $(data).each(function() {
                            getRepliesForComments(this.foodRecipeCommentsId); 
                        });
                    }
                ); 
            }

            // 댓글 수정 기능
            $('#foodRecipeComments').on('click', '.foodRecipeComments_item .btn_update', function(){
                var foodRecipeCommentsId = $(this).prevAll('#foodRecipeCommentsId').val(); 
                var foodRecipeCommentsContent = $(this).prevAll('#foodRecipeCommentsContent').val(); 

                $.ajax({
                    type : 'PUT', 
                    url : '../foodRecipeComments/' + foodRecipeCommentsId,
                    headers : {
                        'Content-Type' : 'application/json'
                    },
                    data : foodRecipeCommentsContent, 
                    success : function(result) {
                        if(result == 1) {
                            alert('댓글 수정 성공!');
                            getAllFoodRecipeComments();
                        }
                    }
                });
            }); 
            
            // 댓글 삭제 기능
            $('#foodRecipeComments').on('click', '.foodRecipeComments_item .btn_delete', function(){
                var foodRecipeCommentsId = $(this).prevAll('#foodRecipeCommentsId').val(); 

                $.ajax({
                    type : 'DELETE', 
                    url : '../foodRecipeComments/' + foodRecipeCommentsId, 
                    headers : {
                        'Content-Type' : 'application/json'
                    },
                    success : function(result) {
                        if(result == 1) {
                            alert('댓글 삭제 성공!');
                            getAllFoodRecipeComments();
                        }
                    }
                });
            }); 

            // 답글 작성 기능
            $('#foodRecipeComments').on('click', '.foodRecipeComments_item .btn_reply', function() {
                var foodRecipeCommentsId = $(this).closest('.foodRecipeComments_item').find('#foodRecipeCommentsId').val();
                var memberId = 'test'; 
                var foodRecipeReplyContent = prompt("답글을 입력하세요: ");

                if (foodRecipeReplyContent) {
                    var replyObj = {
                        'foodRecipeCommentsId': foodRecipeCommentsId,
                        'memberId': memberId,
                        'foodRecipeReplyContent': foodRecipeReplyContent
                    };

                    $.ajax({
                        type: 'POST',
                        url: '../foodRecipeReply', 
                        headers: {
                            'Content-Type': 'application/json' 
                        },
                        data: JSON.stringify(replyObj), 
                        success: function(result) {
                            if (result == 1) {
                                alert('답글 작성 성공!');
                                getRepliesForComments(foodRecipeCommentsId); 
                            }
                        }
                    });
                }
            });

            // 선택된 댓글에 대한 답글 목록을 가져오는 함수
            function getRepliesForComments(foodRecipeCommentsId) {
                $.getJSON('../foodRecipeReply/all/' + foodRecipeCommentsId, function(replies) {
                    var replyList = '';
                    $.each(replies, function(index, reply) {
                        replyList += '<div class="replyItem">'
                            + '<p>' + reply.memberId + ': ' + reply.foodRecipeReplyContent + '</p>'
                            + '<button class="btn_reply_update">수정</button>'
                            + '<button class="btn_reply_delete">삭제</button>'
                            + '<input type="hidden" id="foodRecipeReplyId" value="' + reply.foodRecipeReplyId + '">'
                            + '</div>';
                    });
                    $('#foodRecipeComments').find('#foodRecipeCommentsId[value="' + foodRecipeCommentsId + '"]')
                        .closest('.foodRecipeComments_item')
                        .find('.replies')
                        .html(replyList); 
                });
            }

            // 답글 수정 기능
            $('#foodRecipeComments').on('click', '.foodRecipeComments_item .replyItem .btn_reply_update', function() {
                var foodRecipeReplyId = $(this).closest('.replyItem').find('#foodRecipeReplyId').val();
                var foodRecipeReplyContent = prompt("답글을 수정하세요: ", $(this).closest('.replyItem').find('p').text());

                if (foodRecipeReplyContent) {
                    $.ajax({
                        type: 'PUT',
                        url: '../foodRecipeReply/' + foodRecipeReplyId, 
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        data: foodRecipeReplyContent, 
                        success: function(result) {
                            if (result == 1) {
                                alert('답글 수정 성공!');
                                getRepliesForComments(foodRecipeReplyId); 
                            }
                        }
                    });
                }
            });

            // 답글 삭제 기능
            $('#foodRecipeComments').on('click', '.foodRecipeComments_item .replyItem .btn_reply_delete', function() {
                var foodRecipeReplyId = $(this).closest('.replyItem').find('#foodRecipeReplyId').val();  // 답글 ID
                var foodRecipeCommentsId = $(this).closest('.foodRecipeComments_item').find('#foodRecipeCommentsId').val();  // 댓글 ID 추가

                $.ajax({
                    type: 'DELETE',
                    url: '../foodRecipeReply/' + foodRecipeReplyId + '/' + foodRecipeCommentsId,  // foodRecipeCommentsId도 URL에 추가
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    success: function(result) {
                        if (result == 1) {
                            alert('답글 삭제 성공!');
                            getRepliesForComments(foodRecipeCommentsId);  // 댓글 ID를 사용해 답글 목록 갱신
                        }    
                    }
                });
            });
        });
    </script>
</body>
</html>