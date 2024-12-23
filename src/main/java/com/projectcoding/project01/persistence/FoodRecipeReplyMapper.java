package com.projectcoding.project01.persistence;

import org.apache.ibatis.annotations.Mapper;
import com.projectcoding.project01.domain.FoodRecipeReplyVO;
import java.util.List;

@Mapper
public interface FoodRecipeReplyMapper {

    int insert(FoodRecipeReplyVO foodRecipeReplyVO); 
    
    List<FoodRecipeReplyVO> selectListByCommentsId(int foodRecipeCommentsId); 
    int update(FoodRecipeReplyVO foodRecipeReplyVO); 
    
    int delete(int foodRecipeReplyId); 

    List<FoodRecipeReplyVO> selectRepliesByBoardId(int foodRecipeBoardId);
    
}
