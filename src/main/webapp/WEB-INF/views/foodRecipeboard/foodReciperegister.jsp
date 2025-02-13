<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성 페이지</title>
</head>
<body>
   <h2>게시글 작성 페이지</h2>
   <form action="foodReciperegister" method="POST">
   <!-- input 태그의 name은 vo의 멤버 변수 이름과 동일하게 작성 -->
      <div>
         <p>제목 : </p>
         <input type="text" name="foodRecipeBoardTitle" 
         placeholder="제목 입력" maxlength="30" required>
      </div>
      <div>
         <p>작성자 : </p>
         <input type="text" name="memberId" maxlength="30" required>
      </div>
      <div>
         <p>내용 : </p>
         <textarea rows="20" cols="120" name="foodRecipeBoardContent" 
         placeholder="내용 입력" maxlength="500" required></textarea>
      </div>
      <div>
         <input type="submit" value="등록">
      </div>
   </form>
</body>
</html>