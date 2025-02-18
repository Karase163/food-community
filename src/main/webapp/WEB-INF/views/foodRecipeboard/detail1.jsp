<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <meta charset="UTF-8">
    <title>${foodRecipeBoardVO.foodRecipeBoardTitle }</title>
</head>
<body>

    <!-- 게시글 보기 -->
    <h2>게시글 보기</h2>
    <div>
        <p>글 번호 : ${foodRecipeBoardVO.foodRecipeBoardId}</p>
    </div>
    <div>
        <p>제목 : </p>
        <p>${foodRecipeBoardVO.foodRecipeBoardTitle}</p>
    </div>
    <div>
        <p>작성자 : ${foodRecipeBoardVO.memberId}</p>
        
        <fmt:formatDate value="${foodRecipeBoardVO.foodRecipeBoardCreated}" pattern="yyyy-MM-dd HH:mm:ss" var="foodRecipeBoardCreated"/>
        <p>작성일 : ${foodRecipeBoardCreated }</p>
    </div>
    <div>
        <textarea rows="20" cols="120" readonly>${foodRecipeBoardVO.foodRecipeBoardContent }</textarea>
    </div>

    <!-- 게시글 관련 버튼 -->
    <button onclick="location.href='foodRecipelist'">게시판</button>
    <button onclick="location.href='foodRecipemodify?foodRecipeBoardId=${foodRecipeBoardVO.foodRecipeBoardId}'">게시글 수정</button>
    <button id="deletefoodRecipeBoard">게시글 삭제</button>
    <form id="deleteForm" action="project1/detial1/foodRecipeboard/delete" method="POST">
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
        });
    </script>

    <!-- 이미지 및 첨부 파일 영역 -->
    <div class="file-upload">
        <div class="file-view">
            <h2>파일 리스트</h2>
            <div class="file-list">
                <c:forEach var="foodRecipeAttachDTO" items="${foodRecipeboardVO.foodRecipeAttachList}">
                    <!-- 이미지 파일 처리 -->
                    <c:if test="${foodRecipeAttachDTO.foodRecipeAttachExtension eq 'jpg' or 
                                 foodRecipeAttachDTO.foodRecipeAttachExtension eq 'jpeg' or 
                                 foodRecipeAttachDTO.foodRecipeAttachExtension eq 'png' or 
                                 foodRecipeAttachDTO.foodRecipeAttachExtension eq 'gif'}">
                        <div class="image_item">
                            <a href="../image/get?foodRecipeAttachId=${foodRecipeAttachDTO.foodRecipeAttachId }" target="_blank">
                                <img width="100px" height="100px" 
                                     src="../image/get?foodRecipeAttachId=${foodRecipeAttachDTO.foodRecipeAttachId }&foodRecipeAttachExtension=${foodRecipeAttachDTO.foodRecipeAttachExtension}"/>
                            </a>
                        </div>
                    </c:if>

                    <!-- 일반 첨부 파일 처리 -->
                    <c:if test="${not (foodRecipeAttachDTO.foodRecipeAttachExtension eq 'jpg' or 
                                       foodRecipeAttachDTO.foodRecipeAttachExtension eq 'jpeg' or 
                                       foodRecipeAttachDTO.foodRecipeAttachExtension eq 'png' or 
                                       foodRecipeAttachDTO.foodRecipeAttachExtension eq 'gif')}">
                        <div class="foodRecipeAttach_item">
                            <p><a href="../foodRecipeAttach/download?foodRecipeAttachId=${foodRecipeAttachDTO.foodRecipeAttachId }">${foodRecipeAttachDTO.foodRecipeAttachRealName }.${foodRecipeAttachDTO.foodRecipeAttachExtension }</a></p>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>

</body>
</html>
