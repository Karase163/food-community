package  com.projectcoding.project01.reviewpersistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.projectcoding.project01.reviewdomain.ReviewCommentsVO;


@Mapper
public interface ReviewCommentsMapper {

    int insert(ReviewCommentsVO reviewCommentsVO); 
    
    List<ReviewCommentsVO> selectListByBoardId(int reviewBoardId);
    
    int update(ReviewCommentsVO reviewCommentsVO);
    
    int delete(int reviewCommentsId); 
	
    int updateReplyCount(@Param("reviewCommentsId") int reviewCommentsId, @Param("amount") int amount); // 답글 수 변경

}