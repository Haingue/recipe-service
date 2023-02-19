package com.groupeun.recipe.infrastructure.input.mapper;

import com.groupeun.recipe.domain.model.RecipeStep;
import com.groupeun.recipe.infrastructure.input.dto.RecipeStepDto;
import org.springframework.stereotype.Component;

@Component
public class RecipeStepInputMapper {
    
    public RecipeStepDto modelToDto (RecipeStep model) {
        RecipeStepDto dto = new RecipeStepDto();
        dto.setRecipeId(model.getRecipeId());
        dto.setIngredientId(model.getIngredientId());
        dto.setStepNumber(model.getStepNumber());
        dto.setDescription(model.getDescription());
        return dto;
    }

    public RecipeStep dtoToModel (RecipeStepDto dto) {
        RecipeStep model = new RecipeStep();
        model.setRecipeId(dto.getRecipeId());
        model.setIngredientId(dto.getIngredientId());
        model.setStepNumber(dto.getStepNumber());
        model.setDescription(dto.getDescription());
        return model;
    }

}
