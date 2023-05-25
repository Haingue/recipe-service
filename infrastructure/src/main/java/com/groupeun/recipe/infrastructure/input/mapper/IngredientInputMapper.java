package com.groupeun.recipe.infrastructure.input.mapper;

import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;
import com.groupeun.recipe.infrastructure.input.dto.IngredientDto;
import com.groupeun.recipe.infrastructure.input.dto.RecipeDto;
import com.groupeun.recipe.infrastructure.input.dto.RecipeStepDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class IngredientInputMapper {

    public static IngredientDto modelToDto (Ingredient model) {
        IngredientDto dto = new IngredientDto();
        dto.setId(model.getId());
        dto.setQuantity(model.getQuantity());
        return dto;
    }

    public static Ingredient dtoToModel (IngredientDto dto) {
        Ingredient model = new Ingredient();
        model.setId(dto.getId());
        model.setQuantity(dto.getQuantity());
        return model;
    }

}
