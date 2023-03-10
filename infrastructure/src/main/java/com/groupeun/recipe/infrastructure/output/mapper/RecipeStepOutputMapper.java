package com.groupeun.recipe.infrastructure.output.mapper;

import com.groupeun.recipe.domain.model.RecipeStep;
import com.groupeun.recipe.infrastructure.output.entity.RecipeStepEntity;
import com.groupeun.recipe.infrastructure.output.entity.id.RecipeStepId;
import org.springframework.stereotype.Component;

@Component
public class RecipeStepOutputMapper {

    public RecipeStepEntity modelToEntity (RecipeStep model) {
        RecipeStepId id = new RecipeStepId();
        id.setRecipeId(model.getRecipeId());
        id.setIngredientId(model.getIngredientId());

        RecipeStepEntity entity = new RecipeStepEntity();
        entity.setId(id);
        entity.setStepNumber(model.getStepNumber());
        entity.setDescription(model.getDescription());
        return entity;
    }

    public RecipeStep entityToModel (RecipeStepEntity entity) {
        RecipeStep model = new RecipeStep();
        model.setRecipeId(entity.getId().getRecipeId());
        model.setIngredientId(entity.getId().getIngredientId());
        model.setStepNumber(model.getStepNumber());
        model.setDescription(entity.getDescription());
        return model;
    }
}
