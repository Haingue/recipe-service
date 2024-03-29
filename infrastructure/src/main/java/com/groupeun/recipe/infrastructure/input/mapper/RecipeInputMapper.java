package com.groupeun.recipe.infrastructure.input.mapper;

import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.infrastructure.input.dto.IngredientDto;
import com.groupeun.recipe.infrastructure.input.dto.RecipeDto;
import com.groupeun.recipe.infrastructure.input.dto.RecipeStepDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeInputMapper {

    @Autowired
    private RecipeStepInputMapper recipeStepInputMapper;

    public RecipeDto modelToDto (Recipe model) {
        RecipeDto dto = new RecipeDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setNutritionalScore(model.getNutritionalScore());
        dto.setPreparationTime(model.getPreparationTime());
        dto.setAuthorId(model.getAuthorId());
        model.getIngredients().stream().map(IngredientInputMapper::modelToDto)
                .forEach(dto.getIngredients()::add);
        model.getSteps().stream().map(recipeStepInputMapper::modelToDto)
                .forEach(dto.getSteps()::add);
        return dto;
    }

    public Recipe dtoToModel (RecipeDto dto) {
        Recipe model = new Recipe();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setNutritionalScore(dto.getNutritionalScore());
        model.setPreparationTime(dto.getPreparationTime());
        model.setAuthorId(dto.getAuthorId());
        for (IngredientDto ingredientDto : dto.getIngredients()) {
            model.addIngredient(IngredientInputMapper.dtoToModel(ingredientDto));
        }
        for (RecipeStepDto stepDto : dto.getSteps()) {
            model.getSteps().add(recipeStepInputMapper.dtoToModel(stepDto));
        }
        return model;
    }

}
