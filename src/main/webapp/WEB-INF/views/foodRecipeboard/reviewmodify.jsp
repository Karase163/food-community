<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${reviewBoardVO.reviewBoardTitle}</title>
</head>
<body>
    <h2>글 수정 페이지</h2>
    <form action="reviewmodify" method="POST">
        <div>
            <p>번호 : </p>
            <!-- reviewBoardId를 수정할 수 없도록 readonly 처리 -->
            <input type="text" name="reviewBoardId" value="${reviewBoardVO.reviewBoardId}" readonly>
        </div>
        <div>
            <p>제목 : </p>
            <!-- 제목 수정할 수 있도록 -->
            <input type="text" name="reviewBoardTitle" placeholder="제목 입력" 
                   maxlength="30" value="${reviewBoardVO.reviewBoardTitle}" required>
        </div>
        <div>
            <p>작성자 : ${reviewBoardVO.memberId}</p>
        </div>
        <div>
            <p>내용 : </p>
            <!-- 내용 수정할 수 있도록 -->
            <textarea rows="20" cols="120" name="reviewBoardContent" placeholder="내용 입력" 
                      maxlength="500" required>${reviewBoardVO.reviewBoardContent}</textarea>
        </div>
        <div>
            <input type="submit" value="수정">
        </div>
    </form>
</body>
</html>
