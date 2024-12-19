package com.projectcoding.project01.persistence;

import org.apache.ibatis.annotations.Mapper;
import com.projectcoding.project01.domain.ReplyVO;
import java.util.List;

@Mapper
public interface ReplyMapper {
    
    int insert(ReplyVO replyVo);
    
    List<ReplyVO> selectListByCommentsId(int commentsId);
    
    int update(ReplyVO replyVO);
    
    int delete(int replyId);

    List<ReplyVO> selectRepliesByBoardId(int boardId);
    
    int updateReplyCount(int boardId, int count);
}
