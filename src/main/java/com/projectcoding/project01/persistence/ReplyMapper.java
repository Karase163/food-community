package com.projectcoding.project01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.projectcoding.project01.domain.ReplyVO;

@Mapper
public interface ReplyMapper {
	
	int insert(ReplyVO replyVo);
	
	List<ReplyVO> selectListByCommentsId(int commentsId);
	
	int update(ReplyVO replyVO);
	
	int delete(int replyId);

}
	