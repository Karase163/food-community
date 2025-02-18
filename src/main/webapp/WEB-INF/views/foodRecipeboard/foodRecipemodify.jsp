<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<!-- css 파일 불러오기 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/image.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/attach.css">
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>${foodRecipeBoardVO.foodRecipeBoardTitle}</title>
</head>
<body>
	<h2>게시글 수정 페이지</h2>
	<form id="modifyForm" action="foodRecipemodify" method="POST">
		<!-- 숨겨진 파라미터들 (페이지 정보 등) -->
		<input type="hidden" name="pageNum" value="${pagination.pageNum}">
		<input type="hidden" name="pageSize" value="${pagination.pageSize}">
		<input type="hidden" name="type" value="${pagination.type}"> <input
			type="hidden" name="keyword" value="${pagination.keyword}">

		<div>
			<p>번호 :</p>
			<input type="text" name="foodRecipeBoardId"
				value="${foodRecipeBoardVO.foodRecipeBoardId}" readonly>
		</div>
		<div>
			<p>제목 :</p>
			<input type="text" name="foodRecipeBoardTitle" placeholder="제목 입력"
				maxlength="30" value="${foodRecipeBoardVO.foodRecipeBoardTitle}"
				required>
		</div>
		<div>
			<p>작성자 : ${foodRecipeBoardVO.memberId}</p>
		</div>
		<div>
			<p>내용 :</p>
			<textarea rows="20" cols="120" name="foodRecipeBoardContent"
				placeholder="내용 입력" maxlength="500" required>${foodRecipeBoardVO.foodRecipeBoardContent}</textarea>
		</div>

		<!-- 기존 첨부 파일 리스트 데이터 구성 -->
		<c:forEach var="FoodRecipeAttachDTO"
			items="${foodRecipeBoardVO.foodRecipeAttachList}" varStatus="status">
			<input type="hidden" class="input-attach-list"
				name="foodRecipeAttachList[${status.index}].foodRecipeAttachPath"
				value="${FoodRecipeAttachDTO.foodRecipeAttachPath}">
			<input type="hidden" class="input-attach-list"
				name="foodRecipeAttachList[${status.index}].foodRecipeAttachRealName"
				value="${FoodRecipeAttachDTO.foodRecipeAttachRealName}">
			<input type="hidden" class="input-attach-list"
				name="foodRecipeAttachList[${status.index}].foodRecipeAttachChgName"
				value="${FoodRecipeAttachDTO.foodRecipeAttachChgName}">
			<input type="hidden" class="input-attach-list"
				name="foodRecipeAttachList[${status.index}].foodRecipeAttachExtension"
				value="${FoodRecipeAttachDTO.foodRecipeAttachExtension}">
		</c:forEach>

		<!-- 이미지 파일 리스트 -->
		<div class="image-upload">
			<div class="image-view">
				<h2>이미지 파일 리스트</h2>
				<div class="image-list">
					<c:forEach var="FoodRecipeAttachDTO"
						items="${foodRecipeBoardVO.foodRecipeAttachList}">
						<c:if
							test="${FoodRecipeAttachDTO.foodRecipeAttachExtension eq 'jpg' or FoodRecipeAttachDTO.foodRecipeAttachExtension eq 'jpeg' or FoodRecipeAttachDTO.foodRecipeAttachExtension eq 'png' or FoodRecipeAttachDTO.foodRecipeAttachExtension eq 'gif'}">
							<div class="image_item">
								<a
									href="../image/get?foodRecipeAttachId=${FoodRecipeAttachDTO.foodRecipeAttachId}"
									target="_blank"> <img width="100px" height="100px"
									src="../image/get?foodRecipeAttachId=${FoodRecipeAttachDTO.foodRecipeAttachId}&attachExtension=${FoodRecipeAttachDTO.foodRecipeAttachExtension}" />
								</a>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="image-modify" style="display: none;">
				<h2>이미지 파일 업로드</h2>
				<p>* 이미지 파일은 최대 3개까지 가능합니다.</p>
				<p>* 최대 용량은 10MB입니다.</p>
				<div class="image-drop"></div>
				<h2>선택한 이미지 파일 :</h2>
				<div class="image-list"></div>
			</div>
		</div>

		<!-- 첨부 파일 리스트 -->
		<div class="attach-upload">
			<div class="attach-view">
				<h2>첨부 파일 리스트</h2>
				<div class="attach-list">
					<c:forEach var="FoodRecipeAttachDTO"
						items="${foodRecipeBoardVO.foodRecipeAttachList}">
						<c:if
							test="${not (FoodRecipeAttachDTO.foodRecipeAttachExtension eq 'jpg' or FoodRecipeAttachDTO.foodRecipeAttachExtension eq 'jpeg' or FoodRecipeAttachDTO.foodRecipeAttachExtension eq 'png' or FoodRecipeAttachDTO.foodRecipeAttachExtension eq 'gif')}">
							<div class="attach_item">
								<p>
									<a
										href="../attach/download?foodReicpeAttachId=${FoodRecipeAttachDTO.foodReicpeAttachId}">${FoodRecipeAttachDTO.foodReicpeAttachRealName}.${FoodRecipeAttachDTO.foodReicpeAttachExtension}</a>
								</p>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="attach-modify" style="display: none;">
				<h2>첨부 파일 업로드</h2>
				<p>* 첨부 파일은 최대 3개까지 가능합니다.</p>
				<p>* 최대 용량은 10MB입니다.</p>
				<input type="file" id="attachInput" name="files" multiple="multiple"><br>
				<h2>선택한 첨부 파일 정보 :</h2>
				<div class="attach-list"></div>
			</div>
		</div>

		<button type="submit">수정</button>
	</form>

	<script src="${pageContext.request.contextPath}/resources/js/image.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/attach.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			// 이미지 및 첨부 파일 관련 처리 (변경 버튼 클릭 시)
			$('#change-upload').click(function() {
				if (!confirm('기존에 업로드된 파일들은 삭제됩니다. 계속 하시겠습니까?')) {
					return;
				}
				$('.image-modify').show();
				$('.image-view').hide();
				$('.attach-modify').show();
				$('.attach-view').hide();
				$('.input-attach-list').remove(); // 기존 첨부 파일 리스트 제거
			});
		});
	</script>
</body>
</html>
