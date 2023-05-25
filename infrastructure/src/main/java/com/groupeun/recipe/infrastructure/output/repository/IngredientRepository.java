package com.groupeun.recipe.infrastructure.output.repository;

import com.groupeun.recipe.infrastructure.output.entity.IngredientEntity;
import com.groupeun.recipe.infrastructure.output.entity.id.IngredientEntityId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IngredientRepository extends CrudRepository<IngredientEntity, IngredientEntityId> {

    Set<IngredientEntity> findAllByIdRecipeId(String recipeId);

}
