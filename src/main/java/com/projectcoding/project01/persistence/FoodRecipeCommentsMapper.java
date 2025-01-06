package  com.projectcoding.project01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.projectcoding.project01.domain.FoodRecipeCommentsVO;

@Mapper
public interface FoodRecipeCommentsMapper {

    int insert(FoodRecipeCommentsVO foodRecipeCommentsVO); 
    
    List<FoodRecipeCommentsVO> selectListByBoardId(int foodRecipeBoardId);
    
    int update(FoodRecipeCommentsVO foodRecipeCommentsVO);
    
    int delete(int foodRecipeCommentsId); 
	
    int updateReplyCount(@Param("foodRecipeCommentsId") int foodRecipeCommentsId, @Param("amount") int amount); // 답글 수 변경

}

	




