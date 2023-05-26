package com.groupeun.recipe.infrastructure.output.repository;

import com.groupeun.recipe.infrastructure.output.entity.RecipeStepEntity;
import com.groupeun.recipe.infrastructure.output.entity.id.RecipeStepEntityId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RecipeStepRepository extends CrudRepository<RecipeStepEntity, RecipeStepEntityId> {

    Set<RecipeStepEntity> findAllByIdRecipeId(String recipeId);

}
