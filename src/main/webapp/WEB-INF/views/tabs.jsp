<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 탭</title>
    <style>
        .tabs { list-style: none; display: flex; }
        .tab { margin-right: 10px; cursor: pointer; padding: 10px; background-color: #f0f0f0; }
        .tab-content { display: none; padding: 20px; background-color: #f9f9f9; }
        .active-tab { background-color: #ccc; }
    </style>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script>
        // 탭 클릭 시 AJAX로 콘텐츠를 동적으로 로드
        function loadTabContent(tab) {
            // 탭 클릭 시 모든 탭의 콘텐츠를 숨김
            $(".tab-content").hide();

            // 클릭된 탭에 해당하는 콘텐츠만 보여줌
            $("#" + tab).show();

            // 클릭된 탭에 active 클래스 추가
            $(".tab").removeClass("active-tab");
            $("#" + tab + "-tab").addClass("active-tab");

            // AJAX 호출로 해당 탭의 게시판 내용 로드
            $.ajax({
                url: '/project01/' + tab + 'board/' + tab + 'list',  // URL을 각 게시판에 맞게 설정
                method: 'GET',
                success: function(data) {
                    $("#" + tab).html(data); // 해당 탭에 게시판 내용을 동적으로 로드
                }
            });
        }
    </script>
</head>
<body>

<h2>게시판 탭</h2>

<ul class="tabs">
    <!-- Food Recipe 탭 -->
    <li class="tab" onclick="location.href='/project01/foodRecipeboard/foodRecipelist'">음식/레시피</li>

    <!-- Review 탭 -->
    <li class="tab" onclick="location.href='/project01/reviewboard/reviewlist'">맛집 리뷰</li>

    <!-- Question 탭 -->
    <li class="tab" onclick="location.href='/project01/questionboard/questionlist'">질문</li>
</ul>


<!-- 탭 콘텐츠들 -->
<div class="tab-content" id="foodRecipe">
    <!-- FoodRecipe 게시판 콘텐츠가 여기에 로드됨 -->
</div>

<div class="tab-content" id="review">
    <!-- Review 게시판 콘텐츠가 여기에 로드됨 -->
</div>

<div class="tab-content" id="question">
    <!-- Question 게시판 콘텐츠가 여기에 로드됨 -->
</div>

</body>
</html>