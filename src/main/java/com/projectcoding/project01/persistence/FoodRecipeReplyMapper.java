package com.projectcoding.project01.persistence;

import org.apache.ibatis.annotations.Mapper;
import com.projectcoding.project01.domain.FoodRecipeReplyVO;
import java.util.List;

@Mapper
public interface FoodRecipeReplyMapper {
    
    int insert(FoodRecipeReplyVO foodrecipereplyVo);
    
    List<FoodRecipeReplyVO> selectListByCommentsId(int foodrecipecommentsId);
    
    int update(FoodRecipeReplyVO foodrecipereplyVO);
    
    int delete(int foodrecipereplyId);

    List<FoodRecipeReplyVO> selectRepliesByBoardId(int foodrecipeboardId);
    
}
