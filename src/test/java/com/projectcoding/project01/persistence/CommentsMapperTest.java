package com.projectcoding.project01.persistence;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.projectcoding.project01.config.RootConfig;
import com.projectcoding.project01.domain.CommentsVO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit test 연결 // 에러가 나면 Build Path를 추가해줘야 한다.
@ContextConfiguration(classes = {RootConfig.class}) // 설정 파일 연결 *이거 class 배열이니까 여러개 할 수 있는거 아님??
@Log4j
public class CommentsMapperTest {
	
	@Autowired
	private CommentsMapper commentsMapper;
	
	@Test
	public void test() {
		insertReply();
		
	}

	private void insertReply() {
		CommentsVO commentsVO = new CommentsVO();
		commentsVO.setBoardId(5);
		commentsVO.setCommentsContent("댓글");
		commentsVO.setMemberId("테스트");
		int result = commentsMapper.insert(commentsVO);
		log.info(result + "행 삽입");
	}
	
	
	
}
