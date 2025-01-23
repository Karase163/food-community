package  com.projectcoding.project01.questionpersistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.projectcoding.project01.questiondomain.QuestionCommentsVO;


@Mapper
public interface QuestionCommentsMapper {

    int insert(QuestionCommentsVO questionCommentsVO); 
    
    List<QuestionCommentsVO> selectListByBoardId(int questionBoardId);
    
    int update(QuestionCommentsVO questionCommentsVO);
    
    int delete(int questionCommentsId); 
	
    int updateReplyCount(@Param("questionCommentsId") int questionCommentsId, @Param("amount") int amount); // 답글 수 변경

}