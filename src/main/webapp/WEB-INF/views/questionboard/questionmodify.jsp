<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${questionBoardVO.questionBoardTitle}</title>
</head>
<body>
    <h2>게시글 수정 페이지</h2>
    <form action="questionmodify" method="POST">
        <div>
            <p>번호 : </p>
            <!-- questionBoardId를 수정할 수 없도록 readonly 처리 -->
            <input type="text" name="questionBoardId" value="${questionBoardVO.questionBoardId}" readonly>
        </div>
        <div>
            <p>제목 : </p>
            <!-- 제목 수정할 수 있도록 -->
            <input type="text" name="questionBoardTitle" placeholder="제목 입력" 
                   maxlength="30" value="${questionBoardVO.questionBoardTitle}" required>
        </div>
        <div>
            <p>작성자 : ${questionBoardVO.memberId}</p>
        </div>
        <div>
            <p>내용 : </p>
            <!-- 내용 수정할 수 있도록 -->
            <textarea rows="20" cols="120" name="questionBoardContent" placeholder="내용 입력" 
                      maxlength="500" required>${questionBoardVO.questionBoardContent}</textarea>
        </div>
        <div>
            <input type="submit" value="수정">
        </div>
    </form>
</body>
</html>
