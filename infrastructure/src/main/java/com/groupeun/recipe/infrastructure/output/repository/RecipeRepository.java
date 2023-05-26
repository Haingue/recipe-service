package com.groupeun.recipe.infrastructure.output.repository;

import com.groupeun.recipe.infrastructure.output.entity.RecipeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<RecipeEntity, String> {

    List<RecipeEntity> findAllByNameLikeOrderByName (String nameLike);
    List<RecipeEntity> findAllByNameLikeOrPreparationTimeOrderByNameAscPreparationTimeAsc (String nameLike, int preparationTime);

}
