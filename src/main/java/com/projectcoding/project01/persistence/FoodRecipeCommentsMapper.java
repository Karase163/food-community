package  com.projectcoding.project01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.projectcoding.project01.domain.FoodRecipeCommentsVO;

@Mapper
public interface FoodRecipeCommentsMapper {
	
	int insert(FoodRecipeCommentsVO foodrecipecommentsVO);
	
	List<FoodRecipeCommentsVO> selectListByBoardId(int foodrecipeboardId);
	
	int update(FoodRecipeCommentsVO foodrecipecommentsVO);
	
	int delete(int foodrecipecommentsId);

	

}

