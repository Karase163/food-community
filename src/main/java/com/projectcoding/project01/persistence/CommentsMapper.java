package  com.projectcoding.project01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.projectcoding.project01.domain.CommentsVO;

@Mapper
public interface CommentsMapper {
	
	int insert(CommentsVO commentsVO);
	
	List<CommentsVO> selectListByBoardId(int boardId);
	
	int update(CommentsVO commentsVO);
	
	int delete(int commentsId);
}

